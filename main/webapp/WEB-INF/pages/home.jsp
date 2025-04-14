<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
  
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" />

</head>
<body>
	<jsp:include page = "navbar.jsp"/>

    <!-- HERO SECTION STARTS HERE -->
    <section id = "hero_img">
        <div class = "hero">
            <div class="text">
                <h2>HEALTY LIVING AND MAINTAINENCE</h2>
                <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Obcaecati, a 
                    vel officia odio quasi perferendstrum aut.</p>
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
              <h2>help us stay up<br>donate $1</h2>
              <div class="hand-icon">🤲</div>
              <div>Funding Level</div>
              <div class="progress">
                <div class="progress-bar">19%</div>
              </div>
              <p>Just paid bills...</p>
              <p style="color: grey;">What Does This Mean?</p>
            </div>
          
            <!-- Built to Last Column -->
            <div class="column" style="text-align: center;">
              <div class="title-large">BUILT<br>TO<br>LAST</div>
              <div class="downloads">
                <div style="font-size: 24px;">📚</div>
              </div>
            </div>
          
            <!-- Get Started Column -->
            <div class="column">
              <div class="section-title">GET<br>STARTED</div>
              <p style="margin-top: 10px; color: grey;">New to DAREBEE? Start here:</p>
              <div class="links">
                <p>Introduction</p>
                <p>Workout Manual</p>
                <p>Warmup & Stretching</p>
                <p>How to Pick a Program</p>
                <p>Exercise Alternatives</p>
                <p>Certificates & Badges</p>
                <p>Video Exercise Library</p>
                <p>Website Functionality</p>
                <p>Help / Ask a Question</p>
              </div>
            </div>
          
            <!-- The Project Column -->
            <div class="column">
              <div class="section-title">THE<br>PROJECT</div>
              <div class="links project-links">
                <p>about the project</p>
                <p>how we are supported & why</p>
                <p>why we have no ads</p>
                <p>team behind the project</p>
                <p>the internet of tomorrow</p>
                <p>local workshops</p>
                <p>initiatives & collaborations</p>
                <p>print a t-shirt</p>
                <p>popular questions / answered</p>
                <p>copyright & terms of use</p>
              </div>
            </div>
          </div>
        </div>
    </section>

    <!-- NEWSLETTER -->
    <section id ="newsletter" class="sectionforpadding sectionformargin">
		<div class = "newtext">
			<h4>Sign Up For Newsletter</h4>
			<p>Get Email updates about our latest posts and <span>workouts</span></p>
		</div>

		<div class = "form">
			<input type = "text" placeholder="Your Email address">
			<button class = "normal">Sign Up</button>
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