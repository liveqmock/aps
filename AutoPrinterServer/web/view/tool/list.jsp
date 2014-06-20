<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>制单工具</h2>
        <p>您可以直接通过浏览器打开制单工具，进行快速制单或者触摸屏打印系统的设计，包括样式，背景，图片，打印尺寸等。</p>
        <div style="margin:20px 0;"></div>
        <div class="jvdl0 jvdl0v0">
            <a href="<%=root%>/deploy/launch.jnlp" class="jvdla0">
                <div class="jvdl0x1"></div>
                <span>
                    启动制单工具
                </span>
            </a>
        </div>
                <p>1.您需要确认您的电脑上已经安装了Java Runtime Environment(简称jre), 您可以<a href="http://javadl.sun.com/webapps/download/AutoDL?BundleId=90237"><font size=3 color="red"><b>下载JRE</b></font></a>并且安装。</p>  
                <p>2.window用户,如果出现因为安全问题引起的应用程序被阻止，请在控制面板中，点击java，设置安全级别为“中”即可。</p>
                <p>3.您点击后应用程序没有直接被启动，浏览器弹出保存jnlp的文件。请保存在本地,通过双击应用程序启动。</p>
                <p>以上方式都不能正常启动应用程序，很遗憾，您需要亲自<a href="<%=root%>/deploy/AutoPrinter.zip"><font size=3 color="red"><b>下载制单工具</b></font></a>安装包进行安装。
    </body>
</html>
