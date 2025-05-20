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
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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
          <div class="stat-card">
            <h3>Calories Today</h3>
            <div class="stat-value">${totalCalories} / ${calorieGoal}</div>
            <div class="progress-bar">
              <div class="progress" style="width: ${(totalCalories / calorieGoal) * 100}%"></div>
            </div>
          </div>
          <div class="stat-card">
            <h3>Current Weight</h3>
            <div class="stat-value" id="currentWeightValue">${currentWeight} kg</div>
            <div class="weight-control">
              <div class="weight-buttons">
                <button class="weight-btn decrease" onclick="adjustWeight('decrease')">
                  <i class="fas fa-minus"></i>
                </button>
                <input type="number" id="currentWeightInput" step="0.1" min="0" placeholder="Enter weight">
                <button class="weight-btn increase" onclick="adjustWeight('increase')">
                  <i class="fas fa-plus"></i>
                </button>
              </div>
              <div class="action-buttons">
                <button class="weight-btn update" onclick="updateCurrentWeight()">
                  <i class="fas fa-sync-alt"></i> Update
                </button>
                <button class="weight-btn save" onclick="saveWeight()">
                  <i class="fas fa-save"></i> Save
                </button>
              </div>
            </div>
          </div>
          <div class="stat-card">
            <h3>Goal Weight</h3>
            <div class="stat-value" id="goalWeightValue">${goalWeight} kg</div>
            <div class="weight-control">
              <input type="number" id="goalWeightInput" step="0.1" min="0" placeholder="Enter goal">
              <button class="btn add-task-btn" onclick="updateGoalWeight()">UPDATE GOAL</button>
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

      <!-- Checklist Section -->
      <div class="checklist-section animated slide-in-right" style="animation-delay: 1s;">
        <h3 class="checklist-title">TODAY'S CHECKLIST</h3>
        
        <div class="checklist-container">
          <div id="taskList">
            <!-- Tasks will be added here dynamically -->
          </div>
          
          <div>
            <input type="text" id="newTaskInput" placeholder="Enter a new task...">
            <button class="btn add-task-btn" id="addTask">ADD TASK</button>
          </div>
          
          <div>
            <button class="btn save-btn" id="saveBtn">SAVE LIST</button>
            <button class="btn clear-btn" id="clearBtn">CLEAR ALL</button>
          </div>
          
          <div class="status-bar" id="statusBar"></div>
        </div>
      </div>
    </div>
    
    <!-- Features Section -->
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

  <script>
    let progressChart;
    let tempWeight = parseFloat(${currentWeight}); // Store temporary weight changes

    document.addEventListener('DOMContentLoaded', function() {
      // Progress Chart
      const ctx = document.getElementById('progressChart').getContext('2d');
      progressChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: [],
          datasets: [{
            label: 'Weight (kg)',
            data: [],
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

      // Load initial weight data
      loadWeightHistory();

      // Task List Functionality
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

      // Animate elements when they come into view
      const animatedElements = document.querySelectorAll('.animated');
      animatedElements.forEach(element => {
        element.classList.add('fade-in');
      });
    });

    function loadWeightHistory() {
      const xhr = new XMLHttpRequest();
      xhr.open('GET', '${pageContext.request.contextPath}/progress?action=getWeightHistory', true);
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
          const data = JSON.parse(xhr.responseText);
          updateChart(data);
        }
      };
      xhr.send();
    }

    function updateChart(data) {
      progressChart.data.labels = data.dates;
      progressChart.data.datasets[0].data = data.weights;
      progressChart.update();
    }

    function updateCurrentWeight() {
      const weightInput = document.getElementById('currentWeightInput');
      if (weightInput.value) {
        tempWeight = parseFloat(weightInput.value);
        document.getElementById('currentWeightValue').textContent = tempWeight.toFixed(1) + ' kg';
        weightInput.value = '';
      }
    }

    function adjustWeight(action) {
      const currentWeightDisplay = document.getElementById('currentWeightValue');
      
      if (action === 'increase' || action === 'decrease') {
        const change = action === 'increase' ? 0.1 : -0.1;
        tempWeight = (parseFloat(tempWeight) + change).toFixed(1);
        currentWeightDisplay.textContent = tempWeight + ' kg';
      }
    }

    function saveWeight() {
      const xhr = new XMLHttpRequest();
      xhr.open('POST', '${pageContext.request.contextPath}/progress', true);
      xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            document.getElementById('currentWeightValue').textContent = xhr.responseText + ' kg';
            tempWeight = parseFloat(xhr.responseText);
            loadWeightHistory(); // Reload chart data
          } else {
            alert('Error updating weight: ' + xhr.responseText);
          }
        }
      };
      xhr.send('action=updateWeight&weight=' + tempWeight);
    }

    function updateGoalWeight() {
      const goalWeight = document.getElementById('goalWeightInput').value;
      if (!goalWeight) {
        alert('Please enter a goal weight');
        return;
      }

      const xhr = new XMLHttpRequest();
      xhr.open('POST', '${pageContext.request.contextPath}/progress', true);
      xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            document.getElementById('goalWeightValue').textContent = xhr.responseText + ' kg';
            document.getElementById('goalWeightInput').value = '';
          } else {
            alert('Error updating goal weight: ' + xhr.responseText);
          }
        }
      };
      xhr.send('action=updateGoalWeight&goalWeight=' + goalWeight);
    }
  </script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>

  <style>
    .weight-control {
      margin-top: 15px;
      display: flex;
      flex-direction: column;
      gap: 12px;
    }

    .weight-buttons {
      display: flex;
      gap: 8px;
      align-items: center;
    }

    .action-buttons {
      display: flex;
      gap: 8px;
      justify-content: center;
    }

    .weight-control input {
      flex: 1;
      padding: 10px;
      border: 2px solid #e0e0e0;
      border-radius: 6px;
      text-align: center;
      font-size: 16px;
      font-family: 'Montserrat', sans-serif;
      transition: border-color 0.3s;
      margin-bottom: 8px;
    }

    .weight-control input:focus {
      outline: none;
      border-color: #ff6b6b;
    }

    .weight-btn {
      padding: 10px 16px;
      color: white;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      transition: all 0.3s;
      font-size: 14px;
      font-weight: 600;
      font-family: 'Montserrat', sans-serif;
      display: flex;
      align-items: center;
      gap: 6px;
    }

    .weight-btn i {
      font-size: 14px;
    }

    .weight-btn.decrease {
      background-color: #ff6b6b;
      padding: 10px;
    }

    .weight-btn.increase {
      background-color: #ff6b6b;
      padding: 10px;
    }

    .weight-btn.update {
      background-color: #ff6b6b;
      flex: 1;
    }

    .weight-btn.save {
      background-color: #ff6b6b;
      flex: 1;
    }

    .weight-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 2px 5px rgba(0,0,0,0.2);
      background-color: #ff5252;
    }

    .weight-btn:active {
      transform: translateY(0);
      box-shadow: none;
      background-color: #ff3d3d;
    }

    .btn.add-task-btn {
      width: 100%;
      padding: 12px;
      background-color: #ff6b6b;
      color: white;
      border: none;
      border-radius: 6px;
      font-family: 'Montserrat', sans-serif;
      font-weight: 600;
      font-size: 14px;
      text-transform: uppercase;
      letter-spacing: 1px;
      cursor: pointer;
      transition: all 0.3s;
    }

    .btn.add-task-btn:hover {
      background-color: #ff5252;
      transform: translateY(-2px);
      box-shadow: 0 2px 5px rgba(0,0,0,0.2);
    }

    .btn.add-task-btn:active {
      transform: translateY(0);
      box-shadow: none;
      background-color: #ff3d3d;
    }
  </style>
</body>
</html>