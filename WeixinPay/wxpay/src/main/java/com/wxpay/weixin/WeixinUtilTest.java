package com.wxpay.weixin;
import com.github.wxpay.sdk.WXPayUtil;


import java.util.HashMap;
import java.util.Map;

/**
 * @author: Joe
 * @created: 2020/7/8 9:38
 */
public class WeixinUtilTest {

    public static void Testdao() throws Exception {
        //随机字符串
        String str = WXPayUtil.generateNonceStr();
        System.out.println(str);

        //map转成xml字符串
        Map<String,String> dataMap = new HashMap<String, String>();
        dataMap.put("id","No.001");
        dataMap.put("title","华佰建筑资源");
        dataMap.put("money","9.8");
        String xmstr = WXPayUtil.mapToXml(dataMap);
        System.out.println("转成xmlstr"+'\n'+xmstr);

        //将Map转成xml字符串并且有签名
        String signXmlstr = WXPayUtil.generateSignedXml(dataMap,"itcast");
        System.out.println("转成xmlstr带有签名的"+'\n'+signXmlstr);

        //将xmlstr转成map
        Map<String,String> map= WXPayUtil.xmlToMap(signXmlstr);
        System.out.println("xmlstr带有签名的转成map"+'\n'+map);

    }

    public static void main(String[] args) throws Exception {

        Testdao();

    }
}
