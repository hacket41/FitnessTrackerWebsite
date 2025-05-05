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

      <div class="checklist-section animated slide-in-right" style="animation-delay: 1s;">
        <h3 class="checklist-title">TODAY'S CHECKLIST</h3>
        
        <div class="checklist-container">
           <div id="taskList">
        </div>
        
        <div class="todo-controls">
            <input type="text" id="newTaskInput" placeholder="Enter a new task...">
            <button class="btn add-task-btn" id="addTask">ADD TASK</button>
        </div>
        
        <div class="todo-controls">
            <button class="btn" id="saveBtn">SAVE LIST</button>
            <button class="btn" id="clearBtn">CLEAR ALL</button>
        </div>
        
        <div class="status-bar" id="statusBar"></div>
    </div>
        </div>
       
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
  </main>

  <jsp:include page="footer.jsp"/>

  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <script>

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


    document.addEventListener('DOMContentLoaded', function() {
        const taskList = document.getElementById('taskList');
        const addTaskBtn = document.getElementById('addTask');
        const newTaskInput = document.getElementById('newTaskInput');
        const saveBtn = document.getElementById('saveBtn');
        const clearBtn = document.getElementById('clearBtn');
        const statusBar = document.getElementById('statusBar');
        
        loadTasks();
        
        addTaskBtn.addEventListener('click', function() {
            addNewTask();
        });
        
        newTaskInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                addNewTask();
            }
        });
        
        saveBtn.addEventListener('click', function() {
            saveTasks();
            showStatus('Tasks saved successfully!');
        });
        
        clearBtn.addEventListener('click', function() {
            if (confirm('Are you sure you want to clear all tasks?')) {
                taskList.innerHTML = '';
                localStorage.removeItem('todoTasks');
                showStatus('All tasks cleared!');
            }
        });
        
        function addNewTask(text = '') {
            const taskText = text || newTaskInput.value.trim();
            
            if (taskText) {
                const taskItem = document.createElement('div');
                taskItem.classList.add('checklist-item', 'fade-in');
                
                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.id = 'task-' + Date.now();
                
                const label = document.createElement('label');
                label.htmlFor = checkbox.id;
                label.textContent = taskText;
                
                const deleteBtn = document.createElement('button');
                deleteBtn.classList.add('delete-btn');
                deleteBtn.innerHTML = '✖';
                deleteBtn.title = 'Delete Task';
                
                taskItem.appendChild(checkbox);
                taskItem.appendChild(label);
                taskItem.appendChild(deleteBtn);
                taskList.appendChild(taskItem);

                newTaskInput.value = '';

                checkbox.addEventListener('change', function() {
                    if (this.checked) {
                        taskItem.classList.add('completed');
                    } else {
                        taskItem.classList.remove('completed');
                    }

                    saveTasks();
                    showStatus('Progress updated!');
                });
                
                deleteBtn.addEventListener('click', function() {
                    taskItem.style.opacity = '0';
                    taskItem.style.transform = 'translateX(20px)';
                    taskItem.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
                    
                    setTimeout(() => {
                        taskItem.remove();
                        saveTasks();
                        showStatus('Task removed!');
                    }, 300);
                });
                
                saveTasks();
                showStatus('Task added!');
                
                newTaskInput.focus();
            }
        }
        
        function saveTasks() {
            const tasks = [];
            const taskItems = taskList.querySelectorAll('.checklist-item');
            
            taskItems.forEach(item => {
                const checkbox = item.querySelector('input[type="checkbox"]');
                const label = item.querySelector('label');
                
                tasks.push({
                    text: label.textContent,
                    completed: checkbox.checked
                });
            });
            
            localStorage.setItem('todoTasks', JSON.stringify(tasks));
        }
        
        function loadTasks() {
            const savedTasks = localStorage.getItem('todoTasks');
            
            if (savedTasks) {
                const tasks = JSON.parse(savedTasks);
                
                tasks.forEach(task => {
                    addNewTask(task.text);

                    const taskItem = taskList.lastElementChild;
                    const checkbox = taskItem.querySelector('input[type="checkbox"]');

                    checkbox.checked = task.completed;

                    if (task.completed) {
                        taskItem.classList.add('completed');
                    }
                });
                
                if (tasks.length > 0) {
                    showStatus('Your saved tasks have been loaded!');
                }
            }
        }
        
        function showStatus(message) {
            statusBar.textContent = message;
            statusBar.style.opacity = '1';

            setTimeout(() => {
                statusBar.style.opacity = '0';
                setTimeout(() => {
                    statusBar.textContent = '';
                }, 300);
            }, 2000);
        }
    });
  </script>
</body>
</html>