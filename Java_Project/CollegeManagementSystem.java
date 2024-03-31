import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

class Student {
    long reg;
    String name, branch;

    public void input() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter name: ");
        name = scanner.nextLine();
        System.out.println("\nEnter roll no: ");
        reg = scanner.nextLong();
        scanner.nextLine();
        System.out.println("\nEnter Branch: ");
        branch = scanner.nextLine();
    }

    public void display() {
        clearScreen();
        System.out.println("\t\tDisplay Records");
        System.out.println("\n Name - " + name);
        System.out.println(" Reg no. - " + reg);
        System.out.println(" Branch - " + branch + "\n");
        pause();
        clearScreen();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pause() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class CollegeManagementSystem{
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        int choice = 1;
        getFile();
        Scanner scanner = new Scanner(System.in);

        while (choice != 0) {
            System.out.println("\t\t\t-----------------------------------------");
            System.out.println("\t\t\t Simple College Management System");
            System.out.println("\t\t\t-----------------------------------------");
            System.out.println("\n\t\t\tEnter <1> to Add new student");
            System.out.println("\t\t\tEnter <2> to Display all student");
            System.out.println("\t\t\tEnter <3> to Remove student");
            System.out.println("\t\t\tEnter <4> to Edit student");
            System.out.println("\t\t\tEnter <5> to Search student");
            System.out.println("\t\t\tEnter <0> to Exit\n");
            System.out.print("\n\n\t\t\tEnter Your Choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    insertNew();
                    break;
                case 2:
                    show();
                    break;
                case 3:
                    deleteData();
                    break;
                case 4:
                    editData();
                    break;
                case 5:
                    searchAndShow();
                    break;
                case 0:
                    writeFile();
                    break;
                default:
                    System.out.println("\nWRONG CHOICE!!!\nTRY AGAIN");
            }
        }
    }

    static void getFile() {
        try {
            File file = new File("College.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Student student = new Student();
                student.name = scanner.nextLine();
                student.reg = Long.parseLong(scanner.nextLine());
                student.branch = scanner.nextLine();
                students.add(student);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeFile() {
        try {
            FileWriter writer = new FileWriter("College.txt");
            for (Student student : students) {
                writer.write(student.name + "\n");
                writer.write(student.reg + "\n");
                writer.write(student.branch + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void insertNew() {
        Scanner scanner = new Scanner(System.in);
        char ch = 'y';

        while (ch == 'y') {
            Student student = new Student();
            student.input();
            if (searchReg(student.reg) == -1) {
                students.add(student);
            } else {
                System.out.println("\nTHE REGISTRATION NO. ALREADY EXIST!!!\nCANNOT ADD");
            }
            System.out.print("\n Press [Y] to enter more: ");
            ch = scanner.next().charAt(0);
            scanner.nextLine();
        }
    }

    static int searchReg(long reg) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).reg == reg) {
                return i;
            }
        }
        return -1;
    }

    static void show() {
        for (Student student : students) {
            student.display();
        }
    }

    static void deleteData() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Reg. no.: ");
        long reg = scanner.nextLong();
        int index = searchReg(reg);
        if (index != -1) {
            Student student = students.get(index);
            System.out.println("\nThe following data is being deleted");
            student.display();
            students.remove(index);
        }
    }

    static void editData() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Reg. no.: ");
        long reg = scanner.nextLong();
        int index = searchReg(reg);
        if (index != -1) {
            System.out.println("\nEnter new data:");
            students.get(index).input();
        }
    }

    static void searchAndShow() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1. Press to search reg no.");
            System.out.println("2. Press to search name");
            System.out.println("3. Press to search branch");
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                System.out.print("\nEnter reg no.: ");
                long reg = scanner.nextLong();
                int index = searchReg(reg);
                if (index != -1) {
                    students.get(index).display();
                } else {
                    System.out.println("\nRecord NOT FOUND!!!");
                }
                break;
            case 2:
                System.out.print("\nEnter name: ");
                String name = scanner.next();
                searchName(name);
                break;
            case 3:
                System.out.print("\nEnter branch: ");
                String branch = scanner.next();
                searchBranch(branch);
                break;
        }
    }

    static void searchName(String name) {
        boolean found = false;
        for (Student student : students) {
            if (student.name.equals(name)) {
                student.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("\nRecord NOT FOUND!!!");
        }
    }

    static void searchBranch(String branch) {
        boolean found = false;
        for (Student student : students) {
            if (student.branch.equals(branch)) {
                student.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("\nRecord NOT FOUND!!!");
        }
    }
}
