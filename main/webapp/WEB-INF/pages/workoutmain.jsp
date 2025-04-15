<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Workouts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workout.css" />
</head>
<body>
<jsp:include page = "navbar.jsp"/>
    
    <div class="container">
        <h1 class="page-title">My Workouts</h1>
        
        <div class="workout-selector">
            <select class="workout-type-selector" onchange="updateTheme(this.value)">
                <option value="" disabled selected>Select Workout Type</option>
                <option value="strength">Strength Training</option>
                <option value="cardio">Cardio</option>
            </select>
        </div>
    
    </div>
    
    <script>
        function updateTheme(selectedType) {
            // Remove existing theme classes
            document.body.classList.remove('strength-theme', 'cardio-theme');
            // Add the selected theme class
            document.body.classList.add(selectedType + '-theme');
        }
    </script>

        <div class="days-grid">
            <div class="day-card">
                <img src="${pageContext.request.contextPath}/resources/images/Sunday.jpg" alt="Sunday" />
            <div class="day-name"><a href="${pageContext.request.contextPath}/sunday">Sunday</a></div>
        </div>
            <div class="day-card">
                <img src="${pageContext.request.contextPath}/resources/images/Monday.jpg" alt="Monday" />
                <div class="day-name"><a href="${pageContext.request.contextPath}/monday">Monday</a></div>
            </div>
            <div class="day-card">
                <img src="${pageContext.request.contextPath}/resources/images/Tuesday.webp" alt="Tuesday" />
                <div class="day-name"><a href="${pageContext.request.contextPath}/tuesday">Tuesday</a></div>
            </div>
            <div class="day-card">
                <img src="${pageContext.request.contextPath}/resources/images/Wednesday.jpg" alt="Wednesday" />
                <div class="day-name"><a href="${pageContext.request.contextPath}/wednesday">Wednesday</a></div>
            </div>
            <div class="day-card">
                <img src="${pageContext.request.contextPath}/resources/images/Thursday.jpg" alt="Thursday" />
                <div class="day-name"><a href="${pageContext.request.contextPath}/thursday">Thursday</a></div>
            </div>
            <div class="day-card">
            	
                <img src="${pageContext.request.contextPath}/resources/images/Friday.webp" alt="Friday" />
                <div class="day-name"><a href="${pageContext.request.contextPath}/friday">Friday</a>
                </div>
            </div>
        </div>

        <script>
            function navigateToDay(day) {
                window.location.href = day.toLowerCase() + '.html';
            }
        </script>
        
            <script>
		function showSidebar() {
		    document.querySelector('.sidebar').style.display = 'flex';
		}
		
		function hideSidebar() {
		    document.querySelector('.sidebar').style.display = 'none';
		}
	</script>
	<script>
		function showSidebar() {
		    document.querySelector('.sidebar').style.display = 'flex';
		}
		
		function hideSidebar() {
		    document.querySelector('.sidebar').style.display = 'none';
		}
	</script>
        <jsp:include page = "footer.jsp"/>
		
		
</body>
</html>