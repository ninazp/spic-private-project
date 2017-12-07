<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="act" tagdir="/WEB-INF/tags/act" %>
<%@ taglib prefix="table" tagdir="/WEB-INF/tags/table" %>
<%@ taglib prefix="t" uri="/menu-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>

<script type="text/javascript">
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
</script>