package com.wxpay.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.wxpay.entity.Result;
import com.wxpay.service.WeixinPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
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
    //支付回调
    @RequestMapping("/notify/url")
    public String notifyurl(HttpServletRequest request) throws Exception{

        ServletInputStream is = request.getInputStream();
        //创建一个接受数据的对象
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //缓冲区
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len=is.read(buffer))!=-1){
            baos.write(buffer,0,len);
        }
        byte[] bytes = baos.toByteArray();
        String xmlresult = new String(bytes,"UTf-8");

        Map<String,String> resultMap = WXPayUtil.xmlToMap(xmlresult);
//        System.out.println("1");
//        System.out.println(resultMap);



        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        return result;
    }


    @GetMapping(value = "/status")
    public Result queryStatus(String outtradeno){

        //查询支付状态
        Map map = weixinPayService.queryStatus(outtradeno);
        Result m = new Result();
        m = Result.succ(map,"查询订单状态！");
        return m;
    }

    @RequestMapping("/index")
    public String index(){
        return "redirect:index";
    }

}

