<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealTo"--%>
<%--@elvariable id="action" type="String"--%>
<c:set var="action"  value="${requestScope.action}" />

<html>
<head>
    <title>User meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${action} Meal</h2>
<c:set var="meal"  value="${requestScope.mealForUpd}" />

<form method="post" action="<c:url value="/meals"/>">
    <input type="hidden" name="action" value="${action}">


    <input type="hidden" name="id" value="${meal.id}" readonly>

    <br><label for="datetime"><b>datetime</b></label><br>
    <input id="datetime" name="datetime" type="datetime-local" value="${meal.dateTime}">

    <br><label for="description"><b>description</b></label><br>
    <input id="description" name="description" value="${meal.description}">

    <br><label for="calories"><b>calories</b></label><br>
    <input id="calories" name="calories" value="${meal.calories}">

    <br><br><input type="submit" name="button" value="save"/>
</form>

</body>
</html>

