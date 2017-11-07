<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#fea_incocalmethodVOTable').bootstrapTable({
		 
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
               url: "${ctx}/fea/calmethod/fea_incocalmethodVO/data",
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
                        jp.confirm('确认要删除该测算方式记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/fea/calmethod/fea_incocalmethodVO/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#fea_incocalmethodVOTable').bootstrapTable('refresh');
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
		        field: 'calmethod',
		        title: '计算方式',
		        sortable: true
		       
		    }
			,{
		        field: 'incometype',
		        title: '目标收益选择',
		        sortable: true
		       
		    }
			,{
		        field: 'targetval',
		        title: '目标值（%）',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#fea_incocalmethodVOTable').bootstrapTable("toggleView");
		}
	  
	  $('#fea_incocalmethodVOTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#fea_incocalmethodVOTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#fea_incocalmethodVOTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/fea/calmethod/fea_incocalmethodVO/import/template';
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
		  $('#fea_incocalmethodVOTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#fea_incocalmethodVOTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#fea_incocalmethodVOTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该测算方式记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/fea/calmethod/fea_incocalmethodVO/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#fea_incocalmethodVOTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function add(){
	  jp.openDialog('新增测算方式', "${ctx}/fea/calmethod/fea_incocalmethodVO/form",'800px', '500px', $('#fea_incocalmethodVOTable'));
  }
  
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="fea:calmethod:fea_incocalmethodVO:edit">
	  jp.openDialog('编辑测算方式', "${ctx}/fea/calmethod/fea_incocalmethodVO/form?id=" + id,'800px', '500px', $('#fea_incocalmethodVOTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="fea:calmethod:fea_incocalmethodVO:edit">
	  jp.openDialogView('查看测算方式', "${ctx}/fea/calmethod/fea_incocalmethodVO/form?id=" + id,'800px', '500px', $('#fea_incocalmethodVOTable'));
	  </shiro:lacksPermission>
  }
  
  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#fea_incocalmethodVOChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/fea/calmethod/fea_incocalmethodVO/detail?id="+row.id, function(fea_incocalmethodVO){
    	var fea_incocalmethodVOChild1RowIdx = 0, fea_incocalmethodVOChild1Tpl = $("#fea_incocalmethodVOChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  fea_incocalmethodVO.fea_incocalmethodBVOList;
		for (var i=0; i<data1.length; i++){
			addRow('#fea_incocalmethodVOChild-'+row.id+'-1-List', fea_incocalmethodVOChild1RowIdx, fea_incocalmethodVOChild1Tpl, data1[i]);
			fea_incocalmethodVOChild1RowIdx = fea_incocalmethodVOChild1RowIdx + 1;
		}
				
    	var fea_incocalmethodVOChild2RowIdx = 0, fea_incocalmethodVOChild2Tpl = $("#fea_incocalmethodVOChild2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data2 =  fea_incocalmethodVO.fea_incocalmethodTVOList;
		for (var i=0; i<data2.length; i++){
			addRow('#fea_incocalmethodVOChild-'+row.id+'-2-List', fea_incocalmethodVOChild2RowIdx, fea_incocalmethodVOChild2Tpl, data2[i]);
			fea_incocalmethodVOChild2RowIdx = fea_incocalmethodVOChild2RowIdx + 1;
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
<script type="text/template" id="fea_incocalmethodVOChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">测算方式价格子表</a></li>
				<li><a data-toggle="tab" href="#tab-{{idx}}-2" aria-expanded="true">测算方式分利比</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>项目类别</th>
								<th>单位</th>
								<th>2017</th>
								<th>2018</th>
								<th>2019</th>
								<th>2020</th>
								<th>2021</th>
								<th>2022</th>
								<th>2023</th>
								<th>2024</th>
								<th>2025</th>
								<th>2026</th>
							</tr>
						</thead>
						<tbody id="fea_incocalmethodVOChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-2" class="tab-pane fade">
					<table class="ani table">
						<thead>
							<tr>
								<th>项目类别</th>
								<th>单位</th>
								<th>2017</th>
								<th>2018</th>
								<th>2019</th>
								<th>2020</th>
								<th>2021</th>
								<th>2022</th>
								<th>2023</th>
								<th>2024</th>
								<th>2025</th>
								<th>2026</th>
							</tr>
						</thead>
						<tbody id="fea_incocalmethodVOChild-{{idx}}-2-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="fea_incocalmethodVOChild1Tpl">//<!--
				<tr>
					<td>
						{{row.itemtype}}
					</td>
					<td>
						{{row.unitname}}
					</td>
					<td>
						{{row.year17}}
					</td>
					<td>
						{{row.year18}}
					</td>
					<td>
						{{row.year19}}
					</td>
					<td>
						{{row.year20}}
					</td>
					<td>
						{{row.year21}}
					</td>
					<td>
						{{row.year22}}
					</td>
					<td>
						{{row.year23}}
					</td>
					<td>
						{{row.year24}}
					</td>
					<td>
						{{row.year25}}
					</td>
					<td>
						{{row.year26}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="fea_incocalmethodVOChild2Tpl">//<!--
				<tr>
					<td>
						{{row.itemtype}}
					</td>
					<td>
						{{row.unitname}}
					</td>
					<td>
						{{row.year17}}
					</td>
					<td>
						{{row.year18}}
					</td>
					<td>
						{{row.year19}}
					</td>
					<td>
						{{row.year20}}
					</td>
					<td>
						{{row.year21}}
					</td>
					<td>
						{{row.year22}}
					</td>
					<td>
						{{row.year23}}
					</td>
					<td>
						{{row.year24}}
					</td>
					<td>
						{{row.year25}}
					</td>
					<td>
						{{row.year26}}
					</td>
				</tr>//-->
	</script>
