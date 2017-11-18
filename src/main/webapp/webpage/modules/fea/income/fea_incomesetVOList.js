<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#fea_incomesetVOTable').bootstrapTable({
		 
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
               url: "${ctx}/fea/income/fea_incomesetVO/data",
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
                        jp.confirm('确认要删除该基本参数记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/fea/income/fea_incomesetVO/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#fea_incomesetVOTable').bootstrapTable('refresh');
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
		        field: 'vtaxrate',
		        title: '综合增值税税率（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'incomerate',
		        title: '所得税税率（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'umctax',
		        title: '城市维护建设税率（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'surtax',
		        title: '教育费附加费率（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'legalaccfund',
		        title: '法定盈余公积金比例（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'accfund',
		        title: '任意盈余公积金比例（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'isaccfundwsd',
		        title: '公积金提取不超过资本金的50%',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'yflrprop',
		        title: '应付利润比例（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'isvtaxjzjt',
		        title: '增值税即征即退50%',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'issdssjsm',
		        title: '所得税三免三减半',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'capinvesttype',
		        title: '资本金投入方式',
		        sortable: true
		       
		    }
			,{
		        field: 'capinvestprop',
		        title: '资本金比例（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'capinvestrate',
		        title: '资本金基准收益率（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'industrysqrate',
		        title: '行业基准收益率（所得税前）（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'industryshrate',
		        title: '行业基准收益率（所得税后）（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'capcostrate',
		        title: '资本成本率（%）',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#fea_incomesetVOTable').bootstrapTable("toggleView");
		}
	  
	  $('#fea_incomesetVOTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#fea_incomesetVOTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#fea_incomesetVOTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/fea/income/fea_incomesetVO/import/template';
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
		  $('#fea_incomesetVOTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#fea_incomesetVOTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#fea_incomesetVOTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该基本参数记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/fea/income/fea_incomesetVO/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#fea_incomesetVOTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  jp.openDialog('新增基本参数', "${ctx}/fea/income/fea_incomesetVO/form",'800px', '500px', $('#fea_incomesetVOTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="fea:income:fea_incomesetVO:edit">
	  jp.openDialog('编辑基本参数', "${ctx}/fea/income/fea_incomesetVO/form?id=" + id,'800px', '500px', $('#fea_incomesetVOTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="fea:income:fea_incomesetVO:edit">
	  jp.openDialogView('查看基本参数', "${ctx}/fea/income/fea_incomesetVO/form?id=" + id,'800px', '500px', $('#fea_incomesetVOTable'));
	  </shiro:lacksPermission>
  }

</script>