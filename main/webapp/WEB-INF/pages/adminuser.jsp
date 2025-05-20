<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>User List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminuser.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <section id="menu">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/resources/images/logoone.png" alt="Logo" />
            <h2>Fitness Tracker</h2>
        </div>
        <div class="items">
            <ul>
                <li><i class="fa-solid fa-chart-pie"></i><a href="${pageContext.request.contextPath}/admin">Dashboard</a></li>
                <li><i class="fa-solid fa-users"></i><a href="${pageContext.request.contextPath}/adminusers">Users</a></li>
                <li><i class="fa-solid fa-chart-simple"></i><a href="${pageContext.request.contextPath}/adminstat">Statistics</a></li>
                <li><i class="fa-solid fa-gears"></i><a href="${pageContext.request.contextPath}/admincontent">Content</a></li>
                <li><i class="fa-solid fa-house"></i><a href="${pageContext.request.contextPath}/home">Home</a></li>
            </ul>
        </div>
    </section>

    <section id="interface">
        <div class="navigation">
            <div class="n1">
                <div>
                    <i id="menu-btn" class="fa-solid fa-bars"></i>
                </div>
            </div>

            <div class="profile">
                <i class="fa-solid fa-user"></i>
                <img src="${pageContext.request.contextPath}/resources/images/user-1.jpg" alt="User Profile" />
            </div>
        </div>

        <h3 class="i-name">User List</h3>

        <div class="navigation-new">
            <div class="search-bar">
                <form method="get" action="${pageContext.request.contextPath}/adminusers" class="search-bar">
                    <input
                        type="text"
                        name="search"
                        value="${searchQuery}"
                        placeholder="Search by username..."
                    />
                    <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                </form>
            </div>
        </div>

        <!-- Display messages -->
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="error-message" style="color: red; text-align: center; margin: 10px 0; padding: 10px; background-color: #ffebee; border-radius: 4px;">
                ${sessionScope.errorMessage}
            </div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>
        
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="success-message" style="color: green; text-align: center; margin: 10px 0; padding: 10px; background-color: #e8f5e9; border-radius: 4px;">
                ${sessionScope.successMessage}
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>

        <div class="board">
            <table width="100%">
                <thead>
                    <tr>
                        <td><input type="checkbox" /></td>
                        <td>Name</td>
                        <td>Title</td>
                        <td>Status</td>
                        <td>Role</td>
                       
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td class="people">
                                <img src="${user.image_path != null ? user.image_path : pageContext.request.contextPath.concat('/resources/images/default-user.jpg')}" 
                                     alt="${user.username}" />
                                <div class="people-de">
                                    <h5>${user.username}</h5>
                                    <p>${user.email}</p>
                                </div>
                            </td>
                            <td class="people-des">
                                <h5>${user.username}</h5>
                                <p>${user.f_name} ${user.l_name}</p>
                            </td>
                            <td class="${onlineUsers.contains(user.username) ? 'active' : 'offline'}">
                                <p><c:out value="${onlineUsers.contains(user.username) ? 'Online' : 'Offline'}" /></p>
                            </td>
                            <td class="role">
                                <p>
                                    <c:choose>
                                        <c:when test="${not empty user.role}">
                                            ${user.role}
                                        </c:when>
                                        <c:otherwise>
                                            User
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${user.username == sessionScope.user.username}">
                                        (You)
                                    </c:if>
                                </p>
                            </td>
                            <td class="edit">
                                <a href="${pageContext.request.contextPath}/adminedit?userId=${user.userId}">View</a> |
                                <a
                                    href="${pageContext.request.contextPath}/adminuser/delete?userId=${user.userId}"
                                    onclick="return confirm('Are you sure you want to delete this user?');"
                                    >Delete</a
                                >
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty userList}">
                        <tr>
                            <td colspan="?" style="text-align: center;">
                                No users found for "${searchQuery}".
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </section>

    <script>
        // Toggle menu on mobile
        $('#menu-btn').click(function() {
            $('#menu').toggleClass('active');
        });
    </script>
</body>
</html>