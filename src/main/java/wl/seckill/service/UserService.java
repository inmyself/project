package wl.seckill.service;

import wl.seckill.dto.UserInfo;
import wl.seckill.entity.User;

public interface UserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    UserInfo userRegister(User user);

    /**
     * 注册电话校验
     * @param userPhone
     * @return
     */
    UserInfo judgePhone(long userPhone);

    /**
     * 用户登录
     * @param userPhone
     * @param userPsw
     * @return
     */
    UserInfo userLogin(long userPhone, String userPsw);
}
