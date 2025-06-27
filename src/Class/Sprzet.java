package Class;

import java.util.Date;

public abstract class Sprzet {
    public int id;
    public boolean isAvaibleForRent;
    public String nazwa;
    public String model;
    public String firma;
    public float rozmiar;
    public Date data_zakupu;
    public float cena_zakupu;
    public float cena_dzienna;

    public Sprzet() {
    }

    public Sprzet(String nazwa, String model, String firma, float rozmiar, Date data_zakupu, float cena_zakupu, float cena_dzienna) {
        this.nazwa = nazwa;
        this.model = model;
        this.firma = firma;
        this.rozmiar = rozmiar;
        this.data_zakupu = data_zakupu;
        this.cena_zakupu = cena_zakupu;
        this.cena_dzienna = cena_dzienna;
    }
}
