<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供热参数管理</title>
	<meta name="decorator" content="ani"/>
	<script src="${ctxStatic}/common/js/Util-tools.js"></script>
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
					jp.post("${ctx}/fea/design/fea_design_heatVO/save",$('#inputForm').serialize(),function(data){
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
		
		function callback(projectId){
			
			if(isNull(projectId)){
 				jp.get("${ctx}/fea/project/feaProjectB/getProjectById?id=" + projectId, function (data) {
 					if(data.success){
 						debugger;
 						$("#heatarea").val(data.body.feaProjectB.heatArea*10000);
 				    	$("#heatdays").val(data.body.feaProjectB.heatDays);
 	      	  		}else{
 	      	  			//execute(projectIds);
 	      	  			//jp.error(data.msg);
 	      	  		}
 	            })
 			}
		}
		
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="fea_design_heatVO" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35">
						<sys:gridselectCallback url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${fea_design_heatVO.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${fea_design_heatVO.feaProjectB.projectName}"
							 title="选择项目名称" cssClass="form-control required" fieldLabels="项目名称" fieldKeys="projectName" searchLabels="项目名称" searchKeys="projectName" callBack="true"></sys:gridselectCallback>
					</td>
					<td class="width-15 active"><label class="pull-right">热负荷指标（瓦/平方米）：</label></td>
					<td class="width-35">
						<form:input path="heatload" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">供热面积（平方米）：</label></td>
					<td class="width-35">
						<form:input path="heatarea" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">电费（元/度）：</label></td>
					<td class="width-35">
						<form:input path="powerfee" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">供暖天数：</label></td>
					<td class="width-35">
						<form:select path="heatdays" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('heating_days')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">每日供热小时数（小时）：</label></td>
					<td class="width-35">
						<form:input path="dayheathours" htmlEscape="false"   max="24"  min="0" class="form-control  isFloatLtZero"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">建筑物高度（米）：</label></td>
					<td class="width-35">
						<form:input path="buildheight" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right">分区选择：</label></td>
					<td class="width-35">
						<form:select path="areaselect" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${fea_design_heatVO.office.id}" labelName="office.name" labelValue="${fea_design_heatVO.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>