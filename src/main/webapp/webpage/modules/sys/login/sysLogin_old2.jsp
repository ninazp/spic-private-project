<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!-- _login_page_ --><!--登录超时标记 勿删-->
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>登陆</title>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/common/css/login-styles.css">
<style> 
.div-left{ float:left;width:55%;line-height:400px;margin:0 0 0 40px}
.div-right{ float:right;width:45%;}
</style> 
</head>
<body>
<div align="right" style="margin:15px 18px 0 0">
	<img src="${ctxStatic}/common/images/login-logo1.png" width="20%" />
</div>

<div class="htmleaf-container">
	<div class="wrapper">
		<div class="div-left">
			<img style="opacity:1;" src="${ctxStatic}/common/images/home_background2.png" />
		</div>
		<div class="container div-right">
			<h1></h1>
			<form class="form">
				<input type="text" placeholder="用户名">
				<input type="password" placeholder="密码">
				<button type="submit" id="login-button">登陆</button>
			</form>
		</div>
		
		
		<ul class="bg-bubbles">
			<li></li><li></li><li></li><li></li><li></li><li></li><li></li><li></li><li></li><li></li>
		</ul>
	</div>
</div>

<script src="${ctxStatic}/common/js/jquery-2.1.1.min.js"></script>
<script>
$('#login-button').click(function (event) {
	event.preventDefault();
	$('form').fadeOut(500);
	$('.wrapper').addClass('form-success');
});
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';color:#000000">
</div>
</body>
</html>