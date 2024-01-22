import java.util.List;
import java.util.Random;

public class StudentGenerator {
    private List<String> maleNames;
    private List<String> femaleNames;
    private List<String> surnames;
    private StudentDatabase studentDatabase;

    public StudentGenerator(List<String> maleNames, List<String> femaleNames, List<String> surnames, StudentDatabase studentDatabase) {
        this.maleNames = maleNames;
        this.femaleNames = femaleNames;
        this.surnames = surnames;
        this.studentDatabase = studentDatabase;
    }

    public void generateRandomStudents() {
        Random random = new Random();

        // Generate 81,000 random students
        for (int year = 2013; year <= 2022; year++) {
            for (int facultyID = 1; facultyID <= 9; facultyID++) {
                for (int departmentID = 1; departmentID <= 9; departmentID++) {
                    for (int studentID = 1; studentID <= 100; studentID++) {
                        // Selecting gender randomly for each student
                        boolean isMale = random.nextBoolean();
                        String name = isMale ? maleNames.get(random.nextInt(maleNames.size())) : femaleNames.get(random.nextInt(femaleNames.size()));

                        String lastName = surnames.get(random.nextInt(surnames.size()));

                        // Generating unique ID
                        int uniqueID;
                        String formattedID;
                        formattedID = String.format("%02d%02d%02d%03d", year % 100, facultyID, departmentID, studentID);


                        String department = "Department" + departmentID;
                        String faculty = "Faculty" + facultyID;

                        Student student = new Student(name, lastName, Integer.parseInt(formattedID), department, faculty);

                        // Adding student to the database
                        studentDatabase.insertStudent(student);
                    }
                }
            }
        }
    }

    public void printAllStudents() {
        for (Student student : studentDatabase.getStudentList()) {
            System.out.println("Student Details:");
            System.out.println("Name: " + student.getName());
            System.out.println("LastName: " + student.getLastName());
            System.out.println("ID: " + student.getID());
            System.out.println("Department: " + student.getDepartment());
            System.out.println("Faculty: " + student.getFaculty());
            System.out.println(); // Adds an empty line for readability
        }
    }
}
