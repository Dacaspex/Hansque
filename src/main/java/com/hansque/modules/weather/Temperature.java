package com.hansque.modules.weather;

public class Temperature {

    /**
     * Temperature in kelvin
     */
    private final double kelvin;

    /**
     * @param kelvin Temperature in kelvin
     */
    public Temperature(double kelvin) {
        this.kelvin = kelvin;
    }

    public double kelvin() {
        return kelvin;
    }

    public double celsius() {
        return kelvinToCelcius(kelvin);
    }

    /**
     * Converts kelvin to celsius.
     *
     * @param kelvin Temperature in kelvin
     * @return Temperature in celsius
     */
    public static double kelvinToCelcius(double kelvin) {
        if (kelvin < 0) {
            throw new IllegalArgumentException("Temperature in kelvin cannot be negative");
        }

        return kelvin - 273.15;
    }
}
