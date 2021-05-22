import javax.xml.stream.events.Characters;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Semaphore;

public class StudentDriver {
    private final String filePath = "src/main/resources/students.json";
    private ConcurrentNavigableMap<Integer, Student> studentsById;
    private NavigableMap<String, Student> studentsByName;
    private NavigableMap<String, Student> studentsBySurname;
    private NavigableMap<String, Student> studentsByFathername;

    private final Comparator<String> allowDuplicates =
            (str1, str2) -> str1.equals(str2) ? -1 : str1.compareTo(str2);

    Semaphore change = new Semaphore(0);

    StudentDriver(){
        studentsById = new ConcurrentSkipListMap<>();
        studentsByName = new TreeMap<>(allowDuplicates);
        studentsBySurname = new TreeMap<>(allowDuplicates);
        studentsByFathername = new TreeMap<>(allowDuplicates);
    }

    public void start() throws IOException {
        // Read file from memory to students map.
        ConcurrentNavigableMap<Integer, Student> map = Utilities.jsonToMap(filePath);
        if(map != null){
            this.studentsById = map;
            Student.setCounter(map.lastEntry().getValue().getId() + 1);

            map.values().forEach((s) -> {
                studentsByName.put(s.getName().toLowerCase(),s);
                studentsBySurname.put(s.getSurname().toLowerCase(),s);
                studentsByFathername.put(s.getFatherName().toLowerCase(),s);
            });
        }

        // Helper thread, writes contents of students map for every change automatically.
        new Thread(() -> {
            while(true){
                try {
                    if(change.drainPermits() != 0){
                        Utilities.mapToJson(filePath, this.studentsById);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addStudent(){
        String userInput;
        while(true){
            System.out.println("Adding a new student: \n" + (" ").repeat(50));
            System.out.println("Enter the name of the student(Max 20 Characters), \n" +
                    "or [B] to go back.");
            userInput = Utilities.promptForName();
            if(userInput.equals("b")) return;
            String name = userInput;

            System.out.println("Enter the last name of the student(Max 20 Characters): \n" +
                    "or [B] to go back.");
            userInput = Utilities.promptForName();
            if(userInput.equals("b")) return;
            String lastName = userInput;

            System.out.println("Enter the father name of the student(Max 20 Characters): \n" +
                    "or [B] to go back.");
            userInput = Utilities.promptForName();
            if(userInput.equals("b")) return;
            String fatherName = userInput;

            System.out.println("Enter the email of the student(Max 20 Characters): \n" +
                    "or [B] to go back.");
            userInput = Utilities.promptForEmail();
            if(userInput.equals("b")) return;
            String email = userInput;

            System.out.println("Enter the phone number of the student(Max 20 Characters): \n" +
                    "or [B] to go back.");
            userInput = Utilities.promptForNumber();
            if(userInput.equals("b")) return;
            String number = userInput;

            Student newStudent = new Student(name, lastName, fatherName, email, number);
            studentsById.put(newStudent.getId(), newStudent);
            new Thread(() -> this.studentsByName.put(name, newStudent)).start();
            new Thread(() -> this.studentsBySurname.put(lastName, newStudent)).start();
            new Thread(() -> this.studentsByFathername.put(fatherName, newStudent)).start();
            // Signal helper thread that map has changed.
            change.release();

            System.out.println("-".repeat(50));
            System.out.printf("Student [%s] has been added successfully.\n", name);
            System.out.println("-".repeat(50));
            System.out.println();
        }

    }

    public void updateStudent(){
        Scanner scanner = new Scanner(System.in);
        String input;
        String updateMenu = "What would you like to update? \n" +
                ("-").repeat(50) + "\n" +
                "[1] Name. \n" +
                "[2] Surname. \n" +
                "[3] Father Name, \n" +
                "[4] Email. \n" +
                "[5] Phone number. \n" +
                "[B] Back. \n" +
                "Choice: ";

        while(true){
            System.out.println("Please enter student id to update: ");
            int studentId = getStudentIdFromUser();
            if(studentId == -1){
                return;
            }
            if(studentId == -2){
                continue;
            }

            Student studentToUpdate = studentsById.get(studentId);

            System.out.print(updateMenu);
            input = scanner.nextLine();
            switch (input.toLowerCase()){
                case("1"):{
                    input = Utilities.promptForName();
                    if(input.equals("b")) continue;

                    String oldName = studentToUpdate.getName();
                    studentToUpdate.setName(input);
                    change.release();
                    System.out.printf("Updated student name from [%s] to [%s], \nfor student with id [%d].\n\n",
                            oldName, input, studentId);
                    break;
                }
                case("2"):{
                    input = Utilities.promptForName();
                    if(input.equals("b")) continue;

                    String oldSurname = studentToUpdate.getSurname();
                    studentToUpdate.setSurname(input);
                    change.release();
                    System.out.printf("Updated student surname from [%s] to [%s], \nfor student with id [%d],\n\n",
                            oldSurname, input, studentId);
                    break;
                }
                case("3"):{
                    input = Utilities.promptForName();
                    if(input.equals("b")) continue;

                    String oldFatherName = studentToUpdate.getFatherName();
                    studentToUpdate.setFatherName(input);
                    change.release();
                    System.out.printf("Updated student father name from [%s] to [%s], \nfor student with id [%d],\n\n",
                            oldFatherName, input, studentId);
                    break;
                }
                case("4"):{
                    input = Utilities.promptForEmail();
                    if(input.equals("b")) continue;

                    String oldEmail = studentToUpdate.getEmail();
                    studentToUpdate.setEmail(input);
                    change.release();
                    System.out.printf("Updated student email from [%s] to [%s], \nfor student with id [%d],\n\n",
                            oldEmail, input, studentId);
                    break;
                }
                case("5"):{
                    input = Utilities.promptForNumber();
                    if(input.equals("b")) continue;

                    String oldNumber = studentToUpdate.getPhoneNumber();
                    studentToUpdate.setPhoneNumber(input);
                    change.release();
                    System.out.printf("Updated student phone number from [%s] to [%s], \nfor student with id [%d],\n\n",
                            oldNumber, input, studentId);
                    break;
                }
                case("b"):{
                    continue;
                }
                default:
                    System.out.println("Please enter a valid option: [1] - [5], or [B] to go back.\n");
            }
        }
    }

    public void deleteStudent(){
        while(true){
            System.out.println("Please enter student id to delete: ");
            int studentId = getStudentIdFromUser();
            if(studentId == -1){
                return;
            }
            if(studentId == -2){
                continue;
            }
            Student toDelete = studentsById.get(studentId);
            if(toDelete == null){
                System.out.printf("Student with id [%d] does not exist. \n\n", studentId);
                continue;
            }
            studentsById.remove(studentId);
            new Thread(() -> this.studentsByName
                    .entrySet()
                    .removeIf(entry -> entry.getValue().getId() == studentId))
                    .start();

            new Thread(() -> this.studentsBySurname
                    .entrySet()
                    .removeIf(entry -> entry.getValue().getId() == studentId))
                    .start();

            new Thread(() -> this.studentsByFathername
                    .entrySet()
                    .removeIf(entry -> entry.getValue().getId() == studentId))
                    .start();

            change.release();
            System.out.println("-".repeat(50));
            System.out.printf("Student with id [%d] has been deleted successfully. \n", studentId);
            System.out.println("-".repeat(50));
        }
    }

    public static void printStudentHeader(){
        System.out.printf("|%-5s|%-20s|%-20s|%-20s|%-25s|%-15s| \n",
                "ID", "NAME", "SURNAME", "FATHERNAME", "EMAIL", "PHONE NUMBER");
        System.out.println("-".repeat(105));
    }

    public static void printStudent(Student s){
        System.out.printf("|%-5s|%-20s|%-20s|%-20s|%-25s|%-15s|\n",
                s.getId(), Utilities.capitalizeString(s.getName())
                , Utilities.capitalizeString(s.getSurname())
                , Utilities.capitalizeString(s.getFatherName())
                , s.getEmail(), s.getPhoneNumber());
    }

    private int getStudentIdFromUser(){
        String input = Utilities.promptForId();
        if(input.equals("b")){
            return -1;
        }
        int studentId = Integer.parseInt(input);
        Student toUpdate = studentsById.get(studentId);
        if(toUpdate == null){
            System.out.printf("Student with id [%d] does not exist. \n\n", studentId);
            return -2;
        }
        return studentId;
    }

    public void searchBy(String type){
        String input;
        System.out.println("-".repeat(50));
        switch (type){
            case("1"):{
                System.out.println("     Searching by name.");
                System.out.println("-".repeat(50));
                System.out.println("Please enter the first name of student. ");
                input = Utilities.promptForName();
                System.out.println(input);
                if(input.equals("b")) return;
                printStudentHeader();
                studentsByName
                        .subMap(input, true, input + Character.MAX_VALUE, true)
                        .values().forEach(StudentDriver::printStudent);
                System.out.println();
                break;
            }
            case("2"):{
                System.out.println("     Searching by surname.");
                System.out.println("-".repeat(50));
                System.out.println("Please enter the last name of student. ");
                input = Utilities.promptForName();
                if(input.equals("b")) return;
                printStudentHeader();
                studentsBySurname
                        .subMap(input, true, input + Character.MAX_VALUE, true)
                        .values().forEach(StudentDriver::printStudent);
                System.out.println();
                break;
            }
            case("3"):{
                System.out.println("     Searching by father name.");
                System.out.println("-".repeat(50));
                System.out.println("Please enter the father name of student. ");
                input = Utilities.promptForName();
                if(input.equals("b")) return;
                printStudentHeader();
                studentsByFathername
                        .subMap(input, true, input + Character.MAX_VALUE, true)
                        .values().forEach(StudentDriver::printStudent);
                System.out.println();
                break;
            }
        }
    }
}
