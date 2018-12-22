import static org.junit.jupiter.api.Assertions.*;

import com.hansque.services.weather.Temperature;
import org.junit.jupiter.api.Test;

public class TemperatureTest {

    /**
     * Tests normal behaviour of conversion method
     */
    @Test
    void kelvinToCelsiusTest() {
        double kelvin = 290;
        double celsius = kelvin - 273.15;
        assertEquals(celsius, Temperature.kelvinToCelcius(kelvin));
    }

    @Test
    void temperatureGettersTest() {
        double kelvin = 290;
        double celsius = kelvin - 273.15;
        Temperature temperature = new Temperature(kelvin);

        assertEquals(kelvin, temperature.kelvin());
        assertEquals(celsius, temperature.celsius());
    }

    @Test
    void illegalArgumentConstructorTest() {
        assertThrows(IllegalArgumentException.class, () -> new Temperature(-1));
    }

    @Test
    void illegalArgumentKelvinToCelsiusTest() {
        assertThrows(IllegalArgumentException.class, () -> Temperature.kelvinToCelcius(-1));
    }
}
