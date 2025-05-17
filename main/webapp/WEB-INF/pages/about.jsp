<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Fitness Tracker</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css">
</head>
<body>
    <jsp:include page="navbar.jsp"/>

    <section class="hero-section" style="background-image: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/resources/images/herosection.jpg')">
        <div class="hero-content">
            <div class="hero-subtitle">ABOUT US</div>
            <h1 class="hero-title">OUR MISSION</h1>
            <p class="hero-description">Empowering individuals to achieve their fitness goals through comprehensive tracking and personalized guidance.</p>
        </div>
    </section>

    <main>
        <div class="about-container">
            <section class="about-section animated fade-in">
                <h2 class="section-title">WHO WE ARE</h2>
                <div class="about-content">
                    <div class="about-text">
                        <p>Fitness Tracker is a comprehensive platform designed to help you achieve your fitness goals through detailed tracking and personalized guidance. Our mission is to make fitness tracking accessible, intuitive, and effective for everyone.</p>
                    </div>
                    <div class="about-image">
                        <img src="${pageContext.request.contextPath}/resources/images/feature.jpg" alt="Fitness tracking">
                    </div>
                </div>
            </section>

            <section class="features-section animated fade-in" style="animation-delay: 0.2s;">
                <h2 class="section-title">OUR FEATURES</h2>
                <div class="features-grid">
                    <div class="feature-card">
                        <div class="feature-icon">📊</div>
                        <h3>Progress Tracking</h3>
                        <p>Monitor your fitness journey with detailed progress tracking and analytics.</p>
                    </div>
                    <div class="feature-card">
                        <div class="feature-icon">🍽️</div>
                        <h3>Meal Planning</h3>
                        <p>Track your nutrition with our comprehensive meal logging system.</p>
                    </div>
                    <div class="feature-card">
                        <div class="feature-icon">💪</div>
                        <h3>Workout Plans</h3>
                        <p>Access personalized workout routines for your fitness goals.</p>
                    </div>
                    <div class="feature-card">
                        <div class="feature-icon">📱</div>
                        <h3>Easy to Use</h3>
                        <p>User-friendly interface designed for seamless fitness tracking.</p>
                    </div>
                </div>
            </section>

            <section class="team-section animated fade-in" style="animation-delay: 0.4s;">
                <h2 class="section-title">ABOUT THE DEVELOPERS</h2>
                <div class="team-grid">
                    <div class="team-member">
                        <div class="member-image">
                            <img src="${pageContext.request.contextPath}/resources/images/AboutUser.png" alt="Team Member">
                        </div>
                        <h3>Abhijit Singh</h3>
                        <p>Developer 1</p>
                    </div>
                    <div class="team-member">
                        <div class="member-image">
                            <img src="${pageContext.request.contextPath}/resources/images/AboutUser.png" alt="Team Member">
                        </div>
                        <h3>Pranish Chaulagain</h3>
                        <p>Developer 2</p>
                    </div>
                    <div class="team-member">
                        <div class="member-image">
                            <img src="${pageContext.request.contextPath}/resources/images/AboutUser.png" alt="Team Member">
                        </div>
                        <h3>Aashutosh Chalise</h3>
                        <p>Developer 3</p>
                    </div>
                     <div class="team-member">
                        <div class="member-image">
                            <img src="${pageContext.request.contextPath}/resources/images/AboutUser.png" alt="Team Member">
                        </div>
                        <h3>Nobel Kumar Aryal</h3>
                        <p>Developer 4</p>
                    </div>
                     <div class="team-member">
                        <div class="member-image">
                            <img src="${pageContext.request.contextPath}/resources/images/AboutUser.png" alt="Team Member">
                        </div>
                        <h3>Kaushal Raj Thapa</h3>
                        <p>Developer 5</p>
                    </div>
                     <div class="team-member">
                        <div class="member-image">
                            <img src="${pageContext.request.contextPath}/resources/images/AboutUser.png" alt="Team Member">
                        </div>
                        <h3>Samik Bhandari</h3>
                        <p>Developer 6</p>
                    </div>
                </div>
            </section>
        </div>
    </main>

    <script>
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