// javaScript模块化
var seckill = {
    //封装秒杀相关ajax的URL
    URL : {
        now : function () {
            return "seckill/time/now";
        },
        exposer : function (seckillId) {
            return "seckill/" + seckillId + "/exposer";

        },
        execution : function (seckillId, md5) {
            return "seckill/" + seckillId + "/" + md5 + "/execution";
        }
    },
    //验证手机号
    validatePhone : function(phone){
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },

    //处理秒杀逻辑
    handlerSeckill : function (seckillId, seckillBox) {
        //获取秒杀地址，控制显示逻辑，执行秒杀
        seckillBox.hide().html("<button class='btn btn-primary btn-lg' id='killBtn'>开始秒杀</button>");
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            if (result && result['success']){
                var exposer = result['data'];
                if (exposer['exposed']){
                   //开启秒杀
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killUrl:" + killUrl);
                    $("#killBtn").one("click", function () {
                      //执行秒杀请求
                      $(this).addClass("disabled");//禁用按钮
                      $.post(killUrl, {}, function (result) {//发送请求
                          if (result && result['success']){
                              var killResult = result['data'];
                              var state = killResult['state'];
                              var stateInfo = killResult['stateInfo'];
                              var successKilled = killResult['successKillId'];
                              seckillBox.html("<span class='label label-success'>"+ stateInfo +"</span>");
                          }
                      });
                    });
                    seckillBox.show();
                }else {
                    //未开启秒杀:用户计算机时间和服务器时间存在偏差
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countDown(seckillId, start, now, end);
                }
            }
        })
    },
    //计时判断
    countDown : function(seckillId, startTime, nowTime, endTime){
        var seckillBox = $("#seckill-box");
        if (nowTime > endTime){
            //秒杀结束
            seckillBox.html("秒杀结束!");
        }else if (nowTime < startTime){
            //秒杀未开始，计时绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown( killTime, function (event) {
                //事件格式
                var format = event.strftime("秒杀倒计时： %D天 %H时 %M分 %S秒");
                seckillBox.html(format);
            }).on('finish.countdown', function () {//计时结束
                //获取秒杀地址，控制秒杀逻辑
                seckill.handlerSeckill(seckillId, seckillBox);
            });
        } else {
            //秒杀已开始
            seckill.handlerSeckill(seckillId, seckillBox);
        }
    },
    //详情的秒杀逻辑
    detail : {
        //详情页初始化
        init : function (params) {
            //手机验证和登录、计时交互
            //在cookie中查找用户
            var userPhone = params['userPhone'];
            //console.log("userPhone:"+ userPhone);
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            //验证手机
            if (!seckill.validatePhone(userPhone)) {
                /*//绑定phone
                //控制输出
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show: true ,//显示弹出层
                backdrop : 'static',//禁止位置关闭
                keyboard : false//关闭键盘事件
                });
                $("#killPhoneBtn").click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    if (seckill.validatePhone(inputPhone)){
                        //电话写入cookie
                        $.cookie("userPhone", inputPhone, {expires : 7, path : "/seckill"});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html("<label class='label label-danger'>手机号错误</label>").show(300);
                    }
                });*/
                alert("请先登录")
                window.location.href = "user/toLoginOrRegister"
            }
            //已经登录
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']){
                    seckill.countDown(seckillId, startTime, new Date().getTime(), endTime);
                }
            })
        }
    }
}