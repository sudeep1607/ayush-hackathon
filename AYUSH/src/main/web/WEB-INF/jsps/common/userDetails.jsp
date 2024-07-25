<%@page import="com.fts.Constants"%>
<%@page import="com.fts.hibernate.models.UserInfo"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
    UserInfo userInfo = ((UserInfo) session.getAttribute(Constants.USERSESSION));
%>
<script type="text/javascript">
	var FC_PWD_FLAG    	= 0;
 
	var COMMON_STATUS = {
			<%=Constants.Status.OPEN%> : '<%=Constants.StatusText.OPEN%>'
			,<%=Constants.Status.ASSIGNED%> : '<%=Constants.StatusText.ASSIGNED%>'
			,<%=Constants.Status.CLOSED%> : '<%=Constants.StatusText.CLOSED%>'
		};
</script>
</head>
<body>
</body>
</html>