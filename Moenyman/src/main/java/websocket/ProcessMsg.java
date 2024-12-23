package websocket;

import java.sql.Date;
import java.sql.Time;
import java.time.*;

import org.json.JSONArray;
import org.json.JSONObject;

import Data.Datacollection;
import UserDefinedClasses.StockIntraDayPrices;


public class ProcessMsg{

	public static void process(String response) {

	       JSONArray jsonArray = new JSONArray(response);
	       

           // Extract the first object from the array
           JSONObject jsonObject = jsonArray.getJSONObject(0);

         // Extract data
         for (int i = 0; i < jsonArray.length(); i++) {
             JSONObject result = jsonArray.getJSONObject(i);
             long millisec=0;
             if(jsonArray.getJSONObject(i).has("sym")) {
              millisec=jsonArray.getJSONObject(i).getLong("e");}

             Instant instant = Instant.ofEpochMilli(millisec);
             ZoneId newYorkZoneId = ZoneId.of("America/New_York");
             LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, newYorkZoneId);
             LocalDate localDate = localDateTime.toLocalDate();
             LocalTime localTime = localDateTime.toLocalTime();

             String symbol=null;
             if(jsonArray.getJSONObject(i).has("sym")) {
             symbol = jsonArray.getJSONObject(i).getString("sym"); }
             
             double tickopen=0;
             if(jsonArray.getJSONObject(i).has("o")) {
              tickopen = jsonArray.getJSONObject(i).getDouble("o");} // Open
             
             double tickhigh=0;
             if(jsonArray.getJSONObject(i).has("h")) {
             tickhigh = jsonArray.getJSONObject(i).getDouble("h");} // High
             
             double ticklow=0;
             if(jsonArray.getJSONObject(i).has("l")) {
              ticklow = jsonArray.getJSONObject(i).getDouble("l");} // Low
             
             double tickclose=0;
             if(jsonArray.getJSONObject(i).has("c")) {
              tickclose = jsonArray.getJSONObject(i).getDouble("c");} // Close
             
             long tickvol=0;
             if(jsonArray.getJSONObject(i).has("v")) {
              tickvol=jsonArray.getJSONObject(i).getLong("v");}
             
             double tickavgvolprice=0;
             if(jsonArray.getJSONObject(i).has("vw")) {
             tickavgvolprice=jsonArray.getJSONObject(i).getDouble("vw");}
             
             long tickavgvolsize=0;
             if(jsonArray.getJSONObject(i).has("z")) {
              tickavgvolsize=jsonArray.getJSONObject(i).getLong("z");}
             
             double todayopen=0;
             if(jsonArray.getJSONObject(i).has("op")) {
              todayopen=jsonArray.getJSONObject(i).getDouble("op");}
             
             double todayavgvolprice=0;
             if(jsonArray.getJSONObject(i).has("a")) {
              todayavgvolprice=jsonArray.getJSONObject(i).getDouble("a");}
             
             long todayaccvol=0;
             if(jsonArray.getJSONObject(i).has("av")) {
             todayaccvol=jsonArray.getJSONObject(i).getLong("av");}
            
   
             Data.Datacollection.INTRADAY_ARRAY.offer(new StockIntraDayPrices(localDate,localTime, symbol,tickopen,
            		tickhigh, ticklow, tickclose,tickvol, tickavgvolprice,
			        tickavgvolsize,todayopen ,todayavgvolprice , todayaccvol));
           
           // System.out.println(localTime+" Date: "  + ", Open: " + tickopen + " Counter: "+Datacollection.COUNTER++);
         }
		
	}
	}

