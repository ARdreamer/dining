<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">
    <br>
    <br>
    <form method="get" action="/sell/seller/product/list">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <input class="form-control" name="productId" id="productId" type="text" placeholder="商品id（选填）"
                           value="${productSearchForm.productId!""}"><br>
                    <input class="form-control" name="productName" id="productName" type="text" placeholder="商品名称（选填）"
                           value="${productSearchForm.productName!""}"><br>
                    <input class="form-control" name="categoryNo" id="categoryNo" type="text" placeholder="类目（选填）"
                           value="${productSearchForm.categoryNo!""}"><br>
                </div>
                <div class="col-md-6 column">
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
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>售出数量</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="3">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list productInfoPage.content as productInfo>
                            <tr>
                                <td>${productInfo.productId}</td>
                                <td>${productInfo.productName}</td>
                                <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                                <td>${productInfo.productPrice}</td>
                                <td>${productInfo.productStock}</td>
                                <td>${productInfo.productDesc}</td>
                                <td>${productInfo.categoryNo}</td>
                                <td>${productInfo.sellCount!"0"}</td>
                                <td>${productInfo.createTime?datetime}</td>
                                <td>${productInfo.updateTime?datetime}</td>
                                <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                                <td>
                                    <#if productInfo.getProductStatusEnum().desc == "上架">
                                        <a href="/sell/seller/product/offLine?productId=${productInfo.productId}">下架</a>
                                    <#else>
                                        <a href="/sell/seller/product/onLine?productId=${productInfo.productId}">上架</a>
                                    </#if>
                                </td>
                                <td><a href="/sell/seller/product/delete?productId=${productInfo.productId}"
                                       onclick="return deleteConfirm()">删除</a></td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                        </#if>

                        <#list 1..productInfoPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>

                        <#if currentPage gte productInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="/sell/js/jquery.min.js"></script>
<script>
    function deleteConfirm() {
        return confirm("您确定要删除该商品吗?")
    }

    function clearText() {
        $("#productId").val("")
        $("#categoryNo").val("")
        $("#productName").val("")
    }
</script>
</body>
</html>