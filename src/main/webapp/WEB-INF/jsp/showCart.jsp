<%@include file="../../includes/header.jsp"%>
<h1>Your Cart</h1>

<table class="beauty">
    <thead>
        <td>Quantity</td>
        <td>Description</td>
        <td>Price</td>
        <td>Total</td>
    </thead>
    <tbody>
<c:forEach items="${cartItems}" var="item">
    <tr>
        <td>
            <form action="/updateItem">
                <input type="number" name="quantity" value="<c:out value="${item.quantity}"/>">
                <input type="hidden" name="id" value="${item.productId}"/>
                <input type="submit" value="Update"/>
            </form>
        </td>
        <td><c:out value="${item.description}"/></td>
        <td><c:out value="${item.price}"/></td>
        <td>
            <form action="/removeItem">
                <c:out value="${item.price.multiply(item.quantity)}"/>
                <input type="hidden" name="id" value="${item.productId}"/>
                <input type="submit" value="Remove"/>
            </form>
        </td>
    </tr>
</c:forEach>
    </tbody>
</table>

<br>
<form action="/showCatalog">
    <input type="submit" value="Continue Shopping"/>
</form>
<br>
<form action="/checkout">
    <input type="submit" value="Checkout"/>
</form>

</body>
</html>


