<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>

    </head>
    <body>
       	<h2>数据查询结果</h2>
        <p>您当前的查询条件为 【单据名称】:<font color="red">${condition.danjuName}</font>,【客户】:<font color="red">${condition.key}</font>,【开始时间】:<font color="red">${condition.startDate}</font>,【结束时间】:<font color="red">${condition.endDate}</font>,【网点】:<font color="red">${condition.branch.name}</font></p>

         <form id="ff" method="post" action="<%=root%>/service/admin/search/data">
            <input name="danjuName" id="danjuName" type="text" value="${condition.danjuName}" style="display:none"/>
            <input name="key" id="key" type="text" value="${condition.key}" style="display:none"/> 
            <input name="startDate" id="startDate" type="text" value="${condition.startDate}" style="display:none"/>
            <input name="endDate" id="endDate" type="text" value="${condition.endDate}" style="display:none"/> 
            <input name="branch" id="branch" type="text" value="${condition.branch.name}" style="display:none"/> 
            <input name="pageindex" id="pageindex" type="text" value="${pager.pageindex}" style="display:none"/> 
            <input name="count" id="count" type="text" value="${pager.count}" style="display:none"/> 
            <input name="pageSize" id="pageSize" type="text" value="${pager.pageSize}" style="display:none"/> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-undo" onclick="javascript:window.history.back()">返回</a> 
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteData()">清除数据</a>    
            <a href="#" class="easyui-linkbutton" onclick="last()">上一页</a>    
            <a href="#" class="easyui-linkbutton" onclick="next()">下一页</a> 
            &nbsp;&nbsp;&nbsp;&nbsp;搜索到填单数据 ${pager.count} 条. 当前页根据客户和时间作为主键整理为${data.count}条,当前第 ${pager.pageindex} 页.
        </form>
        
        <div data-options="region:'center',title:'数据查询结果,iconCls:'icon-ok'">

                <div title="数据" style="padding:10px">
                    <table id="dg" style="width:800px;height:350px;border:1px solid #ccc;">
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
       
                        </tbody>
                    </table>
                    
                </div>

               
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
                if (!confirm("确定删除所有记录?")) {
                    return;
                }
                $("form").attr("action", "<%=root%>/service/admin/search/data/delete");
                $("form").submit();

            }
        </script>
    </body>
</html>
