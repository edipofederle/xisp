<style>



#content-login {
	margin: 0px auto 0px auto;
	padding: 10px;
	width: 360px;
	min-height: 270px;
	font-family: Verdana;
	color: #666;
	font-size: 12px;
}

#content-signup {
	margin: 0px auto 0px auto;
	padding: 10px;
	width: 360px;
	min-height: 600px;
	font-family: Verdana;
	color: #666;
	font-size: 12px;
}

.input-login {
	padding: 10px;
	font-size: 25px;
	border: 1px solid #ccc;
	width: 280px;
}
#title {
	background: #F2F6FD;
	font-weight: bold;
	font-size: 16px;
	color: #333;
	letter-spacing: -1px;
	padding: 15px;
	border-bottom: 1px solid #EFEFEF;
}
#align-login{
	margin-left: 35px;
	margin-top: 40px;
	width: 290px;
	height: auto;
}
.button-login {
	background: url("../button_login.jpg") no-repeat;
	width: 98px;
	height: 22px;
	border: none;
	text-align: center;
	font-weight: bold;
	color: #FFF;
}

h2.loginTitle{
	font-family:Verdana;
		background: #F2F6FD;
		font-weight: bold;
		font-size: 16px;
		color:#36393D;
		letter-spacing: -1px;
		padding: 15px;
		border-bottom: 1px solid #EFEFEF;
}

.btn {
  display: inline-block;
  background-color: #e6e6e6;
  background-repeat: no-repeat;
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), color-stop(0.25, #ffffff), to(#e6e6e6));
  background-image: -webkit-linear-gradient(#ffffff, #ffffff 0.25, #e6e6e6);
  background-image: -moz-linear-gradient(#ffffff, #ffffff 0.25, #e6e6e6);
  background-image: -ms-linear-gradient(#ffffff, #ffffff 0.25, #e6e6e6);
  background-image: -o-linear-gradient(#ffffff, #ffffff 0.25, #e6e6e6);
  background-image: linear-gradient(#ffffff, #ffffff 0.25, #e6e6e6);
  padding: 4px 14px;
  text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
  color: #333;
  font-size: 13px;
  line-height: 18px;
  border: 1px solid #ccc;
  border-bottom-color: #bbb;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
}
.btn:hover {
  background-position: 0 -15px;
  color: #333;
  text-decoration: none;
}
.primary {
  background-color: #0064cd;
  background-repeat: repeat-x;
  background-image: -khtml-gradient(linear, left top, left bottom, from(#049cdb), to(#0064cd));
  background-image: -moz-linear-gradient(top, #049cdb, #0064cd);
  background-image: -ms-linear-gradient(top, #049cdb, #0064cd);
  background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #049cdb), color-stop(100%, #0064cd));
  background-image: -webkit-linear-gradient(top, #049cdb, #0064cd);
  background-image: -o-linear-gradient(top, #049cdb, #0064cd);
  background-image: linear-gradient(top, #049cdb, #0064cd);
  color: #fff;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  border: 1px solid #004b9a;
  border-bottom-color: #003f81;
}
.primary:hover {
  color: #fff;
}
.btn {
  -webkit-transition: 0.1s linear all;
  -moz-transition: 0.1s linear all;
  transition: 0.1s linear all;
}
.btn.primary {
  color: #fff;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  border-color: #0064cd #0064cd #003f81;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
}
.btn.primary:hover {
  color: #fff;
}
</style>

<head>
	<script type="text/javascript" src="../js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.js"></script>

	<script>
		$(document).ready(function() {
			$("#formLogin").validate({
			
			rules: {
				email:{
					required: true,
					maxlength: 5
				},
				password:{
					required: true,
					maxlength: 5
				}
			}
			});
		});
	</script>
</head>
<h2 class="loginTitle">Xisp - Login</h2><Br />
<div id="content-login">
	
	<div id="align-login">
		
		<form action="${pageContext.request.contextPath}/login/login" method="post" id="formLogin">
			<input type="text" name="user.email" id="email"  class="input-login required" />
	
			<br /><br />
	
			<input type="password" name="user.password"  id="password" class="input-login required"/>
			<br /><br />
	
			<input type="submit" value="Login" class="btn primary">
		</form>
	</div>
</div>