package db;
import java.sql.*;
import ui.EntryGate;

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

    public int findIdentityIdByPlat(String plat, dbhandler db) {
        int identityId = -1;
        String query = "SELECT identity_id FROM vehicle WHERE plat_nomor = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingsys", "root", "255.255.255.0Fa");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, plat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                identityId = rs.getInt("identity_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return identityId;
    }

    public int findVehicleIDbyPLAT(String plat) {
        int vehicleId = -1;
        String query = "SELECT id FROM vehicle WHERE plat_nomor = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, plat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vehicleId = rs.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vehicleId;
    }

    public int findMatchingFaceId(float[] scannedEmbedding, dbhandler db) {
        int identityId = -1;
        float bestSimilarity = 0.0f;
        float threshold = 0.7f; // Threshold cocok, bisa disesuaikan

        String query = "SELECT id, face FROM identity";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingsys", "root", "255.255.255.0Fa");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                byte[] faceBytes = rs.getBytes("face");
                if (faceBytes == null) continue;
                EntryGate EG = new EntryGate();
                float[] dbEmbedding = EG.byteArrayToFloatArray(faceBytes);
                float sim = cosineSimilarity(scannedEmbedding, dbEmbedding);
                if (sim > bestSimilarity && sim >= threshold) {
                    bestSimilarity = sim;
                    identityId = id;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return identityId;
    }

    public void entry_log (Timestamp entrylog, int id) {
        String query = "INSERT INTO parking_log (jam_masuk, vehicle_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, entrylog);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit_log (Timestamp exitlog, int id) {
        String query = "UPDATE parking_log SET jam_keluar = ? WHERE vehicle_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, exitlog);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String log_exitWindow (int id) {
        String query = "SELECT jam_masuk, jam_keluar FROM parking_log WHERE vehicle_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String jam_masuk = rs.getString("jam_masuk");
                String jam_keluar = rs.getString("jam_keluar");
                return "Jam Masuk : " + jam_masuk + "\nJam Keluar : " + jam_keluar;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "ERROR : " + e.getMessage();
        }
        return null;
    }

    public float cosineSimilarity(float[] a, float[] b) {
        if (a.length != b.length) return 0f;
        float dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return (float) (dot / (Math.sqrt(normA) * Math.sqrt(normB)));
    }
}