package stockmarkethistoryapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StockpriceshistoryAPI {

		public static String getHistory(String stock,String beginn,String end){
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.polygon.io/v2/aggs/ticker/"+stock+"/range/1/day/"+beginn+"/"+end+"?adjusted=true&sort=asc&limit=5000&apiKey=tEboLkojLxgaz2hAj83wTQZIG1te0iLT"))
				
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response = null;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
	
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		return response.body();}
		
	}


