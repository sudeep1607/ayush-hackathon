
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <%@ include file="common/common-js-css.jsp"%>
		<%@ include file="common/userDetails.jsp"%>       
       	<%
			String viewName			=	(String)request.getAttribute("viewName");
       		//@SuppressWarnings("unchecked")
       		//List<ScreenInfo> screenInfos =  (ArrayList<ScreenInfo>) request.getAttribute("screenInfos");
       		//String screensJsonData = new ObjectMapper().writeValueAsString(screenInfos);
		%>
		
		<script type="text/javascript">
			var viewName		=	'<%=viewName%>';
		</script>
       
        <title> POUDURU <%=viewName %></title>
        
        <script type="text/javascript" src="js/modules/<%=viewName %>/main.js"></script>
        <%	if(request.getAttribute("menuId") != null && !"9".equals(request.getAttribute("menuId")))
        	{
        %>
        	<script type="text/javascript" src="js/common/menuItems.js"></script>
        <% } %>
        
    </head>
  
<style type="text/css">
html {
	height: 100%;
 	width: 100%;
}
body {
	height: 100%;
 	width: 100%;
  	position:absolute;
  	overflow: hidden;
}
</style>
<body>
    <div id="jsErrorMessage"></div>
</body>
</html>