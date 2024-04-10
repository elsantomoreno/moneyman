package UserDefinedClasses;


import java.time.LocalDate;
import java.time.LocalTime;

public class StockIntraDayPrices {
	long id; 
	LocalDate date;
	LocalTime time;
	String stockname; 
	double tickOpen;
	double tickHigh;
	 double tickLow;
	double tickClose;
	long tickVol;
	double tickVolAvgPrice;
	long tickAvgTradeSize;
	double todayOpen;
	double todayVolAvgPrice;
	long accvol;
	
	public StockIntraDayPrices(long id, LocalDate date, LocalTime time, String stockname, double tickOpen,
			double tickHigh, double tickLow, double tickClose, long tickVol, double tickVolAvgPrice,
			long tickAvgTradeSize, double todayOpen, double todayVolAvgPrice, long accvol) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.stockname = stockname;
		this.tickOpen = tickOpen;
		this.tickHigh = tickHigh;
		this.tickLow = tickLow;
		this.tickClose = tickClose;
		this.tickVol = tickVol;
		this.tickVolAvgPrice = tickVolAvgPrice;
		this.tickAvgTradeSize = tickAvgTradeSize;
		this.todayOpen = todayOpen;
		this.todayVolAvgPrice = todayVolAvgPrice;
		this.accvol = accvol;
	}
	
	public StockIntraDayPrices(LocalDate date, LocalTime time, String stockname, double tickOpen, double tickHigh,
			double tickLow, double tickClose, long tickVol, double tickVolAvgPrice, long tickAvgTradeSize,
			double todayOpen, double todayVolAvgPrice, long accvol) {
		super();
		this.date = date;
		this.time = time;
		this.stockname = stockname;
		this.tickOpen = tickOpen;
		this.tickHigh = tickHigh;
		this.tickLow = tickLow;
		this.tickClose = tickClose;
		this.tickVol = tickVol;
		this.tickVolAvgPrice = tickVolAvgPrice;
		this.tickAvgTradeSize = tickAvgTradeSize;
		this.todayOpen = todayOpen;
		this.todayVolAvgPrice = todayVolAvgPrice;
		this.accvol = accvol;
	}

	public long getId() {
		return id;
		
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public String getStockname() {
		return stockname;
	}
	public void setStockname(String stockname) {
		this.stockname = stockname;
	}
	public double getTickOpen() {
		return tickOpen;
	}
	public void setTickOpen(double tickOpen) {
		this.tickOpen = tickOpen;
	}
	public double getTickHigh() {
		return tickHigh;
	}
	public void setTickHigh(double tickHigh) {
		this.tickHigh = tickHigh;
	}
	public double getTickLow() {
		return tickLow;
	}
	public void setTickLow(double tickLow) {
		this.tickLow = tickLow;
	}
	public double getTickClose() {
		return tickClose;
	}
	public void setTickClose(double tickClose) {
		this.tickClose = tickClose;
	}
	public long getTickVol() {
		return tickVol;
	}
	public void setTickVol(long tickVol) {
		this.tickVol = tickVol;
	}
	public double getTickVolAvgPrice() {
		return tickVolAvgPrice;
	}
	public void setTickVolAvgPrice(double tickVolAvgPrice) {
		this.tickVolAvgPrice = tickVolAvgPrice;
	}
	public long getTickAvgTradeSize() {
		return tickAvgTradeSize;
	}
	public void setTickAvgTradeSize(long tickAvgTradeSize) {
		this.tickAvgTradeSize = tickAvgTradeSize;
	}
	public double getTodayOpen() {
		return todayOpen;
	}
	public void setTodayOpen(double todayOpen) {
		this.todayOpen = todayOpen;
	}
	public double getTodayVolAvgPrice() {
		return todayVolAvgPrice;
	}
	public void setTodayVolAvgPrice(double todayVolAvgPrice) {
		this.todayVolAvgPrice = todayVolAvgPrice;
	}
	public long getAccvol() {
		return accvol;
	}
	public void setAccvol(long accvol) {
		this.accvol = accvol;
	}

}
