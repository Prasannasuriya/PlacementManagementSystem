import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PlacementService ps = new PlacementService();

        while (true) {

            System.out.println("\n===== STUDENT PLACEMENT MANAGEMENT SYSTEM =====");

            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Add Company");
            System.out.println("4. Check Eligibility");
            System.out.println("5. Update Placement Status");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    ps.addStudent();
                    break;

                case 2:
                    ps.viewStudents();
                    break;

                case 3:
                    ps.addCompany();
                    break;

                case 4:
                    ps.checkEligibility();
                    break;

                case 5:
                    ps.updatePlacementStatus();
                    break;

                case 6:
                    System.out.println("Thank You");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}