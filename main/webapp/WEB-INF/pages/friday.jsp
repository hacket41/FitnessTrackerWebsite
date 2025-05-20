<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Friday</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/exercises.css" />
</head>
<body>
    <jsp:include page="navbar.jsp"/>
    
    <button class="back-button" onclick="window.location.href='${pageContext.request.contextPath}/workout'">Go Back</button>
    
    <!-- Display Error Messages -->
    <c:if test="${not empty error}">
        <div class="error" style="color: red; margin: 10px 20px;">${error}</div>
    </c:if>

    <!-- Progress Bar -->
    <div class="progress-container" style="width: 100%; padding: 0 20px; box-sizing: border-box;">
        <div class="progress-title">Today's Progress</div>
        <div class="progress-bar-outer" style="width: 100%;">
            <div class="progress-bar-inner" id="progress-bar"></div>
        </div>
        <div class="progress-text">
            <span id="progress-percentage">0%</span>
        </div>
    </div>
    <div class="workout-details">
        <div class="workout-info">
            <div class="exercise-name-container">
                <p>CARDIO <br> WORKOUT</p>
                <div class="workout-duration">Duration - 45 Mins</div>
                <hr class="custom-line">
                <div class="exercise-description">
                    Lets boost your cardio today with these
                    simple and beginner friendly Cardio workout. <br>
                    BEST OF LUCK 
                </div>
            </div>
        </div>
        
        <form id="workoutForm" action="${pageContext.request.contextPath}/friday" method="post">
            <input type="hidden" name="action" id="formAction">
            <div class="exercises-table">
                <div class="table-header">
                    <div class="workout-type">Exercise</div>
                    <div class="Sets">Sets</div>
                    <div class="reps-weight">Reps</div>
                    <div class="completed">Done</div>
                </div>
                
                <!-- Exercise Rows -->
                <div class="exercise-row">
                    <div class="exercise-name">Running</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">5-10 mins</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="running"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Jumping Jacks</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">30-50</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="jumpingJacks"></div>
                </div>

                <div class="exercise-row">
                    <div class="exercise-name">Cycling</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">5-10 mins</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="cycling"></div>
                </div>

                <div class="exercise-row">
                    <div class="exercise-name">High Knees</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">30-40s</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="highKnees"></div>
                </div>

                <div class="exercise-row">
                    <div class="exercise-name">Burpees</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">10-12</div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="burpees"></div>
                </div>
                
                <!-- Action Buttons -->
                <div class="action-buttons">
                    <button type="button" class="btn btn-primary" id="save-progress" onclick="submitForm('completeWorkout')">Save Progress</button>
                    <button type="button" class="btn btn-success" id="complete-workout" onclick="submitForm('completeWorkout')">Complete Workout</button>
                </div>
            </div>
        </form>
    </div>

    <script>
        window.onload = function() {
            // Progress bar functionality
            const checkboxes = document.querySelectorAll('.exercise-checkbox');
            checkboxes.forEach(checkbox => {
                checkbox.addEventListener('change', updateProgress);
            });

            updateProgress();
        }

        // Progress bar update
        function updateProgress() {
            const totalExercises = document.querySelectorAll('.exercise-checkbox').length;
            const completedExercises = document.querySelectorAll('.exercise-checkbox:checked').length;
            const progressPercentage = Math.round((completedExercises / totalExercises) * 100);

            document.getElementById('progress-bar').style.width = progressPercentage + '%';
            document.getElementById('progress-percentage').textContent = progressPercentage + '%';

            // Visual 'completed' class toggle
            document.querySelectorAll('.exercise-checkbox').forEach(checkbox => {
                if (checkbox.checked) {
                    checkbox.closest('.exercise-row').classList.add('completed');
                } else {
                    checkbox.closest('.exercise-row').classList.remove('completed');
                }
            });
        }

        // Submit form with the specified action
        function submitForm(action) {
            document.getElementById('formAction').value = action;
            document.getElementById('workoutForm').submit();
        }
    </script>
</body>
</html>