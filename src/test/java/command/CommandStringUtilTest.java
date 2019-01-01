package command;

import com.hansque.commands.CommandStringUtil;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CommandStringUtilTest {

    @Test
    void startWithPrefixTest() {
        assertTrue(CommandStringUtil.startsWithPrefix("abcde", "ab"));
        assertFalse(CommandStringUtil.startsWithPrefix("abcde", "bc"));

        // Empty case, must return true
        assertTrue(CommandStringUtil.startsWithPrefix("abcde", ""));

        // Prefix is too long
        assertFalse(CommandStringUtil.startsWithPrefix("abc", "abcde"));
    }

    @Test
    void stripePrefixFromStringTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> CommandStringUtil.stripPrefixFromString("abcde", "bc")
        );
        assertEquals("cde", CommandStringUtil.stripPrefixFromString("abcde", "ab"));
        assertEquals("abcde", CommandStringUtil.stripPrefixFromString("abcde", ""));
    }

    @Test
    void convertAliasTest() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("alias", "mappedAlias");

        assertEquals("mappedAlias", CommandStringUtil.convertAlias("alias", aliases));
        assertEquals("abcde", CommandStringUtil.convertAlias("abcde", aliases));
    }

    @Test
    void getModuleFromCommandStringTest() {
        assertEquals("weather", CommandStringUtil.getModuleFromCommandString("weather:run arg1 arg2"));
        assertEquals("", CommandStringUtil.getModuleFromCommandString(":run arg1 arg2"));
        assertEquals("weather", CommandStringUtil.getModuleFromCommandString("weather: arg1 arg2"));
        assertThrows(IllegalArgumentException.class, () -> CommandStringUtil.getModuleFromCommandString("weather run arg1"));
    }

    @Test
    void getTriggerFromCommandStringTest() {
        assertEquals("run", CommandStringUtil.getTriggerFromCommandString("weather:run arg1 arg2"));
        assertEquals("", CommandStringUtil.getTriggerFromCommandString("weather: arg1 arg2"));
        assertEquals("run", CommandStringUtil.getTriggerFromCommandString(":run arg1 arg2"));
    }

    @Test
    void getArgumentsFromCommandStringTest() {
        List<String> expected = new ArrayList<>(
                Arrays.asList(
                        "arg1",
                        "arg2"
                )
        );
        assertEquals(expected, CommandStringUtil.getArgumentsFromCommandString("module:trigger arg1 arg2"));
    }
}
