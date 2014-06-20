<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="com.mc.printer.server.constants.Constants" %> 
<%
    String root = request.getContextPath();
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="<%=root%>/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/resources/themes/icon.css">	
<link rel="stylesheet" type="text/css" href="<%=root%>/resources/document.css">
<script type="text/javascript" src="<%=root%>/resources/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=root%>/resources/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=root%>/resources/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=root%>/resources/json2.js"></script>
<!-- 引入时间选择器 -->
<script type="text/javascript"  src="<%=root%>/resources/js/JTimer_2.0.js"></script>

<style type="text/css">
    a:link{text-decoration:none;}
    a:link{color:#000000} 
    a:linked{color:#000000} 
    ul{ list-style-type: none; margin:0px; background:#FFFFFF} 
    ul li a{ display:block; width: 100%; FONT-FAMILY:微软雅黑;font-size: 14;background:#FFFFFF} 
    ul li a:hover{ background:#7AA3CC}
    <!--
    body {
        margin-left: 0px;
        margin-top: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
    }
    -->
</style>
<title>银行自助填单管理系统</title>
<script type="text/javascript">
    $.ajaxSetup({cache: false});
</script>