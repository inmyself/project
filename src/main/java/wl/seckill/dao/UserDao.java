package wl.seckill.dao;

import wl.seckill.entity.User;

public interface UserDao {

    /**
     * 根据电话查询用户
     * @param userPhone
     * @return
     */
    User queryByPhone(long userPhone);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int insertUser(User user);
}
