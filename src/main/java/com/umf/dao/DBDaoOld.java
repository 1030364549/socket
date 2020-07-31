package com.umf.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Package com.umf.dao
 * @Author:LiuYuKun
 * @Date:2020/7/10 11:43
 * @Description:
 */
@Repository
public interface DBDaoOld {

    List<Map<String, Object>> select1(@Param("a") Map<String,String> auMap, @Param("b") Map<String,String> agMap);

    /** 是否渠道 */
    String selAgNat(Map<String, String> posReqMap);

    /** 查询流水是否存在 */
    Integer selSerial(@Param("isChannel") String isChannel, @Param("serial") String serial);

    /** 分润数据（小微） */
    Map<String, Object> selAuditdataXW(Map<String, String> posReqMap);

    /** 分润数据（非渠道） */
    Map<String, String> selAuditdata(Map<String, String> posReqMap);

    /** 分润数据（渠道） */
    Map<String, String> selChannelAuditdata(Map<String, String> posReqMap);

    /** 代理数据 */
    List<Map<String, String>> selAgdata(@Param("agent_num") String agent_num);

    /** 是否活动 */
    String isAct(@Param("localdate") String localdate, @Param("posno") String posno);

    /** 查询非渠道结算底价 */
    List<Map<String, String>> selPoldata(Map<String, String> audMap);

    /** 添加分润 */
    int insProData(@Param("auMap") Map<String,String> auMap, @Param("agMap") Map<String,String> agMap);

}
