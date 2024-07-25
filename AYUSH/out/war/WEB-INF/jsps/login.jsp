<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <% 
	   			String contextPath  =   request.getContextPath();
	   			String message 		=   request.getAttribute("message") != null ? "*"+(String) request.getAttribute("message") : "";
		%>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="generator" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
  <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
  <meta name="description" content="">
  <title>AYUSH - For Next Generation</title>
<style type="text/css" title="currentStyle" media="screen">

				@import "<%=contextPath %>/css/style.css";
				@import "<%=contextPath %>/css/bootstrap/css/bootstrap.min.css";
				
</style>

<script src="js/lib/jquery/jquery-1.11.2.min.js"></script>
<script src="js/modules/login/bootstrap.min.js"></script>

<script type='text/javascript' src="js/modules/login/jquery.imageScroller.js"></script>
<script type='text/javascript' src="js/modules/login/test.js"></script>
<script type="text/javascript" src="js/modules/login/login.js"></script>

<script type='text/javascript'>
         $(document).ready(function() {
           //carousel options
           $('#quote-carousel').carousel({
             pause: true, interval: 6000,
           });
         });
      </script>
</head>
<body>
<div id="wraper">
  <!--<div id="header">
    <h1 id="logo"><a href="./"><img src="images/logo1.png" class="hlogo" alt="" title=""></a></h1>
	 <div class="ansptext">Ordnance Factories Board </br></br> <span class="anspundertext">File Tracking System<span></div>
	<div class="drdologo"><img src="images/drdlLogo.png" class="drdlogo" alt="" title=""></div> 
   </div>-->
   <div class="container-fluid">
   		<div class="row">
		<div class="col-md-8 fullHeightBox">
		
		</div>
		<div class="col-md-4 rightLogin">
		<div id="loginbox" class="mainbox">                    
            <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title" style="background-color: green;">User Login </div>
                    </div>    

                   <div class="panel-body" >
                   		<a href="./"><img src="images/logomain.png" class="hlogo" title=""></a>
                   		<div class="OFBtext">AYUSH </div> 
                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-10"></div>
                        <form action="home.htm?actionName=login" method="post" name="loginForm" onsubmit="return validate()"  >
                           <div style="margin-bottom: 20px; margin-top: 20px; " class="input-group">
                              <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                              <input id="login-username" type="text" class="form-control" name="userName" value="" placeholder="Employee Email">                                        
                           </div>
                           <div style="margin-bottom: 15px" class="input-group">
                              <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                              <input id="login-password" type="password" class="form-control" name="password" placeholder="Password">
                           </div>
                           <div style="margin-top:10px" class="form-group">
                              
                              <button id="btn-login" type="submit" class="login_btn plogin">LOGIN</button>
                         
                           </div>
						   <div style="margin-top:10px; margin-bottom:0px;" class="form-group">
                              <!-- Button -->
                              <div class="col-sm-12 controls">
                                 <!-- <a id="btn-fpass" href="#" >Forgot Password ?</a> -->
                              </div>
                           </div>
                        </form>
                        <div id="errorMsg" class="alert"><%= message%></div>
                     </div>                      
                    </div>  
        </div>
		</div>
		</div>      
        
		 <!-- charts -->


 </div>
    

	 
 
</div>
<div id="footer">
 
    
        <p>AYUSH </a></p>

</div>
</body>
</html>
