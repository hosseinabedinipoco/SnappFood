package view;

import java.util.Scanner;

public class ScannerWrapper {

    private static Scanner scanner = new Scanner(System.in);

    public static String nextLine() {
        return scanner.nextLine();
    }
}
