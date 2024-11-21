package dbinitializationfirsttime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateAllDBTables_firsttime {
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
        String sqlstockprices = "CREATE TABLE IF NOT EXISTS stockprices_daily (date DATE, ticker VARCHAR(6)," +
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
                "PRIMARY KEY (date, ticker)," +
              "FOREIGN KEY (ticker) REFERENCES tickersdetails(ticker));";
        String sqltickersdetails = "CREATE TABLE tickersdetails(ticker VARCHAR(6),list_date DATE,delisted_utc DATE,market_cap Numeric(20,2),shares_outstanding NUMERIC(20, 0)"
                + ",sic_description VARCHAR(80),sic_code int,weighted_shares_outstanding Numeric(20,2),PRIMARY KEY(ticker))";
        String sqlalltickers = "CREATE TABLE IF NOT EXISTS allticker(active BOOLEAN,ticker VARCHAR(15));";
        String intradayprices = "CREATE TABLE intradayprices (date DATE, time TIME,ticker VARCHAR(10), " +
                "tickopen Numeric(12,2)," +
                "tickhigh Numeric(12,2), " +
                "ticklow Numeric(12,2), " +
                "tickclose Numeric(12,2), " +
                "tickvol BIGINT, " +
                "tickvolavgprice Numeric(12,2), " +
                "tickavgtradesize BIGINT, " +
                "todayopen Numeric(12,2), " +
                "todayvolvvgprice Numeric(12,2), " +
                "accvol BIGINT);";
        ArrayList<String> sqlcommands = new ArrayList<>();
        sqlcommands.add(sqltickersdetails);
        sqlcommands.add(sqlstockprices);
        sqlcommands.add(sqlalltickers);
       sqlcommands.add(intradayprices);
        for (int i = 0; i < sqlcommands.size(); i++) {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

                PreparedStatement preparedStatement = connection.prepareStatement(sqlcommands.get(i));

                preparedStatement.execute();


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "All tables created";
    }

}
