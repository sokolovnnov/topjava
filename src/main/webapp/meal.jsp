
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

            <td>${meal.id}
            </td>
            <td><javatime:format value="${meal.dateTime}" style="MM"/>
                <form action="${pageContext.request.contextPath}/yourServletURL" method="post">
                    <p>Изменить:
                        <input type="datetime-local" name="name"/></p>
                </form>

            </td>
            <td>${meal.description}
                <form action="${pageContext.request.contextPath}/yourServletURL" method="post">
                    <p>Изменить:
                        <input type="text" name="name"/></p>
                </form>
            </td>
            <td>${meal.calories}
                <form action="${pageContext.request.contextPath}/yourServletURL" method="post">
                    <p>Изменить:
                        <input type="number" name="calories"/></p>
                </form>

            </td>
            <td>
                <form action="${pageContext.request.contextPath}/yourServletURL" method="post">
                    <p>
                        <br>
                        <input type="button" name="" value="Принять изменения!"/></p>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/yourServletURL" method="post">
                    <p>
                        <br>
                        <input type="button" name="" value="Удалить строку!"/></p>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="${pageContext.request.contextPath}/yourServletURL" method="post">
    <p>Normal text field.
        <input type="text" name="name"/></p>

    <p>Secret text field.
        <input type="password" name="pass"/></p>

    <p>Single-selection radiobuttons.
        <input type="radio" name="gender" value="M"/> Male
        <input type="radio" name="gender" value="F"/> Female</p>

    <p>Single-selection checkbox.
        <input type="checkbox" name="agree"/> Agree?</p>

    <p>Multi-selection checkboxes.
        <input type="checkbox" name="role" value="USER"/> User
        <input type="checkbox" name="role" value="ADMIN"/> Admin</p>

    <p>Single-selection dropdown.
        <select name="countryCode">
            <option value="NL">Netherlands</option>
            <option value="US">United States</option>
        </select></p>

    <p>Multi-selection listbox.
        <select name="animalId" multiple="true" size="2">
            <option value="1">Cat</option>
            <option value="2">Dog</option>
        </select></p>

    <p>Text area.
        <textarea name="message"></textarea></p>

    <p>Submit button.
        <input type="submit" name="submit" value="submit"/></p>
</form>
</body>
</html>