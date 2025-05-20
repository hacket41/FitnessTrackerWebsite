<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Fitness Tracker Dashboard</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstat.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
  <section id="menu">
    <div class="logo">
      <img src="${pageContext.request.contextPath}/resources/images/logoone.png" alt="">
      <h2>Fitness Tracker</h2>
    </div>
    <div class="items">
      <ul>
       <li><i class="fa-solid fa-chart-pie"></i><a href ="${pageContext.request.contextPath}/admin">Dashboard</a></li>
            <li><i class="fa-solid fa-users"></i><a href="${pageContext.request.contextPath}/adminusers">Users</a></li>
            <li><i class="fa-solid fa-chart-simple"></i><a href="${pageContext.request.contextPath}/adminstat">Statistics</a></li>
            <li><i class="fa-solid fa-gears"></i><a href="${pageContext.request.contextPath}/admincontent">Content</a></li>
            <li><i class="fa-solid fa-house"></i><a href="${pageContext.request.contextPath}/home">Home</a></li>
      </ul>
    </div>
  </section>

  <div class="main">
    <div class="header">Statistics</div>

    <div class="stats-cards">
      <div class="card">
        <h3>New Users</h3>
        <p>${newUsers}</p>
      </div>
      <div class="card">
        <h3>Meal Plans</h3>
        <p>${mealPlans}</p>
      </div>
      <div class="card">
        <h3>Workout Routines</h3>
        <p>${workoutRoutines}</p>
      </div>
      <div class="card">
        <h3>Progress</h3>
        <p>${progress}%</p>
      </div>
    </div>

    <div class="charts">
      <div class="chart-box">
        <div class="chart-title">Weekly Active Users</div>
        <c:forEach items="${weeklyActiveUsers}" var="count">
          <div class="bar bar-${count}"></div>
        </c:forEach>
      </div>

      <div class="chart-box">
        <div class="chart-title">Meal Plan Engagement</div>
        <c:forEach items="${mealPlanEngagement}" var="count">
          <div class="bar bar-${count}"></div>
        </c:forEach>
      </div>
    </div>

    <div class="extra-section">
      <div class="pie-chart-box">
        <div class="chart-title">User Engagement Breakdown</div>
        <canvas id="pieChart"></canvas>
      </div>
    </div>
  </div>

  <script>
    const ctx = document.getElementById('pieChart').getContext('2d');
    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: [
          <c:forEach items="${userEngagement}" var="stat">
            '${stat.category}',
          </c:forEach>
        ],
        datasets: [{
          label: 'User Engagement',
          data: [
            <c:forEach items="${userEngagement}" var="stat">
              ${stat.count},
            </c:forEach>
          ],
          backgroundColor: [
            '#5c6ef8',
            '#a3a8f0',
            '#e1e3f8'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'bottom'
          }
        }
      }
    });
  </script>



<script>
  // Pie Chart code
  const ctxPie = document.getElementById('pieChart').getContext('2d');
  new Chart(ctxPie, {
    type: 'pie',
    data: {
      labels: ['Active', 'Inactive'],
      datasets: [{
        label: 'Users',
        data: [70, 30],
        backgroundColor: ['#5c6ef8', '#e2e5f3']
      }]
    },
    options: {
      responsive: false
    }
  });

  // Bar Chart code
  const ctxBar = document.getElementById('barChart').getContext('2d');
  new Chart(ctxBar, {
    type: 'bar',
    data: {
      labels: ['Cardio', 'Strength', 'Flexibility', 'Nutrition'],
      datasets: [{
        label: 'Progress (%)',
        data: [60, 80, 45, 70],
        backgroundColor: '#5c6ef8'
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true,
          max: 100
        }
      },
      responsive: false
    }
  });
</script>
</body>
</html>
    