package com.maxzuo.controller;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSONObject;
import com.maxzuo.dubbo.common.api.IScUserService;
import com.maxzuo.dubbo.common.api.IShopOrderInfoService;
import com.maxzuo.dubbo.common.model.ScUser;
import com.maxzuo.dubbo.common.model.ShopOrderInfo;
import com.maxzuo.form.Param;
import com.maxzuo.util.RedisUtils;
import com.maxzuo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 店铺Rest
 * Created by zfh on 2019/01/19
 */
@RestController
@RequestMapping("/ws/shop")
public class ShopRest {

    private static final Logger logger = LoggerFactory.getLogger(ShopRest.class);

    @Autowired
    private IScUserService scUserService;

    @Autowired
    private IShopOrderInfoService shopOrderInfoService;

    /**
     * APM-示例查询接口
     * @param param {@link Param}
     * @return {@link Result}
     */
    @PostMapping("findTradingGiftList")
    public Result findTradingGiftList(@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE, "系统繁忙！");
        JSONObject jsonObject = JSONObject.parseObject(param.getData().toString());
        Integer orderId = jsonObject.getInteger("orderId");
        try {
            // 1.查询dubbo服务
            ShopOrderInfo orderInfo = shopOrderInfoService.getShopOrderInfoByPrimaryKey(orderId);
            // 2.查询dubbo服务
            ScUser userInfo = scUserService.queryUserByPrimaryKey(orderInfo.getUserId());
            // 3.写入Redis
            RedisUtils.setStr("userInfo", JSONObject.toJSONString(userInfo));

            // 4.组装Response
            Map<String, String> data = new HashMap<>(10);
            data.put("userId", userInfo.getId().toString());
            data.put("username", userInfo.getUsername());
            data.put("orderNo", orderInfo.getOrderNo());
            result.setData(data);
            result.setCode(Result.RESULT_SUCCESS);
            result.setMsg("查询成功！");
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        return result;
    }

    /**
     * 限流规则接口
     */
    @PostMapping("/flow")
    public Result flow () {
        logger.info("flowRules: {}", JSONObject.toJSONString(FlowRuleManager.getRules()));
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("Welcome to flow method !");
        return new Result(Result.RESULT_SUCCESS, "ok");
    }

    /**
     * 降级规则接口
     */
    @PostMapping("/degrade")
    public Result degrade () {
        try {
            Thread.sleep(520);
            System.err.println("Welcome to degrade method !");
        } catch (InterruptedException e) {
            logger.info("接口超时异常！");
        }
        return new Result(Result.RESULT_SUCCESS, "ok");
    }
}
