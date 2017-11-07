<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>测算方式管理</title>
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
					jp.post("${ctx}/fea/calmethod/fea_incocalmethodVO/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="fea_incocalmethodVO" action="${ctx}/fea/calmethod/fea_incocalmethodVO/save" method="post" class="form-horizontal">
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
					<td class="width-15 active"><label class="pull-right">计算方式：</label></td>
					<td class="width-35">
						<form:input path="calmethod" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">目标收益选择：</label></td>
					<td class="width-35">
						<form:input path="incometype" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">目标值（%）：</label></td>
					<td class="width-35">
						<form:input path="targetval" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">测算方式价格子表：</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">测算方式分利比：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#fea_incocalmethodBVOList', fea_incocalmethodBVORowIdx, fea_incocalmethodBVOTpl);fea_incocalmethodBVORowIdx = fea_incocalmethodBVORowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>项目类别</th>
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
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="fea_incocalmethodBVOList">
				</tbody>
			</table>
			<script type="text/template" id="fea_incocalmethodBVOTpl">//<!--
				<tr id="fea_incocalmethodBVOList{{idx}}">
					<td class="hide">
						<input id="fea_incocalmethodBVOList{{idx}}_id" name="fea_incocalmethodBVOList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="fea_incocalmethodBVOList{{idx}}_delFlag" name="fea_incocalmethodBVOList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_itemtype" name="fea_incocalmethodBVOList[{{idx}}].itemtype" type="text" value="{{row.itemtype}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_unitname" name="fea_incocalmethodBVOList[{{idx}}].unitname" type="text" value="{{row.unitname}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year17" name="fea_incocalmethodBVOList[{{idx}}].year17" type="text" value="{{row.year17}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year18" name="fea_incocalmethodBVOList[{{idx}}].year18" type="text" value="{{row.year18}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year19" name="fea_incocalmethodBVOList[{{idx}}].year19" type="text" value="{{row.year19}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year20" name="fea_incocalmethodBVOList[{{idx}}].year20" type="text" value="{{row.year20}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year21" name="fea_incocalmethodBVOList[{{idx}}].year21" type="text" value="{{row.year21}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year22" name="fea_incocalmethodBVOList[{{idx}}].year22" type="text" value="{{row.year22}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year23" name="fea_incocalmethodBVOList[{{idx}}].year23" type="text" value="{{row.year23}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year24" name="fea_incocalmethodBVOList[{{idx}}].year24" type="text" value="{{row.year24}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year25" name="fea_incocalmethodBVOList[{{idx}}].year25" type="text" value="{{row.year25}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodBVOList{{idx}}_year26" name="fea_incocalmethodBVOList[{{idx}}].year26" type="text" value="{{row.year26}}"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#fea_incocalmethodBVOList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var fea_incocalmethodBVORowIdx = 0, fea_incocalmethodBVOTpl = $("#fea_incocalmethodBVOTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(fea_incocalmethodVO.fea_incocalmethodBVOList)};
					for (var i=0; i<data.length; i++){
						addRow('#fea_incocalmethodBVOList', fea_incocalmethodBVORowIdx, fea_incocalmethodBVOTpl, data[i]);
						fea_incocalmethodBVORowIdx = fea_incocalmethodBVORowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-2" class="tab-pane fade">
			<a class="btn btn-white btn-sm" onclick="addRow('#fea_incocalmethodTVOList', fea_incocalmethodTVORowIdx, fea_incocalmethodTVOTpl);fea_incocalmethodTVORowIdx = fea_incocalmethodTVORowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>项目类别</th>
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
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="fea_incocalmethodTVOList">
				</tbody>
			</table>
			<script type="text/template" id="fea_incocalmethodTVOTpl">//<!--
				<tr id="fea_incocalmethodTVOList{{idx}}">
					<td class="hide">
						<input id="fea_incocalmethodTVOList{{idx}}_id" name="fea_incocalmethodTVOList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="fea_incocalmethodTVOList{{idx}}_delFlag" name="fea_incocalmethodTVOList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_itemtype" name="fea_incocalmethodTVOList[{{idx}}].itemtype" type="text" value="{{row.itemtype}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_unitname" name="fea_incocalmethodTVOList[{{idx}}].unitname" type="text" value="{{row.unitname}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year17" name="fea_incocalmethodTVOList[{{idx}}].year17" type="text" value="{{row.year17}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year18" name="fea_incocalmethodTVOList[{{idx}}].year18" type="text" value="{{row.year18}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year19" name="fea_incocalmethodTVOList[{{idx}}].year19" type="text" value="{{row.year19}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year20" name="fea_incocalmethodTVOList[{{idx}}].year20" type="text" value="{{row.year20}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year21" name="fea_incocalmethodTVOList[{{idx}}].year21" type="text" value="{{row.year21}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year22" name="fea_incocalmethodTVOList[{{idx}}].year22" type="text" value="{{row.year22}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year23" name="fea_incocalmethodTVOList[{{idx}}].year23" type="text" value="{{row.year23}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year24" name="fea_incocalmethodTVOList[{{idx}}].year24" type="text" value="{{row.year24}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year25" name="fea_incocalmethodTVOList[{{idx}}].year25" type="text" value="{{row.year25}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_incocalmethodTVOList{{idx}}_year26" name="fea_incocalmethodTVOList[{{idx}}].year26" type="text" value="{{row.year26}}"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#fea_incocalmethodTVOList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var fea_incocalmethodTVORowIdx = 0, fea_incocalmethodTVOTpl = $("#fea_incocalmethodTVOTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(fea_incocalmethodVO.fea_incocalmethodTVOList)};
					for (var i=0; i<data.length; i++){
						addRow('#fea_incocalmethodTVOList', fea_incocalmethodTVORowIdx, fea_incocalmethodTVOTpl, data[i]);
						fea_incocalmethodTVORowIdx = fea_incocalmethodTVORowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
		</form:form>
</body>
</html>