<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sunday</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/exercises.css" />
</head>
<body>
    <jsp:include page="navbar.jsp"/>
    
    <button class="back-button" onclick="window.location.href='${pageContext.request.contextPath}/workout'">Go Back</button>
    
        <!-- Progress Bar -->
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
                    <p>PUSH <br> WORKOUT</p>
                    <div class="workout-duration">Duration - 45 Mins</div>
                    <hr class="custom-line">
                    <div class ="exercise-description">
                           Lets target your Chest, Shoulders and Triceps today with these
                            simple and beginner friendly Push workout. <br>
                            BEST OF LUCK 
                    
                    </div>

                </div>
            </div>
            
            <div class="exercises-table">
                <div class="table-header">
                    <div class="workout-type">Exercise </div>
                    <div class ="Sets">Sets</div>
                    <div class="reps-weight">Reps</div>
                    <div class="tutorial">Tutorial</div>
                    <div class="completed">Done</div>
                </div>
                
                <!-- Exercise Rows -->
                <div class="exercise-row">
                    <div class="exercise-name">Bench Press</div>
                    <div class="sets-range">4</div>
                    <div class="exercise-specs">10-12</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="benchPress"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Incline DB Press</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">10-12</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="inclineDbPress"></div>
                </div>

                <div class="exercise-row">
                    <div class="exercise-name">Machine Fly</div>
                    <div class="sets-range">4</div>
                    <div class="exercise-specs">10-12</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="machineFly"></div>
                </div>

                <div class="exercise-row">
                    <div class="exercise-name">Triceps Pushdown</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">12-15</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="tricepsPushdown"></div>
                </div>

                <div class="exercise-row">
                    <div class="exercise-name">Skull Crushers</div>
                    <div class="sets-range">3</div>
                    <div class="exercise-specs">8-10</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="skullCrushers"></div>
                </div>

                <div class="exercise-row">
                    <div class="exercise-name">Lat Raises</div>
                    <div class="sets-range">4</div>
                    <div class="exercise-specs">12-15</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="latRaises"></div>
                </div>

                <div class="exercise-row">
                    <div class="exercise-name">DB Shoulder Press</div>
                    <div class="sets-range">4</div>
                    <div class="exercise-specs">10-12</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox" name="dbShoulderPress"></div>
                </div>
                
                <!-- Action Buttons -->
                <div class="action-buttons">
                    <button class="btn btn-primary" id="save-progress">Save Progress</button>
                    <button class="btn btn-success" id="complete-workout">Complete Workout</button>
                </div>
            </div>
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
    </script>

    <jsp:include page="footer.jsp"/>
</body>
</html>
