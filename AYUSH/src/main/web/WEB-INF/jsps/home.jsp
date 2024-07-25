  <%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.fts.hibernate.models.UserInfo"%>
<%@page import="com.fts.hibernate.models.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="shortcut icon" href="images/logo.png" type="image/png">
		<title>AYUSH - For Next Generation</title>
		<link rel="stylesheet" type="text/css" href="css/home.css">
		<link rel="stylesheet" type="text/css" href="css/all.css">
		<link rel="stylesheet" type="text/css" href="css/iconCls.css">
		<link rel="stylesheet" type="text/css" href="css/hover-min.css">
		<link rel="stylesheet" type="text/css" href="css/header-style.css">
		
	
		
		<!-- <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=3&sensor=false"></script> -->
		<!-- <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=3&sensor=false&libraries=geometry,drawing"></script> -->
		
		
		<%@ include file="common/userDetails.jsp"%>
		<%@ include file="common/common-js-css.jsp"%>
		<%@ include file="common/common-jquery.jsp"%>

		<%
			@SuppressWarnings("unchecked")
			List<String> menuInfos			=	(ArrayList<String>) request.getAttribute("menuInfos");
			@SuppressWarnings("unchecked")
			List<String> userRoles			=	(ArrayList<String>) request.getAttribute("userRoles");
			@SuppressWarnings("unchecked")
			List<String> userProjects		=	(ArrayList<String>) request.getAttribute("userProjects");
			UserInfo userinfo 				= 	(UserInfo) request.getAttribute("userinfo");
			Role defaultRole 				= 	(Role) request.getAttribute("userDefaultRole");
			String menuJsonData 			=	new ObjectMapper().writeValueAsString(menuInfos);
			String rolesJsonData 			=	new ObjectMapper().writeValueAsString(userRoles);
			Long alertCount 				=   (Long) request.getAttribute("alertCount");
			Long newFilesCount 				=   (Long) request.getAttribute("newFilesCount");
			/* String projectsJsonData 		=	new ObjectMapper().writeValueAsString(userProjects); */
		%>
		<script type="text/javascript">
			var forcedLogout		=	'<%=session.getAttribute("FORCEDLOGOUT")%>';
			//var menuJsonData	=	'[{"id":1,"module":"USER","menuText":"User Management","refMethod":"getUserGrid","iconCls":null,"status":true,"priority":1},{"id":2,"module":"WEBSITE","menuText":"Websites","refMethod":"getWebsiteGrid","iconCls":null,"status":true,"priority":2},{"id":3,"module":"POLICE_STATION","menuText":"VJA Police Stations","refMethod":"getPoliceStationGrid","iconCls":null,"status":true,"priority":3}]';
			AMS.menuJsonData			=	'<%=menuJsonData%>';
			AMS.rolesJsonData			=	'<%=rolesJsonData%>';
			<%-- AMS.projectsJsonData		=	'<%=projectsJsonData%>'; --%>
			AMS.menuJsonData  	    	=	'<%=menuJsonData%>';
			UserData.defaultRoleId		= 	'<%= defaultRole.getId() %>';
			UserData.roleName			= 	'<%= defaultRole.getName() %>';
			UserData.firstname			=	'<%= userInfo.getFirstName() %>';
			UserData.userId				= 	'<%= userInfo.getId() %>';
			UserData.lastnme           ='<%= userInfo.getLastName() %>';
			UserData.section           ='<%= userInfo.getSection().getId() %>';
			if( UserData.lastnme !=null)
				{
			   UserData.lastName				= 	'<%= userInfo.getLastName() %>';
				}
			var refresh					=	'<%=request.getParameter("r")%>';
			var alertCount				=	'<%=alertCount%>';
			var newFilesCount 			= 	'<%=newFilesCount%>'; 
			var scheme = '<%=request.getScheme()%>';            
			var serverName = '<%=request.getServerName()%>'; 
			var serverPort ='<%=request.getServerPort()%>';    
			var path = '<%=request.getContextPath()%>';
			var appUrl = scheme + "://" +serverName + ":" + serverPort + path ;
			
		</script>
		<script type="text/javascript" src="js/modules/home/functions.js"></script>
		<script type="text/javascript" src="js/modules/home/home.js"></script>
		<script type="text/javascript" src="js/modules/userInfo/changeRolenProject.js"> </script>
		<script type="text/javascript" src="js/modules/userInfo/userAuthorization.js"> </script>
	 	<script type="text/javascript" src="js/modules/userInfo/user.js"></script> 
		<script type="text/javascript" src="js/modules/userInfo/role.js"></script>
		<script type="text/javascript" src="js/modules/masters/designation.js"></script>
		<script type="text/javascript" src="js/modules/masters/section.js"></script>
		<script type="text/javascript" src="js/modules/masters/fileType.js"></script>
		<script type="text/javascript" src="js/modules/masters/organization.js"></script>
		<script type="text/javascript" src="js/modules/masters/deviceInfo.js"></script>
		<%if(userinfo.getIsCreateFile()== 1){%>
		 <script type="text/javascript" src="js/modules/fileManagement/fileEntry.js"></script>
		<%}%>
		<script type="text/javascript" src="js/modules/masters/workflow.js"></script> 
		<script type="text/javascript" src="js/modules/fileManagement/fileApproval.js"></script>
		<script type="text/javascript" src="js/modules/userInfo/fileCreatingAuthorizationScreen.js"></script>
		<script type="text/javascript" src="js/modules/fileManagement/scanFile.js"></script>
		<script type="text/javascript" src="js/modules/reports/fileStatusReport.js"></script>
		
		<script type="text/javascript" src="js/modules/reports/sectionWiseReport.js"></script> 
		<script type="text/javascript" src="js/modules/reports/userWiseReport.js"></script> 
		<script type="text/javascript" src="js/modules/reports/statusWiseReport.js"></script>
		<script type="text/javascript" src="js/modules/reports/approvalAuthorityWiseReport.js"></script>
		<script type="text/javascript" src="js/modules/userInfo/userSectionMapping.js"></script>
		  
		  
		  <!--  DASHBOARD SCREENS -->
		   <script type="text/javascript" src="js/modules/dashboard/sectoinWiseFilesDb.js"></script>
	</head>
<body id="home-body" style="background-color:#fff;">
<!-- <div id="map-canvas-sos" style="height: 100%; width: 100%;"></div> -->
</body>
</html>