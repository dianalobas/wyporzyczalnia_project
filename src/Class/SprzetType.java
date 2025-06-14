package Class;

public class SprzetType {
    public int id;
    public  String nazwa;

    public SprzetType(int id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public SprzetType() {
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
