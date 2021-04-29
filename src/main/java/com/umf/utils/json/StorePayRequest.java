package com.umf.utils.json;

import lombok.Data;

import java.util.Date;

@Data
public class StorePayRequest {

    private Integer id;//

    /** 版本号 **/
    private String version;
    /** 产品类型
     * W01-微信扫码支付
     * W02-微信公众号支付
     * W03-微信刷卡（反扫）
     * W04-微信H5支付
     * W05-微信APP支付
     * W06-微信小程序
     * A01-支付宝扫码支付
     * A02-支付宝刷卡支付
     * A03-支付窗支付
     * A04-支付宝APP支付
     * U01-银联二维码扫码支付
     * U03-银联二维码被扫
     * U05-银联云闪付(ApplePay)
     * U06-银联行业码
     * Q01-QQ扫码支付
     * Q02-QQ公众号支付
     * Q03-QQ刷卡支付
     */
    private String productId;
    /** 商户号 **/
    private String merchantNo;
    /** 订单日期 yyyyMMdd **/
    private String orderDate;
    /** 商户订单号 **/
    private String orderNo;
    /** 异步通知地址 **/
    private String notifyUrl;
    /** 前台通知地址 **/
    private String returnUrl;
    /** 交易金额 分**/
    private Integer orderAmount;
    /** 商品名称 **/
    private String goodsName;
    /** 商户门店编号 **/
    private String storeId;
    /** 商户机具终端编号 **/
    private String terminalId;
    /** 微信openId/支付宝用户号 **/
    private String userOpenid;
    /** 授权码 W03、A02、Q03时必填 **/
    private String authCode;
    /** 指定支付方式 **/
    private String limitPay;
    /** 商户营业类型 **/
    private String mcc;
    /** 城市编码 **/
    private String city;
    /** 设备IP **/
    private String clientIp;
    /** 二维码有效时间 默认180 **/
    private String validTime;
    /** 扩展字段 **/
    private String ext;
    /** 交易商户Id **/
    private String transMerId;
    /** 类型 **/
    private String signType;
    /** 签名 **/
    private String digest;

    private String authcode;//授权码 W03、A02、Q03时必填

    private String limitpay;//指定支付方式

    private String clientip;//设备IP

    private String validtime;//二维码有效时间 默认180

    private String transmerid;//交易商户Id

    private String signtype;//类型

    private Date createTime;//创建时间

    private Date updateTime;//修改时间


    private String paymentOrderNo;  //商户支付订单号
    private String paymentInstOrderNo;  //钱宝支付订单号
    private String refundOrderNo;  //商户退款订单号
    private Integer refundAmount;  //退款金额
    private String currency;  //货币类型
    private String refundReson;  //退款原因
    private String instRefundOrderNo;  //钱宝退款订单号
}
