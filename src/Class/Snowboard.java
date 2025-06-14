package Class;

import java.util.Date;

public class Snowboard extends Sprzet{

    public Snowboard(String nazwa, String model, String firma, float rozmiar, Date data_zakupu, float cena_zakupu, String rodzaj, String poziom, int ilosc_dni) {
        super(nazwa, model, firma, rozmiar, data_zakupu, cena_zakupu, rodzaj, poziom, ilosc_dni);
    }

    public Snowboard(String rodzaj, String poziom, int ilosc_dni) {
        super(rodzaj, poziom, ilosc_dni);
    }
}
