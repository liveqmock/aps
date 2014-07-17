<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%   

  List roleArray = Arrays.asList(Constants.ROLE_PAGES_CN);
  request.setAttribute("roleArray", roleArray);
%>
<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
        <h2>增加内容</h2>
        <p>请按照左边提示增加相应内容.</p>
        <div style="margin:20px 0;"></div>
        <div class="easyui-panel" title="增加" style="width:700px">
            <div style="padding:10px 10px 10px 10px">
                <form id="ff" method="post">
                    <table cellpadding="5">
                        <tr>
                            <td>角色名:</td>
                            <td><input class="easyui-validatebox" type="text" name="rolename" id="rolename" data-options="required:true"></input></td>
                        </tr>
                        <tr>
                            <td>访问权限:</td>
                            <td>
                                <table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td colspan="4">
                                            <select name="from" id="from" multiple="multiple" size="10" style="width:150px">
                                                 <c:forEach items="${roleArray}" var="current">
                                                    <option>${current}</option>
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
                                            <select name="to" id="to" multiple="multiple" size="10" style="width:150px">
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
                    "rolename": $("#rolename").val(),
                    "ext1": optionAll
                };
                var successFun = function(data, textStatus) {
                    window.location = '<%=root%>/view/role/list.jsp';
                };
                var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                };

                $.ajax({
                    type: "POST",
                    url: "<%=root%>/service/role/add",
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
