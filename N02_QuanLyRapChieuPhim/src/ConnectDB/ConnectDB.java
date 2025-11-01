package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    private ConnectDB() {
    }

    public static ConnectDB getInstance() {
        return instance;
    }

    public void connect() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QLRapChieuPhim;encrypt=false";
        String user = "sa";
        String password = "sapassword";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Kết nối CSDL thành công!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Không tìm thấy driver SQL Server!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối CSDL!");
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        if (con != null) {
            try {
                con.close();
                con = null;
                System.out.println("🔌 Đã ngắt kết nối CSDL.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        // Tự động kết nối nếu chưa có
        if (con == null) {
            getInstance().connect();
        }
        return con;
    }
}
