<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>敏感分析（单因素）管理</title>
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
					jp.post("${ctx}/analysisearnings/analysisEarnings/save",$('#inputForm').serialize(),function(data){
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
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			$(list+idx).find(".form_datetime").each(function(){
				 $(this).datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
			    });
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="analysisEarnings" action="${ctx}/analysisearnings/analysisEarnings/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">线条选项：</label></td>
					<td class="width-35">
						<form:radiobuttons path="lineOptions" items="${fns:getDictList('line_options')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">初始投资：</label></td>
					<td class="width-35">
						<form:input path="initialOutlay" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">电费：</label></td>
					<td class="width-35">
						<form:input path="electricityAmount" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">水费：</label></td>
					<td class="width-35">
						<form:input path="waterAmount" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">人工费：</label></td>
					<td class="width-35">
						<form:input path="laborAmount" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">取暖费：</label></td>
					<td class="width-35">
						<form:input path="warmAmount" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${analysisEarnings.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${analysisEarnings.feaProjectB.projectName}"
							 title="选择项目" cssClass="form-control required" fieldLabels="test" fieldKeys="projectName" searchLabels="test1" searchKeys="projectName" ></sys:gridselect>
					</td>
				</tr>
		 	</tbody>
		</table>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">敏感分析（单因素）子表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#analysisEarningsBList', analysisEarningsBRowIdx, analysisEarningsBTpl);analysisEarningsBRowIdx = analysisEarningsBRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="analysisEarningsBList">
				</tbody>
			</table>
			<script type="text/template" id="analysisEarningsBTpl">//<!--
				<tr id="analysisEarningsBList{{idx}}">
					<td class="hide">
						<input id="analysisEarningsBList{{idx}}_id" name="analysisEarningsBList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="analysisEarningsBList{{idx}}_delFlag" name="analysisEarningsBList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<textarea id="analysisEarningsBList{{idx}}_remarks" name="analysisEarningsBList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#analysisEarningsBList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var analysisEarningsBRowIdx = 0, analysisEarningsBTpl = $("#analysisEarningsBTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(analysisEarnings.analysisEarningsBList)};
					for (var i=0; i<data.length; i++){
						addRow('#analysisEarningsBList', analysisEarningsBRowIdx, analysisEarningsBTpl, data[i]);
						analysisEarningsBRowIdx = analysisEarningsBRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
		</form:form>
</body>
</html>