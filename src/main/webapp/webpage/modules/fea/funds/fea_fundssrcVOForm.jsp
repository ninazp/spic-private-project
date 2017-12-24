<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>资金来源管理</title>
<meta name="decorator" content="ani" />
<script src="${ctxStatic}/common/js/Util-tools.js"></script>
<script type="text/javascript">
	var validateForm;
	var $table; // 父页面table表格id
	var $topIndex; //弹出窗口的 index
	function doSubmit(table, index) { //回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
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
			submitHandler : function(form) {
				jp.post("${ctx}/fea/funds/fea_fundssrcVO/save", $('#inputForm').serialize(), function(data) {
					if (data.success) {
						$table.bootstrapTable('refresh');
						jp.success(data.msg);
						jp.close($topIndex); //关闭dialog

					} else {
						jp.error(data.msg);
					}
				})
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
		HeaderInputEditEnding();
	});

	jQuery.validator.addMethod("checkTable2Capprop",function(value,element){       
  		var cappropCount = 0;
  		$("#fea_fundssrcBVOList").find('input[id$=_capprop]').each(function(index,element){
  			cappropCount = cappropCount + parseInt(element.value);
  		});
  		if(cappropCount > 100){
  			return false;
  		}
        return true;
  
     } ,  "比例合计不能超过100");   
     
   	jQuery.validator.addMethod("checkTable3Loanprop",function(value,element){       
  		var loanpropCount = 0;
  		$("#fea_fundssrcTVOList").find('input[id$=_loanprop]').each(function(index,element){
  			loanpropCount = loanpropCount + parseInt(element.value);
  		});
  		if(loanpropCount > 100){
  			return false;
  		}
        return true;
  
     } ,  "比例合计不能超过100");
     
	function addRow(list, idx, tpl, row) {
		$(list).append(Mustache.render(tpl, {
			idx : idx,
			delBtn : true,
			row : row
		}));
		$(list + idx).find("select").each(function() {
			$(this).val($(this).attr("data-value"));
		});
		$(list + idx).find("input[type='checkbox'], input[type='radio']").each(function() {
			var ss = $(this).attr("data-value").split(',');
			for (var i = 0; i < ss.length; i++) {
				if ($(this).val() == ss[i]) {
					$(this).attr("checked", "checked");
				}
			}
		});
		$(list + idx).find(".form_datetime").each(function() {
			$(this).datetimepicker({
				format : "YYYY-MM-DD HH:mm:ss"
			});
		});
 

	}
	function delRow(obj, prefix) {
		var id = $(prefix + "_id");
		var delFlag = $(prefix + "_delFlag");
		if (id.val() == "") {
			$(obj).parent().parent().remove();
		} else if (delFlag.val() == "0") {
			delFlag.val("1");
			$(obj).html("&divide;").attr("title", "撤销删除");
			$(obj).parent().parent().addClass("error");
		} else if (delFlag.val() == "1") {
			delFlag.val("0");
			$(obj).html("&times;").attr("title", "删除");
			$(obj).parent().parent().removeClass("error");
		}
	}
</script>
</head>
<body class="bg-white">
	<form:form id="inputForm" modelAttribute="fea_fundssrcVO"
		action="${ctx}/fea/funds/fea_fundssrcVO/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<table class="table table-bordered">
			<tbody>
				<%-- <tr>
					<td class="width-15 active"><label class="pull-right">项目编码：</label></td>
					<td class="width-35">
						<form:input path="projectCode" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
						<td class="width-35">
							<form:input path="projectName" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				
				 --%>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目：</label></td>
					<td class="width-35"><sys:gridselect
							url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB"
							name="feaProjectB.id" value="${fea_fundssrcVO.feaProjectB.id}"
							labelName="feaProjectB.projectName"
							labelValue="${fea_fundssrcVO.feaProjectB.projectName}"
							title="选择项目" cssClass="form-control required" fieldLabels="项目名称"
							fieldKeys="projectName" searchLabels="项目名称"
							searchKeys="projectName"></sys:gridselect></td>
					<td class="width-15 active"><label class="pull-right">投资总额：</label></td>
					<td class="width-35"><form:input path="investtotal"
							htmlEscape="false" class="form-control required" /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">增值税抵扣：</label></td>
					<td class="width-35"><form:select path="isdeductvtax"
							class="form-control ">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<td class="width-15 active"><label class="pull-right">可抵扣税金：</label></td>
					<td class="width-35"><form:input path="deductvtax"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">资本金比例(%)：</label></td>
					<td class="width-35"><form:input path="capitalprop"
							htmlEscape="false" class="form-control required" /></td>
					<td class="width-15 active"><label class="pull-right">资本金额度：</label></td>
					<td class="width-35"><form:input path="capitalamt"
							htmlEscape="false" class="form-control required" placeholder="自动输入" readOnly="true"/></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">借款比例(%)：</label></td>
					<td class="width-35"><form:input path="loanprop"
							htmlEscape="false" class="form-control required" /></td>
					<td class="width-15 active"><label class="pull-right">借款金额：</label></td>
					<td class="width-35"><form:input path="loanamt"
							htmlEscape="false" class="form-control required" placeholder="自动输入" readOnly="true"/></td>
				</tr>
			</tbody>
		</table>
		<div class="tabs-container">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1"
					aria-expanded="true">投资来源：</a></li>
				<li class=""><a data-toggle="tab" href="#tab-2"
					aria-expanded="false">融资来源：</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
					<a class="btn btn-white btn-sm"
						onclick="addRow('#fea_fundssrcBVOList', fea_fundssrcBVORowIdx, fea_fundssrcBVOTpl);fea_fundssrcBVORowIdx = fea_fundssrcBVORowIdx + 1;"
						title="新增"><i class="fa fa-plus"></i> 新增</a>
					<table id="table2"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th width="10">&nbsp;</th>
								<th>注资方名称</th>
								<th>币种</th>
								<th>汇率</th>
								<th>比例</th>
								<th>注资金额</th>
							</tr>
						</thead>
						<tbody id="fea_fundssrcBVOList">
						</tbody>
					</table>
					<script type="text/template" id="fea_fundssrcBVOTpl">//<!--
				<tr id="fea_fundssrcBVOList{{idx}}">
					<td class="hide">
						<input id="fea_fundssrcBVOList{{idx}}_id" name="fea_fundssrcBVOList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="fea_fundssrcBVOList{{idx}}_delFlag" name="fea_fundssrcBVOList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#fea_fundssrcBVOList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_capitaltype" name="fea_fundssrcBVOList[{{idx}}].capitaltype" type="text" value="{{row.capitaltype}}"    class="form-control required"/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_currency" name="fea_fundssrcBVOList[{{idx}}].currency" type="text" value="{{row.currency}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_exchangerate" name="fea_fundssrcBVOList[{{idx}}].exchangerate" type="text" value="{{row.exchangerate}}"    class="form-control "/>
					</td>
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_capprop" name="fea_fundssrcBVOList[{{idx}}].capprop" type="text" value="{{row.capprop}}"    class="form-control required checkTable2Capprop" onChange="table2propChange(fea_fundssrcBVOList{{idx}}_capprop, fea_fundssrcBVOList{{idx}}_capamt)"/>
					</td>
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_capamt" name="fea_fundssrcBVOList[{{idx}}].capamt" type="text" value="{{row.capamt}}"    class="form-control required"/>
					</td>
				</tr>//-->
			</script>
					<script type="text/javascript">
				var fea_fundssrcBVORowIdx = 0, fea_fundssrcBVOTpl = $("#fea_fundssrcBVOTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(fea_fundssrcVO.fea_fundssrcBVOList)};
					for (var i=0; i<data.length; i++){
						addRow('#fea_fundssrcBVOList', fea_fundssrcBVORowIdx, fea_fundssrcBVOTpl, data[i]);
						fea_fundssrcBVORowIdx = fea_fundssrcBVORowIdx + 1;
					}
				});
			</script>
				</div>
				<div id="tab-2" class="tab-pane fade pre-scrollable"
					style="height:1000px">
					<a class="btn btn-white btn-sm"
						onclick="addRow('#fea_fundssrcTVOList', fea_fundssrcTVORowIdx, fea_fundssrcTVOTpl);fea_fundssrcTVORowIdx = fea_fundssrcTVORowIdx + 1;"
						title="新增"><i class="fa fa-plus"></i> 新增</a>
					<table id="table3" style="width:1600px;"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th width="10">&nbsp;</th>
								<th width="250">借款方</th>
								<th width="70">币种</th>
								<th width="70">汇率</th>
								<th width="70">比例</th>
								<th width="100">借款金额</th>
								<th width="100">计息次数（年）</th>
								<th width="100">本金利率（%）</th>
								<th width="100">利息利率（%）</th>
								<th width="100">还款方式</th>
								<th width="70">还款期</th>
								<th width="100">承诺费率（%）</th>
								<th width="70">宽限期</th>
							</tr>
						</thead>
						<tbody id="fea_fundssrcTVOList">
						</tbody>
					</table>
					<script type="text/template" id="fea_fundssrcTVOTpl">//<!--
				<tr id="fea_fundssrcTVOList{{idx}}">
					<td class="hide">
						<input id="fea_fundssrcTVOList{{idx}}_id" name="fea_fundssrcTVOList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="fea_fundssrcTVOList{{idx}}_delFlag" name="fea_fundssrcTVOList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#fea_fundssrcTVOList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_loantyp" name="fea_fundssrcTVOList[{{idx}}].loantyp" type="text" value="{{row.loantyp}}"    class="form-control required"/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_currency" name="fea_fundssrcTVOList[{{idx}}].currency" type="text" value="{{row.currency}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_exchangerate" name="fea_fundssrcTVOList[{{idx}}].exchangerate" type="text" value="{{row.exchangerate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_loanprop" name="fea_fundssrcTVOList[{{idx}}].loanprop" type="text" value="{{row.loanprop}}"    class="form-control required checkTable3Loanprop" onChange="table3propChange(fea_fundssrcTVOList{{idx}}_loanprop,fea_fundssrcTVOList{{idx}}_loanamt)"/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_loanamt" name="fea_fundssrcTVOList[{{idx}}].loanamt" type="text" value="{{row.loanamt}}"    class="form-control required"/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_interestcount" name="fea_fundssrcTVOList[{{idx}}].interestcount" type="text" value="{{row.interestcount}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_principalrate" name="fea_fundssrcTVOList[{{idx}}].principalrate" type="text" value="{{row.principalrate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_langrate" name="fea_fundssrcTVOList[{{idx}}].langrate" type="text" value="{{row.langrate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<select id="fea_fundssrcTVOList{{idx}}_repaytype" name="fea_fundssrcTVOList[{{idx}}].repaytype" data-value="{{row.repaytype}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('loan_type')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_repayperiod" name="fea_fundssrcTVOList[{{idx}}].repayperiod" type="text" value="{{row.repayperiod}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_commitrate" name="fea_fundssrcTVOList[{{idx}}].commitrate" type="text" value="{{row.commitrate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_graceperiod" name="fea_fundssrcTVOList[{{idx}}].graceperiod" type="text" value="{{row.graceperiod}}"    class="form-control "/>
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var fea_fundssrcTVORowIdx = 0, fea_fundssrcTVOTpl = $("#fea_fundssrcTVOTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(fea_fundssrcVO.fea_fundssrcTVOList)};
					for (var i=0; i<data.length; i++){
						addRow('#fea_fundssrcTVOList', fea_fundssrcTVORowIdx, fea_fundssrcTVOTpl, data[i]);
						fea_fundssrcTVORowIdx = fea_fundssrcTVORowIdx + 1;
					}
				});
				/*表头编辑后事件*/
				function HeaderInputEditEnding(){
					
					$("#investtotal").blur(function(event){
						var investtotal = $("#investtotal").val();//投资总额
						var capitalprop = $("#capitalprop").val();//资本金比例
						var loanprop = $("#loanprop").val();//借款比例
						if(isNull(investtotal) && isNull(capitalprop)){
							var value = investtotal * capitalprop /100;
							$("#capitalamt").val(value.toFixed(2));
							excuteCapitalTableAmount(value.toFixed(2));
						}
						if(isNull(investtotal) && isNull(loanprop)){
							var value = investtotal * loanprop /100;
							$("#loanamt").val(value.toFixed(2));
							excuteLoanTableAmount(value.toFixed(2));
						}
					});
					$("#capitalprop").blur(function(event){
						var investtotal = $("#investtotal").val();//投资总额
						var capitalprop = $("#capitalprop").val();//资本金比例
						if(isNull(investtotal) && isNull(capitalprop)){
							var value = investtotal * capitalprop /100;
							$("#capitalamt").val(value.toFixed(2));
							excuteCapitalTableAmount(value.toFixed(2));
						}
					});
					$("#loanprop").blur(function(event){
						var investtotal = $("#investtotal").val();//投资总额
						var loanprop = $("#loanprop").val();//借款比例
						if(isNull(investtotal) && isNull(loanprop)){
							var value = investtotal * loanprop / 100;
							$("#loanamt").val(value.toFixed(2));
							excuteLoanTableAmount(value.toFixed(2));
						}
					});
				}
				/*计算表体投资金额*/
				function excuteCapitalTableAmount(capitalamt){
					$("#fea_fundssrcBVOList").find("tr").each(function(index,element){
						var capprop = $(element).find('input[id$=_capprop]').val();
						if(isNull(capprop)){
							$(element).find('input[id$=_capamt]').val((capitalamt * capprop / 100).toFixed(2));
						}
			  		});
				}
				/*计算表体融资金额*/
				function excuteLoanTableAmount(loanamt){
					$("#fea_fundssrcTVOList").find("tr").each(function(index,element){
						var loanprop = $(element).find('input[id$=_loanprop]').val();
						if(isNull(loanprop)){
							$(element).find('input[id$=_loanamt]').val((loanamt * loanprop / 100).toFixed(2));
						}
			  		});
				}
				
				function table2propChange(prop,amt){
					var capitalamt = $("#capitalamt").val();
					amt.value=($("#capitalamt").val() * prop.value /100).toFixed(2);
				}
				function table3propChange(prop,amt){
					var loanamt = $("#loanamt").val();
					amt.value=($("#loanamt").val() * prop.value /100).toFixed(2);
				}
			</script>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>