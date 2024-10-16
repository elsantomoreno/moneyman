package Persistence;

import Data.Datacollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayDeque;
import java.util.Queue;

public class StoringStockPricesDaily {


    public static void storeDailyPrices(String response) {
        String sql = "INSERT INTO stockprices_daily(date,ticker,open,high,low,close,"
                + "vol,volavgprice,wh52,wl52,dwh52,dwl52,ma10,ma21,ma50,ma200,avgvol10days,avgvol30days,sd,bolup,boldown,daysinbol) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try (Connection connection = DriverManager.getConnection(CredentialsPostGres.jdbcUrl, CredentialsPostGres.username, CredentialsPostGres.password)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            JSONObject res = new JSONObject(response);

            // Extract the array of historical stock data
            JSONArray results = res.getJSONArray("results");
            String ticker = res.getString("ticker");

            // Iterate over each entry in the historical stock data array
            Queue<Double> queue10 = new ArrayDeque<>();
            Queue<Double> queue21 = new ArrayDeque<>();
            Queue<Double> queue30 = new ArrayDeque<>();
            Queue<Double> queue50 = new ArrayDeque<>();
            Queue<Double> queue200 = new ArrayDeque<>();
            Queue<Long> vol10days = new ArrayDeque<>();
            Queue<Long> vol30days = new ArrayDeque<>();
            Date datesql = null;
            double open = 0;
            double high = 0;
            double low = 0;
            double close = 0;
            long vol = 0;
            double avgvolprice = 0;
            double wh52 = 0;
            double wl52 = 2845928375.0;
            Date datesql52wh = null;
            Date datesql52wl = null;
            double priceMA10 = 0;
            double priceMA21 = 0;
            double priceMA50 = 0;
            double priceMA200 = 0;
            double avgvol10days = 0;
            double avgvol30days = 0;
            double sd = 0;
            double bolup = 0;
            double boldown = 0;
            int daysinbol = 0;


            for (int i = 0; i < results.length(); i++) {

                JSONObject data = results.getJSONObject(i);


                if (data.has("t")) {
                    long t = data.getLong("t");

                    Instant instant = Instant.ofEpochMilli(t);
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
                    LocalDate date = localDateTime.toLocalDate();
                    datesql = Date.valueOf(date);


                }


                if (data.has("o")) {
                    open = data.getInt("o");
                }


                if (data.has("h")) {
                    high = data.getInt("h");
                    if (wh52 > high) {
                        wh52 = high;
                        datesql52wh = datesql;
                    }
                }

                if (data.has("l")) {
                    low = data.getInt("l");
                    if (low < wl52) {
                        wl52 = low;
                        datesql52wl = datesql;
                    }
                }


                if (data.has("c")) {
                    close = data.getInt("c");
                    queue10.offer(close);
                    queue21.offer(close);
                    queue30.offer(close);
                    queue50.offer(close);
                    queue200.offer(close);
                    if (queue10.size() > 9) {
                        priceMA10 = queue10.stream().mapToDouble(a -> a).average().getAsDouble();
                        queue10.poll();
                    }
                    if (queue21.size() > 20) {
                        priceMA21 = queue21.stream().mapToDouble(a -> a).average().getAsDouble();
                        queue21.poll();
                    }

                    if (queue30.size() > 29) {
                        sd = Datacollection.getSD(queue30);

                        double ma30 = queue30.stream().mapToDouble(a -> a).average().getAsDouble();
                        queue30.poll();
                        double sd2 = 2 * sd;
                        bolup = ma30 + sd2;
                        boldown = ma30 - sd2;
                    }
                    if (queue50.size() > 49) {
                        priceMA50 = queue50.stream().mapToDouble(a -> a).average().getAsDouble();
                        queue50.poll();
                    }
                    if (queue200.size() > 199) {
                        priceMA200 = queue200.stream().mapToDouble(a -> a).average().getAsDouble();
                        queue200.poll();
                    }
                }


                if (data.has("v")) {
                    vol = data.getInt("v");
                    vol30days.offer(vol);
                    vol10days.offer(vol);
                    if (vol30days.size() > 29) {
                        avgvol30days = vol30days.stream().mapToDouble(a -> a).average().getAsDouble();
                        vol30days.poll();
                    }
                    if (vol10days.size() > 9) {
                        avgvol10days = vol10days.stream().mapToDouble(a -> a).average().getAsDouble();
                        vol10days.poll();
                    }
                }


                if (data.has("vw")) {
                    avgvolprice = data.getInt("vw");
                }
                if(close<bolup&&close>boldown){daysinbol++;}else {
                    if(daysinbol>0)daysinbol--;
                }



                preparedStatement.setDate(1, datesql);
                preparedStatement.setString(2, ticker);
                preparedStatement.setDouble(3, open);
                preparedStatement.setDouble(4, high);
                preparedStatement.setDouble(5, low);
                preparedStatement.setDouble(6, close);
                preparedStatement.setLong(7, vol);
                preparedStatement.setDouble(8, avgvolprice);
                preparedStatement.setDouble(9, wh52);
                preparedStatement.setDouble(10, wl52);
                preparedStatement.setDate(11, datesql52wh);
                preparedStatement.setDate(12, datesql52wl);
                preparedStatement.setDouble(3, priceMA10);
                preparedStatement.setDouble(14, priceMA21);
                preparedStatement.setDouble(15, priceMA50);
                preparedStatement.setDouble(16, priceMA200);
                preparedStatement.setDouble(17, avgvol10days);
                preparedStatement.setDouble(18, avgvol30days);
                preparedStatement.setDouble(19, sd);
                preparedStatement.setDouble(20, bolup);
                preparedStatement.setDouble(21, boldown);
                preparedStatement.setDouble(22, daysinbol);


                preparedStatement.addBatch();

            }
            preparedStatement.executeBatch();
            System.out.println("Finsished");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}