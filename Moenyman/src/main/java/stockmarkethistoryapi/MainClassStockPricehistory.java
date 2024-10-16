package stockmarkethistoryapi;

import Persistence.StoringStockPricesDaily;

import java.util.ArrayList;

public class MainClassStockPricehistory {
    public static void main(String[] args) {
        ArrayList<String> stocks = new ArrayList<>();
        // stocks= GetAllTickers.getAllTickers();


        String response = StockpriceshistoryAPI.getHistory("AAPL", "2024-04-05", "2024-06-05");
        System.out.println(response);
        StoringStockPricesDaily.storeDailyPrices(response);


    }
}
