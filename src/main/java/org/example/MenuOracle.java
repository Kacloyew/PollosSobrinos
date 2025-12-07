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

        while (opc != 8) {
            System.out.println("===== Menu Oracle =====");
            System.out.println("1. Lista Tiendas");
            System.out.println("2. Lista Empleados");
            System.out.println("3. Lista Clientes");
            System.out.println("4. Lista Proveedores");
            System.out.println("5. Lista pedidos");
            System.out.println("6.  ");
            System.out.println("7. ");
            System.out.println("8. Salir");

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

                case 8:
                    break;

                default:
                    System.out.println("Error, selecciona un número válido");
            }
        }
    }
}
