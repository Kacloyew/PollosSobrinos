package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==============================");
        int opc = 0;

        System.out.println("Seleciona una base de datos: \n1. mySQL \n2. Oracle \n3. Salir del programa");


        while(opc !=3) {

            switch (opc = sc.nextInt()){

                case 1:
                    MenuMySQL.mostrarMenu();
                    break;

                case 2:
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