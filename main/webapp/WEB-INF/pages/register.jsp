<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <title>Register - Fitness Tracker</title>
</head>
<body>
    <div class="login-container animated fade-in">
        <!-- Register Form -->
        <div class="login-box">
            <div class="login-header animated slide-up" style="animation-delay: 0.2s;">
                <header>REGISTER</header>
                <p class="login-subtitle">Start your fitness journey today</p>
            </div>

            <!-- Message section -->
            <%
                String error = (String) request.getAttribute("error");
                String success = (String) request.getAttribute("success");
                if (error != null) {
            %>
                <div class="message error-message animated slide-up" style="animation-delay: 0.25s;">
                    <%= error %>
                </div>
            <%
                }
                if (success != null) {
            %>
                <div class="message success-message animated slide-up" style="animation-delay: 0.25s;">
                    <%= success %>
                </div>
            <%
                }
            %>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="input-box animated slide-up" style="animation-delay: 0.3s;">
                    <input type="text" name="firstName" id="firstName" class="input-field" placeholder="First Name" autocomplete="off" required>
                </div>

                <div class="input-box animated slide-up" style="animation-delay: 0.4s;">
                    <input type="text" id="lastName" name="lastName" class="input-field" placeholder="Last Name" autocomplete="off" required>
                </div>

                <div class="input-box animated slide-up" style="animation-delay: 0.5s;">
                    <input type="text" id="userName" name="userName" class="input-field" placeholder="Username" autocomplete="off" required>
                </div>

                <div class="input-box animated slide-up" style="animation-delay: 0.6s;">
                    <input type="email" id="email" name="email" class="input-field" placeholder="Email" autocomplete="off" required>
                </div>

                <div class="input-box animated slide-up" style="animation-delay: 0.7s;">
                    <input type="date" class="input-field" id="birthday" name="birthday" required>
                </div>

                <div class="input-box animated slide-up" style="animation-delay: 0.8s;">
                    <input type="password" id="password" name="password" class="input-field" placeholder="Password" autocomplete="off" required>
                </div>

                <div class="input-submit animated slide-up" style="animation-delay: 0.9s;">
                    <button type="submit" class="submit-btn" id="submit"></button>
                    <label for="submit">Sign Up</label>
                </div>

                <div class="sign-up-link animated slide-up" style="animation-delay: 1s;">
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign In</a></p>
                </div>
            </form>
        </div>

        <!-- Feature illustration -->
        <div class="login-illustration animated slide-in-right" style="animation-delay: 0.4s;">
            <div class="illustration-wrapper">
                <img src="${pageContext.request.contextPath}/resources/images/register.png" alt="Fitness Journey" class="illustration-image">
               
               <!--  <div class="illustration-text">
                    <h2>FITNESS JOURNEY</h2>
                    <p>Track your progress and achieve your goals</p>
                </div>
              -->
            </div>
        </div>
    </div>

    <!-- JavaScript for animations -->
    <script>
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
            checkInView(); // Run once on page load to activate visible elements
        });
    </script>
</body>
</html>