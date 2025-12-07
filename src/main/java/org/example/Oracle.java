package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Oracle {

    public static Connection conectar() {
        System.out.println("=====================");
        System.out.println("Conectando con Oracle");
        System.out.println();

     try {

        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "system", "1234");
        return conexion;

    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        return null;
    }
}
}
