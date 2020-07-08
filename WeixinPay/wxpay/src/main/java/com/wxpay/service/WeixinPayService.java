package com.wxpay.service;

import java.util.Map;

/**
 * @author: Joe
 * @created: 2020/7/8 10:58
 */

public interface WeixinPayService {

    //创建二维码操作
    Map createNative(Map<String,String> paramaterMap);

}
