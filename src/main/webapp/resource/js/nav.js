var nav = {
    URL : {
      toOrder : function (userPhone, page) {
          return "order/" + userPhone + "/" + page + "/list"
      },
        toLoginOrRegister : function () {
            return "user/toLoginOrRegister";
        }
    },
    //session是否有值
    isSession : {
        judge : function (params) {
            var userPhone = params['userPhone']
            var userInfo = $("#userInfo");
            userInfo.hide();
            if (userPhone){//如果登录显示手机号
                userInfo.html("<ul class='nav navbar-nav navbar-right' id='userInfo'>" +
                    "<li class='dropdown'><a class='dropdown-toggle'  data-toggle='dropdown' href='#'><span class='glyphicon glyphicon-user'>"+userPhone+"</span></a>" +
                    "<b class='caret'></b>" +
                    "<ul class='dropdown-menu'>" +
                    "<li><a id='logout'>退出登录</a> </li>" +
                    "</ul> " +
                    "</li>" +
                    "</ul>");
                $("#logout").one("click", function () {//退出登录，还原页面
                    $.get(user.URL.logout(), {}, function (result) {
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
            /*nav.phoneCheck();
            nav.pswCheck();*/
        },

        //去订单页面
        toOrder : function (params) {
            var userPhone = params['userPhone'];
            var page = params['page'];
            if (userPhone){
                window.location.href = nav.URL.toOrder(userPhone, page);
            }else {
                alert("请先登录");
                window.location.href = nav.URL.toLoginOrRegister();
            }
        }
    }
}