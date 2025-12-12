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

    public static void aniadirClienteOracle(Connection conexion) {

        Scanner sc = new Scanner(System.in);

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


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void aniadirProductoOracle(Connection conexion) {

        Scanner sc = new Scanner(System.in);

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

        } catch (SQLException e) {

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

    public static void metadatosOracle(Connection conexion) {

        try {
            DatabaseMetaData meta = conexion.getMetaData();

            System.out.println("=== INFORMACIÓN GENERAL ORACLE ===");
            System.out.println("Nombre BD: " + meta.getDatabaseProductName());
            System.out.println("Driver: " + meta.getDriverName());
            System.out.println("URL: " + meta.getURL());
            System.out.println("Usuario: " + meta.getUserName());
            System.out.println();

            String catalog = null;
            String schema = meta.getUserName().toUpperCase();  // muy importante

            System.out.println("=== TABLAS DEL ESQUEMA " + schema + " ===");

            try (ResultSet tablas = meta.getTables(catalog, schema, "%", new String[]{"TABLE"})) {

                while (tablas.next()) {

                    String tabla = tablas.getString("TABLE_NAME");
                    System.out.println("\nTABLA: " + tabla);

                    // ----------------------------
                    // COLUMNAS
                    // ----------------------------
                    System.out.println(" - Columnas:");
                    try (ResultSet cols = meta.getColumns(catalog, schema, tabla, "%")) {

                        while (cols.next()) {
                            System.out.printf(
                                    "   %s | %s | Tamaño=%d | Null=%s%n",
                                    cols.getString("COLUMN_NAME"),
                                    cols.getString("TYPE_NAME"),
                                    cols.getInt("COLUMN_SIZE"),
                                    cols.getString("IS_NULLABLE")
                            );
                        }
                    }

                    // ----------------------------
                    // PRIMARY KEY
                    // ----------------------------
                    System.out.println(" - Clave primaria:");
                    boolean tienePK = false;

                    try (ResultSet pk = meta.getPrimaryKeys(catalog, schema, tabla)) {
                        while (pk.next()) {
                            System.out.println("   " + pk.getString("COLUMN_NAME"));
                            tienePK = true;
                        }
                    }
                    if (!tienePK) System.out.println("   (no tiene PK)");

                    // ----------------------------
                    // FOREIGN KEYS IMPORTADAS
                    // ----------------------------
                    System.out.println(" - FKs importadas:");
                    boolean tieneFKimp = false;

                    try (ResultSet fkImp = meta.getImportedKeys(catalog, schema, tabla)) {
                        while (fkImp.next()) {
                            System.out.printf(
                                    "   %s → %s(%s)%n",
                                    fkImp.getString("FKCOLUMN_NAME"),
                                    fkImp.getString("PKTABLE_NAME"),
                                    fkImp.getString("PKCOLUMN_NAME")
                            );
                            tieneFKimp = true;
                        }
                    }
                    if (!tieneFKimp) System.out.println("   (ninguna)");

                    // ----------------------------
                    // FOREIGN KEYS EXPORTADAS
                    // ----------------------------
                    System.out.println(" - FKs exportadas:");
                    boolean tieneFKexp = false;

                    try (ResultSet fkExp = meta.getExportedKeys(catalog, schema, tabla)) {
                        while (fkExp.next()) {
                            System.out.printf(
                                    "   %s(%s) ← %s%n",
                                    fkExp.getString("PKTABLE_NAME"),
                                    fkExp.getString("PKCOLUMN_NAME"),
                                    fkExp.getString("FKTABLE_NAME")
                            );
                            tieneFKexp = true;
                        }
                    }
                    if (!tieneFKexp) System.out.println("   (ninguna)");
                }
            }

        } catch (Exception e) {
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
