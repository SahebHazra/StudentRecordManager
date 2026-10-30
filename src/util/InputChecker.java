package util;

public class InputChecker {

    public static String stringChecker(String input) throws InvalidInputException {
        if (input == null || input.contains("null") || input.isBlank())
            throw new InvalidInputException("Invalid Input for \'" + input + "\'");
        return input;
    }

    public static Double marksChecker(Double marks) throws InvalidMarksException {
        if (marks.isNaN() || marks.isInfinite() || marks < 0)
            throw new InvalidMarksException("Marks should valid positive");
        else if (marks > 100)
            throw new InvalidMarksException("Marks should be lower then 100");

        return marks;
    }
}
