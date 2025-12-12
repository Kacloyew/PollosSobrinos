package org.example;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

public class Oracle {

    public static void crearTablas (Connection conexion) {

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

        } catch (FileNotFoundException | SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void listarTablasOracle (Connection conexion, int posicion) {

        switch (posicion) {

            case 1:

                imprimirTabla(conexion, "Tiendas", "SELECT * FROM Tiendas");
                break;


            case 2:

                imprimirTabla(conexion, "Empleados", "SELECT * FROM Empleados");
                break;

            case 3:

                imprimirTabla(conexion, "Clientes", "SELECT * FROM Clientes");
                break;

            case 4:

                imprimirTabla(conexion, "Proveedores", "SELECT * FROM Proveedores");
                break;


            case 5:

                imprimirTabla(conexion, "Pedidos", "SELECT * FROM Pedidos");
                break;


            case 6:

                imprimirTabla(conexion, "Productos", "SELECT * FROM Productos");
                break;

            case 7:

                System.out.println("**** Retrocediendo... ****");
                break;

            default:
                System.out.println("Error, selecciona un número válido");

        }

    }

    private static void imprimirTabla(Connection conexion, String nombreTabla, String consultaSQL) {

        System.out.printf("**** Tabla %s ****%n", nombreTabla);

        try {

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(consultaSQL);

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            // Imprimir nombres de columnas
            for (int i = 1; i <= columnas; i++) {
                System.out.printf("%-30s", meta.getColumnName(i));
            }
            System.out.println();

            // Imprimir separador
            for (int i = 1; i <= columnas; i++) {
                System.out.print("--------------------");
            }
            System.out.println();

            // Imprimir datos
            while (rs.next()) {

                for (int i = 1; i <= columnas; i++) {
                    System.out.printf("%-30s", rs.getString(i));
                }
                System.out.println();

            }

        } catch (SQLException e) {

        }

    }

    public static void nuevoProducto(Connection conexion, String nombreTabla, String consultaSQL) {


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
