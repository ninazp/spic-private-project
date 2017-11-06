<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资金来源管理</title>
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
					jp.post("${ctx}/fea/funds/fea_fundssrcVO/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="fea_fundssrcVO" action="${ctx}/fea/funds/fea_fundssrcVO/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">项目编码：</label></td>
					<td class="width-35">
						<form:input path="projectCode" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35">
						<form:input path="projectName" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">投资总额：</label></td>
					<td class="width-35">
						<form:input path="invest_Total" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">增值税抵扣：</label></td>
					<td class="width-35">
						<sys:checkbox id="isdeduct_Vtax" name="isdeduct_Vtax" items="${fns:getDictList('')}" values="${fea_fundssrcVO.isdeduct_Vtax}" cssClass="i-checks "/>
					</td>
					<td class="width-15 active"><label class="pull-right">资本金比例(%)：</label></td>
					<td class="width-35">
						<form:input path="capital_Prop" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">资本金额度：</label></td>
					<td class="width-35">
						<form:input path="capital_Amt" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">借款比例(%)：</label></td>
					<td class="width-35">
						<form:input path="loan_Prop" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">借款金额：</label></td>
					<td class="width-35">
						<form:input path="loan_Amt" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">注资循环：</label></td>
					<td class="width-35">
						<sys:checkbox id="is_Capital_cy" name="is_Capital_cy" items="${fns:getDictList('')}" values="${fea_fundssrcVO.is_Capital_cy}" cssClass="i-checks "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${fea_fundssrcVO.office.id}" labelName="office.name" labelValue="${fea_fundssrcVO.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">公司：</label></td>
					<td class="width-35">
						<form:input path="pk_corp" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">资金来源投资子表：</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">资金来源融资子表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#fea_fundssrcBVOList', fea_fundssrcBVORowIdx, fea_fundssrcBVOTpl);fea_fundssrcBVORowIdx = fea_fundssrcBVORowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>备注信息</th>
						<th>注资方</th>
						<th>币种</th>
						<th>汇率</th>
						<th>比例</th>
						<th>额度</th>
						<th width="10">&nbsp;</th>
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
					
					<td>
						<textarea id="fea_fundssrcBVOList{{idx}}_remarks" name="fea_fundssrcBVOList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_capital_type " name="fea_fundssrcBVOList[{{idx}}].capital_type " type="text" value="{{row.capital_type }}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_currency" name="fea_fundssrcBVOList[{{idx}}].currency" type="text" value="{{row.currency}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_exchangerate" name="fea_fundssrcBVOList[{{idx}}].exchangerate" type="text" value="{{row.exchangerate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_cap_prop" name="fea_fundssrcBVOList[{{idx}}].cap_prop" type="text" value="{{row.cap_prop}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcBVOList{{idx}}_cap_amt" name="fea_fundssrcBVOList[{{idx}}].cap_amt" type="text" value="{{row.cap_amt}}"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#fea_fundssrcBVOList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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
				<div id="tab-2" class="tab-pane fade">
			<a class="btn btn-white btn-sm" onclick="addRow('#fea_fundssrcTVOList', fea_fundssrcTVORowIdx, fea_fundssrcTVOTpl);fea_fundssrcTVORowIdx = fea_fundssrcTVORowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>备注信息</th>
						<th>借款类别</th>
						<th>币种</th>
						<th>汇率</th>
						<th>比例</th>
						<th>额度</th>
						<th>固定额度</th>
						<th>计息次数</th>
						<th>本金利率</th>
						<th>利息利率</th>
						<th>承诺费率</th>
						<th>宽限期</th>
						<th>还款期</th>
						<th>还款方式</th>
						<th width="10">&nbsp;</th>
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
					
					<td>
						<textarea id="fea_fundssrcTVOList{{idx}}_remarks" name="fea_fundssrcTVOList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_loan_typ" name="fea_fundssrcTVOList[{{idx}}].loan_typ" type="text" value="{{row.loan_typ}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_currency" name="fea_fundssrcTVOList[{{idx}}].currency" type="text" value="{{row.currency}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_exchangerate" name="fea_fundssrcTVOList[{{idx}}].exchangerate" type="text" value="{{row.exchangerate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_loan_prop" name="fea_fundssrcTVOList[{{idx}}].loan_prop" type="text" value="{{row.loan_prop}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_loan_amt" name="fea_fundssrcTVOList[{{idx}}].loan_amt" type="text" value="{{row.loan_amt}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_fix_amt" name="fea_fundssrcTVOList[{{idx}}].fix_amt" type="text" value="{{row.fix_amt}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_interest_count" name="fea_fundssrcTVOList[{{idx}}].interest_count" type="text" value="{{row.interest_count}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_principal_rate" name="fea_fundssrcTVOList[{{idx}}].principal_rate" type="text" value="{{row.principal_rate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_interest_rate" name="fea_fundssrcTVOList[{{idx}}].interest_rate" type="text" value="{{row.interest_rate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_commit_rate" name="fea_fundssrcTVOList[{{idx}}].commit_rate" type="text" value="{{row.commit_rate}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_grace_period" name="fea_fundssrcTVOList[{{idx}}].grace_period" type="text" value="{{row.grace_period}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_repay_period" name="fea_fundssrcTVOList[{{idx}}].repay_period" type="text" value="{{row.repay_period}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_fundssrcTVOList{{idx}}_repay_type" name="fea_fundssrcTVOList[{{idx}}].repay_type" type="text" value="{{row.repay_type}}"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#fea_fundssrcTVOList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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
			</script>
			</div>
		</div>
		</div>
		</form:form>
</body>
</html>