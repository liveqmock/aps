<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>认证用户列表</h2>
        <p>填单机上认证用户列表,可以暂停/启动用户办理业务,或删除注册用户.</p>
        <div id="tb" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="changeObj()">启用/停用</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="deleteObj()">删除</a>
            </div>
        </div>
        <table id="tt"></table> 

        <script type="text/javascript">

            $('#tt').datagrid({
                title: '认证用户',
                width: 700,
                singleSelect:true,
                height: 450,
                toolbar:'#tb',
                url: '<%=root%>/service/client/auth/',
                columns: [[
                        {field: 'id', title: 'ID', width: 80},
                        {field: 'userid', title: '认证用户名', width: 120},
                        {field: 'branchname', title: '注册网点', width: 120, align: 'left'},
                        {field: 'context', title: '注册内容', width: 200, align: 'left', formatter: function(value, row, index) {
                                return formatContext(value);
                            }},
                        {field: 'status', title: '状态', width: 80, align: 'left', formatter: function(value, row, index) {
                                return formatStatus(value);
                            }
                        }
                    ]],
                pagination: true
            });

            function formatStatus(value) {
                if (value) {
                    if (value === 2) {
                        return "<font color='green'>●</font>";
                    }
                }
                if (value === 3) {
                    return "<font color='red'>●</font>";
                }
            }
            function formatContext(value) {
                if (value) {
                    var array = value.split("###");
                    var newString = "";
                    for (var i = 0; i < array.length; i++) {
                        newString += array[i] + "<br />";
                    }
                    return newString;
                }

            }

            function submitForm() {
                $("#tt").datagrid("reload");
            }

            function changeObj() {

                var row = $('#tt').datagrid('getSelected');
                if (row) {
                    var sta = 2;
                    if (row.status === 2) {
                        sta = 3;
                    }
                    var message = "确定开启该注册用户办理业务?";
                    if (sta === 3) {
                        message = "确定暂停该注册用户办理业务?";
                    }
                    if (!confirm(message)) {
                        return;
                    }
//                    var params = {
//                        "status": sta
//                    };
                    var successFun = function(data, textStatus) {
                        window.location = '<%=root%>/view/branch/auth.jsp';
                    };
                    var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                    };

                    var row = $('#tt').datagrid('getSelected');

                    $.ajax({
                        type: "POST",
                        url: "<%=root%>/service/client/auth/update/" + row.id + "?status=" + sta,
//                        data: JSON.stringify(params),
                        async: true,
                        dataType: 'json',
                        success: successFun,
                        error: errorFun,
                        contentType: "application/json"
                    });
                }
            }

            function deleteObj() {

                var row = $('#tt').datagrid('getSelected');
                if (row) {
                    if (!confirm("是否确定删除该注册用户?")) {
                        return;
                    }
                    var params = {
                        "id": $("#id").val()
                    };
                    var successFun = function(data, textStatus) {
                        window.location = '<%=root%>/view/branch/auth.jsp';
                    };
                    var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                    };

                    var row = $('#tt').datagrid('getSelected');

                    $.ajax({
                        type: "POST",
                        url: "<%=root%>/service/client/auth/delete/" + row.id,
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
