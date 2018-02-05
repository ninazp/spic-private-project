
	function isNull(data) {
	    return (data == "" || data == undefined || data == null) ? false: true;
	}
				
	function obj2string(o) {
	    var r = [];
	    if (typeof o == "string") {
	        return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	    }
	    if (typeof o == "object") {
	        if (!o.sort) {
	            for (var i in o) {
	                r.push(i + ":" + obj2string(o[i]));
	            }
	            if ( !! document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
	                r.push("toString:" + o.toString.toString());
	            }
	            r = "{" + r.join() + "}";
	        } else {
	            for (var i = 0; i < o.length; i++) {
	                r.push(obj2string(o[i]))
	            }
	            r = "[" + r.join() + "]";
	        }
	        return r;
	    }
	    return o.toString();
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
	
