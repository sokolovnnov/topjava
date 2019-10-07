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
        <form action="${pageContext.request.contextPath}/meal" method="post">
            <tr style="background-color:${(meal.excess ? 'red' : 'green' )}">

                <td>
                        ${meal.id}
                    <input type="hidden" name="id" value="${meal.id}"/></p>
                </td>
                <td>
                    <javatime:format value="${meal.dateTime}" style="MM"/>
                    <input type="datetime-local" name="datetime" value="${meal.dateTime}"/></p>

                </td>
                <td>
                        ${meal.description}
                    <input type="text" name="descriptions" value="${meal.description}"/></p>
                </td>
                <td>
                        ${meal.calories}
                    <input type="text" name="calories" value="${meal.calories}"/></p>
                </td>
                <td>
                    <input type="submit" name="button" value="edit"/>
                </td>
                <td>
                    <input type="submit" name="button" value="delete"/>
                </td>

                    <%--<td><a href="<c:url value="/meal?action=edit&id=${meal.id}"/>">EDIT</a></td>--%>
                    <%--<td><a href="<c:url value="/meal?action=delete&id=${meal.id}"/>">DEL</a></td>--%>
        </form>
    </c:forEach>
    <%--<c:set var="meal1" value="${requestScope.mealList}" scope="request"/>--%>
    <form action="${pageContext.request.contextPath}/meals" method="post">
    <tr>
        <td>
            <%--${meal1.id}--%>
            <input type="hidden" name="id" /></p>
            <%--<input type="hidden" name="id" value="${meal1.id}"/></p>--%>
        </td>
        <td>
            <%--<javatime:format value="${meal.dateTime}" style="MM"/>--%>
            <%--<input type="datetime-local" name="datetime" value="${meal.dateTime}"/></p>--%>
            <input type="datetime-local" name="datetime" /></p>

        </td>
        <td>
            <%--${meal.description}--%>
            <input type="text" name="description" /></p>
        </td>
        <td>
            <%--${meal.calories}--%>
            <input type="text" name="calories" /></p>
        </td>
        <td>
            <input type="submit" name="button" value="new"/>
        </td>

    </tr>
    </form>
    </tbody>
</table>
</body>
</html>