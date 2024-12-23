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

		String sql = "select distinct ticker from allticker where ticker not in(select distinct ticker from stockprices_daily);";


		try (Connection connection = DriverManager.getConnection(CredentialsPostGres.jdbcUrl, CredentialsPostGres.username, CredentialsPostGres.password)) {
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
