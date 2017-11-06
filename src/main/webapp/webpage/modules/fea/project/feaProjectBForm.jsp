<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>项目（子表）管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		alert(11);
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
					jp.post("${ctx}/fea/project/feaProjectB/save",$('#inputForm').serialize(),function(data){
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
				alert('error' + element);
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
	        $('#projectStart').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#startupDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#productDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="feaProjectB" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目编号：</label></td>
					<td class="width-35">
						<form:input path="projectCode" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35">
						<form:input path="projectName" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>供暖面积：</label></td>
					<td class="width-35">
						<form:input path="heatArea" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">类型：</label></td>
					<td class="width-35">
						<sys:treeselect id="fea_project" name="fea_project.id" value="${feaProjectB.fea_project.id}" labelName="fea_project.name" labelValue="${feaProjectB.fea_project.name}"
							title="类型" url="/fea/project/feaProject/treeData" extId="${feaProjectB.id}" cssClass="form-control " allowClear="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>供暖天数：</label></td>
					<td class="width-35">
						<form:input path="heatDays" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>项目起始日期：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='projectStart'>
			                    <input type='text'  name="projectStart" class="form-control"  value="<fmt:formatDate value="${feaProjectB.projectStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>开工日期：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='startupDate'>
			                    <input type='text'  name="startupDate" class="form-control"  value="<fmt:formatDate value="${feaProjectB.startupDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>建设期(月)：</label></td>
					<td class="width-35">
						<form:input path="constructPeriod" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>投产日期：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='productDate'>
			                    <input type='text'  name="productDate" class="form-control"  value="<fmt:formatDate value="${feaProjectB.productDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${feaProjectB.office.id}" labelName="office.name" labelValue="${feaProjectB.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>