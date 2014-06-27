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
            <a href="<%=root%>/deploy/client.jnlp" class="jvdla0">
                <div class="jvdl0x1"></div>
                <span>
                    启动自助填单系统
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
                <p>1.您需要确认您的电脑上已经安装了Java Runtime Environment(简称jre), 您可以<a href="http://javadl.sun.com/webapps/download/AutoDL?BundleId=90237"><font size=3 color="red"><b>下载JRE</b></font></a>并且安装。</p>  
                <p>2.window用户,如果出现因为安全问题引起的应用程序被阻止，请在控制面板中，点击java，设置安全级别为“中”即可。</p>
                <p>3.您点击后应用程序没有直接被启动，浏览器弹出保存jnlp的文件。请保存在本地,通过双击应用程序启动。</p>
                <p>以上方式都不能正常启动应用程序，很遗憾，您需要亲自<a href="<%=root%>/deploy/AutoPrinterClient.zip"><font size=3 color="red"><b>下载程序</b></font></a>安装包进行安装。
            </div>
        </div>
    </body>
</html>
