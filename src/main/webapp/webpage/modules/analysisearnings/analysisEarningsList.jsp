<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>敏感分析（单因素）管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="analysisEarningsList.js" %>
	
	<script src="${ctxStatic}/common/js/echarts.js"></script>
	
	<style>
	 	.width-15{
			width:5%;
		}
		.self-label {
		    text-align:center;
		    border: 0px;
		}
	</style>

	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var myChart;
		var option;
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}
		
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/analysisearnings/analysisEarnings/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

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
			
			//图标选择（单选按钮选中）
			$('.lineOptions').on('ifChecked', function(event){
		        lineOptionsChangeChart(event);
		        
		    });
			//radiosChecked();
			initChart();
		});
		
		function radiosChecked(){
			var radiosChecked = $("input[name='lineOptions']:checked").val();
			//$(".inputForm input:first").attr("checked",true);
			
			//$("input[name='lineOptions']").prop("checked",'2');
			//$("input[@name='lineOptions'][@value='2']").prop("checked",true);
			$("input[name='lineOptions']").each(function(){
				if($(this).val() == '1'){
					//$(this).prop("checked", true);
					//$(this).attr("checked",'1');
					alert($(this).val());
				}
			})
		}
		
		function lineOptionsChangeChart(event){
			var radioValue =  parseInt($(event.target).val());
			switch(radioValue)
			{
			case 1:
			  	showLine(null);
			  	break;
			case 2:
			  	showLine("初始投资");
			  	break;
			case 3:
			  	showLine("电费");
			  	break;
			case 4:
			  	showLine("水费");
			  	break;
			case 5:
			  	showLine("人工费");
			  	break;
			 case 6:
			  	showLine("取暖费");
			  	break;
			default:
			  	break;
			}
		}
		
		function showLine(isShowDataArr){
			var datasArr = option.legend.data;
			var selected = {}; 
			for(var i=0; i<datasArr.length; i++){
				if(null == isShowDataArr){
					selected[datasArr[i]] = true;
				}else if(datasArr[i] == isShowDataArr){
					selected[datasArr[i]] = true;
				}else{
					selected[datasArr[i]] = false;
				}
			}
			option.legend.selected = selected;
            myChart.setOption(option);
		}

		function initChart(){
		  	// 基于准备好的dom，初始化echarts实例
			myChart = echarts.init(document.getElementById('main'));
		
			option = {
				title : {
					text : '敏感性分析'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : ['初始投资', '电费', '水费', '人工费', '取暖费' ]
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				toolbox : {
					feature : {
						saveAsImage : {}
					}
				},
				xAxis : {
					type : 'category',
					boundaryGap : false,
					data : [ '-15%', '-10%', '-5%', '0', '5%', '10%', '15%' ]
				},
				
				yAxis : {
					name:'敏感性系数',
					type : 'value'
				},
				series : [
					{
						name : '初始投资',
						type : 'line',
						stack : '总量',
						data : [ '', 0.4, -1.3, 0, 0.9, -0.8,  '']
					},
					{
						name : '电费',
						type : 'line',
						stack : '总量',
						data : [ '', -1.2, -1.0,0, -1.1, 1.1, '' ]
					},
					{
						name : '水费',
						type : 'line',
						stack : '总量',
						data : [ '', 1.4, 1.5, 0, 0.9, 0.8, '' ]
					},
					{
						name : '人工费',
						type : 'line',
						stack : '总量',
						data : [ '', 0.3, 0.4, 0, -0.6, -0.7, '']
					},
					{
						name : '取暖费',
						type : 'line',
						stack : '总量',
						data : [ '', -0.7, 0.6, 0, -0.4, 0.3, '' ]
					}
				]
			};
			
			//myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
			
			// 使用刚指定的配置项和数据显示图表。
			myChart.setOption(option);
		}
		
		/*计算*/
		function calculation(){
		
			var names=[];    //类别数组（实际用来盛放X轴坐标值）
         	var nums=[];    //销量数组（实际用来盛放Y坐标值）
         	/*
			jp.post("${ctx}/analysisearnings/analysisEarnings/zhaopeng",{"ZHAOPENG":"TEAT"},function(data){
				alert(data);
				if(data.success){
                   	$table.bootstrapTable('refresh');
                   	jp.success(data.msg);
                   	jp.close($topIndex);//关闭dialog
                   }else{
          	  			jp.error(data.msg);
                   }
			})
			*/
			jp.post("${ctx}/analysisearnings/analysisEarnings/calculation?json=11111", $('#inputForm').serialize(), function(data){
       	  		if(data.success){
       	  			$('#courseTable').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
		}
		
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">敏感性分析（单因素）</h3>
			</div>
			<div class="panel-body">
			
				<div style="width:100%;height:100%;margin:0 auto;">
					
					<div id="main" style="width:40%;height:400px;float:left;"></div>
					<div id="selfform" style="width:55%;float:right;">
						<form:form id="inputForm" modelAttribute="analysisEarnings" action="${ctx}/analysisearnings/analysisEarnings/zhaopeng" method="post" class="form-horizontal">
							<form:hidden path="id"/>
							<sys:message content="${message}"/>
							
							<table class="table table-no-bordered" style="width:450px">
								<tr>
									<td style="width:7%"><label class="self-label"></label></td>
									<td style="width:10%"><label class="self-label">初始值(%)</label></td>
									<td style="width:10%"><label class="self-label">步长</label></td>
									<td style="width:10%"><label class="self-label"">终值(%)</label></td>
								</tr>
								<tr>
									<td style="width:6%"">
										<label class="checkbox-inline">
                                        <input type="checkbox" value="option1" id="inlineCheckbox1">初始投资：</label>
									</td>
									<!-- <label type="checkbox" class="pull-right">初始投资：</label> -->
									<td style="width:10%">
										<form:input path="initialOutlay" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="initialOutlay" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="initialOutlay" htmlEscape="false"    class="form-control "/>
									</td>
								</tr>
								<tr>
									<td style="width:6%">
										<label class="checkbox-inline">
                                        <input type="checkbox" value="option2" id="inlineCheckbox1">电费：</label>
									</td>
									<td style="width:10%">
										<form:input path="electricityAmount" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="electricityAmount" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="electricityAmount" htmlEscape="false"    class="form-control "/>
									</td>
								</tr>
								<tr>
									<td style="width:6%">
										<label class="checkbox-inline">
                                        <input type="checkbox" value="option3" id="inlineCheckbox1">水费：</label>
									</td>
									<td style="width:10%">
										<form:input path="waterAmount" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="waterAmount" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="waterAmount" htmlEscape="false"    class="form-control "/>
									</td>
								</tr>
								<tr>
									<td style="width:6%">
										<label class="checkbox-inline">
                                        <input type="checkbox" value="option4" id="inlineCheckbox1">人工费：</label>
									</td>
									<td style="width:10%">
										<form:input path="laborAmount" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="laborAmount" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="laborAmount" htmlEscape="false"    class="form-control "/>
									</td>
								</tr>
								<tr>
									<td style="width:6%">
										<label class="checkbox-inline">
                                        <input type="checkbox" value="option5" id="inlineCheckbox1">取暖费：</label>
									</td>
									<td style="width:10%">
										<form:input path="warmAmount" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="warmAmount" htmlEscape="false"    class="form-control "/>
									</td>
									<td style="width:10%">
										<form:input path="warmAmount" htmlEscape="false"    class="form-control "/>
									</td>
						  		</tr>
									
							</table>
							
							<table class="table table-no-bordered" style="width:450px">
							   <tbody>
						   			<fieldset>
									<legend>图形选项</legend>
										<tr>
											<td style="width:70px" colspan="6">
												<form:radiobuttons path="lineOptions" items="${fns:getDictList('line_options')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks lineOptions"/>
											</td>
										</tr>
									</fieldset>
							 	</tbody>
							</table>
							
						</form:form>
						<!-- 工具栏 -->
						<div id="toolbar">
								<shiro:hasPermission name="fea:project:feaProjectB:add">
									<a id="add" class="btn btn-primary" onclick="calculation()"><i class="glyphicon glyphicon-edit"></i>  计算</a>
								</shiro:hasPermission>
						</div>
					</div>
					<div style="clear:both;">
				</div>
				</div>
				</body>
			</div>
		</div>
	
	</div>
	
	
</body>
</html>