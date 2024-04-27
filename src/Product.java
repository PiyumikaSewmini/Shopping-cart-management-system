public abstract class Product {// Abstract class representing a general product
    private String product_id;
    private String product_name;
    private int number_of_available_items;
    private double price_of_product;
    private String category;
    private int quantity;
    // Constructor to initialize common fields
    public Product(String product_id, String product_name, int number_of_available_items, double price_of_product, String category) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.number_of_available_items = number_of_available_items;
        this.price_of_product = price_of_product;
        this.category = category;
    }
    // Getter for product ID
    public String getProduct_id() {
        return product_id;
    }
    // Getter for product name
    public String getProduct_name() {
        return product_name;
    }
    // Getter for number of available items
    public int getNumber_of_available_items() {
        return number_of_available_items;
    }
    // Getter for price of the product
    public double getPrice_of_product() {
        return price_of_product;
    }
    // Setter for product ID
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    // Setter for price of the product
    public void setPrice_of_product(double price_of_product) {
        this.price_of_product = price_of_product;
    }
    // Setter for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter for product category
    public void setCategory(String category) {
        this.category = category;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory() {
        return category;
    }
    // Setter for number of available items
    public void setNumber_of_available_items(int number_of_available_items) {
        this.number_of_available_items = number_of_available_items;
    }


    // Override toString method to provide a string representation of the product
    @Override
    public String toString() {
        return "Product ID: " + product_id + "\nProduct Name: " + product_name + "\nCategory: " + category +
                "\nNumber of available items: " + number_of_available_items + "\nPrice of product: " + price_of_product;
    }

    public abstract String getAdditionalInfo();
}
