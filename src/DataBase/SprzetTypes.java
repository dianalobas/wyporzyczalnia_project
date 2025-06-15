package DataBase;

public enum SprzetTypes {
    UNKNOWN(0), NARTY(1), SNOWBOARD(2), KIJKI(3), KASK(4), BUTY(5);

    private final int id;

    SprzetTypes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SprzetTypes fromId(int id) {
        for (SprzetTypes type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
