package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

public class Main {

    Connection connection;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("db.properties"))) {
            Properties props = new Properties();
            props.load(reader);

            connection = DriverManager.getConnection(props.getProperty("url"), props);
            System.out.println("Success");
            PreparedStatement ps = connection.prepareStatement(
                    "select count(*)\n" +
                    "from invoiceline\n" +
                    "where InvoiceId = ?");
            ps.setInt(1, 37);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("count = " + rs.getInt(1));
            }
            rs.close();
            ps.close();
        } catch (IOException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
