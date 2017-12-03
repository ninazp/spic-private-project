<%@ page contentType="text/html;charset=UTF-8" %>
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