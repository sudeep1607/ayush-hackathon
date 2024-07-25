<%@page import="com.fts.Constants"%>
<%
    String context      =   request.getContextPath();
%>
<script type="text/javascript">
var appURL =   "<%=context %>";
</script>
<!-- added some caalender js files -->
<link rel="stylesheet" type="text/css"  href="<%=context%>/js/lib/calender/fullcalendar.min.css" />
<link rel="stylesheet" type="text/css"  href="<%=context%>/js/lib/calender/fullcalendar.print.min.css" media="print"  />
<script type="text/javascript" src="<%=context%>/js/lib/calender/moment.min.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/jqplugin/jquery.nimble.loader.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/calender/fullcalendar.min.js"></script>
<%-- <script type="text/javascript" src="<%=context%>/js/lib/calender/calender.js"></script> --%>



<%--
<script type="text/javascript" src="<%=context%>/js/lib/jquery/jqplugin/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/jqplugin/jquery.tooltipster.min.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/plugin/ajaxService.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/plugin/hashmap.js"></script>

<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery/jquery-ui.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery/jquery.mCustomScrollbar.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery/tooltipster.css" />
--%>

<script type="text/javascript" src="<%=context%>/js/lib/jquery/plugin/hashmap.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/plugin/popup.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/plugin/alert.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/plugin/xcpw.js"></script>
<script type="text/javascript" src="<%=context%>/js/lib/jquery/plugin/dragdropupload.js"></script> 
<script type="text/javascript" src="<%=context%>/js/lib/jquery/plugin/attachment-v1.0.js"></script>
<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery/attachment-v1.0.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery/popup.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery/alert.css">
<script type="text/javascript" src="<%=context%>/js/lib/jquery/JsBarcode.all.min.js"></script>
