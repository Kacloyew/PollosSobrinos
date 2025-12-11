package org.example;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

public class mySQL {

    public static void crearTablas () {

        Connection conexion = conectar();
        ScriptRunner scriptRunner = new ScriptRunner(conexion);

        try {

            Reader rutaTable = new FileReader("./Script/mySQL/creationTablesSQL.sql");
            scriptRunner.runScript(rutaTable);

            Reader rutaInsert = new FileReader("./Script/mySQL/insertDataTablesSQL.sql");
            scriptRunner.runScript(rutaInsert);

            System.out.println("Script ejecutado");

        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);

        }

    }

    public static void listarTablasMySQL (Connection conexion, int posicion) {

        try {

            switch (posicion) {

                case 1:

                    System.out.println("**** Tabla Tiendas ****");
                    String sqlTienda = "SELECT * FROM Tiendas;";
                    Statement stmtT = conexion.createStatement();
                    ResultSet rsT = stmtT.executeQuery(sqlTienda);

                    ResultSetMetaData metaT = rsT.getMetaData();
                    int columnasT = metaT.getColumnCount();

                    for (int i = 1; i <= columnasT; i++) {
                        System.out.printf("%-20s", metaT.getColumnName(i));
                    }
                    System.out.println();

                    for (int i = 1; i <= columnasT; i++) {
                        System.out.print("--------------------");
                    }
                    System.out.println();

                    while (rsT.next()) {
                        for (int i = 1; i <= columnasT; i++) System.out.printf("%-20s", rsT.getString(i));
                        System.out.println();
                    }

                    stmtT.close();
                    rsT.close();
                    break;


                case 2:

                    System.out.println("**** Tabla Empleados ****");
                    String sqlEmpleados = "SELECT * FROM Empleados;";
                    Statement stmtE = conexion.createStatement();
                    ResultSet rsE = stmtE.executeQuery(sqlEmpleados);

                    ResultSetMetaData metaE = rsE.getMetaData();
                    int columnasE = metaE.getColumnCount();

                    for (int i = 1; i <= columnasE; i++) {
                        System.out.printf("%-20s", metaE.getColumnName(i));
                    }
                    System.out.println();

                    for (int i = 1; i <= columnasE; i++) {
                        System.out.print("--------------------");
                    }
                    System.out.println();

                    while (rsE.next()) {
                        for (int i = 1; i <= columnasE; i++) System.out.printf("%-20s", rsE.getString(i));
                        System.out.println();
                    }

                    stmtE.close();
                    rsE.close();
                    break;


                case 3:

                    System.out.println("**** Tabla Clientes ****");
                    String sqlClientes = "SELECT * FROM Clientes;";
                    Statement stmtC = conexion.createStatement();
                    ResultSet rsC = stmtC.executeQuery(sqlClientes);

                    ResultSetMetaData metaC = rsC.getMetaData();
                    int columnasC = metaC.getColumnCount();

                    for (int i = 1; i <= columnasC; i++) {
                        System.out.printf("%-20s", metaC.getColumnName(i));
                    }
                    System.out.println();

                    for (int i = 1; i <= columnasC; i++) {
                        System.out.print("--------------------");
                    }
                    System.out.println();

                    while (rsC.next()) {
                        for (int i = 1; i <= columnasC; i++) System.out.printf("%-20s", rsC.getString(i));
                        System.out.println();
                    }

                    stmtC.close();
                    rsC.close();
                    break;


                case 4:

                    System.out.println("**** Tabla Proveedores ****");
                    String sqlProveedores = "SELECT * FROM Proveedores;";
                    Statement stmtP = conexion.createStatement();
                    ResultSet rsP = stmtP.executeQuery(sqlProveedores);

                    ResultSetMetaData metaP = rsP.getMetaData();
                    int columnasP = metaP.getColumnCount();

                    for (int i = 1; i <= columnasP; i++) {
                        System.out.printf("%-20s", metaP.getColumnName(i));
                    }
                    System.out.println();

                    for (int i = 1; i <= columnasP; i++) {
                        System.out.print("--------------------");
                    }
                    System.out.println();

                    while (rsP.next()) {
                        for (int i = 1; i <= columnasP; i++) System.out.printf("%-20s", rsP.getString(i));
                        System.out.println();
                    }

                    stmtP.close();
                    rsP.close();
                    break;


                case 5:

                    System.out.println("**** Tabla Pedidos ****");
                    String sqlPedidos = "SELECT * FROM Pedidos;";
                    Statement stmtPe = conexion.createStatement();
                    ResultSet rsPe = stmtPe.executeQuery(sqlPedidos);

                    ResultSetMetaData metaPe = rsPe.getMetaData();
                    int columnasPe = metaPe.getColumnCount();

                    for (int i = 1; i <= columnasPe; i++) {
                        System.out.printf("%-20s", metaPe.getColumnName(i));
                    }
                    System.out.println();

                    for (int i = 1; i <= columnasPe; i++) {
                        System.out.print("--------------------");
                    }
                    System.out.println();

                    while (rsPe.next()) {
                        for (int i = 1; i <= columnasPe; i++) System.out.printf("%-20s", rsPe.getString(i));
                        System.out.println();
                    }

                    stmtPe.close();
                    rsPe.close();
                    break;


                case 6:

                    System.out.println("**** Tabla Productos ****");
                    String sqlProductos = "SELECT * FROM Productos;";
                    Statement stmtPr = conexion.createStatement();
                    ResultSet rsPr = stmtPr.executeQuery(sqlProductos);

                    ResultSetMetaData metaPr = rsPr.getMetaData();
                    int columnasPr = metaPr.getColumnCount();

                    for (int i = 1; i <= columnasPr; i++) {
                        System.out.printf("%-20s", metaPr.getColumnName(i));
                    }
                    System.out.println();

                    for (int i = 1; i <= columnasPr; i++) {
                        System.out.print("--------------------");
                    }
                    System.out.println();

                    while (rsPr.next()) {
                        for (int i = 1; i <= columnasPr; i++) System.out.printf("%-20s", rsPr.getString(i));
                        System.out.println();
                    }

                    stmtPr.close();
                    rsPr.close();
                    break;

                case 7:

                    System.out.println("**** Retrocediendo... ****");
                    break;

                default:
                    System.out.println("Error, selecciona un número válido");

            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static Connection conectar() {

        try {

            System.out.println("=====================");
            System.out.println("Conectando con mySQL");
            System.out.println();

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
