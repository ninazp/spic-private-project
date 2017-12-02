<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>生成成本管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jp.loading();
					form.submit();
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
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/fea/procost/fea_productcostVO"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="fea_productcostVO" action="${ctx}/fea/procost/fea_productcostVO/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">项目编码：</label>
					<div class="col-sm-10">
						<form:input path="projectcode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目名称：</label>
					<div class="col-sm-10">
						<form:input path="projectname" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">定员（人）：</label>
					<div class="col-sm-10">
						<form:input path="persons" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">年人均工资：</label>
					<div class="col-sm-10">
						<form:input path="perwage" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">福利费系数（%）：</label>
					<div class="col-sm-10">
						<form:input path="welfare" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">材料费：</label>
					<div class="col-sm-10">
						<form:input path="material" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">保险费率（‰）：</label>
					<div class="col-sm-10">
						<form:input path="insurance" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">泵送费：</label>
					<div class="col-sm-10">
						<form:input path="wateramt" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">供暖费：</label>
					<div class="col-sm-10">
						<form:input path="heatdeposit" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">无形资产摊销（年）：</label>
					<div class="col-sm-10">
						<form:input path="intangibletx" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">其他资产摊销（年）：</label>
					<div class="col-sm-10">
						<form:input path="otherassettx" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">其他费用：</label>
					<div class="col-sm-10">
						<form:input path="othercost" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
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
						<th>第1年</th>
						<th>第2年</th>
						<th>第3年</th>
						<th>第4年</th>
						<th>第5年</th>
						<th>第6年</th>
						<th>第7年</th>
						<th>第8年</th>
						<th>第9年</th>
						<th>第10年</th>
						<th>第11年</th>
						<th>第12年</th>
						<th>第13年</th>
						<th>第14年</th>
						<th>第15年</th>
						<th>第16年</th>
						<th>第17年</th>
						<th>第18年</th>
						<th>第19年</th>
						<th>第20年</th>
						<th>第21年</th>
						<th>第22年</th>
						<th>第23年</th>
						<th>第24年</th>
						<th>第25年</th>
						<th>第26年</th>
						<th>第27年</th>
						<th>第28年</th>
						<th>第29年</th>
						<th>第30年</th>
						<th>第31年</th>
						<th>第32年</th>
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
						<input id="fea_productcostBVOList{{idx}}_year1" name="fea_productcostBVOList[{{idx}}].year1" type="text" value="{{row.year1}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year2" name="fea_productcostBVOList[{{idx}}].year2" type="text" value="{{row.year2}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year3" name="fea_productcostBVOList[{{idx}}].year3" type="text" value="{{row.year3}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year4" name="fea_productcostBVOList[{{idx}}].year4" type="text" value="{{row.year4}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year5" name="fea_productcostBVOList[{{idx}}].year5" type="text" value="{{row.year5}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year6" name="fea_productcostBVOList[{{idx}}].year6" type="text" value="{{row.year6}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year7" name="fea_productcostBVOList[{{idx}}].year7" type="text" value="{{row.year7}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year8" name="fea_productcostBVOList[{{idx}}].year8" type="text" value="{{row.year8}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year9" name="fea_productcostBVOList[{{idx}}].year9" type="text" value="{{row.year9}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year10" name="fea_productcostBVOList[{{idx}}].year10" type="text" value="{{row.year10}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year11" name="fea_productcostBVOList[{{idx}}].year11" type="text" value="{{row.year11}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year12" name="fea_productcostBVOList[{{idx}}].year12" type="text" value="{{row.year12}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year13" name="fea_productcostBVOList[{{idx}}].year13" type="text" value="{{row.year13}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year14" name="fea_productcostBVOList[{{idx}}].year14" type="text" value="{{row.year14}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year15" name="fea_productcostBVOList[{{idx}}].year15" type="text" value="{{row.year15}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year16" name="fea_productcostBVOList[{{idx}}].year16" type="text" value="{{row.year16}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year17" name="fea_productcostBVOList[{{idx}}].year17" type="text" value="{{row.year17}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year18" name="fea_productcostBVOList[{{idx}}].year18" type="text" value="{{row.year18}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year19" name="fea_productcostBVOList[{{idx}}].year19" type="text" value="{{row.year19}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year20" name="fea_productcostBVOList[{{idx}}].year20" type="text" value="{{row.year20}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year21" name="fea_productcostBVOList[{{idx}}].year21" type="text" value="{{row.year21}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year22" name="fea_productcostBVOList[{{idx}}].year22" type="text" value="{{row.year22}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year23" name="fea_productcostBVOList[{{idx}}].year23" type="text" value="{{row.year23}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year24" name="fea_productcostBVOList[{{idx}}].year24" type="text" value="{{row.year24}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year25" name="fea_productcostBVOList[{{idx}}].year25" type="text" value="{{row.year25}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year26" name="fea_productcostBVOList[{{idx}}].year26" type="text" value="{{row.year26}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year27" name="fea_productcostBVOList[{{idx}}].year27" type="text" value="{{row.year27}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year28" name="fea_productcostBVOList[{{idx}}].year28" type="text" value="{{row.year28}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year29" name="fea_productcostBVOList[{{idx}}].year29" type="text" value="{{row.year29}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year30" name="fea_productcostBVOList[{{idx}}].year30" type="text" value="{{row.year30}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year31" name="fea_productcostBVOList[{{idx}}].year31" type="text" value="{{row.year31}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="fea_productcostBVOList{{idx}}_year32" name="fea_productcostBVOList[{{idx}}].year32" type="text" value="{{row.year32}}"    class="form-control "/>
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
		<c:if test="${fns:hasPermission('fea:procost:fea_productcostVO:edit') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>