<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>综合查询</h2>
        <p>查询客户填单信息，网点单据使用情况等</p>
        <div class="easyui-panel" title="查询" style="width:500px">
            <div style="padding:10px 60px 20px 60px">
                <form id="ff" method="post" action="<%=root%>/service/admin/search/filter">
                    <table cellpadding="5">
                        <tr>
                            <td>单据名称:</td>
                            <td><input name="danjuName" id="danjuName" style="width:80px" /> </td>
                        </tr>
                        <tr>
                            <td>客户:</td>
                            <td><input name="key" id="key" style="width:120px" /> </td>
                        </tr>
                        <tr>
                            <td>开始时间:</td>
                            <td>
                                <input class="easyui-datebox" name="startDate" id="startDate" style="width:140px" />  
                            </td>
                        </tr>
                        <tr>
                            <td>结束时间:</td>
                            <td>
                                <input class="easyui-datebox" name="endDate" id="endDate" style="width:140px" /> 
                            </td>
                        </tr>

                        <tr>
                            <td>网点:</td>
                            <td>
                                <input class="easyui-combobox" 
                                       name="branch"
                                       id="branch"
                                       data-options="
                                       url:'<%=root%>/service/branch/',
                                       method:'get',
                                       valueField:'id',
                                       textField:'name',
                                       panelHeight:'auto'
                                       ">
                            </td>
                        </tr>
                    </table>
                    <!--                
                                    <div style="text-align:center;padding:5px">
                                        <a href="javascript:void(0)" class="easyui-linkbutton" name="butsubmit" id="butsubmit">提交</a>
                                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清除</a>
                                    </div>-->
                    <input type="submit" value="提交"/>
                </form>
            </div>
        </div>

        <script type="text/javascript">

            function submitForm() {
                var params = {
                    "startDate": $("#startDate").val(),
                    "endDate": $("#endDate").val(),
                    "branchid": $("#branch").combobox('getValue'),
                    "danjuName": $("#danjuName").val(),
                    "key": $("#key").val()
                };
                var successFun = function(data, textStatus) {
                };
                var errorFun = function(XMLHttpRequest, textStatus, errorThrown) {
                };

                $.ajax({
                    type: "POST",
                    url: "<%=root%>/service/admin/search/filter",
                    data: JSON.stringify(params),
                    async: true,
                    dataType: 'json',
                    success: successFun,
                    error: errorFun,
                    contentType: "application/json"
                });

                window.location = '<%=root%>/service/admin/search/filter';
            }

            function clearForm() {
                $('#ff').form('clear');
            }
        </script>
    </body>
</html>
