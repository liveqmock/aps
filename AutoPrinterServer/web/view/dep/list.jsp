<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>部门管理</h2>
        <div id="tb" style="padding:5px;height:auto">
            <div style="margin-bottom:5px">
                部门的增加，修改，删除。
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addOne()">增加</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateOne()">更新</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteObj()">删除</a>
            </div>
        </div>
        <table class="easyui-treegrid" title="部门管理" id="tt" style="width:700px;height:400px"
               data-options="
               animate: true,
               collapsible: true,
               fitColumns: true,
               url: '<%=root%>/service/dep/',
               method: 'get',
               idField: 'id',
               treeField: 'text'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:80">ID</th>
                    <th data-options="field:'text',width:350,align:'left'">部门名称</th>
                    <th data-options="field:'ext1',width:350,align:'left'" formatter="formatWD">填单机</th>
                </tr>
            </thead>
        </table>

        <script type="text/javascript">

            function formatWD(value) {
                 if (value) {
                     var array = value.split(",");
                     var newString = "";
                     for(var i=0;i<array.length;i++){
                         newString +=array[i]+"<br />";
                     }
                     return newString;
                 }
            }
            function addOne() {
                window.location = '<%=root%>/view/dep/add.jsp';
            }

            function updateOne() {
                var row = $('#tt').datagrid('getSelected');
                if (row) {
                    
                    window.location = '<%=root%>/view/dep/edit.jsp?id=' + row.id + "&pid=" + row.pid+"&ext1="+row.ext1;
                }
            }

            function deleteObj() {

                var row = $('#tt').datagrid('getSelected');
                if (row) {
                    if (!confirm("是否确定删除该记录?")) {
                        return;
                    }
                    var params = {
                        "id": $("#id").val()
                    };
                    var successFun = function(data, textStatus) {
                        window.location = '<%=root%>/view/dep/list.jsp';
                    };
                    var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                    };

                    var row = $('#tt').datagrid('getSelected');

                    $.ajax({
                        type: "POST",
                        url: "<%=root%>/service/dep/delete/" + row.id,
                        data: JSON.stringify(params),
                        async: true,
                        dataType: 'json',
                        success: successFun,
                        error: errorFun,
                        contentType: "application/json"
                    });
                }
            }

        </script>
    </body>
</html>
