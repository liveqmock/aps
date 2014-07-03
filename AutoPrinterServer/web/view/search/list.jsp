<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
    <head>
        <%@ include file="../common.jsp"%>

        <script type="text/javascript" src="<%=root%>/resources/jquery.tufte-graph.js"></script>
        <script type="text/javascript" src="<%=root%>/resources/jquery.enumerable.js"></script>
        <script type="text/javascript" src="<%=root%>/resources/raphael.js"></script>
        <link type="text/css" rel="stylesheet" href="<%=root%>/resources/tufte-graph.css"/>

    </head>
    <body>
       	<h2>综合查询结果</h2>
        <p>查询客户填单信息，网点单据使用情况等</p>
        <div style="margin:20px 0;">您当前的查询条件为 【单据名称】:<font color="red">${condition.danjuName}</font>,【客户】:<font color="red">${condition.key}</font>,【开始时间】:<font color="red">${condition.startDate}</font>,【结束时间】:<font color="red">${condition.endDate}</font>,【网点】:<font color="red">${condition.branch.name}</font></div>

        <div data-options="region:'center',title:'数据查询结果,iconCls:'icon-ok'">

            <div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
                <div title="数据" style="padding:10px">
                    <table id="dg" style="width:800px;height:auto;border:1px solid #ccc;">
                        <thead>
                            <tr>
                                <th data-options="field:'danjuName'">单据名称</th>
                                <th data-options="field:'key'">客户</th>
                                <th data-options="field:'submitdate',align:'right'">打印时间</th>
                                <th data-options="field:'branchName'">网点</th>
                                <th data-options="field:'context'">内容</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${data.count>0}">
                                <c:forEach items="${data.result}" var="current">
                                    &nbsp; 
                                    <tr>  
                                        <td>${current.danjuName}</td><td>${current.key}</td><td>${current.submitdate}</td><td>${current.branchName}</td><td>${current.context}</td>
                                    </tr>
                                </c:forEach>

                            </c:if>
                            <c:if test="${messages.count<=0}">
                                <tr><td colspan="5">当前没有任何备份记录.</td></tr>
                            </c:if>

                        </tbody>
                    </table>
                    <a href="#" class="easyui-linkbutton" onclick="javascript:window.history.back()">返回</a>
                </div>

                <div title="图表" class='example awesome' style="padding:10px">
                    <div class='graph-header'>
                        <h3>Animal awesomeness</h3>
                        <p>Measured by standard combo multiplier</p>
                    </div>
                    <div id='awesome-graph' class='graph' style='width: 370px; height: 200px;'></div>
                </div> 
            </div>
        </div>
        <script type="text/javascript">

            $('#dg').datagrid();

            function formatRole(value) {
                if (value) {
                    return value.rolename;
                }
            }
   
            function formatDep(value) {
                if (value) {
                    return value.depname;
                }
            }


            jQuery(document).ready(function() {
                jQuery('#awesome-graph').tufteBar({
                    data: [
                        // First element is the y-value
                        // Other elements are arbitary - they are not used by the lib
                        // but are passed back into callback functions
                        [1.0, {label: 'Dog'}],
                        [1.3, {label: 'Raccoon'}],
                        // etc...

                        // For stacked graphs, pass an array of non-cumulative y values
                        [[1.5, 1.0, 0.51], {label: '2005'}]
                    ],
                    // Any of the following properties can be either static values 
                    // or a function that will be called for each data point. 
                    // For functions, 'this' will be set to the current data element, 
                    // just like jQuery's $.each

                    // Bar width in arbitrary units, 1.0 means the bars will be snuggled
                    // up next to each other
                    barWidth: 0.2,
                    // The label on top of the bar - can contain HTML
                    // formatNumber inserts commas as thousands separators in a number
                    barLabel: function(index) {
                        return $.tufteBar.formatNumber(this[0]) + 'x'
                    },
                    // The label on the x-axis - can contain HTML
                    axisLabel: function(index) {
                        return this[1].label
                    },
                    // The color of the bar
                    color: function(index) {
                        return ['#E57536', '#82293B'][index % 2]
                    },
                    // Stacked graphs also pass a stackedIndex parameter
                    color:     function(index, stackedIndex) {
                        return ['#E57536', '#82293B'][stackedIndex % 2]
                    },
                            // Alternatively, you can just override the default colors and keep
                            // the built in color functions
                            colors: ['#82293B', '#E57536', '#FFBE33'],
                    // Legend is optional
                    legend: {
                        // Data can be an array of any type of object, but the default
                        // formatter works with strings
                        data: "${yewu}".replace('[','').replace(']','').split(','),
                        // By default, the colors of the graph are used
                        color: function(index) {
                            return ['#E57536', '#82293B'][index % 2]
                        },
                        // You can customize the element label - can contain HTML
                        label: function(index) {
                            return this
                        }
                    }
                });
            });

        </script>
    </body>
</html>
