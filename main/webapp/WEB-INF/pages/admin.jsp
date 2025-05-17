<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <section id="menu">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/resources/images/pic1.jpg" alt="">
            <h2>Fitness Tracker</h2>
        </div>
        <div class="items">
            <ul>
                <li><i class="fa-solid fa-chart-pie"></i><a href ="${pageContext.request.contextPath}/admin">Dashboard</a></li>
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
                <div class="search">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" placeholder="Search">
                </div>
            </div>
            <div class="profile">
                <i class="fa-solid fa-user"></i>
                <img src="${pageContext.request.contextPath}/resources/images/user-1.jpg" alt="">
            </div>
        </div>

        <h3 class="i-name">Dashboard</h3>
        <div class="values">
            <div class="val-box">
                <i class="fa-solid fa-users"></i>
                <div>
                    <h3>${userCount}</h3>
                    <span>New Users</span>
                </div>
            </div>

            <div class="val-box">
                <i class="fa-solid fa-utensils"></i>
                <div>
                    <h3>30</h3>
                    <span>Meal Plans</span>
                </div>
            </div>

            <div class="val-box">
                <i class="fa-solid fa-dumbbell"></i>
                <div>
                    <h3>12</h3>
                    <span>Workout Routines</span>
                </div>
            </div>
            
            <div class="val-box">
                <i class="fa-solid fa-bars-progress"></i>
                <div>
                    <h3>45%</h3>
                    <span>Progress Increase</span>
                </div>
            </div>
        </div>

        <div class="board">
            <table width="100%">
                <thead>
                    <tr>
                        <td>Username</td>
                        <td>Title</td>
                        <td>Status</td>
                        <td>Role</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                  <c:forEach var="user" items="${userList}">
				    <tr>
				        <td class="people">
				            <img src="${user.image_path}" alt="">
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
						    <p>${onlineUsers.contains(user.username) ? 'Online' : 'Offline'}</p>
						</td>
				        <td class="role">
						    <c:choose>
						        <c:when test="${user.username == sessionScope.user.username}">
						            <p>Admin</p>
						        </c:when>
						        <c:otherwise>
						            <p>User</p>
						        </c:otherwise>
						    </c:choose>
						</td>

				        <td class="edit"><a href="#">Edit</a></td>
				    </tr>
				</c:forEach>

                </tbody>
            </table>
        </div>
    </section>
	
	
	<!-- Function button  -->
    <script>
        $('#menu-btn').click(function() {
            $('#menu').toggleClass("active");
        })
    </script>
</body>
</html>
