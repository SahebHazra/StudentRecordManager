import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StudentStorage {
    private final String PATH = "data";
    private final String STUDENT_RECORD = "student_records.csv";
    private final String LAST_ID = "last_unique_id.txt";

    private StudentStorage() throws IOException {
        File DIR = new File(PATH);
        if (!DIR.exists() && !DIR.mkdirs())
            throw new IOException("Could not create data directory: " + DIR.getAbsolutePath());
        if (!DIR.isDirectory())
            throw new IOException("Data path is not a directory: " + DIR.getAbsolutePath());
    }

    private File createFileIfNotExist(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists() && !file.createNewFile())
            throw new IOException("Could not create file: " + file.getPath());
        return file;
    }

    public Map<Integer, Student> getPreviousRecords() throws IOException {
        Map<Integer, Student> studentMap = new HashMap<>();
        return studentMap;
    }

    public int getLastId() throws IOException {
        var id = 1;
        File file = createFileIfNotExist(LAST_ID);
        try (Scanner fileScaner = new Scanner(file)) {
            if (fileScaner.hasNextInt())
                id = fileScaner.nextInt();
        }
        return id;
    }

    public void storeId(int id) throws IOException {
        var IdToStore = Math.max(getLastId(), id);
        File file = createFileIfNotExist(LAST_ID);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(Integer.toString(IdToStore));
        }
    }

    public boolean saveCurrentRecord(Student student) throws IOException {

        return true;
    }

    public void updateRecordsAccodingToMap(Map<Integer, Student> studentMap) throws IOException {

    }
}
