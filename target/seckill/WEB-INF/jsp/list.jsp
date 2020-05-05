<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/4/29
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<html>

<head>
    <title>秒杀列表页</title>
    <%@include file="commeon/head.jsp" %>
    <%@include file="commeon/tag.jsp" %>
    <base href="<%= basePath%>"><%--不加这个，跳转后会丢失项目名--%>
</head>
<body>
    <div class="container">
        <%--导航条--%>
        <nav class="navber navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/seckill/1/list">限时秒杀</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="/seckill/1/list">秒杀列表</a> </li>
                    <li><a href="#">我的订单</a> </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="user/toLoginOrRegister"><span class="glyphicon glyphicon-user">注册</span> </a> </li>
                    <li><a href="user/toLoginOrRegister"><span class="glyphicon glyphicon-user">登录</span> </a> </li>
                </ul>
            </div>
        </nav>
        <br>
        <br>
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th></th>
                            <th>名称</th>
                            <th>库存</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>创建时间</th>
                            <th>详情</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="sk" items="${list.seckillList}" varStatus="state">
                            <tr>
                                <td>${state.index + 1}</td>
                                <td>${sk.name}</td>
                                <td>${sk.number}</td>
                                <td>
                                    <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td><td>
                                    <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td><td>
                                    <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <a class="btn btn-info" href="seckill/${sk.seckillId}/detail" target="_blank">详情</a>
                                </td>
                                <td></td>
                                <td></td>
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

</html>