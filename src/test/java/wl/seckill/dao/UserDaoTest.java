package wl.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wl.seckill.entity.User;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    public void queryByPhone() {
        long phone = 555555555555l;
        User user = userDao.queryByPhone(phone);
        if (user == null)
            System.out.println("未查询到此人信息");
        else
            System.out.println(user);
    }

    @Test
    public void insertUser() {
        long phone = 555555555555l;
        String psw = "1234";
        String ads = "郑州市金水区";
        User user = new User();
        user.setUserPhone(phone);
        user.setUserPsw(psw);
        user.setUserAds(ads);
        int i = userDao.insertUser(user);
        if (i > 0){
            System.out.println("插入成功");
        }else {
            System.out.println("插入失败");
        }
    }
}