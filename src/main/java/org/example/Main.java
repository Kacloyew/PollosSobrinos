package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opc = 0;

        while(opc !=3) {

            System.out.println("==============================");
            System.out.println("Seleciona una base de datos: \n1. mySQL \n2. Oracle \n3. Salir del programa");

            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido");
                continue;
            }

            switch (opc){

                case 1:
                    MenuMySQL.mostrarMenu();
                    break;

                case 2:
                    MenuOracle.mostrarMenu(sc);
                    break;

                case 3:

                    break;

                default:
                    System.out.println("Error, selecione un número válido");
                    break;

            }

        }

    }

}