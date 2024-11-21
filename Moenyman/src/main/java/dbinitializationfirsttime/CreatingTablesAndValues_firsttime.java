package dbinitializationfirsttime;

import Data.Datacollection;
import Persistence.*;
import databaseinitialization.CreateAllDBTables;
import stockmarkethistoryapi.StockpriceshistoryAPI;
import stockmarkethistoryapi.TickerDetailsAPI;

import java.util.ArrayList;

public class CreatingTablesAndValues_firsttime {
    public static void creatingTablesandValues() throws InterruptedException {
        //Create all tables for the DataBase
      /*  String stringresult = CreateAllDBTables_firsttime.createTableStockPrices();
		System.out.println(stringresult);
		Thread.sleep(5000);
		//Get all Tickers:common stocks
		String url="https://api.polygon.io/v3/reference/tickers?type=CS&market=stocks&apiKey=tEboLkojLxgaz2hAj83wTQZIG1te0iLT";
		StoringAllTickers so=new StoringAllTickers();
		so.storeAllTickers(url);
		Thread.sleep(5000);"

        //Getting all Stocks and their details and storing it in the DB
        ArrayList<String> stocks = GetAllTickers.getAllTickers();
        StoringTickerDetails storingDetailsstocks = new StoringTickerDetails();

        //for(String stock:stocks) {
        int counter = 0;
        for (String stock : stocks) {
            counter++;
            String detailsticker = TickerDetailsAPI.getTickerDetails(stock);
            boolean valueboolean = storingDetailsstocks.storeTickerDetails(detailsticker);
            System.out.println(stock + valueboolean + " number " + counter);

        }
        String finise= GelAllDates.getAllDates();
        System.out.println("Finished collecting all dates from aappl"+finise);*/
        ArrayList<String> stocks2=GetAllTickers.getAllTickers();
        String enddate = Datacollection.getSQLDateString(0);
        String startdate = Datacollection.getSQLDateString(5);
        for (int i = 0; i < stocks2.size(); i++) {
            System.out.println(stocks2.get(i));
            String history = StockpriceshistoryAPI.getHistory(stocks2.get(i), startdate, enddate);

            StoringStockPricesDaily store = new StoringStockPricesDaily();
           String fin= store.storeDailyPrices(history);
           System.out.println(fin);
        }

    }

}
