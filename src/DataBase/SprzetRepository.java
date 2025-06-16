package DataBase;
import Class.*;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class SprzetRepository{
    public ArrayList<SprzetType> getAllRodzaje() throws Exception{
        ArrayList<SprzetType> lista = new ArrayList<>();
        String sql = "SELECT id_sprzet_typa, nazwa FROM SprzetType;";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeQuery();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new SprzetType(rs.getInt("id_sprzet_typa"), rs.getString("nazwa")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return  lista;
    }

    public ArrayList<Sprzet> getAvailableSprzetByTypeId(int type_id) throws Exception {
        ArrayList<Sprzet> lista = new ArrayList<>();
        String sql = "SELECT sp.*, st.nazwa AS typ_sprzetu, wp.* " +
                "FROM sprzet AS sp " +
                "LEFT JOIN ( " +
                "    SELECT w.* " +
                "    FROM wypozyczenie w" +
                "    JOIN ( " +
                "        SELECT id_sprzetu, MAX(data_operacji) AS max_data " +
                "        FROM wypozyczenie " +
                "        GROUP BY id_sprzetu " +
                "    ) AS latest " +
                "    ON w.id_sprzetu = latest.id_sprzetu  " +
                "    AND w.data_operacji = latest.max_data " +
                ") AS wp ON sp.id_sprzetu = wp.id_sprzetu " +
                "JOIN SprzetType st ON sp.type_id = st.id_sprzet_typa " +
                "WHERE (wp.typ IS NULL OR wp.typ = 1) AND sp.type_id = ?;";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, type_id);
            pstmt.executeQuery();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Sprzet sprzet;
                switch (SprzetTypes.fromId(rs.getInt("type_id"))){
                    case NARTY:
                        sprzet = new Narty();
                        break;
                    case SNOWBOARD:
                        sprzet = new Snowboard();
                        break;
                    case KIJKI:
                        sprzet = new Kijki();
                        break;
                    case KASK:
                        sprzet = new Kask();
                        break;
                    case BUTY:
                        sprzet = new Buty();
                        break;

                    case UNKNOWN:
                    default:
                        sprzet = new Inny();
                }
                sprzet.id = rs.getInt("id_sprzetu");
                sprzet.nazwa = rs.getString("typ_sprzetu");
                sprzet.model = rs.getString("model");
                sprzet.firma = rs.getString("firma");
                sprzet.rozmiar = rs.getFloat("rozmiar");
                sprzet.cena_dzienna = rs.getFloat("cena_dzienna");
                lista.add(sprzet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return lista;
    }

    public void wynajemZwrotSprzetu(Klient klient, Sprzet sprzet, boolean zwrot) throws Exception{
        String sql = "INSERT INTO `wypozyczenie`(`id_klienta`, `id_sprzetu`, `typ`) VALUES (?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, klient.id);
            pstmt.setInt(2, sprzet.id);
            pstmt.setInt(3, zwrot ? 1 : 0);
            int res = pstmt.executeUpdate();
            if (res != 1)
                throw new Exception("Błąd w wykonaniu metody wynajemZwrotSprzetu()");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    public ArrayList<Sprzet> otrzymacWyporzyczonySprzetKlienta(int id_klienta) throws Exception {
        ArrayList<Sprzet> lista = new ArrayList<>();
        String sql = "SELECT sp.*, st.nazwa AS typ_sprzetu, wp.* " +
                "FROM sprzet AS sp " +
                "LEFT JOIN ( " +
                "    SELECT w.* " +
                "    FROM wypozyczenie w" +
                "    JOIN ( " +
                "        SELECT id_sprzetu, MAX(data_operacji) AS max_data " +
                "        FROM wypozyczenie " +
                "        GROUP BY id_sprzetu " +
                "    ) AS latest " +
                "    ON w.id_sprzetu = latest.id_sprzetu  " +
                "    AND w.data_operacji = latest.max_data " +
                ") AS wp ON sp.id_sprzetu = wp.id_sprzetu " +
                "JOIN SprzetType st ON sp.type_id = st.id_sprzet_typa " +
                "WHERE wp.typ = 0 AND wp.id_klienta = ?;";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_klienta);
            pstmt.executeQuery();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Sprzet sprzet;
                switch (SprzetTypes.fromId(rs.getInt("type_id"))){
                    case NARTY:
                        sprzet = new Narty();
                        break;
                    case SNOWBOARD:
                        sprzet = new Snowboard();
                        break;
                    case KIJKI:
                        sprzet = new Kijki();
                        break;
                    case KASK:
                        sprzet = new Kask();
                        break;
                    case BUTY:
                        sprzet = new Buty();
                        break;

                    case UNKNOWN:
                    default:
                        sprzet = new Inny();
                }
                sprzet.id = rs.getInt("id_sprzetu");
                sprzet.nazwa = rs.getString("typ_sprzetu");
                sprzet.model = rs.getString("model");
                sprzet.firma = rs.getString("firma");
                sprzet.rozmiar = rs.getFloat("rozmiar");
                sprzet.cena_dzienna = rs.getFloat("cena_dzienna");
                lista.add(sprzet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return lista;
    }
    public ArrayList<Sprzet> getAvailableAllSprzety() throws Exception {
        ArrayList<Sprzet> lista = new ArrayList<>();
        String sql = "SELECT sp.*, st.nazwa AS typ_sprzetu, wp.* " +
                "FROM sprzet AS sp " +
                "LEFT JOIN ( " +
                "    SELECT w.* " +
                "    FROM wypozyczenie w" +
                "    JOIN ( " +
                "        SELECT id_sprzetu, MAX(data_operacji) AS max_data " +
                "        FROM wypozyczenie " +
                "        GROUP BY id_sprzetu " +
                "    ) AS latest " +
                "    ON w.id_sprzetu = latest.id_sprzetu  " +
                "    AND w.data_operacji = latest.max_data " +
                ") AS wp ON sp.id_sprzetu = wp.id_sprzetu " +
                "JOIN SprzetType st ON sp.type_id = st.id_sprzet_typa;";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeQuery();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Sprzet sprzet;
                switch (SprzetTypes.fromId(rs.getInt("type_id"))){
                    case NARTY:
                        sprzet = new Narty();
                        break;
                    case SNOWBOARD:
                        sprzet = new Snowboard();
                        break;
                    case KIJKI:
                        sprzet = new Kijki();
                        break;
                    case KASK:
                        sprzet = new Kask();
                        break;
                    case BUTY:
                        sprzet = new Buty();
                        break;

                    case UNKNOWN:
                    default:
                        sprzet = new Inny();
                }
                sprzet.id = rs.getInt("id_sprzetu");
                int type = rs.getInt("typ");
                sprzet.isAvaibleForRent = type == 1 || rs.wasNull();
                sprzet.nazwa = rs.getString("typ_sprzetu");
                sprzet.model = rs.getString("model");
                sprzet.firma = rs.getString("firma");
                sprzet.rozmiar = rs.getFloat("rozmiar");
                sprzet.cena_dzienna = rs.getFloat("cena_dzienna");
                lista.add(sprzet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return lista;
    }

}
