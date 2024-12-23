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

public class StoringTickerDetails {




	public static Boolean storeTickerDetails(String response) {
		
		String sql = "INSERT INTO tickersdetails (ticker,list_date,market_cap,shares_outstanding,sic_description,sic_code,weighted_shares_outstanding) values(?,?,?,?,?,?,?);";

		try (Connection connection = DriverManager.getConnection(CredentialsPostGres.jdbcUrl, CredentialsPostGres.username, CredentialsPostGres.password)) {

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			JSONObject res = new JSONObject(response);

			// Extract the array of historical stock data
		JSONObject data = res.getJSONObject("results");
		
	

				String ticker = null;
				if (data.has("ticker")) {
					ticker= data.getString("ticker");
			
				}
				String listdatestring = null;
				java.sql.Date listdatesql=null;
				if (data.has("list_date")) {
					listdatestring= data.getString("list_date");
					listdatesql=Datacollection.getSQLDate(listdatestring);
						
				}
			

				double marketcap = 0;
				if (data.has("market_cap")) {
					marketcap = data.getDouble("market_cap");
				}
				long sharesoutstanding = 0;
				if (data.has("share_class_shares_outstanding")) {
					sharesoutstanding = data.getLong("share_class_shares_outstanding");
				}
				String category = null;
				if (data.has("sic_description")) {
					category= data.getString("sic_description");
			
				}
				int siccode = 0;
				if (data.has("sic_code")) {
					 String sicstring= data.getString("sic_code");
					 siccode=Integer.valueOf(sicstring);
			
				}
				double sharesoutstandingavg = 0;
				if (data.has("weighted_shares_outstanding")) {
					sharesoutstandingavg = data.getInt("weighted_shares_outstanding");
				}

	
			
				preparedStatement.setString(1, ticker);
				preparedStatement.setDate(2, listdatesql);
				preparedStatement.setDouble(3, marketcap);
				preparedStatement.setLong(4, sharesoutstanding);
				preparedStatement.setString(5, category);
				preparedStatement.setInt(6, siccode);
				preparedStatement.setDouble(7, sharesoutstandingavg);
				
				preparedStatement.addBatch();

			
			preparedStatement.executeBatch();
			System.out.println("Finsished");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return true;}
}