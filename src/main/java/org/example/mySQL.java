package org.example;

import org.apache.ibatis.jdbc.ScriptRunner;

import javax.swing.plaf.nimbus.State;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class mySQL {

    public static void crearTablas (Connection conexion) {

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

    public static void aniadirPedidoMySQL(Connection conexion, Scanner sc) {

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

            sc.nextLine();

        } catch (SQLException | ParseException e) {

            throw new RuntimeException(e);

        }

    }

    public static void aniadirClienteMySQL(Connection conexion, Scanner sc) {

        try {

            System.out.println("=== NUEVO CLIENTE ===");

            String sqlCliente = "INSERT INTO Clientes (Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Tienda_ID) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexion.prepareStatement(sqlCliente);

            // Pedimos el Nombre
            System.out.println("Nombre del cliente: ");
            String nombre = sc.nextLine();
            pstmt.setString(1, nombre);

            // Pedir Apellido
            System.out.print("Apellido del cliente: ");
            String apellido = sc.nextLine();
            pstmt.setString(2, apellido);

            // Pedir NIF_NIE
            System.out.print("NIF_NIE del cliente: ");
            String NIF_NIE = sc.nextLine();
            pstmt.setString(3, NIF_NIE);

            // Pedir Telefono
            String telefono;
            while (true) {

                System.out.print("Telefono del cliente: ");
                telefono = sc.nextLine();

                if (telefono.length() != 11) {

                    break;

                }

            }
            pstmt.setString(4, telefono);

            // Pedir Correo
            System.out.print("Correo del cliente: ");
            String correo = sc.nextLine();
            pstmt.setString(5, correo);

            // Pedir Tienda_ID
            System.out.print("Pedir Tienda del cliente: ");
            int tienda_id = sc.nextInt();
            pstmt.setInt(6, tienda_id);

            // Confirmar pedido
            System.out.println("\n--- Resumen del Cliente ---");
            System.out.println("Nombre del cliente: " + nombre);
            System.out.println("Apellido del cliente: " + apellido);
            System.out.println("NIF_NIE del cliente: " + NIF_NIE);
            System.out.println("Telefono del cliente: " + telefono);
            System.out.println("Correo del cliente: " + correo);
            System.out.println("Tienda ID: " + tienda_id);

            System.out.print("\n¿Confirmar cliente? (S/N): ");
            sc.nextLine();
            String confirmacion = sc.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {

                int filasAfectadas = pstmt.executeUpdate();
                System.out.println("Cliente insertado correctamente. Filas afectadas: " + filasAfectadas + "\n");

            } else {

                System.out.println("Cliente cancelado.");

            }

            sc.nextLine();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void aniadirProductoMySQL(Connection conexion, Scanner sc) {

        try {

            System.out.println("=== NUEVO PRODUCTO ===");

            String sqlProducto = "INSERT INTO Productos (Nombre, Precio, Stock, Proveedor_ID) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = conexion.prepareStatement(sqlProducto);

            // Pedimos el Nombre
            System.out.println("Nombre del producto: ");
            String nombre = sc.nextLine();
            pstmt.setString(1, nombre);

            // Pedir Precio
            System.out.print("Precio del producto: ");
            double precio = sc.nextDouble();
            pstmt.setDouble(2, precio);

            // Pedir Stock
            System.out.print("Stock del producto: ");
            int stock = sc.nextInt();
            pstmt.setInt(3, stock);

            // Pedir proveedor_ID
            System.out.print("Pedir proveedor_ID: ");
            int proveedor_id = sc.nextInt();
            pstmt.setInt(4, proveedor_id);

            // Confirmar pedido
            System.out.println("\n--- Resumen del Producto ---");
            System.out.println("Nombre del producto: " + nombre);
            System.out.println("Precio del producto: " + precio);
            System.out.println("Stock del producto: " + stock);
            System.out.println("ID del proveedor: " + proveedor_id);

            System.out.print("\n¿Confirmar producto? (S/N): ");
            sc.nextLine();
            String confirmacion = sc.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {

                int filasAfectadas = pstmt.executeUpdate();
                System.out.println("Producto insertado correctamente. Filas afectadas: " + filasAfectadas + "\n");

            } else {

                System.out.println("Producto cancelado.");

            }

            sc.nextLine();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void modificarPrecioProducto(Connection conexion, Scanner sc) {

        try {

            System.out.println("=== MODIFICAR PRODUCTO ===");

            // Mostrar los productos disponibles a modificar
            Statement stmt = conexion.createStatement();
            ResultSet productos = stmt.executeQuery("SELECT Producto_ID, Nombre FROM Productos ORDER BY Producto_ID");

            while (productos.next()) {

                System.out.println("   - ID: " + productos.getInt("Producto_ID") + ", Nombre: " + productos.getString("Nombre"));

            }

            // Pedir que producto modificar
            System.out.println("Que producto quieres modificar: ");
            int prodSelect = sc.nextInt();

            // Pedir el nuevo precio del producto
            System.out.println("Introduce el nuevo precio del producto: ");
            double precio = sc.nextDouble();

            // Preparamos la sentencia para actualizar el precio
            String sql = "UPDATE Productos SET Precio = ? WHERE Producto_ID = ?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setDouble(1, precio);
            pstmt.setInt(2, prodSelect);

            // Ejecutamos la sentencia anteriormente preparada
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Precio actualizado correctamente");
            } else {
                System.out.println("No existe un producto con ese ID");
            }

            sc.nextLine();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void buscarProductoPorProveedor (Connection conexion, Scanner sc) {

        try {

            System.out.println("=== BUSCAR PRODUCTO ===");

            // Mostramos la tabla Proveedores para tenerlos de referencia y posteriormente filtrar por producto
            Statement stmt = conexion.createStatement();
            ResultSet proveedores =  stmt.executeQuery("SELECT Proveedor_ID, Nombre FROM Proveedores");

            while (proveedores.next()) {

                System.out.println("   - ID: " + proveedores.getInt("Proveedor_ID") + ", Nombre: " + proveedores.getString("Nombre"));

            }

            // Pedimos el ID del proveedor
            System.out.println("Introduce el ID del proveedor: ");
            int id_proveedor = sc.nextInt();
            sc.nextLine();

            // Preparamos la sentencia de búsqueda
            String sql = "SELECT * FROM Productos WHERE Proveedor_ID = ?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id_proveedor);

            // Ejecutamos la sentencia anteriormente preparada
            ResultSet rs = pstmt.executeQuery();
            boolean encontrado = false;

            while (rs.next()) {
                encontrado = true;
                System.out.println("ID: " + rs.getInt("Producto_ID") +
                        ", Nombre: " + rs.getString("Nombre") +
                        ", Precio: " + rs.getDouble("Precio"));
            }

            if (!encontrado) {
                System.out.println("No existen productos para ese proveedor.");
            }

            sc.nextLine();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void eliminarProducto(Connection conexion, Scanner sc) {

        try {

            System.out.println("=== ELIMINAR PRODUCTO ===");

            // Mostrar productos
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT Producto_ID, Nombre FROM Productos ORDER BY Producto_ID"
            );

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Producto_ID")
                        + " | " + rs.getString("Nombre"));
            }

            // Pedimos el ID_Producto
            System.out.print("ID del producto a eliminar: ");
            int idProducto = sc.nextInt();

            // Comprobar si hay algún pedido con ese producto
            String sqlCheck = "SELECT 1 FROM Pedidos WHERE Producto_ID = ?";
            PreparedStatement check = conexion.prepareStatement(sqlCheck);
            check.setInt(1, idProducto);

            ResultSet rsEliminar = check.executeQuery();

            if (rsEliminar.next()) {

                System.out.println("No se puede eliminar: tiene pedidos asociados");

            } else {

                String sqlDelete = "DELETE FROM Productos WHERE Producto_ID = ?";
                PreparedStatement delete = conexion.prepareStatement(sqlDelete);
                delete.setInt(1, idProducto);

                int filas = delete.executeUpdate();

                if (filas > 0) {

                    System.out.println("Producto eliminado");

                } else {

                    System.out.println("Producto no encontrado");

                }

            }

            sc.nextLine();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void modificarSalarioEmpleado (Connection conexion, Scanner sc) {

        try {

            System.out.println("=== MODIFICAR SALARIO ===");

            // Mostramos los empleados disponibles a modificar el salario
            Statement stmt = conexion.createStatement();
            ResultSet empleados = stmt.executeQuery("SELECT Empleado_ID, Nombre, Salario FROM Empleados ORDER BY Empleado_ID");

            while (empleados.next()) {

                System.out.println("   - ID: " + empleados.getInt("Empleado_ID") + ", Nombre: " + empleados.getString("Nombre") + ", Salario: " + empleados.getDouble("Salario"));

            }

            // Pedimos el empleado al que queremos modificar
            System.out.println("Introduce el ID del empleado: ");
            int id_empleado = sc.nextInt();

            // Pedimos el nuevo salario de ese empelado
            System.out.println("Introduce el nuevo Salario del empleado: ");
            double salario = sc.nextDouble();

            // Preparamos la sentencia para modificar el salario del empleado que hemos dicho
            String sql = "UPDATE Empleados SET Salario = ? WHERE Empleado_ID = ?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setDouble(1, salario);
            pstmt.setInt(2, id_empleado);

            // Ejecutamos la sentencia preparada con su checkeo de funcionamiento de filas.
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Precio actualizado correctamente");
            } else {
                System.out.println("No existe un producto con ese ID");
            }

            sc.nextLine();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void listarTablasMySQL (Connection conexion, int posicion) {

        switch (posicion) {

            case 1:

                imprimirTabla(conexion, "Tiendas", "SELECT * FROM Tiendas;");
                break;


            case 2:

                imprimirTabla(conexion, "Empleados", "SELECT * FROM Empleados;");
                break;

            case 3:

                imprimirTabla(conexion, "Clientes", "SELECT * FROM Clientes;");
                break;

            case 4:

                imprimirTabla(conexion, "Proveedores", "SELECT * FROM Proveedores;");
                break;


            case 5:

                imprimirTabla(conexion, "Pedidos", "SELECT * FROM Pedidos;");
                break;


            case 6:

                imprimirTabla(conexion, "Productos", "SELECT * FROM Productos;");
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

            throw new RuntimeException(e);

        }

    }

    public static void aumentarSalario (Connection conexion, Scanner sc) {

        String sqlProcedure = "{ call subida_salario_empleado (?, ?) }";

        try {

            // Imprimimos la tabla de
            imprimirTabla(conexion, "Empleados", "SELECT * FROM Empleados");

            // Recogemos los datos necesarios para ejecutar el procedimiento y pasarlo por parametro
            System.out.println("Cuanto salario quieres aumentarle al empleado -> ");
            double aumentoSalario = sc.nextDouble();

            System.out.println("Dime el ID del empleado que quieres asignar -> ");
            int id_empleado = sc.nextInt();

            // Creamos la llamada al Procedure
            CallableStatement cstmt = conexion.prepareCall(sqlProcedure);
            cstmt.setInt(1, id_empleado);
            cstmt.setDouble(2, aumentoSalario);

            // Ejecutamos la sentencia
            cstmt.executeUpdate();
            System.out.println("Salario aumentado correctamente");

            cstmt.close();

            sc.nextLine();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void empleadosDeUnaTienda (Connection conexion, Scanner sc) {

        String sqlProcedure = "{ call filtrar_empleados_tienda (?) }";

        try {

            imprimirTabla(conexion, "Tiendas", "SELECT * FROM Tiendas");

            System.out.println("Dime el ID de la tienda que quieres filtrar -> ");
            int id_tienda = sc.nextInt();

            CallableStatement cstmt = conexion.prepareCall(sqlProcedure);
            cstmt.setInt(1, id_tienda);

            ResultSet rs = cstmt.executeQuery();

            System.out.println("\n=== EMPLEADOS DE LA TIENDA " + id_tienda + " ===");

            while (rs.next()) {

                System.out.println(

                        rs.getInt("Empleado_ID") + " | " +
                                rs.getString("Nombre") + " | " +
                                rs.getDouble("Salario")

                );

            }

            rs.close();
            cstmt.close();

            sc.nextLine();

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
