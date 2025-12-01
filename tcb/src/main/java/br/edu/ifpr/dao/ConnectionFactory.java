package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection conn;

    public ConnectionFactory(){}

    public static Connection connect(){
        try {
            if(conn==null){
                //jdbc:gdbd://ip do servidor do BD:porta/database
                String url = "jdbc:mysql://localhost:3306/TCB_lima_kortz";
                String user= "casa";
                String password="belinha";
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("conectado ao banco com sucesso!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;

    }

}
