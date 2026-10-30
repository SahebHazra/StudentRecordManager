package util;

import java.util.Scanner;

public class NumberValidator {
    public static int forInt(Scanner sc) {
        while (true) {
            try {
                var val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please Enter Valid Integer");
                System.out.print("Enter again : ");
            }
        }
    }

    public static double forDouble(Scanner sc) {
        while (true) {
            try {
                var val = Double.parseDouble(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please Enter Valid Number");
                System.out.print("Enter again : ");
            }
        }
    }
}
