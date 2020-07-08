package com.wxpay.controller;

import com.wxpay.entity.Result;
import com.wxpay.service.WeixinPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @author: Joe
 * @created: 2020/7/8 14:47
 */
@RestController
@RequestMapping("/weixin/pay")
public class WeixinPayController {

    @Autowired
    WeixinPayService weixinPayService;

    /**
     * 创建二维码连接地址返回给前端 生成二维码图片
     *
     * @param parameters  包含 订单号  包含 金额  包含 queue队列名称 交换机信息 路由信息 用户名
     * @return
     */
    @RequestMapping("/create/native")
    public Result createNative(@RequestParam Map<String,String> parameters) {
        //获取用户名
        Map<String,String> resultMap = weixinPayService.createNative(parameters);
        Result m = new Result();


        m = Result.succ(resultMap);
        return m;
    }

}

