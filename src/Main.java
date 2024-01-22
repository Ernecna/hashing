    import java.util.List;
    import java.util.Random;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class Node {
        private Student student;
        private Node next;

        public Node(Student student) {
            this.student = student;
            this.next = null;
        }

        public Student getStudent() {
            return student;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class StudentLinkedList {
        private Node head;

        public Node getHead() {
            return head;
        }

        public StudentLinkedList() {
            this.head = null;
        }

        public void addStudent(Student student) {
            Node newNode = new Node(student);

            if (head == null) {
                // Eğer liste boşsa, yeni düğüm baş düğüm olur.
                head = newNode;
            } else {
                // Liste boş değilse, listenin sonuna kadar git.
                Node current = head;
                while (current.getNext() != null) {
                    current = current.getNext();
                }
                // Listenin sonundaki düğümün 'next' değerini yeni düğüme ayarla.
                current.setNext(newNode);
            }
        }

        public Student findStudentById(int id) {
            Node current = head;
            while (current != null) {
                if (current.getStudent().getID() == id) {
                    return current.getStudent();
                }
                current = current.getNext();
            }
            return null;
        }

        public void printAllStudents() {
            Node current = head;
            while (current != null) {
                System.out.println(current.getStudent().toString());
                current = current.getNext();
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class HashFunctions {
        private int m; // size of the hash table, set to a prime number
        private int a1, b1, a2, b2; // coefficients for hash functions

        public void setM(int m) {
            this.m = m;
        }

        public HashFunctions(int m) {
            this.m = m;

            // Assigning prime numbers for a1, b1, a2, b2
            a1 = 2; // prime
            b1 = 3; // prime

            a2 = 5; // prime
            b2 = 7; // prime
        }

        public int h1(int x) {
            return (((a1 * x) + b1)/1001)%m;

        }

        public int h2(int x) {
            return (((a2 * x) + b2)/1001)%m;
        }

        public int probe(int x, int i) {
            int hash1 = h1(x);
            int hash2 = h2(x);
           return  (hash1 + (hash2 * i)) % m;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ChainingHashTable {
        private StudentLinkedList[] table;
        private HashFunctions hashFunctions;
        private int tableSize;

        public ChainingHashTable(int tableSize) {
            this.tableSize = tableSize;
            this.hashFunctions = new HashFunctions(tableSize);
            this.table = new StudentLinkedList[tableSize];

            // Initialize each spot with a new StudentLinkedList
            for (int i = 0; i < tableSize; i++) {
                table[i] = new StudentLinkedList();
            }
        }


        public void insertStudent(Student student) {
            int id = student.getID();
            int index=  hashFunctions.h2(id);
            table[index].addStudent(student);
        }

        public Student searchStudent(int id) {
            int hash = hashFunctions.h2(id);
            int index = hash;
            return table[index].findStudentById(id);  // Directly finding student in the linked list at the hashed index
        }



        // Search for a student using double hashing
        public void printTable() {
            for (int i = 0; i < tableSize; i++) {
                StudentLinkedList list = table[i];
                System.out.print("Index " + i + ": ");
                Node current = list.getHead();  // Assuming head is accessible; if it's private, use a method to access it
                while (current != null) {
                    Student student = current.getStudent();
                    System.out.print(student.getID() + " -> ");
                    current = current.getNext();
                }
                System.out.println("NULL");  // End of the list at this index
            }
            System.out.println();  // Extra newline for separation after the entire table is printed
        }

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class  OpenAddressingHashTable {
        private Student[] table;
        private int tableSize;
        private HashFunctions hashFunctions;
        private int itemCount;

        public OpenAddressingHashTable(int tableSize) {
            this.tableSize = tableSize;
            this.table = new Student[tableSize];
            this.itemCount = 0;
            this.hashFunctions = new HashFunctions(tableSize);  // Initialize after setting tableSize
        }

        public Student[] getTable() {
            return table;
        }

        public void insert(Student student){
            if(itemCount == tableSize){
                resizeTable();
            }
            int i = 0;
            int index = hashFunctions.probe(student.getID(), i);
            //System.out.println("index " + index + " student " + student.getName() + " " + student.getID()+"table size"+tableSize);
            while(table[index] != null  ) {  // Check if the slot is already occupied
             //   System.out.println("index " + index + " student " + student.getName() + " " + student.getID()+"table size"+tableSize+" "+table[index].getID());
                i++;  // Increment i to probe next slot
                index = hashFunctions.probe(student.getID(), i);  // Recalculate the index with the new i value

            }
           // System.out.println("index " + index + " student " + student.getName() + " " + student.getID()+"table size"+tableSize);
            table[index] = student;  // Insert the student at the calculated index
            itemCount++;
            //System.out.println(tableSize+"+tableSize");
        }

        private void resizeTable() {
            int primedSize = nextPrime(tableSize);
            Student[] newTable = new Student[primedSize];
            System.arraycopy(table, 0, newTable, 0, tableSize);
            tableSize = primedSize;
            table = newTable;
            hashFunctions.setM(primedSize);
        }
        public static int nextPrime(int currentSize) {
            int newSize = 2 * currentSize;  // Double the current size
            while (!isPrime(newSize)) {
                newSize++;
            }
            return newSize;
        }

        // Helper function to check if a number is prime
        private static boolean isPrime(int number) {
            if (number <= 1) return false;
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }

        public void printTable() {
            for (int i = 0; i < tableSize ; i++) {
                System.out.print("Slot " + i + ": ");
                if (table[i] != null) {
                    System.out.println(table[i].getID() + " - " + table[i].getName());
                } else {
                    System.out.println("empty");
                }
            }
        }
        public Student searchStudent(int id) {
            int i = 0;
            int index = hashFunctions.probe(id, i);
            while (table[index] != null && i < tableSize) {
                if (table[index].getID() == id) {
                    // Found the student
                    return table[index];
                }
                i++;
                index = hashFunctions.probe(id, i);
            }
            // Student not found
            return null;
        }



        // ... Any additional methods required ...
    }






    public class Main {
        public static void main(String[] args) {
            // Prepare name lists by reading them
            ReadNames reader = new ReadNames();
            List<String> maleNames = reader.readMaleNames("malename.txt");
            List<String> femaleNames = reader.readFemaleNames("femalename.txt");
            List<String> surnames = reader.readLastNames("lastnames.txt");

            // Create StudentDatabase and StudentGenerator
            StudentDatabase database = new StudentDatabase();
            StudentGenerator studentGenerator = new StudentGenerator(maleNames, femaleNames, surnames, database);
            studentGenerator.generateRandomStudents();
            //studentGenerator.printAllStudents();

            // Create ChainingHashTable and insert students
            ChainingHashTable hashTable = new ChainingHashTable(4001);
            long startInsertTime = System.currentTimeMillis();
            for (Student student : database.getStudentList()) {
                hashTable.insertStudent(student);
            }
            long endInsertTime = System.currentTimeMillis();
            System.out.println("Time taken to insert students: " + (endInsertTime - startInsertTime) + " ms.");
            //hashTable.printTable();

            // Search for 100 random students
            Random random = new Random();
            long startSearchTime = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                Student student = null;
                while (student == null) {
                    int yy = random.nextInt(10) + 10; // For years between 2010 and 2019
                    int ff = random.nextInt(9) + 1;   // For faculties from 1 to 9
                    int dd = random.nextInt(9) + 1;   // For departments from 1 to 9
                    int nnn = random.nextInt(100);    // For student numbers from 000 to 999
                    String randomId = String.format("%02d%02d%02d%03d", yy, ff, dd, nnn);
                    student = hashTable.searchStudent(Integer.parseInt(randomId));
                }
                System.out.println((i+1)+".Found student in Chaning Table: " + student.getName() + " " + student.getLastName());

            }
            long endSearchTime = System.currentTimeMillis();
            System.out.println("Time taken to search for 100 students: " + (endSearchTime - startSearchTime) + " ms.");


            ////////////////////////////////////OPEN ADDRESSING/////////////////////////////////////////////
            OpenAddressingHashTable openAddressingHashTable = new OpenAddressingHashTable(401); // Start with 401 spots
            startInsertTime = System.currentTimeMillis();
            int a = 0;
            for (Student student : database.getStudentList()) {
                openAddressingHashTable.insert(student);
               /*
                if(a==5000){
                    break;
                }
                a++;
*/

            }
            endInsertTime = System.currentTimeMillis();
            //openAddressingHashTable.printTable();
            System.out.println("Time taken to insert students into the hash table: " + (endInsertTime - startInsertTime) + " ms.");
            // CHECKİNG PRİME SİZE OF THE  OPENADDRESSİNG HASHİNG TABLE.
            //System.out.println(openAddressingHashTable.getTable().length);
            long startSearchTimeOA = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                Student student = null;
                while (student == null) {
                    int yy = random.nextInt(10) + 10;  // 2010 ile 2019 arasındaki yıllar için
                    int ff = random.nextInt(9) + 1;    // 1 ile 9 arasındaki fakülteler için
                    int dd = random.nextInt(9) + 1;    // 1 ile 9 arasındaki departmanlar için
                    int nnn = random.nextInt(100);     // 000 ile 999 arasındaki öğrenci numaraları için
                    String randomId = String.format("%02d%02d%02d%03d", yy, ff, dd, nnn);
                    student = openAddressingHashTable.searchStudent(Integer.parseInt(randomId));
                }

                System.out.println((i+1)+".Found student in Open Addressing Hash Table: " + student.getName() + " " + student.getLastName());
            }

            long endSearchTimeOA = System.currentTimeMillis();
            System.out.println("Time taken to search for 100 students in Open Addressing Hash Table: " + (endSearchTimeOA - startSearchTimeOA) + " ms.");
        }
    }

