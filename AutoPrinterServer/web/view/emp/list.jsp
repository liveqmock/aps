<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>用户管理</h2>
        <p>内部员工增加，修改，删除。</p>
        <div style="margin:20px 0;"></div>
        <table class="easyui-datagrid" title="用户管理" id="tt" style="width:700px;height:400px"
               data-options="rownumbers:true,singleSelect:true,url:'<%=root%>/service/emp/',method:'get',toolbar:'#tb'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:80">ID</th>
                    <th data-options="field:'name',width:80,align:'center'">用户名</th>
                    <th data-options="field:'role',width:100,align:'center'" formatter="formatRole">角色</th>
                    <th data-options="field:'department',width:80,align:'center'" formatter="formatDep">部门</th>
                    <th data-options="field:'descms',width:240,align:'center'">说明</th>
                </tr>
            </thead>
        </table>
        <div id="tb" style="padding:5px;height:auto">
            <div style="margin-bottom:5px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addOne()">增加</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateOne()">更新</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteObj()">删除</a>
            </div>
        </div>
        <script type="text/javascript">

            function formatRole(value) {
                if (value) {
                    return value.rolename;
                }
            }
            ;

            function addOne() {
                window.location = '<%=root%>/view/emp/add.jsp';
            }

            function updateOne() {
                var row = $('#tt').datagrid('getSelected');
                if (row) {
                    window.location = '<%=root%>/view/emp/edit.jsp?id=' + row.id;
                }
            }


            function formatDep(value) {
                if (value) {
                    return value.depname;
                }
            }
            ;

            var toolbar = [{
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: function() {
                        window.location = '<%=root%>/view/emp/add.jsp';
                    }
                }, {
                    text: '更新',
                    iconCls: 'icon-edit',
                    handler: function() {
                        var row = $('#tt').datagrid('getSelected');
                        if (row) {
                            window.location = '<%=root%>/view/emp/edit.jsp?id=' + row.id;
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
                        window.location = '<%=root%>/view/emp/list.jsp';
                    };
                    var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                    };

                    var row = $('#tt').datagrid('getSelected');

                    $.ajax({
                        type: "POST",
                        url: "<%=root%>/service/emp/delete/" + row.id,
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
