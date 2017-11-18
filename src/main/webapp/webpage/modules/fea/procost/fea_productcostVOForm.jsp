<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>生成成本管理</title>
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
					jp.post("${ctx}/fea/procost/fea_productcostVO/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="fea_productcostVO" action="${ctx}/fea/procost/fea_productcostVO/save" method="post" class="form-horizontal">
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
					<td class="width-15 active"><label class="pull-right">定员（人）：</label></td>
					<td class="width-35">
						<form:input path="persons" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">年人均工资：</label></td>
					<td class="width-35">
						<form:input path="perwage" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">福利费系数（%）：</label></td>
					<td class="width-35">
						<form:input path="welfare" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">材料费：</label></td>
					<td class="width-35">
						<form:input path="material" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">保险费率（‰）：</label></td>
					<td class="width-35">
						<form:input path="insurance" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">泵送费：</label></td>
					<td class="width-35">
						<form:input path="wateramt" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">供暖费：</label></td>
					<td class="width-35">
						<form:input path="heatdeposit" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">无形资产摊销（年）：</label></td>
					<td class="width-35">
						<form:input path="intangibletx" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">其他资产摊销（年）：</label></td>
					<td class="width-35">
						<form:input path="otherassettx" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">其他费用：</label></td>
					<td class="width-35">
						<form:input path="othercost" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">成本种类：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#fea_productcostBVOList', fea_productcostBVORowIdx, fea_productcostBVOTpl);fea_productcostBVORowIdx = fea_productcostBVORowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>成本种类</th>
						<th>单位</th>
						<th>2017</th>
						<th>2018</th>
						<th>2019</th>
						<th>2020</th>
						<th>2021</th>
						<th>2022</th>
						<th>2023</th>
						<th>2024</th>
						<th>2025</th>
						<th>2026</th>
						<th>2027</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="fea_productcostBVOList">
				</tbody>
			</table>
			<script type="text/template" id="fea_productcostBVOTpl">//<!--
				<tr id="fea_productcostBVOList{{idx}}">
					<td class="hide">
						<input id="fea_productcostBVOList{{idx}}_id" name="fea_productcostBVOList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="fea_productcostBVOList{{idx}}_delFlag" name="fea_productcostBVOList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_costtype" name="fea_productcostBVOList[{{idx}}].costtype" type="text" value="{{row.costtype}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_costunit" name="fea_productcostBVOList[{{idx}}].costunit" type="text" value="{{row.costunit}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2017" name="fea_productcostBVOList[{{idx}}].year2017" type="text" value="{{row.year2017}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2018" name="fea_productcostBVOList[{{idx}}].year2018" type="text" value="{{row.year2018}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2019" name="fea_productcostBVOList[{{idx}}].year2019" type="text" value="{{row.year2019}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2020" name="fea_productcostBVOList[{{idx}}].year2020" type="text" value="{{row.year2020}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2021" name="fea_productcostBVOList[{{idx}}].year2021" type="text" value="{{row.year2021}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2022" name="fea_productcostBVOList[{{idx}}].year2022" type="text" value="{{row.year2022}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2023" name="fea_productcostBVOList[{{idx}}].year2023" type="text" value="{{row.year2023}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2024" name="fea_productcostBVOList[{{idx}}].year2024" type="text" value="{{row.year2024}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2025" name="fea_productcostBVOList[{{idx}}].year2025" type="text" value="{{row.year2025}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2026" name="fea_productcostBVOList[{{idx}}].year2026" type="text" value="{{row.year2026}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2027" name="fea_productcostBVOList[{{idx}}].year2027" type="text" value="{{row.year2027}}"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#fea_productcostBVOList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var fea_productcostBVORowIdx = 0, fea_productcostBVOTpl = $("#fea_productcostBVOTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(fea_productcostVO.fea_productcostBVOList)};
					for (var i=0; i<data.length; i++){
						addRow('#fea_productcostBVOList', fea_productcostBVORowIdx, fea_productcostBVOTpl, data[i]);
						fea_productcostBVORowIdx = fea_productcostBVORowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
		</form:form>
</body>
</html>