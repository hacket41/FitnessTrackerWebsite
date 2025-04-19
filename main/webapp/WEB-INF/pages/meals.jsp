<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Meals</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/meals.css">
</head>
<body>
	<jsp:include page = "navbar.jsp"/>

    <section class="hero-section" style="background-image: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/resources/images/herosection.jpg')">
        <div class="hero-content">
            <div class="hero-subtitle">HEALTH · FOOD · MAINTENANCE</div>
            <h1 class="hero-title">MY MEALS</h1>
            <p class="hero-description">Monitor your daily nutrition intake, track macros, and maintain a balanced diet to achieve your fitness goals.</p>
        </div>
    </section>
    
    <main>
        <div class="layout">
            <div class="main-content">
                <!-- Stats section with JSP for dynamic updates -->
                <h2 class="section-title">TODAY'S STATS</h2>
                <div class="tracking-stats">
                    <div class="stat-card animated fade-in">
                        <div>
                            <div>TODAY'S WATER INTAKE (Litres)</div>
                            <div class="stat-value" id="waterIntakeValue">0</div>
                        </div>
                        <div class="water-icon">
                            <span class="icon">💧</span>
                        </div>
                    </div>
                    <div class="water-control animated fade-in" style="animation-delay: 0.2s;">
                        <button class="water-btn" id="increaseWaterBtn">+</button>
                        <button class="water-btn" id="decreaseWaterBtn">-</button>
                    </div>

                    <div class="stat-card animated fade-in" style="animation-delay: 0.4s;">
                        <div>
                            <div>TODAY'S CALORIES</div>
                            <div class="stat-value" id="caloriesValue">0</div>
                        </div>
                        <div class="calorie-icon">
                            <span class="icon">🔥</span>
                        </div>
                    </div>
                </div>
                
                <!-- Meals listing section by JSP -->
                <div class="meal-section animated slide-up" style="animation-delay: 0.6s;">
                    <h2 class="subtitle">TODAY'S MEALS</h2>
                    <div class="meal-cards" id="mealCardsContainer">
                        <!-- JSP will insert meal cards here based on database data -->
                    </div>
                </div>
            </div>       
            
            <!-- Meal logging by JSP -->
            <div class="log-meal-section animated slide-in-right" style="animation-delay: 0.8s;">
                <h3 class="log-meal-title">LOG YOUR MEAL</h3>
                
                <form id="mealForm" action="addMeal.jsp" method="post">
                    <input type="text" name="mealName" class="input-field" placeholder="Enter Meal Name">
                    
                    <button type="button" id="toggleMacros" class="toggle-macros">Enter Macros</button>
                    
                    <div id="macroFields" style="display: none;">
                        <div class="macro-field">
                            <label>Protein</label>
                            <div>
                                <input type="number" name="protein" value="0"> <span>g</span>
                            </div>
                        </div>
                        
                        <div class="macro-field">
                            <label>Carbs</label>
                            <div>
                                <input type="number" name="carbs" value="0"> <span>g</span>
                            </div>
                        </div>
                        
                        <div class="macro-field">
                            <label>Fats</label>
                            <div>
                                <input type="number" name="fats" value="0"> <span>g</span>
                            </div>
                        </div>
                        
                        <div class="macro-field">
                            <label>Calories</label>
                            <div>
                                <input type="number" name="calories" value="0"> <span>kcal</span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="time-picker">
                        <input type="text" name="hour" class="time-input" value="0">
                        <div class="time-separator">:</div>
                        <input type="text" name="minute" class="time-input" value="00">
                        <select name="ampm" class="am-pm">
                            <option value="AM">AM</option>
                            <option value="PM">PM</option>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn" style="width: 100%;">ADD MEAL</button>
                </form>
            </div>
            
            <section id="content" class="animated fade-in" style="animation-delay: 1s;">
                <div class="getstarted">
                    <h1>SET GOALS<br>SET WORKOUTS<br>STAY ON TRACK</h1>
                    <p>Track your workouts, set plan meals, track your macros<br>and discover new goals.</p>
                    
                    <div class="button-1">
                        <button class="button">Learn More</button>
                    </div>
                </div>
        
                <div class="image">
                    <img src="${pageContext.request.contextPath}/resources/images/feature.jpg" alt="Fitness goals">
                </div>
            </section>
        </div>
    </main>

    <!--Javascript-->
    <script>
        // Toggle macro fields
        document.getElementById('toggleMacros').addEventListener('click', function() {
            const macroFields = document.getElementById('macroFields');
            if (macroFields.style.display === 'none') {
                macroFields.style.display = 'block';
                this.textContent = 'Hide Macros';
            } else {
                macroFields.style.display = 'none';
                this.textContent = 'Enter Macros';
            }
        });
        
        // Water intake buttons
  
		document.getElementById('increaseWaterBtn').addEventListener('click', function() {
    	let currentValue = parseFloat(document.getElementById('waterIntakeValue').textContent);
    	currentValue += 0.1;
    	document.getElementById('waterIntakeValue').textContent = currentValue.toFixed(1);
		});

		document.getElementById('decreaseWaterBtn').addEventListener('click', function() {
    	let currentValue = parseFloat(document.getElementById('waterIntakeValue').textContent);
    		if (currentValue > 0) {
        	currentValue -= 0.1;
        // Ensure value doesn’t go below 0 due to floating-point errors
        if (currentValue < 0) currentValue = 0;
        document.getElementById('waterIntakeValue').textContent = currentValue.toFixed(1);
    }
		});

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
        <jsp:include page = "footer.jsp"/>
    
</body>
</html>