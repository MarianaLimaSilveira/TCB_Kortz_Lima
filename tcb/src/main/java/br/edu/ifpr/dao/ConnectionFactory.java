package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection conn;

    public ConnectionFactory() {
    }

    public static Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {

                String url = "jdbc:mysql://127.0.0.1:3306/tcb_lima_kortz";
                String user = "root";
                String password = "belinha";

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Conectado ao banco com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
