package wl.seckill.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wl.seckill.dao.UserDao;
import wl.seckill.dto.UserInfo;
import wl.seckill.entity.User;
import wl.seckill.enums.UserStateEnum;
import wl.seckill.service.UserService;

/**
 * create by wule on 2020/5/4
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Override
    public UserInfo userRegister(User user) {
        try {
            int i = userDao.insertUser(user);
            if (i > 0)
                return new UserInfo(true, UserStateEnum.REGISTER_SUCCESS);
            else
                return new UserInfo(false, UserStateEnum.REGISTER_REPEAT);
        }catch (Exception e){
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }
    }

    @Override
    public UserInfo judgePhone(long userPhone) {
        User user = userDao.queryByPhone(userPhone);
        try {
            if (user != null){
                return new UserInfo(false, UserStateEnum.REGISTER_REPEAT);
            }else {
                return new UserInfo(true, UserStateEnum.REGISTER_ENABLED);
            }
        }catch (Exception e){
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }
    }

    @Override
    public UserInfo userLogin(long userPhone, String userPsw) {
        User user = userDao.queryByPhone(userPhone);
        try {
            if (user == null)
                return new UserInfo(false, UserStateEnum.LOGIN_DISABLED);
            else {
                if (user.getUserPsw().equals(userPsw))
                    return new UserInfo(true, UserStateEnum.LOGIN_SUCCESS);
                else
                    return new UserInfo(false, UserStateEnum.LOGIN_ERROR);
            }
        }catch (Exception e){
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }
    }
}
