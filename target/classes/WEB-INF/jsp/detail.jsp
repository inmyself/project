<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="commeon/head.jsp"%>
    <%@include file="commeon/tag.jsp"%>
    <base href="<%= basePath%>">
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
    <div class="panel panel-default text-center">
        <div class="pannel-heading">
            <h1>${seckill.name}</h1>
        </div>

        <div class="panel-body">
            <h2 class="text-danger">
                <%--显示time图标--%>
                <span class="glyphicon glyphicon-time"></span>
                <%--展示倒计时--%>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>
<%--登录弹出层 输入电话--%>
<div id="killPhoneModal" class="modal fade">

    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone">秒杀电话: </span>
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="填写手机号^o^" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <%--验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"> </span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>

        </div>
    </div>

</div>

</body>

<!-- 引入jQuery的cookie -->
<script src="https://cdn.bootcdn.net/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- jQuery的计数-->
<script src="https://cdn.bootcdn.net/ajax/libs/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>

<script src="${pageContext.request.contextPath}/resource/js/seckill.js"></script>
<script type="text/javascript">
    $(function () {
        seckill.detail.init({
            seckillId : ${seckill.seckillId},
            startTime : ${seckill.startTime.time},
            endTime : ${seckill.endTime.time},
            userPhone : ${user}+"",
        });
    })
</script>
</html>