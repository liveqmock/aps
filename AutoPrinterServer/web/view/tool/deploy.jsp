<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
        <h2>推送部署</h2>
        <p>请选择模块，提交后将会推送到各个服务网点.</p>
        <div style="margin:20px 0;"></div>


        <div class="easyui-panel" title="已部署模块" style="width:500px">
            <c:forEach items="${files}" var="current">
                &nbsp; ${current.value} &nbsp; &nbsp; &nbsp; &nbsp; 
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteFile('${current.key}')">删除</a>
                <br />
            </c:forEach>
        </div>


        <div class="easyui-panel" title="推送部署" style="width:500px">
            <div style="padding:10px 60px 20px 60px">
                <form id="ff" method="post" action="<%=root%>/service/admin/push" enctype="multipart/form-data">
                    <table cellpadding="5">
                        <tr>
                            <td>文件1:</td>
                            <td><input class="easyui-validatebox" type="file" name="file" id="file" data-options="required:true" /></td>
                        </tr>
                        <tr>
                            <td>文件2:</td>
                            <td><input type="file" name="file" id="file"/></td>
                        </tr>
                        <tr>
                            <td>文件3:</td>
                            <td><input type="file" name="file" id="file"/></td>
                        </tr>
                    </table>
                    <div style="text-align:center;padding:5px">
                        <input type="submit" value="上传" />
                        <input type="button" value="返回" onclick="javascript:window.history.back()" />
                    </div>
                </form>

            </div>
        </div>
        <script>
            function deleteFile(file) {
                if (!confirm("是否确定删除该模块?")) {
                    return;
                }
                window.location = '<%=root%>/service/admin/deploy/delete/?name=' + file;
            }

        </script>
    </body>
</html>
