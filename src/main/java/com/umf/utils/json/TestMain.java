package com.umf.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package app.aop
 * @Author:LiuYuKun
 * @Date:2021/2/8 9:48
 * @Description:
 */
public class TestMain {

    /**
     * 算法常量
     */
    public static final String SIGN_ALGORITHM_SHA256RSA = "SHA256withRSA";

    /**
     *  私钥
     */
    public static String SIGNKEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCtwz5WQBNA+R/k9ZELlELhle7IAWEATxGkg7As04xdkOgba1nEKYXD7/BrBnjEvITgnAcIT79Xz4vhtHu06FxcnXrEF7wuiUc5fFUwJiGQFlsbxlhue4z3PZ86QfP8f1RhcADMtPwQ3tE6fQNyzgwbkDRmJu/YXI0O62ahTWPOmCz+Vbfp6NzSo1+je3bnLNE8ozZP1GR4BoRwZcOKmTujeIe6d33BYZ9CqwrC4g9NJupl42+f5vR2q0aObsRILFuC+4uXsc4OOrWXxkldFnbyYrPMMm1otQVifg4Ppm/3u0IbdxkIcXqamTAwXJxokxzyenjCxJQHWmu1ApL6SZRlAgMBAAECggEAEOT+1pv8Q0D1nOlTZPDRX+WYBdI8gC8v4/L52iHnb+F678+MNspiux5FHeLPMzGxKbWB6Yj3ba48R4Go1XoSIN8e5Jn6Lfwi9K/mCHxsnvnNefHa/0L7/b38EUoAUvh+V8rwCPc/2tQPO8ST5UGrY7KtpZD7mOnGvUCeBejkX127TTn/mgWqJgAmsOhxGFVV781Z0etODvI1QX1seH30PgONG7xsUSR83804DdJFy9H2Hqj7phxDSyH/lRlwndwQ8+mXyHSzHSm0KJZxZQOElj0LExqIMAIXCms9VwPs8u7VIcb2b8UiNG6OiMiTZhgmXhDNDl0k2G6pUsFLYpGHsQKBgQDfeEikeio/YHBE+/U21JwbZE4j9KBPDQePkTbkV57xc3o/x832YvUApoapOmA03MXfM0w0WJ6rc7ysJvLYRZCV1Mcai3bXbRAtEDmORcvYCoWNrXUHK/wtkhFeGMmyILmbIxTbUC4I8XXffQwZ7NtT7h+noMdp3FX4N9tPGcGA+wKBgQDHDpixkUfZPbfRK6g9JhLJ4yDHLTJ6P8GPUdFDj8RDmZVgkGk+1C/0F6OAv4jwiCxBVF/bYAjARwSry+HmQMXskk0KmPygvUGfGLiF64sot+iqsbf2xJXZFE9y951tDVU3GK5n3/lw9Hoi7TQO1JrXEeMg4AMkeugYy6HLrTsCHwKBgCuyaon29XHlGq7ykbWCB3B3wavYNsyeYJ8bJx+pXoQaL4pvOH/4Q6434dcPeiZ8ERke/8Swm34tKHSFPTE7ERWrQK+ZG8juI56cMJT4Yu7Ax/K3O04GtM34ZPsAX9g7++8xAfAMkqPfC0yDOC2NmimkQ35UuwmhMxJRYcnq4GKnAoGAUPENg/7YYWzoRwTil2LY9wEFfhhR00YDlhyl5DwciYR1Klvuf97WVQIbuSmpLG2i2TnO9Kx7QnxeWOFBzVf/Y3AmJa4J4+6xNVlfBw29e2Q1FtvHO2+6oxfQKqanfAWU5h+CI7j+fEDdPUCJD5LCF7wtSFvuyzGzGQoMk36wZO8CgYEAzgo7x/v534U++u1JdjAJ2ycraPNBo7d01w1q7qpXam7AWdLYaEgIi18T4ZzShSCpWdBupwlS/LCkChNjqJ9asSvRxGDZiASCmA1Uh7+YWTQhyM7jH7Ff6OO8dg/yaMcDIJt8w19tgWGCvOscLIs1x9YlZem3lgG9Dqp+locPvLM=";

    public static void main(String[] args) throws Exception {
//        tuikuan();
        tuikuanSeelct();
    }

    // 退款接口
    public static void tuikuan() throws Exception {
        StorePayRequest payRequest = new StorePayRequest();
        payRequest.setVersion("V1.0");  //版本号
        payRequest.setMerchantNo("666290158120190");                //商户号
        payRequest.setPaymentOrderNo("10000064320210208180249");    //商户支付订单号（store_order_info表order_no字段）
        payRequest.setPaymentInstOrderNo("20210208180216630773");   //钱宝支付订单号（store_order_info表serial字段）
        String refundOrderNo = "19999999" + getNumberDateTime();
        payRequest.setRefundOrderNo(refundOrderNo);                 //商户退款订单号（商户退款时生成的订单号）
        payRequest.setRefundAmount(1);  //退款金额（分）
        payRequest.setCurrency("RMB");  //货币类型
        payRequest.setNotifyUrl("http://9j6k8b.natappfree.cc/basics/selectAgreement.do");   //异步通知接口地址
        payRequest.setRefundReson("充值错误了。");//退款原因
        payRequest.setSignType("RSA2"); //签名类型（默认RSA2）
        Map<String,String> dataMap= objectToMap(payRequest);
        String digest = sign(dataMap);
        payRequest.setDigest(digest);
        dataMap.put("digest", digest);
        //记录请求包
        System.out.println("下单请求:" + dataMap.toString());
        String response = sendPost("https://ipaytest.fortunebill.com/mas/ order/refund.do",dataMap);
        System.out.println("下单响应:" + response);
    }

    // 退款状态查询接口
    public static void tuikuanSeelct() throws Exception {
        StorePayRequest payRequest = new StorePayRequest();
        payRequest.setVersion("V1.0");  //版本号
        payRequest.setMerchantNo("666290158120190");            //商户号
        payRequest.setRefundOrderNo("1999999920210208180529");  //商户退款订单号（同上，商户退款时生成的订单号）
        payRequest.setInstRefundOrderNo("2021020818045969780"); //钱宝退款订单号（退款接口返回的instRefundOrderNo字段）
        payRequest.setSignType("RSA2"); //签名类型（默认RSA2）
        Map<String,String> dataMap= objectToMap(payRequest);
        String digest = sign(dataMap);
        payRequest.setDigest(digest);
        dataMap.put("digest", digest);
        //记录请求包
        System.out.println("下单请求:" + dataMap.toString());
        String response = sendPost("https://ipaytest.fortunebill.com/mas/ order/query_refund.do",dataMap);
        System.out.println("下单响应:" + response);
    }

    /**
     * @Author: wangzw
     * @Description: 获取系统当前时间数字形式
     * @Version: 1.0
     * @Date: 2017/2/17 11:42
     */
    public static String getNumberDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }

    public static Map<String,String> objectToMap(Object obj){
        JSON json = (JSON) JSON.toJSON(obj);
        return (Map) JSONObject.parseObject(json.toString());
    }

    public static String sign(Map<String,String> param) throws Exception{
        RSAPrivateKey privateKey = (RSAPrivateKey) getPrivateKey(SIGNKEY);
        TreeMap<String,Object> treeMap = new TreeMap<>();
        treeMap.putAll(param);
        treeMap.remove("digest");
        String signStr = getParamStr(treeMap);
        byte[] msg = signStr.getBytes("UTF-8");
        byte[] signature = null;
        signature = signWithSha256(privateKey,msg);
        return Base64.getEncoder().encodeToString(signature);
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        //keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    private static String getParamStr(TreeMap<String, Object> paramsMap) {
        StringBuilder param = new StringBuilder();
        for (Iterator<Map.Entry<String, Object>> it = paramsMap.entrySet()
                .iterator(); it.hasNext();) {
            Map.Entry<String, Object> e = it.next();
            if(StringUtil.isNullOrEmpty(e.getValue()+"")){
                continue;
            }
            param.append(e.getKey()).append("=")
                    .append(e.getValue()).append("&");
        }
        return param.toString().substring(0,param.toString().length()-1);
    }

    /**
     * RSA Sha256摘要  私钥签名
     * @param privateKey 私钥
     * @param data 消息
     * @return 签名
     */
    public static byte[] signWithSha256(RSAPrivateKey privateKey, byte[] data) {
        byte[] result = null;
        Signature st;
        try {
            st = Signature.getInstance(SIGN_ALGORITHM_SHA256RSA);
            st.initSign(privateKey);
            st.update(data);
            result = st.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPost(String pathUrl,Map<String, String> map) throws Exception{
        URL url=new URL(pathUrl);
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : map.entrySet()) {
            if (postData.length() != 0){
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(
                    String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length",
                String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        Reader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;){
            sb.append((char) c);
        }
        return sb.toString();
    }

}
