package Class;

import java.util.Date;

public class Snowboard extends Sprzet{

    public Snowboard() {
    }

    public Snowboard(String nazwa, String model, String firma, float rozmiar, Date data_zakupu, float cena_zakupu, float cena_dzienna) {
        super(nazwa, model, firma, rozmiar, data_zakupu, cena_zakupu, cena_dzienna);
    }

    @Override
    public String toString() {
        return "Snowboard: " + model + ", " + firma +", "  + cena_dzienna;
    }
}
