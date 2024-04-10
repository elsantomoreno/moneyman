package Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import UserDefinedClasses.StockIntraDayPrices;
import UserDefinedClasses.StockPrices;

public class Datacollection {
	public static final BlockingQueue<StockIntraDayPrices> INTRADAY_ARRAY = new LinkedBlockingQueue<>();

	public static final Queue<StockPrices> MA_QUEUE = new ArrayDeque<>();
	
	public static double getMA(int number) {
		return MA_QUEUE.stream().skip(Math.max(0,Data.Datacollection.MA_QUEUE.size() - number))
        .collect(Collectors.toList()).stream().mapToDouble(a->a.getClose()).average().orElse(0);
		
	}
	
	public static double getAvgVoldays(int days) {
		return MA_QUEUE.stream().skip(Math.max(0,Data.Datacollection.MA_QUEUE.size() - days))
        .collect(Collectors.toList()).stream().mapToDouble(a->a.getVol()).average().orElse(0);
		
	}


	public static java.sql.Date getSQLDate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sqlDate = null;
		try {
			java.util.Date utilDate = sdf.parse(dateString);
			sqlDate = new java.sql.Date(utilDate.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sqlDate;
	}

	public static String getSQLDateString(int years) {
		
		LocalDate localDatenow = LocalDate.now();
		LocalDate localdate=localDatenow.minusYears(years);
		int dayofmonth = localdate.getDayOfMonth();
		String daystring=String.valueOf(dayofmonth);
		if(dayofmonth<10)daystring="0"+daystring;
		int month = localdate.getMonthValue();
		String monthstring=String.valueOf(month);
		if(month<10)monthstring="0"+monthstring;
		int year = localdate.getYear();
		String startdatestring = year + "-" + monthstring + "-" + daystring;
		
		return startdatestring;

	}



	public static double getSD(int days) {
		double average =getMA(30);

		// Calculate the variance of the last 200 elements
		double variance =MA_QUEUE.stream()
                .skip(Math.max(0, MA_QUEUE.size() - days))
                .mapToDouble(a -> Math.pow(a.getClose() - average, 2))
                .average()
                .orElse(0.0);;

		// Calculate the standard deviation
		return Math.sqrt(variance);
	}

	public static int COUNTER = 0;

	private static String toJson(List<StockIntraDayPrices> stocks) {
		StringBuilder json = new StringBuilder("[");
		for (StockIntraDayPrices stock : stocks) {
			json.append("{");
			json.append("\"date\": \"").append(stock.getDate()).append("\", ");
			json.append("\"time\": \"").append(stock.getTime()).append("\", ");
			json.append("\"stockname\": \"").append(stock.getStockname()).append("\", ");
			json.append("\"open\": ").append(stock.getTickOpen()).append(", ");
			json.append("\"high\": ").append(stock.getTickHigh()).append(", ");
			json.append("\"low\": ").append(stock.getTickLow()).append(", ");
			json.append("\"close\": ").append(stock.getTickClose()).append(", ");
			json.append("\"volume\": ").append(stock.getTickVol());
			json.append("}, ");

		}
		if (!stocks.isEmpty()) {
			json.setLength(json.length() - 2); // Remove trailing comma and space
		}
		json.append("]");
		return json.toString();

	}
	public static String getStockPricesInfoJson(StockPrices stock) {
		StringBuilder json = new StringBuilder("[");

			json.append("{");
			json.append("\"date\": \"").append(stock.getDate()).append("\", ");
			json.append("\"stockname\": \"").append(stock.getSymbol()).append("\", ");
			json.append("\"open\": ").append(stock.getOpen()).append(", ");
			json.append("\"high\": ").append(stock.getHigh()).append(", ");
			json.append("\"low\": ").append(stock.getLow()).append(", ");
			json.append("\"close\": ").append(stock.getClose()).append(", ");
			json.append("\"vol\": ").append(stock.getVol()).append(", ");
			
			json.append("\"volavgprice\": ").append(stock.getVolavgprice()).append(", ");
			json.append("\"ma10\": ").append(stock.getMa10()).append(", ");
			json.append("\"ma21\": ").append(stock.getMa21()).append(", ");
			json.append("\"ma50\": ").append(stock.getMa50()).append(", ");
			json.append("\"ma200\": ").append(stock.getMa200()).append(", ");
			json.append("\"avgvol10days\": ").append(stock.getAvgvol10days()).append(", ");
			json.append("\"avgvol30days\": ").append(stock.getAvgvol30days()).append(", ");
			json.append("\"sd\": ").append(stock.getSd()).append(", ");
			json.append("\"week52high\": ").append(stock.getWeek52high()).append(", ");
			
			json.append("\"week52low\": ").append(stock.getWeek52low()).append(", ");
			json.append("\"date52high\": ").append(stock.getDate52high()).append(", ");
			json.append("\"sddate52low\": ").append(stock.getDate52low()).append(", ");
			json.append("}");
		json.append("]");
		return json.toString();

	}

    public static String getJsonValue(String jsonString, String key) {
        // Find the index of the key in the JSON string
        int keyIndex = jsonString.indexOf("\"" + key + "\"");
        if (keyIndex == -1) {
            // Key not found
            return null;
        }

        // Find the index of the colon (:) after the key
        int colonIndex = jsonString.indexOf(':', keyIndex);
        if (colonIndex == -1) {
            // Malformed JSON
            return null;
        }

        // Find the index of the value after the colon
        int valueIndex = jsonString.indexOf('"', colonIndex + 1);
        if (valueIndex == -1) {
            // Malformed JSON
            return null;
        }

        // Find the index of the closing double quote (") of the value
        int closingQuoteIndex = jsonString.indexOf('"', valueIndex + 1);
        if (closingQuoteIndex == -1) {
            // Malformed JSON
            return null;
        }

        // Extract the value substring
        String value = jsonString.substring(valueIndex + 1, closingQuoteIndex);

        return value;
    }

}
