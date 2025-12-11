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

            // Reiniciamos las tablas
            Reader rutaTable = new FileReader("./Script/Oracle/creationTablesOR.sql");
            scriptRunner.runScript(rutaTable);

            // Y luego volvemos a meterles los INSERTs de inicio.
            Reader rutaInsert = new FileReader("./Script/Oracle/insertDataTablesOR.sql");
            scriptRunner.runScript(rutaInsert);

            System.out.println("Scripts ejecutados con éxito");

            conexion.commit();

            desconectar(conexion);

        } catch (FileNotFoundException | SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static Connection conectar() {

         try {

             System.out.println("=====================");
             System.out.println("Conectando con Oracle");
             System.out.println();

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
