package com.hansque.services.weather;

public class Weather {

    /**
     * Description of the weather
     */
    private final String description;
    /**
     * Current temperature
     */
    private final Temperature temperature;
    /**
     * Largest possible deviation from current temperature
     */
    private final Temperature minTemperature;
    /**
     * Smallest possible deviation from current temperature
     */
    private final Temperature maxTemperature;
    /**
     * Atmospheric pressure in hPa
     */
    private final double pressure;
    /**
     * Humidity percentage
     */
    private final double humidity;
    /**
     * Wind component
     */
    private final Wind wind;
    /**
     * Percentage of clouds
     */
    private final double cloudiness;

    public Weather(
            String description,
            Temperature temperature,
            Temperature minTemperature,
            Temperature maxTemperature,
            double pressure,
            double humidity,
            Wind wind,
            double cloudiness
    ) {
        this.description = description;
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind = wind;
        this.cloudiness = cloudiness;
    }

    public String getDescription() {
        return description;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Temperature getMinTemperature() {
        return minTemperature;
    }

    public Temperature getMaxTemperature() {
        return maxTemperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public Wind getWind() {
        return wind;
    }

    public double getCloudiness() {
        return cloudiness;
    }
}
