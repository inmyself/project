package wl.seckill.enums;

/**
 * 使用枚举表示秒杀结果字段
 */
public enum SeckillSatteEnum {

    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_ERROR(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据篡改");

    private int state;
    private String stateInfo;

    SeckillSatteEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillSatteEnum stateOf(int index){
        for (SeckillSatteEnum state : values()) {
            if (state.getState() == index)
                return state;
        }
        return null;
    }
}
