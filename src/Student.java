import util.*;

public class Student {
    private int ID;
    private String name;
    private String course;
    private double marksOfSub1;
    private double marksOfSub2;
    private double marksOfSub3;

    public Student(int ID, String name, String course, Double marksOfSub1, Double marksOfSub2, Double marksOfSub3)
            throws InvalidInputException {
        setID(ID);
        setName(name);
        setCourse(course);
        setMarksOfSub1(marksOfSub1);
        setMarksOfSub2(marksOfSub2);
        setMarksOfSub3(marksOfSub3);
    }

    private void setID(int ID) throws InvalidInputException {
        if (ID <= 0)
            throw new InvalidInputException("ID should valid positive");
        this.ID = ID;
    }

    // private checking method
    private static String checkStr(String input) throws InvalidInputException {
        if (input == null || input.contains("null") || input.isBlank())
            throw new InvalidInputException("Invalid Input for \'" + input + "\'");
        return input;
    }

    private static Double checkMarks(Double marks) throws InvalidMarksException {
        if (marks.isNaN() || marks.isInfinite() || marks < 0 || marks > 100)
            throw new InvalidMarksException("Marks should in between (0-100)");
        return marks;
    }

    // seters
    public void setName(String name) throws InvalidInputException {
        this.name = checkStr(name);
    }

    public void setCourse(String course) throws InvalidInputException {
        this.course = checkStr(course);
    }

    public void setMarksOfSub1(Double marksOfSub1) throws InvalidMarksException {
        this.marksOfSub1 = checkMarks(marksOfSub1);
    }

    public void setMarksOfSub2(Double marksOfSub2) throws InvalidMarksException {
        this.marksOfSub2 = checkMarks(marksOfSub2);
    }

    public void setMarksOfSub3(Double marksOfSub3) throws InvalidMarksException {
        this.marksOfSub3 = checkMarks(marksOfSub3);
    }

    // geters
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public Double getMarksOfSub1() {
        return marksOfSub1;
    }

    public Double getMarksOfSub2() {
        return marksOfSub2;
    }

    public Double getMarksOfSub3() {
        return marksOfSub3;
    }

    @Override
    public String toString() {
        return "[ " + ID + " ] - " + name + "   " + course + "     " + marksOfSub1 + "     "
                + marksOfSub2 + "    " + marksOfSub3;
    }
}
