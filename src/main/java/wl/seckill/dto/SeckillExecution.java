package wl.seckill.dto;

import wl.seckill.entity.SuccessKilled;
import wl.seckill.enums.SeckillSatteEnum;

/**
 * 封装秒杀执行结果
 */
public class SeckillExecution {

    //秒杀id
    private long seckillId;

    //秒杀结果状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillSatteEnum satteEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = satteEnum.getState();
        this.stateInfo = satteEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillSatteEnum satteEnum) {
        this.seckillId = seckillId;
        this.state = satteEnum.getState();
        this.stateInfo = satteEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
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

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
