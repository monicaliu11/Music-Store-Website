<%@include file="../../includes/header.jsp"%>
<h1>Login</h1>

<form action="login">
    <input type="hidden" name="from" value="${from}"/>
    <table>
        <tr>
            <td>Email: </td>
            <td><input name="email" placeholder="email"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit"></td>
        </tr>
    </table>
</form>