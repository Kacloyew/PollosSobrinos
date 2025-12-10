package org.example;

import java.sql.Connection;
import java.util.Scanner;

public class MenuOracle {

    public static void mostrarMenu() {

        Scanner sc = new Scanner(System.in);

        int opc = 0;

        Connection conectar = Oracle.conectar();

        if (conectar == null) {
            System.out.println("Error al conectar con Oracle");
            return;
        }

        while (opc !=8) {
            System.out.println("===== Menu Oracle =====");
            System.out.println("1. Reinstalar Tablas");
            System.out.println("2. Listar Tablas");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("5. ");
            System.out.println("6.  ");
            System.out.println("7. ");
            System.out.println("8. Volver al menú principal");

            switch (opc = sc.nextInt()) {

                case 1:

                    Oracle.crearTablas();
                    break;

                case 2:

                    listarTablas();
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
                    System.out.println("Salir");
                    return;


                default:
                    System.out.println("Error, selecciona un número válido");
            }
        }


    }

    private static void listarTablas() {

        Scanner sc = new Scanner(System.in);

        int opc = 0;

        Connection conectar = mySQL.conectar();

        if(conectar == null) {
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

            switch (opc = sc.nextInt()) {

                case 1:
                    break;

                case 2:
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

                default:
                    System.out.println("Error, selecciona un número válido");
            }
        }

    }

}
