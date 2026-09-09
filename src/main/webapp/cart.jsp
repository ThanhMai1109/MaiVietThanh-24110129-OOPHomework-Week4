<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="business.Cart" %>
<%@ page import="business.LineItem" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>

<h1>Your cart</h1>

<%
    Cart cart = (Cart) request.getAttribute("cart");
    List<LineItem> lineItems = (cart != null) ? cart.getLineItems() : null;
    String ctx = request.getContextPath();
%>

<table>
    <tr>
        <th>Quantity</th>
        <th>Description</th>
        <th>Price</th>
        <th>Amount</th>
        <th></th>
    </tr>
    <% if (lineItems != null) {
        for (LineItem item : lineItems) { %>
    <tr>
        <td>
            <form action="<%= ctx %>/cart" method="post" class="inline-form">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="productCode" value="<%= item.getProduct().getProductId() %>">
                <input type="text" name="quantity" value="<%= item.getQuantity() %>" size="2" class="qty-input">
                <input type="submit" value="Update">
            </form>
        </td>
        <td><%= item.getProduct().getDescription() %></td>
        <td>$<%= item.getProduct().getFormattedPrice() %></td>
        <td>$<%= item.getFormattedAmount() %></td>
        <td>
            <form action="<%= ctx %>/cart" method="post" class="inline-form">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="productCode" value="<%= item.getProduct().getProductId() %>">
                <input type="submit" value="Remove Item">
            </form>
        </td>
    </tr>
    <% }
    } if (cart == null || cart.isEmpty()) { %>
    <tr>
        <td colspan="5">Your cart is empty.</td>
    </tr>
    <% } %>
</table>

<% if (cart != null && !cart.isEmpty()) { %>
<p><strong>Total: $<%= cart.getFormattedTotal() %></strong></p>
<% } %>

<p><strong>To change the quantity</strong>, enter the new quantity and click on the Update button.</p>

<form action="index.jsp" method="get">
    <input type="submit" value="Continue Shopping">
</form>
<form action="<%= ctx %>/cart" method="get">
    <input type="hidden" name="action" value="checkout">
    <input type="submit" value="Checkout">
</form>

</body>
</html>
