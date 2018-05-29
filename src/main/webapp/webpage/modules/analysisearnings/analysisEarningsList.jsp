<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>敏感分析（单因素）管理</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta name="decorator" content="ani" />
<%@ include file="/webpage/include/bootstraptable.jsp"%>
<%@include file="/webpage/include/treeview.jsp"%>
<%@include file="analysisEarningsList.js"%>

<script src="${ctxStatic}/common/js/echarts.js"></script>

<style>
.width-15 {
	width: 5%;
}

.self-label {
	text-align: center;
	border: 0px;
}
</style>

<meta name="decorator" content="ani" />
<script type="text/javascript">
	var myChart;
	var option;
	var validateForm;
	var $table; // 父页面table表格id
	var $topIndex;//弹出窗口的 index
	function doSubmit(table, index) {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
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
			submitHandler : function(form) {
			jp.post("${ctx}/analysisearnings/analysisEarnings/save",$('#inputForm').serialize(),function(data) {
				if (data.success) {
						$table.bootstrapTable('refresh');
						jp.success(data.msg);
						jp.close($topIndex);//关闭dialog

				} else {
					jp.error(data.msg);
				}
			})},
				errorContainer : "#messageBox",
				errorPlacement : function(error,
				element) {
			$("#messageBox").text(
					"输入有误，请先更正。");
			if (element.is(":checkbox")
					|| element.is(":radio")
					|| element
							.parent()
							.is(
									".input-append")) {
				error.appendTo(element
						.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});

		//图标选择（单选按钮选中）
		$('.lineOptions').on('ifChecked', function(event) {
			lineOptionsChangeChart(event);

		});
		//radiosChecked();
		initChart();

		$('#feaProjectB').bind('keypress', function(event) {
			if (event.keyCode == "13") {
				search();
				return false;
			}
		});
	});

	function radiosChecked() {
		var radiosChecked = $("input[name='lineOptions']:checked").val();
		//$(".inputForm input:first").attr("checked",true);

		//$("input[name='lineOptions']").prop("checked",'2');
		//$("input[@name='lineOptions'][@value='2']").prop("checked",true);
		$("input[name='lineOptions']").each(function() {
			if ($(this).val() == '1') {

			}
		})
	}

	function lineOptionsChangeChart(event) {
		var radioValue = parseInt($(event.target).val());
		switch (radioValue) {
		case 1:
			showLine(null);
			break;
		case 2:
			showLine("初始投资");
			break;
		case 3:
			showLine("电费");
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

	function showLine(isShowDataArr) {
		var datasArr = option.legend.data;
		var selected = {};
		for (var i = 0; i < datasArr.length; i++) {
			if (null == isShowDataArr) {
				selected[datasArr[i]] = true;
			} else if (datasArr[i] == isShowDataArr) {
				selected[datasArr[i]] = true;
			} else {
				selected[datasArr[i]] = false;
			}
		}
		option.legend.selected = selected;
		myChart.setOption(option);
	}
	
	function initChart() {
		// 基于准备好的dom，初始化echarts实例
		myChart = echarts.init(document.getElementById('main'));
		var timestamp = (new Date()).valueOf();
		jp.get("${ctx}/analysisearnings/analysisEarnings/getProjectDatas?ids=&timestamp="+timestamp, function(data) {
			if (data.success) {
				
				var array = eval(data.msg);
				
				$("#feaProjectBId").val(data.projectId);
			    $("#feaProjectBName").val(data.projectName);
			    
			    $('#stepone').val("-15");
				$('#steptwo').val("5");
				$('#stepthree').val("15");
			    
				option = {
						title : {
							text : ''
						},
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : [ '初始投资', '电费', '人工费', '取暖费' ]
						},
						grid : {
							left : '3%',
							right : '4%',
							bottom : '6%',
							containLabel : true
						},
						toolbox : {
							feature : {
								saveAsImage : {}
							}
						},
						xAxis : {
							type : 'category',
							name : "百分比",
							boundaryGap : false,
							data : [ '-15%', '-10%', '-5%', '0', '5%', '10%', '15%' ]
						},

						yAxis : {
							name : '敏感性系数',
							type : 'value'
						},
						series : [
								{
									name : '初始投资',
									type : 'line',
									stack : '总量1',
									data : [ array[0][0], array[0][1],array[0][2], array[0][3],array[0][4], array[0][5],array[0][6]]
								}, {
									name : '电费',
									type : 'line',
									stack : '总量2',
									data : [ array[1][0],  array[1][1], array[1][2],  array[1][3], array[1][4], array[1][5],array[1][6] ]
								}, {
									name : '人工费',
									type : 'line',
									stack : '总量3',
									data : [array[2][0], array[2][1], array[2][2], array[2][3],array[2][4],array[2][5],array[2][6] ]
								}, {
									name : '取暖费',
									type : 'line',
									stack : '总量4',
									data : [array[3][0], array[3][1], array[3][2], array[3][3],array[3][4],array[3][5],array[3][6] ]
								} ]
					};
				
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);

			} else {
				jp.error(data.msg);
			}
		})
	}
	

	/*计算*/
	function calculation() {
		var timestamp = (new Date()).valueOf();
		var projectids = $('#feaProjectBId').val();
		
		var stepone = $('#stepone').val();
		var steptwo = $('#steptwo').val();
		var stepthree = $('#stepthree').val();
		
		var projectval = projectids+";"+stepone+";"+steptwo+";"+stepthree;
		
		jp.get("${ctx}/analysisearnings/analysisEarnings/getProjectDatas?ids="+ projectval+"&timestamp="+timestamp, function(data) {
			if (data.success) {
				initchartproject(data.msg);
				
				
				$('#stepone').val(stepone);
				$('#steptwo').val(steptwo);
				$('#stepthree').val(stepthree);
				
			} else {
				jp.error(data.msg);
			}
		})
	}

	function initchartproject(data) {
		var array = eval(data);
		option = {
			title : {
				text : ''
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '初始投资', '电费', '人工费', '取暖费' ]
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
				data : [ array[4][0]+"%", array[4][1]+"%", array[4][2]+"%", array[4][3]+"%",array[4][4]+"%",array[4][5]+"%",array[4][6]+"%"  ]
			},

			yAxis : {
				name : '敏感性系数',
				type : 'value'
			},
			series : [
					{
						name : '初始投资',
						type : 'line',
						stack : '总量1',
						data : [ array[0][0], array[0][1],array[0][2], array[0][3],array[0][4], array[0][5],array[0][6]]
					}, {
						name : '电费',
						type : 'line',
						stack : '总量2',
						data : [ array[1][0],  array[1][1], array[1][2],  array[1][3], array[1][4], array[1][5],array[1][6] ]
					}, {
						name : '人工费',
						type : 'line',
						stack : '总量3',
						data : [array[2][0], array[2][1], array[2][2], array[2][3],array[2][4],array[2][5],array[2][6] ]
					}, {
						name : '取暖费',
						type : 'line',
						stack : '总量4',
						data : [array[3][0], array[3][1], array[3][2], array[3][3],array[3][4],array[3][5],array[3][6] ]
					} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	}
	
	function downLoad(){
		var param = $("#feaProjectBId").val();
		window.location.href="${ctx}/analysisearnings/analysisEarnings/exportmgExcel?ids=" + param;
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
				<div style="width: 100%; height: 100%; margin: 0 auto;">
					<div id="main" style="width: 40%; height: 400px; float: left;"></div>
					<div id="selfform" style="width: 55%; float: right;">
						<form:form id="inputForm" modelAttribute="analysisEarnings"
							action="${ctx}/analysisearnings/analysisEarnings/zhaopeng"
							method="post" class="form-horizontal">
							<form:hidden path="id" />
							<sys:message content="${message}" />
							<table class="table table-no-bordered" style="width: 450px">
								<tbody>
									<fieldset>
										<legend>项目选择</legend>
										<tr>
											<td class="width-15 active"><label class="pull-right">项目：</label></td>
											<td class="width-35"><sys:gridselect
													url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB"
													name="feaProjectB.id"
													value="${analysisEarnings.feaProjectB.id}"
													labelName="feaProjectB.projectName"
													labelValue="${analysisEarnings.feaProjectB.projectName}"
													title="选择项目" cssClass="form-control required"
													fieldLabels="项目名称" fieldKeys="projectName"
													searchLabels="项目名称" searchKeys="projectName"></sys:gridselect>
											</td>
										</tr>
									</fieldset>
								</tbody>
							</table>

							<table id="stepnum" class="table table-no-bordered" style="width: 450px">
								<tr>
									<td style="width: 10%"><label class="self-label">初始值(%)</label></td>
									<td style="width: 10%"><label class="self-label">步长(%)</label></td>
									<td style="width: 10%"><label class="self-label">终值(%)</label></td>
								</tr>
								<tr>
									<td style="width: 10%"><form:input path="initialOutlay" id="stepone"
											htmlEscape="false" class="form-control " /></td>
									<td style="width: 10%"><form:input path="initialOutlay" id="steptwo"
											htmlEscape="false" class="form-control " /></td>
									<td style="width: 10%"><form:input path="initialOutlay" id="stepthree"
											htmlEscape="false" class="form-control " /></td>
								</tr>
							</table>

							<table class="table table-no-bordered" style="width: 450px">
								<tbody>
									<fieldset>
										<tr>
											<td style="width: 70px" colspan="6"><form:radiobuttons
													path="lineOptions"
													items="${fns:getDictList('line_options')}"
													itemLabel="label" itemValue="value" htmlEscape="false"
													class="i-checks lineOptions" /></td>
										</tr>
									</fieldset>
								</tbody>
							</table>

						</form:form>
						<!-- 工具栏 -->
						<div id="toolbar">
							<shiro:hasPermission name="analysisearnings:analysisEarnings:add">
								<a id="add" class="btn btn-primary" onclick="calculation()"><i
									class="glyphicon glyphicon-edit"></i> 计算</a>
							</shiro:hasPermission>
							
							<td>
								<a id="add" class="btn btn-primary" onclick="downLoad()"><i class="glyphicon glyphicon-edit"></i>导出报表</a>
							</td>
							
						</div>
					</div>
					<div style="clear: both;"></div>
				</div>
</body>
</div>
</div>

</div>


</body>
</html>