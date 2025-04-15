<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workout Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/workout.css" />
</head>
<body>
	<jsp:include page = "navbar.jsp"/>
    <div class="container">
        <h1 class="day-title" id="day-title">Sunday</h1>
        <div class="workout-details">
            <div class="workout-info">
                <div class="exercise-name-container">
                    <p>Push</p>
                    <div class="workout-duration">Workout Duration - 45 Mins</div>
                </div>
            </div>
            
            <div class="exercises-table">
                <div class="table-header">
                    <div class="workout-type">Workout Type</div>
                    <div class="reps-weight">Reps x Weight</div>
                    <div class="interval">Interval</div>
                    <div class="tutorial">Tutorial</div>
                    <div class="completed"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Bench Press</div>
                    <div class="exercise-specs">12 x 40kgs</div>
                    <div class="exercise-interval">2 Mins</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Incline DB Press</div>
                    <div class="exercise-specs">12 x 18kgs</div>
                    <div class="exercise-interval">2 Mins</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Machine Fly</div>
                    <div class="exercise-specs">12 x 12kgs</div>
                    <div class="exercise-interval">5 Mins</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Triceps Pushdown</div>
                    <div class="exercise-specs">12 x 25kgs</div>
                    <div class="exercise-interval">2 Mins</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Skull Crushers</div>
                    <div class="exercise-specs">12 x 10kgs</div>
                    <div class="exercise-interval">5 Mins</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Lat Raises</div>
                    <div class="exercise-specs">12 x 5kgs</div>
                    <div class="exercise-interval">2 Mins</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                <div class="exercise-row">
                    <div class="exercise-name">DB Shoulder Press</div>
                    <div class="exercise-specs">12 x 7.5kgs</div>
                    <div class="exercise-interval">End</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Get day parameter from URL
        function getUrlParameter(name) {
            name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
            var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
            var results = regex.exec(location.search);
            return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
        }

        // Set the day title based on URL parameter
        window.onload = function() {
            const day = getUrlParameter('day');
            if (day) {
                document.getElementById('day-title').textContent = day;
                document.title = day + " Workout";
            }
        }
    </script>
    
    <script>
		function showSidebar() {
		    document.querySelector('.sidebar').style.display = 'flex';
		}
		
		function hideSidebar() {
		    document.querySelector('.sidebar').style.display = 'none';
		}
	</script>
    <jsp:include page = "footer.jsp"/>
</body>
</html>
