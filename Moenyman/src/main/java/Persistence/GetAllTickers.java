package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class GetAllTickers {

	public static ArrayList<String> getAllTickers() {
		ArrayList<String> allstockslist=new ArrayList<>();
		String jdbcUrl = "jdbc:postgresql://localhost:5432/stocks";
		String username = "postgres";
		String password = "stocks";
		String sql = "select distinct ticker from alltickers;";

		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			System.out.println(connection.toString());
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeQuery();
			ResultSet results = preparedStatement.getResultSet();
			
			while (results.next()) {
				String ticker = results.getString("ticker");
				allstockslist.add(ticker);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return allstockslist;}
}
