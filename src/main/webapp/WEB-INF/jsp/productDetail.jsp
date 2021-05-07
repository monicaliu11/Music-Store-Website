<%@include file="../../includes/header.jsp"%>
<h1>Product Detail</h1>

<table>
    <tr>
        <td>Code:</td>
        <td><c:out value="${product.code}"/></td>
    </tr>
    <tr>
        <td>Description:</td>
        <td><c:out value="${product.description}"/></td>
    </tr>
    <tr>
        <td>Price:</td>
        <td><c:out value="${product.price}"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <form action="/addToCart">
                <input type="hidden" name="from" value="productDetail"/>
                <input type="hidden" name="id" value="${product.id}"/>
                Quantity: <input type="number" name="quantity" value="1"/>
                <input type="submit" value="Add to cart"/>
            </form>
        </td>
    </tr>
</table>

<c:choose>
    <c:when test = "${user.emailAddress != null}">
        <h4>Tracks:</h4>
        <table class="beauty">
            <thead>
            <td>Number</td>
            <td>Title</td>
            <td>Sample</td>
            </thead>
            <tbody>
            <c:forEach items="${product.tracks}" var="track">
                <tr>
                    <td><c:out value="${track.trackNumber}"/></td>
                    <td><c:out value="${track.title}"/></td>
                    <td><audio controls><source src="<c:out value="/sound/${track.product.code}/${track.sampleFilename}"/>" type="audio/mpeg"></audio></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <form action="/register">
            <input type="hidden" name="id" value="${product.id}"/>
            <input type="hidden" name="from" value="productDetail?id=${product.id}"/>
            <input type="submit" value="Listen samples"/>
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>


