<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <title>Login - Fitness Tracker</title>
</head>
<body>
    <div class="login-container animated fade-in">
        <!-- Login Form -->
        <div class="login-box">
            <div class="login-header animated slide-up" style="animation-delay: 0.2s;">
                <header>LOGIN</header>
                <p class="login-subtitle">Welcome to your fitness journey</p>
            </div>

            <!-- Error message section -->
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="error-message animated slide-up" style="animation-delay: 0.25s; color: red; text-align: center; margin-bottom: 10px;">
                    <%= error %>
                </div>
            <%
                }
            %>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="input-box animated slide-up" style="animation-delay: 0.3s;">
                    <input type="text" name="username" class="input-field" placeholder="Username" autocomplete="off" required>
                </div>

                <div class="input-box animated slide-up" style="animation-delay: 0.4s;">
                    <input type="password" id="password" name="password" class="input-field" placeholder="Password" autocomplete="off" required>
                </div>

                <!-- Remember Me and Show Password -->
                <div class="forgot animated slide-up" style="animation-delay: 0.5s;">
                    <section>
                        <input type="checkbox" id="check" name="remember">
                        <label for="check">Remember me</label>
                    </section>
                    <section>
                        <input type="checkbox" id="showPassword" onclick="togglePasswordVisibility()" />
                        <label for="showPassword">Show Password</label>
                    </section>
                </div>

                <div class="input-submit animated slide-up" style="animation-delay: 0.6s;">
                    <button type="submit" class="submit-btn" id="submit"></button>
                    <label for="submit">Sign In</label>
                </div>

                <div class="sign-up-link animated slide-up" style="animation-delay: 0.7s;">
                    <p>Don't have account? <a href="${pageContext.request.contextPath}/register">Sign Up</a></p>
                </div>
            </form>
        </div>

        <!-- Feature illustration -->
        <div class="login-illustration animated slide-in-right" style="animation-delay: 0.4s;">
            <img src="${pageContext.request.contextPath}/resources/images/feature.jpg" alt="Fitness Journey" class="illustration-image">
            <div class="illustration-text">
                <h2>FITNESS JOURNEY</h2>
                <p>Track your progress and achieve your goals</p>
            </div>
        </div>
    </div>

    <!-- JavaScript for animations and password toggle -->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
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
            checkInView(); // Run once on page load
        });

        function togglePasswordVisibility() {
            const passwordInput = document.getElementById("password");
            passwordInput.type = passwordInput.type === "password" ? "text" : "password";
        }
    </script>
</body>
</html>
