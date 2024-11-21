package Calculation;

import java.util.Queue;

public class ExponentialMovingAverage {

    public static Double calculateRecentEMA(double price,int periodema,double previousEma) {
        int period = periodema; // Fixed period for EMA


        double k = 2.0 / (period + 1); // Smoothing factor


        // Calculate EMA for each price in the queue
       double thisEma = (price - previousEma) * k + previousEma;

        return thisEma; // Return the most recent EMA value
    }
}
