<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>
    </head>
    <body>
       	<h2>综合查询</h2>
        <p>查询客户填单信息，网点单据使用情况，后台操作日志等</p>

        <table cellpadding="5">
            <tr>
                <td>
                    <div class="easyui-panel" title="业务查询" style="width:350px">
                        <div style="padding:5px 5px 5px 5px">
                            <form id="ff" method="post" action="<%=root%>/service/admin/search/filter">
                                <table cellpadding="5">
                                    <tr>
                                        <td>表单页名称:</td>
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
                                <a href="#" class="easyui-linkbutton" iconCls="icon-large-chart" onclick="submitGrahic()">图表显示</a> 
                                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="submitData()">查询数据</a>  
                            </form>
                        </div>
                    </div>
                </td>


                <td>
                    <div class="easyui-panel" title="操作日志查询" style="width:350px">
                        <div style="padding:5px 5px 5px 5px">
                            <form id="fd" method="post" action="<%=root%>/service/admin/log/filter">
                                <table cellpadding="5">
                                    <tr>
                                        <td>操作员:</td>
                                        <td><input name="user" id="user" style="width:80px" /> </td>
                                    </tr>
                                    <tr>
                                        <td>关键字:</td>
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
                                        <td>部门:</td>
                                        <td>
                                            <input class="easyui-combotree" name="depname" id="depname" data-options="url:'<%=root%>/service/dep/tree',method:'get'" style="width:200px;">
                                        </td>
                                    </tr>
                                </table>
                                <!--                
                                                <div style="text-align:center;padding:5px">
                                                    <a href="javascript:void(0)" class="easyui-linkbutton" name="butsubmit" id="butsubmit">提交</a>
                                                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清除</a>
                                                </div>-->
                                <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="submitLog()">查询日志</a>  
                            </form>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
        <script type="text/javascript">

            function submitData() {
                $("form:eq(0)").attr("action", "<%=root%>/service/admin/search/data");
                $("form:eq(0)").submit();
            }

            function submitGrahic() {
                $("form:eq(0)").submit();
            }
            function submitLog() {
                $("form:eq(1)").submit();
            }
        </script>
    </body>
</html>
