package org.example;

import java.sql.Connection;
import java.util.Scanner;

public class MenuOracle {

    public static void mostrarMenu(Scanner sc) {

        int opc = 0;
        Connection conexion = Oracle.conectar();

        if (conexion == null) {

            System.out.println("Error al conectar con Oracle");
            return;

        }

        while (opc != 9) {

            System.out.println("===== Menu Oracle =====");
            System.out.println("1. Reinstalar Tablas");
            System.out.println("2. Listar Tablas");
            System.out.println("3. Operar con las tablas");
            System.out.println("4. Mostrar metadatos de la BDD");
            System.out.println("9. Volver al menú principal");

            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido");
                continue;
            }


            switch (opc) {

                case 1:
                    Oracle.crearTablas(conexion);
                    break;

                case 2:
                    listarTablas(conexion, sc);
                    break;

                case 3:
                    operarTablas(conexion, sc);
                    break;

                case 4:
                    metadatosOracle(conexion, sc);
                    break;

                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 9:
                    System.out.println("Salir");
                    Oracle.desconectar(conexion);
                    return;

                default:
                    System.out.println("Error, selecciona un número válido");

            }

        }

    }

    private static void operarTablas(Connection conexion, Scanner sc) {

        int opc = 0;

        if (conexion == null) {

            System.out.println("Error al conectar con Oracle");
            return;
        }

        while (opc != 8) {
            System.out.println("===== Menu Oracle =====");
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
                    Oracle.aniadirPedidoOracle(conexion, sc);
                    break;

                case 2:
                    Oracle.aniadirClienteOracle(conexion, sc);
                    break;

                case 3:
                    Oracle.aniadirProductoOracle(conexion, sc);
                    break;

                case 4:
                    Oracle.modificarPrecioProducto(conexion, sc);
                    break;

                case 5:
                    Oracle.buscarProductoPorProveedor(conexion, sc);
                    break;

                case 6:
                    Oracle.eliminarProducto(conexion, sc);
                    break;

                case 7:

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

            System.out.println("Error al conectar con Oracle");
            return;

        }

        while (opc !=7) {

            System.out.println("===== Menu Oracle =====");
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
                    Oracle.listarTablasOracle(conexion, opc);
                    break;

                case 7:
                    System.out.println("**** Retrocediendo ****");
                    break;

                default:
                    System.out.println("Error, selecciona un número válido");

            }

        }

    }
    public static void metadatosOracle(Connection conexion, Scanner sc) {

        int opc = 0;

        if (conexion == null) {
            System.out.println("Error al conectar con Oracle");
        }

        while (opc != 3) {

            System.out.println("===== Metadatos Oracle =====");

            System.out.println("1. Metadatos generales de Oracle");
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
                    metadatosOracle.metadatosOracle(conexion);
                    break;

                case 2:
                    metadatosOracle.metadatosResultSet(conexion);
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
