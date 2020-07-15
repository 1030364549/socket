package com.umf.service.profitservice;

import java.util.Map;

@SuppressWarnings("all")
public interface Profit {
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] posReqMap <br>
	 * [描述] 将参数赋值给线程共享变量 <br>
	 * [返回] Map<String,String> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:33 <br>
	 *********************************************************.<br>
	 */
	void posReqMap(Map<String, String> posReqMap) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [方法] posReqMap <br>
	 * [描述] 将传入的参数赋值给对应的变量中 <br>
	 * [返回] Map<String,String> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:33 <br>
	 *********************************************************.<br>
	 */
	void mapToParam(Map<String, String> map, Object o) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [方法] holdSignature <br>
	 * [描述] mac <br>
	 * [返回] String <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:38 <br>
	 *********************************************************.<br>
	 */
	String holdSignature(Map<String, String> posReqMap) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [方法] chargeProfit <br>
	 * [描述] 非附加 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:42 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> chargeProfit(Map<String, String> auMap, int i) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [方法] additProfit <br>
	 * [描述] 附加 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:47 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> additProfit(Map<String, String> auMap, int i) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [方法] taxpoint <br>
	 * [描述] 税差 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:52 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> taxpoint(Map<String, String> auMap, String taxproSumMoney, String taxaddproSumMoney) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [方法] chargeProfitTj <br>
	 * [描述] 税差 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:52 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> chargeProfitTj(Map<String, String> auMap, int i) throws Exception;

	/**
	 *
	 *********************************************************.<br>
	 * [方法] additTjProfit <br>
	 * [描述] 费率的附加费计算 <br>
	 * [返回] Map<String,Object> <br>
	 * [作者] UMF
	 * [时间] 2019年11月4日 下午1:04:47 <br>
	 *********************************************************.<br>
	 */
	Map<String, Object> additProfitTj(Map<String, String> auMap, int i) throws Exception;
}
