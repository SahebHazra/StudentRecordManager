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
        double oldMark;
        boolean isSame = false;
        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            if (forSubjectIndex == 1) {
                oldMark = student.getMarksOfSub1();
                student.setMarksOfSub1(marks);
                isSame = oldMark != student.getMarksOfSub1();
            } else if (forSubjectIndex == 2) {
                oldMark = student.getMarksOfSub2();
                student.setMarksOfSub1(marks);
                isSame = oldMark != student.getMarksOfSub2();
            } else if (forSubjectIndex == 3) {
                oldMark = student.getMarksOfSub3();
                student.setMarksOfSub1(marks);
                isSame = oldMark != student.getMarksOfSub3();
            }
        } else
            System.out.println("Id not Match");
        if (isSame)
            storage.updateRecordsAccodingToMap(studentMap.values());
        return isSame;
    }

    public boolean updateStudentDetails(int id, String newCourse) throws IOException, InvalidInputException {
        boolean isSame = true;
        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            String oldCourse = student.getCourse();
            student.setCourse(newCourse);
            isSame = oldCourse.equalsIgnoreCase(student.getCourse()); // diffrent returns false
        } else
            System.out.println("Id not Match");

        if (!isSame)
            storage.updateRecordsAccodingToMap(studentMap.values());

        return !isSame;
    }

    public boolean deleteStudent(int id) throws IOException {
        if (studentMap.containsKey(id))
            studentMap.remove(id);
        boolean isPresent = studentMap.containsKey(id);
        if (!isPresent)
            storage.updateRecordsAccodingToMap(studentMap.values());

        return isPresent;
    }

    public Double getMarksTotalOfStudent(int id) {
        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            double marksOfSub1 = student.getMarksOfSub1();
            double marksOfSub2 = student.getMarksOfSub2();
            double marksOfSub3 = student.getMarksOfSub3();

            System.out.println("Total");
            return marksOfSub1 + marksOfSub2 + marksOfSub3;
        }
        return Double.NaN;
    }

    public Double getMarksAverageOfStudent(int id) {
        Double total = getMarksTotalOfStudent(id);
        if (!total.isNaN())
            return total / 3;
        return total;
    }


    public void greadeChart(int id){
        //  A (80+), B(60–79.99), C (40–59.99), F (below 40).

        // Total= 270 - 300; Average = 150; perSubject=90 - 100; = O
        // Total= 240 - 267; Average = 150; perSubject=80 - 89; = A+
        // Total= 210 - 237; Average = 150; perSubject=70 - 79; = A
        // Total= 180 - 207; Average = 150; perSubject=60 - 69; = B
        // Total= 150 - 177; Average = 150; perSubject=50 - 59; = C
        // Total= 120 - 147; Average = 150; perSubject=40 - 49; = D
        // Total= < 120; Average = 150; perSubject= < 40; = F


        double total = getMarksTotalOfStudent(id);
        if (total < 40 ) {
            
        }

        

    }


}
