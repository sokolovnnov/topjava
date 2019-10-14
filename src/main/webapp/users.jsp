<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form method="get" action="<c:url value="/users"/>">
    <h2>User</h2>
    <input name="action" value="setuser" type="hidden">
    <select name="user" >

        <option value="1">User 1</option>
        <option value="2">User 2</option>

    </select>
    <input type="submit" value="Выбрать">
</form>
</body>
</html>