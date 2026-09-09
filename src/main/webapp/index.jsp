<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="data.ProductDB" %>
<%@ page import="business.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>

<h1>CD list</h1>

<table>
    <tr>
        <th>Description</th>
        <th>Price</th>
        <th></th>
    </tr>
    <% for (Product p : ProductDB.getAllProducts()) { %>
    <tr>
        <td><%= p.getDescription() %></td>
        <td>$<%= p.getFormattedPrice() %></td>
        <td>
            <form action="<%= request.getContextPath() %>/cart" method="get">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productCode" value="<%= p.getProductId() %>">
                <input type="submit" value="Add To Cart">
            </form>
        </td>
    </tr>
    <% } %>
</table>

<p>
    <a href="<%= request.getContextPath() %>/cart">View Cart</a>
</p>

</body>
</html>
