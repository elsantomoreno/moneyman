package databaseinitialization;

import Data.Datacollection;
import Persistence.StoringStockPricesDaily;
import stockmarkethistoryapi.StockpriceshistoryAPI;

public class MainClassDataInitialization {
    public static void main(String[] args) throws InterruptedException {
        //Creating all needed tables at the DB on inititialization
        CreatingTablesAndValues_onestock.creatingTablesandValues();

    }
}
