package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection conn;

    public ConnectionFactory(){}

    public static Connection connect(){
        try {
            // AQUI ESTA A CORREÇÃO:
            // Verificamos se é nula OU se a conexão foi fechada anteriormente
            if(conn == null || conn.isClosed()){ 
                
                String url = "jdbc:mysql://localhost:3306/TCB_lima_kortz";
                String user= "root";
                String password="belinha"; // Certifique-se que a senha é essa mesmo
                
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Conectado ao banco com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}