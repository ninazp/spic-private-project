<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#fea_fundssrcVOTable').bootstrapTable({
		 
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
               url: "${ctx}/fea/funds/fea_fundssrcVO/data",
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
                        jp.confirm('确认要删除该资金来源记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/fea/funds/fea_fundssrcVO/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#fea_fundssrcVOTable').bootstrapTable('refresh');
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
			/*,{
		        field: 'projectCode',
		        title: '项目编码',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
		         }
		       
		    }*/
			,{
		        field: 'feaProjectB.projectName',
		        title: '项目',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
		         }
		    }
			/*,{
		        field: 'projectName',
		        title: '项目名称',
		        sortable: true
		       
		    }*/
			,{
		        field: 'investtotal',
		        title: '投资总额',
		        sortable: true
		       
		    }
			/*,{
		        field: 'isdeductvtax',
		        title: '增值税抵扣',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'deductvtax',
		        title: '可抵扣税金',
		        sortable: true
		       
		    }*/
			,{
		        field: 'capitalprop',
		        title: '资本金比例(%)',
		        sortable: true
		       
		    }
			,{
		        field: 'capitalamt',
		        title: '资本金额度',
		        sortable: true
		       
		    }
			,{
		        field: 'loanprop',
		        title: '借款比例(%)',
		        sortable: true
		       
		    }
			,{
		        field: 'loanamt',
		        title: '借款金额',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#fea_fundssrcVOTable').bootstrapTable("toggleView");
		}
	  
	  $('#fea_fundssrcVOTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#fea_fundssrcVOTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#fea_fundssrcVOTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/fea/funds/fea_fundssrcVO/import/template';
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
		  $('#fea_fundssrcVOTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#fea_fundssrcVOTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#fea_fundssrcVOTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该资金来源记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/fea/funds/fea_fundssrcVO/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#fea_fundssrcVOTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function add(){
	  jp.openDialog('新增资金来源', "${ctx}/fea/funds/fea_fundssrcVO/form",'1500px', '800px', $('#fea_fundssrcVOTable'));
  }
  
  function edit(id){//没有权限时，不显示确定按钮
//	  alert('浏览器当前窗口可视区域高度:'+$(window).height()); //浏览器当前窗口可视区域高度 
//	  alert('浏览器当前窗口文档的高度 :'+$(document).height()); //浏览器当前窗口文档的高度 
//	  alert('浏览器当前窗口文档body的高度 :'+$(document.body).height());//浏览器当前窗口文档body的高度 
//	  alert($(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin 
//	  alert($(window).width()); //浏览器当前窗口可视区域宽度 
//	  alert($(document).width());//浏览器当前窗口文档对象宽度 
//	  alert($(document.body).width());//浏览器当前窗口文档body的高度 
//	  alert($(document.body).outerWidth(true));//浏览器当前窗口文档body的总宽度 包括border padding margin 
	  var width = $(document).width();
	  var height = $(document).height();
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="fea:funds:fea_fundssrcVO:edit">
	  jp.openDialog('编辑资金来源', "${ctx}/fea/funds/fea_fundssrcVO/form?id=" + id, '1500px' , '800px', $('#fea_fundssrcVOTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="fea:funds:fea_fundssrcVO:edit">
	  jp.openDialogView('查看资金来源', "${ctx}/fea/funds/fea_fundssrcVO/form?id=" + id,'1500px', '800px', $('#fea_fundssrcVOTable'));
	  </shiro:lacksPermission>
  }
  
  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#fea_fundssrcVOChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/fea/funds/fea_fundssrcVO/detail?id="+row.id, function(fea_fundssrcVO){
    	var fea_fundssrcVOChild1RowIdx = 0, fea_fundssrcVOChild1Tpl = $("#fea_fundssrcVOChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  fea_fundssrcVO.fea_fundssrcBVOList;
		for (var i=0; i<data1.length; i++){
			addRow('#fea_fundssrcVOChild-'+row.id+'-1-List', fea_fundssrcVOChild1RowIdx, fea_fundssrcVOChild1Tpl, data1[i]);
			fea_fundssrcVOChild1RowIdx = fea_fundssrcVOChild1RowIdx + 1;
		}
				
    	var fea_fundssrcVOChild2RowIdx = 0, fea_fundssrcVOChild2Tpl = $("#fea_fundssrcVOChild2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data2 =  fea_fundssrcVO.fea_fundssrcTVOList;
		for (var i=0; i<data2.length; i++){
			addRow('#fea_fundssrcVOChild-'+row.id+'-2-List', fea_fundssrcVOChild2RowIdx, fea_fundssrcVOChild2Tpl, data2[i]);
			fea_fundssrcVOChild2RowIdx = fea_fundssrcVOChild2RowIdx + 1;
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
<script type="text/template" id="fea_fundssrcVOChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">投资来源</a></li>
				<li><a data-toggle="tab" href="#tab-{{idx}}-2" aria-expanded="true">融资来源</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>注资方名称</th>
								<th>币种</th>
								<th>汇率</th>
								<th>比例</th>
								<th>注资金额</th>
							</tr>
						</thead>
						<tbody id="fea_fundssrcVOChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-2" class="tab-pane fade">
					<table class="ani table">
						<thead>
							<tr>
								<th>借款方</th>
								<th>币种</th>
								<th>汇率</th>
								<th>比例</th>
								<th>借款金额</th>
								<th>计息次数（年）</th>
								<th>本金利率（%）</th>
								<th>利息利率（%）</th>
								<th>还款方式</th>
								<th>还款期</th>
								<th>承诺费率（%）</th>
								<th>宽限期</th>
							</tr>
						</thead>
						<tbody id="fea_fundssrcVOChild-{{idx}}-2-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="fea_fundssrcVOChild1Tpl">//<!--
				<tr>
					<td>
						{{row.capitaltype}}
					</td>
					<td>
						{{row.currency}}
					</td>
					<td>
						{{row.exchangerate}}
					</td>
					<td>
						{{row.capprop}}
					</td>
					<td>
						{{row.capamt}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="fea_fundssrcVOChild2Tpl">//<!--
				<tr>
					<td>
						{{row.loantyp}}
					</td>
					<td>
						{{row.currency}}
					</td>
					<td>
						{{row.exchangerate}}
					</td>
					<td>
						{{row.loanprop}}
					</td>
					<td>
						{{row.loanamt}}
					</td>
					<td>
						{{row.interestcount}}
					</td>
					<td>
						{{row.principalrate}}
					</td>
					<td>
						{{row.langrate}}
					</td>
					<td>
						{{dict.repaytype}}
					</td>
					<td>
						{{row.repayperiod}}
					</td>
					<td>
						{{row.commitrate}}
					</td>
					<td>
						{{row.graceperiod}}
					</td>
				</tr>//-->
	</script>
