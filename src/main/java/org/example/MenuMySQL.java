package org.example;

import java.sql.Connection;
import java.util.Scanner;

public class MenuMySQL {

    public static void mostrarMenu() {

        Scanner sc = new Scanner(System.in);

        int opc = 0;

        Connection conexion = mySQL.conectar();

        if (conexion == null) {

            System.out.println("Error al conectar con MySQL");
            return;

        }

        while (opc !=7) {

            System.out.println("===== Menu MySQL =====");
            System.out.println("1. Reinstalar Tablas");
            System.out.println("2. Listar Tablas");
            System.out.println("3. Hacer nuevo pedido");
            System.out.println("4. Añadir nuevo producto");
            System.out.println("5. Modificar precio producto");
            System.out.println("6. Eliminar un artículo (si no está en pedidos)");
            System.out.println("7. Volver al menú");


            switch (opc = sc.nextInt()) {

                case 1:

                    mySQL.crearTablas(conexion);
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
                    System.out.println("Salir");
                    mySQL.desconectar(conexion);
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

            switch (opc = sc.nextInt()) {

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

}
