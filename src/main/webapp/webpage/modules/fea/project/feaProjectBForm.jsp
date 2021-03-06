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
		    init();
		});
		
		/*初始化一些信息*/
		function init(){
			debugger;
		}
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="feaProjectB" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-left"><font color="red">*</font>项目名称：</label></td>
					<td class="width-35">
						<form:input path="projectName" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-left">项目编号：</label></td>
					<td class="width-35">
						<form:input path="projectCode" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-left">项目地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-left">类型：</label></td>
					<td class="width-35">
						<sys:treeselect id="kind" name="kind.id" value="${feaProjectB.kind.id}" labelName="kind.name" labelValue="${feaProjectB.kind.name}"
							title="类型" url="/fea/project/feaProject/treeData" extId="${feaProjectB.id}" cssClass="form-control " allowClear="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-left">供暖面积(万平米)：</label></td>
					<td class="width-35">
						<form:input path="heatArea" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">供暖天数：</label></td>
					<td class="width-35">
						<form:select path="heatDays" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('heating_days')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-left">供暖单价(元/平米)：</label></td>
					<td class="width-35">
						<form:input path="price" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-left">项目起始日期：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='projectStart'>
			                    <input type='text'  name="projectStart" class="form-control required"  value="<fmt:formatDate value="${feaProjectB.projectStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-left">开工日期：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='startupDate'>
			                    <input type='text'  name="startupDate" class="form-control required"  value="<fmt:formatDate value="${feaProjectB.startupDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
					<td class="width-15 active"><label class="pull-left">建设期(月)：</label></td>
					<td class="width-35">
						<form:input path="constructPeriod" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-left">投产日期：</label></td>
					<td class="width-35">
						<p class="input-group">
							<div class='input-group form_datetime' id='productDate'>
			                    <input type='text'  name="productDate" class="form-control required"  value="<fmt:formatDate value="${feaProjectB.productDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
			            </p>
					</td>
					<td class="width-15 active"><label class="pull-left">计算期（年）：</label></td>
					<td class="width-35">
						<form:input path="countyears" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>