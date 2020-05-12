var user = {
    URL : {
        logout : function () {
            return "user/logout"
        },
        /*toLoginOrRegister : function () {
            return "user/toLoginOrRegister"
        }*/
        checkPhone : function (phone) {
            return "user/"+phone+"/check"
        },
        register : function () {
            return "user/register"
        },
        toLoginOrRegister : function () {
            return "user/toLoginOrRegister";
        },
        login : function (userPhone, userPsw) {
            return "user/"+userPhone+"/"+userPsw+"/login"
        },
        list : function () {
            return "seckill/1/list"
        }
    },

    REGISTER : {
        //注册手机号校验
        phoneCheck : function () {
            var phoneEnabled = $("#phoneEnabled");
            var userPhone2 = $("#userPhone2");
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
        },
        //注册密码校验
        pswCheck : function () {
            var userPsw2 = $("#userPsw2");
            var userPsw3 = $("#userPsw3");
            var pswEnabled = $("#pswEnabled")
            console.log("a"+userPsw2.val()+"a")
            if (userPsw2.val() != "" || userPsw3.val() != ""){
                if (userPsw3.val() != userPsw2.val())
                    pswEnabled.html("<span id='pswEnabled' style='color: red'>两次输入密码不一致</span>")
                else
                    pswEnabled.html("<span id='pswEnabled' style='color: green'>密码可用</span>")
            }else {
                pswEnabled.html("<span id='pswEnabled' style='color: red'>请输入密码</span>")
            }
        },
        //注册
        register : function () {
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
                url : user.URL.register(),
                success : function (data) {
                    if (data.success){
                        alert(data.stateInfo)
                        window.location.href = user.URL.toLoginOrRegister()
                    }else
                        alert(data.stateInfo)
                },
                error : function () {
                    alert("操作异常")
                }
            })
        },
    },

    LOGIN : {
        //登录
        login : function () {
            var userPhone = $("#userPhone").val();
            var userPsw = $("#userPsw").val();
            $.ajax({
                type : "GET",
                dataType : "json",
                url : user.URL.login(userPhone, userPsw),
                success : function (data) {
                    if (data.success){
                        alert(data.stateInfo)
                        window.location.href = user.URL.list()
                    }else
                        alert(data.stateInfo)
                },
                error : function () {
                    alert("操作异常")
                }
            })
        }
    }
}