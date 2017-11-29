<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报表管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="reportOneList.js" %>
	<script src="${ctxStatic}/common/js/handsontable.full.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/common/css/handsontable.full.css">
	
	<script>
	
		$(document).ready(function() {
				initreport();
		});
		
		function initreport(){
			var data = [//四行五列
		           ["总成本费用表"],
		           ["序号", "项目", "合计", "建设期", "运行期"],
		           ["", "", "", "第一年", "第二年"],
		           ["1", "折旧费", 11, 14, 13],
		           ["2", "维修费", 11, 14, 13],
		           ["3", "工资及福利", 11, 14, 13],
		           ["4", "保险费", 11, 14, 13],
		           ["5", "取暖费", 11, 14, 13],
		           ["6", "摊销费", 11, 14, 13],
		           ["7", "利息支出", 11, 14, 13],
		           ["8", "趸热费", 11, 14, 13],
		           ["", "固定成本", 11, 14, 13],
		           ["", "可变成本", 11, 14, 13],
		           ["", "总成本费用", 11, 14, 13],
		           ["", "经营成本", 11, 14, 13]
		    ];
		    
		    function negativeValueRenderer(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.color = '#32CD32';
                    td.style.textAlign = 'center';
            }
            function firstRowRenderer(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.color = '#000000';
                    td.style.textAlign = 'center';
                    td.style.fontSize= = 50px;
            }
            Handsontable.renderers.registerRenderer('negativeValueRenderer', negativeValueRenderer);
            Handsontable.renderers.registerRenderer('firstRowRenderer', firstRowRenderer);

		    $("#example").handsontable({
		           data: data,//初始化时候的数据
		           minSpareRows:0,//空出多少行
		           colHeaders:false,//显示表头
		           contextMenu:true//显示表头下拉菜单
		           ,mergeCells: [
					      //{ row: 0, col: 0, rowspan: 2, colspan: 2 },
					      //{ row: 2, col: 0, rowspan: 19, colspan: 1 },
					      { row: 0, col: 0, rowspan: 1, colspan: 5 }
					 ]
					,customBorders: [
					    /* {
					        range: {
					            from: {row: 0, col:1},
					            to: {row: 0, col:1}
					        },
					        top: {width: 0, color: '#25e825'},
					        right: {width: 0, color: '#25e825'},
					        bottom: {width: 1, color: '#25e825'},
					        left: {width: 0, color: '#25e825'}
					    } */
					    /* , */
					    {
					        row: 0,
					        col: 0,
					        top: {width: 0, color: '#FFFFFF'},
					        right: {width: 0, color: '#FFFFFF'},
					        left: {width: 0, color: '#FFFFFF'}
					    }
					]
					/* ,cell: [
					    {row:0, col:0, className: 'htCenter htMiddle', editor: false}, // 右对齐垂直居中，只读
					] */
					, cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    cellProperties.renderer = "firstRowRenderer";
	                    return cellProperties;
	                }
					
		    })
		}
		
		
		
		    
	</script>


</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">报表列表</h3>
			</div>
			<div class="panel-body">
				<sys:message content="${message}"/>
				
				
				<div id="example" ></div>
				
			</div>
		</div>
	</div>
</body>
</html>