package EconomicIndicators;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class FredEconIndicatorsAPI {

    // FRED API key
    private static final String API_KEY = "c97be4d1ad1e018c9ab8ff9fb032a483";
    private static final String BASE_FRED_URL = "https://api.stlouisfed.org/fred/series/observations?api_key=" + API_KEY + "&file_type=json";

    // PostgreSQL credentials
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/stocks";
    private static final String USERNAME = "andresjulioaguilartaveras";
    private static final String PASSWORD = "postgres";

    // Indicators map with series IDs and column names
    private static final Map<String, String> indicators = new HashMap<>() {{
        put("GDP", "gdp");                      // Gross Domestic Product
        put("UNRATE", "unemployment_rate");     // Unemployment Rate
        // Initial Claims for Unemployment Benefits
        put("UMCSENT", "consumer_confidence");  // Consumer Confidence
        put("HOUST", "housing_starts");         // Housing Starts
        put("EXHOSLUSM495S", "existing_home_sales"); // Existing Home Sales
        put("INDPRO", "industrial_production");  // Industrial Production
        put("T10Y2Y", "yield_curve");           // Yield Curve (10-Year - 2-Year Treasury spread)
        put("FEDFUNDS", "interest_rate");       // Federal Funds Rate
        put("CPILFESL", "core_cpi");            // Core CPI (All Items Less Food & Energy)
        put("RSAFS", "retail_sales");           // Retail Sales
    }};

    public static void main(String[] args) {
        try {
            System.out.println("Starting data update for all economic indicators...");

            for (Map.Entry<String, String> entry : indicators.entrySet()) {
                String seriesID = entry.getKey();
                String columnName = entry.getValue();

                // Fetch data from FRED API for the last 5 years
                System.out.println("Fetching data from FRED for: " + columnName);
                String jsonResponse = fetchDataFromFRED(seriesID);

                // Parse the JSON response
                Map<String, Double> dataByDate = parseData(jsonResponse);
                System.out.println("Finished fetching data for: " + columnName);

                // Add or modify the column in the table with sufficient precision
                addColumnIfNotExists(columnName);
                System.out.println("Finished adding column for: " + columnName);

                // Update the table with the data for the indicator
                updateIndicatorInTable(dataByDate, columnName);
                System.out.println("Finished saving data for: " + columnName + " in PostgreSQL.");
            }

            System.out.println("All indicators updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to fetch data from FRED API for the last 5 years
    private static String fetchDataFromFRED(String seriesID) throws Exception {
        LocalDate startDate = LocalDate.now().minusYears(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateStr = startDate.format(formatter);

        String urlWithDate = BASE_FRED_URL + "&series_id=" + seriesID + "&observation_start=" + startDateStr;

        URL url = new URL(urlWithDate);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    // Method to parse data from FRED JSON response
    private static Map<String, Double> parseData(String jsonResponse) {
        Map<String, Double> dataByDate = new HashMap<>();
        JSONArray observations = new JSONObject(jsonResponse).getJSONArray("observations");

        for (int i = 0; i < observations.length(); i++) {
            JSONObject observation = observations.getJSONObject(i);
            String date = observation.getString("date");
            double value;

            try {
                value = Double.parseDouble(observation.getString("value"));
            } catch (NumberFormatException e) {
                System.out.println("Skipping entry with invalid value for date: " + date);
                continue;
            }

            dataByDate.put(date, value);
        }

        return dataByDate;
    }

    // Method to add a column if it doesn't exist with sufficient precision
    private static void addColumnIfNotExists(String columnName) throws SQLException {
        String alterTableSQL = "ALTER TABLE econindicators ADD COLUMN IF NOT EXISTS " + columnName + " NUMERIC(15, 5);";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute(alterTableSQL);
        }
    }

    // Method to update data for a specific indicator in the table
    private static void updateIndicatorInTable(Map<String, Double> dataByDate, String columnName) throws SQLException {
        String selectDatesSQL = "SELECT date, " + columnName + " FROM econindicators ORDER BY date;";
        String updateSQL = "UPDATE econindicators SET " + columnName + " = ? WHERE date = ?;";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectDatesSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            Double lastKnownValue = null;

            while (resultSet.next()) {
                String date = resultSet.getDate("date").toString();
                Double value = dataByDate.get(date);

                if (value != null) {
                    lastKnownValue = value;
                } else if (lastKnownValue != null) {
                    value = lastKnownValue;
                } else {
                    System.out.println("No available data for date " + date + " and no previous value to fill.");
                    continue;
                }

                preparedStatement.setDouble(1, value);
                preparedStatement.setDate(2, resultSet.getDate("date"));
                preparedStatement.executeUpdate();
            }
        }
    }
}
