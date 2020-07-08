package com.wxpay.weixin;


import com.wxpay.entity.HttpClient;

import java.io.IOException;

/**
 * @author: Joe
 * @created: 2020/7/8 10:19
 */
public class HttpClientTest {




    public static void Test() throws IOException {

        //1.设置url
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        HttpClient httpClient = new HttpClient(url);

        //2.设置请求参数 xml形式的
        String xml = "<xml><money>9.8</money><sign>962D95090667CE8FF8D9BBCD32223FD9</sign><id>No.001</id><title>华佰建筑资源</title></xml>";
        httpClient.setXmlParam(xml);

        //3. 设置发生什么请求 http or https
        httpClient.setHttps(true);

        //4.发送请求，xml形式的请求都是post
        httpClient.post();
        String result = httpClient.getContent();
        System.out.println("结果"+'\n'+result);
    }
    public static void main(String[] args) throws IOException {
        Test();


    }
}
