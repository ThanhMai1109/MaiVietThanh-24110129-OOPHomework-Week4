package business;

public class Product {

    private String productId;
    private String description;
    private double price;

    public Product(String productId, String description, double price) {
        this.productId = productId;
        this.description = description;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        return String.format("%.2f", price);
    }
}
