<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userprofile.css">
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <ul>
                <li class="active">User-Information</li>
                <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Log-Out</a></li>
            </ul>
        </div>

        <div class="content">
            <h2>My Profile</h2>

            <!-- Profile Picture (Removed the imagePath logic) -->
            <div class="profile-pic">
                <div class="avatar"></div> <!-- Placeholder for avatar -->
            </div>

            <!-- User Information Form -->
            <form action="${pageContext.request.contextPath}/userprofile" method="post">
                <!-- Hidden username (or user ID) -->
                <input type="hidden" name="username" value="${user.username}"/>

                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="f_name" value="${user.f_name}" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="l_name" value="${user.l_name}" required>
                </div>


                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" value="${user.email}" required>
                </div>

                <button class="save-btn" type="submit">Save Changes</button>
            </form>

            <!-- Display success/failure message -->
            <c:if test="${not empty message}">
                <p style="margin-top: 20px; color: green;">${message}</p>
            </c:if>
        </div>
    </div>
</body>
</html>
