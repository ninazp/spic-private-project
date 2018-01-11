<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!-- _login_page_ --><!--登录超时标记 勿删-->
<!DOCTYPE html>
<html lang="en" class="no-js">

    <head>

        <meta charset="utf-8">
        <!-- <meta name="decorator" content="ani"/> -->
        		
        <!-- Javascript -->
        <script src="${ctxStatic}/common/js/login_js/jquery-1.8.2.min.js"></script>
        <script src="${ctxStatic}/common/js/login_js/jquery.validate.js"></script>
        <script src="${ctxStatic}/common/js/login_js/supersized.3.2.7.min.js"></script>
        <script src="${ctxStatic}/common/js/login_js/scripts.js"></script>
        
		<script>
			if (window.top !== window.self) {
				window.top.location = window.location;
			}
		</script>
		
        <title>国家电投集团中央研究院</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel="stylesheet" href="${ctxStatic}/common/css/login_css/reset.css">
        <link rel="stylesheet" href="${ctxStatic}/common/css/login_css/supersized.css">
        <link rel="stylesheet" href="${ctxStatic}/common/css/login_css/style.css">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        
        <script type="text/javascript">
				$(document).ready(function() {
					$("#loginForm").validate({
						rules: {
							//validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
							username: {
						        required: true
						    }
						},
						messages: {
							username: {
						        required: "请输入用户名"
						    }
							//validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
						},
						errorPlacement: function(error, element) {
							debugger;
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

        <div class="page-container">
            <h1 id = "errorMsg"></h1>
            <img src="${ctxStatic}/common/images/login_2.png"/>
            <sys:message2 content="${message}" showType="1"/>
            <form id="loginForm" role="form" action="${ctx}/login" method="post">
            	<div class="form-group">
                	<input type="text" id="username" name="username" placeholder="用户名">
                </div>
                <div class="form-group">
                	<input type="password" id="password" name="password" placeholder="密码">
                </div>
                <c:if test="${isValidateCodeLogin}">
					<div class="form-group  text-muted">
						<label class="inline"><font color="white">验证码:</font></label>
						<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;" buttonCssStyle="color:white"/>
					</div>
				</c:if>
				<div class="form-group">
                	<button type="submit">登    录</button>
                </div>
                
            </form>
        </div>
        <script type="text/javascript">
        	jQuery(function($){
			    $.supersized({
			    	
			        // Functionality
			        slide_interval     : 4000,    // Length between transitions
			        transition         : 1,    // 0-None, 1-Fade, 2-Slide Top, 3-Slide Right, 4-Slide Bottom, 5-Slide Left, 6-Carousel Right, 7-Carousel Left
			        transition_speed   : 1000,    // Speed of transition
			        performance        : 1,    // 0-Normal, 1-Hybrid speed/quality, 2-Optimizes image quality, 3-Optimizes transition speed // (Only works for Firefox/IE, not Webkit)
			
			        // Size & Position
			        min_width          : 0,    // Min width allowed (in pixels)
			        min_height         : 0,    // Min height allowed (in pixels)
			        vertical_center    : 1,    // Vertically center background
			        horizontal_center  : 1,    // Horizontally center background
			        fit_always         : 0,    // Image will never exceed browser width or height (Ignores min. dimensions)
			        fit_portrait       : 1,    // Portrait images will not exceed browser height
			        fit_landscape      : 0,    // Landscape images will not exceed browser width
			
			        // Components
			        slide_links        : 'blank',    // Individual links for each slide (Options: false, 'num', 'name', 'blank')
			        slides             : [    // Slideshow Images
			                                 {image : '${ctxStatic}/common/images/login_backgrounds/1-1.jpg'},
			                                 {image : '${ctxStatic}/common/images/login_backgrounds/2-2.jpg'},
			                                 {image : '${ctxStatic}/common/images/login_backgrounds/3-3.jpg'}
			                       ]
			
			    });
			
			});
        </script>

    </body>

</html>