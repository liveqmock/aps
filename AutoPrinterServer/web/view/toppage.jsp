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
                    <td class="sun-lightblue">123</td>
                </tr>
                <tr>
                    <td class="sun-darkblue">
                        <h1>欢迎使用银行自助填单管理系统</h1>

                        <font size="4">服务端版本:<%=Constants.VERSION%></font>
                    </td>
                </tr>
            </tbody>
        </table>

        <c:if test="${type=='1'}">
            <div class="demo-info">
                <div class="demo-tip icon-tip"></div>
                <div>认证失败，请重新登录.</div>
            </div>
        </c:if>

        <c:if test="${first=='1'}">
            <div class="demo-info">
                <div class="demo-tip icon-tip"></div>
                <div>会话过期，请重新登录.</div>
            </div>
        </c:if>

        <c:if test="${first=='3'}">
            <div class="demo-info">
                <div class="demo-tip icon-tip"></div>
                <div>系统完成初始化,请重新登录.</div>
            </div>
        </c:if>                

        <c:if test="${first=='4'}">
            <div class="demo-info">
                <div class="demo-tip icon-tip"></div>
                <div>系统有数据，不能完成初始化.请先手动删除数据后再完成初始化工作.</div>
            </div>
        </c:if>   

        <c:choose>  

            <c:when test="${first=='2'}"> 
                <div class="demo-info">
                    <div class="demo-tip icon-tip"></div>
                    <div>当前系统还没有初始化.</div>
                    <div id="dlg" class="easyui-dialog" style="width:550px;height:300px;padding:10px 30px;"
                         title="初始化" buttons="#dlg-buttons" closable="false" align="center" >
                        <h2>系统初始化信息设置</h2>
                        <form id="ff" method="post">
                            <table>
                                <tr>
                                    <td>部门:</td>
                                    <td><input type="text" id="departmentName" name="departmentName" style="width:350px;"/></td>
                                </tr>
                                <tr>
                                    <td>管理员:</td>
                                    <td><input type="text" id="name"  name="name" style="width:350px;"/></td>
                                </tr>
                                <tr>
                                    <td>密码:</td>
                                    <td><input type="password" id="password" class="easyui-validatebox" data-options="required:true" name="password" style="width:350px;"/></td>
                                    <!--                                    <td><select class="easyui-combotree" url="../view/json/roles.json" name="city" style="width:156px;"/></td>-->
                                </tr>
                            </table>
                            <div id="dlg-buttons">
                                <input type="button" name="提交" value="提交" onClick="submitForm()"/>
                                <!--                                <a href="#" id="btn" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitForm()">提交</a>-->
                            </div>
                        </form>
                    </div>
                </div>
            </c:when>  
            <c:otherwise>
                <div id="login" class="easyui-window" title="请登陆" style="margin-left:auto;margin-right:auto;width:500px;height:240px;padding:10px;"
                     collapsible="true" closable="false" minimizable="false"  maximizable=false>  

                    <!--        <div id="p" class="easyui-panel" title="My Panel" style="width:500px;height:150px;padding:10px;background:#fafafa;" 
                                 iconCls="icon-save" closable="true" 
                                 collapsible="true" minimizable="true" maximizable=true>-->

                    <div style="padding: 10px 0 0px 10px" align="center" >
                        <form id="ff" action="<%=root%>/j_spring_security_check" method="post">
                            <table>
                                <tr>
                                    <td>用户名:</td>
                                    <td><input class="easyui-validatebox" type="text"
                                               name="j_username" data-options="required:true"></input></td>
                                </tr>
                                <tr>
                                    <td>密码:</td>
                                    <td><input class="easyui-validatebox" type="password"
                                               name="j_password"></input></td>
                                </tr>
                            </table>
                            <input type="submit" name="登录" value="登录" />
                        </form>
                    </div>
                </div>
            </c:otherwise>  
        </c:choose> 

        <script type="text/javascript">

            function submitForm() {
                var params = {
                    "departmentName": $("#departmentName").val(),
                    "name": $("#name").val(),
                    "password": $("#password").val()
                };
                var successFun = function(data, textStatus) {
                    alert("系统初始化成功.");
                };
                var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                };

                $.ajax({
                    type: "POST",
                    url: "<%=root%>/service/init",
                    data: JSON.stringify(params),
                    dataType: 'json',
                    success: successFun,
                    error: errorFun,
                    contentType: "application/json"
                });
 
            }
        </script>
    </body>
</html>
