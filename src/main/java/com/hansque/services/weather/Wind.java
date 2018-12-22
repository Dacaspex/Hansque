package com.hansque.services.weather;

public class Wind {

    /**
     * Wind speed in meter/sec
     */
    private final double speed;
    /**
     * Wind direction in degrees
     */
    private final double degrees;

    public Wind(double speed, double degrees) {
        this.speed = speed;
        this.degrees = degrees;
    }

    /**
     * @return Wind speed in meter/sec
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return Wind direction in degrees
     */
    public double getDegrees() {
        return degrees;
    }
}
