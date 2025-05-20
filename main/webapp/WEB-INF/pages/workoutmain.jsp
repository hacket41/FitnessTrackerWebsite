<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Workouts - Fitness Tracker</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workout.css" />
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp"/>

    <!-- Hero Section -->
    <section class="hero-section" style="background-image: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/resources/images/herosection.jpg')">
        <div class="hero-content">
            <div class="hero-subtitle">HEALTH · STRENGTH · MAINTENANCE</div>
            <h1 class="hero-title">MY WORKOUTS</h1>
            <p class="hero-description">Select your workout day to view and track your fitness journey</p>
        </div>
    </section>

    <main>
        <div class="container animated fade-in">
			<!-- Training Days Heading -->
            <div class="training-days-heading animated slide-up" style="animation-delay: 0.2s; text-align: center; margin-bottom: 30px;">
                <h2>Training Days</h2>
            </div>
            <!-- Days Grid -->
            <div class="days-grid">
                <div class="day-card animated slide-up" style="animation-delay: 0.3s;">
                    <img src="${pageContext.request.contextPath}/resources/images/Sunday.jpg" alt="Sunday" />
                    <div class="day-name"><a href="${pageContext.request.contextPath}/sunday">Sunday</a></div>
                </div>
                <div class="day-card animated slide-up" style="animation-delay: 0.4s;">
                    <img src="${pageContext.request.contextPath}/resources/images/Monday.jpg" alt="Monday" />
                    <div class="day-name"><a href="${pageContext.request.contextPath}/monday">Monday</a></div>
                </div>
                <div class="day-card animated slide-up" style="animation-delay: 0.5s;">
                    <img src="${pageContext.request.contextPath}/resources/images/Tuesday.webp" alt="Tuesday" />
                    <div class="day-name"><a href="${pageContext.request.contextPath}/tuesday">Tuesday</a></div>
                </div>
                <div class="day-card animated slide-up" style="animation-delay: 0.6s;">
                    <img src="${pageContext.request.contextPath}/resources/images/Wednesday.jpg" alt="Wednesday" />
                    <div class="day-name"><a href="${pageContext.request.contextPath}/wednesday">Wednesday</a></div>
                </div>
                <div class="day-card animated slide-up" style="animation-delay: 0.7s;">
                    <img src="${pageContext.request.contextPath}/resources/images/Thursday.jpg" alt="Thursday" />
                    <div class="day-name"><a href="${pageContext.request.contextPath}/thursday">Thursday</a></div>
                </div>
                <div class="day-card animated slide-up" style="animation-delay: 0.8s;">
                    <img src="${pageContext.request.contextPath}/resources/images/Friday.webp" alt="Friday" />
                    <div class="day-name"><a href="${pageContext.request.contextPath}/friday">Friday</a></div>
                </div>
            </div>
			
			<div class="meal-wrapper days-grid">
				<div class="meal-section animated slide-up" style="animation-delay: 0.6s;">
				        <h2>Workout Routine</h2>
				       <div class="meal-cards" id="mealCardsContainer">
						    <c:forEach var="workout" items="${workoutList}">
						        <div class="meal-card">
						            <h3>${workout.name}</h3>
						            <p>Type: ${workout.type}</p>
						            <p>Duration: ${workout.duration}</p>
						        </div>
						    </c:forEach>
						</div>

				</div>
			</div>
			
            <!-- Feature section -->
            <section id="content" class="animated fade-in" style="animation-delay: 1s;">
                <div class="getstarted">
                    <h1>GET STRONGER<br>STAY FIT<br>ACHIEVE GOALS</h1>
                    <p>Follow your workout plan, track your progress<br>and reach your fitness goals.</p>
                    
                    <div class="button-1">
                        <button class="button" onclick="window.location.href='${pageContext.request.contextPath}/progress">View Progress</button>
                    </div>
                </div>
        
                <div class="image">
                    <img src="${pageContext.request.contextPath}/resources/images/feature.jpg" alt="Fitness goals">
                </div>
            </section>
        </div>
    </main>

    <script>
        // Function to update theme based on workout type
        function updateTheme(selectedType) {
            // Remove existing theme classes
            document.body.classList.remove('strength-theme', 'cardio-theme');
            // Add the selected theme class
            document.body.classList.add(selectedType + '-theme');
        }

        // Animation on scroll
        document.addEventListener('DOMContentLoaded', function() {
            const animatedElements = document.querySelectorAll('.animated');
            
            function checkInView() {
                animatedElements.forEach(element => {
                    const elementTop = element.getBoundingClientRect().top;
                    const elementVisible = 150;
                    
                    if (elementTop < window.innerHeight - elementVisible) {
                        element.classList.add('active');
                    }
                });
            }
            
            window.addEventListener('scroll', checkInView);
            checkInView();
        });
    </script>

    <jsp:include page="footer.jsp"/>
</body>
</html>