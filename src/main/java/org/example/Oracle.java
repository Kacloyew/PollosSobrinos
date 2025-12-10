package org.example;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Oracle {

    public static void crearTablas () {

        Connection conexion = conectar();
        ScriptRunner scriptRunner = new ScriptRunner(conexion);

        try {

            Reader rutaTable = new FileReader("././Script/creationTablesOR.sql");
            Reader rutaInsert = new FileReader("././Script/insertDataTablesOR.sql");
            scriptRunner.setAutoCommit(false);
            scriptRunner.runScript(rutaTable);
            scriptRunner.runScript(rutaInsert);

            System.out.println("Script ejecutado");

            // "Objects.equireNonNull" simplemente permite que la conexion no pueda ser nula
            desconectar(conexion);

        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);

        }

    }

    public static Connection conectar() {
        System.out.println("=====================");
        System.out.println("Conectando con Oracle");
        System.out.println();

         try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", "system", "1234");
            return conexion;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void desconectar(Connection conexion) {

        try {

            System.out.println("=====================");
            System.out.println("Desconectando de Oracle");
            conexion.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

}
