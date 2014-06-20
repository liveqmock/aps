<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
        <h2>增加内容</h2>
        <p>请按照左边提示增加相应内容.</p>
        <div style="margin:20px 0;"></div>
        <div class="easyui-panel" title="增加" style="width:500px">
            <div style="padding:10px 60px 20px 60px">
                <form id="ff" method="post">
                    <table cellpadding="5">
                        <tr>
                            <td>部门名:</td>
                            <td><input class="easyui-validatebox" type="text" name="depname" id="depname" data-options="required:true"></input></td>
                        </tr>
                        
                        <tr>
                            <td>说明:</td>
                            <td>
                                <textarea name="descms" id="descms"
                                          style="height: 100px; width: 200px"></textarea>
                            </td>
                        </tr>
                    </table>
                </form>
                <div style="text-align:center;padding:5px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清除</a>
                    <a href="#" class="easyui-linkbutton" onclick="javascript:window.history.back()">返回</a>
                </div>
            </div>
        </div>

        <script>
            function submitForm() {
                var params = {
                    "depname": $("#depname").val(),
                    "descms": $("#descms").val()
                };
                var successFun = function(data, textStatus) {
                    window.location = '<%=root%>/view/dep/list.jsp';
                };
                var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                };

                $.ajax({
                    type: "POST",
                    url: "<%=root%>/service/dep/add",
                    data: JSON.stringify(params),
                    async: true,
                    dataType: 'json',
                    success: successFun,
                    error: errorFun,
                    contentType: "application/json"
                });
            }

            function clearForm() {
                $('#ff').form('clear');
            }
        </script>

    </body>
</html>
