package wl.seckill.dto;

import java.util.List;

/**
 * create by wule on 2020/5/5
 * 列表显示
 */
public class SeckillList<T> {

    private int page;//当前页数

    private List<T> seckillList;//显示内容

    public SeckillList(int page, List<T> seckillList) {
        this.page = page;
        this.seckillList = seckillList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getSeckillList() {
        return seckillList;
    }

    public void setSeckillList(List<T> seckillList) {
        this.seckillList = seckillList;
    }
}
