import java.io.IOException;
import java.util.Scanner;

import util.InvalidInputException;
import util.InvalidMarksException;
import util.NumberValidator;

class Menu {
    public static void showMenu() {
        System.out.println("1: Add student       ");
        System.out.println("2: View All students ");
        System.out.println("3: Search student    ");
        System.out.println("4: Update student    ");
        System.out.println("5: Delete student    ");
        System.out.println("6: Marks BrakeDown for Student ID");
        System.out.println("7: Show All Student's Marks Grade");
        System.out.println("8: Exit              ");
    }
}

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static boolean isEnd;

    public static void main() {

        StudentService service = null;
        try {
            service = new StudentService(new StudentStorage());
        } catch (Exception e) {
            System.out.println("Failed to Load Record");
            e.printStackTrace();
        }

        while (!isEnd) {
            try {
                Menu.showMenu();
                System.out.print("Choose : ");
                runMenu(sc.nextLine().trim(), service);
                System.out.println();
            } catch (Exception e) {
                System.out.println(e);
                // e.printStackTrace();
            }
        }

    }

    public static String getInput(String message) {
        System.out.print(message);
        String str = sc.nextLine();
        return str;
    }

    public static void runMenu(String option, StudentService service)
            throws InvalidInputException, IOException, NumberFormatException {

        switch (option) {
            case "1" -> {
                // Add student
                System.out.println("Enter details to add a New Student");

                String name = getInput("Enter Name : ");
                String course = getInput("Enter Course : ");
                try {
                    System.out.print("Enter marks of sub-1 : ");
                    Double marks_1 = NumberValidator.forDouble(sc);
                    System.out.print("Enter marks of sub-2 : ");
                    Double marks_2 = NumberValidator.forDouble(sc);
                    System.out.print("Enter marks of sub-3 : ");
                    Double marks_3 = NumberValidator.forDouble(sc);

                    if (service.addRecord(name, course, marks_1, marks_2, marks_3))
                        System.out.println("Student Added ");
                    else
                        System.out.println("Failed to Add");
                } catch (NumberFormatException e) {
                    throw new InvalidMarksException("Please ener valid marks");
                }
            }

            case "2" -> {
                // Show all student
                System.out.println("  ID     Name   Course  Marks_1  Marks_2  Marks_3");
                for (Student student : service.getAllStudentRecords()) {
                    System.out.println(student);
                }
            }

            case "3" -> {
                // Search student
                System.out.println();
                String optionForSearch = getInput("1: Search by ID (recomended) \n2: Search by Name \nChoose : ");

                switch (optionForSearch) {
                    case "1" -> {
                        System.out.print("Enter id : ");
                        int id = NumberValidator.forInt(sc);
                        service.searchStudent(id);
                    }
                    case "2" -> service.searchStudent(getInput("Enter Name : "));

                    default -> System.out.println("Invalid Option");
                }
            }

            case "4" -> {
                // Update student
                System.out.println("1: Change Course ");
                System.out.println("2: Marks of Subject-1 \n3: Marks of Subject-2");
                String optionForUpdate = getInput("4: Marks of Subject-3 \nChoose : ");

                if (optionForUpdate.equals("1")) {
                    System.out.print("Enter ID : ");
                    int id = NumberValidator.forInt(sc);
                    if (service.updateStudentDetails(id, getInput("Enter Course Name : ")))
                        System.out.println("Updated Sucessfully");
                    else
                        System.out.println("Failed to update");
                } else {

                    if (optionForUpdate.equals("2") || optionForUpdate.equals("3") || optionForUpdate.equals("4")) {
                        System.out.print("Enter ID : ");
                        int id = NumberValidator.forInt(sc);
                        System.out.println("Enter Marks: ");
                        double marks = NumberValidator.forDouble(sc);
                        if (service.updateStudentDetails(id, marks, Integer.parseInt(optionForUpdate) - 1))
                            System.out.println("Updated Sucessfully");
                        else
                            System.out.println("Failed to update");
                    } else
                        System.out.println("Invalid Option!");
                }
            }
            case "5" -> {
                // Delete student
                System.out.print("Enter Id : ");
                if (service.deleteStudent(NumberValidator.forInt(sc)))
                    System.out.println("Deleted Sucessfully");
                else
                    System.out.println("Failed to deleted");
            }
            case "6" -> {
                // Marks BrakeDown with greade
                System.out.print("Enter Id :");
                int id = NumberValidator.forInt(sc);
                service.showGradeforStudent(id);
            }
            case "7" -> service.showGradeBreackDownOfAllStudent();

            case "8" -> {
                System.out.println("See you next time!");
                isEnd = true;
            }
            default -> System.out.println("Invalid input, please enter between [1-7]");
        }

    }

}