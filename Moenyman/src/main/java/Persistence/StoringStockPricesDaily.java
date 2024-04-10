package Persistence;

import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import org.json.JSONArray;
import org.json.JSONObject;

import Data.Datacollection;
import UserDefinedClasses.StockIntraDayPrices;
import UserDefinedClasses.StockPrices;

public class StoringStockPricesDaily {
	String jdbcUrl;
	String username;
	String password;

	public StoringStockPricesDaily() {
		super();
		this.jdbcUrl = "jdbc:postgresql://localhost:5432/stocks";
		this.username = "postgres";
		this.password = "stocks";

	}

	public void storeDailyPrices(String response) {
		String sql = "INSERT INTO stockprices(date,ticker,open,high,low,close,"
				+ "vol,volavgprice,ma10,ma21,ma50,ma200,avgvol10days,avgvol30days,sd) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			JSONObject res = new JSONObject(response);

			// Extract the array of historical stock data
			JSONArray results = res.getJSONArray("results");
			String ticker = res.getString("ticker");

			// Iterate over each entry in the historical stock data array
			for (int i = 0; i < results.length(); i++) {
				JSONObject data = results.getJSONObject(i);

				java.sql.Date datesql = null;
				if (data.has("t")) {
					long t = data.getLong("t");

					Instant instant = Instant.ofEpochMilli(t);
					LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
					LocalDate date = localDateTime.toLocalDate();
					datesql = Date.valueOf(date);

				}

				double open = 0;
				if (data.has("o")) {
					open = data.getInt("o");
				}

				double high = 0;
				if (data.has("h")) {
					high = data.getInt("h");
				}

				double low = 0;
				if (data.has("l")) {
					low = data.getInt("l");
				}

				double close = 0;
				if (data.has("c")) {
					close = data.getInt("c");
				}

				long vol = 0;
				if (data.has("v")) {
					vol = data.getInt("v");
				}

				double avgvolprice = 0;
				if (data.has("vw")) {
					avgvolprice = data.getInt("vw");
				}

				Data.Datacollection.MA_QUEUE.offer(new StockPrices(datesql, ticker, open, high, low, close, vol,
						avgvolprice, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null));
System.out.println(datesql+"open: "+open+" high: "+high+" low: "+low+" close: "+close+" volume: "+vol);
				double ma10 = Datacollection.getMA(10);
				double ma21 = Datacollection.getMA(21);
				double ma50 = Datacollection.getMA(50);
				double ma200 = Datacollection.getMA(200);
				double avgvol10days = Datacollection.getAvgVoldays(10);
				double avgvol30days = Datacollection.getAvgVoldays(30);
				double sd = Datacollection.getSD(30);

				StockPrices lastStockPrices = Data.Datacollection.MA_QUEUE.poll();
				lastStockPrices.setMa10(ma10);
				lastStockPrices.setMa21(ma21);
				lastStockPrices.setMa50(ma50);
				lastStockPrices.setMa200(ma200);
				lastStockPrices.setAvgvol10days(avgvol10days);
				lastStockPrices.setAvgvol30days(avgvol30days);
				lastStockPrices.setSd(sd);
				lastStockPrices.setWeek52high(high);
				lastStockPrices.setWeek52low(low);
				lastStockPrices.setDate52high(datesql);
				lastStockPrices.setDate52low(datesql);
				Data.Datacollection.MA_QUEUE.add(lastStockPrices);

				preparedStatement.setDate(1, datesql);
				preparedStatement.setString(2, ticker);
				preparedStatement.setDouble(3, open);
				preparedStatement.setDouble(4, high);
				preparedStatement.setDouble(5, low);
				preparedStatement.setDouble(6, close);
				preparedStatement.setLong(7, vol);
				preparedStatement.setDouble(8, avgvolprice);

				preparedStatement.setDouble(9, ma10);
				preparedStatement.setDouble(10, ma21);
				preparedStatement.setDouble(11, ma50);
				preparedStatement.setDouble(12, ma200);
				preparedStatement.setDouble(13, avgvol10days);
				preparedStatement.setDouble(14, avgvol30days);
				preparedStatement.setDouble(15, sd);

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