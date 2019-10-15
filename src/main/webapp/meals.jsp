<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<a href="users.jsp">Change user</a>
<h2>Meals</h2>
<a href="meals?action=create">Add Meal</a>

<form method="get" action="<c:url value="/meals"/>">
    Фильтровать даты:
    <input name="action" value="datefilter" type="hidden">
    <label for="startdate"><b>start</b></label>
    <input id="startdate" name="startdate" type="date">

    <label for="enddate"><b>end</b></label>
    <input id="enddate" name="enddate" type="date">

<br>
    Фильтровать время:
    <input name="action" value="timefilter" type="hidden">
    <label for="starttime"><b>start</b></label>
    <input id="starttime" name="starttime" type="time">

    <label for="endtime"><b>end</b></label>
    <input id="endtime" name="endtime" type="time">
    <input type="submit" value="Фильровать"/>
</form>
<br>


<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>
                    <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                    <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                    <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                    ${fn:formatDateTime(meal.dateTime)}
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</section>
</body>
</html>