<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/5/4
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <title>登录注册页面</title>
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
                <li><a href="/seckill/1/list">秒杀列表</a> </li>
                <li><a href="#">我的订单</a> </li>
            </ul>
            <ul class="nav navbar-nav navbar-right" id="userInfo">
                <li><a href="#"><span class="glyphicon glyphicon-user">注册</span> </a> </li>
                <li><a href="#"><span class="glyphicon glyphicon-user">登录</span> </a> </li>
            </ul>
        </div>
    </nav>
    <br>
    <br>

    <div class="panel panel-default">
        <ul id="myTab" class="nav nav-tabs text-center">
            <li class="active">
                <a href="#login" data-toggle="tab"><h3>登录</h3></a>
            </li>
            <li>
                <a href="#register" data-toggle="tab"><h3>注册</h3></a>
            </li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="login">
                <h3 class="center-block text-center">用户登录</h3>
                <form role="form" onsubmit="return false" method="get" >
                    <div class="form-group">
                        <label for="userPhone">手机号</label>
                        <input type="text" class="form-control" name="userPhone" id="userPhone" placeholder="请输入手机号">
                    </div>
                    <div class="form-group">
                        <label for="userPsw">密码</label>
                        <input type="password" class="form-control" name="userPsw" id="userPsw" placeholder="请输入密码">
                    </div>
                    <div class="center-block text-center">
                        <button type="submit" class="btn btn-default" id="loginSub">提交</button>
                    </div>
                </form>
            </div>
            <div class="tab-pane fade in" id="register">
                <h3 class="center-block text-center">用户注册</h3>
                <form role="form" onsubmit="return false" id="registerForm" method="post" >
                    <div class="form-group">
                        <label for="userPhone2">手机号</label>&nbsp;&nbsp;&nbsp;<span id="phoneEnabled"></span>
                        <input type="text" class="form-control" name="userPsw" id="userPhone2" placeholder="请输入手机号">
                    </div>
                    <div class="form-group">
                        <label for="userPsw2">密码</label>
                        <input type="password" class="form-control" name="userPsw" id="userPsw2" placeholder="请输入密码">
                    </div>
                    <div class="form-group">
                        <label for="userPsw3">再次输入密码</label>&nbsp;&nbsp;&nbsp;<span id="pswEnabled"></span>
                        <input type="password" class="form-control" name="userPsw" id="userPsw3" placeholder="请输入密码">
                    </div>
                    <div class="form-group">
                        <label for="userAds">收货地址</label>
                        <textarea class="form-control" name="userAds" id="userAds" rows="4" cols="60"></textarea>
                    </div>
                    <div class="center-block text-center">
                        <button type="submit" class="btn btn-default" id="registerSub">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/resource/js/nav.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {

        //第一个标签页显示
        $('#myTab li:eq(0) a').tab('show');

        //是否登录
        var userInfo = ${user}

        console.log(userInfo)
        var flag = false;
        if (userInfo != null){
            flag = true;
        }
        nav.isSession.judge({
            flag : flag,
            userPhone : userInfo
        })
    });
    //登录
    $(function () {
        $("#loginSub").click(function () {
            var userPhone = $("#userPhone").val();
            var userPsw = $("#userPsw").val();
            $.ajax({
                type : "GET",
                dataType : "json",
                url : "${pageContext.request.contextPath}/user/"+userPhone+"/"+userPsw+"/login",
                success : function (data) {
                    if (data.success){
                        alert(data.stateInfo)
                        window.location.href = "${pageContext.request.contextPath}/seckill/1/list"
                    }else
                        alert(data.stateInfo)
                },
                error : function () {
                    alert("操作异常")
                }
            })
        })
    })

    //注册
    $(function () {
        $("#registerSub").click(function () {
            var userPhone = $("#userPhone2").val();
            var userPsw = $("#userPsw2").val();
            var userAds = $("#userAds").val();
            $.ajax({
                type : "POST",
                dataType : "json",
                data : {
                    "userPhone":userPhone,
                    "userPsw":userPsw,
                    "userAds":userAds,
                        },
                url : "${pageContext.request.contextPath}/user/register",
                success : function (data) {
                    if (data.success){
                        alert(data.stateInfo)
                        window.location.href = "${pageContext.request.contextPath}/user/toLoginOrRegister"
                    }else
                        alert(data.stateInfo)
                },
                error : function () {
                    alert("操作异常")
                }
            })
        })
    })
</script>
</html>
