import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShoppingCart extends JFrame {
    public List<Product> productList;
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final JTextArea productDetailsTextArea = new JTextArea();

    private JComboBox<String> productTypeDropdown;
    private JTable table;
    private final DefaultTableModel cartTableModel = new DefaultTableModel();
    private final ShoppingCartWindow shoppingCartWindow = new ShoppingCartWindow();
    private List<Product> products;
    private Map<String, Integer> categoryCount;
    private boolean firstPurchase;
    private final Map<Product, Integer> productQuantityMap;
    private Product product;
    private int quantity;


    public ShoppingCart(WestminsterShoppingManager shoppingManager, List<Product> productList) {
        this.productList = productList;
        this.products = new ArrayList<>();
        this.categoryCount = new HashMap<>();
        this.firstPurchase = true;
        this.productQuantityMap = new HashMap<>();


        //to  Create a panel to hold components
        JPanel panel = new JPanel(null);

        // Create a table to display products
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price");
        tableModel.addColumn("Info");

        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 80, 700, 300);

        // Create a dropdown for selecting product type
        String[] productTypes = {"All", "Electronics", "Clothes"};
        JComboBox<String> productTypeDropdown = new JComboBox<>(productTypes);
        productTypeDropdown.setPreferredSize(new Dimension(150, 30));
        productTypeDropdown.setBounds(50, 20, 150, 30);

        // Create a text area for displaying product details
        productDetailsTextArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(productDetailsTextArea);
        detailsScrollPane.setBounds(50, 400, 700, 100);

        // Create buttons for adding to cart and viewing shopping cart
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBounds(350, 510, 120, 30);

        JButton shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.setBounds(630, 20, 120, 30);


        // Add components to the panel
        panel.add(productTypeDropdown);
        panel.add(tableScrollPane);
        panel.add(detailsScrollPane);
        panel.add(addToCartButton);
        panel.add(shoppingCartButton);

        // Event listener for the dropdown
        productTypeDropdown.addActionListener(e -> updateTable(productTypeDropdown, table, productList));

        // Event listener for the "Add to Cart" button
        addToCartButton.addActionListener(e -> addToShoppingCart(table));

        // Event listener for the "Shopping Cart" button
        shoppingCartButton.addActionListener(e -> showShoppingCartWindow());

        // Enable sorting for the table
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Product selectedProduct = productList.get(selectedRow);
                        displayProductDetails(selectedProduct);
                    }
                }
            }
        });

        // Set the frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("Westminster Shopping Centre");
        add(panel);

        // Make the frame visible
        setVisible(true);

    }
    public JComboBox<String> getProductTypeDropdown() {
        return productTypeDropdown;
    }

    public JTable getTable() {
        return table;
    }

    private void displayProductDetails(Product product) {
        productDetailsTextArea.setText(""); // Clear previous content

        if (product != null) {
            // Display product details in a vertical format
            String formattedDetails = String.format("Selected product Details \n Product ID: %s\nProduct Name: %s\nCategory: %s\n",
                    String.valueOf(product.getProduct_id()), product.getProduct_name(),product.getNumber_of_available_items(),
                    (product instanceof Electronics) ? "Electronics" : "Clothes",
                    product.getNumber_of_available_items());

            // Append additional details based on the product type
            if (product instanceof Electronics) {
                Electronics electronicsProduct = (Electronics) product;
                formattedDetails += String.format("Warranty Period: %s\nBrand: %s\n",
                        electronicsProduct.getWarranty_period(), electronicsProduct.getElectronic_brand());
            } else if (product instanceof Clothing) {
                Clothing clothingProduct = (Clothing) product;
                formattedDetails += String.format("Size: %s\nColor: %s\n",
                        clothingProduct.getSize(), clothingProduct.getColor());
            }

            // Display each piece of information in a separate line
            String[] detailsArray = formattedDetails.split("\n");
            for (String detail : detailsArray) {
                productDetailsTextArea.append(detail + "\n");
            }
        }
    }

    public void updateTable(JComboBox<String> productTypeDropdown, JTable table, List<Product> productList) {
        tableModel.setRowCount(0);
        String selectedProductType = (String) productTypeDropdown.getSelectedItem(); // Get the selected product type

        // Add products to the table based on the selected product type
        for (Product product : productList) {
            if (selectedProductType.equals("All") ||
                    (selectedProductType.equals("Electronics") && product instanceof Electronics) ||
                    (selectedProductType.equals("Clothes") && product instanceof Clothing)) {
                String[] productInfo = getProductInfoArray(product);
                tableModel.addRow(productInfo);
            }
        }
    }

    // New method to split product information into individual pieces
    private String[] getProductInfoArray(Product product) {
        // Check the type of product and extract information accordingly
        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            return new String[]{
                    String.valueOf(electronicsProduct.getProduct_id()),
                    electronicsProduct.getProduct_name(),
                    "Electronics",
                    String.valueOf(electronicsProduct.getPrice_of_product()),
                    "Warranty Period: " + electronicsProduct.getWarranty_period() +
                            ", Brand: " + electronicsProduct.getElectronic_brand()
            };
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            return new String[]{
                    String.valueOf(clothingProduct.getProduct_id()),
                    clothingProduct.getProduct_name(),
                    "Clothes",
                    String.valueOf(clothingProduct.getPrice_of_product()),
                    "Size: " + clothingProduct.getSize() +
                            ", Color: " + clothingProduct.getColor()
            };
        } else {
            // For other product types, use default representation
            return product.toString().split(", ");
        }
    }
    public void addToShoppingCart(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Product selectedProduct = productList.get(selectedRow);

            // Check if the product is already in the shopping cart
            int rowIndexInCart = shoppingCartWindow.findProductRowIndex(String.valueOf(selectedProduct.getProduct_id()));

            if (rowIndexInCart != -1) {
                // If it is, update the quantity
                int currentQuantity = shoppingCartWindow.getProductQuantity(selectedProduct);
                int updatedQuantity = currentQuantity + 1;
                shoppingCartWindow.updateProductInfo(selectedProduct, updatedQuantity);
            } else {
                // If it is not, add the product to the cart with a quantity of 1
                double totalPrice = selectedProduct.getPrice_of_product();
                shoppingCartWindow.updateShoppingCart(selectedProduct, totalPrice);

                // Example: Get the quantity of the selected product in the cart
                int quantityInCart = shoppingCartWindow.getProductQuantity(selectedProduct);

                // Display the category and quantity in the shopping cart window
                shoppingCartWindow.updateProductInfo(selectedProduct, quantityInCart);
            }
        }
    }

    private void showShoppingCartWindow() {
        // Display the shopping cart window
        shoppingCartWindow.setVisible(true);
    }



    public void removeProduct(Product product) {
        this.product = product;
        // Remove the selected product from the cart
        int quantity = productQuantityMap.getOrDefault(product, 0);
        if (quantity > 1) {
            productQuantityMap.put(product, quantity - 1);
        } else {
            productQuantityMap.remove(product);
        }

        // Update category count
        String category = product.getCategory();
        categoryCount.put(category, categoryCount.get(category) - 1);
    }




}