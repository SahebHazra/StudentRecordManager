import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import util.InvalidInputException;
import util.InvalidMarksException;

public class StudentService {
    private final StudentStorage storage;
    private int ID;

    private HashMap<Integer, Student> studentMap;

    public StudentService(StudentStorage storage) throws IOException, InvalidInputException {
        this.storage = storage;
        studentMap = new HashMap<>(storage.getPreviousRecords());
        ID = storage.getLastId() + 1;
    }

    // If failed to add data | return false
    public boolean addRecord(String name, String course, double marksOfSub1, double marksOfSub2,
            double marksOfSub3) throws InvalidInputException, IOException {

        Student student = new Student(ID, name, course, marksOfSub1, marksOfSub2, marksOfSub3);
        studentMap.put(ID, student);
        if (studentMap.containsKey(ID)) {
            storage.saveCurrentRecord(student);
            return true;
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
            throws NumberFormatException, InvalidMarksException, IOException {

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
                student.setMarksOfSub1(marks);
                isSame = oldMark == student.getMarksOfSub2();
            } else if (forSubjectIndex == 3) {
                oldMark = student.getMarksOfSub3();
                student.setMarksOfSub1(marks);
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

    private Double getTotalMarksForStudent(int id) {
        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            double marksOfSub1 = student.getMarksOfSub1();
            double marksOfSub2 = student.getMarksOfSub2();
            double marksOfSub3 = student.getMarksOfSub3();
            return marksOfSub1 + marksOfSub2 + marksOfSub3;
        }
        return Double.NaN;
    }

    private void showGradesofSubjectsMarks(int id) {
        if (!studentMap.containsKey(id)) {
            System.out.println("ID not Match!");
            return;
        }
        Student student = studentMap.get(id);
        Double[] marksArr = { student.getMarksOfSub1(), student.getMarksOfSub2(), student.getMarksOfSub3() };

        for (int i = 0; i < marksArr.length; i++) {
            double marks = marksArr[i];
            StringBuilder grade = new StringBuilder();
            if (marks < 40) {
                grade.replace(0, 1, "F");
            } else if (marks >= 40 && marks <= 49) {
                grade.replace(0, 1, "D");
            } else if (marks > 49 && marks <= 59) {
                grade.replace(0, 1, "C");
            } else if (marks > 59 && marks <= 69) {
                grade.replace(0, 1, "B");
            } else if (marks > 69 && marks <= 79) {
                grade.replace(0, 1, "A");
            } else if (marks > 79 && marks <= 90) {
                grade.replace(0, 1, "A+");
            } else if (marks > 90 && marks <= 100) {
                grade.replace(0, 1, "O");
            }
            System.out.println("Marks in Subject_" + i + 1 + " : " + marks + ", Grade : " + grade);
        }
    }

    public void showGradeforStudent(int id) {
        if (!studentMap.containsKey(id)) {
            System.out.println("ID NOT MATCH!");
            return;
        }
        Student student = studentMap.get(id);
        Double total = getTotalMarksForStudent(id);
        if (total.isNaN()) {
            System.out.println("Encounter with \'INVALID\' Informations");
            return;
        }
        StringBuilder grade = new StringBuilder();
        if (total < 120) {
            grade.replace(0, 1, "F");
        } else if (total >= 120 && total <= 147) {
            grade.replace(0, 1, "D");
        } else if (total > 147 && total <= 177) {
            grade.replace(0, 1, "C");
        } else if (total > 177 && total <= 207) {
            grade.replace(0, 1, "B");
        } else if (total > 207 && total <= 237) {
            grade.replace(0, 1, "A");
        } else if (total > 237 && total <= 267) {
            grade.replace(0, 1, "A+");
        } else if (total > 267 && total <= 300) {
            grade.replace(0, 1, "O");
        } else {
            System.out.println("Encounter with \'INVALID\' Informations");
            return;
        }
        System.out.println(studentMap.get(id).getName() + " : " + studentMap.get(id).getCourse());
        System.out.println("Total Marks : " + total + ", Average : " + total / 3 + ", Grade in Total : " + grade);
        showGradesofSubjectsMarks(id);
        System.out.println();
    }

    public void showGradeBreackDownOfAllStudent() {
        System.out.println("All Students Grade Info : ");
        studentMap.keySet().stream().forEach(id -> showGradeforStudent(id));
    }
}
