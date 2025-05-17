<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                <li><a href="${pageContext.request.contextPath}/adminusers">Back</a></li>
            </ul>
        </div>

        <div class="content">
            <h2>My Profile</h2>

            <!-- Profile Picture -->
            <div class="profile-pic">
                <c:choose>
                    <c:when test="${not empty user.image_path}">
                        <img src="${pageContext.request.contextPath}/${user.image_path}" alt="Profile"
                             style="width:64px; height:64px; border-radius:50%; object-fit:cover;">
                    </c:when>
                    <c:otherwise>
                        <div class="avatar"></div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- User Information Form -->
           <form>
		    <div class="form-group">
		        <label for="firstName">First Name</label>
		        <input type="text" id="firstName" name="firstName" value="${editUser.f_name}" readonly>
		    </div>
		
		    <div class="form-group">
		        <label for="lastName">Last Name</label>
		        <input type="text" id="lastName" name="lastName" value="${editUser.l_name}" readonly>
		    </div>
		
		    <div class="form-group">
		        <label for="username">Username</label>
		        <input type="text" id="username" name="username" value="${editUser.username}" readonly>
		    </div>
		
		    <div class="form-group">
		        <label for="email">Email Address</label>
		        <input type="email" id="email" name="email" value="${editUser.email}" readonly>
		    </div>
		
		    <div class="form-group">
		        <label for="birthday">Birthday</label>
		        <input type="date" id="birthday" name="birthday" value="${editUser.birthday}" readonly>
		    </div>
		
		    <!-- Hide the submit button -->
		    <!-- <button type="submit" class="save-btn">Save Changes</button> -->
		</form>

            <!-- Display success/failure message -->
            <c:if test="${not empty message}">
                <p style="margin-top: 20px; color: green;">${message}</p>
            </c:if>
        </div>
    </div>
</body>
</html>
