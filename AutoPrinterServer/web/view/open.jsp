<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%    String dengjiType = request.getParameter("type");
    request.setAttribute("type", dengjiType);
%>
<html>
    <head>
        <%@ include file="common.jsp"%>
    </head>

    <body>
        <table border="0"
               cellpadding="0" cellspacing="0" width="100%">
            <tbody>
                <tr>
                    <td class="sun-lightblue"></td>
                </tr>
                <tr>
                    <td class="sun-darkblue">
                        <h1>欢迎使用银行自助填单管理系统</h1>

                        <font size="4">服务端版本:<%=Constants.VERSION%></font>
                    </td>
                </tr>
            </tbody>
        </table>

        <div style="margin:20px 0;"></div>
        <div class="jvdl0 jvdl0v0">
            <a href="<%=root%>/deploy/AutoPrinter.zip" class="jvdla0">
                <div class="jvdl0x1"></div>
                <span>
                    下载自助填单系统
                </span>
            </a>
        </div>
        <div class="jvdl0 jvdl0v0">
            <a href="<%=root%>/service/toppage" class="jvdla0">
                <div class="jvdl0x1"></div>
                <span>
                    进入后台管理
                </span>
            </a>
        </div>
        <div align="center">
            <div style="width:600px;height:400px" align="left">
                
            </div>
        </div>
    </body>
</html>
