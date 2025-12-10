package org.example;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class mySQL {

    public static void crearTablas () {

        Connection conexion = conectar();
        ScriptRunner scriptRunner = new ScriptRunner(conexion);

        try {

            Reader rutaTable = new FileReader("././Script/creationTablesSQL.sql");
            Reader rutaInsert = new FileReader("././Script/insertDataTablesSQL.sql");
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
        System.out.println("Conectando con mySQL");
        System.out.println();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/pollosSobrinos", "root", "");
            return conexion;

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
            return null;

        }

    }

    public static void desconectar(Connection conexion) {

        try {

            System.out.println("=====================");
            System.out.println("Desconectando de MySQL");
            conexion.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

}
