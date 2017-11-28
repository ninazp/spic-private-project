<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#fea_investdisVOTable').bootstrapTable({
		 
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
               url: "${ctx}/fea/funds/fea_investdisVO/data",
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
                        jp.confirm('确认要删除该投资分配记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/fea/funds/fea_investdisVO/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#fea_investdisVOTable').bootstrapTable('refresh');
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
		        field: 'projectcode',
		        title: '项目编码',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'projectname',
		        title: '项目名称',
		        sortable: true
		       
		    }
			,{
		        field: 'year',
		        title: '年度',
		        sortable: true
		       
		    }
			,{
		        field: 'investprop',
		        title: '投资比例',
		        sortable: true
		       
		    }
			,{
		        field: 'investamt',
		        title: '投资额度',
		        sortable: true
		       
		    }
			,{
		        field: 'deductvtax',
		        title: '可抵扣税金',
		        sortable: true
		       
		    }
			,{
		        field: 'cappropsum',
		        title: '注资方合计',
		        sortable: true
		       
		    }
			,{
		        field: 'loanpropsum',
		        title: '融资合计',
		        sortable: true
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#fea_investdisVOTable').bootstrapTable("toggleView");
		}
	  
	  $('#fea_investdisVOTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#fea_investdisVOTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#fea_investdisVOTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/fea/funds/fea_investdisVO/import/template';
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
		  $('#fea_investdisVOTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#fea_investdisVOTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#fea_investdisVOTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该投资分配记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/fea/funds/fea_investdisVO/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#fea_investdisVOTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function add(){
	  jp.openDialog('新增投资分配', "${ctx}/fea/funds/fea_investdisVO/form",'800px', '500px', $('#fea_investdisVOTable'));
  }
  
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="fea:funds:fea_investdisVO:edit">
	  jp.openDialog('编辑投资分配', "${ctx}/fea/funds/fea_investdisVO/form?id=" + id,'800px', '500px', $('#fea_investdisVOTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="fea:funds:fea_investdisVO:edit">
	  jp.openDialogView('查看投资分配', "${ctx}/fea/funds/fea_investdisVO/form?id=" + id,'800px', '500px', $('#fea_investdisVOTable'));
	  </shiro:lacksPermission>
  }
  
  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#fea_investdisVOChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/fea/funds/fea_investdisVO/detail?id="+row.id, function(fea_investdisVO){
    	var fea_investdisVOChild1RowIdx = 0, fea_investdisVOChild1Tpl = $("#fea_investdisVOChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  fea_investdisVO.fea_investdisBVOList;
		for (var i=0; i<data1.length; i++){
			addRow('#fea_investdisVOChild-'+row.id+'-1-List', fea_investdisVOChild1RowIdx, fea_investdisVOChild1Tpl, data1[i]);
			fea_investdisVOChild1RowIdx = fea_investdisVOChild1RowIdx + 1;
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
<script type="text/template" id="fea_investdisVOChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">分配组成</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>资金方名称</th>
								<th>资金方类别</th>
								<th>当期比例（%）</th>
								<th>资金金额</th>
								<th>用于建设金额</th>
								<th>用于流动资金金额</th>
							</tr>
						</thead>
						<tbody id="fea_investdisVOChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="fea_investdisVOChild1Tpl">//<!--
				<tr>
					<td>
						{{row.zjname}}
					</td>
					<td>
						{{dict.investtype}}
					</td>
					<td>
						{{row.investprop}}
					</td>
					<td>
						{{row.investamt}}
					</td>
					<td>
						{{row.jsamt}}
					</td>
					<td>
						{{row.ldamt}}
					</td>
				</tr>//-->
	</script>
