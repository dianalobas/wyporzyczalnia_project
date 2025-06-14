package Class;

import java.util.Date;

public abstract class Sprzet {
    public String nazwa;
    public String model;
    public String firma;
    public float rozmiar;
    public Date data_zakupu;
    public float cena_zakupu;
    ///
    public String rodzaj;
    public String poziom;
    public int ilosc_dni;
    public float cena_dzienna = cena_zakupu/60;

    public Sprzet(String nazwa, String model, String firma, float rozmiar,
                  Date data_zakupu, float cena_zakupu, String rodzaj, String poziom, int ilosc_dni) {
        this.nazwa = nazwa;
        this.model = model;
        this.firma = firma;
        this.rozmiar = rozmiar;
        this.data_zakupu = data_zakupu;
        this.cena_zakupu = cena_zakupu;
        this.rodzaj = rodzaj;
        this.poziom = poziom;
        this.ilosc_dni = ilosc_dni;
    }

    public Sprzet(String rodzaj, String poziom, int ilosc_dni) {
        this.rodzaj = rodzaj;
        this.poziom = poziom;
        this.ilosc_dni = ilosc_dni;
    }
}
