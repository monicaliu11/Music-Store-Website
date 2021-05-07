<%@include file="../../includes/header.jsp"%>
<h1>Your invoice</h1>

<table>
    <tr>
        <td>Date:</td>
        <td><c:out value="${invoice.invoiceDate}"/></td>
    </tr>
    <tr>
        <td>Ship to:</td>
        <td><c:out value="${invoice.userFullName}"/></td>
    </tr>
    <tr>
        <td></td>
        <td><c:out value="${invoice.userAddress}"/></td>
    </tr>
    <tr>
        <td>Total:</td>
        <td><c:out value="${invoice.totalAmount}"/></td>
    </tr>
</table>

<h3>Products</h3>
<table class="beauty">
    <thead>
        <td>Description</td>
        <td>Price</td>
        <td>Quantity</td>
    </thead>
    <c:forEach items="${invoice.lineItems}" var="lineItem">
        <c:set var="product" value="${productMap.get(lineItem.productCode)}"/>
        <tr>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td>${lineItem.quantity}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>


