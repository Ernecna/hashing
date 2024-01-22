import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
    private List<Student> studentList;

    public StudentDatabase() {
        studentList = new ArrayList<>();
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void insertStudent(Student student) {
        studentList.add(student);
    }

    public Student findStudent(int id) {
        for (Student student : studentList) {
            if (student.getID() == id) {
                return student;
            }
        }
        return null;  // Eğer ID bulunamazsa null döndür
    }

    public void deleteStudent(int id) {
        studentList.removeIf(student -> student.getID() == id);
    }
}
