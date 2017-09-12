<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <style>
        .mealNormal {
            color: green;
        }
        .mealExceed {
            color: red;
        }
    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<form action="/meals" method="get">
    <input type="reset" value="Reset">
    <br>
    <br>
    Begin date:
    <input type="datetime-local" name="startDate" value="${startDate}">
    End Date:
    <input type="datetime-local" name="endDate" value="${endDate}">
    <br>
    <br>
    Start time:
    <input type="time" name="startTime" value="${startTime}">
    End Time:
    <input type="time" name="endTime" value="${endTime}">
    <br>
    <br>
    <input type="submit" value="Filter">
</form>
<br>
<form action="/meals/edit">
    <input type="submit" value="Add new meal">
</form>
<br>
<form action="<c:url value="/meals"/>" method="post">
<table>
    <thead>
        <tr>
            <td></td>
            <td>Date</td>
            <td>Meal</td>
            <td>Calories</td>
            <td>Edit</td>
        </tr>
    </thead>
    <tbody class="meal">
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? 'mealExceed' : 'mealNormal'}">
            <td><input type="checkbox" name="selectedMeals" value="${meal.id}"></td>
            <td>${fn:formatLocalDateTime(meal.dateTime)}</td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="/meals/edit?id=${meal.id}">Edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<input type="submit" value="Delete selected">
</form>
</body>
</html>