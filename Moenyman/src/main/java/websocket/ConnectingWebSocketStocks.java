package websocket;

import java.net.URI;
import java.net.URISyntaxException;

public class ConnectingWebSocketStocks {
	public static void connect() {
		 String webSocketUrl = "wss://delayed.polygon.io/stocks";

	        try {
	            URI uri = new URI(webSocketUrl);
	            PolygonWebSocketClient client = new PolygonWebSocketClient(uri);
	            client.connect();
	        } catch (URISyntaxException e) {
	            e.printStackTrace();
	        }
	        
	        
	}
	

}