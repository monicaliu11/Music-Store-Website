<%@include file="../../includes/header.jsp"%>
<h1>Browse Catalog</h1>

<table class="beauty">
    <thead>
        <td>Code</td>
        <td>Description</td>
        <td>Price</td>
        <td>Quantity</td>
        <td>Action</td>
    </thead>
    <tbody>
<c:forEach items="${products}" var="product">
    <form action="/addToCart">
        <input type="hidden" name="id" value="${product.id}"/>
        <tr>
            <td><a href="<c:url value="productDetail"><c:param name="id" value="${product.id}"/></c:url>"><c:out value="${product.code}"/></a></td>
            <td><c:out value="${product.description}"/></td>
            <td><c:out value="${product.price}"/></td>
            <td><input type="number" name="quantity" value="1"/></td>
            <td><input type="submit" value="Add to cart"/></td>
        </tr>
    </form>
</c:forEach>
    </tbody>
</table>
</body>
</html>


