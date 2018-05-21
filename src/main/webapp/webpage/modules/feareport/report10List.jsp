<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>EVA测算表管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="report10List.js" %>
	
		<script src="${ctxStatic}/common/js/handsontable.full.js"></script>
	<script src="${ctxStatic}/common/js/xlsx.full.min.js"></script>
<%-- 	<script src="${ctxStatic}/common/js/exportReportUtil.js"></script> --%>
	<link rel="stylesheet" href="${ctxStatic}/common/css/handsontable.full.css">
    <script>
		//----工具类 开始----
		var utl = {};
		utl.Binary = {
		    fixdata(data) { //文件流转BinaryString
		        var o = "",
		            l = 0,
		            w = 10240;
		        for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
		        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
		        return o;
		    },
		    s2ab(s) { //字符串转字符流
		        var buf = new ArrayBuffer(s.length);
		        var view = new Uint8Array(buf);
		        for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
		        return buf;
		    }
		}
		utl.XLSX = {
		    wb: null,
		    rABS: false,
		    import(f, c) {//导入根据文件
		        this.wb = null;
		        var reader = new FileReader();
		        reader.onload = function (e) {
		            var data = e.target.result;
		            if (utl.XLSX.rABS) {
		                utl.XLSX.wb = XLSX.read(btoa(utl.Binary.fixdata(data)), {//手动转化
		                    type: 'base64'
		                });
		            } else {
		                utl.XLSX.wb = XLSX.read(data, {
		                    type: 'binary'
		                });
		            }
		            if (c && typeof (c)) {
		                c();
		            }
		        };
		        if (utl.XLSX.rABS) {
		            reader.readAsArrayBuffer(f);
		        } else {
		            reader.readAsBinaryString(f);
		        }
		    },
		    onImport(obj, c) {//导入根据 input file标签
		        if (!obj.files) {
		            return;
		        }
		        this.import(obj.files[0], c);
		    },
		    getSheetsByIndex(index = 0) {//获取数据根据Sheets索引
		        return XLSX.utils.sheet_to_json(this.wb.Sheets[this.wb.SheetNames[index]]);
		    },
		    getCharCol(n) {
		        let temCol = '',
		            s = '',
		            m = 0
		        while (n > 0) {
		            m = n % 26 + 1
		            s = String.fromCharCode(m + 64) + s
		            n = (n - m) / 26
		        }
		        return s
		    },
		    export(json, title, type) {//导出
		        var keyMap = [];//获取keys
		        for (k in json[0]) {
		            keyMap.push(k);
		        }
		        var tmpdata = [];//用来保存转换好的json 
		        json.map((v, i) => keyMap.map((k, j) => Object.assign({}, {
		            v: v[k],
		            position: (j > 25 ? utl.XLSX.getCharCol(j) : String.fromCharCode(65 + j)) + (i + 1)
		        }))).reduce((prev, next) => prev.concat(next)).forEach((v, i) => tmpdata[v.position] = {
		            v: v.v
		        });
		        var outputPos = Object.keys(tmpdata); //设置区域,比如表格从A1到D10
		        var tmpWB = new Object();
		        title = title ? title : "mySheet";
		        tmpWB.SheetNames = [title];
		        tmpWB.Sheets = {};
		        tmpWB.Sheets[title] = Object.assign({}, tmpdata, //内容
		            {
		                '!ref': outputPos[0] + ':' + outputPos[outputPos.length - 1] //设置填充区域
		            });
		        return new Blob([utl.Binary.s2ab(XLSX.write(tmpWB,
		            { bookType: (type == undefined ? 'xlsx' : type), bookSST: false, type: 'binary' }//这里的数据是用来定义导出的格式类型
		        ))], { type: "" }); //创建二进制对象写入转换好的字节流
		    },
		    onExport(json, title, type) {//直接下载
		        utl.Download.byObj(this.export(json, title, type), "下载.xlsx");
		    }
		};
		utl.Download = {
		    byURL(url, fileName) {//动态下载
		        var tmpa = document.createElement("a");
		        tmpa.download = fileName || "下载";
		        tmpa.href = url; //绑定a标签
		        tmpa.click(); //模拟点击实现下载
		    },
		    byObj(obj, fileName) {//内存二进制数据下载
		        this.byURL(URL.createObjectURL(obj), fileName);
		        setTimeout(function () { //延时释放
		            URL.revokeObjectURL(obj); //用URL.revokeObjectURL()来释放这个object URL
		        }, 100);
		    }
		}
		utl.Object = {
		    reverse(obj) {//对象键值倒转
		        var o = new Object();
		        for (var k in obj) {
		            o[obj[k]] = k;
		        }
		        return o;
		    },
		    deepCopy() {//深拷贝
		        let temp = obj.constructor === Array ? [] : {}
		        for (let val in obj) {
		            temp[val] = typeof obj[val] == 'object' ? deepCopy(obj[val]) : obj[val];
		        }
		        return temp;
		    },
		    copyJson(o) { return JSON.parse(JSON.stringify(o)); }//拷贝JSON格式
		}
		//----工具类 结束----
	</script>
	<script>
		var hot;
		var projectIds = '';
		$(document).ready(function() {
				init(projectIds);
		});
		
		function init(projectIds){
			if(projectIds.length ==0){
 				jp.get("${ctx}/feareport/report10/getProjectDatas?ids=" + projectIds, function (data) {
 					if(data.success){
 						$("#feaProjectBId").val(data.projectId);
 				    	$("#feaProjectBName").val(data.projectName);
 				    	execute(data.projectId);
 	      	  		}else{
 	      	  			//execute(projectIds);
 	      	  			//jp.error(data.msg);
 	      	  		}
 	            })
 			}else{
 				execute(projectIds);
 			}
		}
		
		function execute(projectIds){
			jp.loading();
			var timestamp = (new Date()).valueOf();   
			jp.get("${ctx}/feareport/report10/getReportDatas?ids=" + projectIds+"&timestamp="+timestamp, function (data) {
				if(data.success){
      	  			//初始化报表
					initreport(data.msg);
      	  		}else{
      	  			jp.error(data.msg);
      	  		}
            })
			jp.close();
		
		}
		
		function initreport(datas){
			$('#report').empty();
			//给项目参照赋值
			//固定数据
 			var data = [
		           ["","","","EVA测算表列表"],
		           ["","","","人民币单位：万元"],
		           ["序号", "项目", "合计","建设期","运行期"],
		           [""],
		           ["1","税后净营业利润"],
					["1.1","净利润"],
					["1.2","利息支出"],
					["2","资本成本"],
					["2.1","所有者权益"],
					["2.2","负债小计"],
					["2.3","在建工程"],
					["3","EVA"],
					["4","△EVA"]
		    ];
 			//报表数据
			var array = eval(datas);
			var colLeg = array[0].length;
			//计算年份数据 和 时期数据
			var yearAndPeriodArray = yearAndPeriodDatas(colLeg);
			//计算列宽
			var colWidthsArray = getColWidths(colLeg);

			function firstRowRenderer(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.color = '#000000';
                    td.style.textAlign = 'center';
                    //td.style.fontSize= = 50px;
            }
            function SecondRowRenderer(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.color = '#000000';
                    td.style.textAlign = 'right';
            }
            function ThirdRowRenderer(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.color = '#000000';
                    td.style.lineHeight =4;
                    td.style.textAlign = 'right';
            }
            
            function col1Renderer(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.color = '#000000';
                    td.style.textAlign = 'left';
                    //td.style.fontSize= = 50px;
            }
            
            function formatamount(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.color = '#000000';
                    td.style.textAlign = 'right';
                    td.innerText = value != null ? fmoney(value ,2) : "0.00";
            }
            Handsontable.renderers.registerRenderer('firstRowRenderer', firstRowRenderer);
            Handsontable.renderers.registerRenderer('SecondRowRenderer', SecondRowRenderer);
            Handsontable.renderers.registerRenderer('ThirdRowRenderer', ThirdRowRenderer);
            Handsontable.renderers.registerRenderer('col1Renderer', col1Renderer);
            Handsontable.renderers.registerRenderer('formatamount', formatamount);
            
            var hotElement = document.getElementById('report');
		    var hotElementContainer = hotElement.parentNode;
		    
		    var hotSettings = {
			    data: data,
			    //stretchH: 'all',
			    //width: 1648,
			    autoWrapRow: true,
			    height: 500,
			    maxRows: 22,
			    //maxCols: 15,
			    colWidths:colWidthsArray,
			    //colWidths: [50,100,200,300,100,43,22,22,22],
			    //autoColumnSize:true,
			    rowHeaders: true,
			    mergeCells: [
			        //第一行
			    	{row:0, col:3, rowspan:1, colspan:colLeg - 1},
			    	{row:0, col:0, rowspan:1, colspan:3},
			    	//第二行
			    	{row:1, col:3, rowspan:1, colspan:colLeg - 1},
			    	{row:1, col:0, rowspan:1, colspan:3},
			    	//第三行
			    	//{row:2, col:4, rowspan:1, colspan:10},//冻结的话  合并不生效
			    	//其他行
			    	{row:2, col:0, rowspan:2, colspan:1},
			    	{row:2, col:2, rowspan:2, colspan:1},
			    	{row:2, col:1, rowspan:2, colspan:1}
			    ],
			    contextMenu: true,
			    colHeaders:true,
			    cells: function(row, col, prop) {//单元格渲染  
			    	if(row>3 && col>1){
			    		this.renderer = formatamount;
			    	}
			    	else if(row==1){
			    		this.renderer = SecondRowRenderer;
			    	}
			    	/* else if(row==2){
			    		this.renderer = ThirdRowRenderer;
			    	} */
			    	else if(row >3 && (col == 0 || col == 1)){
			    		this.renderer = col1Renderer;
			    	}
			    	else{
                    	this.renderer = firstRowRenderer;
			    	}
            	}, 
            	fixedRowsTop:4,
            	fixedColumnsLeft:3
            	
			};
	    	hot = new Handsontable(hotElement, hotSettings);
		      /**
			   * Populate cells at position with 2D input array (e.g. `[[1, 2], [3, 4]]`).
			   * Use `endRow`, `endCol` when you want to cut input when a certain row is reached.
			   * Optional `source` parameter (default value "populateFromArray") is used to identify this call in the resulting events (beforeChange, afterChange).
			   * Optional `populateMethod` parameter (default value "overwrite", possible values "shift_down" and "shift_right")
			   * has the same effect as pasteMode option {@link Options#pasteMode}
			   *
			   * @memberof Core#
			   * @function populateFromArray
			   * @since 0.9.0
			   * @param {Number} row Start visual row index.
			   * @param {Number} col Start visual column index.
			   * @param {Array} input 2d array
			   * @param {Number} [endRow] End visual row index (use when you want to cut input when certain row is reached).
			   * @param {Number} [endCol] End visual column index (use when you want to cut input when certain column is reached).
			   * @param {String} [source="populateFromArray"] Source string.
			   * @param {String} [method="overwrite"] Populate method. Possible options: `shift_down`, `shift_right`, `overwrite`.
			   * @param {String} direction Populate direction. (left|right|up|down)
			   * @param {Array} deltas Deltas array.
			   * @returns {Object|undefined} The ending TD element in pasted area (only if any cells were changed).
			   */
			 //填充报表数据
		     hot.populateFromArray(4, 2, array, 12, colLeg+1, "populateFromArray", "overwrite", null, null);
		     //填充年份数据
		     hot.populateFromArray(2, 3, yearAndPeriodArray, 3, colLeg+1, "populateFromArray", "overwrite", null, null);
		     //hot.colWidths = colWidthsArray;
		     
		}
		
		function downLoad(){
			var d = utl.Object.copyJson(hot.getData());//<=====读取handsontable的数据
            //d.unshift(utl.Object.reverse(hot.getSettings().colHeaders));//<====追加到列头
            utl.XLSX.onExport(d);//<====导出
		}

	
		function getColWidths(colLeg) {
			var colWidthsArray = [];
			for (var i = 0; i < colLeg + 2; i++) {
				switch (i) {
				case 0:
					colWidthsArray[i] = 45;
					break;
				case 1:
					colWidthsArray[i] = 110;
					break;
				default:
					colWidthsArray[i] = 80;
				}
			}
			return colWidthsArray;
		}
	
		function yearAndPeriodDatas(colLeg){
			if(colLeg == null || colLeg < 1) return;
			var yearAndPeriodArray = [];
			yearAndPeriodArray[0] = []; //时期只有一行数据
			for(var i=0;i<colLeg-1;i++){
				switch (i) {
				case 0:
					yearAndPeriodArray[0][i] = "建设期";
					break;
				default:
					yearAndPeriodArray[0][i] = "运行期"; 
				}
			}
			yearAndPeriodArray[1] = []; //年份只有一行数据
			for(var i=0;i<colLeg-1;i++){
				var year = i+1
				yearAndPeriodArray[1][i] = "第"+ year +"年"; 
			}

	
			return yearAndPeriodArray;
		}
		function fmoney(s, n) {
			n = n > 0 && n <= 20 ? n : 2;
			s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
			var l = s.split(".")[0].split("").reverse(),
				r = s.split(".")[1];
			t = "";
			for (i = 0; i < l.length; i++) {
				t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
			}
			return t.split("").reverse().join("") + "." + r;
		}
		
		function callback(projectIds){
			init(projectIds);
		}
		
	</script>
	
</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">EVA测算表列表</h3>
			</div>
			<table class="table table-no-bordered" style="width:450px">
			   <tbody>
		   			<fieldset>
						<tr>
							<td class="width-15 active"><label class="pull-right">项目：</label></td>
							<td class="width-70">
								<sys:gridselectCallback url="${ctx}/fea/project/feaProjectB/data" id="feaProjectB" name="feaProjectB.id" value="${analysisEarnings.feaProjectB.id}" labelName="feaProjectB.projectName" labelValue="${analysisEarnings.feaProjectB.projectName}"
									 title="选择项目" cssClass="form-control required" fieldLabels="项目名称" fieldKeys="projectName" searchLabels="项目名称" searchKeys="projectName" callBack="true"></sys:gridselectCallback>
							</td>
						</tr>
			 	</tbody>
			</table>
			
			<div id="bodyHeader" class="panel-body">
				<div id="report"></div>
				<sys:message content="${message}"/>
			</div>
		</div>
	</div>
</body>
</html>