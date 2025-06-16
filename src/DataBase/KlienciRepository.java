package DataBase;
import Class.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KlienciRepository {
    public int dodac(String imie, String nazwisko, String email, String telefon, java.util.Date data_urodzenia, String plec, int wzrost,
                            int waga, float rozmiar_buta, String numer_documentu) throws Exception {
        String sql = "INSERT INTO `Klienci`(`imie`, `nazwisko`, `email`, `telefon`, " +
                "`data_urodzenia`, `plec`, `wzrost`, `waga`, `rozmiar_buta`, `numer_documentu`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        java.sql.Date dusql = data_urodzenia != null ? new java.sql.Date(data_urodzenia.getTime()) : null;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, imie);
                pstmt.setString(2, nazwisko);
                pstmt.setString(3, email);
                pstmt.setString(4, telefon);
                pstmt.setDate(5, dusql);
                pstmt.setString(6, plec);
                pstmt.setInt(7, wzrost);
                pstmt.setInt(8, waga);
                pstmt.setFloat(9, rozmiar_buta);
                pstmt.setString(10, numer_documentu);

                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next())
                {
                    return rs.getInt(1);
                } else {
                    return 0;
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    public int dodac(Klient klient) throws Exception {
        return dodac(klient.imie, klient.nazwisko, klient.email, klient.telefon, klient.data_urodzenia, klient.plec, klient.wzrost, klient.waga, klient.rozmiar_buta, klient.numer_documentu);
    }

    public ArrayList<Klient> otrzymacWszyskrich() throws Exception {
        ArrayList<Klient> result = new ArrayList<>();
        String sql = "SELECT `id_klienta`, `imie`, `nazwisko`, `email`, `telefon`," +
                "`data_urodzenia`, `plec`, `wzrost`, `waga`, `rozmiar_buta`, `numer_documentu` FROM Klienci";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Date dusql = rs.getDate("data_urodzenia");
                java.util.Date du = dusql != null ? new java.util.Date(dusql.getTime()) : null;
                Klient klient = new Klient(
                rs.getInt("id_klienta"),
                rs.getString("imie"),
                rs.getString("nazwisko"),
                rs.getString("email"),
                rs.getString("telefon"),
                du,
                rs.getString("plec"),
                rs.getInt("wzrost"),
                rs.getInt("waga"),
                rs.getFloat("rozmiar_buta"),
                rs.getString("numer_documentu")
                );
                result.add(klient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return result;
    }
    public Klient otrzymacKlienta(String telefon) throws Exception {        ///
        String sql = "SELECT `id_klienta`, `imie`, `nazwisko`, `email`, `telefon`," +
                "`data_urodzenia`, `plec`, `wzrost`, `waga`, `rozmiar_buta`, `numer_documentu` FROM Klienci WHERE `telefon` = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, telefon);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date dusql = rs.getDate("data_urodzenia");
                java.util.Date du = dusql != null ? new java.util.Date(dusql.getTime()) : null;
                Klient klient = new Klient(
                        rs.getInt("id_klienta"),
                        rs.getString("imie"),
                        rs.getString("nazwisko"),
                        rs.getString("email"),
                        rs.getString("telefon"),
                        du,
                        rs.getString("plec"),
                        rs.getInt("wzrost"),
                        rs.getInt("waga"),
                        rs.getFloat("rozmiar_buta"),
                        rs.getString("numer_documentu")
                );
                return klient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        throw new Exception("Klient nie odnaleźony");
    }

    public void usunacKlienta(String telefon) throws Exception{
        String sql = "DELETE FROM `klienci` WHERE telefon = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, telefon);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Klient nie odnaleźony\n"+e.getMessage());
        }

    }

    public void edit(Klient klient) throws Exception {
        String sql = "UPDATE `klienci` " +
                "SET `imie`=?,`nazwisko`=?,`email`=?," +
                "`telefon`=?, `data_urodzenia`=?,`plec`=?,`wzrost`=?," +
                "`waga`=?,`rozmiar_buta`=?,`numer_documentu`=? WHERE `id_klienta`=?";

        java.sql.Date dusql = klient.data_urodzenia != null ? new java.sql.Date(klient.data_urodzenia.getTime()) : null;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, klient.imie);
            pstmt.setString(2, klient.nazwisko);
            pstmt.setString(3, klient.email);
            pstmt.setString(4, klient.telefon);
            pstmt.setDate(5, dusql);
            pstmt.setString(6, klient.plec);
            pstmt.setInt(7, klient.wzrost);
            pstmt.setInt(8, klient.waga);
            pstmt.setFloat(9, klient.rozmiar_buta);
            pstmt.setString(10, klient.numer_documentu);
            pstmt.setInt(11, klient.id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }


}

