<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>财务费用及流动资金管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<script src="${ctxStatic}/common/js/Util-tools.js"></script>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="fea_costfecfVOList.js" %>
	<%@include file="/webpage/modules/fea/project/feaProjectBTreeListPublic.js" %>
	<script type="text/javascript">
		var validateForm;
		function selectproject(){
			/*判断是否是编辑状态*/
			if($("#save").is(":visible")){//saveBtn是否可见
				readOnly(true);
				/* jp.confirm("正在编辑的项目尚未保存，如果切换项目将丢失刚刚录入的数据，是否还要切换项目？", function(){
					readOnly(true);
				},function(){
					return;
				}); */
			}
			/*切换项目清空表单数据*/
			//$('#inputForm')[0].reset();
			$('#inputForm').clearForm();
			$("#inputForm input[type='hidden']").val(null);
			var node = $('#feaProjectjsTree').jstree(true).get_selected(true)[0];
			
        	$('#edit').prop('disabled', !(isNull(node) && isNull(node.id)));
            
			jp.get("${ctx}/fea/fecl/fea_costfecfVO/getFormByProjectId?feaProjectId="+node.id,function(data){
				
				if(data.success){
					
                	//2$table.bootstrapTable('refresh');
                	//jp.success(data.msg);
                	//var test1 = "${fea_design_heatVO}";
                	//var test2 = "${fea_design_heatVO.feaProjectB.projectName}";
                	loadJsonDataToForm(data.body.fea_design_heatVO);
                }else{
       	  			jp.error(data.msg);
                }
			})
		}
		
		/**
	     * 加载json的数据到页面的表单中，以name为唯一标示符加载
	     * @param {String} jsonStr json表单数据
	     */
	    function loadJsonDataToForm(jsonStr){
	        try{
	            //var obj = eval("("+jsonStr+")");
	            var obj = jsonStr;
	            var key,value,tagName,type,arr;
	            for(x in obj){
	                key = x;
	                value = obj[x];
	                
	                if(typeof(value)==="object"){
	                	for(x1 in value){
	                		key1 = x1;
	                		value1 = value[x1];
	                		var keyConcat = key.concat(".").concat(key1);
	                		$("[name='"+keyConcat+"'],[name='"+keyConcat+"[]']").each(function(){
	                			$(this).val(value1);
	                		});
	                	}
	                }else{
	                	$("[name='"+key+"'],[name='"+key+"[]']").each(function(){
		                    tagName = $(this)[0].tagName;
		                    type = $(this).attr('type');
		                    if(tagName=='INPUT'){
		                        if(type=='radio'){
		                            $(this).attr('checked',$(this).val()==value);
		                        }else if(type=='checkbox'){
		                            arr = value.split(',');
		                            for(var i =0;i<arr.length;i++){
		                                if($(this).val()==arr[i]){
		                                    $(this).attr('checked',true);
		                                    break;
		                                }
		                            }
		                        }else{
		                            $(this).val(value);
		                        }
		                    }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
		                        $(this).val(value);
		                    }
		                });
	                }
	            }
	        }catch(e){
	            alert("加载表单出错："+e.message+",数据内容"+JSON.stringify(jsonStr));
	        }
	    }
	    
	    function doSubmit(){
		  if(validateForm.form()){
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}
	    
	    $(document).ready(function() {
			readOnly(true);
			
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/fea/fecl/fea_costfecfVO/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	//$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	readOnly(true);
	                    	//jp.close($topIndex);//关闭dialog
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
		
		/**
			整体表单的change事件
		*/
		function readOnly(flag){
			if(flag){
				$("#edit").show();
				$("#save").hide();
				$("#cancel").hide();
			}else{
				$("#edit").hide();
				$("#save").show();
				$("#cancel").show();
			}
			
			$('tbody input,select,textarea').each(function(index,element){
           		element.disabled = flag;
           	});
			/*
			$('tbody input,select').bind('input propertychange', function()
			{
				if(editStutas){
				}else{
					jp.confirm("确认要开始编辑吗？", function(){
						jp.info("通知信息");
					});
				}
			})
			*/
		}
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">财务费用及流动资金列表</h3>
	</div>
	<div class="panel-body">
		<div class="row">
				<div class="col-sm-4 col-md-3" >
					<div class="form-group">
						<div class="row">
							<div class="col-sm-10" >
								<div class="input-search">
									<button type="submit" class="input-search-btn">
										<i class="fa fa-search" aria-hidden="true"></i></button>
									<input   id="search_q" type="text" class="form-control input-sm" name="" placeholder="查找...">

								</div>
							</div>
							<%-- <div class="col-sm-2" >
								<button  class="btn btn-default btn-sm"  onclick="jp.openDialog('新建项目（主表）', '${ctx}/fea/project/feaProject/form','800px', '500px', $('#feaProjectjsTree'))">
									<i class="fa fa-plus"></i>
								</button>
							</div> --%>
						</div>
					</div>
					<div id="feaProjectjsTree"></div>
				</div>
		<div  class="col-sm-8 col-md-9 animated fadeInRight">
	<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="fea_costfecfVO" class="form form-horizontal well clearfix">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="项目：">项目：</label>
				<sys:gridselect url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${fea_costfecfVO.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${fea_costfecfVO.feaProjectB.projectName}"
					title="选择项目" cssClass="form-control required" fieldLabels="项目名称" fieldKeys="projectName" searchLabels="项目名称" searchKeys="projectName" ></sys:gridselect>
			</div>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<%-- <shiro:hasPermission name="fea:fecl:fea_costfecfVO:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="fea:fecl:fea_costfecfVO:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="fea:fecl:fea_costfecfVO:edit">
			    <button id="save" class="btn btn-danger" onclick="doSubmit()">
	            	<i class="glyphicon glyphicon-ok-sign"></i> 保存
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="fea:fecl:fea_costfecfVO:edit">
			    <button id="cancel" class="btn btn-default" onclick="readOnly(true)">
	            	<i class="glyphicon glyphicon-off"></i> 取消
	        	</button>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="fea:fecl:fea_costfecfVO:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission> --%>
			<%-- <shiro:hasPermission name="fea:fecl:fea_costfecfVO:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/fea/fecl/fea_costfecfVO/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission> --%>
	        	<%-- <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a> --%>
		    </div>
		
	<!-- 表格 -->
	<!-- <table id="fea_costfecfVOTable"   data-toolbar="#toolbar"></table> -->
	<form:form id="inputForm" modelAttribute="fea_costfecfVO" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">项目名称：</label></td>
					<td class="width-35">
						<form:input path="projectname" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">长期借款利率（%）：</label></td>
					<td class="width-35">
						<form:input path="langrate" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">长期借款年限：</label></td>
					<td class="width-35">
						<form:input path="langyear" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">短期借款利率（%）：</label></td>
					<td class="width-35">
						<form:input path="circularate" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">流动资金指标（万元）：</label></td>
					<td class="width-35">
						<form:input path="flowamt" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">流动资金贷款比例（%）：</label></td>
					<td class="width-35">
						<form:input path="flowloanprop" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">流动资金贷款利率（%）：</label></td>
					<td class="width-35">
						<form:input path="flowcaprate" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
	</div>
	</div>
	</div>
</body>
</html>