package Persistence;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import UserDefinedClasses.StockPrices;

public class GetPriceHistory {
	public static StockPrices gethistory(String stock) {
	

		String sql = "select* from stockprices where ticker='"+stock+"'";

		StockPrices	 stockprices=null;
		try (Connection connection = DriverManager.getConnection(CredentialsPostGres.jdbcUrl, CredentialsPostGres.username, CredentialsPostGres.password)) {
			System.out.println(connection.toString());
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeQuery();
			ResultSet results = preparedStatement.getResultSet();
			
			while (results.next()) {
				String ticker = results.getString("ticker");
				Date datesql=results.getDate("date");
				double open=results.getDouble("open");
				double high=results.getDouble("high");
				double low=results.getDouble("low");
				double close=results.getDouble("close");
				double volume=results.getDouble("vol");
				double volavgprice=results.getDouble("volavgprice");
				double ma10=results.getDouble("ma10");
				double ma21=results.getDouble("ma21");
				double ma50=results.getDouble("ma50");
				double ma200=results.getDouble("ma200");
				double avgvol10days=results.getDouble("avgvol10days");
				double avgvol30days=results.getDouble("avgvol10days");
				double sd=results.getDouble("sd");
		 stockprices=	new StockPrices(datesql, ticker, open, high, low, close, volume,
						volavgprice, ma10, ma21, ma50, ma200, avgvol10days, avgvol30days,sd, 0, 0, null, null);	
			 
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return stockprices;}
	}
	


