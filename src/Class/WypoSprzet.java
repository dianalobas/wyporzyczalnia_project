package Class;

import java.util.Date;

public class WypoSprzet {
    public String nazwa;
    public String model;
    public String firma;
    public float rozmiar;
    public Date data_zakupu;
    public float cena_zakupu;

    public String rodzaj;
    public float cena;

    @Override
    public String toString() {
        return nazwa + ", " + model + ", " + firma + ", " + rozmiar;
    }
}
