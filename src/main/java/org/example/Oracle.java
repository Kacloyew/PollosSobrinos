package org.example;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Oracle {

    public static void crearTablas(Connection conexion) {

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

    public static void aniadirPedidoOracle(Connection conexion, Scanner sc) {

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

    public static void aniadirClienteOracle(Connection conexion, Scanner sc) {

        try {

            System.out.println("=== NUEVO CLIENTE ===");

            String sqlCliente = "INSERT INTO Clientes (Cliente_ID, Nombre, Apellido, NIF_NIE, Telefono, CorreoElectronico, Tienda_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexion.prepareStatement(sqlCliente);

            //Pedir ID Cliente

            System.out.println("ID Cliente: ");
            String id_cliente = sc.nextLine();
            pstmt.setString(1, id_cliente);

            // Pedir Nombre Cliente
            System.out.print("Nombre del Cliente: ");
            String nombreCliente = sc.nextLine();
            pstmt.setString(2, nombreCliente);

            // Pedir Apellido Cliente
            System.out.print("Apellido del Cliente: ");
            String apellidoCliente = sc.nextLine();
            pstmt.setString(3, apellidoCliente);

            //Pedir NIF/NIE del cliente
            System.out.print("NIF/NIE del Cliente: ");
            String NIF_NIECliente = sc.nextLine();
            pstmt.setString(4, NIF_NIECliente);

            //Pedir teléfono del cliente
            String telefono;
            while (true) {

                System.out.print("Teléfono del cliente: ");
                telefono = sc.nextLine();

                if (telefono.length() != 11) {

                    break;

                }

            }
            pstmt.setString(5, telefono);

            //Pedir Correo
            System.out.print("Correo del cliente: ");
            String correoElectronicoCliente = sc.nextLine();
            pstmt.setString(6, correoElectronicoCliente);

            //Pedir ID de tienda
            System.out.print("Tienda del cliente: ");
            int id_Tienda = Integer.parseInt(sc.nextLine());
            pstmt.setInt(7, id_Tienda);


            //Confirmar añadir cliente
            System.out.println("\n--- Resumen del nuevo Cliente ---");
            System.out.println("Nombre: " + nombreCliente);
            System.out.println("Apellido: " + apellidoCliente);
            System.out.println("NIF/NIE: " + NIF_NIECliente);
            System.out.println("Teléfono: " + telefono);
            System.out.println("Correo Electronico: " + correoElectronicoCliente);
            System.out.println("Tienda del nuevo Cliente: " + id_Tienda);

            System.out.print("\n¿Confirmar nuevo Cliente? (S/N): ");
            String confirmacion = sc.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {

                int filasAfectadas = pstmt.executeUpdate();
                System.out.println("Cliente insertado correctamente. Filas afectadas: " + filasAfectadas + "\n");

            } else {

                System.out.println("Cliente cancelado.");

            }

            sc.nextLine();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void aniadirProductoOracle(Connection conexion, Scanner sc) {

        try {

            System.out.println("=== NUEVO PRODUCTO ===");

            String sqlProducto = "INSERT INTO Productos (Producto_ID, Nombre, Precio, Stock, Proveedor_ID) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexion.prepareStatement(sqlProducto);

            //pedir producto_ID
            System.out.println("ID del producto: ");
            int ID = sc.nextInt();
            pstmt.setInt(1, ID);

            sc.nextLine();
            // Pedimos el Nombre
            System.out.println("Nombre del producto: ");
            String nombre = sc.nextLine();
            pstmt.setString(2, nombre);

            // Pedir Precio
            System.out.print("Precio del producto: ");
            double precio = sc.nextDouble();
            pstmt.setDouble(3, precio);

            // Pedir Stock
            System.out.print("Stock del producto: ");
            int stock = sc.nextInt();
            pstmt.setInt(4, stock);

            // Pedir proveedor_ID
            System.out.print("Pedir proveedor_ID: ");
            int proveedor_id = sc.nextInt();
            pstmt.setInt(5, proveedor_id);

            // Confirmar pedido
            System.out.println("\n--- Resumen del Producto ---");
            System.out.println("ID del producto: " + ID);
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

    public static void buscarProductoPorProveedor(Connection conexion, Scanner sc) {

        try {

            System.out.println("=== BUSCAR PRODUCTO POR PROVEEDOR ===");

            //mostrar proveedores
            Statement stmt = conexion.createStatement();
            ResultSet proveedores = stmt.executeQuery("SELECT Proveedor_ID, Nombre FROM Proveedores");

            while (proveedores.next()) {
                System.out.println("  - ID: " + proveedores.getInt("Proveedor_ID")
                        + ", Nombre: " + proveedores.getString("Nombre"));
            }

            //pedir ID proveedor
            System.out.println("Introduce el ID del proveedor: ");
            int idProveedor = sc.nextInt();

            //limpiar el buffer
            sc.nextLine();

            //consulta de prodcutos
            String sql = "SELECT Producto_ID, Nombre, Precio FROM Productos WHERE Proveedor_ID = ?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, idProveedor);

            ResultSet rs = pstmt.executeQuery();

            boolean hayResultados = false;

            while (rs.next()) {
                hayResultados = true;
                System.out.println(
                        "Producto ID: " + rs.getInt("Producto_ID") +
                                ", Nombre: " + rs.getString("Nombre") +
                                ", Precio: " + rs.getDouble("Precio")
                );
            }

            if (!hayResultados) {
                System.out.println("No hay productos para ese proveedor");
            }

            sc.nextLine();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    public static void eliminarProducto(Connection conexion, Scanner sc) {

        try {

            conexion.setAutoCommit(true); // para confirmar el DELETE
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
                System.out.println("Filas afectadas: " + filas); // Depuración

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

    public static void modificarSalarioEmpleado(Connection conexion, Scanner sc) {

        try {

            System.out.println("=== MODIFICAR SALARIO ===");

            // Mostramos los empleados disponibles a modificar el salario
            Statement stmt = conexion.createStatement();
            ResultSet empleados = stmt.executeQuery("SELECT Empleado_ID, Nombre, Salario FROM Empleados ORDER BY Empleado_ID");

            while (empleados.next()) {
                System.out.println("   - ID: " + empleados.getInt("Empleado_ID") +
                        ", Nombre: " + empleados.getString("Nombre") +
                        ", Salario: " + empleados.getDouble("Salario"));
            }

            // Pedimos el empleado al que queremos modificar
            System.out.println("Introduce el ID del empleado: ");
            int id_empleado = Integer.parseInt(sc.nextLine());

            // Pedimos el nuevo salario de ese empelado
            System.out.println("Introduce el nuevo Salario del empleado: ");
            double salario = Double.parseDouble(sc.nextLine());

            // Preparamos la sentencia para modificar el salario del empleado que hemos dicho
            String sql = "UPDATE Empleados SET Salario = ? WHERE Empleado_ID = ?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setDouble(1, salario);
            pstmt.setInt(2, id_empleado);

            // Ejecutamos la sentencia preparada con su checkeo de funcionamiento de filas.
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Salario actualizado correctamente");
            } else {
                System.out.println("No existe un empleado con ese ID");
            }

            sc.nextLine();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    public static void ejecutarProcedimientos(Connection conexion, Scanner sc) {

    }

    public static void listarTablasOracle(Connection conexion, int posicion) {

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

    public static void aumentarSalario(Connection conexion, Scanner sc) {

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

            // Ejecutamos la sentencia
            cstmt.executeUpdate();
            System.out.println("Busqueda filtrada correctamente");

            cstmt.close();

            sc.nextLine();

        } catch (SQLException e) {

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