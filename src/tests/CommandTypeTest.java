package tests;

import org.junit.jupiter.api.Test;
import model.CommandType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CommandTypeTest {

    @Test
    void fromString_load_returnsLoad() {
        assertEquals(CommandType.LOAD, CommandType.fromString("load"));
    }

    @Test
    void fromString_save_returnsSave() {
        assertEquals(CommandType.SAVE, CommandType.fromString("save"));
    }

    @Test
    void fromString_print_returnsPrint() {
        assertEquals(CommandType.PRINT, CommandType.fromString("print"));
    }

    @Test
    void fromString_exit_returnsExit() {
        assertEquals(CommandType.EXIT, CommandType.fromString("exit"));
    }
    @ParameterizedTest
    @ValueSource(strings = {"LOAD", "Load", "lOaD", "SAVE", "Save", "PRINT", "Print", "EXIT", "Exit"})
    void fromString_isCaseInsensitive(String input) {
        assertDoesNotThrow(() -> CommandType.fromString(input));
    }
}