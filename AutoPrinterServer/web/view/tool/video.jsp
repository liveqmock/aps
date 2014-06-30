<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
        <h2>广告视频推送</h2>
        <p>请选择FLV视频模式的视频。推送后，网点下次启动后，会自动更新为最新的视频，客户端系统空闲时候自动播放。</p>
        <div style="margin:20px 0;"></div>


        <div class="easyui-panel" title="已发布视频" style="width:500px">
            <c:forEach items="${files}" var="current">
                &nbsp; ${current.value} &nbsp; &nbsp; &nbsp; &nbsp; 
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteFile('${current.key}')">删除</a>
                <br />
            </c:forEach>
        </div>


        <div class="easyui-panel" title="视频广告发布" style="width:500px">
            <div style="padding:10px 60px 20px 60px">
                <form id="ff" method="post" action="<%=root%>/service/admin/video/push" enctype="multipart/form-data">
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
                window.location = '<%=root%>/service/admin/video/deploy/delete/?name=' + file;
            }

        </script>
    </body>
</html>
