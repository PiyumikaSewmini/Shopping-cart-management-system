import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

// WestminsterShoppingManager implements ShoppingManager interface
class WestminsterShoppingManager implements ShoppingManager {

    private final List<Product> productList; // List to store products
    private final Scanner input;// Scanner for user input
    private ShoppingCart shoppingGUI;
    public JComboBox<String> productTypeDropdown;
    public JTable table;

    public WestminsterShoppingManager() {  // Constructor initializes the product list and scanner
        this.productList = new ArrayList<>();
        this.input = new Scanner(System.in);
    }
    // Getter for the product list
    public List<Product> getProductList() {

        return productList;
    }
    // Setter for the ShoppingGUI reference
    public void setShoppingGUI(ShoppingCart shoppingGUI) {
        this.shoppingGUI = shoppingGUI;
    }

    // Getter for the ShoppingGUI reference
    public ShoppingCart getShoppingGUI() {
        return shoppingGUI;
    }

    // Notifies the GUI to update when changes occur
    public void notifyGUI(JComboBox<String> productTypeDropdown, JTable table) {
        if (shoppingGUI != null) {
            shoppingGUI.updateTable(productTypeDropdown, table, productList);
        }
    }

    public void addProduct(Scanner input) {//Method that add products
        System.out.print("Enter product ID: ");
        String product_id = input.next();

        System.out.print("Enter product name: ");
        String product_name = input.next();

        int number_of_available_items;
        while (true) {
            try {
                System.out.print("Enter number of available items: ");
                number_of_available_items = Integer.parseInt(input.next());
                break;  // Exit the loop if parsing is successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid available items.");
            }
        }

        double price_of_product;
        while (true) {
            try {
                System.out.print("Enter price: ");
                price_of_product = Double.parseDouble(input.next());
                break;  // Exit the loop if parsing is successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }

        System.out.print("Two categories :");
        String category = input.nextLine();

        System.out.println("Select product type:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Enter your choice: ");

        int productTypeChoice = input.nextInt();
        input.nextLine();

        switch (productTypeChoice) {
            case 1 ->
                    addElectronics(product_id, product_name, number_of_available_items, price_of_product, category);
            case 2 ->
                    addClothing(input, product_id, product_name, number_of_available_items, price_of_product, category);
            default -> System.out.println("Invalid choice.");
        }

        notifyGUI(productTypeDropdown, table);
    }


    // Method to add an electronics product
    private void addElectronics(String product_id, String product_name, int number_of_available_items, double price_of_product, String category) {
        System.out.print("Enter brand: ");
        String brand = this.input.nextLine();

        int warrantyPeriod;
        while (true) {
            try {
                System.out.print("Enter the warranty period (months): ");
                warrantyPeriod = Integer.parseInt(input.next());
                break;  // Exit the loop if parsing is successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid warranty period in integer.");
            }
        }
        Electronics electronics = new Electronics(product_id, product_name, number_of_available_items, price_of_product, category, brand, warrantyPeriod);
        productList.add(electronics);
        System.out.println("Electronics product added successfully.");
    }

    // Method to add a clothing product
    private void addClothing(Scanner input, String product_id, String product_name, int number_of_available_items, double price_available_items, String category) {
        System.out.print("Enter size: ");
        String size = input.nextLine();

        System.out.print("Enter color: ");
        String colour = input.nextLine();

        Clothing clothing = new Clothing(product_id, product_name, number_of_available_items, price_available_items, category, size, colour);
        productList.add(clothing);
        System.out.println("Clothing product added successfully.");
    }
    // Method to delete a product
    public void deleteProduct(Scanner input) {
        System.out.print("Enter product ID to delete: ");
        String productIdToDelete = input.next();

        Product productToDelete = null;
        for (Product product : productList) {
            if (product.getProduct_id().equals(productIdToDelete)) {
                productToDelete = product;
                break;
            }
        }

        if (productToDelete != null) {
            productList.remove(productToDelete);
            System.out.println("Product deleted: " + productToDelete.getProduct_name() + " (Type: " + productToDelete.getProduct_name() + ")");
            System.out.println("Total number of products left: " + productList.size());
        } else {
            System.out.println("Product with ID " + productIdToDelete + " not found.");
        }
        notifyGUI(productTypeDropdown, table);
    }
    // Method to print the product list
    public void printProductList() {
        if (productList.isEmpty()) {
            System.out.println("The product list is empty.");
        } else {
            productList.sort(Comparator.comparing(Product::getProduct_id));
            for (Product product : productList) {
                System.out.println(product);
            }
        }
    }
    // Method to save the product list to a file
    public void saveFile(Scanner scanner) {
        try {
            FileWriter file = new FileWriter("newfile.txt");
            file.write("product list" + getProductList());
            file.close();
            System.out.println("Program data stored into the file named 'newfile' successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to read the product list from a file
    public void readFile(Scanner scanner) {
        try {
            FileReader pointer = new FileReader("newfile.txt");
            Scanner read_file = new Scanner(pointer);
            System.out.println("Your program data that stored into the file is : " + "\n");
            while (read_file.hasNextLine()) {
                String data = read_file.nextLine();
                System.out.println(data);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    // Method to open the GUI
    public void openGUI() {
        SwingUtilities.invokeLater(() -> {
            if (shoppingGUI == null) {
                shoppingGUI = new ShoppingCart(this, getProductList());
                setShoppingGUI(shoppingGUI);
                shoppingGUI.setVisible(true);
            } else {
                shoppingGUI.setVisible(true);
            }
        });
    }
}
