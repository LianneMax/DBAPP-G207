package G207DBAPP;
import java.util.Scanner;

public class employee_management_menu {

    public employee_management_menu() {}

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        employee_management empManager = new employee_management();

        while (true) {
            System.out.println("===============================================================");
            System.out.println("Employee Management Menu");
            System.out.println("---------------------------------------------------------------");
            System.out.println("[1] Create a new Employee");
            System.out.println("[2] Update a record of an Employee");
            System.out.println("[3] Delete a record of an Employee");
            System.out.println("[4] Resign an Employee");
            System.out.println("[5] View a record of a specific Employee and the list of customers they are handling");
            System.out.println("[0] Exit Employee Management");
            System.out.println("===============================================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter employee number: ");
                    int employeeNumber = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter extension: ");
                    String extension = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter office code: ");
                    String officeCode = scanner.nextLine();
                    System.out.print("Enter reports to: ");
                    int reportsTo = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter job title: ");
                    String jobTitle = scanner.nextLine();
                    empManager.createEmployee(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);
                    break;
                case 2:
                    System.out.print("Enter employee number to update: ");
                    employeeNumber = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.println("Choose the field to update:");
                    System.out.println("[1] Last Name");
                    System.out.println("[2] First Name");
                    System.out.println("[3] Extension");
                    System.out.println("[4] Email");
                    System.out.println("[5] Office Code");
                    System.out.println("[6] Reports To");
                    System.out.println("[7] Job Title");
                    System.out.print("Enter your choice: ");
                    int updateChoice = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    String field = null;
                    String newValue = null;

                    switch (updateChoice) {
                        case 1:
                            field = "lastName";
                            System.out.print("Enter new last name: ");
                            newValue = scanner.nextLine();
                            break;
                        case 2:
                            field = "firstName";
                            System.out.print("Enter new first name: ");
                            newValue = scanner.nextLine();
                            break;
                        case 3:
                            field = "extension";
                            System.out.print("Enter new extension: ");
                            newValue = scanner.nextLine();
                            break;
                        case 4:
                            field = "email";
                            System.out.print("Enter new email: ");
                            newValue = scanner.nextLine();
                            break;
                        case 5:
                            field = "officeCode";
                            System.out.print("Enter new office code: ");
                            newValue = scanner.nextLine();
                            break;
                        case 6:
                            field = "reportsTo";
                            System.out.print("Enter new reports to: ");
                            newValue = scanner.nextLine();
                            break;
                        case 7:
                            field = "jobTitle";
                            System.out.print("Enter new job title: ");
                            newValue = scanner.nextLine();
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose again.");
                            continue;
                    }

                    empManager.updateEmployee(employeeNumber, field, newValue);
                    break;
                case 3:
                    System.out.print("Enter employee number to delete: ");
                    employeeNumber = scanner.nextInt();
                    empManager.deleteEmployee(employeeNumber);
                    break;
                case 4:
                    System.out.print("Enter employee number to resign: ");
                    employeeNumber = scanner.nextInt();
                    empManager.resignEmployee(employeeNumber);
                    break;
                case 5:
                    System.out.print("Enter employee number to view: ");
                    employeeNumber = scanner.nextInt();
                    empManager.viewEmployee(employeeNumber);
                    break;
                case 0:
                    System.out.println("Exiting Employee Management Menu.");
                   
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
}