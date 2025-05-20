<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admincontent.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <title>Content</title>
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
	
	<!-- Inteface Portions -->
    <section id="interface">
        <div class = "navigation">
            <div class = "n1">
                
                <div class="search">
                    <form method="get" action="${pageContext.request.contextPath}/admincontent" class="search-form">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        <input type="text" name="search" value="${searchQuery}" placeholder="Search content...">
                        <button type="submit" style="display: none;"></button>
                    </form>
                </div>
            </div>

            <div class="profile">
                <i class="fa-solid fa-user"></i>
                <img src="${pageContext.request.contextPath}/resources/images/user-1.jpg" alt="">
            </div>
        </div>

        <h3 class = "i-name">
            Dashboard
        </h3>

        <div class="content-section">
            <div class="content-card">
                <h4>Upload Meal Plan</h4>
                <c:if test="${not empty error}">
                    <div class="error-message">
                        ${error}
                    </div>
                </c:if>
				<form action="${pageContext.request.contextPath}/admincontent" method="post">
				    <input type="text" name="mealName" placeholder="Meal Name" required>
				    <input type="text" name="mealType" placeholder="Meal Type" required>
				    <input type="number" name="calories" placeholder="Calories" required min="0" style="height: 40px;">
				    <textarea name="macros" placeholder="Macros" required></textarea>
				    <button type="submit">Upload</button>
				</form>

            </div>
            <div class="content-card">
                <h4>Upload Workout Routine</h4>
                <form action="${pageContext.request.contextPath}/admincontent" method="post">
				    <input type="hidden" name="action" value="uploadWorkout" />
				    <input type="text" name="workoutName" placeholder="Workout Name" required />
				    <input type="text" name="workoutType" placeholder="Workout Type" required />
				    <input type="text" name="workoutDuration" placeholder="Workout Duration" required />
				    <button type="submit">Upload</button>
				</form>	
            </div>            
        </div>
        
        <!-- Suggested Meals Section -->
			<!-- Suggested Meals Section -->
			

         <!--  -->
         <div class="meal-wrapper days-grid">
				<div class="meal-section animated slide-up" style="animation-delay: 0.6s;">
				        <h2>Workout Routine</h2>
				       <div class="meal-cards" id="mealCardsContainer">
						    <c:forEach var="workout" items="${uploadedWorkouts}">
						        <div class="meal-card">
						            <h3>${workout.name}</h3>
						            <p>Type: ${workout.type}</p>
						            <p>Duration: ${workout.duration}</p>
						        </div>
						    </c:forEach>
						</div>

				</div>
			</div>
			
		
		<!-- Suggested Meals Section -->
		<div class="meal-wrapper days-grid">
		    <div class="meal-section animated slide-up" style="animation-delay: 0.6s;">
		        <h2>Suggested Meals</h2>
		        <div class="meal-cards" id="mealCardsContainer">
		
		            <c:if test="${empty suggestedMeals}">
		                <p>No suggested meals available.</p>
		            </c:if>
		
		            <c:forEach items="${suggestedMeals}" var="meal">
		                <div class="meal-card">
		                    <h3>${meal.name}</h3>
		                    <p>Type: ${meal.type}</p>
		                    <p>Calories: ${meal.calories}</p>
		                    <p>Macros: ${meal.macros}</p>
		                </div>
		            </c:forEach>
		
		        </div>
		    </div>
		</div>

				
    <script>
        // Auto-submit search form when user stops typing
        let searchTimeout;
        document.querySelector('.search-form input').addEventListener('input', function() {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(() => {
                this.form.submit();
            }, 500);
        });
    </script>
</body>
</html>

<style>
    .error-message {
        background-color: #ffebee;
        color: #c62828;
        padding: 10px;
        margin-bottom: 15px;
        border-radius: 4px;
        border: 1px solid #ffcdd2;
        font-size: 14px;
    }
</style>