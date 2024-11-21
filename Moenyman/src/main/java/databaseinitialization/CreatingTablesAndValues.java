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
        String stringresult = CreateAllDBTables.createTableStockPrices();
        System.out.println(stringresult);
        ArrayList<String> stocks2=GetAllTickers.getAllTickers();
        String enddate = Datacollection.getSQLDateString(0);
        String startdate = Datacollection.getSQLDateString(5);
        for (int i = 0; i < stocks2.size(); i++) {
            String history = StockpriceshistoryAPI.getHistory(stocks2.get(i), startdate, enddate);

            StoringStockPricesDaily store = new StoringStockPricesDaily();
           String fin= store.storeDailyPrices(history);
           System.out.println(fin);
        }

    }

}
