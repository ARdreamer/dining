<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">
    <br>
    <br>
    <form method="get" action="/sell/seller/order/search">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <input class="form-control" name="orderId" id="orderId" type="text" placeholder="订单id（选填）"
                           value="${searchForm.orderId!""}"><br>
                    <input class="form-control" name="username" id="username" type="text" placeholder="用户名（选填）"
                           value="${searchForm.username!""}"><br>
                    <input class="form-control" name="phone" id="phone" type="text" placeholder="手机号（选填）"
                           value="${searchForm.phone!""}"><br>
                </div>
                <div class="col-md-6 column">
                    <input class="form-control" type="text" placeholder="请选择日期（选填）" id="date" name="dateDay"
                           readonly="readonly" value="${searchForm.dateDay!""}"><br>
                    <input id="begintime" class="form-control" type="text" onclick="setmonth(this)"
                           name="dateMonth" placeholder="请选择月份（选填）" readonly="readonly"
                           value="${searchForm.dateMonth!""}"/><br>
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <input type="submit" class="btn btn-default btn-success btn-block" value="搜索">
                            <input type="button" class="btn btn-default btn-success btn-block" value="清空"
                                   onclick="clearText();">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed" align="center">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list orderDTOPage.content as orderDTO>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerPhone}</td>

                                <#if orderDTO.buyerAddress == "堂食">
                                    <td bgcolor="#green">${orderDTO.buyerAddress}</td>
                                <#else>
                                    <td>${orderDTO.buyerAddress}</td>
                                </#if>

                                <td>${orderDTO.orderAmount}</td>
                                <#if orderDTO.getOrderStatusEnum().desc == "新订单">
                                    <td bgcolor="#adff2f">${orderDTO.getOrderStatusEnum().desc}</td>
                                <#elseif orderDTO.getOrderStatusEnum().desc == "完成">
                                    <td bgcolor="red">${orderDTO.getOrderStatusEnum().desc}</td>
                                <#else>
                                    <td bgcolor="#663399">${orderDTO.getOrderStatusEnum().desc}</td>
                                </#if>

                                <#if orderDTO.getPayStatusEnum().desc == "未支付">
                                    <td bgcolor="#4169e1">${orderDTO.getPayStatusEnum().desc}</td>
                                <#else>
                                    <td bgcolor="red">${orderDTO.getPayStatusEnum().desc}</td>
                                </#if>

                                <td>${orderDTO.createTime?datetime}</td>
                                <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                <td>
                                    <#if orderDTO.getOrderStatusEnum().desc == "新订单">
                                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
<#--                <#if searchForm.orderId?? || searchForm.username?? || searchForm.phone?? || searchForm.dateDay?? || searchForm.dateMonth?? >-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}&orderId=${searchForm.orderId!""}&username=${searchForm.username!""}&phone=${searchForm.phone!""}&dateDay=${searchForm.dateDay!""}&dateMonth=${searchForm.dateMonth!""}">上一页</a></li>
                            </#if>

                            <#list 1..orderDTOPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${size}&orderId=${searchForm.orderId!""}&username=${searchForm.username!""}&phone=${searchForm.phone!""}&dateDay=${searchForm.dateDay!""}&dateMonth=${searchForm.dateMonth!""}">${index}</a></li>
                                </#if>
                            </#list>

                            <#if currentPage gte orderDTOPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}&orderId=${searchForm.orderId!""}&username=${searchForm.username!""}&phone=${searchForm.phone!""}&dateDay=${searchForm.dateDay!""}&dateMonth=${searchForm.dateMonth!""}">下一页</a></li>
                            </#if>
                        </ul>
                    </div>
<#--                <#else>-->
<#--                    <div class="col-md-12 column">-->
<#--                        <ul class="pagination pull-right">-->
<#--                            <#if currentPage lte 1>-->
<#--                                <li class="disabled"><a href="#">上一页</a></li>-->
<#--                            <#else>-->
<#--                                <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>-->
<#--                            </#if>-->

<#--                            <#list 1..orderDTOPage.getTotalPages() as index>-->
<#--                                <#if currentPage == index>-->
<#--                                    <li class="disabled"><a href="#">${index}</a></li>-->
<#--                                <#else>-->
<#--                                    <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>-->
<#--                                </#if>-->
<#--                            </#list>-->

<#--                            <#if currentPage gte orderDTOPage.getTotalPages()>-->
<#--                                <li class="disabled"><a href="#">下一页</a></li>-->
<#--                            <#else>-->
<#--                                <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>-->
<#--                            </#if>-->
<#--                        </ul>-->
<#--                    </div>-->

<#--                </#if>-->

                <#--分页-->
            </div>
        </div>
    </div>
</div>


<#--弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('notice').pause()" type="button"
                        class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
            </div>
        </div>
    </div>
</div>


<#--播放音乐-->
<audio id="notice" loop="loop">
    <source src="/sell/mp3/demo.mp3" type="audio/mpeg"/>
</audio>

<script src="/sell/js/jquery.min.js"></script>
<script src="/sell/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/sell/js/dateTime.min.js"></script>
<script>
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://dining.natapp1.cc/sell/webSocket');
    } else {
        alert('该浏览器不支持websocket!');
    }

    websocket.onopen = function (event) {
        heartCheck.reset().start();
        console.log('建立连接');
    }

    websocket.onclose = function (event) {
        websocket = null;
        console.log('连接关闭');
    }

    websocket.onmessage = function (event) {
        heartCheck.reset().start();
        console.log('收到消息:' + event.data)
        //弹窗提醒, 播放音乐
        $('#myModal').modal('show');

        document.getElementById('notice').play();
    }

    websocket.onerror = function () {
        alert('websocket通信发生错误！');
    }

    window.onbeforeunload = function () {
        websocket.close();
    }
    var heartCheck = {
        timeout: 60000,        //1分钟发一次
        timeoutObj: null,
        serverTimeoutObj: null,
        reset: function () {
            clearTimeout(this.timeoutObj);
            clearTimeout(this.serverTimeoutObj);
            return this;
        },
        start: function () {
            var self = this;
            this.timeoutObj = setTimeout(function () {
                //这里发送一个心跳，后端收到后，返回一个心跳消息，
                //onmessage拿到返回的心跳就说明连接正常
                websocket.send("发送维持连接消息");
                console.log("发送维持连接消息！");
                self.serverTimeoutObj = setTimeout(function () {//如果超过一定时间还没重置，说明后端主动断开了
                    websocket.close();     //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
                }, self.timeout)
            }, this.timeout)
        }
    }
</script>
<script type="text/javascript">
    $("#date").datetime({
        type: "date",
        value: [2020, 1, 1],
        success: function (res) {
            console.log(res)
        }
    })

    $("#time").datetime({
        type: "time",
        value: [12, 28]
    })

    $("#datetime").datetime({
        type: "datetime",
        value: [2019, 7, 15, 15, 30]
    })

    function clearText() {
        searchFlag()
        $("#username").val("")
        $("#phone").val("")
        $("#orderId").val("")
        $("#date").val("")
        $("#begintime").val("")
    }

    function searchFlag() {
        if ($("#username").val() != "" || $("#phone").val() != "" || $("#orderId").val() != "" || $("#date").val() != "" || $("#begintime").val() != ""){
            return true;
        }
        return false;
    }
</script>
</body>
</html>