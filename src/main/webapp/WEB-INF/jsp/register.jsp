<%@include file="../../includes/header.jsp"%>
<h1>Register new user</h1>

<form action="register">
    <input type="hidden" name="from" value="${from}" />
    <table>
        <tr>
            <td>First Name: </td>
            <td><input name="firstName" placeholder="firstName"></td>
        </tr>
        <tr>
            <td>Last Name: </td>
            <td><input name="lastName" placeholder="lastName"></td>
        </tr>
        <tr>
            <td>Email: </td>
            <td><input name="email" placeholder="email"></td>
        </tr>
        <tr>
            <td>Address: </td>
            <td><input name="address" placeholder="address"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit"></td>
        </tr>
    </table>
</form>