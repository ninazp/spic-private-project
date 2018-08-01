<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<head>
    <meta charset="UTF-8">
    <script src="${ctxStatic}/common/js/Util-tools.js"></script>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>方案简图</title>
    <link rel="stylesheet" type="text/css" href="${ctxModules}/fea/quotation/css/base.css">
    <link rel="stylesheet" type="text/css" href="${ctxModules}/fea/quotation/css/pro.css">
</head>
<body>
<!--动态-->
<div id="pro-con"></div>
<canvas id="myCanvas" width="750" height="400"></canvas>
<script type="text/x-jquery-tmpl" id="pro">
<div class="con">
    <!--地热井潜水泵-->
    <div class="sp-con container">
        <div class="sp-con-ep">
            <img src="${ctxModules}/fea/quotation/images/sp.png"/>
            <p class="txt">${data.sp.title}</p>
        </div>
        <div class="tip sp">
            <p>流量：${data.sp.traffic}m³/h</p>
            <p>扬程：${data.sp.hm}m</p>
            <p>电机功率：${data.sp.kw}kW</p>
            <p>数量：${data.sp.num}</p>
            <p>价格：${data.sp.price}</p>
        </div>
    </div>
    <!--旋流除砂器-->
    <div class="bcd-con container">
        <div class="bcd-con-ep">
             <img src="${ctxModules}/fea/quotation/images/bcd.png"/>
            <p class="txt">${data.bcd.title}</p>
        </div>
        <div class="tip">
            <p>数量：${data.bcd.num}</p>
            <p>价格：${data.bcd.price}</p>
        </div>
    </div>
    <!--汽水分离器-->
    <div class="ms-con container">
        <div class="ms-con-ep">
            <img src="${ctxModules}/fea/quotation/images/ms.png"/>
            <p class="txt">${data.ms.title}</p>
        </div>
        <div class="tip">
            <p>数量：${data.ms.num}</p>
            <p>价格：${data.ms.price}</p>
        </div>
    </div>
    <!--板式换热器-->
    <div class="phe-con container">
        <div class="phe-con-ep">
            <img src="${ctxModules}/fea/quotation/images/phe.png"/>
            <p class="txt">${data.phe.title}</p>
        </div>
        <div class="tip">
            <p>换热量：${data.phe.kw}kW</p>
            <p>换热面积：${data.phe.m2}m2</p>
            <p>设计压力：${data.phe.mpa}Mpa</p>
            <p>数量：${data.phe.num}</p>
            <p>价格：${data.phe.price}</p>
        </div>
    </div>
    <!--循环水泵组（灰色面板）-->
    <div class="panel-con">
        <div class="panel-a"></div>
        <div class="panel-b"></div>
    </div>
    <!--高区供水-->
    <div class="hw">高区供水</div>
    <!--低区供水-->
    <div class="lw">低区供水</div>
    <!--循环水泵组-->
    <div class="cwp-con container">
        <div class="cwp-con-ep">
            <div class="cwp-a">
                <img src="${ctxModules}/fea/quotation/images/pump.png"/>
            </div>
            <div class="cwp-b">
                <img src="${ctxModules}/fea/quotation/images/pump.png"/>
            </div>
            <p class="txt">${data.cwp.title}</p>
        </div>
        <div class="tip">
            <p>流量：${data.cwp.traffic}m³/h</p>
            <p>扬程：${data.cwp.hm}m</p>
            <p>功率：${data.cwp.kw}kW</p>
            <p>数量：${data.cwp.num}</p>
            <p>价格：${data.cwp.price}</p>
        </div>
    </div>
    <!--高区回水-->
    <div class="hb">高区回水</div>
    <!--低区回水-->
    <div class="lb">低区回水</div>
    <!--回灌井-->
    <div class="rw-con">
        <div class="rw-con-ep">
            <p class="txt">回灌井</p>
        </div>
    </div>
    <!--过滤器-->
    <div class="filter-con container">
        <div class="filter-con-ep">
            <img src="${ctxModules}/fea/quotation/images/filter.png"/>
            <p class="txt">${data.filter.title}</p>
        </div>
        <div class="tip">
            <p>数量：${data.filter.num}</p>
            <p>价格：${data.filter.price}</p>
        </div>
    </div>
    <!--板式换热器2-->
    <div class="phet-con container">
        <div class="phet-con-ep">
            <img src="${ctxModules}/fea/quotation/images/phe.png"/>
            <p class="txt">${data.phet.title}</p>
        </div>
        <div class="tip">
            <p>换热量：${data.phet.kw}kW</p>
            <p>换热面积：${data.phet.m2}m2</p>
            <p>设计压力：${data.phet.mpa}Mpa</p>
            <p>数量：${data.phet.num}</p>
            <p>价格：${data.phet.price}</p>
        </div>
    </div>
    <!--补水泵-->
    <div class="pump-con container">
        <div class="pump-con-ep">
            <img src="${ctxModules}/fea/quotation/images/pump.png"/>
            <p class="txt">${data.pump.title}</p>
        </div>
        <div class="tip">
            <p>流量：${data.pump.traffic}m³/h</p>
            <p>扬程：${data.pump.hm}m</p>
            <p>功率：${data.pump.kw}kW</p>
            <p>数量：${data.pump.num}</p>
            <p>价格：${data.pump.price}</p>
        </div>
    </div>
    <!--热泵机组-->
    <div class="hpu-con container">
        <div class="hpu-con-ep">
            <img src="${ctxModules}/fea/quotation/images/hpu.png"/>
            <p class="txt">${data.hpu.title}</p>
        </div>
        <div class="tip">
            <p>制热量：${data.hpu.heat}kW</p>
            <p>电功率：${data.hpu.ep}</p>
            <p>数量：${data.hpu.num}</p>
            <p>价格：${data.hpu.price}</p>
        </div>
    </div>
    <!--循环水泵（灰色面板）-->
    <div class="panelt-con">
        <div class="panel-a">
            <p class="txt">循环水泵</p>
        </div>
        <div class="tip"></div>
    </div>
    <!--循环水泵组-->
    <div class="cwpt-con container">
        <div class="cwp-con-ep">
            <div class="cwp-a">
                <img src="${ctxModules}/fea/quotation/images/pump.png"/>
            </div>
            <div class="cwp-b">
                <img src="${ctxModules}/fea/quotation/images/pump.png"/>
            </div>
            <p class="txt">循环水泵</p>
        </div>
        <div class="tip">
            <p>流量：${data.cwp.traffic}m³/h</p>
            <p>扬程：${data.cwp.hm}m</p>
            <p>功率：${data.cwp.kw}kW</p>
            <p>数量：${data.cwp.num}</p>
            <p>价格：${data.cwp.price}</p>
        </div>
    </div>
    <!--高区回水-->
    <div class="hb-o">高区回水</div>
    <!--低区回水-->
    <div class="lb-o">低区回水</div>
</div>
</script>
</body>
<script type="text/javascript" src="${ctxModules}/fea/quotation/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${ctxModules}/fea/quotation/js/jquery.tmpl.js"></script>
<script type="text/javascript">

    window.addEventListener('load', eventWindowLoaded, false);
    function getData(){
    	/* jp.get("${ctxModules}/fea/quotation/feaDesignReport/imageDatas?projectid=${feaDesignReport.feaProjectB.id}&feaProjectB.projectName=${feaDesignReport.feaProjectB.projectName}", function(data){
   	  		if(data.success){
   	  			$('#feaDesignReportTable').bootstrapTable('refresh');
   	  			jp.success(data.msg);
   	  		}else{
   	  			jp.error(data.msg);
   	  		}
   	  	}) */
        $.ajax({
            url: "${ctx}/fea/quotation/feaDesignReport/imageDatas?feaProjectB.id=${feaDesignReport.feaProjectB.id}&feaProjectB.projectName=${feaDesignReport.feaProjectB.projectName}",
            type: 'GET',
            dataType: 'json',
        }).done(function(res) {
            setData(res);
        }).fail(function() {
            console.log("error");
        }).always(function() {
            console.log("complete");
        });
    }
    function setData(data) {
        $("#pro-con").html($("#pro").tmpl(data));
    }
    function eventWindowLoaded() {
        canvasApp(); //包含整个Canvas应用程序
        getData();//动态获取数据
    }
    function canvasSupport(e) {
        return !!e.getContext;
    }
    function canvasApp() {
        var myCanvas = document.getElementById('myCanvas');

        if (!canvasSupport(myCanvas)) {
            return;
        }

        var ctx = myCanvas.getContext('2d');

        // 画线
        function drawGrid(ctx, ox, oy, x1, y1, x2, y2, strokeStyle) {
            //参数说明 canvas的 id ，原点坐标  第一个端点的坐标，第二个端点的坐标
            var sta = new Array(x1, y1);
            var end = new Array(x2, y2);
            ctx.lineWidth = 2;
            ctx.strokeStyle = strokeStyle;
            //画线
            ctx.beginPath();
            ctx.translate(ox, oy, 0); //坐标源点
            ctx.moveTo(sta[0], sta[1]);
            ctx.lineTo(end[0], end[1]);
            ctx.fill();
            ctx.stroke();
            ctx.save();
        }
        // 画圆弧
        function drawArc(ctx,centerX, centerY, radius) {
            ctx.lineWidth = 2;
            ctx.strokeStyle = "#cd2828";

            //创建变量,保存圆弧的各方面信息
            var startingAngle = 1 * Math.PI;
            var endingAngle = 2 * Math.PI;

            //使用确定的信息绘制圆弧
            ctx.arc(centerX, centerY, radius, startingAngle, endingAngle);
            ctx.stroke();
        }
        // 画箭头
        function drawArrow(ctx, fromX, fromY, toX, toY, theta, headlen, width, color) {

            theta = typeof(theta) != 'undefined' ? theta : 30;
            headlen = typeof(headlen) != 'undefined' ? headlen : 10;
            width = typeof(width) != 'undefined' ? width : 1;
            color = typeof(color) != 'color' ? color : '#000';

            // 计算各角度和对应的P2,P3坐标
            var angle = Math.atan2(fromY - toY, fromX - toX) * 180 / Math.PI,
                    angle1 = (angle + theta) * Math.PI / 180,
                    angle2 = (angle - theta) * Math.PI / 180,
                    topX = headlen * Math.cos(angle1),
                    topY = headlen * Math.sin(angle1),
                    botX = headlen * Math.cos(angle2),
                    botY = headlen * Math.sin(angle2);


            ctx.save();
            ctx.beginPath();

            var arrowX = fromX - topX,
                    arrowY = fromY - topY;
            ctx.moveTo(arrowX, arrowY);
            ctx.moveTo(fromX, fromY);
            ctx.lineTo(toX, toY);
            arrowX = toX + topX;
            arrowY = toY + topY;
            ctx.moveTo(arrowX, arrowY);
            ctx.lineTo(toX, toY);
            arrowX = toX + botX;
            arrowY = toY + botY;
            ctx.lineTo(arrowX, arrowY);
            ctx.strokeStyle = color;
            ctx.lineWidth = width;
            ctx.stroke();
            ctx.restore();
        }

        function drawScreen() {
            //画圆弧
            drawArc(ctx,625,60,5);
            //画线
            drawGrid(ctx, 0, 0, 35, 180, 35, 55, '#f36');
            drawGrid(ctx, 0, 0, 250, 100, 200, 100, '#f36');
            drawGrid(ctx, 0, 0, 200, 100, 200, 245, '#f36');
            drawGrid(ctx, 0, 0, 480, 225, 480, 200, '#f36');
            drawGrid(ctx, 0, 0, 480, 200, 700, 200, '#f36');
            drawGrid(ctx, 0, 0, 120, 290, 80, 290, '#2F63AE');
            drawGrid(ctx, 0, 0, 395, 60, 620, 60, '#f36');

            // 向右箭头
            drawArrow(ctx, 35, 55, 80, 55, 15, 15, 2, '#f36');
            drawArrow(ctx, 120, 55, 170, 55, 15, 15, 2, '#f36');
            drawArrow(ctx, 200, 55, 250, 55, 15, 15, 2, '#f36');
            drawArrow(ctx, 300, 45, 370, 45, 15, 15, 2, '#f36');
            drawArrow(ctx, 395, 30, 600, 30, 15, 15, 2, '#f36');
            drawArrow(ctx, 630, 60, 670, 60, 15, 15, 2, '#f36');
            drawArrow(ctx, 200, 245, 260, 245, 15, 15, 2, '#f36');
            drawArrow(ctx, 300, 245, 330, 245, 15, 15, 2, '#f36');
            drawArrow(ctx, 350, 245, 380, 245, 15, 15, 2, '#f36');

            // 向左箭头
            drawArrow(ctx, 370, 110, 300, 110, 15, 15, 2, '#f36');
            drawArrow(ctx, 430, 90, 405, 90, 15, 15, 2, '#f36');
            drawArrow(ctx, 430, 125, 405, 125, 15, 15, 2, '#f36');
            drawArrow(ctx, 515, 90, 460, 90, 15, 15, 2, '#f36');
            drawArrow(ctx, 515, 125, 460, 125, 15, 15, 2, '#f36');
            drawArrow(ctx, 560, 270, 535, 270, 15, 15, 2, '#f36');
            drawArrow(ctx, 635, 250, 590, 250, 15, 15, 2, '#f36');
            drawArrow(ctx, 635, 290, 590, 290, 15, 15, 2, '#f36');
            drawArrow(ctx, 715, 250, 665, 250, 15, 15, 2, '#f36');
            drawArrow(ctx, 715, 290, 665, 290, 15, 15, 2, '#f36');

            // 向下箭头
            drawArrow(ctx, 380, 290, 300, 290, 15, 15, 2, '#2F63AE');
            drawArrow(ctx, 260, 290, 200, 290, 15, 15, 2, '#2F63AE');
            drawArrow(ctx, 170, 290, 125, 290, 15, 15, 2, '#2F63AE');

            // 向上箭头
            drawArrow(ctx, 700, 200, 700, 80, 15, 15, 2, '#f36');
            drawArrow(ctx, 625, 200, 625, 35, 15, 15, 2, '#f36');
            drawArrow(ctx, 80, 290, 80, 330, 15, 15, 2, '#2F63AE');
        }
        drawScreen();
    }
</script>
</html>