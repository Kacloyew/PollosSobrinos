package org.example;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

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

    public static void aniadirPedidoOracle(Connection conexion) {

        Scanner sc = new Scanner(System.in);

        try {

            System.out.println("=== NUEVO PEDIDO ===");

            String sqlPedido = "INSERT INTO Pedidos (Empleado_ID, Tienda_ID, Cliente_ID, Producto_ID, Fecha_Pedido, Cantidad) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexion.prepareStatement(sqlPedido);

            // Pedimos el ID Empleado
            System.out.println("ID del Empleado: ");
            String empleadoInput = sc.nextLine();
            pstmt.setInt(1, Integer.parseInt(empleadoInput));


            // Pedir Tienda_ID
            System.out.print("ID de la Tienda: ");
            int tiendaId = Integer.parseInt(sc.nextLine());
            pstmt.setInt(2, tiendaId);

            // Pedir Cliente_ID
            System.out.print("ID del Cliente: ");
            int clienteId = Integer.parseInt(sc.nextLine());
            pstmt.setInt(3, clienteId);

            // Pedir Producto_ID
            int productoId;
            while (true) {

                System.out.print("ID del Producto: ");
                productoId = Integer.parseInt(sc.nextLine());

                // Verificar si el producto existe (ESTA ES LA VALIDACIÓN IMPORTANTE)
                PreparedStatement checkProducto = conexion.prepareStatement("SELECT Producto_ID FROM Productos WHERE Producto_ID = ?");
                checkProducto.setInt(1, productoId);
                ResultSet rs = checkProducto.executeQuery();

                if (rs.next()) {

                    break; // Producto existe

                } else {

                    System.out.println("El Producto_ID " + productoId + " no existe.");

                    // Mostrar productos disponibles para ayudar al usuario
                    System.out.println("Productos disponibles:");

                    Statement listProd = conexion.createStatement();
                    ResultSet productos = listProd.executeQuery("SELECT Producto_ID, Nombre FROM Productos ORDER BY Producto_ID");

                    while (productos.next()) {
                        System.out.println("   - ID: " + productos.getInt("Producto_ID") + ", Nombre: " + productos.getString("Nombre"));

                    }

                    System.out.println("Intenta de nuevo:");

                }

            }
            pstmt.setInt(4, productoId);

            // Fecha del Pedido
            System.out.print("Fecha del pedido (formato: yyyy-MM-dd) o Enter para fecha actual: ");
            String fechaInput = sc.nextLine();

            if (!fechaInput.isEmpty()) {

                // Convertir String a Timestamp (solo fecha)
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = dateFormat.parse(fechaInput);
                Timestamp timestamp = new Timestamp(parsedDate.getTime());
                pstmt.setTimestamp(5, timestamp);

            } else {

                // Usar fecha actual por defecto
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            }

            // Pedir Cantidad
            System.out.print("Cantidad (presiona Enter para 1): ");
            String cantidadInput = sc.nextLine();
            if (!cantidadInput.isEmpty()) {
                pstmt.setInt(6, Integer.parseInt(cantidadInput));
            } else {
                pstmt.setInt(6, 1); // Valor por defecto
            }

            // Confirmar pedido
            System.out.println("\n--- Resumen del Pedido ---");
            System.out.println("Empleado ID: " + (empleadoInput.isEmpty() ? "NULL" : empleadoInput));
            System.out.println("Tienda ID: " + tiendaId);
            System.out.println("Cliente ID: " + clienteId);
            System.out.println("Producto ID: " + productoId);
            System.out.println("Fecha: " + (fechaInput.isEmpty() ? "Actual" : fechaInput));
            System.out.println("Cantidad: " + (cantidadInput.isEmpty() ? "1" : cantidadInput));

            System.out.print("\n¿Confirmar pedido? (S/N): ");
            String confirmacion = sc.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {

                int filasAfectadas = pstmt.executeUpdate();
                System.out.println("Pedido insertado correctamente. Filas afectadas: " + filasAfectadas + "\n");

            } else {

                System.out.println("Pedido cancelado.");

            }

        } catch (SQLException | ParseException e) {

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

    public static void nuevoProducto(Connection conexion, String nombreTabl, String consultaSQL) {

        try {
            //crear Statement para ejecutar consulta
            Statement stmt = conexion.createStatement();

            //realizar consulta para obetener registros de tablas
            ResultSet rs = stmt.executeQuery(consultaSQL);

            // obtener metada de las columnas
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            System.out.println();





        } catch (SQLException e) {
//            throw new RuntimeException(e);
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
