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
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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

    public ArrayList<WypoSprzet> getAvailableSprzetByTypeId(int type_id) throws Exception {
        ArrayList<WypoSprzet> lista = new ArrayList<>();
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
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, type_id);
            pstmt.executeQuery();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                WypoSprzet sprzet = new WypoSprzet();
                sprzet.nazwa = rs.getString("typ_sprzetu");
                sprzet.model = rs.getString("model");
                sprzet.firma = rs.getString("firma");
                sprzet.rozmiar = rs.getFloat("rozmiar");
                lista.add(sprzet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return lista;
    }
}
