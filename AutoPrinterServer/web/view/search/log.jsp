<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>

    </head>
    <body>
       	<h2>日志查询结果</h2>
        <p>查询操作日志 【操作员】:<font color="red">${condition.username}</font>,【部门ID】:<font color="red">${condition.depid}</font>,【开始时间】:<font color="red">${condition.startDate}</font>,【结束时间】:<font color="red">${condition.endDate}</font>,【关键字】:<font color="red">${condition.context}</font></p>

        <form id="ff" method="post" action="<%=root%>/service/admin/log/filter">
            <input name="user" id="user" type="text" value="${condition.username}" style="display:none"/>
            <input name="key" id="key" type="text" value="${condition.context}" style="display:none"/> 
            <input name="startDate" id="startDate" type="text" value="${condition.startDate}" style="display:none"/>
            <input name="endDate" id="endDate" type="text" value="${condition.endDate}" style="display:none"/> 
            <input name="depname" id="depname" type="text" value="${condition.depid}" style="display:none"/> 
            <input name="pageindex" id="pageindex" type="text" value="${pager.pageindex}" style="display:none"/> 
            <input name="count" id="count" type="text" value="${pager.count}" style="display:none"/> 
            <input name="pageSize" id="pageSize" type="text" value="${pager.pageSize}" style="display:none"/> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick="javascript:window.history.back()">返回</a> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteData()">清除数据</a>    
            <a href="#" class="easyui-linkbutton" onclick="last()">上一页</a>    
            <a href="#" class="easyui-linkbutton" onclick="next()">下一页</a> 
            &nbsp;&nbsp;&nbsp;&nbsp;搜索到数据 ${pager.count} 条,当前第 ${pager.pageindex} 页,每页显示 ${pager.pageSize} 条数据.
        </form>

        <div data-options="region:'center',title:'数据查询结果,iconCls:'icon-ok'">
            <table id="dg" style="width:800px;height:350px;border:1px solid #ccc;">
                <thead>
                    <tr>
                        <th data-options="field:'id'">ID</th>
                        <th data-options="field:'eventdate'">时间</th>
                        <th data-options="field:'username'">操作员</th>
                        <th data-options="field:'depname'">部门</th>
                        <th data-options="field:'branchName'">内容</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${data}" var="current">
                        &nbsp; 
                        <tr>  
                            <td>${current.id}</td> <td>${current.eventdate}</td><td>${current.username}</td><td>${current.depname}</td><td>${current.context}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script type="text/javascript">

            $('#dg').datagrid();

            function last() {
                var pageindex = $("#pageindex").val();
                var pageSize = $("#pageSize").val();
                var count = $("#count").val();
//                alert("count" + count + ",pageSize" + pageSize + ",pageindex:" + pageindex);
                pageindex = pageindex * 1 - 1;
                pageindex = pageindex <= 0 ? 1 : pageindex;
                $("#pageindex").val(pageindex);
                $("form").submit();
            }
            function next() {
                var pageindex = $("#pageindex").val();
                var pageSize = $("#pageSize").val();
                var count = $("#count").val();
//                alert("count" + count + ",pageSize" + pageSize + ",pageindex:" + pageindex);
                pageindex = pageindex * 1 + 1;
                var maxPage = Math.ceil(count * 1 / pageSize * 1);
                pageindex = pageindex > maxPage ? maxPage : pageindex;
                $("#pageindex").val(pageindex);
                $("form").submit();
            }

            function deleteData() {
                var count = $("#count").val();
                if (!confirm("确定删除所有 "+count+" 条记录?")) {
                    return;
                }
                $("form").attr("action", "<%=root%>/service/admin/log/delete");
                $("form").submit();

            }
        </script>
    </body>
</html>
