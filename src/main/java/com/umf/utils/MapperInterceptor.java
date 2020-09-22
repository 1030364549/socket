package com.umf.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;

import cn.jiguang.common.utils.StringUtils;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


//@Component
//@DependsOn({"springUtil"})    /*需要依赖的spring类*/
@SuppressWarnings("all")
@Intercepts({ @Signature(type = Executor.class, method = "query",
                         args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class MapperInterceptor implements Interceptor {

//    @Autowired
    public LogUtil log = new LogUtil();/*= SpringUtil.getBean(LogUtil.class);*/

    public Object intercept(Invocation invocation) throws Exception {
        /** 打印sql语句 */
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        // 原始sql语句
//        String oldsql = boundSql.getSql();
        // 获取节点的配置
        Configuration configuration = mappedStatement.getConfiguration();
        /** 最终的sql语句 */
        String sql = showSql(configuration, boundSql);
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("调用方法："+mappedStatement.getId());
        System.out.println("传入参数："+splicingVal(parameter));
        System.out.println("完整参数："+parameter);
        System.out.println("sql语句："+"\n"+sql);
        System.out.println("--------------------------------------------------------------------------------------------");
        log.infoSql(mappedStatement.getId(),parameter,sql);
        Object result = null;
        try {
            /** 变量名小写,变量值类型转换 */
            result = invocation.proceed();
            changeType(result, mappedStatement);
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
     * @date 2020/8/18 15:21
     * @param configuration
     * @param boundSql
     * @return java.lang.String
     */
    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();                     // 实际参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings(); // 所需参数
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!CollectionUtils.isEmpty(parameterMappings) && !ObjectUtils.isEmpty(parameterObject)) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            // 判断当前参数是否为一个简单类型或是一个注册了typeHandler、可根据getClass()找到对应对象类型的参数，直接替换占位符
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,
                // 主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql，foreach会生成额外动态参数需要特殊取值
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
     * @date 2020/8/18 15:22
     * @param obj
     * @return java.lang.String
     */
    private String getParameterValue(Object obj) {
//        String value = null;
//        if (obj instanceof String) {
//            value = "'" + obj.toString() + "'";
//        } else if (obj instanceof Date) {
//            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
//            value = "'" + formatter.format(obj) + "'";
//        } else {
//            if (obj != null) {
//                value = obj.toString();
//            } else {
//                value = "";
//            }
//        }
        return ObjectUtils.isEmpty(obj) ? "''" : "'" + obj.toString() + "'";
    }


    /**
     * 获取实体类存在值的变量
     *
     * @date 2020/9/1 16:44
     * @param parameter
     * @return java.lang.String
     */
    public String splicingVal(Object parameter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Class clazz = parameter.getClass();
        if (clazz.getName().contains("entity.business")){   // 实体类路径
            if(clazz != null){
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields){
                    String name = field.getName();
                    Method method = clazz.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                    Object value = method.invoke(parameter);
                    if(value != null){
                        sb.append(name).append(" = \"").append(value).append("\"").append(",");
                    }
                }
            }
            sb.deleteCharAt(sb.length()-1);
        }else if(parameter instanceof Map){     // Map集合
            Map<String,String> parameter1 = (Map) parameter;
            for(Map.Entry<String,String> entry : parameter1.entrySet()){
                if(entry.getValue() != null){
                    sb.append(entry.getKey()).append(" = \"").append(entry.getValue()).append("\"").append(",");
                }
            }
            sb.deleteCharAt(sb.length()-1);
        }else {     // 其他数据类型
            sb.append(parameter.toString());
        }
        return sb.toString();
    }

    /**
     * 变量名小写,变量值类型转换
     *
     * @date 2020/9/21 15:03
     * @param result
     * @param mappedStatement
     * @return void
     */
    public void changeType(Object result, MappedStatement mappedStatement) {
        // 获取mapper标签的resultType，返现类型
        List<ResultMap> rms = mappedStatement.getResultMaps();
        ResultMap rm = CollectionUtils.isEmpty(rms) ? null : rms.get(0);
        Class<?> type = rm == null ? null : rm.getType();
//            String typeName = rm != null && rm.getType() != null ? rm.getType().getSimpleName() : "";   // 简称
        // 判断返现类型是否为map，根据结果集类型修改（否则即使查询结果为字符串整数，结果集仍然为list集合）
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
    }

}