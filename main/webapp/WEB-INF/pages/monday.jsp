<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Monday</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/exercises.css" />
</head>
<body>
    <jsp:include page="navbar.jsp"/>
    
    <button class="back-button" onclick="window.location.href='${pageContext.request.contextPath}/workout'">Go Back</button>
    
    <c:if test="${not empty error}">
        <div class="error" style="color: red; margin: 10px 20px;">${error}</div>
    </c:if>

    <div class="progress-container">
        <div class="progress-title">Today's Progress</div>
        <div class="progress-bar-outer">
            <div class="progress-bar-inner" id="progress-bar"></div>
        </div>
        <div class="progress-text">
            <span id="progress-percentage">0%</span>
        </div>
    </div>

    <div class="workout-details">
        <div class="workout-info">
            <div class="exercise-name-container">
                <p>PULL <br> WORKOUT</p>
                <div class="workout-duration">Duration - 45 Mins</div>
                <hr class="custom-line">
                <div class="exercise-description">
                    Lets target your Upper Back, Lats and Biceps today with these
                            simple and beginner friendly Pull workout. <br>
                    BEST OF LUCK
                </div>
            </div>
        </div>

        <div class="exercises-table">
            <form id="workoutForm" action="${pageContext.request.contextPath}/monday" method="post">
                <input type="hidden" name="action" value="saveProgress" id="formAction">
                <div class="table-header">
                    <div class="workout-type">Exercise</div>
                    <div class="Sets">Sets</div>
                    <div class="reps-weight">Reps</div>
                    <div class="completed">Done</div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Barbell Squats</div>
                    <div class="sets-range">4</div>
                    <div class="exercise-specs">8-10</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="barbellSquats" value="completed"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Deadlifts</div>
                    <div class="sets-range">4</div>
                    <div class="exercise-specs">6-8</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="benchPress" value="completed"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Assisted Pull Ups</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">8-10</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="assistedPullups" value="completed"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Barbell Rows</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">10-12</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="barbellRows" value="completed"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Barbell Bicep Curls</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">10-12</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="barbellBicepsCurls" value="completed"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Hammer Curls</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">10-12</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="hammerCurls" value="completed"></div>
                </div>
                
                <div class="action-buttons">
                    <button type="submit" class="btn btn-primary" name="action" value="saveProgress">Save Progress</button>
                    <button type="submit" class="btn btn-success" name="action" value="completeWorkout">Complete Workout</button>
                </div>
            </form>
        </div>

    <script>
        window.onload = function() {
            const checkboxes = document.querySelectorAll('.exercise-checkbox');
            checkboxes.forEach(checkbox => {
                checkbox.addEventListener('change', updateProgress);
            });
            updateProgress();
        }

        function updateProgress() {
            const totalExercises = document.querySelectorAll('.exercise-checkbox').length;
            const completedExercises = document.querySelectorAll('.exercise-checkbox:checked').length;
            const progressPercentage = Math.round((completedExercises / totalExercises) * 100);

            document.getElementById('progress-bar').style.width = progressPercentage + '%';
            document.getElementById('progress-percentage').textContent = progressPercentage + '%';

            document.querySelectorAll('.exercise-checkbox').forEach(checkbox => {
                if (checkbox.checked) {
                    checkbox.closest('.exercise-row').classList.add('completed');
                } else {
                    checkbox.closest('.exercise-row').classList.remove('completed');
                }
            });
        }
    </script>
</body>
</html>