package stockmarkethistoryapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TickerDetailsAPI {

		public static String getTickerDetails(String ticker){
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.polygon.io/v3/reference/tickers/"+ticker+"?apiKey=tEboLkojLxgaz2hAj83wTQZIG1te0iLT"))
				
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