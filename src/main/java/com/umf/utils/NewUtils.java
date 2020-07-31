package com.umf.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @Package com.umf.utils
 * @Author:LiuYuKun
 * @Date:2020/7/15 16:59
 * @Description:
 */
@SuppressWarnings("all")
public class NewUtils {

    /**
     * 以代理商编号为key,数据为val
     *
     * @date 2020/7/30 11:23
     * @param [selPoldata]
     * @return java.util.Map<java.lang.String,java.util.Map<java.lang.String,java.lang.String>>
     */
    public static Map<String, Map<String,String>> selPoldata(List<Map<String,String>> selPoldata){
        Map<String, Map<String, String>> map = new HashMap();
        for(Map<String, String> row : selPoldata){
            map.put(row.get("agent_id").toUpperCase(),row);
        }
        return map;
    }

    /**
     * 参数校验
     *
     * @date 2020/7/30 11:23
     * @param [auMap, agMap]
     * @return void
     */
    public static void paramHand(Map<String, String> auMap, Map<String, String> agMap) throws Exception {
        if("1".equals(auMap.get("phonepay"))) {
            auMap.put("inputtypeNew","3");
        }
        auMap.put("ar_typeNew", "0".equals(auMap.get("ar_type")) ? auMap.get("sett_price") : auMap.get("ceiling_cost"));
        auMap.put("aff_typeNew", auMap.get("aff_type") == null ? "" : "0".equals(auMap.get("aff_type")) ? auMap.get("surcharge") : auMap.get("surcharge_ein"));
        String[] dateTime = DateUtil.getGroupDateTime();
        auMap.put("createdateNew", dateTime[0]);
        auMap.put("createtimeNew", dateTime[1]);
        auMap.put("createmanNew", "系统");
        String[] params = {"level_tax","below_tax","level_tax_profit","below_tax_profit", "profit_tax_diff","addlevel_tax", "addbelow_tax",
                "addlevel_tax_profit", "addbelow_tax_profit","addprofit_tax_diff","distribution_ratio","tj_profit","tj_profit_below"};
        StringBuilder sb ;
        for(String param : params){
            sb = new StringBuilder(param).append("New");
            auMap.put(sb.toString(),auMap.get(param)==null?"":auMap.get(param));
        }
        auMap.put("lower_agent_numAg",agMap.get("lower_agent_num"));
        auMap.put("lower_agent_nameAg",agMap.get("lower_agent_name"));
        auMap.put("agent_numAg",agMap.get("agent_num"));
        auMap.put("agent_nameAg",agMap.get("agent_name"));
        auMap.put("agent_levelAg",agMap.get("agent_level"));
        auMap.put("isgraduallyAg",agMap.get("isgradually"));

    }

    /**
     * 变量名小写,变量值类型转换
     *
     * @date 2020/7/30 11:25
     * @param [t]
     * @return java.lang.Object
     */
    public static <T> Object objToStr(T t){
        if(t != null){
            if(t instanceof List){
                List<Map<String, Object>> list = (List<Map<String, Object>>) t;
                for(ListIterator<Map<String, Object>> it = list.listIterator(); it.hasNext();){
                    Map<String, Object> map = it.next();
                    Map<String, Object> newMap = new HashMap();
                    map.forEach((k,v) -> newMap.put(k.toLowerCase(),v==null?"":v.toString()));
                    it.remove();
                    it.add(newMap);
                }
            }
            if(t instanceof Map){
                Map<String, Object> map = (Map<String, Object>) t;
                Map<String, Object> newMap = new HashMap();
                map.forEach((k,v) -> newMap.put(k.toLowerCase(),v==null?"":v.toString()));
                map = newMap;
            }
        }
        return t;
    }

    /**
     * 添加到集合中
     *
     * @date 2020/7/30 11:25
     * @param [objects]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String,Object> add(Object... objects){
        Object[] object = objects;
        Map<String,Object> map = new HashMap();
        for(int i=0;i<object.length;i=i+2){
            map.put(object[i].toString(),object[i+1]);
        }
        return map;
    }

    /**
     * 判断集合泛型类型
     *
     * @date 2020/7/30 14:09
     * @param [list, clazz]
     * @return boolean
     */
    public static boolean judgeType(List list, Class clazz) throws IllegalAccessException, InstantiationException {
        boolean flag = false;
        for(Object obj : list){
            if(obj != null && !"".equals(obj) ){
                flag = obj.getClass() == clazz;
                break;
            }
        }
        return flag;
    }


}
