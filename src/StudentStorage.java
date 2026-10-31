import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import util.InvalidInputException;
import util.InvalidMarksException;

public class StudentStorage {
    private final String PATH = "data";
    private final String STUDENT_RECORD = "student_records.csv";
    private final String LAST_ID = "last_unique_id.txt";

    public StudentStorage() throws IOException {
        createPATHifNotExist(PATH);
    }

    private void createPATHifNotExist(String PATH) throws IOException {
        File DIR = new File(PATH);
        if (!DIR.exists() && !DIR.mkdirs())
            throw new IOException("Could not create data directory: " + DIR.getAbsolutePath());
        if (!DIR.isDirectory())
            throw new IOException("Data path is not a directory: " + DIR.getAbsolutePath());
    }

    private File createFileIfNotExist(String fileName) throws IOException {
        createPATHifNotExist(PATH);
        File file = new File(PATH + "/" + fileName);
        if (!file.exists() && !file.createNewFile())
            throw new IOException("Could not create file: " + file.getPath());
        return file;
    }

    private File writeHeaderIfNotPresent(String record) throws IOException {
        File file = createFileIfNotExist(record);
        try (Scanner fileScanner = new Scanner(file)) {
            if (!fileScanner.hasNextLine() || (fileScanner.hasNextLine() && fileScanner.nextLine().isBlank())) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("ID,Name,Course,Marks_1,Marks_2,Marks_3" + System.lineSeparator());
                }
            }
        }
        return file;
    }

    public Map<Integer, Student> getPreviousRecords() throws IOException, InvalidInputException {

        Map<Integer, Student> studentMap = new HashMap<>();
        try (Scanner fileScanner = new Scanner(createFileIfNotExist(STUDENT_RECORD))) {
            int lineNumber = -1;
            boolean needToRewriteTheRecord = false;

            while (fileScanner.hasNextLine()) {
                lineNumber++;
                String line = fileScanner.nextLine();
                needToRewriteTheRecord = false;
                if (lineNumber == 0 || line.isBlank())
                    continue;

                String[] parts = line.split("\\|", 6);
                if (parts.length != 6 || parts[1] == null || parts[2] == null) {
                    System.out.println("\nInvalid task record at line " + lineNumber + ".");
                    System.out.println("Skipping the problematic line!");
                    needToRewriteTheRecord = true;
                    continue;
                }

                try {
                    var id = Integer.parseInt(parts[0]);
                    var name = parts[1];
                    var course = parts[2];
                    var marks1 = Double.parseDouble(parts[3]);
                    var marks2 = Double.parseDouble(parts[4]);
                    var marks3 = Double.parseDouble(parts[5]);
                    if (marks1 > 100 || marks2 > 100 || marks3 > 100 || marks1 < 0 || marks2 < 0 || marks3 < 0)
                        throw new InvalidMarksException("Invalid Marks Entrys found at line " + lineNumber);

                    Student student = new Student(id, name, course, marks1, marks2, marks3);
                    studentMap.put(id, student);

                } catch (NumberFormatException | InvalidMarksException e) {
                    System.out.println("\nInvalid task record at line " + lineNumber + ", " + e.getMessage());
                    System.out.println("Skipping the problematic line!");
                    needToRewriteTheRecord = true;
                    continue;
                }
            }
            if (needToRewriteTheRecord)
                updateRecordsAccodingToMap(studentMap.values());
        }
        return studentMap;
    }

    public int getLastId() throws IOException {
        int id = 100;
        int newid = id;
        try (Scanner fileScaner = new Scanner(createFileIfNotExist(LAST_ID))) {
            if (fileScaner.hasNextInt())
                newid = fileScaner.nextInt();
        }
        return (newid > id) ? newid : id;
    }

    private void storeId(int newId) throws IOException {
        int oldId = getLastId();
        // if old ID is smaller and new ID is greater | only then store the new ID;
        if (oldId < newId) {
            try (FileWriter writer = new FileWriter(createFileIfNotExist(LAST_ID))) {
                writer.write(Integer.toString(newId));
            }
        }
    }

    public void saveCurrentRecord(Student student) throws IOException {

        // id, name, course, marksOfSub1, marksOfSub2, marksOfSub3
        try (FileWriter writer = new FileWriter(writeHeaderIfNotPresent(STUDENT_RECORD), true)) {
            int newID = student.getID();
            writer.write(Integer.toString(newID) + "|" + student.getName() + "|" + student.getCourse() + "|"
                    + String.valueOf(student.getMarksOfSub1()) + "|" + String.valueOf(student.getMarksOfSub2())
                    + "|" + String.valueOf(student.getMarksOfSub3()) + System.lineSeparator());

            storeId(newID);
        }
    }

    public void updateRecordsAccodingToMap(Collection<Student> studentRecords) throws IOException {

        // Write all record accoding to the provided record
        new File(PATH + "/" + STUDENT_RECORD).delete();
        try (FileWriter writer = new FileWriter(writeHeaderIfNotPresent(STUDENT_RECORD), true)) {
            int storeID = getLastId();
            for (Student student : studentRecords) {
                int newID = student.getID();
                writer.write(Integer.toString(newID) + "|" + student.getName() + "|" + student.getCourse() + "|"
                        + String.valueOf(student.getMarksOfSub1()) + "|" + String.valueOf(student.getMarksOfSub2())
                        + "|" + String.valueOf(student.getMarksOfSub3()) + System.lineSeparator());

                if (storeID < newID)
                    storeID = newID;
            }
            storeId(storeID);
        }
    }

}
