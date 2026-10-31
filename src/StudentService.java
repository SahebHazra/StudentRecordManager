import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import util.InvalidInputException;
import util.InvalidMarksException;

public class StudentService {
    private final StudentStorage storage;
    private int ID;

    private HashMap<Integer, Student> studentMap;

    public StudentService(StudentStorage storage) throws IOException, InvalidInputException {
        this.storage = storage;
        studentMap = new HashMap<>(storage.getPreviousRecords());

        Iterator<Integer> IdFromMap = studentMap.keySet().iterator();

        if (IdFromMap.hasNext()) {
            var newId = IdFromMap.next();
            while (IdFromMap.hasNext()) {
                int i = IdFromMap.next();
                if (newId < i)
                    newId = i;
            }
            ID = newId + 1;
        } else
            ID = 101;

        // ID = storage.getLastId() + 1;
    }

    // If failed to add data | return false
    public boolean addRecord(String name, String course, double marksOfSub1, double marksOfSub2,
            double marksOfSub3) throws InvalidInputException, IOException {

        Student student = new Student(ID, name, course, marksOfSub1, marksOfSub2, marksOfSub3);
        studentMap.put(ID, student);
        if (studentMap.containsKey(ID)) {
            if (storage.saveCurrentRecord(student)) {
                ID++;
                return true;
            } else {
                studentMap.remove(ID);
            }
        }
        return false;
    }

    public Collection<Student> getAllStudentRecords() {
        return studentMap.values();
    }

    public void searchStudent(String name) {
        boolean idFound = false;
        System.out.println("Search Result for Student with Name : " + name);
        for (Student student : studentMap.values()) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println(student);
                idFound = true;
            }
        }
        if (!idFound)
            System.out.println("No Record Found for Name : " + name);
    }

    public void searchStudent(int id) {
        if (studentMap.containsKey(id)) {
            System.out.println("ID   Name  Course  Marks-1  Marks-2  Marks-3");
            System.out.println(studentMap.get(id));
        } else
            System.out.println("Id not Match");
    }

    public boolean updateStudentDetails(int id, double marks, int forSubjectIndex)
            throws InvalidMarksException, IOException {

        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            boolean isSame = false;
            double oldMark;
            if (forSubjectIndex == 1) {
                oldMark = student.getMarksOfSub1();
                student.setMarksOfSub1(marks);
                isSame = oldMark == student.getMarksOfSub1();
            } else if (forSubjectIndex == 2) {
                oldMark = student.getMarksOfSub2();
                student.setMarksOfSub2(marks);
                isSame = oldMark == student.getMarksOfSub2();
            } else if (forSubjectIndex == 3) {
                oldMark = student.getMarksOfSub3();
                student.setMarksOfSub3(marks);
                isSame = oldMark == student.getMarksOfSub3();
            }
            if (!isSame) {
                storage.updateRecordsAccodingToMap(studentMap.values());
                return true;
            }
        } else
            System.out.println("Id not Match");

        return false;
    }

    public boolean updateStudentDetails(int id, String newCourse) throws IOException, InvalidInputException {
        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            String oldCourse = student.getCourse();
            student.setCourse(newCourse);
            if (!oldCourse.equalsIgnoreCase(student.getCourse())) {
                System.out.println("here");
                storage.updateRecordsAccodingToMap(studentMap.values());
                return true;
            }
        } else
            System.out.println("Id not Match");

        return false;
    }

    public boolean deleteStudent(int id) throws IOException {
        if (studentMap.containsKey(id)) {
            studentMap.remove(id);
            if (!studentMap.containsKey(id)) {
                storage.updateRecordsAccodingToMap(studentMap.values());
                return true;
            }
        }
        System.out.println("ID not match!");
        return false;
    }

    private String marksToGrade(double marks) {
        if (marks <= 100 && marks >= 90) {
            return "O";
        } else if (marks >= 80) {
            return "A+";
        } else if (marks >= 70) {
            return "A";
        } else if (marks >= 60) {
            return "B";
        } else if (marks >= 50) {
            return "C";
        } else if (marks >= 40) {
            return "D";
        } else if (marks < 40 && marks >= 0) {
            return "F";
        }
        return "INVALID Marks";
    }

    public void showGradeforStudent(int id) {
        if (!studentMap.containsKey(id)) {
            System.out.println("ID NOT MATCH!");
            return;
        } else {
            Student student = studentMap.get(id);
            Double marks1 = student.getMarksOfSub1();
            Double marks2 = student.getMarksOfSub2();
            Double marks3 = student.getMarksOfSub3();
            Double total = marks1 + marks2 + marks3;
            Double average = total / 3.0;
            boolean isFail = false;

            Double[] marksArr = { average, marks1, marks2, marks3 };
            String grade;

            System.out.println("ID : " + id + "Name : " + student.getName() + "  -  Course : " + student.getCourse());
            System.out.print("Total Marks : " + total);

            for (int i = 0; i < marksArr.length; i++) {
                double marks = marksArr[i];
                grade = marksToGrade(marks);
                if (grade.equalsIgnoreCase("INVALID Marks")) {
                    System.out.println(grade);
                    return;
                }

                if (grade.equalsIgnoreCase("F")) {
                    isFail = true;
                }

                if (i == 0) {
                    System.out.print(", Average Marks : ");
                } else {
                    System.out.print("Marks in Subject_" + i + " : ");
                }
                System.out.println(marks + ", Grade : " + grade);
            }

            if (isFail)
                System.out.println("Status : FAILED\n");
            else
                System.out.println("Status : PASSED\n");
        }
    }

    public void showGradeBreackDownOfAllStudent() {
        System.out.println("All Students Grade Info : ");
        studentMap.keySet().stream().forEach(id -> showGradeforStudent(id));
    }
}
