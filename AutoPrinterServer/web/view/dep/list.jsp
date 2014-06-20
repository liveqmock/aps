<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>部门管理</h2>
        <p>部门的增加，修改，删除。</p>
        <div style="margin:20px 0;"></div>
        <table class="easyui-datagrid" title="部门管理" id="tt" style="width:700px;height:400px"
               data-options="rownumbers:true,singleSelect:true,url:'<%=root%>/service/dep/',method:'get',toolbar:toolbar">
            <thead>
                <tr>
                    <th data-options="field:'id',width:80">ID</th>
                    <th data-options="field:'depname',width:80,align:'center'">名称</th>
                    <th data-options="field:'descms',width:240,align:'center'">说明</th>
                </tr>
            </thead>
        </table>
        <script type="text/javascript">

            var toolbar = [{
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: function() {
                        window.location = '<%=root%>/view/dep/add.jsp';
                    }
                }, {
                    text: '更新',
                    iconCls: 'icon-edit',
                    handler: function() {
                        var row = $('#tt').datagrid('getSelected');
                        if (row) {
                            window.location = '<%=root%>/view/dep/edit.jsp?id=' + row.id;
                        }

                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function() {
                        deleteObj();
                    }
                }];



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
