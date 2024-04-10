package UserDefinedClasses;

import java.util.Date;

public class StockPrices {
	
	private Date date;
	private String symbol;
	private double open;
	private double high;
	private double low;
	private double close;
	private double vol;
	private double volavgprice;
	private double ma10;
	private double ma21;
	private double ma50;
	private double ma200;
	private double avgvol10days;
	private double avgvol30days;
	private double sd;
	private double week52high;
	private double week52low;
	private Date date52high;
	private Date date52low;
	public StockPrices(Date date, String symbol, double open, double high, double low, double close, double vol,
			double volavgprice, double ma10, double ma21, double ma50, double ma200, double avgvol10days,
			double avgvol30days, double sd, double week52high, double week52low, Date date52high, Date date52low) {
		super();
		this.date = date;
		this.symbol = symbol;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.vol = vol;
		this.volavgprice = volavgprice;
		this.ma10 = ma10;
		this.ma21 = ma21;
		this.ma50 = ma50;
		this.ma200 = ma200;
		this.avgvol10days = avgvol10days;
		this.avgvol30days = avgvol30days;
		this.sd = sd;
		this.week52high = week52high;
		this.week52low = week52low;
		this.date52high = date52high;
		this.date52low = date52low;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getVol() {
		return vol;
	}
	public void setVol(double vol) {
		this.vol = vol;
	}
	public double getVolavgprice() {
		return volavgprice;
	}
	public void setVolavgprice(double volavgprice) {
		this.volavgprice = volavgprice;
	}
	public double getMa10() {
		return ma10;
	}
	public void setMa10(double ma10) {
		this.ma10 = ma10;
	}
	public double getMa21() {
		return ma21;
	}
	public void setMa21(double ma21) {
		this.ma21 = ma21;
	}
	public double getMa50() {
		return ma50;
	}
	public void setMa50(double ma50) {
		this.ma50 = ma50;
	}
	public double getMa200() {
		return ma200;
	}
	public void setMa200(double ma200) {
		this.ma200 = ma200;
	}
	public double getAvgvol10days() {
		return avgvol10days;
	}
	public void setAvgvol10days(double avgvol10days) {
		this.avgvol10days = avgvol10days;
	}
	public double getAvgvol30days() {
		return avgvol30days;
	}
	public void setAvgvol30days(double avgvol30days) {
		this.avgvol30days = avgvol30days;
	}
	public double getSd() {
		return sd;
	}
	public void setSd(double sd) {
		this.sd = sd;
	}
	public double getWeek52high() {
		return week52high;
	}
	public void setWeek52high(double week52high) {
		this.week52high = week52high;
	}
	public double getWeek52low() {
		return week52low;
	}
	public void setWeek52low(double week52low) {
		this.week52low = week52low;
	}
	public Date getDate52high() {
		return date52high;
	}
	public void setDate52high(Date date52high) {
		this.date52high = date52high;
	}
	public Date getDate52low() {
		return date52low;
	}
	public void setDate52low(Date date52low) {
		this.date52low = date52low;
	}

}
