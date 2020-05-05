var nav = {
    URL : {
        loginout : function () {
            return "user/logout"
        },
        /*toLoginOrRegister : function () {
            return "user/toLoginOrRegister"
        }*/
        checkPhone : function (phone) {
            return "user/"+phone+"/check"
        }
    },
    isSession : {
        judge : function (params) {
            var flag = params['flag'];
            var userPhone = params['userPhone']
            var userInfo = $("#userInfo");
            userInfo.hide();
            if (flag){//如果登录显示手机号
                userInfo.html("<ul class='nav navbar-nav navbar-right' id='userInfo'>" +
                    "<li class='dropdown'><a class='dropdown-toggle'  data-toggle='dropdown' href='#'><span class='glyphicon glyphicon-user'>"+userPhone+"</span></a>" +
                    "<b class='caret'></b>" +
                    "<ul class='dropdown-menu'>" +
                    "<li><a id='logout'>退出登录</a> </li>" +
                    "</ul> " +
                    "</li>" +
                    "</ul>");
                $("#logout").one("click", function () {//退出登录，还原页面
                    $.get(nav.URL.loginout(), {}, function (result) {
                        if(result){
                            /*userInfo.html("<ul class='nav navbar-nav navbar-right' id='userInfo'>" +
                                "<li><a href='"+nav.URL.toLoginOrRegister()+"'><span class='glyphicon glyphicon-user'>注册</span></a>" +
                                "<li><a href='"+nav.URL.toLoginOrRegister()+"'><span class='glyphicon glyphicon-user'>登录</span></a>" +
                                "</ul>");*/
                            window.location.reload();
                        }
                    })

                });
            }
            userInfo.show();
            nav.phoneCheck();
            nav.pswCheck();
        }
    },
    //注册手机号校验
    phoneCheck : function () {
        var phoneEnabled = $("#phoneEnabled");
        var userPhone2 = $("#userPhone2");
        userPhone2.blur(function () {
            if (userPhone2.val().length != 11){
                phoneEnabled.html("<span id='phoneEnabled' style='color: red'>手机号错误</span>")
            }else {
                $.get(nav.URL.checkPhone(userPhone2.val()), {}, function (result) {
                    if (result && result['success']){
                        phoneEnabled.html("<span id='phoneEnabled' style='color: green'>"+result['stateInfo']+"</span>")
                    }else {
                        phoneEnabled.html("<span id='phoneEnabled' style='color: red'>"+result['stateInfo']+"</span>")
                    }
                })
            }
        })
    },
    //注册密码校验
    pswCheck : function () {
        var userPsw2 = $("#userPsw2");
        var userPsw3 = $("#userPsw3");
        var pswEnabled = $("#pswEnabled")
        userPsw3.blur(function () {
            console.log("a"+userPsw2.val()+"a")
            if (userPsw2.val() != "" || userPsw3.val() != ""){
                if (userPsw3.val() != userPsw2.val())
                    pswEnabled.html("<span id='pswEnabled' style='color: red'>两次输入密码不一致</span>")
                else
                    pswEnabled.html("<span id='pswEnabled' style='color: green'>密码可用</span>")
            }else {
                pswEnabled.html("<span id='pswEnabled' style='color: red'>请输入密码</span>")
            }
        });
    }
}