<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/editcss.css">
<title>Edit Workout</title>
</head>
<body>
    <div class="form-container">
        <h2>Edit Workout</h2>
        <form action="${pageContext.request.contextPath}/admincontent" method="post">
            <input type="hidden" name="action" value="editWorkout">
            <input type="hidden" name="workoutId" value="${editWorkout.id}">

            <label>Workout Name:</label>
            <input type="text" name="workoutName" value="${editWorkout.name}" required>

            <label>Workout Type:</label>
            <input type="text" name="workoutType" value="${editWorkout.type}" required>

            <label>Duration:</label>
            <input type="text" name="workoutDuration" value="${editWorkout.duration}" required>

            <button type="submit">Update Workout</button>
        </form>
    </div>
</body>
</html>