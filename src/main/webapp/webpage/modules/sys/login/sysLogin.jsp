<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!-- _login_page_ --><!--登录超时标记 勿删-->
<!DOCTYPE html>
<html>

	<head>
	<style>
		*{margin: 0;padding: 0;}
		#container {
			position: absolute;
			height: 100%;
			width: 100%;
		}
		#output {
			width: 100%;
			height: 100%;
		}
		.color{
			width: 120px;
			height: 20px;
			margin: 0 auto;
			position: fixed;
			left: 50%;
			margin-left: -60px;
			bottom: 20px;
		}
		.color li{
			float: left;
			margin: 0 5px;
			width: 20px;
			height: 20px;
			background: #ccc;
			box-shadow: 0 0 4px #FFF;
			list-style: none;
			cursor: pointer;
		}
		.color li:nth-child(1){
			background: #002c4a;
		}
		.color li:nth-child(2){
			background: #35ac03;
		}
		.color li:nth-child(3){
			background: #ac0908;
		}
		.color li:nth-child(4){
			background: #18bbff;
		}
		.login-page1 {
		  position: absolute;
		  top: 0;
		  left: 0;
		  right: 0;
		  bottom: 0;
		  overflow: auto;
		  text-align: center;
		  padding: 3em;
		}
		.login-page1 h1 {
		  font-weight: 300;
		}
		.login-page1 h1 small {
		}
		.login-page1 .form-group {
		  padding: 8px 0;
		}
		.login-page1 .form-content {
		  padding: 40px 0;
		}
	</style>
	<script src="${ctxStatic}/common/js/login-background-jquery.js"></script>
	<script src="${ctxStatic}/common/js/login-background-vector.js"></script>
	<script>
		$(function(){
			// 初始化 传入dom id
			var victor = new Victor("container", "output");
			var theme = [
					["#002c4a", "#005584"],
					["#35ac03", "#3f4303"],
					["#ac0908", "#cd5726"],
					["#18bbff", "#00486b"]
				]
			$(".color li").each(function(index, val) {
				var color = theme[index];
				 $(this).mouseover(function(){
					victor(color).set();
				 })
			});
		});
	</script>
		<meta name="decorator" content="ani"/>
		<title>${fns:getConfig('productName')} 登录</title>
		<script>
			if (window.top !== window.self) {
				window.top.location = window.location;
			}
		</script>
		<script type="text/javascript">
				$(document).ready(function() {
					$("#loginForm").validate({
						rules: {
							validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
						},
						messages: {
							username: {required: "请填写用户名."},password: {required: "请填写密码."},
							validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
						},
						errorLabelContainer: "#messageBox",
						errorPlacement: function(error, element) {
							error.appendTo($("#loginError").parent());
						} 
					});
				});
				// 如果在框架或在对话框中，则弹出提示并跳转到首页
				if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
					alert('未登录或登录超时。请重新登录，谢谢！');
					top.location = "${ctx}";
				}
		</script>
	
	</head>

	
	<body>
		<div id="container"><div id="output"></div></div>
		<div class="login-page1">
		<div class="row">
			<div class="col-md-4 col-lg-4 col-md-offset-4 col-lg-offset-4">
				<img class="img-circleSP" src="${ctxStatic}/common/images/logo.png" class="user-avatar"/>
				<h1></h1>
				<sys:message content="${message}" showType="1"/>
				<form id="loginForm" role="form" action="${ctx}/login" method="post">
					<div class="form-content">
						<div class="form-group">
							<input type="text" id="username" name="username" class="form-control input-underline input-lg required" placeholder="用户名">
						</div>

						<div class="form-group">
							<input type="password" id="password" name="password" class="form-control input-underline input-lg required" placeholder="密码">
						</div>
						<c:if test="${isValidateCodeLogin}">
						<div class="form-group  text-muted">
								<label class="inline"><font color="white">验证码:</font></label>
							<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;" buttonCssStyle="color:white"/>
						</div>
						</c:if>
							
						<label class="inline">
								<%-- <input  type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} class="ace" />
								<span class="lbl"> 记住我</span> --%>
						</label>
					</div>
					<input type="submit" class="btn btn-white btn-outline btn-lg btn-rounded progress-login"  value="登录">
					
					<%-- <a href="${ctx}/sys/register" class="btn btn-white btn-outsline btn-lg btn-rounded progress-login">注册</a> --%>
				</form>
			</div>			
		</div>
		<div class="">
	         <img alt="image" style="width:100%;opacity:1"class="" src="${ctxStatic}/common/images/home_background.png">
	    </div>
	</div>
	
	<script>

		
$(function(){
		$('.theme-picker').click(function() {
			changeTheme($(this).attr('data-theme'));
		}); 	
	
});

function changeTheme(theme) {
	$('<link>')
	.appendTo('head')
	.attr({type : 'text/css', rel : 'stylesheet'})
	.attr('href', '${ctxStatic}/common/css/app-'+theme+'.css');
	//$.get('api/change-theme?theme='+theme);
	 $.get('${pageContext.request.contextPath}/theme/'+theme+'?url='+window.top.location.href,function(result){  });
}
</script>
<style>
.img-circleSP {
	margin-left:0px;
}
li.color-picker i {
    font-size: 24px;
    line-height: 30px;
}
.red-base {
    color: #D24D57;
}
.blue-base {
    color: #3CA2E0;
}
.green-base {
    color: #27ae60;
}
.purple-base {
    color: #957BBD;
}
.midnight-blue-base {
    color: #2c3e50;
}
.lynch-base {
    color: #6C7A89;
}
</style>
</body>
</html>