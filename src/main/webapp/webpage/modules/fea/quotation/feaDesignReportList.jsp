<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>设备选型报价清单管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<script src="${ctxStatic}/common/js/Util-tools.js"></script>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="feaDesignReportList.js" %>
	<%@include file="feaDesignReportListTree.js" %>
	<script type="text/javascript">
		function selectproject(){
			var node = $('#feaProjectjsTree').jstree(true).get_selected(true)[0];
			$("#feaProjectBId").val(node.id);
			$("#feaProjectBName").val(node.text);
			var opt = {
					silent: true,
					query:{
						'feaProjectB.id':node.id
					}
				};
			$('#feaDesignReportTable').bootstrapTable('refresh',opt);
			//alert("选中了："+node.text);
		}
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">设备选型报价清单列表</h3>
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
			<form:form id="searchForm" modelAttribute="feaDesignReport" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="项目：">项目：</label>
				<sys:gridselect url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${feaDesignReport.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${feaDesignReport.feaProjectB.projectName}"
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
			<%-- <shiro:hasPermission name="fea:quotation:feaDesignReport:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission> --%>
			 <shiro:hasPermission name="fea:quotation:feaDesignReport:edit">
			    <button id="edit" class="btn btn-success" enable onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission> 
			<%-- <shiro:hasPermission name="fea:quotation:feaDesignReport:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission> --%>
			<%-- <shiro:hasPermission name="fea:quotation:feaDesignReport:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/fea/quotation/feaDesignReport/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission> --%>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
				<shiro:hasPermission name="fea:quotation:feaDesignReport:edit">
				    <button id="showImage" class="btn btn-success" disabled onclick="showImage()">
		            	<i class="glyphicon glyphicon-play"></i> 方案简图
		        	</button>
				</shiro:hasPermission>
		    </div>
		
	<!-- 表格 -->
	<table id="feaDesignReportTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="fea:quotation:feaDesignReport:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="fea:quotation:feaDesignReport:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>