<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

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
       <ul class="sidebar">
		    <li onclick="hideSidebar()">
		        <a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 -960 960 960" width="26" fill="001231"><path d="m256-200-56-56 224-224-224-224 56-56 224 224 224-224 56 56-224 224 224 224-56 56-224-224-224 224Z"/></svg></a>
		    </li>
		    <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
		    <li><a href="${pageContext.request.contextPath}/meals">Meals</a></li>
		    <li><a href="${pageContext.request.contextPath}/progress">Progress</a></li>
		    <li><a href="${pageContext.request.contextPath}/workout">Workout</a></li>
		
		    <c:choose>
		        <c:when test="${not empty sessionScope.username}">
		            <li><a href="${pageContext.request.contextPath}/userprofile">Profile</a></li>
		        </c:when>
		        <c:otherwise>
		            <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
		        </c:otherwise>
		    </c:choose>
		</ul>
        
        <ul class="sidebar">
            <li onclick = "hideSidebar()"><a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 -960 960 960" width="26" fill="001231"><path d="m256-200-56-56 224-224-224-224 56-56 224 224 224-224 56 56-224 224 224 224-56 56-224con-224-224 224Z"/></svg></a></li>
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/meals">Meals</a></li>
            <li><a href="${pageContext.request.contextPath}/progress">Progress</a></li>
            <li><a href="${pageContext.request.contextPath}/workout">Workouts</a></li>
            <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
            
        </ul>
        <ul>
            <li class="hideOnMobile logo"><a href="#"><span class="be">Be</span><span class="fit">Fit</span></a>
            </li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/meals">Meals</a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/progress">Progress</a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/workout">Workout</a></li>
            <li><c:choose>
		    <c:when test="${not empty sessionScope.username}">
		        <li class="hideOnMobile login-dropdown-wrapper" onclick="toggleMenu()">
		            <a href="#">Profile</a>
		            <div class="sub-menu-wrap" id="subMenu">
		                <div class="sub-menu">
		                    <div class="user-info">
		                        <img src="${pageContext.request.contextPath}/resources/images/user.png" alt="User">
		                        <h2></h2>
		                    </div>
		                    <hr>
		                    <a href="${pageContext.request.contextPath}/userprofile" class="sub-menu-link">
		                        <i class="fa-solid fa-user"></i><p>Profile</p>
		                        <span>></span>
		                    </a>
		                    <a href="${pageContext.request.contextPath}/logout" class="sub-menu-link">
		                        <i class="fa-solid fa-right-from-bracket"></i><p>Log-Out</p>
		                        <span>></span>
		                    </a>
		                </div>
		            </div>
		        </li>
		    </c:when>
		    <c:otherwise>
       		 <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/login">Login</a></li>
    			</c:otherwise>
			</c:choose>
			<li>
           	
            <li onclick="showSidebar()"><a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 -960 960 960" width="26" fill="000000"><path d="M120-240v-80h720v80H120Zm0-200v-80h720v80H120Zm0-200v-80h720v80H120Z"/></svg></a></li>
        </ul>    
    </nav>
    
	<script>
        function showSidebar() {
            document.querySelector('.sidebar').style.display = 'flex';
        }

        function hideSidebar() {
            document.querySelector('.sidebar').style.display = 'none';
        }

        function toggleMenu(){
            document.getElementById("subMenu").classList.toggle("open-menu");
        }
    </script>
           
    
	
</body>
</html>