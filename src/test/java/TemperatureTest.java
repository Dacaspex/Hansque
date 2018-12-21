import static org.junit.jupiter.api.Assertions.*;

import com.hansque.modules.weather.Temperature;
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
    void illegalArgumentKelvinToCelsiusTest() {
        assertThrows(IllegalArgumentException.class, () -> Temperature.kelvinToCelcius(-1));
    }

}
