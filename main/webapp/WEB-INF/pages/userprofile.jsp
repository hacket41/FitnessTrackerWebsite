<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userprofile.css">
</head>
<body>
    <div class="container">
        <div class="sidebar">
          <ul>
            <li class="active">User-Information</li>
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Log-Out</a></li>
          </ul>
        </div>
    
        <div class="content">
          <h2>My Profile</h2>
    
          <div class="profile-pic">
            <div class="avatar">👤</div>
          </div>
    
          <form>
            <div class="form-group">
              <label>First-Name</label>
              <input type="text">
            </div>

            <div class="form-group">
                <label>Last-Name</label>
                <input type="text">
            </div>
    
            <div class="form-group">
              <label>Username</label>
              <input type="text">
            </div>
    
            <div class="form-group">
              <label>Email Address</label>
              <input type="text" >
            </div>
    
            <div class="form-group">
              <label>Password</label>
              <input type="text" placeholder="Phone Number">
            </div>

            <button class="save-btn">Save Changes</button>
          </form>
        </div>
      </div>
</body>
</html>