package com.wxpay.service.impl;
import com.github.wxpay.sdk.WXPayUtil;
import com.wxpay.entity.HttpClient;
import com.wxpay.service.WeixinPayService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Joe
 * @created: 2020/7/8 10:56
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Override
    public Map createNative(Map<String, String> paramaterMap)  {

        //参数NATIVE
        Map<String,String> map = new HashMap<String, String>();

        map.put("appid","wx8397f8696b538317");
        map.put("mch_id","1473426802");
        //map.put("sub_mch_id","1473426802");
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body","华佰建筑信息资源");
        map.put("out_trade_no",paramaterMap.get("outtradeno"));
        map.put("total_fee",paramaterMap.get("totalfee"));
        map.put("spbill_create_ip","123.12.12.123");
        map.put("notify_url","http://www.linwar.top");
        map.put("trade_type","NATIVE");
        String partnerkey="T6m9iK73b0kn9g5v426MKfHQH7X8rKwb";

        String xmlparameters = null;
        try {
            xmlparameters = WXPayUtil.generateSignedXml(map,partnerkey);

        //url
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        HttpClient httpClient= new HttpClient(url);
        //请求方式
         httpClient.setHttps(true);
        //提交参数
         httpClient.setXmlParam(xmlparameters);
         //提交请求
         httpClient.post();
        //返回值
         String result = httpClient.getContent();
         System.out.println(result);
        //返回数据转成map
        Map<String,String> resultMap = WXPayUtil.xmlToMap(result);

        return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
