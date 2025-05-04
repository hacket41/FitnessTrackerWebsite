<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
    <h2>Edit User Profile</h2>
    <form action="${pageContext.request.contextPath}/adminuser/edit" method="post">
        <input type="hidden" name="userId" value="${editUser.userId}" />

        <label>Username (read-only):</label>
        <input type="text" value="${editUser.username}" readonly /><br>

        <label>First Name:</label>
        <input type="text" name="firstName" value="${editUser.f_name}" /><br>

        <label>Last Name:</label>
        <input type="text" name="lastName" value="${editUser.l_name}" /><br>

        <label>Email:</label>
        <input type="email" name="email" value="${editUser.email}" /><br>

        <label>Birthday:</label>
        <input type="date" name="birthday" value="${editUser.birthday}" /><br>

        <button type="submit">Save Changes</button>
    </form>

    <form action="${pageContext.request.contextPath}/adminuser/delete" method="get" onsubmit="return confirm('Are you sure you want to delete this user?');">
        <input type="hidden" name="userId" value="${editUser.userId}" />
        <button type="submit" style="background-color:red;color:white;">Delete User</button>
    </form>

    <a href="${pageContext.request.contextPath}/adminusers">Back to User List</a>
</body>
</html>
