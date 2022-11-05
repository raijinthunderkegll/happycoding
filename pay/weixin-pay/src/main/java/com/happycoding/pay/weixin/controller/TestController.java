package com.happycoding.pay.weixin.controller;

import com.happycoding.pay.weixin.config.WxPayConfig;
import com.happycoding.pay.weixin.service.NativePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    WxPayConfig wxPayConfig;

//    @Autowired
//    Config config;

    @Autowired
    NativePayService nativePayService;

    @RequestMapping
    public void test() {
        System.out.println("app_id: " + wxPayConfig.getAppid());
    }

    @RequestMapping("prepay")
    public void prepay() throws IOException {
        nativePayService.prePay();

//        JsapiService service = new JsapiService.Builder().config(config).build();
//        PrepayRequest request = new PrepayRequest();
//        Amount amount = new Amount();
//        amount.setTotal(1);
//        amount.setCurrency("CNY");
//        request.setAmount(amount);
//        request.setAppid(wxPayConfig.getAppid());
//        request.setMchid(wxPayConfig.getMchId());
//        request.setDescription("测试商品标题");
//        request.setNotifyUrl("https://notify_url");
//        request.setOutTradeNo("out_trade_no_001");
//
//        Payer payer = new Payer();
//        /**
//         * 用户openId
//         *
//         * 用户在公众号内的身份标识，不同公众号拥有不同的openid。
//         * 商户后台系统通过登录授权、支付通知、查询订单等API可获取到用户的openid。
//         * 主要用途是判断同一个用户，对用户发送客服消息、模板消息等。
//         * 企业号用户需要使用企业号userid转openid接口将企业成员的userid转换成openid。
//         */
//        payer.setOpenid("oLTPCuN5a-nBD4rAL_fa********");
//        request.setPayer(payer);
//
//        PrepayResponse response = service.prepay(request);
//        System.out.println(response.getPrepayId());
    }
}
