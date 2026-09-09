package cart;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import business.Cart;
import business.Product;
import data.ProductDB;
import util.CartUtil;
import util.CookieUtil;

public class CartServlet extends HttpServlet {

    private static final String CART_COOKIE_NAME = "cart";
    // cart cookie lasts 1 week so it survives across browser sessions
    private static final int CART_COOKIE_MAX_AGE = 60 * 60 * 24 * 7;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        switch (action) {
            case "add":
                addToCart(request, response);
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            case "remove":
                removeFromCart(request, response);
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            case "checkout":
                request.getRequestDispatcher("/checkout.jsp")
                        .forward(request, response);
                return;
            default:
                showCart(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateQuantity(request, response);
        } else if ("remove".equals(action)) {
            removeFromCart(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private Cart readCart(HttpServletRequest request) {
        String cookieValue = CookieUtil.getCookieValue(
                request.getCookies(), CART_COOKIE_NAME);
        return CartUtil.decodeCart(cookieValue);
    }

    private void saveCart(HttpServletResponse response, Cart cart) {
        Cookie cookie = new Cookie(CART_COOKIE_NAME, CartUtil.encodeCart(cart));
        cookie.setMaxAge(CART_COOKIE_MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) {
        String productId = request.getParameter("productCode");
        Cart cart = readCart(request);

        Product product = ProductDB.getProduct(productId);
        if (product != null) {
            cart.addItem(product);
        }
        saveCart(response, cart);
    }

    private void updateQuantity(HttpServletRequest request, HttpServletResponse response) {
        String productId = request.getParameter("productCode");
        String quantityParam = request.getParameter("quantity");

        Cart cart = readCart(request);
        try {
            int quantity = Integer.parseInt(quantityParam);
            cart.updateQuantity(productId, quantity);
        } catch (NumberFormatException e) {
            // ignore invalid input, leave cart unchanged for this item
        }
        saveCart(response, cart);
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) {
        String productId = request.getParameter("productCode");
        Cart cart = readCart(request);
        cart.removeItem(productId);
        saveCart(response, cart);
    }

    private void showCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = readCart(request);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
}
