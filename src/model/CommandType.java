package model;

public enum CommandType {
    LOAD("load"),
    SAVE("save"),
    PRINT("print"),
    EXIT("exit");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // string -> enum; throws on unknown input, matching CsvLoader's style
    public static CommandType fromString(String s) {
        for (CommandType c : values())
            if (c.name.equalsIgnoreCase(s.trim()))
                return c;
        throw new IllegalArgumentException("Unknown command: " + s);
    }
}
