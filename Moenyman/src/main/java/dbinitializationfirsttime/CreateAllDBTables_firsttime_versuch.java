package dbinitializationfirsttime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateAllDBTables_firsttime_versuch {
    public static String createTableStockPrices() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/stocks";
        String username = "andresjulioaguilartaveras";
        String password = "postgres";
        String usindex = "CREATE TABLE IF NOT EXISTS index_daily (date DATE, ticker VARCHAR(6)," +
                "open Numeric(15,2), " +
                "high Numeric(15,2)," +
                "low Numeric(15,2), " +
                "close Numeric(15,2), " +
                "vol INT, " +
                "volavgprice Numeric(15,2)," +
                "wh52 Numeric(15,2),"+
                "wl52 Numeric(15,2),"+
                "dwh52 Date,"+
                "dwl52 Date,"+
                "ma10 Numeric(15,2), " +
                "ema21 Numeric(15,2), " +
                "ma50 Numeric(15,2), " +
                "ma200 Numeric(15,2), " +
                "avgvol10days Numeric(15,2)," +
                "avgvol30days Numeric(15,2)," +
                "sd Numeric(15,2)," +
                "bolup Numeric(15,2)," +
                "boldown Numeric(15,2)," +
                "daysinbol INT," +
                "ma10abvma21ema Boolean,"+
                "ema21abv50ma Boolean, "+
                "countnewhigh INT, "+
                "countnewlow INT, "+
                "distbdays INT, "+
                "cblw21ema INT, "+
                "cblw50ma INT, "+
                "cblw200ma INT, "+
                "cabv21ema INT, "+
                "cabv50ma INT, "+
                "cabv200ma INT, "+
                "PRIMARY KEY (date, ticker));";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

                PreparedStatement preparedStatement = connection.prepareStatement(usindex);

                preparedStatement.execute();


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return "All tables created";
    }

}
