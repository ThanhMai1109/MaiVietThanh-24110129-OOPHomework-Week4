package business;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<LineItem> lineItems;

    public Cart() {
        this.lineItems = new ArrayList<>();
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public boolean isEmpty() {
        return lineItems.isEmpty();
    }

    // adds 1 unit of the product; if it's already in the cart, bumps the quantity instead
    public void addItem(Product product) {
        for (LineItem item : lineItems) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        lineItems.add(new LineItem(product, 1));
    }

    // quantity <= 0 removes the line item entirely
    public void updateQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            removeItem(productId);
            return;
        }
        for (LineItem item : lineItems) {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public void removeItem(String productId) {
        lineItems.removeIf(item -> item.getProduct().getProductId().equals(productId));
    }

    public double getTotal() {
        double total = 0;
        for (LineItem item : lineItems) {
            total += item.getAmount();
        }
        return total;
    }
}
