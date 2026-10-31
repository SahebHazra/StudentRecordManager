package util;

import java.util.Scanner;

public class NumberValidator {
    public static int forInt(Scanner sc) {

        for (int i = 1; i <= 3; i++) {
            try {
                var val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please Enter Valid Integer, try (" + (i + 1) + "/" + 3 + ")");
                System.out.print("Enter again : ");
            }
        }
        throw new NumberFormatException("Invalid Input");
    }

    public static double forDouble(Scanner sc) {
        for (int i = 1; i <= 3; i++) {
            try {
                var val = Double.parseDouble(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please Enter Valid Number, try (" + (i+1) + "/" + 3 + ")");
                System.out.print("Enter again : ");
            }
        }
        throw new NumberFormatException("Invalid Input");
    }
}
