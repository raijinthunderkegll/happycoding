package com.happycoding.pay.weixin.service.impl;

import com.happycoding.pay.weixin.config.WxPayConfig;
import com.happycoding.pay.weixin.enums.wxpay.WxApiType;
import com.happycoding.pay.weixin.enums.wxpay.WxNotifyType;
import com.happycoding.pay.weixin.service.NativePayService;
import com.wechat.pay.java.core.util.GsonUtil;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NativePayServiceImpl implements NativePayService {

    @Autowired
    CloseableHttpClient httpClient;

    @Autowired
    WxPayConfig wxPayConfig;

    @Override
    public void prePay() throws IOException {
        String url = wxPayConfig.getDomain() + WxApiType.NATIVE_PAY.getType();
        HttpPost httpPost = new HttpPost(url);
        Map<String, Object> params = new HashMap<>();
        params.put("appid", wxPayConfig.getAppid());
        params.put("mchid", wxPayConfig.getMchId());
        params.put("description", "description");
        params.put("out_trade_no", "trade" + new Date().getTime());
        params.put("notify_url", wxPayConfig.getNotifyDomain() + WxNotifyType.NATIVE_NOTIFY);
        Amount amount = new Amount();
        amount.setTotal(1);// 分
        amount.setCurrency("CNY");
        params.put("amount", amount);
        String json = GsonUtil.toJson(params);
        StringEntity entity = new StringEntity(json, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        log.info("httpPost: " + httpPost);
        log.info(json);
        log.info(entity.toString());
        CloseableHttpResponse response = httpClient.execute(httpPost);

        log.info("repsonse: " + response.getEntity());

        try {
            String bodyAsString = EntityUtils.toString(response.getEntity());//响应体
            int statusCode = response.getStatusLine().getStatusCode();//响应状态码
            if (statusCode == 200) { //处理成功
                log.info("成功, 返回结果 = " + bodyAsString);
            } else if (statusCode == 204) { //处理成功，无返回Body
                log.info("成功");
            } else {
                log.info("Native下单失败,响应码 = " + statusCode+ ",返回结果 = " + bodyAsString);
                throw new IOException("request failed");
            }

            //响应结果
//            Map<String, String> resultMap = gson.fromJson(bodyAsString, HashMap.class);
//            //二维码
//            codeUrl = resultMap.get("code_url");

            //保存二维码
//            String orderNo = orderInfo.getOrderNo();
//            orderInfoService.saveCodeUrl(orderNo, codeUrl);

            //返回二维码
//            Map<String, Object> map = new HashMap<>();
//            map.put("codeUrl", codeUrl);
//            map.put("orderNo", orderInfo.getOrderNo());

        } finally {
            response.close();
        }
    }
}
