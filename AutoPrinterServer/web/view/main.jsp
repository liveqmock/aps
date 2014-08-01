<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <%@ include file="common.jsp"%>
    </head>

    <div id="frameflag"></div>
    <body>
        <table border="0"
               cellpadding="0" cellspacing="0" width="100%">
            <tbody>
                <tr>
                    <td class="sun-lightblue" align="left"> <font size="2" >服务端版本:<%=Constants.VERSION%>, 欢迎您:${user.name},角色:${user.role.rolename},部门:${user.department.depname}</font></td>
                    <td class="sun-lightblue" align="right"><a href="<%=root%>/service/logout/clear"><font color="white">注销</font></a></td>
                </tr>
                <tr>
                    <td colspan="2" class="sun-darkblue" >
                        <h2 >欢迎使用银行自助填单管理系统</h2>
                    </td>
                </tr>
            </tbody>
        </table>
        &nbsp;&nbsp;&nbsp;&nbsp;<font FONT-FAMILY=微软雅黑; size="2">-<a href="<%=root%>/">回到首页</a></font>
        <table style="width: 100%" border="0" cellpadding="0"
               cellspacing="0">
            <tr>
                <td valign="top" width="30%">
                    <div class="Head2 sun-verylightblue">系统管理</div>
                    <ul>
                        <c:choose> 
                            <c:when test="${fn:contains(user.role.ext1,'网点监控') or fn:contains(user.role.ext1,'系统查询')}">    
                                <li><a href="<%=root%>/service/admin/branch" target="contextframe">网点监控</a></li> 
                                </c:when> 
                            </c:choose> 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'制单工具')}">    
                                <li><a href="<%=root%>/service/admin/tool" target="contextframe">制单工具</a></li>
                                </c:when> 
                            </c:choose> 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'推送部署')}">    
                                <li><a href="<%=root%>/service/admin/deploy" target="contextframe">推送部署</a></li>
                                </c:when> 
                            </c:choose> 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'业务控制')}">    
                                <li><a href="<%=root%>/service/admin/control" target="contextframe">业务控制</a></li>
                                </c:when> 
                            </c:choose>                                 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'视频推送')}">    
                                <li><a href="<%=root%>/service/admin/video/deploy" target="contextframe">视频广告</a></li>
                                </c:when> 
                            </c:choose> 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'综合查询') or fn:contains(user.role.ext1,'系统查询')}">    
                                <li><a href="<%=root%>/service/admin/search" target="contextframe">综合查询</a></li>
                                </c:when> 
                            </c:choose> 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'认证用户')}">    
                                <li><a href="<%=root%>/service/admin/auth" target="contextframe">认证用户</a></li>
                                </c:when> 
                            </c:choose> 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'部门管理')}">    
                                <li><a href="<%=root%>/service/admin/dep" target="contextframe">部门管理</a></li>
                                </c:when> 
                            </c:choose> 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'角色管理')}">    
                                <li><a href="<%=root%>/service/admin/role" target="contextframe">角色管理</a></li>
                                </c:when> 
                            </c:choose> 
                            <c:choose> 
                                <c:when test="${fn:contains(user.role.ext1,'用户管理')}">    
                                <li><a href="<%=root%>/service/admin/user" target="contextframe">用户管理</a></li>
                                </c:when> 
                            </c:choose>                                 
                    </ul>

                </td>
                <td valign="top"  width="70%">
                    <iframe src="<%=root%>/view/lefttopage.jsp" id="contextframe" name ="contextframe" style="border : none;margin: 0px auto;scrolling:auto" width="100%" height="100%" onload="document.all['contextframe'].style.height = contextframe.document.body.scrollHeight" >
                </td>
            </tr>
        </table>
    </body>
</html>
