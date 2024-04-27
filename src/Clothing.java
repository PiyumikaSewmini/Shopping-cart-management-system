// Clothing class extends Product class
public class Clothing extends Product{

    private int size;
    private String color;
    // Constructor for Clothing class, initializing fields using super constructor
    public Clothing(String product_id, String product_name, int number_of_available_items, double price_of_product,String category, String size,String color) {
        super(product_id,product_name,number_of_available_items,price_of_product,category);

        this.size= Integer.parseInt(size);
        this.color=color;
    }
// Getter for color
    public String getColor() {

        return color;
    }
    // Getter for size
    public int getSize() {

        return size;
    }

    // Setter for color
    public void setColor(String color) {

        this.color = color;
    }
    // Setter for size
    public void setSize(int size) {

        this.size = size;
    }
    // Override toString method to provide a formatted string representation of Clothing object
    @Override
    public String toString() {
     return String.format("Clothes - Product ID: %s, Name: %s, Available Items: %d, Price: %.2f, Color: %s, Size: %d",
                getProduct_id(), getProduct_name(), getNumber_of_available_items(), getPrice_of_product(), getColor(), getSize());
    }
    @Override
    public String getAdditionalInfo() {
        // Provide implementation for Clothing additional information
        return "Size: " + getSize() +
                ", Color: " + getColor();
    }
}
