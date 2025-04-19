<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NavBar</title>
<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css" />
</head>
<body>
	   <nav>
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
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/workout">Workouts</a></li>
            <li class="hideOnMobile"><a href="${pageContext.request.contextPath}/login">Login</a></li>
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
	</script>
</body>
</html>