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
import stockmarkethistoryapi.AllTickersAPI;

public class StoringAllTickers {


	public StoringAllTickers() {
		super();


	}

	public void storeAllTickers(String url) {
		String response=AllTickersAPI.getAllTickers(url);
		String sql = "INSERT INTO allticker (active,ticker) VALUES(?,?);";

		try (Connection connection = DriverManager.getConnection(CredentialsPostGres.jdbcUrl, CredentialsPostGres.username, CredentialsPostGres.password)) {

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			JSONObject res = new JSONObject(response);

			// Extract the array of historical stock data
			JSONArray results = res.getJSONArray("results");

		

			// Iterate over each entry in the historical stock data array
			for (int i = 0; i < results.length(); i++) {
				JSONObject data = results.getJSONObject(i);

			boolean active = false;
				if (data.has("active")) {
					 active = data.getBoolean("active");

			
				}

				String ticker = null;
				if (data.has("ticker")) {
					ticker = data.getString("ticker");
				}

				preparedStatement.setBoolean(1, active);
				preparedStatement.setString(2, ticker);
				

				preparedStatement.addBatch();

			}
			preparedStatement.executeBatch();
			StoringAllTickers so=new StoringAllTickers();
			String nexturl = res.getString("next_url");
		String next=nexturl+"&apiKey=tEboLkojLxgaz2hAj83wTQZIG1te0iLT";
			
		so.storeAllTickers(next);
			System.out.println("Finsished storing all tickers");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
