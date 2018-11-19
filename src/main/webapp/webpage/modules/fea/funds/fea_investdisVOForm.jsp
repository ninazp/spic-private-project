<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>投资分配管理</title>
<meta name="decorator" content="ani" />
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
					jp.post("${ctx}/fea/funds/fea_investdisVO/save",$('#inputForm').serialize(),function(data){
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
			
			HeaderInputEditEnding();
			
			initreadedit();
			
		});
		
		jQuery.validator.addMethod("checkTableInvestprop",function(value,element){       
	  		var cappropCount = 0;
	  		$("#fea_investdisBVOList").find('input[id$=_investprop]').each(function(index,element){
	  			cappropCount = cappropCount + parseInt(element.value);
	  		});
	  		if(cappropCount > 100){
	  			return false;
	  		}
	        return true;
	    } ,  "比例合计不能超过100");   
		
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
	<form:form id="inputForm" modelAttribute="fea_investdisVO"
		action="${ctx}/fea/funds/fea_investdisVO/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目：</label></td>
					<td class="width-35"><sys:gridselect
							url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB"
							name="feaProjectB.id" value="${fea_investdisVO.feaProjectB.id}"
							labelName="feaProjectB.projectName"
							labelValue="${fea_investdisVO.feaProjectB.projectName}"
							title="选择项目" cssClass="form-control required" fieldLabels="项目名称"
							fieldKeys="projectName" searchLabels="项目名称"
							searchKeys="projectName"></sys:gridselect></td>

					<td class="width-15 active"><label class="pull-right">年度：</label></td>
					<td class="width-35"><form:input path="year"
							htmlEscape="false" class="form-control required" /></td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">是否依赖方案设计：</label></td>
					<td class="width-35"><form:select path="isreaddesgn"
							class="form-control " data-toggle="tooltip" data-placement="top"
							title="">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<td class="width-15 active"><label class="pull-right">打井费用：</label></td>
					<td class="width-35"><form:input path="djamt"
							htmlEscape="false" class="form-control " data-toggle="tooltip"
							data-placement="top" title="" /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">换热站建设费：</label></td>
					<td class="width-35"><form:input path="transamt"
							htmlEscape="false" class="form-control " data-toggle="tooltip"
							data-placement="top" title="" /></td>
					<td class="width-15 active"><label class="pull-right">设备购置费：</label></td>
					<td class="width-35"><form:input path="equitamt"
							htmlEscape="false" class="form-control " data-toggle="tooltip"
							data-placement="top" title="" /></td>
				</tr>
				<tr>
				    <td class="width-15 active"><label class="pull-right">安装费：</label></td>
					<td class="width-35">
						<form:input path="setupamt" htmlEscape="false"    class="form-control" />
					</td>
				
					<td class="width-15 active"><label class="pull-right">管网费：</label></td>
					<td class="width-35"><form:input path="gwamt"
							htmlEscape="false" class="form-control " data-toggle="tooltip"
							data-placement="top" title="" /></td>
					
				</tr>

				<tr>
				    <td class="width-15 active"><label class="pull-right">其他费：</label></td>
					<td class="width-35" ><form:input path="otheramt"
							htmlEscape="false" class="form-control " data-toggle="tooltip"
							data-placement="top" title="" /></td>
					<td class="width-15 active" style="display:none;" id="hidetd" ><label class="pull-right">投资额度：</label></td>
					<td class="width-35" style="display:none;" id="hidetd"><form:input path="investamt"
							htmlEscape="false" class="form-control required"
							data-toggle="tooltip" data-placement="top" title="" /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35"><form:textarea path="remarks"
							htmlEscape="false" rows="4" class="form-control " /></td>
				</tr>
			</tbody>
		</table>
		<div class="tabs-container">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1"
					aria-expanded="true">分配组成：</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
					<a class="btn btn-white btn-sm"
						onclick="addRow('#fea_investdisBVOList', fea_investdisBVORowIdx, fea_investdisBVOTpl);fea_investdisBVORowIdx = fea_investdisBVORowIdx + 1;"
						title="新增"><i class="fa fa-plus"></i> 新增</a>
					<table class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>资金方名称</th>
								<th>资金方类别</th>
								<th>当期比例（%）</th>
								<th width="10">&nbsp;</th>
							</tr>
						</thead>
						<tbody id="fea_investdisBVOList">
						</tbody>
					</table>
					<script type="text/template" id="fea_investdisBVOTpl">//<!--
				<tr id="fea_investdisBVOList{{idx}}">
					<td class="hide">
						<input id="fea_investdisBVOList{{idx}}_id" name="fea_investdisBVOList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="fea_investdisBVOList{{idx}}_delFlag" name="fea_investdisBVOList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="fea_investdisBVOList{{idx}}_zjname" name="fea_investdisBVOList[{{idx}}].zjname" type="text" value="{{row.zjname}}"    class="form-control "/>
					</td>
					
					
					<td>
						<select id="fea_investdisBVOList{{idx}}_investtype" name="fea_investdisBVOList[{{idx}}].investtype" data-value="{{row.investtype}}" class="form-control m-b  required">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('distrtype')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<input id="fea_investdisBVOList{{idx}}_investprop" name="fea_investdisBVOList[{{idx}}].investprop" type="text" value="{{row.investprop}}"    class="form-control required checkTableInvestprop" max="100" />
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#fea_investdisBVOList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
					<script type="text/javascript">
				var fea_investdisBVORowIdx = 0, fea_investdisBVOTpl = $("#fea_investdisBVOTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(fea_investdisVO.fea_investdisBVOList)};
					for (var i=0; i<data.length; i++){
						addRow('#fea_investdisBVOList', fea_investdisBVORowIdx, fea_investdisBVOTpl, data[i]);
						fea_investdisBVORowIdx = fea_investdisBVORowIdx + 1;
					}
				});
			</script>
					<script type="text/javascript">
			
				var investtotal_pub;//总投资额
				
				function HeaderInputEditEnding(){
					$("#investamt").blur(function(event){
						investpropChange(null,null);
					}); 					
					$("#djamt").blur(function(event){
						investpropChange(null,null);
					});
					
					$("#transamt").blur(function(event){
						investpropChange(null,null);
					});
					
					$("#equitamt").blur(function(event){
						var equitamtnew = $("#equitamt").val();//投资比例
						if(!isNull(equitamtnew)) {
							$("#setupamt").val(0.00);
						}else{
							$("#setupamt").val((Number(equitamtnew)*0.15).toFixed(2));
						}
						investpropChange(null,null);
					});
					
					$("#gwamt").blur(function(event){
						investpropChange(null,null);
					});
					
					$("#otheramt").blur(function(event){
						investpropChange(null,null);
					});
					
					$("#isreaddesgn").blur(function(event){
						var isreaddesgn = $("#isreaddesgn").val();//投资比例
						if(!isNull(isreaddesgn)) {
							return;
						}
						isreadedit(isreaddesgn);
					});
				}
								
				function initreadedit(){
					
					var isreadinit = $("#isreaddesgn").val();
					if(!isNull(isreadinit)){
						return;
					}
					if(Number(isreadinit)==1){
						document.getElementById("otheramt").readOnly=true;
						document.getElementById("gwamt").readOnly=true;
						document.getElementById("equitamt").readOnly=true;
						document.getElementById("transamt").readOnly=true;
						document.getElementById("djamt").readOnly=true;
					}else{
						document.getElementById("otheramt").readOnly=false;
						document.getElementById("gwamt").readOnly=false;
						document.getElementById("equitamt").readOnly=false;
						document.getElementById("transamt").readOnly=false;
						document.getElementById("djamt").readOnly=false;
					}
				}
				
				function isreadedit(isread){
					if(Number(isread)==1){
						document.getElementById("otheramt").readOnly=true;
						document.getElementById("gwamt").readOnly=true;
						document.getElementById("equitamt").readOnly=true;
						document.getElementById("transamt").readOnly=true;
						document.getElementById("djamt").readOnly=true;
					}else{
						document.getElementById("otheramt").readOnly=false;
						document.getElementById("gwamt").readOnly=false;
						document.getElementById("equitamt").readOnly=false;
						document.getElementById("transamt").readOnly=false;
						document.getElementById("djamt").readOnly=false;
					}
				}
				
				function investpropChange(investpropDom, investamtDom){   
					var djamt = $("#djamt").val();
					var transamt =$("#transamt").val();
					var equitamt = $("#equitamt").val();
					var gwamt = $("#gwamt").val();
                    var otheramt = $("#otheramt").val();
                    var setupamt = $("#setupamt").val();
                    var intputinvestamt = $("#investamt").val();
                    var isreaddesgn = $("#isreaddesgn").val();//投资比例
                    
                    
                    
                    var investamt = 0;
                    
                    if(Number(isreaddesgn)==1){
                    	investamt = intputinvestamt;
                    }else{
                    	investamt = Number(djamt) + Number(transamt) + Number(equitamt) + Number(gwamt) + Number(otheramt)+Number(setupamt);
                    }
                    
                    $("#investamt").val(Number(investamt));
				}
				
				function tableChange(prop,amt){
				//fea_investdisBVOList
					var capitalamt = $("#capitalamt").val();
					amt.value=($("#capitalamt").val() * prop.value /100).toFixed(2);
				}
			</script>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>