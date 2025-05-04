<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminuser.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <section id="menu">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/resources/images/logoone.png" alt="">
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
        <div class = "navigation">
            <div class = "n1">
                <div>
                    <i id = "menu-btn" class="fa-solid fa-bars"></i>
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

        <h3 class = "i-name">
            User List
        </h3>

        <div class = "navigation-new">
                <div class="search-bar">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" placeholder="Search">
                </div>

                <div class="filter-box">
                    <select name="filter" id="filter">
                        <option disabled selected>Filter</option>
                        <option value="az">A-Z</option>
                        <option value="date">Date-Joined</option>
                        <option value="role">Role</option>
                    </select>
                    <span class="dropdown-arrow">▼</span>
                </div>
        </div>
        <div class="guest-nav">
            <div class="drop-down">
                <select name = "role" id="role">
                    <option value="guest">Guest</option>
                    <option value="basic">Basic</option>
                    <option value="subscription">Subscription</option>
                </select>
            </div>
            
            <div class="drop-down-1">
                <select name = "role" id="role">
                    <option value="guest">Guest</option>
                    <option value="basic">Basic</option>
                    <option value="subscription">Subscription</option>
                </select>
            </div>
        </div>
        <div class = "board">
            <table width = "100%">
                <thead>
                    <tr>
                        <td><input type="checkbox"></td>
                        <td>Name</td>
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

				        <td class="edit">
						    <a href="${pageContext.request.contextPath}/userprofile?userId=${user.userId}">Edit</a>
						    |
						    <a href="${pageContext.request.contextPath}/adminuser/delete?userId=${user.userId}" 
						       onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
						</td>


				    </tr>
				</c:forEach>
				</tbody>
        </div>
    </section>
        

        
</body>
</html>
    