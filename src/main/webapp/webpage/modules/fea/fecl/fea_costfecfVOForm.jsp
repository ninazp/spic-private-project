<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>财务费用及流动资金管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/fea/fecl/fea_costfecfVO/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

	                    }else{
            	  			jp.error(data.msg);
	                    }
					})
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="fea_costfecfVO" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目编码：</label></td>
					<td class="width-35">
						<form:input path="projectcode" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35">
						<form:input path="projectname" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">流动资金贷款利率（%）：</label></td>
					<td class="width-35">
						<form:input path="circularate" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">短期借款贷款利率（%）：</label></td>
					<td class="width-35">
						<form:input path="shortloanrate" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">流动资金贷款比例（%）：</label></td>
					<td class="width-35">
						<form:input path="circulaloanrate" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">应收账款周转次数：</label></td>
					<td class="width-35">
						<form:input path="receivacount" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">燃料周转次数：</label></td>
					<td class="width-35">
						<form:input path="fuelcount" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">原材料周转次数：</label></td>
					<td class="width-35">
						<form:input path="origincount" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">水费周转次数：</label></td>
					<td class="width-35">
						<form:input path="watercount" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">现金周转次数：</label></td>
					<td class="width-35">
						<form:input path="cashcount" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">发热成本摊销比例（%）：</label></td>
					<td class="width-35">
						<form:input path="heatcostdisprop" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">发热投资分摊次数(%)：</label></td>
					<td class="width-35">
						<form:input path="heatcapdisprop" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>