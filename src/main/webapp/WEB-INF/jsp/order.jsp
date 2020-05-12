<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <title>用户订单</title>
        <base href=<%= basePath%> />
        <%@include file="commeon/head.jsp" %>
        <%@include file="commeon/tag.jsp" %>
    </head>
    <body>
    <div class="container">
        <%--导航条--%>
        <nav class="navber navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="seckill/1/list">限时秒杀</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="seckill/1/list">秒杀列表</a> </li>
                    <li><a href="#" onclick="nav.isSession.toOrder({userPhone:${user.userPhone}+'', page:1})">我的订单</a> </li>
                </ul>
                <ul class="nav navbar-nav navbar-right" id="userInfo">
                    <li><a href="user/toLoginOrRegister"><span class="glyphicon glyphicon-user">注册</span> </a> </li>
                    <li><a href="user/toLoginOrRegister"><span class="glyphicon glyphicon-user">登录</span> </a> </li>
                </ul>
            </div>
        </nav>
        <br>
        <br>
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>我的订单</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th></th>
                            <th>商品名称</th>
                            <th>电话</th>
                            <th>地址</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ss" items="${list.seckillList}" varStatus="state">
                            <tr>
                                <td>${state.index + 1}</td>
                                <td>${ss.seckill.name}</td>
                                <td>${ss.userPhone}</td>
                                <td>${user.userAds}</td>
                                <td>
                                    <a class="btn btn-info" href="javaScript:;" onclick="deleteOrder(${ss.userPhone}, ${ss.seckillId})">取消订单</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <%--分页--%>
            <div class="panel-footer text-center">
                <ul class="pagination pagination-lg pager ">
                    <li><a href="seckill/${list.page - 1}/list">上一页</a> </li>
                    <li class="disabled"><span>${list.page}</span> </li>
                    <li><a href="seckill/${list.page + 1}/list">下一页</a> </li>
                </ul>
            </div>
        </div>
    </div>
    </body>
<script>
    function deleteOrder(userPhone, seckillId) {
        $.get("order/"+userPhone+"/"+seckillId+"/delete",{},function (result) {
            if (result)
                alert(result['stateInfo'])
            else
                alert("发生错误")
        })
    }
</script>
</html>
