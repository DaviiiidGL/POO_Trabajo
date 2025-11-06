package utils;

import java.util.Scanner;

public class IO {
    private static final Scanner scanner = new Scanner(System.in);

    public static void print(String mensaje) {
        System.out.print(mensaje);
    }

    public static void println(String mensaje) {
        System.out.println(mensaje);
    }

    public static String readln() {
        return scanner.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}