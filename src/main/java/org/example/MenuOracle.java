package org.example;

import java.sql.Connection;
import java.util.Scanner;

public class MenuOracle {

    public static void mostrarMenu() {

        Scanner sc = new Scanner(System.in);

        int opc = 0;

        Connection conexion = Oracle.conectar();

        if (conexion == null) {

            System.out.println("Error al conectar con Oracle");
            return;

        }


        while (opc != 0) {

            System.out.println("===== Menu Oracle =====");
            System.out.println("1. Reinstalar Tablas");
            System.out.println("2. Listar Tablas");
            System.out.println("3. Añadir nuevo pedido");
            System.out.println("4. Añadir nuevo cliente");
            System.out.println("5. Añadir nuevo producto");
            System.out.println("6. Modificar precio producto");
            System.out.println("7. Buscar producto por Proveedor_ID");
            System.out.println("8. Eliminar un artículo (si no está en pedidos)");
            System.out.println("9. Actualizar salario de empleado por ID_Empleado");
            System.out.println("0. Volver al menú");

            switch (opc = sc.nextInt()) {

                case 1:

                    Oracle.crearTablas(conexion);
                    break;

                case 2:

                    listarTablas(conexion);
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 9:
                    break;

                case 0:
                    System.out.println("Salir");
                    Oracle.desconectar(conexion);
                    return;

                default:
                    System.out.println("Error, selecciona un número válido");
            }
        }


    }

    private static void listarTablas(Connection conexion) {

        Scanner sc = new Scanner(System.in);

        int opc = 0;

        if (conexion == null) {

            System.out.println("Error al conectar con Oracle");
            return;

        }

        while (opc != 7) {
            System.out.println("===== Menu Oracle =====");
            System.out.println("1. Tiendas");
            System.out.println("2. Empleados");
            System.out.println("3. Clientes");
            System.out.println("4. Proveedores");
            System.out.println("5. Pedidos");
            System.out.println("6. Productos");
            System.out.println("7. Salir");


            switch (opc = sc.nextInt()) {

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
}