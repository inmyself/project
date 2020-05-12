package wl.seckill.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import wl.seckill.dao.UserDao;
import wl.seckill.dto.ResultInfo;
import wl.seckill.entity.User;
import wl.seckill.enums.UserStateEnum;
import wl.seckill.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * create by wule on 2020/5/4
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ResultInfo userRegister(User user) {
        try {
            int i = userDao.insertUser(user);
            if (i > 0)
                return new ResultInfo(true, UserStateEnum.REGISTER_SUCCESS);
            else
                return new ResultInfo(false, UserStateEnum.REGISTER_REPEAT);
        }catch (Exception e){
            logger.error("user error : " + e.getMessage(), e);
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }
    }

    @Override
    public ResultInfo judgePhone(long userPhone) {
        User user = userDao.queryByPhone(userPhone);
        try {
            if (user != null){
                return new ResultInfo(false, UserStateEnum.REGISTER_REPEAT);
            }else {
                return new ResultInfo(true, UserStateEnum.REGISTER_ENABLED);
            }
        }catch (Exception e){
            logger.error("user error : " + e.getMessage(), e);
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }
    }

    @Override
    public ResultInfo userLogin(long userPhone, String userPsw, HttpSession session) {
        User user = userDao.queryByPhone(userPhone);
        try {
            if (user == null)
                return new ResultInfo(false, UserStateEnum.LOGIN_DISABLED);
            else {
                if (user.getUserPsw().equals(userPsw)) {
                    session.setAttribute("user", user);
                    return new ResultInfo(true, UserStateEnum.LOGIN_SUCCESS);
                }
                else
                    return new ResultInfo(false, UserStateEnum.LOGIN_ERROR);
            }
        }catch (Exception e){
            logger.error("user error : " + e.getMessage(), e);
            throw new RuntimeException(UserStateEnum.INNER_ERROR.getStateInfo(), e);
        }
    }
}
