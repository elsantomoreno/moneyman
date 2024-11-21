package databaseinitialization;

import Data.Datacollection;
import Persistence.GetAllTickers;
import Persistence.StoringIndexPricesDaily;
import Persistence.StoringIndexPricesDaily_Versuch;
import Persistence.StoringStockPricesDaily;
import stockmarkethistoryapi.StockpriceshistoryAPI;

import java.util.ArrayList;

public class CreatingTablesAndValues_onestock {
    public static void creatingTablesandValues() throws InterruptedException {


        String enddate = Datacollection.getSQLDateString(0);
        String startdate = Datacollection.getSQLDateString(5);

            String history = StockpriceshistoryAPI.getHistory("I:NDX", startdate, enddate);


           String fin= StoringIndexPricesDaily_Versuch.storeDailyPrices(history);
           System.out.println(fin);


    }

}
