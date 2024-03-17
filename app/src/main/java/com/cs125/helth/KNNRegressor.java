package com.cs125.helth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KNNRegressor {

    private List<DataPoint> trainingData;
    private int k;

    public KNNRegressor(List<DataPoint> trainingData, int k) {
        this.trainingData = trainingData;
        this.k = k;
    }

    public double predict(double heartRate) {
        // Find the k nearest neighbors
        List<DataPoint> nearestNeighbors = findNearestNeighbors(heartRate);

        // Predict distance based on the average of the distances of the k nearest neighbors
        double sum = 0.0;
        for (DataPoint neighbor : nearestNeighbors) {
            sum += neighbor.getValue();
        }

        return sum / nearestNeighbors.size();
    }

    private List<DataPoint> findNearestNeighbors(double heartRate) {
        // Calculate distances from the given heart rate to all data points
        List<DataPoint> distances = new ArrayList<>();
        for (DataPoint dataPoint : trainingData) {
            double distance = Math.abs(dataPoint.getHeartRate() - heartRate);
            distances.add(new DataPoint(distance, dataPoint.getValue()));
        }

        // Sort the distances list
        Collections.sort(distances, Comparator.comparingDouble(DataPoint::getHeartRate));

        // Select the k nearest neighbors
        return distances.subList(0, k);
    }

    public static void main(String[] args) {
        // Example usage
        List<DataPoint> trainingData = new ArrayList<>();
        trainingData.add(new DataPoint(60, 3.5));
        trainingData.add(new DataPoint(65, 4.0));
        trainingData.add(new DataPoint(70, 4.5));
        // Add more training data as needed

        KNNRegressor knnRegressor = new KNNRegressor(trainingData, 2);

        double heartRate = 78; // Example heart rate for prediction
        double predictedDistance = knnRegressor.predict(heartRate);
        System.out.println("Predicted Distance: " + predictedDistance);
    }
}

class DataPoint {
    private double heartRate;
    private double value;

    public DataPoint(double heartRate, double distance) {
        this.heartRate = heartRate;
        this.value = distance;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public double getValue() {
        return value;
    }
}
