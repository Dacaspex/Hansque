package com.hansque.modules.weather;

public class Temperature {

    /**
     * Converts kelvin to celcius.
     *
     * @param kelvin Temperature in kelvin
     * @return Temperature in celcius
     */
    public static double kelvinToCelcius(double kelvin) {
        return kelvin - 273.15;
    }
}
