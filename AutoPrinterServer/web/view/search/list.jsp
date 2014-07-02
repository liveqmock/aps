<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>综合查询结果</h2>
        <p>查询客户填单信息，网点单据使用情况等</p>
        <div style="margin:20px 0;">您当前的查询条件为 【单据名称】:<font color="red">${condition.danjuName}</font>,【客户】:<font color="red">${condition.key}</font>,【开始时间】:<font color="red">${condition.startDate}</font>,【结束时间】:<font color="red">${condition.endDate}</font>,【网点】:<font color="red">${condition.branch.name}</font></div>
        <table id="dg" style="width:800px;height:380px;border:1px solid #ccc;">
            <thead>
                <tr>
                    <th data-options="field:'danjuName'">单据名称</th>
                    <th data-options="field:'key'">客户</th>
                    <th data-options="field:'submitdate',align:'right'">打印时间</th>
                    <th data-options="field:'branchName'">网点</th>
                    <th data-options="field:'context'">内容</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${data.count>0}">
                    <c:forEach items="${data.result}" var="current">
                        &nbsp; 
                        <tr>  
                            <td>${current.danjuName}</td><td>${current.key}</td><td>${current.submitdate}</td><td>${current.branchName}</td><td>${current.context}</td>
                        </tr>
                    </c:forEach>

                </c:if>
                <c:if test="${messages.count<=0}">
                    <tr><td colspan="5">当前没有任何备份记录.</td></tr>
                </c:if>

            </tbody>
        </table>
        <a href="#" class="easyui-linkbutton" onclick="javascript:window.history.back()">返回</a>
        <script type="text/javascript">


            $('#dg').datagrid();

            function formatRole(value) {
                if (value) {
                    return value.rolename;
                }
            }
            ;

            function formatDep(value) {
                if (value) {
                    return value.depname;
                }
            }
            ;


        </script>
    </body>
</html>
