package wl.seckill.service;

import wl.seckill.dto.ResultInfo;
import wl.seckill.entity.User;

import javax.servlet.http.HttpSession;

public interface UserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    ResultInfo userRegister(User user);

    /**
     * 注册电话校验
     * @param userPhone
     * @return
     */
    ResultInfo judgePhone(long userPhone);

    /**
     * 用户登录
     * @param userPhone
     * @param userPsw
     * @return
     */
    ResultInfo userLogin(long userPhone, String userPsw, HttpSession session);
}
