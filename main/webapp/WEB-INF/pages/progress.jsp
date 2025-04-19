<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Progress</title>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700;800&display=swap">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/progress.css">
</head>
<body>
  <jsp:include page="navbar.jsp"/>

  <section class="hero-section" style="background-image: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/resources/images/herosection.jpg')">
    <div class="hero-content">
      <div class="hero-subtitle">TRACK · IMPROVE · ACHIEVE</div>
      <h1 class="hero-title">MY PROGRESS</h1>
      <p class="hero-description">Monitor your fitness journey, track your improvements, and visualize your path to success.</p>
    </div>
  </section>

  <main>
    <div class="layout">
      <div class="main-content">
        <!-- Stats Section -->
        <h2 class="section-title">PROGRESS OVERVIEW</h2>
        <div class="tracking-stats">
          <div class="stat-card animated fade-in">
            <div>
              <div>CURRENT WEIGHT</div>
              <div class="stat-value">65 kg</div>
            </div>
            <div class="stat-icon">
              <span class="icon">⚖️</span>
            </div>
          </div>

          <div class="stat-card animated fade-in" style="animation-delay: 0.2s;">
            <div>
              <div>CALORIES TODAY</div>
              <div class="stat-value">1200/2500</div>
            </div>
            <div class="stat-icon">
              <span class="icon">🔥</span>
            </div>
          </div>

          <div class="stat-card animated fade-in" style="animation-delay: 0.4s;">
            <div>
              <div>GOAL WEIGHT</div>
              <div class="stat-value">80 kg</div>
            </div>
            <div class="stat-icon">
              <span class="icon">🏋️</span>
            </div>
          </div>
        </div>

        <!-- Chart Section -->
        <div class="chart-section animated slide-up" style="animation-delay: 0.6s;">
          <h2 class="subtitle">WEIGHT PROGRESS</h2>
          <div class="chart-container">
            <canvas id="progressChart"></canvas>
          </div>
        </div>

        <!-- Strength Gains Section -->
        <div class="strength-section animated slide-up" style="animation-delay: 0.8s;">
          <h2 class="subtitle">STRENGTH GAINS</h2>
          <div class="strength-container">
            <div class="strength-item">
              <div class="exercise">BENCH PRESS</div>
              <div class="progress-bar">
                <div class="progress-fill" style="width: 87.5%;">
                  <span class="start-value">60kg</span>
                  <span class="current-value">70kg</span>
                </div>
              </div>
            </div>
            
            <div class="strength-item">
              <div class="exercise">DEADLIFT</div>
              <div class="progress-bar">
                <div class="progress-fill" style="width: 95%;">
                  <span class="start-value">80kg</span>
                  <span class="current-value">95kg</span>
                </div>
              </div>
            </div>
            
            <div class="strength-item">
              <div class="exercise">SQUAT</div>
              <div class="progress-bar">
                <div class="progress-fill" style="width: 90%;">
                  <span class="start-value">90kg</span>
                  <span class="current-value">100kg</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Side Panel for Checklist -->
      <div class="checklist-section animated slide-in-right" style="animation-delay: 1s;">
        <h3 class="checklist-title">TODAY'S CHECKLIST</h3>
        
        <div class="checklist-container">
          <div class="checklist-item">
            <input type="checkbox" id="cardio" name="activity1">
            <label for="cardio">30 minutes of cardio</label>
          </div>
          
          <div class="checklist-item">
            <input type="checkbox" id="strength" name="activity2">
            <label for="strength">Strength training</label>
          </div>
          
          <div class="checklist-item">
            <input type="checkbox" id="meals" name="activity3">
            <label for="meals">Balanced meals</label>
          </div>
          
          <div class="checklist-item">
            <input type="checkbox" id="calories" name="activity4">
            <label for="calories">Tracked calorie intake</label>
          </div>
        </div>
        
        <button class="btn add-task-btn">ADD TASKS</button>
        <button class="btn">SAVE PROGRESS</button>
      </div>
      
      <section id="content" class="animated fade-in" style="animation-delay: 1.2s;">
        <div class="getstarted">
            <h1>SET GOALS<br>SET WORKOUTS<br>STAY ON TRACK</h1>
            <p>Track your workouts, set plan meals, track your macros<br>and discover new goals.</p>
          
          <div class="button-1">
            <button class="button">Get Started</button>
          </div>
        </div>
  
        <div class="image">
          <img src="${pageContext.request.contextPath}/resources/images/feature.jpg" alt="Fitness progress">
        </div>
      </section>
    </div>
  </main>

  <jsp:include page="footer.jsp"/>

  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <script>
    // Chart JS Configuration
    const ctx = document.getElementById('progressChart').getContext('2d');
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5', 'Week 6', 'Week 7', 'Week 8', 'Week 9', 'Week 10'],
        datasets: [{
          label: 'Weight (kg)',
          data: [60, 58, 55, 54, 56, 58, 60, 63, 64, 65],
          borderColor: '#ff6b6b',
          backgroundColor: 'rgba(255, 107, 107, 0.1)',
          borderWidth: 3,
          pointBackgroundColor: '#fff',
          pointBorderColor: '#ff6b6b',
          pointBorderWidth: 2,
          pointRadius: 5,
          fill: true,
          tension: 0.3
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: true,
            position: 'top',
            labels: {
              font: {
                family: 'Montserrat',
                size: 14,
                weight: 'bold'
              }
            }
          }
        },
        scales: {
          y: {
            beginAtZero: false,
            min: 50,
            max: 85,
            grid: {
              color: 'rgba(0, 0, 0, 0.05)'
            },
            ticks: {
              font: {
                family: 'Montserrat'
              }
            }
          },
          x: {
            grid: {
              display: false
            },
            ticks: {
              font: {
                family: 'Montserrat'
              }
            }
          }
        },
        animation: {
          duration: 2000,
          easing: 'easeOutQuart'
        }
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

    // Sidebar toggle functions
    function showSidebar() {
      document.querySelector('.sidebar').style.display = 'flex';
    }
    
    function hideSidebar() {
      document.querySelector('.sidebar').style.display = 'none';
    }
  </script>
</body>
</html>