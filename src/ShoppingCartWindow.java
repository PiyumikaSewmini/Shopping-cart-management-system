import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
public class ShoppingCartWindow extends JFrame {
    private final DefaultTableModel cartTableModel = new DefaultTableModel();
    private final Map<String, Integer> productQuantityMap = new HashMap<>();
    private Product[] productList;
    private boolean isFirstPurchase = true;
    private JTextArea totalTextArea; // JTextArea to display the total price

    public ShoppingCartWindow() {// constructor of shoppingCartWindow class

        // to Create a panel
        JPanel panel = new JPanel(null);

        // Create a table model with columns: Product, Quantity, Price
        cartTableModel.addColumn("Product");
        cartTableModel.addColumn("Quantity");
        cartTableModel.addColumn("Price");

        //  to Create a JTable with the table model
        JTable shoppingCartTable = new JTable(cartTableModel);


        //  to Create a scroll pane for the table
        JScrollPane cartTableScrollPane = new JScrollPane(shoppingCartTable);
        cartTableScrollPane.setPreferredSize(new Dimension(400, 150));
        // to Create a scroll pane for the product details text area
        JTextArea productDetailsTextArea = new JTextArea();
        JScrollPane detailsScrollPane = new JScrollPane(productDetailsTextArea);
        detailsScrollPane.setPreferredSize(new Dimension(400, 100));

        //  to Create a JTextArea for displaying the total price
        totalTextArea = new JTextArea();
        totalTextArea.setEditable(false);
        totalTextArea.setLineWrap(true);
        totalTextArea.setWrapStyleWord(true);
        totalTextArea.setPreferredSize(new Dimension(400, 50));

        // Add components to the panel
        panel.add(cartTableScrollPane);
        panel.add(detailsScrollPane);
        panel.add(totalTextArea);

        // Set the layout manager to BorderLayout
        setLayout(new BorderLayout());

        // Add the table and details scroll panes to the frame
        add(cartTableScrollPane, BorderLayout.CENTER);
        add(detailsScrollPane, BorderLayout.SOUTH);
        add(totalTextArea, BorderLayout.PAGE_END);

        // Set the frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setTitle("Shopping Cart");
        setVisible(false);  // Initially not visible
    }

    // Method to update the shopping cart table with product details, quantity, and price
    public void updateShoppingCart(Product product, double totalPrice) {
        String productId = String.valueOf(product.getProduct_id());
        String productName = product.getProduct_name();
        String productInfo = getProductInfo(product);

        // Check if the product is already in the cart
        if (productQuantityMap.containsKey(productId)) {
            int currentQuantity = productQuantityMap.get(productId);
            int updatedQuantity = currentQuantity + 1;
            productQuantityMap.put(productId, updatedQuantity);

            for (int rowIndex = 0; rowIndex < cartTableModel.getRowCount(); rowIndex++) {
                if (productId.equals(cartTableModel.getValueAt(rowIndex, 0))) {
                    cartTableModel.setValueAt(updatedQuantity, rowIndex, 1);
                    return; // Exit the method once the row is updated
                }
            }
        } else {
            // If it is not, add the product to the cart with a quantity of 1
            productQuantityMap.put(productId, 1);
            cartTableModel.addRow(new Object[]{getProductDetails(product), 1, totalPrice});

            // Update total price in column index 2
        }

        // Update the total text area
        updateTotalTextArea();
    }

    public int findProductRowIndex(String productId) {
        for (int rowIndex = 0; rowIndex < cartTableModel.getRowCount(); rowIndex++) {
            if (productId.equals(cartTableModel.getValueAt(rowIndex, 0))) {
                return rowIndex;
            }
        }
        return -1;
    }

    // Method to get product details as a formatted string
    private String getProductDetails(Product product) {
        String productId = String.valueOf(product.getProduct_id());
        String productName = product.getProduct_name();
        String productInfo = getProductInfo(product);

        return String.format("%s - %s (%s)", productId, productName, productInfo);
    }

    private String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            return "Warranty Period: " + electronicsProduct.getWarranty_period() +
                    ", Brand: " + electronicsProduct.getElectronic_brand();
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            return "Size: " + clothingProduct.getSize() +
                    ", Color: " + clothingProduct.getColor();
        } else {
            // Default representation for other product types
            return "No additional info";
        }
    }

    // Method to update the total text area
    private void updateTotalTextArea() {
        // Set the text of the total text area to the calculated total price
        double total = calculateTotalPrice();
        totalTextArea.setText("Total = $" + total);

        double discountedTotal = applyDiscounts(total);
        totalTextArea.append("\nFinal Cost after Discounts = $" + discountedTotal); // to display the final cost message
    }

    private double applyDiscounts(double total) {
        // Apply 10% discount for the first purchase
        if (isFirstPurchase) {
            total *= 0.9;
            isFirstPurchase = false;
        }
        // Apply 20% discount when buying at least three products of the same category
        if (totalQuantityInCart() >= 3) {
            total *= 0.8;
        }
        return total;
    }

    // Method to calculate the total quantity of items in the cart
    private int totalQuantityInCart() {
        int totalQuantity = 0;
        for (int rowIndex = 0; rowIndex < cartTableModel.getRowCount(); rowIndex++) {
            Object value = cartTableModel.getValueAt(rowIndex, 1);
            if (value instanceof Integer) {
                totalQuantity += (int) value;
            }
        }
        return totalQuantity;
    }

    // Method to calculate the total price of all items in the cart
    private double calculateTotalPrice() {
        double total = 0.0;

        for (int rowIndex = 0; rowIndex < cartTableModel.getRowCount(); rowIndex++) {
            Object value = cartTableModel.getValueAt(rowIndex, 2);
            if (value instanceof Double) {
                total += (double) value;
            }
        }
        return total;
    }

    public void updateProductInfo(Product product, int quantity) {
        // Find the row index of the product in the table
        int rowIndex = findProductRowIndex(String.valueOf(product.getProduct_id()));

        if (rowIndex != -1) {
            // Update the quantity column in the table
            cartTableModel.setValueAt(quantity, rowIndex, 1);


            // Update the total text area
            updateTotalTextArea();
        }
    }
    public int getProductQuantity(Product product) {
        String productId = String.valueOf(product.getProduct_id());
        return productQuantityMap.getOrDefault(productId, 0);
    }

}
