package command;

import com.hansque.commands.CommandStringUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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

        assertEquals("mappedAlias", CommandStringUtil.convertAlias("test", aliases));
        assertEquals("abcde", CommandStringUtil.convertAlias("abcde", aliases));
    }

    @Test
    void getModuleFromCommandStringTest() {
        // TODO
    }

    @Test
    void getTriggerFromCommandStringTest() {
        // TODO
    }

    @Test
    void getArgumentsFromCommandStringTest() {
        // TODO
    }
}
