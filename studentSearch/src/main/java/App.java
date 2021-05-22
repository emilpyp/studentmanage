import java.io.IOException;
import java.util.Scanner;

public class App {
    private StudentDriver driver = new StudentDriver();
    private Scanner scanner = new Scanner(System.in);
    String input = "";

    private static App app = null;
    public App() throws IOException {}

    public static App app() throws IOException {
        if (app == null){
            app = new App();
        }
        return app;
    }
    public void start() throws IOException, InterruptedException {
        // Read file from memory to hashmap
        driver.start();
        mainMenu();
    }

    public void mainMenu() throws InterruptedException {
        String mainMenu = ("-").repeat(50) + "\n" +
                "     Main menu \n" +
                ("-").repeat(50) + "\n" +
                "[1] Student operations. \n" +
                "[2] Search. \n" +
                "[Q] Quit \n" +
                "Choice: ";

        do {
            System.out.print(mainMenu);
            input = scanner.nextLine();
            switch(input.toLowerCase()){
                case("1"):{
                    studentOps();
                    break;
                }
                case("2"):{
                    searchOps();
                    break;
                }
                case("q"):{
                    Thread.sleep(2000);
                    System.exit(0);
                }
                default:
                    System.out.print("\nPlease enter a valid choice [1], [2], or [q] to exit.\n");
            }
        }
        while(true);
    }

    private void studentOps() throws InterruptedException {
        String studentMenu = ("-").repeat(50) + "\n" +
                "     Operations on students \n" +
                ("-").repeat(50) + "\n" +
                "[1] Add new student. \n" +
                "[2] Update student. \n" +
                "[3] Delete student \n" +
                "[B] Back. \n" +
                "[Q] Quit. \n" +
                "Choice: ";

        do {
            System.out.print(studentMenu);
            input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case ("1"):{
                    driver.addStudent();
                    break;
                }
                case ("2"):{
                    driver.updateStudent();
                    break;
                }
                case ("3"):{
                    driver.deleteStudent();
                    break;
                }
                case("b"):{
                    return;
                }
                case("q"):{
                    Thread.sleep(2000);
                    System.exit(0);
                }
                default:
                    System.out.println("\nPlease enter a valid choice [1], [2], [3], " +
                            "[b] to go back, [q] to exit.\n");
            }
        }
        while (true);
    }

    private void searchOps() throws InterruptedException {
        String searchMenu = ("-").repeat(50) + "\n" +
                "     Search operations \n" +
                ("-").repeat(50) + "\n" +
                "[1] Search by name. \n" +
                "[2] Search by surname. \n" +
                "[3] Search by fathername \n" +
                "[B] Back. \n" +
                "[Q] Quit. \n" +
                "Choice: ";
        do {
            System.out.print(searchMenu);
            input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case ("1"):
                case ("2"):
                case ("3"):{
                    driver.searchBy(input);
                    break;
                }
                case("b"):{
                    return;
                }
                case("q"):{
                    Thread.sleep(2000);
                    System.exit(0);
                }
                default:
                    System.out.println("\nPlease enter a valid choice [1], [2], [3], " +
                            "[b] to go back, [q] to exit.\n");
            }
        }
        while (true);
    }
}
