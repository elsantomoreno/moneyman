package Persistence;

import Data.Datacollection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Queue;


public class GelAllDates {

	public static String getAllDates() {


		String sql = "select distinct date from stockprices_daily where ticker='AAPL' order by date asc;";

		try (Connection connection = DriverManager.getConnection(CredentialsPostGres.jdbcUrl, CredentialsPostGres.username, CredentialsPostGres.password)) {
			System.out.println(connection.toString());
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeQuery();
			ResultSet results = preparedStatement.getResultSet();
			
			while (results.next()) {
				Date datesql = results.getDate("date");
				java.util.Date date = new java.util.Date(datesql.getTime());
				Datacollection.DATE_QUEUE.offer(date);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return "finished";}
}
