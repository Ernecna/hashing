import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadNames {
    private  static List<String> maleNames=new ArrayList<>();
    private static List<String> femaleNames=new ArrayList<>();
    private static List<String> surnames=new ArrayList<>();

    public static List<String> getMaleNames() {
        return maleNames;
    }

    public static List<String> getFemaleNames() {
        return femaleNames;
    }

    public static List<String> getSurnames() {
        return surnames;
    }

    public static void main(String[] args) {
        maleNames = readMaleNames("malename.txt");
        femaleNames = readFemaleNames("femalename.txt");
         surnames = readLastNames("lastname.txt");

        // Test the functions
        printList("Male Names:", maleNames);
        printList("Female Names:", femaleNames);
        printList("Surnames:", surnames);
    }

    // Function to read names from a file and return them as an ArrayList
     public static List<String> readLastNames(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                surnames.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return surnames;
    }
    public static List<String> readMaleNames(String fileName) {


        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                femaleNames.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return femaleNames;
    }
    public static List<String> readFemaleNames(String fileName) {
        List<String> namesList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                maleNames.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maleNames;
    }

    // Function to print an ArrayList
    private static void printList(String title, List<String> list) {
        System.out.println(title);
        for (String element : list) {
            System.out.println(element);
        }
        System.out.println();
    }
}
