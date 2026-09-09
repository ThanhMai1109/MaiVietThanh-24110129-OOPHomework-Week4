package util;

import business.Cart;
import business.LineItem;
import business.Product;
import data.ProductDB;

/**
 * Converts between the cart cookie string ("8601:1,pf01:2") and a real
 * Cart object. This is the only place that knows about the cookie's
 * text format -- Cart itself just holds LineItems and doesn't know
 * it's ever stored in a cookie.
 */
public class CartUtil {

    public static String encodeCart(Cart cart) {
        StringBuilder sb = new StringBuilder();
        for (LineItem item : cart.getLineItems()) {
            if (sb.length() > 0) {
                sb.append("|");   // đổi từ "," sang "|"
            }
            sb.append(item.getProduct().getProductId()).append(":").append(item.getQuantity());
        }
        return sb.toString();
    }

    public static Cart decodeCart(String cookieValue) {
        Cart cart = new Cart();
        if (cookieValue != null && !cookieValue.isEmpty()) {
            String[] entries = cookieValue.split("\\|");   // đổi từ "," sang "\\|"
            for (String entry : entries) {
                String[] parts = entry.split(":");
                if (parts.length == 2) {
                    try {
                        String productId = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        Product product = ProductDB.getProduct(productId);
                        if (product != null) {
                            cart.getLineItems().add(new LineItem(product, quantity));
                        }
                    } catch (NumberFormatException e) {
                        // skip malformed entry
                    }
                }
            }
        }
        return cart;
    }
}
