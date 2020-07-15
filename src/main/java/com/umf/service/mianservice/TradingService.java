package com.umf.service.mianservice;

import com.umf.config.Properties;
import com.umf.utils.DateUtil;
import com.umf.utils.LoFunction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.umf.dao.DBDao;
import com.umf.service.profitservice.Profit;
import com.umf.service.profitservice.impl.ProfitImpl;
import com.umf.utils.RunningData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@SuppressWarnings("all")
public class TradingService {

    private String ip;
    private String reqpack;
    private Map<String, String> posReqMap;
//    private LogUtil log = new LogUtil();
    private LoFunction lo = new LoFunction();
    private Profit pro = new ProfitImpl();
    private String isAct = "0"; // 是否活动 0 不是 1 是
    private String isVip = "0"; // 是否VIP 0 不是 1 是
    private String proMark = "0"; // 0-分润底价 1-活动底价 2-会员底价
    private String isChannel = "0"; // 是否是渠道 0-否 1-是
    private String actId = "";
    private Map<String, String> audMap;
    private List<Map<String, String>> agList;
    private Map<String, Map<String, String>> selPolMap;
    private Map<String, String> channelPolMap;
//    private Jpush jpush = new Jpush();

    @Autowired
    private DBDao dbdao;

    @Autowired
    Properties properties;

    public void insRunningData(String reqpack, String ipp) {
        this.reqpack = reqpack;
        this.ip = ipp;
        try {
            RunningData.removeAll();  // 清除共享线程变量
            RunningData.setIp(ip);
            this.posReqMap = lo.changeJsonToMap(reqpack);
            pro.posReqMap(posReqMap);
        } catch (Exception e) {
            System.out.println(e);
//            log.errinfoE(e);
        }
    }

    public void mainService() throws Exception {
        try {
//            System.out.println("-----------------------------------------------------------------");
//            System.out.println(RunningData.getSerial());
//            System.out.println(RunningData.getSignature());
//            System.out.println(RunningData.getLocaldate());
//            System.out.println(RunningData.getPosno());
//            System.out.println(RunningData.getMerno());
//            System.out.println(RunningData.getMsgtype());
//            System.out.println(RunningData.getIp());
//            System.out.println("-----------------------------------------------------------------");
//
//            System.out.println(System.getProperty("line.separator"));
//            System.out.println("嘎嘎嘎过"+System.getProperty("line.separator")+"嘤嘤嘤");

//            List<Map> agentInfo = dbdao.getAgentInfo();
//            System.out.println(agentInfo);
//            agentInfo.forEach(map -> map.forEach((k,v) -> System.out.println(k+"="+v)));
//            log.error(agentInfo.toString());
//            log.info("嘤嘤嘤");


//			/** IP校验 */
//			if(!checkIp()) {
//				System.out.println("上送IP地址异常......");
//				return;
//			}

            /** 不是小微商户时 , 校验mac */
            if(!lo.isXw() && !checkMak()){
                System.out.println("Mak校验异常......");
                return;
            }

            /** 校验是否渠道  */
            if("2".equals(dbdao.selAgNat(posReqMap))) {
                isChannel = "1";   // 是否是渠道 0-否 1-是
            }
            System.out.println("是否是渠道 0-否 1-是："+isChannel);

            /** 反射到方法 */
            getClass().getMethod("deal_0" + isChannel, new Class[] {}).invoke(this);
        } catch (Exception e) {
            System.out.println(e);
//            log.errinfoE(e);
        }
    }

    /**
     *
     *********************************************************.<br>
     * [方法] pro_f0 <br>
     * [描述] 非渠道分润<br>
     * [返回] void <br>
     * [作者] UMF
     * [时间] 2019年8月30日 下午1:13:06 <br>
     *********************************************************.<br>
     */
    public void deal_00() throws Exception {

        try {
            if(!deal()) {
                return;
            }
//            /** 查询代理结算底价  */
//            selPolMap = dbdao.selPoldata(audMap);
//            String upPrice = "";   		   // 临时手续费结算底价
//            String addupPrice = "";  	   // 临时附加结算底价
//            String upTax = ""; 			   // 临时税点底价
//            String upProfitTj = ""; 	   // 临时调价手续费分润比例
//            String addupProfitTj = ""; 	   // 临时调价附加费分润比例
//            String taxproSumMoney = "0";   // 临时本级及以下分润金额
//            String taxaddproSumMoney = "0";// 临时本级及以下高签分润金额
//            for (int i = 0; i < agList.size(); i++) {
//                Map<String,String> auMap = new HashMap<String,String>();
//                auMap.putAll(audMap);
//                Map<String, String> agMap = agList.get(i);
//                Map<String, String> polMap = selPolMap.get(agMap.get("agent_num"));
//
//                auMap.put("sett_price", polMap==null ? "" : polMap.get("sett_price") == null ? "" : polMap.get("sett_price"));
//                auMap.put("ceiling_cost", polMap==null ? "" : polMap.get("ceiling_cost") == null ? "" : polMap.get("ceiling_cost"));
//                auMap.put("surcharge", polMap==null ? "" : polMap.get("surcharge") == null ? "" : polMap.get("surcharge"));
//                auMap.put("surcharge_ein", polMap==null ? "" : polMap.get("surcharge_ein") == null ? "" : polMap.get("surcharge_ein").equals("null") ? "" : polMap.get("surcharge_ein"));
//                auMap.put("tax_point", polMap==null ? "" : polMap.get("tax_point") == null ? InitConfig.tax : polMap.get("tax_point").equals("null") ? InitConfig.tax : polMap.get("tax_point"));
//                auMap.put("distribution_ratio", polMap==null ? "" : polMap.get("distribution_ratio") == null ? "" : polMap.get("distribution_ratio").equals("null") ? "" : polMap.get("distribution_ratio"));
//
//                auMap.put("upPrice", upPrice);
//                auMap.put("addupPrice", addupPrice);
//                auMap.put("upTax", upTax);
//                auMap.put("agent_level", agMap.get("agent_level"));
//                /** 计算手续费分润  */
//                Map<String, Object> charMap = pro.chargeProfit(auMap , i);
//                upPrice = charMap.get("upPrice").toString();
//                auMap.put("proMoney", charMap.get("proMoney").toString());
//                auMap.put("proSumMoney", charMap.get("proSumMoney").toString());
//                /**调价分润*/
//                Map<String, Object> charMapTj = pro.chargeProfitTj(auMap , i);
//                upProfitTj = charMapTj.get("upProfitTj").toString();
//                auMap.put("tj_profit", charMapTj.get("tj_profit").toString());
//                auMap.put("tj_profit_below", charMapTj.get("tj_profit_below").toString());
//
//                /** 计算附加手续费分润  */
//                if("0".equals(auMap.get("tj_fj"))){
//                    if("0".equals(auMap.get("sett_type"))) {
//                        Map<String, Object> addMap = pro.additProfit(auMap , i);
//                        addupPrice = addMap.get("addupPrice").toString();
//                        auMap.put("addproMoney", addMap.get("addproMoney").toString());
//                        auMap.put("addproSumMoney", addMap.get("addproSumMoney").toString());
//                    }
//                    if("1".equals(auMap.get("agent_level"))) {
//                        taxproSumMoney = auMap.get("proSumMoney").toString();
//                        if("0".equals(auMap.get("sett_type"))) {
//                            taxaddproSumMoney = auMap.get("addproSumMoney").toString();
//                        }
//                    }
//                    /** 计算税差  */
//                    Map<String, Object> taxMap = pro.taxpoint(auMap,taxproSumMoney,taxaddproSumMoney);
//                    auMap.put("level_tax", taxMap.get("level_tax").toString());  // 本级扣税
//                    auMap.put("below_tax", taxMap.get("below_tax").toString());  // 本级及以下扣税
//                    auMap.put("level_tax_profit", taxMap.get("level_tax_profit").toString());  // 本级扣税后分润金额
//                    auMap.put("below_tax_profit", taxMap.get("below_tax_profit").toString());  // 本级及以下扣税后分润金额
//                    auMap.put("profit_tax_diff", taxMap.get("profit_tax_diff").toString());  // 本级税差
//                    auMap.put("addlevel_tax", taxMap.get("addlevel_tax").toString());  // 附加本级扣税
//                    auMap.put("addbelow_tax", taxMap.get("addbelow_tax").toString());  // 附加本级及以下扣税
//                    auMap.put("addlevel_tax_profit", taxMap.get("addlevel_tax_profit").toString());  // 附加本级扣税后分润金额
//                    auMap.put("addbelow_tax_profit", taxMap.get("addbelow_tax_profit").toString());  // 附加本级及以下扣税后分润金额
//                    auMap.put("addprofit_tax_diff", taxMap.get("addprofit_tax_diff").toString());  // 附加本级税差
//                    upTax = taxMap.get("upTax").toString();
//                }else{
//                    /** 调价附加手续费 */
//                    if("0".equals(auMap.get("sett_type"))) {
//                        Map<String, Object> addMap = pro.additProfitTj(auMap , i);
//                        addupProfitTj = addMap.get("addupProfitTj").toString();
//                        auMap.put("addproMoney", addMap.get("tj_addproMoney").toString());
//                        auMap.put("addproSumMoney", addMap.get("tj_addproSumMoney").toString());
//                    }
//                }
//                dbdao.insProData(auMap, agMap);
////				if(new BigDecimal(auMap.get("level_tax_profit")).compareTo(new BigDecimal("0")) == 1) {
////					jpush.pAll(agMap.get("agent_num"),auMap.get("level_tax_profit"));
////				}
//            }
//
//            Map<String, String> proMap = dbdao.selProMap();
//            if(new BigDecimal(proMap.get("charge_profit")).compareTo(new BigDecimal(audMap.get("charge"))) == 1
//                    || new BigDecimal(proMap.get("surcharge_profit")).compareTo(new BigDecimal(audMap.get("affix_charge"))) == 1 ) {
//                dbdao.upProData();
//            }
////			dbdao.upAudData();/

        } catch (Exception e) {
            System.out.println(e);
//            log.errinfoE(e);
        }
    }
//
//    /**
//     *
//     *********************************************************.<br>
//     * [方法] pro_f1 <br>
//     * [描述] 渠道分润<br>
//     * [返回] void <br>
//     * [作者] UMF
//     * [时间] 2019年8月30日 下午1:12:53 <br>
//     *********************************************************.<br>
//     */
//    public void deal_01() throws Exception {
//        if(!deal()) {
//            return;
//        }
//		/*查询机构费率*/
//        String rate = dbdao.selChaRate(audMap);
//		/*机构成本，分润费率*交易金额*/
//        BigDecimal cost = new BigDecimal(rate).multiply(new BigDecimal(audMap.get("amount"))).setScale(2, BigDecimal.ROUND_HALF_UP);
//		/*机构分润，手续费-机构成本*/
//        BigDecimal profit = new BigDecimal(audMap.get("charge")).subtract(cost).setScale(2, BigDecimal.ROUND_HALF_UP);
//		/*机构成本，机构利润*/
//        audMap.put("cost",rate);
//        audMap.put("channel_cost",cost+"");
//        audMap.put("channel_profit",profit+"");
//		/*将渠道分润信息添加到渠道分润信息表中*/
//        Integer i = dbdao.insChaData(audMap);
//		/*修改清算表状态码为1，已发*/
////		dbdao.upAudData();
//    }

    /**
     *
     *********************************************************.<br>
     * [方法] deal <br>
     * [描述] <br>
     * [参数] <br>
     * [返回] boolean <br>
     * [作者] UMF
     * [时间] 2019年9月2日 下午1:31:45 <br>
     *********************************************************.<br>
     */
    public boolean deal() {

        try {
            /** 根据流水号查询分润表是否存在 */
            boolean serial = !(dbdao.selSerial(isChannel, posReqMap.get("serial")) == 0);
            System.out.println("是否存在分润表："+serial);
            if(serial) {
                System.out.println("已经生成过分润信息了!");
                return false;
            }

            /** 查询要分润的结算数据 */
            if("0".equals(isChannel)) {
                if(lo.isXw()){
                    audMap = dbdao.selAuditdataXW(posReqMap);
                }else{
                    audMap = dbdao.selAuditdata(posReqMap);
                }
            }else {
                audMap = dbdao.selChannelAuditdata(posReqMap);
            }
            System.out.println("audMap="+audMap);
//            if(audMap == null) {
//                return false;
//            }

            /** 查询代理商信息 */
            agList = dbdao.selAgdata(/*audMap.get("agent_num")*/"100016074");
            System.out.println("agList="+agList.toString());
            if(agList.size() == 0) {
                return false;
            }

            /** 是否mypos 终端类型 0-传统Pos 1-Mpos 2-智能Pos 3-机构  4 电签pos */
            if ("0".equals(audMap.get("pos_type"))) {
                /** 查询当前终端是否是活动终端 */
                actId = dbdao.isAct(audMap.get("localdate"),posReqMap.get("posno"));
                if (!"".equals(actId)) {
                    isAct = "1"; // 是否活动 0 不是 1 是
                    proMark = "1"; // 0-分润底价 1-活动底价 2-会员底价
                }
            }

//            if ("1".equals(audMap.get("pos_type"))) {
//                /** 查询当前终端是否是活动终端 */
//                actId = dbdao.isAct(audMap.get("localdate"));
//                if (!"".equals(actId)) {
//                    isAct = "1"; // 是否活动 0 不是 1 是
//                    proMark = "1"; // 0-分润底价 1-活动底价 2-会员底价
//                }
//
//                /** 查看当前商户是否是会员商户 */
//                if (!"0000-00-00".equals(audMap.get("vip_status")) && DateUtil.getbooleanDay(audMap.get("vip_status"))) {
//                    isVip = "1"; // 是否VIP 0 不是 1 是
//                    proMark = "2"; // 0-分润底价 1-活动底价 2-会员底价
//                }
//            }
//
//            if ("4".equals(audMap.get("pos_type")) && "V".equals(audMap.get("vip")) && "H007".equals(RunningData.getMsgtype())) {
//                isVip = "1"; // 是否VIP 0 不是 1 是
//                proMark = "2";
//            }
//
//            audMap.put("actId", actId);
//            audMap.put("isAct", isAct);
//            audMap.put("proMark", proMark);
//            audMap.put("isVip", isVip);
//            audMap.put("isChannel", isChannel);
        } catch (Exception e) {
//            log.errinfoE(e);
        }
        return true;
    }

    /**
     *
     *********************************************************.<br>
     * [方法] checkIp <br>
     * [描述] ip校验 <br>
     * [返回] boolean <br>
     * [作者] UMF
     * [时间] 2019年8月30日 下午2:08:47 <br>
     *********************************************************.<br>
     */
    public boolean checkIp() throws Exception {
        String[] strIp = properties.getIp();
        System.out.println(strIp.toString());
        for (int i = 0; i < strIp.length; i++) {
            System.out.println(RunningData.getIp());
            System.out.println(strIp[i]);
            if(RunningData.getIp().equals(strIp[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     *********************************************************.<br>
     * [方法] checkMak <br>
     * [描述] mak校验 <br>
     * [返回] boolean <br>
     * [作者] UMF
     * [时间] 2019年8月30日 下午2:09:09 <br>
     *********************************************************.<br>
     */
    public boolean checkMak() throws Exception {
        return RunningData.getSignature().equals(pro.holdSignature(posReqMap)) ? true : false;
    }

//    /**
//     *
//     *********************************************************.<br>
//     * [方法] checkMak <br>
//     * [描述] 查询小微商户的数据 <br>
//     * [返回] boolean <br>
//     * [作者] UMF
//     * [时间] 2019年8月30日 下午2:09:09 <br>
//     *********************************************************.<br>
//     */
//    public void mainServiceXw() throws Exception {
//
//        /** 校验mac */
//        if(!checkMak()){
//            System.out.println("小微Mak校验异常......");
//            return;
//        }
//
//        /** 判断单条数据还是多条数据 */
//        if(RunningData.getLocaldate()!=null && !"".equals(RunningData.getLocaldate())){
//            // 查询小微商户的基础信息
//            List<Map<String, String>> xwData = dbdao.selXwData();
//            System.out.println("数据总数量："+xwData.size());
//            for(Map<String,String> map : xwData){
//                System.out.println(map);
//                RunningData.removeAll(); // 清除线程共享变量
//                pro.posReqMap(map);	     // 为线程共享变量赋值
//                mainService();
//            }
//        }else{
//            mainService();
//        }
//    }
}
