
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.stream.Collectors;

import org.json.JSONObject;

import Data.Datacollection;
import FrontSide.GetStockHistory;
import Persistence.GetAllTickers;
import Persistence.GetPriceHistory;
import Persistence.StoringAllTickers;
import Persistence.StoringStockPricesDaily;
import Persistence.StoringTickerDetails;
import UserDefinedClasses.StockPrices;
import databaseinitialization.CreateAllDBTables;
import databaseinitialization.CreatingTablesAndValues;
import stockmarkethistoryapi.AllTickersAPI;
import stockmarkethistoryapi.StockpriceshistoryAPI;
import stockmarkethistoryapi.TickerDetailsAPI;
import websocket.ConnectingWebSocketStocks;

public class MainClass {

	public static void main(String[] args) throws InterruptedException {
/*ConnectingWebSocketStocks.connect();
StoringStockPrices store=new StoringStockPrices();
new Thread(store).start();*/
		String aa = "{\"ticker\":\"ONYX\"}";
	JSONObject al=new JSONObject(aa);
	String aaaaa=al.getString("ticker");
	 StockPrices stockprices= GetStockHistory.getHistory("HUGE");
	System.out.println(stockprices.toString());

	System.out.println();
	}

}
