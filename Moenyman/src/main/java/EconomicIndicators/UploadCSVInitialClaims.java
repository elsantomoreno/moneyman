package EconomicIndicators;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class UploadCSVInitialClaims {

    // PostgreSQL credentials
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/stocks";
    private static final String USERNAME = "andresjulioaguilartaveras";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        try {
            System.out.println("Starting update of Initial Claims data...");

            // Step 1: Parse CSV data
            String filePath = System.getProperty("user.home") + "/Desktop/ICSA.csv";
            Map<String, Double> claimsData = parseCSV(filePath);
            System.out.println("Finished parsing CSV file.");

            // Step 2: Add initial_claims column if it doesn't exist
            addInitialClaimsColumn();
            System.out.println("Finished adding column for initial_claims.");

            // Step 3: Insert or update data in econindicators table
            updateInitialClaimsInTable(claimsData);
            System.out.println("Initial Claims data update complete.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to parse the CSV file and return a map of date and initial claims
    private static Map<String, Double> parseCSV(String filePath) throws Exception {
        Map<String, Double> claimsData = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        // Read the CSV file line by line
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            String date = values[0].trim();
            double initialClaims;


            try {
                initialClaims = Double.parseDouble(values[0].trim());
                System.out.println(initialClaims);
            } catch (NumberFormatException e) {
              //  System.out.println("Skipping invalid value in CSV for date: " + date);
                continue;
            }

            claimsData.put(date, initialClaims);
        }
        br.close();
        return claimsData;
    }

    // Method to add 'initial_claims' column if it doesn't exist
    private static void addInitialClaimsColumn() throws SQLException {
        String alterTableSQL = "ALTER TABLE econindicators ADD COLUMN IF NOT EXISTS initial_claims NUMERIC(15, 5);";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute(alterTableSQL);
        }
    }

    // Method to update initial claims in the table with fill-forward for missing data
    private static void updateInitialClaimsInTable(Map<String, Double> claimsData) throws SQLException {
        String selectDatesSQL = "SELECT date FROM econindicators ORDER BY date;";
        String updateSQL = "UPDATE econindicators SET initial_claims = ? WHERE date = ?;";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectDatesSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            Double lastKnownClaims = null;

            while (resultSet.next()) {
                String date = resultSet.getDate("date").toString();
                Double claims = claimsData.get(date);

                // Use current claims if available, otherwise use the last known claims
                if (claims != null) {
                    lastKnownClaims = claims;
                } else if (lastKnownClaims != null) {
                    claims = lastKnownClaims;
                } else {
                    System.out.println("No available data for date " + date + " and no previous value to fill.");
                    continue;
                }

                preparedStatement.setDouble(1, claims);
                preparedStatement.setDate(2, resultSet.getDate("date"));
                preparedStatement.executeUpdate();
            }
        }
    }
}
