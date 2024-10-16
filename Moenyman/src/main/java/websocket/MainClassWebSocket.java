package websocket;

import Persistence.StoringStockPricesIntra;

public class MainClassWebSocket {
    public static void main(String[] args){

        ConnectingWebSocketStocks.connect();
        StoringStockPricesIntra storingintra=new StoringStockPricesIntra();
        new Thread(storingintra).start();

    }
}
