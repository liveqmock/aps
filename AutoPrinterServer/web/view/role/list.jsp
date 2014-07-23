<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>角色管理</h2>
        <p>角色的增加，修改，删除。角色标识用于控制系统中各个模块的权限.权限的配置请联系管理员.</p>
        <div style="margin:20px 0;"></div>
        <table class="easyui-datagrid" title="角色管理" id="tt" style="width:700px;height:450px"
               data-options="rownumbers:true,singleSelect:true,url:'<%=root%>/service/role/',method:'get',toolbar:'#tb'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:80">ID</th>
                    <th data-options="field:'rolename',width:80,align:'center'">角色名</th>
                    <th data-options="field:'ext1',width:500,align:'left'" formatter="formatWD">权限</th>
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

            function addOne() {
                window.location = '<%=root%>/view/role/add.jsp';
            }

            function updateOne() {
                var row = $('#tt').datagrid('getSelected');
                if (row) {
                    window.location = '<%=root%>/service/role/detail/' + row.id;
                }
            }

            function formatWD(value) {
                if (value) {
                    var array = value.split(",");
                    var newString = "";
                    for (var i = 0; i < array.length; i++) {
                        newString += array[i] + "<br />";
                    }
                    return newString;
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
