<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%    String id = request.getParameter("id");
    String pid = request.getParameter("pid");
    String ext1 = request.getParameter("ext1");
    if (ext1 != null && !ext1.trim().equals("")) {
        ext1 = new String(ext1.getBytes("ISO-8859-1"), "UTF-8");
        String[] extArray = ext1.split(",");
        List ls = Arrays.asList(extArray);
        request.setAttribute("extarray", ls);
    }
%>
<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
        <h2>修改内容</h2>
        <p>请按照左边提示，修改对应选项.</p>
        <div style="margin:20px 0;"></div>
        <div class="easyui-panel" title="修改" style="width:700px">
            <div style="padding:10px 60px 20px 60px">
                <form id="ff" method="post">
                    <table cellpadding="5">
                        <tr>
                            <td>ID:</td>
                            <td><input class="easyui-validatebox" type="text" name="id" id="id" disabled></input></td>
                        </tr>
                        <tr>
                            <td>部门名:</td>
                            <td><input class="easyui-validatebox" type="text" name="depname" id="depname" data-options="required:true"></input></td>
                        </tr>
                        <tr>
                            <td>上级部门:</td>
                            <td><input class="easyui-combotree" name="pid" value="<%=pid%>" id="pid" data-options="url:'<%=root%>/service/dep/tree?status=1',method:'get'" style="width:200px;"></td>
                        </tr>
                        <tr>
                            <td>说明:</td>
                            <td>
                                <textarea name="descms" id="descms"
                                          style="height: 100px; width: 200px"></textarea>
                            </td>
                        </tr>
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
                                                    <c:forEach items="${extarray}" var="current">
                                                        <option>${current}</option>
                                                    </c:forEach>
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

            $('#ff').form('load', '<%=root%>/service/dep/<%=id%>');

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
                    var selectV = $("#pid").combotree('getValue');
                    var selfId = $("#id").val();
                    if (selectV === selfId) {

                        alert("上级部门不能选取自己.");
                        return;
                    }
                    var params = {
                        "id": $("#id").val(),
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
                        url: "<%=root%>/service/dep/update",
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
