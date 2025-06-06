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
                <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Log-Out</a></li>
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
			<form action="${pageContext.request.contextPath}/userprofile" method="post" enctype="multipart/form-data">

                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" value="${user.f_name}" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" value="${user.l_name}" required>
                </div>

                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" value="${user.username}" readonly>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" value="${user.email}" required>
                </div>

                <div class="form-group">
				    <label for="birthday">Birthday</label>
				    <input type="date" id="birthday" name="birthday" value="${user.birthday}">
				</div>
				
				

                <div class="form-group">
                    <label for="image">Upload Profile Image</label>
                    <input type="file" id="image" name="image" accept="image/*">
                    <p class="note">Supported formats: JPG, PNG (Max: 10MB)</p>
                </div>

                <button class="save-btn" type="submit">Save Changes</button>
            </form>

            <!-- Feedback messages -->
				<c:if test="${not empty success}">
				    <p style="color: green;">${success}</p>
				</c:if>
				<c:if test="${not empty error}">
				    <p style="color: red;">${error}</p>
				</c:if>
				
        </div>
    </div>
</body>
</html>
