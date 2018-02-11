<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#fea_design_setVOTable').bootstrapTable({
		 
		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               //显示刷新按钮
               showRefresh: true,
//               //显示切换手机试图按钮
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
               url: "${ctx}/fea/set/fea_design_setVO/data",
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
                       	jp.get("${ctx}/fea/set/fea_design_setVO/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#fea_design_setVOTable').bootstrapTable('refresh');
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
   		        field: 'feaProjectB.projectName',
   		        title: '项目名称',
   		        sortable: true
   		        ,formatter:function(value, row , index){
    			    if(value == null){
   		            	return "<a href='javascript:edit(\""+row.id+"\")'>-</a>";
   		            }else{
   		                return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
   		            }
   		        }
   		       
   		    }
   			,{
   		        field: 'sumheatefficient',
   		        title: '综合热效率',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'loadrate',
   		        title: '负荷率',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'pumprate',
   		        title: '热泵效率',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'hddept',
   		        title: '潜水泵水下位置（米）',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'hdlose',
   		        title: '潜水泵压力损失（米）',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'rou',
   		        title: '水密度',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'hx2',
   		        title: '循环水泵扬程',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'hb2',
   		        title: '蒸发器侧水泵扬程',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'mpumpcoe',
   		        title: '补水泵流量系数（%）',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'cxxs',
   		        title: '循环水泵单价系数',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'cxcl',
   		        title: '循环水泵单价常量',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'bpxs',
   		        title: '变频控制柜单价系数',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'bpcl',
   		        title: '变频控制柜单价常量',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'bsxs',
   		        title: '补水泵单价系数',
   		        sortable: true
   		       
   		    }
   			,{
   		        field: 'bsxl',
   		        title: '补水泵单价常量',
   		        sortable: true
   		       
   		    }
   		     ]
   		
   		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#fea_design_setVOTable').bootstrapTable("toggleView");
		}
	  
	  $('#fea_design_setVOTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#fea_design_setVOTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#fea_design_setVOTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/fea/set/fea_design_setVO/import/template';
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
		  $('#fea_design_setVOTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#fea_design_setVOTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#fea_design_setVOTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该基本参数记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/fea/set/fea_design_setVO/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#fea_design_setVOTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	   var node = $('#feaProjectjsTree').jstree(true).get_selected(true)[0];
		  if(isNull(node) && isNull(node.id)){
			  jp.openDialog('新增基本参数', "${ctx}/fea/set/fea_design_setVO/form?feaProjectB.id="+node.id +"&feaProjectB.projectName="+node.text,'800px', '500px', $('#fea_design_setVOTable'));
		  }else{
			  jp.warning("请在左侧选择的一个项目");
		  }
  }
  function edit(id){//没有权限时，不显示确定按钮
	  readOnly(false);
	  /*如果id数据不存在，视为新增数据，初始化一些字段，如：项目*/
	  var id = $("#inputForm input[name='id']").val();
	  var node = $('#feaProjectjsTree').jstree(true).get_selected(true)[0];
	  $("#inputForm input[name='feaProjectB.projectName']").val(node.text);
	  $("#inputForm input[name='feaProjectB.id']").val(node.id);
  }
  

</script>