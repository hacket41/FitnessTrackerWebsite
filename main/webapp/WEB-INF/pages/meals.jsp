<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            <div class="stat-value" id="waterIntakeValue">${waterIntake}</div>
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
                            <div class="stat-value" id="caloriesValue">${totalCalories}</div>
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
                        <c:if test="${empty todaysMeals}">
                            <p>No meals logged for today. Start tracking your nutrition by adding meals.</p>
                        </c:if>
                        
                        <c:forEach items="${todaysMeals}" var="meal">
                            <div class="meal-card">
                                <div>
                                    <div class="meal-name">${meal.mealName}</div>
                                    <div class="meal-details">
                                        ${meal.mealType} · ${meal.caloriesConsumed} calories · Protein: ${meal.proteinGm}g, Carbs: ${meal.carbsGm}g, Fats: ${meal.fatsGm}g
                                    </div>
                                </div>
                                <div class="meal-actions">
                                    <div class="meal-time">
                                        ${meal.mealLogDate}
                                    </div>
                                    <button class="favorite-btn ${favoriteMealService.isFavorite(sessionScope.userId, meal.mealId) ? 'active' : ''}" 
                                            onclick="toggleFavorite(${meal.mealId})" 
                                            data-meal-id="${meal.mealId}">
                                        <i class="fas fa-heart"></i>
                                    </button>
                                    <button class="delete-btn" onclick="deleteMeal(${meal.mealId})">🗑️</button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                
                <!-- Favorite Meals section -->
                <div class="meal-section animated slide-up" style="animation-delay: 0.6s;">
                    <h2 class="subtitle">FAVORITE MEALS</h2>
                    <div class="meal-cards" id="favoriteMealCardsContainer">
                        <c:if test="${empty favoriteMeals}">
                            <p>No favorite meals yet. Click the heart icon to add meals to your favorites!</p>
                        </c:if>
                
                        <c:forEach items="${favoriteMeals}" var="meal">
                            <div class="meal-card">
                                <div>
                                    <div class="meal-name">${meal.mealName}</div>
                                    <div class="meal-details">
                                        ${meal.mealType} · ${meal.caloriesConsumed} calories · Protein: ${meal.proteinGm}g, Carbs: ${meal.carbsGm}g, Fats: ${meal.fatsGm}g
                                    </div>
                                </div>
                                <div class="meal-actions">
                                    <div class="meal-time">
                                        ${meal.mealLogDate}
                                    </div>
                                    <button class="favorite-btn active" onclick="toggleFavorite(${meal.mealId})" data-meal-id="${meal.mealId}">
                                        <i class="fas fa-heart"></i>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- Suggested Meals section -->
                <div class="meal-section animated slide-up" style="animation-delay: 0.6s;">
                    <h2>SUGGESTED MEALS</h2>
                    <div class="meal-cards" id="suggestedMealCardsContainer">
                        <c:if test="${empty suggestedMeals}">
                            <p>No suggested meals available.</p>
                        </c:if>
                
                        <c:forEach items="${suggestedMeals}" var="meal">
                            <div class="meal-card">
                                <div>
                                    <div class="meal-name">${meal.name}</div>
                                    <div class="meal-details">
                                        ${meal.type} · ${meal.calories} calories · Macros: ${meal.macros}
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>       
            
            <!-- Meal logging by JSP -->
            <div class="log-meal-section animated slide-in-right" style="animation-delay: 0.8s;">
                <h3 class="log-meal-title">LOG YOUR MEAL</h3>
                
                <form id="mealForm" action="${pageContext.request.contextPath}/meals" method="post">
                    <input type="hidden" name="action" value="addMeal">
                    <input type="text" name="mealName" class="input-field" placeholder="Enter Meal Name" required>
                    
                    <div class="macro-field">
                        <label>Protein</label>
                        <div>
                            <input type="number" name="protein" value="0" min="0"> <span>g</span>
                        </div>
                    </div>
                    
                    <div class="macro-field">
                        <label>Carbs</label>
                        <div>
                            <input type="number" name="carbs" value="0" min="0"> <span>g</span>
                        </div>
                    </div>
                    
                    <div class="macro-field">
                        <label>Fats</label>
                        <div>
                            <input type="number" name="fats" value="0" min="0"> <span>g</span>
                        </div>
                    </div>
                    
                    <div class="macro-field">
                        <label>Calories</label>
                        <div>
                            <input type="number" name="calories" value="0" min="0"> <span>kcal</span>
                        </div>
                    </div>
                    
                    <div class="time-picker">
                        <input type="text" name="hour" class="time-input" value="0" pattern="[0-9]{1,2}" required>
                        <div class="time-separator">:</div>
                        <input type="text" name="minute" class="time-input" value="00" pattern="[0-9]{2}" required>
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
        // Water intake buttons - using AJAX to update on server
        document.getElementById('increaseWaterBtn').addEventListener('click', function() {
            updateWaterIntake('increase');
        });

        document.getElementById('decreaseWaterBtn').addEventListener('click', function() {
            updateWaterIntake('decrease');
        });
        
        // Function to update water intake with AJAX
        function updateWaterIntake(action) {
            const xhr = new XMLHttpRequest();
            xhr.open('POST', '${pageContext.request.contextPath}/meals', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        document.getElementById('waterIntakeValue').textContent = xhr.responseText;
                    } else {
                        console.error('Error updating water intake:', xhr.responseText);
                    }
                }
            };
            xhr.send('action=updateWater&waterAction=' + action);
        }

        // Function to delete a meal
        function deleteMeal(mealId) {
            if (confirm('Are you sure you want to delete this meal?')) {
                const xhr = new XMLHttpRequest();
                xhr.open('POST', '${pageContext.request.contextPath}/meals', true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            // Reload the page to show updated meal list
                            window.location.reload();
                        } else {
                            alert('Error deleting meal: ' + xhr.responseText);
                        }
                    }
                };
                xhr.send('action=deleteMeal&mealId=' + mealId);
            }
        }

        // Function to toggle favorite status
        function toggleFavorite(mealId) {
            const xhr = new XMLHttpRequest();
            xhr.open('POST', '${pageContext.request.contextPath}/toggleFavorite', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        try {
                            const response = JSON.parse(xhr.responseText);
                            if (response.success) {
                                // Reload the page to update both sections
                                window.location.reload();
                            } else {
                                alert('Error toggling favorite status');
                            }
                        } catch (e) {
                            console.error('Error parsing response:', e);
                            alert('Error processing server response');
                        }
                    } else {
                        alert('Error toggling favorite status: ' + xhr.responseText);
                    }
                }
            };
            xhr.send('action=toggleFavorite&mealId=' + mealId);
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
    <jsp:include page = "footer.jsp"/>
</body>
</html>

<style>
.meal-actions {
    display: flex;
    align-items: center;
    gap: 10px;
}

.favorite-btn {
    background: none;
    border: none;
    cursor: pointer;
    padding: 5px;
    color: #ccc;
    transition: color 0.3s ease;
}

.favorite-btn:hover {
    color: #ff6b6b;
}

.favorite-btn.active {
    color: #ff6b6b;
}

.delete-btn {
    background: none;
    border: none;
    cursor: pointer;
    padding: 5px;
    color: #ff6b6b;
    transition: color 0.3s ease;
}

.delete-btn:hover {
    color: #ff0000;
}
</style>