<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css"/>
    <title>Register</title>
</head>
<body>
    
    <div class="login-box">
        <div class="login-header">
            <header>Register</header>
        </div>
        <div class="input-box">
            <input type="text" class="input-field" placeholder="First-Name" autocomplete="off" required>
        </div>
        <div class="input-box">
            <input type="text" class="input-field" placeholder="Last-Name" autocomplete="off" required>
        </div>
        <div class="input-box">
            <input type="text" class="input-field" placeholder="Email" autocomplete="off" required>
        </div>

        <div class="input-box">
            <input 
                type="text" class="input-field" id="birthday" name="birthday" placeholder="Birthday"
                onfocus="(this.type='date')" 
                onblur="if(this.value==''){this.type='text'}" 
                required>
        </div>
        
		
        <div class="input-box">
            <input type="password" class="input-field" placeholder="Password" autocomplete="off" required>
        </div>

        <div class="input-box">
            <input type="password" class="input-field" placeholder="Retype-Password" autocomplete="off" required>
        </div>
        <div class="forgot">
            <section>
                <input type="checkbox" id="check">
                <label for="check">Remember me</label>
            </section>
            <section>
                <a href="#">Forgot password</a>
            </section>
        </div>
        <div class="input-submit">
            <button class="submit-btn" id="submit"></button>
            <label for="submit">Sign Up</label>
        </div>
        
    </div>
</body>
</html>