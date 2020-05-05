package wl.seckill.enums;

public enum UserStateEnum {
    LOGIN_SUCCESS(0, "登录成功！"),
    LOGIN_ERROR(1, "用户名或密码错误"),
    REGISTER_SUCCESS(2, "注册成功！"),
    REGISTER_REPEAT(3, "该手机号已注册"),
    REGISTER_ENABLED(4, "手机号可用"),
    LOGIN_DISABLED(5, "该用户尚未注册"),
    PHONE_ERROE(6, "请输入手机号"),
    PSW_ERROE(7, "请输入密码"),
    INNER_ERROR(8, "发生错误，请重试！")
    ;

    private int state;
    private String stateInfo;

    UserStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
