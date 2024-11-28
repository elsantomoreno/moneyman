package Persistence;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import Data.Datacollection;
import UserDefinedClasses.StockIntraDayPrices;


public class StoringStockPricesIntra implements Runnable {


    public void run() {


        while (true) {
            try {

                try (Connection connection = DriverManager.getConnection(CredentialsPostGres.jdbcUrl, CredentialsPostGres.username, CredentialsPostGres.password)) {
                    String sql = "INSERT INTO intradayprices(date,time,ticker,tickopen,tickhigh,ticklow,tickclose,"
                            + "tickvol,tickvolavgprice,tickavgtradesize,todayopen,todayvolvvgprice,accvol) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);


                    while (Datacollection.COUNTER < 200) {
                        Datacollection.COUNTER++;
                        StockIntraDayPrices intraprice = Data.Datacollection.INTRADAY_ARRAY.take();
                        //System.out.println("Date directly: "+intraprice.getDate());
                        Date datesql = Date.valueOf(intraprice.getDate());
                        Time timesql = Time.valueOf(intraprice.getTime());

                        preparedStatement.setDate(1, datesql);
                        preparedStatement.setTime(2, timesql);
                        preparedStatement.setString(3, intraprice.getStockname());
                        preparedStatement.setDouble(4, intraprice.getTickOpen());
                        preparedStatement.setDouble(5, intraprice.getTickHigh());
                        preparedStatement.setDouble(6, intraprice.getTickLow());
                        preparedStatement.setDouble(7, intraprice.getTickClose());
                        preparedStatement.setLong(8, intraprice.getTickVol());
                        preparedStatement.setDouble(9, intraprice.getTickVolAvgPrice());
                        preparedStatement.setLong(10, intraprice.getTickAvgTradeSize());

                        preparedStatement.setDouble(11, intraprice.getTodayOpen());

                        preparedStatement.setDouble(12, intraprice.getAccvol());
                        preparedStatement.setDouble(13, intraprice.getTodayVolAvgPrice());

                        

                        preparedStatement.addBatch();


                    }
                    Datacollection.COUNTER = 0;

                    preparedStatement.executeBatch();


                    System.out.println("Finsished" + Datacollection.COUNTER);

                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
         
   


