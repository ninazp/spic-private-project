<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#fea_productcostVOTable').bootstrapTable({
		 
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
               url: "${ctx}/fea/procost/fea_productcostVO/data",
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
                        jp.confirm('确认要删除该生产成本记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/fea/procost/fea_productcostVO/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#fea_productcostVOTable').bootstrapTable('refresh');
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
		        title: '项目',
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
		        field: 'perwage',
		        title: '年人均工资（万元）',
		        sortable: true
		       
		    }
			,{
		        field: 'welfare',
		        title: '福利费系数（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'insurance',
		        title: '保险费率（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'repairrate',
		        title: '维修费率（%）',
		        sortable: true
		       
		    }
			,{
		        field: 'intangibletx',
		        title: '无形资产摊销（年）',
		        sortable: true
		       
		    }
			,{
		        field: 'otherassettx',
		        title: '其他资产摊销（年）',
		        sortable: true
		       
		    }
			,{
		        field: 'othercost',
		        title: '其他费用',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#fea_productcostVOTable').bootstrapTable("toggleView");
		}
	  
	  $('#fea_productcostVOTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#fea_productcostVOTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#fea_productcostVOTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/fea/procost/fea_productcostVO/import/template';
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
		  $('#fea_productcostVOTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#fea_productcostVOTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#fea_productcostVOTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该生产成本记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/fea/procost/fea_productcostVO/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#fea_productcostVOTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function add(){
	  jp.openDialog('新增生产成本', "${ctx}/fea/procost/fea_productcostVO/form",'1500px', '750px', $('#fea_productcostVOTable'));
  }
  
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="fea:procost:fea_productcostVO:edit">
	  jp.openDialog('编辑生产成本', "${ctx}/fea/procost/fea_productcostVO/form?id=" + id,'1500px', '750px', $('#fea_productcostVOTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="fea:procost:fea_productcostVO:edit">
	  jp.openDialogView('查看生产成本', "${ctx}/fea/procost/fea_productcostVO/form?id=" + id,'1500px', '750px', $('#fea_productcostVOTable'));
	  </shiro:lacksPermission>
  }
  
  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#fea_productcostVOChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/fea/procost/fea_productcostVO/detail?id="+row.id, function(fea_productcostVO){
    	var fea_productcostVOChild1RowIdx = 0, fea_productcostVOChild1Tpl = $("#fea_productcostVOChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  fea_productcostVO.fea_productcostBVOList;
		for (var i=0; i<data1.length; i++){
			addRow('#fea_productcostVOChild-'+row.id+'-1-List', fea_productcostVOChild1RowIdx, fea_productcostVOChild1Tpl, data1[i],fea_productcostVO.feaProjectB.id,row.id);
			fea_productcostVOChild1RowIdx = fea_productcostVOChild1RowIdx + 1;
		}
				
      	  			
      })
     
        return html;
    }
  
	function addRow(list, idx, tpl, row, projectId, rowId){
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
		
		hideCol(projectId,rowId);
		
	}
	
	function hideCol(projectId, rowId){
		//jp.loading();
		jp.get("${ctx}/fea/project/feaProjectB/getProjectById?id=" + projectId, function (data) {
			if(data.success){
			    var countyears = data.body.feaProjectB.countyears;//建设期
			    var len = $('thead  tr th').length;
			    if(isNull(countyears) && isNull(len) && len > countyears + 2){
			    	var startHidecol = countyears + 2;  //2：多余列2个
			    	
			    	for(var i = startHidecol;i<len;i++){
		    			$("#tab-"+rowId+"-1").find('th:eq('+i+')').addClass("hide");
							//$("#fea_productcostVOChild-"+rowId+"-1-List").find('td:eq('+i+')').addClass("hide");
			    	}
			    	var trLength = $("#fea_productcostVOChild-"+rowId+"-1-List tr").length;
	    			for(var j=0;j<trLength;j++){
	    				var tr = $("#fea_productcostVOChild-"+rowId+"-1-List tr").eq(j);
    					var tdLength = tr.find("td").length;
    					for(var f=startHidecol;f<tdLength;f++){
							tr.find('td:eq('+f+')').addClass("hide");
	    				}
	    			}
			    }
  	  		}else{
  	  			jp.error(data.msg);
  	  		}
        })
		//jp.close();
		
	//var len = $('thead  tr th').length;
	/* $("#listTheadtr").find('th:eq(7)').addClass("hide");
	$("#fea_productcostBVOList tr").find('td:eq(6)').addClass("hide"); */
	}
	
	function isNull(data){ 
		return (data == "" || data == undefined || data == null) ? false : true; 
	}
	
	function obj2string(o){ 
		 var r=[]; 
		 if(typeof o=="string"){ 
		 return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\""; 
		 } 
		 if(typeof o=="object"){ 
		 if(!o.sort){ 
		  for(var i in o){ 
		  r.push(i+":"+obj2string(o[i])); 
		  } 
		  if(!!document.all&&!/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)){ 
		  r.push("toString:"+o.toString.toString()); 
		  } 
		  r="{"+r.join()+"}"; 
		 }else{ 
		  for(var i=0;i<o.length;i++){ 
		  r.push(obj2string(o[i])) 
		  } 
		  r="["+r.join()+"]"; 
		 } 
		 return r; 
		 } 
		 return o.toString(); 
		}	
			
</script>
<script type="text/template" id="fea_productcostVOChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">成本种类</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>成本种类</th>
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
							</tr>
						</thead>
						<tbody id="fea_productcostVOChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="fea_productcostVOChild1Tpl">//<!--
				<tr>
					<td>
						{{row.costtype}}
					</td>
					<td>
						{{row.year1}}
					</td>
					<td>
						{{row.year2}}
					</td>
					<td>
						{{row.year3}}
					</td>
					<td>
						{{row.year4}}
					</td>
					<td>
						{{row.year5}}
					</td>
					<td>
						{{row.year6}}
					</td>
					<td>
						{{row.year7}}
					</td>
					<td>
						{{row.year8}}
					</td>
					<td>
						{{row.year9}}
					</td>
					<td>
						{{row.year10}}
					</td>
					<td>
						{{row.year11}}
					</td>
					<td>
						{{row.year12}}
					</td>
					<td>
						{{row.year13}}
					</td>
					<td>
						{{row.year14}}
					</td>
					<td>
						{{row.year15}}
					</td>
					<td>
						{{row.year16}}
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
					<td>
						{{row.year27}}
					</td>
					<td>
						{{row.year28}}
					</td>
					<td>
						{{row.year29}}
					</td>
					<td>
						{{row.year30}}
					</td>
					<td>
						{{row.year31}}
					</td>
					<td>
						{{row.year32}}
					</td>
				</tr>//-->
	</script>
