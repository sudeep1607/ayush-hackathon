<%@page import="com.fts.Constants"%>
<%
    String contextPath      =   request.getContextPath();
%>
<style type="text/css" title="currentStyle" media="screen">
    @import "<%=contextPath %>/js/lib/ExtJs-5.1.0/packages/ext-theme-neptune/resources/ext-theme-neptune-all-debug.css";
     @import "<%=contextPath %>/js/lib/ExtJs-5.1.0/packages/sencha-charts/neptune/resources/sencha-charts-all.css";
    @import "<%=contextPath %>/css/iconCls.css";
    @import "<%=contextPath %>/css/override.css";
    @import "<%=contextPath %>/css/ext-custom-components.css";
    @import "<%=contextPath %>/theme";
</style>

<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/sencha-charts.js"></script>     
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/packages/ext-theme-neptune/ext-theme-neptune-debug.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/common/overrides.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/common/ext-plugins.js"></script>


<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/Cell.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/CsvFormatter.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/ExcelFormatter.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/Exporter.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/ExporterButton.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/FileSaver.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/Formatter.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/Style.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/Workbook.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/excel/Worksheet.js"></script>


 <%-- <script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/calendar/include-ext.js"></script>
 <script type="text/javascript" src="<%=contextPath %>/js/lib/ExtJs-5.1.0/calendar/options-toolbar.js"></script> --%>
 

<script type="text/javascript">
    var AMS = window.AMS || { };
    var UserData = window.AMS || { };
    AMS.APPLICATION_MODE = 'DEVELOPMENT'; //DEPLOYMENT/DEVELOPMENT
    var contextPath =   '<%=contextPath%>';
    Ext.Loader.setConfig({enabled: true, disableCaching: false,
    });
    Ext.Loader.setPath('Ext.ux', contextPath + '/js/lib/ExtJs-5.1.0/ux');
    Ext.require(
            [
                'Ext.tab.Panel'
            ]);
    
    
    var appURL =   "<%=contextPath %>";
</script>


<script type="text/javascript" src="<%=contextPath %>/js/common/Utilities.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/common/appLabels.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/common/appURLs.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/common/ext-custom-components.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/common/ext-common.js"></script>


<%-- !-- For aaading calender scripts   ----@vivek -->

 <link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/calendar.css"> 
 <link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/examples.css">
 --%>



<script type="text/javascript">

</script>


