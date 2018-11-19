<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>循环水泵价格管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="fea_design_setxhVOList.js" %>
	<%@include file="/webpage/modules/fea/project/feaProjectBTreeListPublic.js" %>
	
	<script type="text/javascript">
		var validateForm;
		function selectproject(){
			/*判断是否是编辑状态*/
			if($("#save").is(":visible")){//saveBtn是否可见
				readOnly(true);
			}
			
			//readOnly(false);
			
			/*切换项目清空表单数据*/
			$('#inputForm').clearForm();
			$("#inputForm input[type='hidden']").val(null);
			var node = $('#feaProjectjsTree').jstree(true).get_selected(true)[0];
			
		//	$('#edit').prop('disabled', !(isNull(node) && isNull(node.id)));
		
		    $("#edit").enable();
			
			jp.get("${ctx}/fea/set/fea_design_setVO/getFormByProjectId?feaProjectId="+node.id,function(data){
				if(data.success){
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
	     
	     function doEidt(){
	    	 readOnly(false);
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
					jp.post("${ctx}/fea/set/fea_design_setVO/save",$('#inputForm').serialize(),function(data){
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
		<h3 class="panel-title">基本参数信息</h3>
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
			<form:form id="searchForm" modelAttribute="fea_design_setVO" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="项目名称：">项目名称：</label>
				<sys:gridselect url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${fea_design_setVO.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${fea_design_setVO.feaProjectB.projectName}"
					title="选择项目名称" cssClass="form-control required" fieldLabels="项目名称" fieldKeys="projectName" searchLabels="项目名称" searchKeys="projectName" ></sys:gridselect>
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
			<shiro:hasPermission name="fea:design_set:fea_design_setxhVO:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="readOnly(false)">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="fea:design_set:fea_design_setxhVO:edit">
			    <button id="save" class="btn btn-danger" onclick="doSubmit()">
	            	<i class="glyphicon glyphicon-ok-sign"></i> 保存
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="fea:design_set:fea_design_setxhVO:edit">
			    <button id="cancel" class="btn btn-default" onclick="readOnly(true)">
	            	<i class="glyphicon glyphicon-off"></i> 取消
	        	</button>
			</shiro:hasPermission>
    </div>
   <form:form id="inputForm" modelAttribute="fea_design_setxhVO"  class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">循环水泵单价系数：</label></td>
					<td class="width-35">
						<form:input path="cxxs" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">循环水泵单价常量：</label></td>
					<td class="width-35">
						<form:input path="cxcl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">变频控制柜单价系数：</label></td>
					<td class="width-35">
						<form:input path="bpxs" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">变频控制柜单价常量：</label></td>
					<td class="width-35">
						<form:input path="bpcl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">补水泵单价系数：</label></td>
					<td class="width-35">
						<form:input path="bsxs" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">补水泵单价常量：</label></td>
					<td class="width-35">
						<form:input path="bsxl" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
			</tbody>
		</table>
				
		</form:form>
	</div>
	</div>
	</div>
</body>
</html>