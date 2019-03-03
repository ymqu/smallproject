<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>插件名称</th>
			<th>版本</th>
			<th>启用</th>

		</tr>
		<c:forEach items="${havePlugins}" var="plugin">
			<tr>
				<td>${plugin.name}</td>
				<td>${plugin.version}</td>
				<td><c:if test="${plugin.active == true}">
						<input type="checkbox" checked="checked"
							onchange="active(this,${plugin.id})" value="" />
					</c:if> <c:if test="${plugin.active == false}">
						<input type="checkbox" onchange="active(this,${plugin.id})"
							value="" />
					</c:if></td>


			</tr>
		</c:forEach>
	</table>
</body>

<script type="text/javascript">
	function active(checkBox,id){
		var check=$(checkBox).prop('checked');
		<!--TODO 需要加上 项目上下文 -->
		if(check){
			$.get("/web-bus/plugin/active?id="+id,function(data,status){
			   	 	alert(data);
			  });
		}else{
			$.get("/web-bus/plugin/disable?id="+id,function(data,status){
		   	 	alert(data);
		  });
		}
	}
</script>
</html>