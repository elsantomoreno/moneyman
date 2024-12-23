package websocket;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class PolygonWebSocketClient extends WebSocketClient {

    public PolygonWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to Polygon.io WebSocket");
        // Authenticate with your API key
        send("{\"action\":\"auth\",\"params\":\"tEboLkojLxgaz2hAj83wTQZIG1te0iLT\"}");
        // Subscribe to the trades channel for all stocks
        send("{\"action\":\"subscribe\",\"params\":\"A.*\"}");
        
    }

    @Override
    public void onMessage(String message) {
        //System.out.println(message);
    	ProcessMsg.process(message);

    
    	
    	
    

    }
    

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed with code " + code + " Reason: " + reason);
        
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
    

}

