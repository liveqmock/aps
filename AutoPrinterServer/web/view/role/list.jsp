<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>角色管理</h2>
        <p>角色的增加，修改，删除。角色标识用于控制系统中各个模块的权限.权限的配置请联系管理员.</p>
        <div style="margin:20px 0;"></div>
        <table class="easyui-datagrid" title="角色管理" id="tt" style="width:700px;height:400px"
               data-options="rownumbers:true,singleSelect:true,url:'<%=root%>/service/role/',method:'get',toolbar:toolbar">
            <thead>
                <tr>
                    <th data-options="field:'id',width:80">ID</th>
                    <th data-options="field:'rolename',width:80,align:'center'">角色名</th>
                    <th data-options="field:'roleid',width:240,align:'center'">角色标识</th>
                </tr>
            </thead>
        </table>
        <script type="text/javascript">

            var toolbar = [{
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: function() {
                        window.location = '<%=root%>/view/role/add.jsp';
                    }
                }, {
                    text: '更新',
                    iconCls: 'icon-edit',
                    handler: function() {
                        var row = $('#tt').datagrid('getSelected');
                        if (row) {
                            window.location = '<%=root%>/view/role/edit.jsp?id=' + row.id;
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
                        window.location = '<%=root%>/view/role/list.jsp';
                    };
                    var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                    };

                    var row = $('#tt').datagrid('getSelected');

                    $.ajax({
                        type: "POST",
                        url: "<%=root%>/service/role/delete/" + row.id,
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
