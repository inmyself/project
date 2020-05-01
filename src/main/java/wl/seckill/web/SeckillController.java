package wl.seckill.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wl.seckill.dto.Exposer;
import wl.seckill.dto.SeckillExecution;
import wl.seckill.dto.SeckillResult;
import wl.seckill.entity.Seckill;
import wl.seckill.enums.SeckillSatteEnum;
import wl.seckill.exception.RepeatKillException;
import wl.seckill.exception.SeckillClosedException;
import wl.seckill.service.SeckillService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/seckill")  // url: /模块/资源/{id}/细分
public class SeckillController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    //展示列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

    //商品详情
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if (seckillId == null)
            return "redirect:/seckill/list";//返回列表
        Seckill seckill = seckillService.getSeckillById(seckillId);
        if (seckill == null)
            return "forward:/seckill/list";
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //输出秒杀接口 ajax
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST,
    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            result = new SeckillResult<>(false, e.getMessage());
        }
        return result;

    }

    //执行秒杀
    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @CookieValue(value = "userPhone", required = false) Long userPhone,
                                                   @PathVariable("md5") String md5){
        //cookie没值，未注册
        if (userPhone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        SeckillExecution seckillExecution;
        try {//成功
            seckillExecution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
            result = new SeckillResult<>(true, seckillExecution);
            return result;
        } catch (RepeatKillException e) {//重复秒杀
            seckillExecution = new SeckillExecution(seckillId, SeckillSatteEnum.REPEAT_ERROR);
            result = new SeckillResult<>(true, seckillExecution);
            return result;
        } catch (SeckillClosedException e) {//秒杀关闭
            seckillExecution = new SeckillExecution(seckillId, SeckillSatteEnum.END);
            result = new SeckillResult<>(true, seckillExecution);
            return result;
        } catch (Exception e) {//其他错误
            seckillExecution = new SeckillExecution(seckillId, SeckillSatteEnum.INNER_ERROR);
            result = new SeckillResult<>(true, seckillExecution);
            return result;
        }
    }

    //服务器当前时间
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date date = new Date();
        return  new SeckillResult<Long>(true, date.getTime());
    }
}
