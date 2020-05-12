package wl.seckill.enums;

public enum OrderStateEnum {

    DELETE_SUCCESS(0, "删除成功！"),
    DELETE_ERROR(1, "删除错误，请重试")
    ;

    private int state;
    private String stateInfo;

    OrderStateEnum(int state, String stateInfo) {
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
