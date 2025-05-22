<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>NavBar</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css" />
</head>
<body>
    <nav>
        <!-- Mobile Sidebar -->
        <ul class="sidebar">
            <li onclick="hideSidebar()">
                <a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 -960 960 960" width="26" fill="001231"><path d="m256-200-56-56 224-224-224-224 56-56 224 224 224-224 56 56-224 224 224 224-56 56-224-224-224 224Z"/></svg></a>
            </li>
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/meals">Meals</a></li>
            <li><a href="${pageContext.request.contextPath}/progress">Progress</a></li>
            <li><a href="${pageContext.request.contextPath}/workout">Workout</a></li>
            <li><a href="${pageContext.request.contextPath}/about">About Us</a></li> <!-- Fixed -->

            

            
            <c:choose>
                <c:when test="${not empty sessionScope.username}">
                    <li><a href="${pageContext.request.contextPath}/userprofile">Profile</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                </c:otherwise>
            </c:choose>
        </ul>

        <!-- Desktop Navigation -->
        <ul>
            <li class="hideOnMobile logo"><a href="#"><span class="be">Be</span><span class="fit">Fit</span></a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/meals">Meals</a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/progress">Progress</a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/workout">Workout</a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/about">About Us</a></li> <!-- Fixed -->

           

            <c:choose>
                <c:when test="${not empty sessionScope.username}">
                    <li class="hideOnMobile login-dropdown-wrapper" onclick="toggleMenu()">
                        <a href="#">Profile</a>
                        <div class="sub-menu-wrap" id="subMenu">
                            <div class="sub-menu">
                                <div class="user-info">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.user and not empty sessionScope.user.image_path}">
                                            <img src="${pageContext.request.contextPath}/${sessionScope.user.image_path}" alt="Profile Image"
                                                 style="width:64px; height:64px; border-radius:50%; object-fit:cover;">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/resources/images/user.png" alt="Default Profile">
                                        </c:otherwise>
                                    </c:choose>
                                    <h2>${sessionScope.username}</h2>
                                </div>
                                <hr>
                                <a href="${pageContext.request.contextPath}/userprofile" class="sub-menu-link">
                                    <i class="fa-solid fa-user"></i><p>Profile</p><span>></span>
                                </a>
                                <a href="${pageContext.request.contextPath}/logout" class="sub-menu-link">
                                    <i class="fa-solid fa-right-from-bracket"></i><p>Log-Out</p><span>></span>
                                </a>
                            </div>
                        </div>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/login">Login</a></li>
                </c:otherwise>
            </c:choose>

            <!-- Mobile Hamburger Menu -->
            <li onclick="showSidebar()">
                <a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 -960 960 960" width="26" fill="000000"><path d="M120-240v-80h720v80H120Zm0-200v-80h720v80H120Zm0-200v-80h720v80H120Z"/></svg></a>
            </li>
        </ul>
    </nav>

    <!-- Responsive JS -->
    <script>
        function showSidebar() {
            const sidebar = document.querySelector('.sidebar');
            sidebar.style.display = 'flex';
            sidebar.classList.add('active');
        }

        function hideSidebar() {
            const sidebar = document.querySelector('.sidebar');
            sidebar.style.display = 'none';
            sidebar.classList.remove('active');
        }

        function toggleMenu() {
            const subMenu = document.getElementById("subMenu");
            if (window.innerWidth > 768) {
                subMenu.classList.toggle("open-menu");
            }
        }

        document.addEventListener('click', function(event) {
            const sidebar = document.querySelector('.sidebar');
            const menuBtn = document.querySelector('li[onclick="showSidebar()"]');
            if (sidebar.classList.contains('active') &&
                !sidebar.contains(event.target) &&
                !menuBtn.contains(event.target)) {
                hideSidebar();
            }
        });

        window.addEventListener('resize', function() {
            if (window.innerWidth > 768) {
                const sidebar = document.querySelector('.sidebar');
                sidebar.style.display = 'none';
                sidebar.classList.remove('active');
            }
        });
    </script>
</body>
</html>
