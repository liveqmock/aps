<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
        <h2>适用网点设置</h2>
        <p>该网点的这个业务将被远程控制,控制该业务是否开启. 未开启的状态下,该业务在填单机上表现为不可点击.</p>
        <div style="margin:20px 0;"></div>
        <div class="easyui-panel" title="设置" style="width:700px">
            <div style="padding:10px 10px 10px 10px">
                <form id="ff" method="post">
                    <table cellpadding="5">
                        <tr>
                            <td>业务ID:</td>
                            <td><input class="easyui-validatebox" type="text" name="id" id="id" value="${data.id}" disabled></input></td>
                        </tr>
                        <tr>
                            <td>向导页名:</td>
                            <td><input class="easyui-validatebox" type="text" name="guidename" id="guidename" value="${data.guidename}" data-options="required:true" disabled></input></td>
                        </tr>
                        <tr>
                            <td>业务名:</td>
                            <td><input class="easyui-validatebox" type="text" name="buttonname" id="buttonname" value="${data.buttonname}" data-options="required:true" disabled></input></td>
                        </tr>
                        <tr>
                            <td>网点选择:</td>
                            <td>
                                <table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td colspan="4">
                                            <select name="from" id="from" multiple="multiple" size="10" style="width:200px">
                                                <c:forEach items="${allbranch}" var="current">
                                                    <option>${current.name}-${current.address}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td align="center">
                                            <input type="button" id="addAll" value=" >> " style="width:50px;" /><br />
                                            <input type="button" id="addOne" value=" > " style="width:50px;" /><br />
                                            <input type="button" id="removeOne" value="&lt;" style="width:50px;" /><br />
                                            <input type="button" id="removeAll" value="&lt;&lt;" style="width:50px;" /><br />
                                        </td>
                                        <td colspan="4">
                                            <select name="to" id="to" multiple="multiple" size="10" style="width:200px">
                                                <c:forEach items="${mybranch}" var="current">
                                                    <option>${current}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </table>

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

            var obj = document.getElementById("to");
            var objFrom = document.getElementById("from");
            for (var k = 0; k < obj.options.length; k++) {
                for (var p = 0; p < objFrom.options.length; p++) {
                    if (objFrom.options[p].text === obj.options[k].text) {
                        objFrom.options.remove(p);
                    }
                }
            }

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

//                var params = {
//                    "rolename": $("#rolename").val(),
//                    "ext1": optionAll
//                };
                var successFun = function(data, textStatus) {
                    window.location = '<%=root%>/view/tool/control.jsp';
                };
                var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                };

                $.ajax({
                    type: "POST",
                    url: "<%=root%>/service/client/cont/updatebranch/"+${data.id}+"?branch="+optionAll,
//                    data: JSON.stringify(params),
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
                    $("#from option:selected").clone().appendTo("#to");
                    $("#from option:selected").remove();
                });

                //选择全部
                $("#addAll").click(function() {
                    $("#from option").clone().appendTo("#to");
                    $("#from option").remove();
                });

                //移除一项
                $("#removeOne").click(function() {
                    $("#to option:selected").clone().appendTo("#from");
                    $("#to option:selected").remove();
                });

                //移除全部
                $("#removeAll").click(function() {
                    $("#to option").clone().appendTo("#from");
                    $("#to option").remove();
                });
            });
        </script>

    </body>
</html>
