<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
        <h2>增加内容</h2>
        <p>请按照左边提示增加相应内容.</p>
        <div style="margin:20px 0;"></div>
        <div class="easyui-panel" title="增加" style="width:700px">
            <div style="padding:10px 60px 20px 60px">
                <form id="ff" method="post">
                    <table cellpadding="5">
                        <tr>
                            <td>部门名:</td>
                            <td><input class="easyui-validatebox" type="text" name="depname" id="depname" data-options="required:true"></input></td>
                        </tr>
                        <tr>
                            <td>上级部门:</td>
                            <td><input class="easyui-combotree" name="pid" id="pid" data-options="url:'<%=root%>/service/dep/tree',method:'get'" style="width:200px;"></td>
                        </tr>
                        <tr>
                            <td>说明:</td>
                            <td>
                                <textarea name="descms" id="descms"
                                          style="height: 100px; width: 200px"></textarea>
                            </td>
                        </tr>
                        <tr>

                            <td colspan="2">
                                <div id="p" class="easyui-panel" title="填单机配置" style="width:550px;height:200px;padding:10px;">
                                    <table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td colspan="4">
                                                网点IP:<input class="easyui-validatebox" type="text" name="wdip" id="wdip"></input>
                                            </td>
                                            <td align="center">
                                                <input type="button" id="addOne" value=" > " style="width:50px;" /><br />
                                                <input type="button" id="removeOne" value="&lt;" style="width:50px;" /><br />
                                            </td>
                                            <td colspan="4">
                                                <select name="to" id="to" multiple="multiple" size="7" style="width:200px">
                                                </select>
                                            </td>
                                        </tr>
                                    </table>

                                </div>
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

                var obj = document.getElementById("to");
                var optionAll = "";
                for (var i = 0; i < obj.options.length; i++) {
                    if (i === obj.options.length - 1) {
                        optionAll += obj.options[i].text;
                    } else {
                        optionAll += obj.options[i].text + ",";
                    }
                }

                var params = {
                    "depname": $("#depname").val(),
                    "descms": $("#descms").val(),
                    "depfather": $("#pid").combotree('getValue'),
                    "ext1": optionAll
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

            $(function() {
                //选择一项
                $("#addOne").click(function() {
                    var wdname = $("#depname").val();
                    var wdip = $("#wdip").val();

                    $("#to").append("<option>" + wdname + "-" + wdip + "</option>");
                    $("#wdip").val("");
                });

                //移除一项
                $("#removeOne").click(function() {
                    $("#to option:selected").remove();
                });

            });
        </script>

    </body>
</html>
