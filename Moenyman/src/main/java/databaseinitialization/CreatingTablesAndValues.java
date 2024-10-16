package databaseinitialization;

import java.util.ArrayList;

import Data.Datacollection;
import Persistence.GetAllTickers;
import Persistence.StoringAllTickers;
import Persistence.StoringStockPricesDaily;
import stockmarkethistoryapi.StockpriceshistoryAPI;

public class CreatingTablesAndValues {
    public static void creatingTablesandValues() throws InterruptedException {
        //Create all tables for the DataBase
		String stringresult=CreateAllDBTables.createTableStockPrices();
		System.out.println(stringresult);
		Thread.sleep(5000);
		//Get all Tickers:common stocks
		String url="https://api.polygon.io/v3/reference/tickers?type=CS&market=stocks&apiKey=tEboLkojLxgaz2hAj83wTQZIG1te0iLT";
		StoringAllTickers so=new StoringAllTickers();
		so.storeAllTickers(url);
		Thread.sleep(5000);
        //Getting all Stocks and their details and storing it in the DB
       /* ArrayList<String> stocks = GetAllTickers.getAllTickers();
        StoringTickerDetails storingDetailsstocks = new StoringTickerDetails();

        //for(String stock:stocks) {
        int counter = 0;
        for (String stock : stocks) {
            counter++;
            String detailsticker = TickerDetailsAPI.getTickerDetails(stock);
            boolean valueboolean = storingDetailsstocks.storeTickerDetails(detailsticker);
            System.out.println(stock + valueboolean + " number " + counter);

        }*/
		ArrayList<String> stocks2=GetAllTickers.getAllTickers();
		String enddate=Datacollection.getSQLDateString(0);
		String startdate=Datacollection.getSQLDateString(5);
		for(int i=0;i<stocks2.size();i++) {
		//String history = StockpriceshistoryAPI.getHistory(stock,Datacollection.getSQLDateString(localDate, 5),Datacollection.getSQLDateString(localDate, 0));
		String history = StockpriceshistoryAPI.getHistory(stocks2.get(i),startdate,enddate);
		
		StoringStockPricesDaily store=new StoringStockPricesDaily();
		store.storeDailyPrices(history);}

    }

}
