package com.umf.service.profitservice.impl;

import com.umf.service.profitservice.Profit;
import com.umf.utils.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("all")
public class ProfitImpl implements Profit {
	
	private LoFunction lo = new LoFunction();
	private EncryUtil eu = new EncryUtil();
	private Utils ut = new Utils();
	private LogUtil log = new LogUtil();

	@Override
	public void posReqMap(Map<String, String> posReqMap) throws Exception {
		mapToParam(posReqMap,new RunningData());
//		log.saveMaplog(posReqMap.toString());
	}

	// 给对象的变量赋值
	public void mapToParam(Map<String,String> map , Object o) throws Exception {
		Class clazz = o.getClass();
		List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
		List<String> fieldsName = new ArrayList();
		fields.forEach(field -> fieldsName.add(field.getName()));   // 将所有变量名取出
		for(Map.Entry<String,String> entry:map.entrySet()){
			String key = entry.getKey();
			if(fieldsName.contains(key)){   // 判断是否存在变量名
				String name = new StringBuffer("set").append(key.substring(0,1).toUpperCase()).append(key.substring(1)).toString();
				clazz.getMethod(name, String.class).invoke(o, entry.getValue());
			}
		}
	}

	@Override
	public String holdSignature(Map<String, String> posReqMap) throws Exception {
		System.out.println(posReqMap);
		if(posReqMap.get("signature")!=null&&!"".equals(posReqMap.get("signature"))){
			posReqMap.remove("signature");
		}
		StringBuffer sb = new StringBuffer();
		posReqMap.forEach((key,value) -> sb.append(key).append("=").append(value).append("&"));
		sb.delete(sb.length()-1,sb.length());
		return eu.encryptMD5(sb.toString());
	}

	@Override
	public Map<String, Object> chargeProfit(Map<String, String> auMap , int i) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (auMap.get("ar_type")) {
		case "0":
			map = rate(auMap , i);
			break;
		case "1":
			map = ceiling(auMap , i);
			break;
		}
		return map;
	}

	public Map<String, Object> rate(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal proMoney = new BigDecimal("0");   // 本级分润金额
		BigDecimal proSumMoney = new BigDecimal("0");   // 本级及以下分润金额
		String ar_content = auMap.get("ar_content");
		if("0".equals(auMap.get("isVip"))) {  // 是否VIP 0 不是 1 是
			ar_content = new BigDecimal(ar_content).subtract(new BigDecimal(auMap.get("price_ratio")))+"";
		}
		if (i > 0) {
			if(!"".equals(auMap.get("sett_price"))) {
				if(!"".equals(auMap.get("upPrice"))) {
					proMoney = ut.noFirstMultiply(auMap.get("amount"),ar_content,auMap.get("upPrice"),auMap.get("sett_price"));
					proSumMoney = ut.firstMultiply(auMap.get("amount"),ar_content,auMap.get("sett_price"));
				}else {
					proMoney = ut.firstMultiply(auMap.get("amount"),ar_content,auMap.get("sett_price"));
					proSumMoney = proMoney;
				}
			}
		}else {
			if(!"".equals(auMap.get("sett_price"))) {
				proMoney = ut.firstMultiply(auMap.get("amount"),ar_content,auMap.get("sett_price"));
				proSumMoney = proMoney;
			}
		}
		map.put("proMoney", proMoney);
		map.put("proSumMoney", proSumMoney);
		map.put("upPrice", auMap.get("sett_price"));
		return map;
	}

	public Map<String, Object> ceiling(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal proMoney = new BigDecimal("0");
		BigDecimal proSumMoney = new BigDecimal("0");
		String upPrice = "";
		if (i > 0) {
			if (!"".equals(auMap.get("ceiling_cost"))) {
				if (!"".equals(auMap.get("upPrice"))) {
					proMoney = ut.noFirstSubtract(auMap.get("charge"),auMap.get("upPrice"),auMap.get("ceiling_cost"));
					proSumMoney = ut.firstSubtract(auMap.get("charge"),auMap.get("ceiling_cost"));
				}else {
					proMoney = ut.firstSubtract(auMap.get("charge"),auMap.get("ceiling_cost"));
					proSumMoney = proMoney;
				}
			}
		}else {
			if (!"".equals(auMap.get("ceiling_cost"))) {
				proMoney = ut.firstSubtract(auMap.get("charge"),auMap.get("ceiling_cost"));
				proSumMoney = proMoney;
			}
		}
		upPrice = auMap.get("ceiling_cost");
		map.put("proMoney", proMoney);
		map.put("proSumMoney", proSumMoney);
		map.put("upPrice", upPrice);
		return map;
	}

	@Override
	public Map<String, Object> additProfit(Map<String, String> auMap , int i) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (auMap.get("aff_type")) {
		case "0":
			map = addRate(auMap, i);
			break;
		case "1":
			map = additCeiling(auMap, i);
			break;
		}
		return map;
	}

	public Map<String, Object> addRate(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal addproMoney = new BigDecimal("0");
		BigDecimal addproSumMoney = new BigDecimal("0");
		String addupPrice = "";
		if (i > 0) {
			if(!"".equals(auMap.get("surcharge"))) {
				if(!"".equals(auMap.get("addupPrice"))) {
					addproMoney = ut.noFirstMultiply(auMap.get("amount"),auMap.get("affix_ar_content"),auMap.get("addupPrice"),auMap.get("surcharge"));
					addproSumMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("affix_ar_content"),auMap.get("surcharge"));
				}else {
					addproMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("affix_ar_content"),auMap.get("surcharge"));
					addproSumMoney = addproMoney;
				}
			}
		}else {
			if(!"".equals(auMap.get("surcharge"))) {
				addproMoney = ut.firstMultiply(auMap.get("amount"),auMap.get("affix_ar_content"),auMap.get("surcharge"));
				addproSumMoney = addproMoney;
			}
		}
		addupPrice = auMap.get("surcharge");
		map.put("addproMoney", addproMoney);
		map.put("addproSumMoney", addproSumMoney);
		map.put("addupPrice", addupPrice);
		return map;
	}

	public Map<String, Object> additCeiling(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal addproMoney = new BigDecimal("0");
		BigDecimal addproSumMoney = new BigDecimal("0");
		if (i > 0) {
			if (!"".equals(auMap.get("surcharge_ein"))) {
				if (!"".equals(auMap.get("addupPrice"))) {
					addproMoney = ut.noFirstSubtract(auMap.get("affix_charge"),auMap.get("addupPrice"),auMap.get("surcharge_ein"));
					addproSumMoney = ut.firstSubtract(auMap.get("affix_charge"),auMap.get("surcharge_ein"));
				}else {
					addproMoney = ut.firstSubtract(auMap.get("affix_charge"),auMap.get("surcharge_ein"));
					addproSumMoney = addproMoney;
				}
			}
		}else {
			if (!"".equals(auMap.get("surcharge_ein"))) {
				addproMoney = ut.firstSubtract(auMap.get("affix_charge"),auMap.get("surcharge_ein"));
				addproSumMoney = addproMoney;
			}
		}
		map.put("addproMoney", addproMoney);
		map.put("addproSumMoney", addproSumMoney);
		map.put("addupPrice", auMap.get("surcharge_ein"));
		return map;
	}

	@Override
	public Map<String, Object> taxpoint(Map<String, String> auMap,String taxproSumMoney,String taxaddproSumMoney) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal level_tax = new BigDecimal("0");
		BigDecimal below_tax = new BigDecimal("0");
		BigDecimal level_tax_profit = new BigDecimal("0");
		BigDecimal below_tax_profit = new BigDecimal("0");
		BigDecimal profit_tax_diff = new BigDecimal("0");
		BigDecimal addlevel_tax = new BigDecimal("0");
		BigDecimal addbelow_tax = new BigDecimal("0");
		BigDecimal addlevel_tax_profit = new BigDecimal("0");
		BigDecimal addbelow_tax_profit = new BigDecimal("0");
		BigDecimal addprofit_tax_diff = new BigDecimal("0");
		int level = Integer.parseInt(auMap.get("agent_level"));
		if (!"".equals(auMap.get("tax_point"))) {
			level_tax = ut.taxMoney(auMap.get("proMoney"),auMap.get("tax_point"),auMap.get("pos_type"));
			below_tax = ut.taxMoney(auMap.get("proSumMoney"),auMap.get("tax_point"),auMap.get("pos_type"));
			level_tax_profit = new BigDecimal(auMap.get("proMoney")).subtract(level_tax);
			below_tax_profit = new BigDecimal(auMap.get("proSumMoney")).subtract(below_tax);
			if("0".equals(auMap.get("sett_type"))) {
				addlevel_tax = ut.taxMoney(auMap.get("addproMoney"),auMap.get("tax_point"),auMap.get("pos_type"));
				addbelow_tax = ut.taxMoney(auMap.get("addproSumMoney"),auMap.get("tax_point"),auMap.get("pos_type"));
				addlevel_tax_profit = new BigDecimal(auMap.get("addproMoney")).subtract(addlevel_tax);
				addbelow_tax_profit = new BigDecimal(auMap.get("addproSumMoney")).subtract(addbelow_tax);
			}
		}

		if(level <= 1) {
			if(!"".equals(auMap.get("tax_point"))) {
				if(!"".equals(auMap.get("upTax"))) {
					profit_tax_diff = profit_tax_diff.add(ut.taxDiff(taxproSumMoney,auMap.get("upTax"),auMap.get("tax_point"),auMap.get("pos_type")));
					if("0".equals(auMap.get("sett_type"))) {
						addprofit_tax_diff = addprofit_tax_diff.add(ut.taxDiff(taxaddproSumMoney,auMap.get("upTax"),auMap.get("tax_point"),auMap.get("pos_type")));
					}
				}
			}
		}

		map.put("level_tax", level_tax);
		map.put("below_tax", below_tax);
		map.put("level_tax_profit", level_tax_profit);
		map.put("below_tax_profit", below_tax_profit);
		map.put("profit_tax_diff", profit_tax_diff);
		map.put("addlevel_tax", addlevel_tax);
		map.put("addbelow_tax", addbelow_tax);
		map.put("addlevel_tax_profit", addlevel_tax_profit);
		map.put("addbelow_tax_profit", addbelow_tax_profit);
		map.put("addprofit_tax_diff", addprofit_tax_diff);
		map.put("upTax", auMap.get("tax_point"));
		return map;
	}

	public Map<String, Object> chargeProfitTj(Map<String, String> auMap , int i) throws Exception  {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal tj_profit = new BigDecimal("0");   // 本级分润金额
		BigDecimal tj_profit_below = new BigDecimal("0");   // 本级及以下分润金额
		if (i > 0) {
			if(!"".equals(auMap.get("distribution_ratio"))) {
				if(!"".equals(auMap.get("upProfitTj"))) {
					tj_profit = ut.noFirstMultiplyTj(auMap.get("tj_rate_fee"),auMap.get("upProfitTj"),auMap.get("distribution_ratio"));
					tj_profit_below = new BigDecimal(auMap.get("tj_rate_fee")).multiply(new BigDecimal(auMap.get("distribution_ratio"))).setScale(2, BigDecimal.ROUND_HALF_UP);
				}else {
					tj_profit = new BigDecimal(auMap.get("tj_rate_fee")).multiply(new BigDecimal(auMap.get("distribution_ratio"))).setScale(2, BigDecimal.ROUND_HALF_UP);
					tj_profit_below = tj_profit;
				}
			}
		}else {
			if(!"".equals(auMap.get("distribution_ratio"))) {
				tj_profit = new BigDecimal(auMap.get("tj_rate_fee")).multiply(new BigDecimal(auMap.get("distribution_ratio"))).setScale(2, BigDecimal.ROUND_HALF_UP);
				tj_profit_below = tj_profit;
			}
		}
		map.put("tj_profit", tj_profit);
		map.put("tj_profit_below", tj_profit_below);
		map.put("upProfitTj", auMap.get("distribution_ratio"));
		return map;
	}

	@Override
	public Map<String, Object> additProfitTj(Map<String, String> auMap , int i) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal tj_addproMoney = new BigDecimal("0");
		BigDecimal tj_addproSumMoney = new BigDecimal("0");
		if (i > 0) {
			if(!"".equals(auMap.get("distribution_ratio"))) {
				if(!"".equals(auMap.get("addupProfitTj"))) {
					tj_addproMoney = ut.noFirstMultiplyTj(auMap.get("tj_rate_fee"),auMap.get("addupProfitTj"),auMap.get("distribution_ratio"));
					tj_addproSumMoney = new BigDecimal(auMap.get("tj_rate_fee")).multiply(new BigDecimal(auMap.get("distribution_ratio"))).setScale(2, BigDecimal.ROUND_HALF_UP);
				}else {
					tj_addproMoney = new BigDecimal(auMap.get("tj_rate_fee")).multiply(new BigDecimal(auMap.get("distribution_ratio"))).setScale(2, BigDecimal.ROUND_HALF_UP);
					tj_addproSumMoney = tj_addproMoney;
				}
			}
		}else {
			if(!"".equals(auMap.get("distribution_ratio"))) {
				tj_addproMoney = new BigDecimal(auMap.get("tj_rate_fee")).multiply(new BigDecimal(auMap.get("distribution_ratio"))).setScale(2, BigDecimal.ROUND_HALF_UP);
				tj_addproSumMoney = tj_addproMoney;
			}
		}
		map.put("tj_addproMoney", tj_addproMoney);
		map.put("tj_addproSumMoney", tj_addproSumMoney);
		map.put("addupProfitTj", auMap.get("distribution_ratio"));
		return map;
	}
}
