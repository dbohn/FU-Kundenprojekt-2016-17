package de.fuberlin.kundenprojekt.friedrich;

import java.sql.*;

/**
 * @author davidbohn
 */
public class Dummy {
    private static Connection connect() throws SQLException {
        String url = "jdbc:postgresql://database/humhub?user=humhub&password=1234";
        return DriverManager.getConnection(url);
    }

    public static String doSomething() {
        try {
            Connection db = connect();

            Statement stmt = db.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            rs.next();

            String username = rs.getString("username");

            rs.close();
            stmt.close();

            db.close();

            return username;
        } catch (SQLException e) {
            return e.getMessage();
            //e.printStackTrace();
        }
    }
}
