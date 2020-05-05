package wl.seckill.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wl.seckill.dto.UserInfo;
import wl.seckill.entity.User;
import wl.seckill.enums.UserStateEnum;
import wl.seckill.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * create by wule on 2020/5/4
 * 用户登录
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/toLoginOrRegister")
    public String toLoginOrRegister(){
        return "user";
    }

    /**
     * 检查用户手机是否已经注册
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/{userPhone}/check", method = RequestMethod.GET)
    @ResponseBody
    public UserInfo checkPhone(@PathVariable("userPhone") Long userPhone){
        try {
            if (userPhone != null){
                return userService.judgePhone(userPhone);
            }else
                return new UserInfo(false, UserStateEnum.PHONE_ERROE);
        }catch (Exception e){
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public UserInfo register(User user){
        try {
            return userService.userRegister(user);
        }catch (Exception e){
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }
    }

    @RequestMapping(value = "/{userPhone}/{userPsw}/login")
    @ResponseBody
    public UserInfo login(@PathVariable("userPhone") Long userPhone, @PathVariable("userPsw") String userPsw,
                            HttpSession session){
        try {
            UserInfo userInfo = userService.userLogin(userPhone, userPsw);
            if (userInfo.isSuccess())//存入session
                session.setAttribute("user", userPhone);
            return userInfo;
        }catch (Exception e){
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> logout(HttpSession session){
        session.removeAttribute("user");
        Map<String, String> map = new HashMap<>();
        map.put("out", "退出成功");
        return map;
    }
}
