package wl.seckill.entity;

/**
 * create by wule on 2020/5/4
 */
public class User {
    private long userId;

    private long userPhone;
    //密码
    private String userPsw;
    //地址
    private String userAds;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getUserAds() {
        return userAds;
    }

    public void setUserAds(String userAds) {
        this.userAds = userAds;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userPhone=" + userPhone +
                ", userPsw='" + userPsw + '\'' +
                ", userAds='" + userAds + '\'' +
                '}';
    }
}
