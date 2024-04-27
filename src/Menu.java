import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            try {
                System.out.println("=== Westminster Shopping Manager Menu ===");
                System.out.println("1. Add a new product");
                System.out.println("2. Delete a product");
                System.out.println("3. Print product list");
                System.out.println("4. Save products to file");
                System.out.println("5. Read products from file");
                System.out.println("6. Open the GUI");
                System.out.println("0. Exit");

                System.out.print("Enter your choice: ");

                // Check if the next input is an integer
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            shoppingManager.addProduct(scanner);
                            break;
                        case 2:
                            shoppingManager.deleteProduct(scanner);
                            break;
                        case 3:
                            shoppingManager.printProductList();
                            break;
                        case 4:
                            shoppingManager.saveFile(scanner);
                            break;
                        case 5:
                            shoppingManager.readFile(scanner);
                            break;
                        case 6:
                            loginAndOpenGUI(scanner, shoppingManager);
                            break;
                        case 0:
                            System.out.println("Exiting Westminster Shopping Manager. Goodbye!");
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                            break;
                    }
                } else {
                    // Consume the non-integer input and display an error message
                    System.out.println("Invalid input. Please enter a valid integer option.");
                    scanner.nextLine(); // Consume the invalid input
                    choice = -1; // Set to an invalid value to continue the loop
                }

            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
                scanner.nextLine(); // Consume the invalid input
                choice = -1; // Set to an invalid value to continue the loop
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void loginAndOpenGUI(Scanner scanner, WestminsterShoppingManager shoppingManager) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        System.out.println("Logging in..."); // You can perform actual login logic here

        // Open the GUI regardless of the username and password
        shoppingManager.openGUI();
    }
}

