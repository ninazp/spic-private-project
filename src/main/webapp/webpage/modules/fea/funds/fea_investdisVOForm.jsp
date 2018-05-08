<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>投资分配管理</title>
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
			//windowLoadEnding();
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
		<form:form id="inputForm" modelAttribute="fea_investdisVO" action="${ctx}/fea/funds/fea_investdisVO/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${fea_investdisVO.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${fea_investdisVO.feaProjectB.projectName}"
							 title="选择项目" cssClass="form-control required" fieldLabels="项目名称" fieldKeys="projectName" searchLabels="项目名称" searchKeys="projectName" ></sys:gridselect>
					</td>
					
					<td class="width-15 active"><label class="pull-right">年度：</label></td>
					<td class="width-35">
						<form:input path="year" htmlEscape="false"    class="form-control required"/>
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
				</tr> 
				<tr>
					<td class="width-15 active"><label class="pull-right">建筑面积：</label></td>
					<td class="width-35">
						<form:input path="heatarea" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>--%>
				<tr>
					<%-- <td class="width-15 active"><label class="pull-right">投资比例：</label></td>
					<td class="width-35">
						<form:input path="investprop" htmlEscape="false"    class="form-control required " max="100" data-toggle="tooltip" data-placement="top" title=""/>
					</td> --%>
					<td class="width-15 active"><label class="pull-right">投资额度：</label></td>
					<td class="width-35">
						<form:input path="investamt" htmlEscape="false"  class="form-control required" data-toggle="tooltip" data-placement="top" title=""/>
					</td>
					<%--<td class="width-15 active"><label class="pull-right">可抵扣税金：</label></td>
					<td class="width-35">
						<form:input path="deductvtax" htmlEscape="false"    class="form-control "/>
					</td>--%>
					<td class="width-15 active"><label class="pull-right">注资方合计：</label></td>
					<td class="width-35">
						<form:input path="cappropsum" htmlEscape="false"    class="form-control required" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">融资合计：</label></td>
					<td class="width-35">
						<form:input path="loanpropsum" htmlEscape="false"    class="form-control required" />
					</td>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">分配组成：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#fea_investdisBVOList', fea_investdisBVORowIdx, fea_investdisBVOTpl);fea_investdisBVORowIdx = fea_investdisBVORowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>资金方名称</th>
						<th>资金方类别</th>
						<th>当期比例（%）</th>
						<th>资金金额</th>
						<th>用于建设金额</th>
						<th>用于流动资金金额</th>
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
						<input id="fea_investdisBVOList{{idx}}_investprop" name="fea_investdisBVOList[{{idx}}].investprop" type="text" value="{{row.investprop}}"    class="form-control required checkTableInvestprop" max="100" onChange="investpropChange(fea_investdisBVOList{{idx}}_investprop,fea_investdisBVOList{{idx}}_investamt)"/>
					</td>
					
					
					<td>
						<input id="fea_investdisBVOList{{idx}}_investamt" name="fea_investdisBVOList[{{idx}}].investamt" type="text" value="{{row.investamt}}"    class="form-control " onChange="investamtChange()" />
					</td>
					
					
					<td>
						<input id="fea_investdisBVOList{{idx}}_jsamt" name="fea_investdisBVOList[{{idx}}].jsamt" type="text" value="{{row.jsamt}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_investdisBVOList{{idx}}_ldamt" name="fea_investdisBVOList[{{idx}}].ldamt" type="text" value="{{row.ldamt}}"    class="form-control "/>
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
			
				$(function() {
					 
					 $('input[name=investamt]').keyup(function(e){
					    if(e.keyCode == 13){
					    	investpropChange(null,null);
					    }
					 }); 
				});
			
				var investtotal_pub;//总投资额
				
				function HeaderInputEditEnding(){
					
					$("#investamt").blur(function(event){
						investpropChange(null,null);
					}); 
					/* $("#investprop").blur(function(event){
						var investprop = $("#investprop").val();//投资比例
						if(!isNull(investprop)) {
							return;
						}
						
						var projectId = $("#feaProjectBId").val();
						if(!isNull(projectId)){
							jp.info("请先选择一个项目，再录入比例");
						}
						
						if(!isNull(investtotal_pub)){
							jp.error("获取项目投资总额为空");
							return;
						}
						$("#investamt").val((investtotal_pub * investprop / 100).toFixed(2));
						investpropChange(null,null);
					}); */
				}
				
				function windowLoadEnding(){
					var projectId = $("#feaProjectBId").val();
					if(!isNull(projectId))return;
					jp.get("${ctx}/fea/funds/fea_fundssrcVO/getFea_fundssrcVOByProjectId?projectId=" + projectId, function (data) {
	 					if(data.success){
	 						var investtotal = data.body.Fea_fundssrcVO.investtotal;//投资总金额
	 						var investprop = $("#investprop").val();//投资比例
							if(!isNull(investtotal)){return;}
							investtotal_pub = investtotal;
							$("#investamt").val((investtotal_pub * investprop / 100).toFixed(2));
							$("#investamt").attr("title","总投资额："+investtotal);
							$(function () { $("[data-toggle='tooltip']").tooltip(); });
	 	      	  		}else{
	 	      	  			jp.error("获取项目投资总额失败");
	 	      	  		}
	 	            })
				}
				
				function investamtChange(){
					
					var investamt1_total = 0;//投资金额
					var investamt2_total = 0;//融资金额
					$("#fea_investdisBVOList").find("tr").each(function(index,element){
					
						var investtype = $(element).find('select[id$=_investtype]').val();
						if(isNull(investtype) && investtype == 1){
							investamt1_total = investamt1_total + Number($(element).find('input[id$=_investamt]').val());
						}
						if(isNull(investtype) && investtype == 2){
							investamt2_total = investamt2_total + Number($(element).find('input[id$=_investamt]').val());
						}
					});
					if(investamt1_total != 0){
						$("#cappropsum").val(investamt1_total.toFixed(2));
					}
					if(investamt2_total != 0){
						$("#loanpropsum").val(investamt2_total.toFixed(2));
					}
				}
				
				function investpropChange(investpropDom, investamtDom){
					var investamt = $("#investamt").val();
					
					if(isNull(investpropDom) && isNull(investamtDom)){//逐条算
						investamtDom.value = (investamt * investpropDom.value /100).toFixed(2);
					}else{//全算一遍
						
						$("#fea_investdisBVOList").find("tr").each(function(index,element){
							investpropDom = $(element).find('input[id$=_investprop]');
							investamtDom = $(element).find('input[id$=_investamt]');
							investamtDom.val((investamt * investpropDom.val() /100).toFixed(2));
						});
					}
					investamtChange();
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