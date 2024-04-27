// Electronics class extends Product class
public class Electronics extends Product {
    private String electronic_brand;
    private int warranty_period;
    // Constructor for Electronics class, initializing fields using super constructor
    public Electronics(String product_id, String product_name,int number_of_available_items,double price_of_product,String category,String clothing_brand,int warranty_period) {
        super(product_id,product_name,number_of_available_items,price_of_product,category);
        this.electronic_brand = clothing_brand;
        this.warranty_period=warranty_period;
    }
    // Getter for electronic brand
    public String getElectronic_brand() {

        return electronic_brand;
    }

    // Getter for warranty period
    public int getWarranty_period() {

        return warranty_period;
    }

    // Setter for electronic brand
    public void setClothing_brand(String clothing_brand) {

        this.electronic_brand = clothing_brand;
    }

    // Setter for warranty period
    public void setWarranty_period(int warranty_period) {

        this.warranty_period = warranty_period;
    }
    // Override getAdditionalInfo method to provide specific additional information for Electronics

    @Override
    public String getAdditionalInfo() {
        // Provide implementation for Electronics additional information
        return "Warranty Period: " + getWarranty_period() +
                ", Brand: " + getElectronic_brand();
    }
    // Override toString method to provide a formatted string representation of Electronics object

    @Override
    public String toString() {
        return String.format("Electronics - Product ID: %s, Name: %s, Available Items: %d, Price: %.2f, Brand: %s, Warranty Period: %d months",
                getProduct_id(), getProduct_name(), getNumber_of_available_items(), getPrice_of_product(), getElectronic_brand(), getWarranty_period());
    }
}

