package FrontSide;

import Persistence.GetPriceHistory;
import UserDefinedClasses.StockPrices;

public class GetStockHistory {
	public static StockPrices getHistory(String stock) {
		
		return GetPriceHistory.gethistory(stock);
	}

}
