<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
    <title>Register</title>
</head>
<body>
   <form action="${pageContext.request.contextPath}/register" method = "post">
   
    <div class="login-box">
        <div class="login-header">
            <header>Register</header>
        </div>
        <div class="input-box">
            <input type="text" name="firstName" id="firstName" class="input-field" placeholder="First-Name" autocomplete="off" required>
        </div>
        <div class="input-box">
            <input type="text" id = "lastName" name = "lastName" class="input-field" placeholder="Last-Name" autocomplete="off" required>
        </div>

        <div class="input-box">
            <input type="text" id="userName" name = "userName" class="input-field"  placeholder="Username" autocomplete="off" required>
        </div>

        <div class="input-box">
            <input type="text" id="email" name="email" class="input-field" placeholder="Email" autocomplete="off" required>
        </div>

        <div class="input-box">
            <input 
                type="date" 
                class="input-field" 
                id="birthday" 
                name="birthday" 
                placeholder="Birthday"
                required>
        </div>
        
		
        <div class="input-box">
            <input type="password" id = "password" name="password" class="input-field" placeholder="Password" autocomplete="off" required>
        </div>

        <div class="input-box">
            <input type="password" id = "retypePassword" name="retypePassword" class="input-field" placeholder="Retype-Password" autocomplete="off" required>
        </div>
      
        <div class="input-submit">
            <button type= "submit" class="submit-btn" id="submit"></button>
            <label for="submit">Sign Up</label>
        </div>
        
    </div>
   </form>
</body>
</html>