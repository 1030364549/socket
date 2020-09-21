package com.umf.dao.Impl;

import com.umf.dao.DBDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.umf.dao.Impl
 * @Author:LiuYuKun
 * @Date:2020/7/16 16:10
 * @Description:
 */
@SuppressWarnings("all")
@Repository
public class DBDaoImpl extends SqlSessionDaoSupport implements DBDao {

    @Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    /**
     * ************************************
     * 查询集合（多行）
     * @param id : mapper id
     * @param params : 参数
     * ************************************
     */
    @Override
    public List<Map<String, String>> getList(String id, Object params) {
//        return (List<Map<String, String>>) NewUtils.objToStr(this.getSqlSession().selectList(id,params));
        return this.getSqlSession().selectList(id,params);
    }

    /**
     * ************************************
     * 查询集合（多行）
     * @param id : mapper id
     * @param params : 参数
     * ************************************
     */
    @Override
    public List getObjectList(String id, Object params) {
//        return (List<Map<String, String>>) NewUtils.objToStr(this.getSqlSession().selectList(id,params));
        return this.getSqlSession().selectList(id,params);
    }

    /**
     * ************************************
     * 查询集合（单行）
     * @param id : mapper id
     * @param params : 参数
     * ************************************
     */
    @Override
    public Map<String, String> getMap(String id, Object params) {
//        return (Map<String, String>) NewUtils.objToStr(this.getSqlSession().selectMap(id,params));
        return this.getSqlSession().selectOne(id,params);
    }

    /**
     * ************************************
     * 查询数字
     * @param id : mapper id
     * @param params : 参数
     * ************************************
     */
    @Override
    public Integer getInteger(String id, Object params){
        return (Integer) this.getSqlSession().selectOne(id,params);
    }

    /**
     * ************************************
     * 查询字符
     * @param id : mapper id
     * @param params : 参数
     * ************************************
     */
    @Override
    public String getString(String id,Object params){
        return (String) this.getSqlSession().selectOne(id,params);
    }

    /**
     * ************************************
     * 修改
     * @param id : mapper id
     * @param params : 参数
     * ************************************
     */
    @Override
    public int update(String id,Object params){
        return this.getSqlSession().update(id, params);
    }

    /**
     * ************************************
     * 添加
     * @param id : mapper id
     * @param params : 参数
     * ************************************
     */
    @Override
    public int insert(String id,Object params){
        return this.getSqlSession().insert(id, params);
    }

    /**
     * ************************************
     * 删除
     * @param id : mapper id
     * @param params : 参数
     * ************************************
     */
    @Override
    public int delete(String id,Object params){
        return this.getSqlSession().delete(id,params);
    }
}
