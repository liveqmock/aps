<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>业务控制</h2>
        <p>用于填单机上业务的启用和停止，控制制单工具中“远程控制”选中的业务模块。</p>
        <div style="margin:20px 0;"></div>
        <table class="easyui-datagrid" title="用户管理" id="tt" style="width:700px;height:400px"
               data-options="rownumbers:true,singleSelect:true,url:'<%=root%>/service//client/cont/all',method:'get',toolbar:'#tb'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:40">ID</th>
                    <th data-options="field:'guidename',width:120,align:'center'">向导页</th>
                    <th data-options="field:'buttonname',width:150,align:'center'" >业务名称</th>
                    <th data-options="field:'branchname',width:240,align:'left'" formatter="formatWD">适用网点</th>
                    <th data-options="field:'status',width:80,align:'center'" formatter="formatStatus">状态</th>

                </tr>
            </thead>
        </table>
        <div id="tb" style="padding:5px;height:auto">
            <div style="margin-bottom:5px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="changeObj()">开启/停止</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateOne()">适用网点设置</a>
            </div>
        </div>
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

            function formatStatus(value) {
                if (value) {
                    if (value === 1) {
                        return "<font color='green'>●</font>";
                    }
                }
                if (value === 0) {
                    return "<font color='red'>●</font>";
                }
            }

            function updateOne() {
                var row = $('#tt').datagrid('getSelected');
                if (row) {
                    window.location = '<%=root%>/service/client/cont/' + row.id;
                }
            }


            function changeObj() {

                var row = $('#tt').datagrid('getSelected');
                if (row) {
                    var sta = 1;
                    if (row.status === 1) {
                        sta = 0;
                    }
                    var message = "是否确定开启该业务?";
                    if (sta === 0) {
                        message = "是否确定停止该业务?";
                    }
                    if (!confirm(message)) {
                        return;
                    }
//                    var params = {
//                        "status": sta
//                    };
                    var successFun = function(data, textStatus) {
                        window.location = '<%=root%>/view/tool/control.jsp';
                    };
                    var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                    };

                    var row = $('#tt').datagrid('getSelected');

                    $.ajax({
                        type: "POST",
                        url: "<%=root%>/service/client/cont/update/" + row.id+"?status="+sta,
//                        data: JSON.stringify(params),
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
