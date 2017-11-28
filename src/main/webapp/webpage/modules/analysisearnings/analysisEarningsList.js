<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#analysisEarningsTable').bootstrapTable({
		 
		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //显示详情按钮
    	       detailView: true,
    	       	//显示详细内容函数
	           detailFormatter: "detailFormatter",
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/analysisearnings/analysisEarnings/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   	edit(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该敏感分析（单因素）记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/analysisearnings/analysisEarnings/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#analysisEarningsTable').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	})
                   	   
                   	});
                      
                   } 
               },
              
               onClickRow: function(row, $el){
               },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'lineOptions',
		        title: '线条选项',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('line_options'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'initialOutlay',
		        title: '初始投资',
		        sortable: true
		       
		    }
			,{
		        field: 'electricityAmount',
		        title: '电费',
		        sortable: true
		       
		    }
			,{
		        field: 'waterAmount',
		        title: '水费',
		        sortable: true
		       
		    }
			,{
		        field: 'laborAmount',
		        title: '人工费',
		        sortable: true
		       
		    }
			,{
		        field: 'warmAmount',
		        title: '取暖费',
		        sortable: true
		       
		    }
			,{
		        field: 'feaProjectB.projectName',
		        title: '项目',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#analysisEarningsTable').bootstrapTable("toggleView");
		}
	  
	  $('#analysisEarningsTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#analysisEarningsTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#analysisEarningsTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/analysisearnings/analysisEarnings/import/template';
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
				        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
				        inputForm.onsubmit = function(){
				        	jp.loading('  正在导入，请稍等...');
				        }
				        inputForm.submit();
					    jp.close(index);
				  },
				 
				  btn3: function(index){ 
					  jp.close(index);
	    	       }
			}); 
		});
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#analysisEarningsTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#analysisEarningsTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#analysisEarningsTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该敏感分析（单因素）记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/analysisearnings/analysisEarnings/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#analysisEarningsTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function add(){
	  jp.openDialog('新增敏感分析（单因素）', "${ctx}/analysisearnings/analysisEarnings/form",'800px', '500px', $('#analysisEarningsTable'));
  }
  
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="analysisearnings:analysisEarnings:edit">
	  jp.openDialog('编辑敏感分析（单因素）', "${ctx}/analysisearnings/analysisEarnings/form?id=" + id,'800px', '500px', $('#analysisEarningsTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="analysisearnings:analysisEarnings:edit">
	  jp.openDialogView('查看敏感分析（单因素）', "${ctx}/analysisearnings/analysisEarnings/form?id=" + id,'800px', '500px', $('#analysisEarningsTable'));
	  </shiro:lacksPermission>
  }
  
  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#analysisEarningsChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/analysisearnings/analysisEarnings/detail?id="+row.id, function(analysisEarnings){
    	var analysisEarningsChild1RowIdx = 0, analysisEarningsChild1Tpl = $("#analysisEarningsChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  analysisEarnings.analysisEarningsBList;
		for (var i=0; i<data1.length; i++){
			addRow('#analysisEarningsChild-'+row.id+'-1-List', analysisEarningsChild1RowIdx, analysisEarningsChild1Tpl, data1[i]);
			analysisEarningsChild1RowIdx = analysisEarningsChild1RowIdx + 1;
		}
				
      	  			
      })
     
        return html;
    }
  
	function addRow(list, idx, tpl, row){
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
	}
			
</script>
<script type="text/template" id="analysisEarningsChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">敏感分析（单因素）子表</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>备注信息</th>
								<th>主表主键</th>
							</tr>
						</thead>
						<tbody id="analysisEarningsChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="analysisEarningsChild1Tpl">//<!--
				<tr>
					<td>
						{{row.remarks}}
					</td>
					<td>
						{{row.pid.id}}
					</td>
				</tr>//-->
	</script>
