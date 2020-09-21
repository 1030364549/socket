package com.umf.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.umf.dao
 * @Author:LiuYuKun
 * @Date:2020/7/16 15:57
 * @Description:
 */
@SuppressWarnings("all")
@Repository
public interface DBDao {

    List<Map<String,String>> getList(String id, Object params);

    public List getObjectList(String id, Object params);

    Map<String, String> getMap(String id, Object params);

    Integer getInteger(String id, Object params);

    String getString(String id,Object params);

    int update(String id,Object params);

    int insert(String id,Object params);

    int delete(String id,Object params);

}
