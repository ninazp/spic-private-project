<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>基本参数管理</title>
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
					jp.post("${ctx}/fea/set/fea_design_setVO/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="fea_design_setVO" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${fea_design_setVO.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${fea_design_setVO.feaProjectB.projectName}"
							 title="选择项目名称" cssClass="form-control required" fieldLabels="项目名称" fieldKeys="projectName" searchLabels="项目名称" searchKeys="projectName" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">综合热效率：</label></td>
					<td class="width-35">
						<form:input path="sumheatefficient" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">负荷率：</label></td>
					<td class="width-35">
						<form:input path="loadrate" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">热泵效率：</label></td>
					<td class="width-35">
						<form:input path="pumprate" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">潜水泵水下位置（米）：</label></td>
					<td class="width-35">
						<form:input path="hddept" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">潜水泵压力损失（米）：</label></td>
					<td class="width-35">
						<form:input path="hdlose" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">水密度：</label></td>
					<td class="width-35">
						<form:input path="rou" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">循环水泵扬程：</label></td>
					<td class="width-35">
						<form:input path="hx2" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">蒸发器侧水泵扬程：</label></td>
					<td class="width-35">
						<form:input path="hb2" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">补水泵流量系数（%）：</label></td>
					<td class="width-35">
						<form:input path="mpumpcoe" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">循环水泵单价系数：</label></td>
					<td class="width-35">
						<form:input path="cxxs" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">循环水泵单价常量：</label></td>
					<td class="width-35">
						<form:input path="cxcl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">变频控制柜单价系数：</label></td>
					<td class="width-35">
						<form:input path="bpxs" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">变频控制柜单价常量：</label></td>
					<td class="width-35">
						<form:input path="bpcl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">补水泵单价系数：</label></td>
					<td class="width-35">
						<form:input path="bsxs" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">补水泵单价常量：</label></td>
					<td class="width-35">
						<form:input path="bsxl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>