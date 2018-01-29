<%@ page contentType="text/html;charset=UTF-8" %>
	<script>
		$(document).ready(function() {
			var to = false;
			$('#search_q').keyup(function () {
				if(to) { clearTimeout(to); }
				to = setTimeout(function () {
					var v = $('#search_q').val();
					$('#feaProjectjsTree').jstree(true).search(v);
				}, 250);
			});
			$('#feaProjectjsTree').jstree({
				'core' : {
					"multiple" : false,
					"animation" : 0,
					"themes" : { "variant" : "large", "icons":true , "stripes":true},
					'data' : {
						"url" : "${ctx}/fea/project/feaProjectB/treeData",
						"dataType" : "json" 
					}
				},
				"conditionalselect" : function (node, event) {
					return false;
				},
				'plugins': ['types', 'wholerow', "search"],
				"types":{
					'default' : { 'icon' : 'fa fa-file-text-o' },
					'1' : {'icon' : 'fa fa-home'},
					'2' : {'icon' : 'fa fa-umbrella' },
					'3' : { 'icon' : 'fa fa-group'},
					'4' : { 'icon' : 'fa fa-file-text-o' }
				}

			}).bind("activate_node.jstree", function (obj, e) {
				selectproject();
				/*var node = $('#feaProjectjsTree').jstree(true).get_selected(true)[0];
				var opt = {
					silent: true,
					query:{
						'kind.id':node.id
					}
				};
				$("#kindId").val(node.id);
				$("#kindName").val(node.text);
				$('#feaProjectBTable').bootstrapTable('refresh',opt);*/
			}).on('loaded.jstree', function() {
				$("#feaProjectjsTree").jstree('open_all');
			});
		});
	</script>