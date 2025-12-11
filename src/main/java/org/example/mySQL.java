package org.example;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.Objects;

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

            // "Objects.equireNonNull" simplemente permite que la conexion no pueda ser nula
            desconectar(conexion);

        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);

        }

    }

    public static void listarTablasMySQL (Connection conexion, int posicion) {

        try {

            switch (posicion) {

                case 1:

                    String sqlTienda = "SELECT * FROM Tiendas;";
                    Statement stmtT = conexion.createStatement();
                    ResultSet rsT = stmtT.executeQuery(sqlTienda);

                    System.out.println("**** Tabla Tienda ****");
                    while (rsT.next()) {

                        System.out.printf("%d, %s, %s %n", rsT.getInt(1),
                                rsT.getString(2), rsT.getString(3));

                    }

                    stmtT.close();
                    rsT.close();
                    break;

                case 2:

                    String sqlEmpleados = "SELECT * FROM Empleados;";
                    Statement stmtE = conexion.createStatement();
                    ResultSet rsE = stmtE.executeQuery(sqlEmpleados);

                    System.out.println("**** Tabla Empleados ****");
                    while (rsE.next()) {

                        System.out.printf("%d, %s, %s %n", rsE.getInt(1),
                                rsE.getString(2), rsE.getString(3));

                    }

                    stmtE.close();
                    rsE.close();
                    break;

                case 3:

                    String sqlClientes = "SELECT * FROM Clientes;";
                    Statement stmtC = conexion.createStatement();
                    ResultSet rsC = stmtC.executeQuery(sqlClientes);

                    System.out.println("**** Tabla Clientes ****");
                    while (rsC.next()) {

                        System.out.printf("%d, %s, %s %n", rsC.getInt(1),
                                rsC.getString(2), rsC.getString(3));

                    }

                    stmtC.close();
                    rsC.close();
                    break;

                case 4:

                    String sqlProveedores = "SELECT * FROM Proveedores;";
                    Statement stmtP = conexion.createStatement();
                    ResultSet rsP = stmtP.executeQuery(sqlProveedores);

                    System.out.println("**** Tabla Proveedores ****");
                    while (rsP.next()) {

                        System.out.printf("%d, %s, %s %n", rsP.getInt(1),
                                rsP.getString(2), rsP.getString(3));

                    }

                    stmtP.close();
                    rsP.close();
                    break;

                case 5:

                    String sqlPedidos = "SELECT * FROM Pedidos;";
                    Statement stmtPe = conexion.createStatement();
                    ResultSet rsPe = stmtPe.executeQuery(sqlPedidos);

                    System.out.println("**** Tabla Proveedores ****");
                    while (rsPe.next()) {

                        System.out.printf("%d, %s, %s %n", rsPe.getInt(1),
                                rsPe.getString(2), rsPe.getString(3));

                    }

                    stmtPe.close();
                    rsPe.close();
                    break;

                case 6:

                    String sqlProductos = "SELECT * FROM Productos;";
                    Statement stmtPr = conexion.createStatement();
                    ResultSet rsPr = stmtPr.executeQuery(sqlProductos);

                    System.out.println("**** Tabla Productos ****");
                    while (rsPr.next()) {

                        System.out.printf("%d, %s, %s %n", rsPr.getInt(1),
                                rsPr.getString(2), rsPr.getString(3));

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

            desconectar(conexion);

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
