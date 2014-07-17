<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>网点监控</h2>
        <p>监控各个网点自助填单系统的在线/离线状态, 只列出当前登录用户部门下面登记的填单机状态。</p>
        <div style="margin:20px 0;"></div>
        <table class="easyui-datagrid" title="监控" id="tt" style="width:700px;height:400px"
               data-options="rownumbers:true,singleSelect:true,url:'<%=root%>/service/branch/',method:'get',toolbar:'#tb'">
            <thead>
                <tr>
                    <th data-options="field:'id',width:50">ID</th>
                    <th data-options="field:'address',width:120,align:'center'">网点IP</th>
                    <th data-options="field:'name',width:200,align:'center'">网点名</th>
                    <th data-options="field:'checkin',width:180,align:'center'" >上次活动时间</th>
                    <th data-options="field:'status',width:100,align:'center'" formatter="formatStatus">状态</th>

                </tr>
            </thead>
        </table>
        <div id="tb" style="padding:5px;height:auto">
            <div style="margin-bottom:5px">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="submitForm()">刷新</a>
            </div>
        </div>
        <script type="text/javascript">

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
            function submitForm() {
                $("#tt").datagrid("reload");
            }
            var toolbar = [{
                    text: '刷新',
                    iconCls: 'icon-reload',
                    handler: function() {
                        $("#tt").datagrid("reload");
                    }
                }];

        </script>
    </body>
</html>
