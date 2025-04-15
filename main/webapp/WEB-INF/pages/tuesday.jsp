<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/workout.css" />
</head>
<body>
<jsp:include page ="navbar.jsp"/>
<div class="container">
        <h1 class="day-title" id="day-title">Tuesday</h1>
        <div class="workout-details">
            <div class="workout-info">
                <div class="exercise-name-container">
                    <p>Exercise Name</p>
                    <div class="workout-duration">Workout Duration</div>
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
                    <div class="exercise-name">Exercise 1</div>
                    <div class="exercise-specs">Reps x Weight</div>
                    <div class="exercise-interval">Interval</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Exercise 2</div>
                    <div class="exercise-specs">Reps x Weight</div>
                    <div class="exercise-interval">Interval</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Exercise 3</div>
                    <div class="exercise-specs">Reps x Weight</div>
                    <div class="exercise-interval">Interval</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Exercise 4</div>
                    <div class="exercise-specs">Reps x Weight</div>
                    <div class="exercise-interval">Interval</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Exercise 5</div>
                    <div class="exercise-specs">Reps x Weight</div>
                    <div class="exercise-interval">Interval</div>
                    <div class="exercise-tutorial"><a href="#" class="tutorial-link">Tutorial</a></div>
                    <div class="exercise-check"><input type="checkbox" class="exercise-checkbox"></div>
                </div>
                
                <div class="exercise-row">
                    <div class="exercise-name">Exercise 6</div>
                    <div class="exercise-specs">Reps x Weight</div>
                    <div class="exercise-interval">Interval</div>
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
   <jsp:include page="footer.jsp">
</body>
</html>