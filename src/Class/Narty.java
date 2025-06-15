package Class;

import java.util.Date;

public class Narty extends Sprzet{

    public Narty() {
    }

    public Narty(String nazwa, String model, String firma, float rozmiar, Date data_zakupu, float cena_zakupu, float cena_dzienna) {
        super(nazwa, model, firma, rozmiar, data_zakupu, cena_zakupu, cena_dzienna);
    }

    @Override
    public String toString() {
        return "Narty: " + model + ", " + firma +", "  + cena_dzienna;
    }
}
