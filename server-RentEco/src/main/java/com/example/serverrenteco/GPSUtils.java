package com.example.serverrenteco;

import static java.lang.Math.*;

public  class GPSUtils  {
    private static final double EARTH_RADIUS = 6371; // Radius of the Earth in kilometers

    // Method to calculate distance between two GPS coordinates using Haversine formula
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);
        double a = sin(dLat / 2) * sin(dLat / 2) +
                cos(toRadians(lat1)) * cos(toRadians(lat2)) *
                        sin(dLon / 2) * sin(dLon / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    // Method to check if a GPS coordinate is within a specified radius of a center point
    public static boolean isWithinRadius(double centerLat, double centerLon, double targetLat, double targetLon, double radius) {
        double distance = calculateDistance(centerLat, centerLon, targetLat, targetLon);
        return distance <= radius;
    }
}
