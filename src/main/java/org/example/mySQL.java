package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mySQL {

    public static Connection conectar() {
        System.out.println("Conectando con mySQL");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/pollosSobrinos", "root", "");
            return conexion;

        } catch (ClassNotFoundException | SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
