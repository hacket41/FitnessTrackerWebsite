<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>My Progress</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/progress.css">
</head>
<body>
 <jsp:include page="navbar.jsp"/>

  <div class="container">
    <h1>My Progress</h1>

    <div class="progress-top">
      <div class="graph">
        <canvas id="progressChart"></canvas>
      </div>

      <div class="side-goals">
        <div class="goal-box">
          <span>Calorie Goals</span>
          <span>1200/2500 🔥</span>
        </div>
        <div class="goal-box">
          <span>Weight Goals</span>
          <span>75 Kgs / 80 Kgs 🏋️</span>
        </div>
      </div>
    </div>

    <div class="bottom-section">
      <div class="strength-gains">
        <h2>Strength Gains</h2>
        <h3> Starting Lifts → Current Lifts</h3>
        <p>Bench Press: 60kg → 70kg</p>
        <p>Deadlift: 80kg → 95kg</p>
        <p>Squat: 90kg → 100kg</p>
      </div>

      <div class="checklist">
        <h2>Checklist</h2>
        <div class="checklist-item">
          <input type="radio" name="activity1">
          <label>Did 30 minutes of cardio</label>
        </div>
        <div class="checklist-item">
          <input type="radio" name="activity2">
          <label>Completed strength training</label>
        </div>
        <div class="checklist-item">
          <input type="radio" name="activity3">
          <label>Ate balanced meals</label>
        </div>
        <div class="checklist-item">
          <input type="radio" name="activity4">
          <label>Tracked calorie intake</label>
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <script>
    const ctx = document.getElementById('progressChart').getContext('2d');
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'],
        label: 'Week',
        datasets: [{
          label: 'Weigh (in kgs)',
          data: [60, 58, 55, 54, 56, 58, 60, 63, 64, 65],
          borderColor: '#00FFAA',
          borderWidth: 2,
          fill: false
        }]
      },  
      options: {
        responsive: true,
        animation: true,
        scales: {
          y: {
            beginAtZero: true,
            max: 100
          }
        }
      }
    });
  </script>
  
      <script>
		function showSidebar() {
		    document.querySelector('.sidebar').style.display = 'flex';
		}
		
		function hideSidebar() {
		    document.querySelector('.sidebar').style.display = 'none';
		}
	</script>
	
	
  
   <jsp:include page="footer.jsp"/>
</body>
</html>
    