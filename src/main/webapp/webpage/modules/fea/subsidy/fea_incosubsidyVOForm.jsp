<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>补贴收入管理</title>
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
					jp.post("${ctx}/fea/subsidy/fea_incosubsidyVO/save",$('#inputForm').serialize(),function(data){
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
			hideCol();
			inputEvent();
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="fea_incosubsidyVO" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${fea_incosubsidyVO.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${fea_incosubsidyVO.feaProjectB.projectName}"
							 title="选择项目" cssClass="form-control required" fieldLabels="项目名称" fieldKeys="projectName" searchLabels="项目名称" searchKeys="projectName" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">补贴类型：</label></td>
					<td class="width-35">
						<form:input path="subsidytype" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<%-- <tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35">
						<form:input path="projectname" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目编码：</label></td>
					<td class="width-35">
						<form:input path="projectcode" htmlEscape="false"    class="form-control "/>
					</td>
					
				</tr> --%>
				<tr>
					<td class="width-15 active"><label class="pull-right">单位：</label></td>
					<td class="width-35">
						<form:input path="unitname" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否纳税：</label></td>
					<td class="width-35">
						<form:select path="ispaytax" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
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
		 	</tbody>
		</table>
		<script type="text/javascript">
				
				function hideCol(){
					//jp.loading();
					var projectId = $("#feaProjectBId").val();
					
					jp.get("${ctx}/fea/project/feaProjectB/getProjectById?id=" + projectId, function (data) {
						if(data.success){
						    var countyears = data.body.feaProjectB.countyears;//计算期
						    var alltd = $('tbody tr td');
						    var len = alltd.length;
						    if(isNull(countyears) && isNull(len)){
						    	var startHidecol = countyears*2 + 8;  //8：前四个固定字段 *2（每隔字段两个td）
					    		alltd.each(function(index,element){
					    			if(index >= startHidecol){
					    				$(this).addClass("hide");
					    			}
					    		});
						    }
		      	  		}else{
		      	  			jp.error(data.msg);
		      	  		}
		            });
					//jp.close();
				}
				
				function inputEvent(){
					var allinput = $('tbody input');
					var projectId = $("#feaProjectBId").val();
					jp.get("${ctx}/fea/project/feaProjectB/getProjectById?id=" + projectId, function (data) {
						if(data.success){
						    var countyears = data.body.feaProjectB.countyears;//计算期
						    allinput.each(function(index,element){
								if(index < 4){
									return true;
								}
								$(this).bind('keypress',function(event){//键盘事件
						            if(event.keyCode == "13")
						            {	
						            	var changeValue = '';
						            	allinput.each(function(index,element){
						            		if(index>countyears+3){
						            			return true;
						            		}
						            		if(event.delegateTarget === this){
						            			changeValue = $(this).val();
						            		}
						            		if(isNull(changeValue)){
						            			element.value = changeValue;
						            		}
						            	});
						            }
						        });
						        $(this).blur(function(event){//失去焦点
									var changeValue = '';
					            	allinput.each(function(index,element){
					            		if(index>countyears+3){
					            			//alert(index + '-' +countyears);
					            			return true;
					            		}
					            		if(event.delegateTarget === this){
					            			changeValue = $(this).val();
					            		}
					            		if(isNull(changeValue)){
					            			element.value = changeValue;
					            		}
					            	});
								});
							});
						}
					});
				}
			</script>
	</form:form>
</body>
</html>