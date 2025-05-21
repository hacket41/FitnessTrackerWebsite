<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/editcss.css">
<title>Insert title here</title>
</head>
<body>
	 <div class="form-container">
        <h2>Edit Meal</h2>
        <form action="${pageContext.request.contextPath}/admincontent" method="post">
            <input type="hidden" name="action" value="editMeal">
            <input type="hidden" name="mealId" value="${editMeal.id}">

            <label>Meal Name:</label>
            <input type="text" name="mealName" value="${editMeal.name}" required>

            <label>Meal Type:</label>
            <input type="text" name="mealType" value="${editMeal.type}" required>

            <label>Calories:</label>
            <input type="number" name="calories" value="${editMeal.calories}" required>

            <label>Macros:</label>
            <textarea name="macros" rows="4" required>${editMeal.macros}</textarea>

            <button type="submit">Update Meal</button>
        </form>
    </div>
</body>
</html>