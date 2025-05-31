package db;
import java.sql.*;

public class dbhandler {
    //ConnectToDB
    private static final String url = "jdbc:mysql://localhost:3306/parkingsys";
    private static final String user = "root";
    private static final String password = "255.255.255.0Fa";

    //InsertIdentity
    public int insertIdentity(String nama, String nim, byte[] embed) {
        int generatedId = -1;
        String query = "INSERT INTO identity (nama, nim, face) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nama);
            stmt.setString(2, nim);
            stmt.setBytes(3, embed);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    //InsertVehicle
    public void insertVehicle(int identityId, String tipe, String jenis, String warna, String plat) {
        String query = "INSERT INTO vehicle (identity_id, tipe_kendaraan, jenis_kendaraan, warna_kendaraan, plat_nomor) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, identityId);
            stmt.setString(2, tipe);
            stmt.setString(3, jenis);
            stmt.setString(4, warna);
            stmt.setString(5, plat);
            stmt.executeUpdate();
            System.out.println("Data kendaraan berhasil dimasukkan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //GetIdentity
    public int getIdentityIdByNim(String nim) {
        String query = "SELECT id FROM identity WHERE nim = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String showID (String name, String nim) {
        String result = "Data Tidak Ditemukan";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT nama, nim FROM identity WHERE nim = ? or nama = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nim);
            stmt.setString(2, name);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama");
                int nimResult = rs.getInt("nim");
                result = "Terbaca! " + nama + " - " + nimResult;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            result = "Terjadi kesalahan saat mengakses database.";
        }
        return result;
    }

    public String showVehicle (String nama, String nopol) {
        String result = "Data Tidak Ditemukan";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT jenis_kendaraan, plat_nomor FROM vehicle WHERE plat_nomor = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String jenisKendaraan = rs.getString("jenis_kendaraan");
                String platNomor = rs.getString("plat_nomor");
                result = "Terbaca! " + jenisKendaraan + " - " + platNomor;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            result = "Terjadi kesalahan saat mengakses database.";
        }
        return result;
    }

    public String delID (String name, String nim) {
        String result = "Data Tidak Ditemukan";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String selSQL = "SELECT nama, nim FROM identity WHERE nim = ? or nama = ?";
            PreparedStatement stmtA = conn.prepareStatement(selSQL);
            stmtA.setString(1, name);
            stmtA.setString(2, nim);
            ResultSet rs = stmtA.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama");
                int nimResult = rs.getInt("nim");
                result = "Terhapus! " + nama + " - " + nimResult;

                String delSQL = "DELETE FROM identity WHERE nim = ? or nama = ?";
                PreparedStatement stmtB = conn.prepareStatement(delSQL);
                stmtB.setString(1, nim);
                stmtB.setString(2, name);
                stmtB.executeUpdate();
                stmtB.close();
            }
            rs.close();
            stmtA.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            result = "Terjadi kesalahan saat mengakses database.";
        }
        return result;
    }

    public String delVehicle (String name, String nopol) {
        String result = "Data Tidak Ditemukan";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String selSQL = "SELECT jenis_kendaraan, plat_nomor FROM vehicle WHERE plat_nomor = ?";
            PreparedStatement stmtA = conn.prepareStatement(selSQL);
            stmtA.setString(1, name);
            ResultSet rs = stmtA.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("jenis_kendaraan");
                String nopolResult = rs.getString("plat_nomor");
                result = "Terhapus! " + nama + " - " + nopolResult;

                String delSQL = "DELETE FROM vehicle WHERE jenis_kendaraan = ? or plat_nomor = ?";
                PreparedStatement stmtB = conn.prepareStatement(delSQL);
                stmtB.setString(1, name);
                stmtB.setString(2, nopol);
                stmtB.executeUpdate();
                stmtB.close();
            }
            rs.close();
            stmtA.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            result = "Terjadi kesalahan saat mengakses database.";
        }
        return result;
    }

    public static boolean AdminAccess (String username, String pass) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String SQL = "SELECT * FROM admin WHERE USERNAME = ? AND PASSWORD = ?";
            PreparedStatement stmtA = conn.prepareStatement(SQL);
            stmtA.setString(1, username);
            stmtA.setString(2, pass);
            ResultSet rs = stmtA.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int updateID (String nama, String nim, byte[] embed, String nimCondition) {
        int generatedId = -1;
        String query = "UPDATE identity SET nama = ?, nim = ?, face = ? WHERE nim = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nama);
            stmt.setString(2, nim);
            stmt.setBytes(3, embed);
            stmt.setString(4, nimCondition);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public String updateVehicle (String tipe, String jenis, String warna, String nopol,
                                 String nopolCondition) {
        String query = "UPDATE vehicle SET tipe_kendaraan = ?, jenis_kendaraan = ?, warna_kendaraan = ?," +
                "plat_nomor = ? WHERE plat_nomor = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tipe);
            stmt.setString(2, jenis);
            stmt.setString(3, warna);
            stmt.setString(4, nopol);
            stmt.setString(5, nopolCondition);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }
}
