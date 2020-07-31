
package com.umf.utils;

import com.umf.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Utils {

	@Autowired
	public Config config;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] multiply <br>
	 * [描述] 费率第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年10月31日 上午10:00:19 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal firstMultiply(String amount, String ar_content, String sett_price) throws Exception {
		if(new BigDecimal(sett_price).compareTo(new BigDecimal(ar_content)) == 1){
			return new BigDecimal("0");
		}
		return new BigDecimal(amount).multiply(new BigDecimal(ar_content).subtract(new BigDecimal(sett_price))).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 *
	 *********************************************************.<br>
	 * [方法] noFirstMultiply <br>
	 * [描述] 费率非第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年10月31日 上午11:49:20 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal noFirstMultiply(String amount, String ar_content, String upPrice, String sett_price) throws Exception {
		if(new BigDecimal(sett_price).compareTo(new BigDecimal(ar_content)) == 1){
			return new BigDecimal("0");
		}else if(new BigDecimal(sett_price).compareTo(new BigDecimal(upPrice)) == 1) {
			return new BigDecimal("0");
		}else if (new BigDecimal(upPrice).subtract(new BigDecimal(sett_price)).compareTo(new BigDecimal(ar_content)) == 1) {
			return new BigDecimal("0");
		}else {	// amount*((ar_content-sett_price)-(ar_content-upPrice))
			return new BigDecimal(amount).multiply(new BigDecimal(upPrice).subtract(new BigDecimal(sett_price))).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}

	/**
	 *
	 *********************************************************.<br>
	 * [方法] firstSubtract <br>
	 * [描述] 封顶第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年10月31日 下午12:37:00 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal firstSubtract(String charge, String ceiling_cost) throws Exception {
		if(new BigDecimal(ceiling_cost).compareTo(new BigDecimal(charge)) == 1){
			return new BigDecimal("0");
		}
		return new BigDecimal(charge).subtract(new BigDecimal(ceiling_cost));
	}

	/**
	 *
	 *********************************************************.<br>
	 * [方法] noFirstSubtract <br>
	 * [描述] 封顶非第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年10月31日 下午12:37:03 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal noFirstSubtract(String charge, String upPrice , String ceiling_cost) throws Exception {
		if(new BigDecimal(ceiling_cost).compareTo(new BigDecimal(charge)) == 1){
			return new BigDecimal("0");
		}else if(new BigDecimal(ceiling_cost).compareTo(new BigDecimal(upPrice)) == 1) {
			return new BigDecimal("0");
		}else if (new BigDecimal(upPrice).subtract(new BigDecimal(ceiling_cost)).compareTo(new BigDecimal(charge)) == 1) {
			return new BigDecimal("0");
		}else {
			return new BigDecimal(upPrice).subtract(new BigDecimal(ceiling_cost));
		}
	}

	/**
	 *
	 *********************************************************.<br>
	 * [方法] taxMoney <br>
	 * [描述] 扣税 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午12:58:14 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal taxMoney (String money, String taxPoint,String pos_type) throws Exception {
		if(new BigDecimal(taxPoint).compareTo(new BigDecimal("4".equals(pos_type) ? config.getDqtax() : config.getTax())) == 1){
			return new BigDecimal(money).multiply(new BigDecimal("4".equals(pos_type) ? config.getDqtax() : config.getTax())).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return new BigDecimal(money).multiply(new BigDecimal(taxPoint)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 *
	 *********************************************************.<br>
	 * [方法] taxDiff <br>
	 * [描述] 税差 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午12:57:52 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal taxDiff(String money, String upTax , String taxPoint,String pos_type) throws Exception {
		if(new BigDecimal(taxPoint).compareTo(new BigDecimal("4".equals(pos_type) ? config.getDqtax() : config.getTax())) == 1){
			return new BigDecimal("0");
		}else if(new BigDecimal(taxPoint).compareTo(new BigDecimal(upTax)) == 1) {
			return new BigDecimal("0");
		}else if (new BigDecimal(upTax).subtract(new BigDecimal(taxPoint)).compareTo(new BigDecimal("4".equals(pos_type) ? config.getDqtax() : config.getTax())) == 1) {
			return new BigDecimal("0");
		}else {
			return new BigDecimal(money).multiply(new BigDecimal(upTax).subtract(new BigDecimal(taxPoint))).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}

	/**
	 *
	 *********************************************************.<br>
	 * [方法] noFirstMultiplyTj <br>
	 * [描述] 调价费率非第一次 <br>
	 * [返回] BigDecimal <br>
	 * [作者] UMF
	 * [时间] 2020年6月17日 上午11:49:20 <br>
	 *********************************************************.<br>
	 */
	public BigDecimal noFirstMultiplyTj(String amount, String upProfit, String distribution_ratio) throws Exception {
		if(new BigDecimal(distribution_ratio).compareTo(new BigDecimal(upProfit)) == 1) {
			return new BigDecimal("0");
		}else {
			return new BigDecimal(amount).multiply(new BigDecimal(upProfit).subtract(new BigDecimal(distribution_ratio))).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
}

	