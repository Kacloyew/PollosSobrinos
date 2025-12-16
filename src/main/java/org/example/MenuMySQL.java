package org.example;

import java.sql.Connection;
import java.util.Scanner;

public class MenuMySQL {

    public static void mostrarMenu(Scanner sc) {

        int opc = 0;

        Connection conexion = mySQL.conectar();

        if (conexion == null) {

            System.out.println("Error al conectar con MySQL");
            return;

        }

        while (opc != 9) {

            System.out.println("===== Menu MySQL =====");
            System.out.println("1. Reinstalar Tablas");
            System.out.println("2. Listar Tablas");
            System.out.println("3. Operar con las tablas");
            System.out.println("4. Mostrar metadatos de la BDD");
            System.out.println("5. Ejecutar procedimientos");
            System.out.println("6. Informes JasperReports");
            System.out.println("9. Volver al menú principal");

            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido");
                continue;
            }

            switch (opc) {

                case 1:
                    mySQL.crearTablas(conexion);
                    break;

                case 2:
                    listarTablas(conexion, sc);
                    break;

                case 3:
                    operarTablas(conexion, sc);
                    break;

                case 4:
                    metadatosMySQL(conexion, sc);
                    break;

                case 5:
                    ejecutarProcedimientos(conexion, sc);
                    break;

                case 6:
                    ejecutarJasperReports(conexion, sc);
                    break;

                case 9:
                    System.out.println("Salir");
                    mySQL.desconectar(conexion);
                    return;

                default:
                    System.out.println("Error, selecciona un número válido");

            }

        }

    }

    private static void operarTablas(Connection conexion, Scanner sc) {

        int opc = 0;

        if (conexion == null) {

            System.out.println("Error al conectar con MySQL");
            return;

        }

        while (opc != 7) {
            System.out.println("===== Menu MySQL =====");
            System.out.println("1. Añadir nuevo pedido");
            System.out.println("2. Añadir nuevo cliente");
            System.out.println("3. Añadir nuevo producto");
            System.out.println("4. Modificar precio producto");
            System.out.println("5. Buscar producto por Proveedor_ID");
            System.out.println("6. Eliminar un producto (si no está en pedidos)");
            System.out.println("7. Actualizar salario de empleado por ID_Empleado");
            System.out.println("8. Salir");

            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido");
                continue;
            }

            switch (opc) {

                case 1:

                    mySQL.aniadirPedidoMySQL(conexion, sc);
                    break;

                case 2:

                    mySQL.aniadirClienteMySQL(conexion, sc);
                    break;

                case 3:

                    mySQL.aniadirProductoMySQL(conexion, sc);
                    break;

                case 4:

                    mySQL.modificarPrecioProducto(conexion, sc);
                    break;

                case 5:

                    mySQL.buscarProductoPorProveedor(conexion, sc);
                    break;

                case 6:

                    mySQL.eliminarProducto(conexion, sc);
                    break;

                case 7:

                    mySQL.modificarSalarioEmpleado(conexion, sc);
                    break;

                case 8:
                    System.out.println("**** Retrocediendo ****");
                    break;

                default:
                    System.out.println("Error, selecciona un número válido");

            }

        }

    }

    private static void listarTablas(Connection conexion, Scanner sc) {

        int opc = 0;

        if (conexion == null) {

            System.out.println("Error al conectar con MySQL");
            return;

        }

        while (opc !=7) {

            System.out.println("===== Menu MySQL =====");
            System.out.println("1. Tiendas");
            System.out.println("2. Empleados");
            System.out.println("3. Clientes");
            System.out.println("4. Proveedores");
            System.out.println("5. Pedidos");
            System.out.println("6. Productos");
            System.out.println("7. Salir");

            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido");
                continue;
            }

            switch (opc) {

                case 1, 2, 3, 4, 5, 6:
                    mySQL.listarTablasMySQL(conexion, opc);
                    break;

                case 7:
                    System.out.println("**** Retrocediendo ****");
                    break;

                default:
                    System.out.println("Error, selecciona un número válido");

            }

        }

    }

    public static void metadatosMySQL(Connection conexion, Scanner sc) {

        int opc = 0;

        if (conexion == null) {
            System.out.println("Error al conectar con MySQL");
        }

        while (opc != 3) {

            System.out.println("===== Metadatos MySQL =====");

            System.out.println("1. Metadatos generales de MySQL");
            System.out.println("2. Metadatos ResultSet");
            System.out.println("3. Volver");

            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido");
                continue;
            }

            switch (opc) {

                case 1:
                    metadatosMySQL.metadatosMySQL(conexion);
                    break;

                case 2:
                    metadatosMySQL.metadatosResultSet(conexion);
                    break;

                case 3:
                    System.out.println("**** Retrocediendo ****");
                    break;

                default:
                    System.out.println("Error, seleciona un número válido");
                    break;
            }

        }

    }

    public static void ejecutarProcedimientos(Connection conexion, Scanner sc) {

        int opc = 0;

        if (conexion == null) {
            System.out.println("Error al conectar con MySQL");
        }

        while (opc != 3) {

            System.out.println("===== Procedimientos MySQL =====");

            System.out.println("1. Aumentar un salario dado un Empleado_ID");
            System.out.println("2. Buscar por los empleados de un Tienda_ID");
            System.out.println("3. Volver");

            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido");
                continue;
            }

            switch (opc) {

                case 1:
                    mySQL.aumentarSalario(conexion, sc);
                    break;

                case 2:
                    mySQL.empleadosDeUnaTienda(conexion, sc);
                    break;

                case 3:
                    System.out.println("**** Retrocediendo ****");
                    break;

                default:
                    System.out.println("Error, seleciona un número válido");
                    break;
            }

        }

    }

    public static void ejecutarJasperReports(Connection conexion, Scanner sc) {

        int opc = 0;

        if (conexion == null) {
            System.out.println("Error al conectar con MySQL");
        }

        while (opc != 3) {

            System.out.println("===== Informes MySQL Jasper =====");

            System.out.println("1. Generar Informe para los Clientes");
            System.out.println("2. Generar Informe para los Pedidos");
            System.out.println("3. Volver");

            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido");
                continue;
            }

            switch (opc) {

                case 1:
                    jasperReports.listarClientesJP_SQL(conexion);
                    break;

                case 2:
                    jasperReports.listarPedidosJP_SQL(conexion);
                    break;

                case 3:
                    System.out.println("**** Retrocediendo ****");
                    break;

                default:
                    System.out.println("Error, seleciona un número válido");
                    break;
            }

        }

    }

}

