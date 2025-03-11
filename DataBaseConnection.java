package lostfound;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/lost_and_found";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void insertUser(String course, String name, String username, String email, String password) throws SQLException {
        String query = "INSERT INTO userinfo (COURSE, `Student Name`,userName, Gmail, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, course);   // COURSE
            pstmt.setString(2, name);     // `Student Name`
            pstmt.setString(3, username); // `userName Index`
            pstmt.setString(4, email);    // Gmail
            pstmt.setString(5, password); // password

            pstmt.executeUpdate();
            conn.close();
        }

    }

public static boolean loginUser(String username, String password) throws SQLException {
    String query = "SELECT password FROM userinfo WHERE userName = ?";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, username);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                String storedHash = rs.getString("password");
                return BCrypt.verifyer().verify(password.toCharArray(), storedHash).verified;
            } else {
                return false;
            }
        }
    }

}

}
