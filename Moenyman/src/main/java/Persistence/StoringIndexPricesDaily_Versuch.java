package Persistence;

import Calculation.ExponentialMovingAverage;
import Data.Datacollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class StoringIndexPricesDaily_Versuch {


    public static String storeDailyPrices(String response) {
        String sql = "UPDATE index_daily SET open = ?, high = ?, low = ?, close = ?, vol = ?, volavgprice = ?, "
                + "wh52 = ?, wl52 = ?, dwh52 = ?, dwl52 = ?, ma10 = ?, ema21 = ?, ma50 = ?, ma200 = ?, "
                + "avgvol10days = ?, avgvol30days = ?, sd = ?, bolup = ?, boldown = ?, daysinbol = ?, "
                + "ma10abvma21ema = ?, ema21abv50ma = ? WHERE ticker = 'QQQ' AND date = ?;";
        int counter = 0;
        String ticker = null;
        try (Connection connection = DriverManager.getConnection(CredentialsPostGres.jdbcUrl, CredentialsPostGres.username, CredentialsPostGres.password)) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            JSONObject res = new JSONObject(response);

            // Extract the array of historical stock data
            JSONArray results = res.getJSONArray("results");
            ticker = res.getString("ticker");

            // Iterate over each entry in the historical stock data array
            Queue<Double> queue10 = new ArrayDeque<>();
            Queue<Double> queue21 = new ArrayDeque<>();
            Queue<Double> queue30 = new ArrayDeque<>();
            Queue<Double> queue50 = new ArrayDeque<>();
            Queue<Double> queue200 = new ArrayDeque<>();
            Queue<Long> vol10days = new ArrayDeque<>();
            Queue<Long> vol30days = new ArrayDeque<>();
            Queue<Map.Entry<Double, Date>> high365days = new ArrayDeque<>();
            Queue<Map.Entry<Double, Date>> low365days = new ArrayDeque<>();
            Queue<Integer> daysinbol = new ArrayDeque<>();
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
            long totaldaybol = 0;
            boolean ma10abv21 = false;
            boolean ema21abv50 = false;


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
                    open = data.getDouble("o");
                }


                if (data.has("h")) {
                    high = data.getDouble("h");
                    high365days.offer(new AbstractMap.SimpleEntry<>(high, datesql));
                    if (high365days.size() > 252) {
                        high365days.poll();
                        Map.Entry<Double, Date> maxEntry = high365days.stream()
                                .max(Comparator.comparingDouble(Map.Entry::getKey)).get();

                        wh52 = maxEntry.getKey();
                        datesql52wh = maxEntry.getValue();


                    }
                }

                if (data.has("l")) {
                    low = data.getDouble("l");
                    low365days.offer(new AbstractMap.SimpleEntry<>(low, datesql));
                    if (low365days.size() > 252) {
                        low365days.poll();
                        Map.Entry<Double, Date> minEntry = low365days.stream()
                                .min(Comparator.comparingDouble(Map.Entry::getKey)).get();

                        wl52 = minEntry.getKey();
                        datesql52wl = minEntry.getValue();


                    }
                }


                if (data.has("c")) {
                    close = data.getDouble("c");
                    queue10.offer(close);
                    queue21.offer(close);
                    queue30.offer(close);
                    queue50.offer(close);
                    queue200.offer(close);
                }
                if (queue10.size() > 9) {
                    priceMA10 = queue10.stream().mapToDouble(a -> a).average().getAsDouble();
                    queue10.poll();
                }


                if (queue21.size() < 21) {


                } else if (queue21.size() == 21) {
                    priceMA21 = queue21.stream().mapToDouble(a -> a).average().getAsDouble();

                } else {

                    priceMA21 = ExponentialMovingAverage.calculateRecentEMA(close, 21, priceMA21);
                    if (priceMA10 > priceMA21) {
                        ma10abv21 = true;
                    } else {
                        ma10abv21 = false;
                    }
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
                        if (priceMA21 > priceMA50) {
                            ema21abv50 = true;
                        } else {
                            ema21abv50 = false;
                        }
                    }
                    if (queue200.size() > 199) {
                        priceMA200 = queue200.stream().mapToDouble(a -> a).average().getAsDouble();
                        queue200.poll();
                    }



                if (data.has("v")) {
                    vol = data.getLong("v");
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
                    avgvolprice = data.getDouble("vw");
                }
                if (daysinbol.size() > 39) daysinbol.poll();
                if (close < bolup && close > boldown) {
                    daysinbol.offer(1);
                } else {
                    daysinbol.offer(0);
                }
                totaldaybol = daysinbol.stream().filter(a -> a == 1).count();


                //  System.out.println("date: " + datesql.toString() + " open: " + open + " high: " + high + " low: " + low + " close: " + close + " counter: " + counter++);
                preparedStatement.setDouble(1, open);
                preparedStatement.setDouble(2, high);
                preparedStatement.setDouble(3, low);
                preparedStatement.setDouble(4, close);
                preparedStatement.setLong(5, vol);
                preparedStatement.setDouble(6, avgvolprice);
                preparedStatement.setDouble(7, wh52);
                preparedStatement.setDouble(8, wl52);
                preparedStatement.setDate(9, datesql52wh);
                preparedStatement.setDate(10, datesql52wl);
                preparedStatement.setDouble(11, priceMA10);
                preparedStatement.setDouble(12, priceMA21);
                preparedStatement.setDouble(13, priceMA50);
                preparedStatement.setDouble(14, priceMA200);
                preparedStatement.setDouble(15, avgvol10days);
                preparedStatement.setDouble(16, avgvol30days);
                preparedStatement.setDouble(17, sd);
                preparedStatement.setDouble(18, bolup);
                preparedStatement.setDouble(19, boldown);
                preparedStatement.setLong(20, totaldaybol);
                preparedStatement.setBoolean(21, ma10abv21);
                preparedStatement.setBoolean(22, ema21abv50);
                preparedStatement.setDate(23, datesql);


                preparedStatement.addBatch();

            }
            preparedStatement.executeBatch();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Finished: " + ticker;
    }
}