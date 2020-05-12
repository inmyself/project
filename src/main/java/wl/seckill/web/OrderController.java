package wl.seckill.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wl.seckill.dto.ResultInfo;
import wl.seckill.dto.SeckillList;
import wl.seckill.entity.SuccessKilled;
import wl.seckill.enums.OrderStateEnum;
import wl.seckill.service.OrderService;

/**
 * create by wule on 2020/5/11
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    //查询用户订单
    @RequestMapping(value = "/{userPhone}/{page}/list", method = RequestMethod.GET)
    public String successKilledList(@PathVariable("userPhone") Long userPhone, @PathVariable("page") Integer page, Model model){
        if (userPhone == null || page == null)
            return null;
        else {
            SeckillList<SuccessKilled> list = orderService.queryOrder(userPhone, page - 1);
            model.addAttribute("list", list);
            return "order";
        }
    }

    //删除订单
    @RequestMapping(value = "/{userPhone}/{seckillId}/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo deleteOrder(@PathVariable("userPhone") Long userPhone, @PathVariable("seckillId") Long seckillId){
        try {
            return orderService.deleteOrder(userPhone, seckillId);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RuntimeException(OrderStateEnum.DELETE_ERROR.getStateInfo(), e);
        }
    }
}
