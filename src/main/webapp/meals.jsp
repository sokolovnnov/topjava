<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealTo"--%>


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
        <form action="${pageContext.request.contextPath}/meals" method="post">
            <tr style="background-color:${(meal.excess ? 'red' : 'green' )}">

                <td>
                    <input type="hidden" name="id" value="${meal.id}">
                        ${meal.id}
                </td>
                <td><javatime:format value="${meal.dateTime}" style="MM"/></td>
                <td> ${meal.description} </td>
                <td> ${meal.calories} </td>
                <td>
                    <input type="submit" name="button" value="edit"/>
                </td>
                <td>
                    <input type="submit" name="button" value="delete"/>
                </td>
            <tr>
        </form>
    </c:forEach>


    <td>
        <form action="${pageContext.request.contextPath}/meals" method="post">
            <input type="submit" name="button" value="new"/>
        </form>
    </td>
    </tr>

    </tbody>
</table>
</body>
</html>