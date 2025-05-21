<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />

</head>
<body>
	<jsp:include page = "navbar.jsp"/>

    <!-- HERO SECTION STARTS HERE -->
    <section id = "hero_img">
        <div class = "hero">
            <div class="text">
                <h2>HEALTY LIVING AND MAINTAINENCE</h2>
               <p>
  At <strong>Be Fit</strong>, we believe that a healthy lifestyle starts with small, consistent habits. Our fitness tracker helps you stay on top of your goals by monitoring activity, sleep, heart rate, and more — giving you the insights you need to maintain your well-being. Whether you're just starting out or pushing for peak performance, <strong>Be Fit</strong> keeps you motivated, informed, and on track for a healthier, stronger you.
</p>
                <div class="button">
                    <button>Get Started</button>
                </div>     
            </div> 
        </div>
    </section>

    <!-- SECTION FOR LEARNMORE-->
    <section id="content">
        <div class = "getstarted">
            <h1>SET GOALS<br>SET WORKOUTS<br>STAY ON TRACK</h1>
            <p>Track your workouts, set plan meals, track your macros<br>and discover new goals.</p>
            
            <div class="button-1">
                <button class = "button">Learn More</button>
            </div>
        </div>

        <div class = "image">
            <img src="${pageContext.request.contextPath}/resources/images/feature.jpg" />
        </div>
    </section>
    
    <!-- SECTION BELOW SMALL LEARN MORE  -->
    <section id = "big-container">
        <div class="headingfeature">
            <h1>INDEX</h1>
        </div>
       <div class="page-wrapper">
  <div class="container">
    <!-- Donation Column -->
    <div class="column donation">
      <h2>support healthy lives<br>donate $1</h2>
      <div class="hand-icon">🤲</div>
      <div>Community Support</div>
      <div class="progress">
        <div class="progress-bar">19%</div>
      </div>
      <p>Your donations helps us improve features, support more users, and keep Be Fit free for everyone.</p>
      <p style="color: grey;">Why donations matter</p>
    </div>

    <!-- Be Fit Tracker Column -->
    <div class="column" style="text-align: center;">
      <div class="title-large">BE<br>FIT<br>TRACKER</div>
      <div class="downloads">
        <div style="font-size: 24px;">⌚📱</div>
      </div>
      <p style="color: grey;">Your journey, your pace — track fitness, sleep, and more with Be Fit.</p>
    </div>

    <!-- Get Started Column -->
    <div class="column">
      <div class="section-title">GET<br>STARTED</div>
      <p style="margin-top: 10px; color: grey;">Everything you need to begin:</p>
      <div class="links">
        <p>Set Up Your Device</p>
        <p>Connect to the Be Fit App</p>
        <p>Start Tracking Steps & Sleep</p>
        <p>Create Personal Goals</p>
        <p>Monitor Progress Over Time</p>
      </div>
    </div>

    <!-- The Project Column -->
    <div class="column">
      <div class="section-title">THE<br>MISSION</div>
      <div class="links project-links">
        <p>what drives be fit</p>
        <p>our commitment to privacy</p>
        <p>why it's built for everyone</p>
        <p>meet the creators</p>
        <p>roadmap to future updates</p>
      </div>
    </div>
  </div>
</div>
    </section>

    <!-- NEWSLETTER -->
    <section id ="newsletter" class="sectionforpadding sectionformargin">
		<div class = "newtext">
			<h4>Sign Up For Newsletter</h4>
			<p>Get Email updates about our latest posts and <a href =""><span>workouts</span></p></a>
		</div>
	 </section>

	
    

    <!-- TOGGLE FEATURE -->
    <script>
		function showSidebar() {
		    document.querySelector('.sidebar').style.display = 'flex';
		}
		
		function hideSidebar() {
		    document.querySelector('.sidebar').style.display = 'none';
		}
	</script>
	
	
	
	
    <jsp:include page = "footer.jsp"/>
</body>
</html>