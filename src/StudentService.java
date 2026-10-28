import java.io.IOException;
import java.util.HashMap;

import util.InvalidMarksException;

public class StudentService {
    private final StudentStorage storage;
    private int ID;

    private HashMap<Integer, Student> studentMap;

    public StudentService(StudentStorage storage) throws IOException{
        this.storage = storage;
        studentMap = new HashMap<>(storage.getPreviousRecords());
        ID = storage.getLastId() + 1;
    }

    // If failed to add data | return false
    public boolean addRecord(int id, String name, String course, double marksOfSub1, double marksOfSub2,
            double marksOfSub3) throws InvalidMarksException,IOException {

        Student student = new Student(id, name, course, marksOfSub1, marksOfSub2, marksOfSub3);
        studentMap.put(ID, student);
        if (studentMap.containsKey(ID))
            return storage.saveCurrentRecord(student);
        return false;
    }

    public void searchStudent(String name) {
        boolean idFound = false;
        for (Student student : studentMap.values()) {
            System.out.println("Search Result for Student with Name : " + name);
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

    public boolean updateStudentDetails(int id, double marks, String option)
            throws NumberFormatException, InvalidMarksException,IOException {
        double oldMark;
        boolean isSame = false;
        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            if (option.equalsIgnoreCase("1")) {
                oldMark = student.getMarksOfSub1();
                student.setMarksOfSub1(marks);
                isSame = oldMark != student.getMarksOfSub1();
            } else if (option.equalsIgnoreCase("2")) {
                oldMark = student.getMarksOfSub2();
                student.setMarksOfSub1(marks);
                isSame = oldMark != student.getMarksOfSub2();
            } else if (option.equalsIgnoreCase("3")) {
                oldMark = student.getMarksOfSub3();
                student.setMarksOfSub1(marks);
                isSame = oldMark != student.getMarksOfSub3();
            }
        } else
            System.out.println("Id not Match");
        if (isSame)
            storage.updateRecordsAccodingToMap(studentMap);
        return isSame;
    }

    public boolean updateStudentDetails(int id, String field) throws NumberFormatException, InvalidMarksException,IOException {
        boolean isSame = false;
        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            String oldCourse = student.getCourse();
            student.setCourse(field);
            isSame = oldCourse.equalsIgnoreCase(student.getCourse());
        } else
            System.out.println("Id not Match");
        if (isSame)
            storage.updateRecordsAccodingToMap(studentMap);
        return isSame;
    }

    public boolean deleteStudent(int id) {
        if (studentMap.containsKey(id))
            studentMap.remove(id);
        return studentMap.containsKey(id);
    }

}
