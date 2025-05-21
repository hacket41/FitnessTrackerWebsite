<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Contact Us</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css">
</head>
<body>
    <div class="contact-container">
        
        <!-- Back to Home Button -->
        <div class="back-home">
            <a href="${pageContext.request.contextPath}/home">← Back to Home</a>
        </div>

        <h2>Contact Us</h2>

        <form>
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required placeholder="Enter your username">
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" required placeholder="Enter your email">
            </div>

            <button type="submit">Submit</button>

            <!-- Image Section -->
            <div class="image-section">
                <img src="${pageContext.request.contextPath}/resources/images/contact.avif" alt="Contact Image" id="contact-image">
            </div>
        </form>
    </div>
</body>
</html>
