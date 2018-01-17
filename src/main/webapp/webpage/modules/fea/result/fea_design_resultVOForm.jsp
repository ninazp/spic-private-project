<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>方案运行费用结果表管理</title>
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
					jp.post("${ctx}/fea/result/fea_design_resultVO/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="fea_design_resultVO" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${fea_design_resultVO.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${fea_design_resultVO.feaProjectB.projectName}"
							 title="选择项目名称" cssClass="form-control required" fieldLabels="项目名称" fieldKeys="projectName" searchLabels="项目名称" searchKeys="projectName" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">结果类别：</label></td>
					<td class="width-35">
						<form:input path="resulttype" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第1年：</label></td>
					<td class="width-35">
						<form:input path="year1" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第2年：</label></td>
					<td class="width-35">
						<form:input path="year2" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第3年：</label></td>
					<td class="width-35">
						<form:input path="year3" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第4年：</label></td>
					<td class="width-35">
						<form:input path="year4" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第5年：</label></td>
					<td class="width-35">
						<form:input path="year5" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第6年：</label></td>
					<td class="width-35">
						<form:input path="year6" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第7年：</label></td>
					<td class="width-35">
						<form:input path="year7" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第8年：</label></td>
					<td class="width-35">
						<form:input path="year8" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第9年：</label></td>
					<td class="width-35">
						<form:input path="year9" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第10年：</label></td>
					<td class="width-35">
						<form:input path="year10" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第11年：</label></td>
					<td class="width-35">
						<form:input path="year11" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第12年：</label></td>
					<td class="width-35">
						<form:input path="year12" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第13年：</label></td>
					<td class="width-35">
						<form:input path="year13" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第14年：</label></td>
					<td class="width-35">
						<form:input path="year14" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第15年：</label></td>
					<td class="width-35">
						<form:input path="year15" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第16年：</label></td>
					<td class="width-35">
						<form:input path="year16" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第17年：</label></td>
					<td class="width-35">
						<form:input path="year17" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第18年：</label></td>
					<td class="width-35">
						<form:input path="year18" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第19年：</label></td>
					<td class="width-35">
						<form:input path="year19" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第20年：</label></td>
					<td class="width-35">
						<form:input path="year20" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第21年：</label></td>
					<td class="width-35">
						<form:input path="year21" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第22年：</label></td>
					<td class="width-35">
						<form:input path="year22" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第23年：</label></td>
					<td class="width-35">
						<form:input path="year23" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第24年：</label></td>
					<td class="width-35">
						<form:input path="year24" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第25年：</label></td>
					<td class="width-35">
						<form:input path="year25" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第26年：</label></td>
					<td class="width-35">
						<form:input path="year26" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第27年：</label></td>
					<td class="width-35">
						<form:input path="year27" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第28年：</label></td>
					<td class="width-35">
						<form:input path="year28" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第29年：</label></td>
					<td class="width-35">
						<form:input path="year29" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第30年：</label></td>
					<td class="width-35">
						<form:input path="year30" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第31年：</label></td>
					<td class="width-35">
						<form:input path="year31" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第32年：</label></td>
					<td class="width-35">
						<form:input path="year32" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第33年：</label></td>
					<td class="width-35">
						<form:input path="year33" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">第34年：</label></td>
					<td class="width-35">
						<form:input path="year34" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">第35年：</label></td>
					<td class="width-35">
						<form:input path="year35" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${fea_design_resultVO.office.id}" labelName="office.name" labelValue="${fea_design_resultVO.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>