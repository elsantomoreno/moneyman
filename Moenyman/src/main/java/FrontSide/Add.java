package FrontSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Data.Datacollection;
import Persistence.GetPriceHistory;
import UserDefinedClasses.StockPrices;


	
	@WebServlet("/add")
	public class Add extends HttpServlet {
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        StringBuilder jsonBuilder = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            jsonBuilder.append(line);
	        }
	        String json = jsonBuilder.toString();
	        
	        response.setContentType("application/json");
	        JSONObject al=new JSONObject(json);
	    	String stock=al.getString("ticker");
	    	 StockPrices stockprices= GetStockHistory.getHistory("HUGE");
	 
	//String stockinfojson=  Datacollection.getStockPricesInfoJson(stockprices);
	   
	      
	         
	         // Set response content type to application/json
	         response.setContentType("application/json");
	         
	         // Send the JSON response back to the client
	       //  response.getWriter().write(stockinfojson);	
	
	      
	    }
	

}
