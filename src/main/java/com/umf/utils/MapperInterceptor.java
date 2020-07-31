package com.umf.utils;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("all")
@Intercepts({ @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class MapperInterceptor implements Interceptor {

    @Autowired
    public LogUtil log;

    private String newLine = System.getProperty("line.separator");

    public Object intercept(Invocation invocation) throws InstantiationException, IllegalAccessException {
        /** 打印sql语句 */
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        // 原始sql语句
//        String oldsql = boundSql.getSql();
        // 获取节点的配置
        Configuration configuration = mappedStatement.getConfiguration();
        // 最终的sql语句
        String newsql = showSql(configuration, boundSql);
        StringBuilder sb = new StringBuilder("=======================================================================================================================================================").append(newLine)
                .append(mappedStatement.getId()).append(" : ").append(newLine)
                .append(parameter).append(newLine)
                .append(newsql).append(newLine)
                .append("=======================================================================================================================================================");
        System.out.println(sb);
        /** 变量名小写,变量值类型转换 */
        Object result = null;
        try {
            result = invocation.proceed();
            // 获取类型
            List<ResultMap> rms = mappedStatement.getResultMaps();
            ResultMap rm = rms != null && rms.size() > 0 ? rms.get(0) : null;
            Class<?> type = rm != null ? rm.getType() : null;
//            String typeName = rm != null && rm.getType() != null ? rm.getType().getSimpleName() : "";   // 简称
            // 校验并修改结果集
            if(result != null && !"".equals(result)){
                if(type == Map.class){
                    if(result instanceof List){
                        List<Map<String, Object>> list = (List<Map<String, Object>>) result;
                        for(ListIterator<Map<String, Object>> it = list.listIterator(); it.hasNext();){
                            Map<String, Object> map = it.next();
                            Map<String, Object> newMap = new HashMap();
                            map.forEach((k,v) -> newMap.put(k.toLowerCase(),v==null?"":v.toString()));
                            it.remove();
                            it.add(newMap);
                        }
                    }
                    if(result instanceof Map){
                        Map<String, Object> map = (Map<String, Object>) result;
                        Map<String, Object> newMap = new HashMap();
                        map.forEach((k,v) -> newMap.put(k.toLowerCase(),v==null?"":v.toString()));
                        map = newMap;
                    }
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            log.errorE(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.errorE(e);
        }

        return result;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }

    /**
     * 进行 ? 的替换
     *
     * @date 2020/7/30 11:26
     * @param [configuration, boundSql]
     * @return java.lang.String
     */
    public String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换　　　　　　　
            // 如果根据parameterObject.getClass()可以找到对应的类型，则替换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，
                // 提供了get和set的方法用于获取和设置originalObject的属性值,
                // 主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

    /**
     * String类型添加单引号，日期类型则转换为时间格式并加单引号；对参数是否为null的情况做处理
     *
     * @date 2020/7/30 11:36
     * @param [obj]
     * @return java.lang.String
     */
    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

}