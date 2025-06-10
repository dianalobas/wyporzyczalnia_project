package Class;

import java.util.Date;

public class Klient {
    public int id;
    public String imie;
    public String nazwisko;
    public String email;
    public Date data_urodzenia;
    public String telefon;
    public String plec;
    public int wzrost;
    public int waga;
    public float rozmiar_buta;
    public String numer_documentu;

    public Klient(int id, String imie, String nazwisko, String email, String telefon,
                  Date data_urodzenia, String plec, int wzrost, int waga, float rozmiar_buta, String numer_documentu) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.data_urodzenia = data_urodzenia;
        this.telefon = telefon;
        this.plec = plec;
        this.wzrost = wzrost;
        this.waga = waga;
        this.rozmiar_buta = rozmiar_buta;
        this.numer_documentu = numer_documentu;
    }
    /// NOT NULL konstructor
    public Klient(String imie, String nazwisko, String email, String telefon, String numer_documentu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.telefon = telefon;
        this.numer_documentu = numer_documentu;
    }

    public Klient() {}
}
