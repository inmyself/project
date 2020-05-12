package wl.seckill.dto;

import wl.seckill.enums.OrderStateEnum;
import wl.seckill.enums.UserStateEnum;

/**
 * create by wule on 2020/5/4
 * 登录或注册返回信息
 */
public class ResultInfo {

    private boolean success;

    private int state;

    private String stateInfo;

    public ResultInfo(boolean success, UserStateEnum userstate){
        this.success = success;
        this.state = userstate.getState();
        this.stateInfo = userstate.getStateInfo();
    }

    public ResultInfo(boolean success, OrderStateEnum orderState){
        this.success = success;
        this.state = orderState.getState();
        this.stateInfo = orderState.getStateInfo();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
