
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>


<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>


<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Редактировать</th>
        <th>Удалить</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${requestScope.mealList}">

        <tr style="background-color:${(meal.excess ? 'red' : 'green' )}">
            <input type="hidden" name="meealid" value="${meal.id}"/>
            <td>${meal.id}
            </td>
            <td><javatime:format value="${meal.dateTime}" style="MM"/>


            </td>
            <td>${meal.description}

            </td>
            <td>${meal.calories}


            </td>
            <td><a href="<c:url value="/meal?action=edit&id=${meal.id}"/>">EDIT</a></td>
            <td><a href="<c:url value="/meal?action=delete&id=${meal.id}"/>">DEL</a></td>
    </c:forEach>
    </tbody>
</table>
<form method="get">
    <input type="hidden" name="action" value="new"/>
    <button type="submit">NEW MEAL</button>
</form>

</body>
</html>