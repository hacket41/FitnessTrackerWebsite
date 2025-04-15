<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminstats.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <title>Content</title>
</head>
<body>
    <section id="menu">
        <div class="logo">
            <img src="logoone.png" alt="">
            <h2>Fitness Tracker</h2>
        </div>
        <div class="items">
        <ul>
            <li><i class="fa-solid fa-chart-pie"></i><a href = "#">Dashboard</a></li>
            <li><i class="fa-solid fa-users"></i><a href="#">Users</a></li>
            <li><i class="fa-solid fa-chart-simple"></i><a href="#">Statistics</a></li>
            <li><i class="fa-solid fa-gears"></i><a href="#">Content</a></li>
        </ul>
        </div>
    </section>

    <section id="interface">
        <div class = "navigation">
            <div class = "n1">
                
                <div class="search">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" placeholder="Search">
                </div>
            </div>

            <div class="profile">
                <i class="fa-solid fa-user"></i>
                <img src="../profile/user-1.jpg" alt="">
            </div>
        </div>

        <h3 class = "i-name">
            Dashboard
        </h3>

        <div class="content-section">
            <div class="content-card">
                <h4>Upload Meal Plan</h4>
                <form>
                    <input type="text" placeholder="Meal Plan Title" required>
                    <textarea placeholder="Meal Plan Details" required></textarea>
                    <input type="file" accept=".pdf,.doc,.docx">
                    <button type="submit">Upload</button>
                </form>
            </div>
            <div class="content-card">
                <h4>Upload Workout Routine</h4>
                <form>
                    <input type="text" placeholder="Workout Title" required>
                    <textarea placeholder="Workout Details" required></textarea>
                    <input type="file" accept=".pdf,.doc,.docx">
                    <button type="submit">Upload</button>
                </form>
            </div>
            <div class="content-card">
                <h4>Upload Fitness Tips</h4>
                <form>
                    <input type="text" placeholder="Tip Title" required>
                    <textarea placeholder="Tip Details" required></textarea>
                    <input type="file" accept=".pdf,.doc,.docx">
                    <button type="submit">Upload</button>
                </form>
            </div>
        </div>
        
</body>
</html>