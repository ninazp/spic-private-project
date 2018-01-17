<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#fea_design_resultVOTable').bootstrapTable({
		 
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
               url: "${ctx}/fea/result/fea_design_resultVO/data",
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
                        jp.confirm('确认要删除该方案运行费用结果表记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/fea/result/fea_design_resultVO/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#fea_design_resultVOTable').bootstrapTable('refresh');
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
		        field: 'resulttype',
		        title: '结果类别',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'feaProjectB.projectName',
		        title: '项目名称',
		        sortable: true
		       
		    }
			,{
		        field: 'year1',
		        title: '第1年',
		        sortable: true
		       
		    }
			,{
		        field: 'year2',
		        title: '第2年',
		        sortable: true
		       
		    }
			,{
		        field: 'year3',
		        title: '第3年',
		        sortable: true
		       
		    }
			,{
		        field: 'year4',
		        title: '第4年',
		        sortable: true
		       
		    }
			,{
		        field: 'year5',
		        title: '第5年',
		        sortable: true
		       
		    }
			,{
		        field: 'year6',
		        title: '第6年',
		        sortable: true
		       
		    }
			,{
		        field: 'year7',
		        title: '第7年',
		        sortable: true
		       
		    }
			,{
		        field: 'year8',
		        title: '第8年',
		        sortable: true
		       
		    }
			,{
		        field: 'year9',
		        title: '第9年',
		        sortable: true
		       
		    }
			,{
		        field: 'year10',
		        title: '第10年',
		        sortable: true
		       
		    }
			,{
		        field: 'year11',
		        title: '第11年',
		        sortable: true
		       
		    }
			,{
		        field: 'year12',
		        title: '第12年',
		        sortable: true
		       
		    }
			,{
		        field: 'year13',
		        title: '第13年',
		        sortable: true
		       
		    }
			,{
		        field: 'year14',
		        title: '第14年',
		        sortable: true
		       
		    }
			,{
		        field: 'year15',
		        title: '第15年',
		        sortable: true
		       
		    }
			,{
		        field: 'year16',
		        title: '第16年',
		        sortable: true
		       
		    }
			,{
		        field: 'year17',
		        title: '第17年',
		        sortable: true
		       
		    }
			,{
		        field: 'year18',
		        title: '第18年',
		        sortable: true
		       
		    }
			,{
		        field: 'year19',
		        title: '第19年',
		        sortable: true
		       
		    }
			,{
		        field: 'year20',
		        title: '第20年',
		        sortable: true
		       
		    }
			,{
		        field: 'year21',
		        title: '第21年',
		        sortable: true
		       
		    }
			,{
		        field: 'year22',
		        title: '第22年',
		        sortable: true
		       
		    }
			,{
		        field: 'year23',
		        title: '第23年',
		        sortable: true
		       
		    }
			,{
		        field: 'year24',
		        title: '第24年',
		        sortable: true
		       
		    }
			,{
		        field: 'year25',
		        title: '第25年',
		        sortable: true
		       
		    }
			,{
		        field: 'year26',
		        title: '第26年',
		        sortable: true
		       
		    }
			,{
		        field: 'year27',
		        title: '第27年',
		        sortable: true
		       
		    }
			,{
		        field: 'year28',
		        title: '第28年',
		        sortable: true
		       
		    }
			,{
		        field: 'year29',
		        title: '第29年',
		        sortable: true
		       
		    }
			,{
		        field: 'year30',
		        title: '第30年',
		        sortable: true
		       
		    }
			,{
		        field: 'year31',
		        title: '第31年',
		        sortable: true
		       
		    }
			,{
		        field: 'year32',
		        title: '第32年',
		        sortable: true
		       
		    }
			,{
		        field: 'year33',
		        title: '第33年',
		        sortable: true
		       
		    }
			,{
		        field: 'year34',
		        title: '第34年',
		        sortable: true
		       
		    }
			,{
		        field: 'year35',
		        title: '第35年',
		        sortable: true
		       
		    }
			,{
		        field: 'office.name',
		        title: '部门',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#fea_design_resultVOTable').bootstrapTable("toggleView");
		}
	  
	  $('#fea_design_resultVOTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#fea_design_resultVOTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#fea_design_resultVOTable').bootstrapTable('getSelections').length!=1);
            $('#calculation').prop('disabled', $('#fea_design_resultVOTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/fea/result/fea_design_resultVO/import/template';
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
		  $('#fea_design_resultVOTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#fea_design_resultVOTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#fea_design_resultVOTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该方案运行费用结果表记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/fea/result/fea_design_resultVO/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#fea_design_resultVOTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  jp.openDialog('新增方案运行费用结果表', "${ctx}/fea/result/fea_design_resultVO/form",'800px', '500px', $('#fea_design_resultVOTable'));
  }
   
   function calculation(){
	   
	   var url="${ctx}/fea/project/feaProjectB/data";
	   var id="feaProjectB" ;
	   var name="feaProjectB.id" ;
	   var value="${analysisEarnings.feaProjectB.id}" ;
	   var labelName="feaProjectB.projectName" ;
	   var labelValue="${analysisEarnings.feaProjectB.projectName}";
	   var title="请要进行计算的项目" ;
	   var cssClass="form-control required" ;
	   var fieldLabels="项目名称" ;
	   var fieldKeys="projectName" ;
	   var searchLabels="项目名称" ;
       var searchKeys="projectName" ;
	   
	   top.layer.open({
		    type: 2,  
		    area: ['800px', '500px'],
		    title:title,
		    auto:true,
		    name:'friend',
		    content: "${ctx}/tag/gridselect?url="+encodeURIComponent(url)
		    		+"&fieldLabels="+encodeURIComponent(fieldLabels)
		    		+"&fieldKeys="+encodeURIComponent(fieldKeys)
		    		+"&searchLabels="+encodeURIComponent(searchLabels)
		    		+"&searchKeys="+encodeURIComponent(searchKeys)
		    		+"&isMultiSelected="+false,
		    btn: ['确定', '关闭'],
		    yes: function(index, layero){
		    	 var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		    	 var items = iframeWin.getSelections();
		    	 if(items == ""){
			    	jp.warning("必须选择一条数据!");
			    	return;
		    	 }
		    	 if(items.length > 1){
		    		 jp.warning("只能选择一条数据!");
				    	return;
		    	 }
		    	 var ids = [];
		    	 var names = [];
		    	 for(var i=0; i<items.length; i++){
		    		 var item = items[i];
		    		 ids.push(item.id);
		    		 names.push(item${fn:substring(labelName, fns:lastIndexOf(labelName, '.'), fn:length(labelName))})
		    	 }
		    	 
		    	 jp.post("${ctx}/fea/result/fea_design_resultVO/calculation?projectId=" + ids[0],null,function(data){
					if(data.success){
						debugger;
		           	$('#fea_design_resultVOTable').bootstrapTable('refresh');
		           	jp.success(data.msg);
		           	top.layer.close(index);//关闭对话框。
		           }else{
			  			jp.error(data.msg);
		           }
				})
			  },
			  cancel: function(index){ 
		       }
		});
	   
	   
	   
   }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="fea:result:fea_design_resultVO:edit">
	  jp.openDialog('编辑方案运行费用结果表', "${ctx}/fea/result/fea_design_resultVO/form?id=" + id,'800px', '500px', $('#fea_design_resultVOTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="fea:result:fea_design_resultVO:edit">
	  jp.openDialogView('查看方案运行费用结果表', "${ctx}/fea/result/fea_design_resultVO/form?id=" + id,'800px', '500px', $('#fea_design_resultVOTable'));
	  </shiro:lacksPermission>
  }

</script>