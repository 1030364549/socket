package com.umf.Test;

import java.util.*;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/7/20 17:03
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList(){{
            add(new HashMap(){{
                put("NAME","hhhh");
                put("AGE","1");
                put("SEX","男");
            }});
        }};
        Map<String,String> map = new HashMap(){{
            put("nAme","hhhh");
            put("aGE","1");
            put("sEX","男");
            put("dDDDD","嘤嘤嘤");
        }};
//        for(Map<String,Object> map : list){
//            for (Iterator<Map.Entry<String,Object>> it = map.entrySet().iterator(); it.hasNext();){
//                Map.Entry<String, Object> next = it.next();
//                map.put(next.getKey().toLowerCase(),next.getValue()==null?"":next.getValue().toString());
////                it.remove();
//            }
//        }
        for(ListIterator<Map<String, Object>> it = list.listIterator(); it.hasNext();){
            Map<String, Object> next = it.next();
            Map<String, Object> map1 = new HashMap();
            next.forEach((k,v) -> map1.put(k.toLowerCase(),v==null?"":v.toString()));
            it.remove();
            it.add(map1);
        }

//        Map m = new HashMap();
//        map.forEach((k,v) -> m.put(k.toLowerCase(),v==null?"":v.toString()));
//        map = m;


        list.forEach(map1 -> map1.forEach((k,v) -> System.out.println(k+"="+v)));
//        map.forEach((k,v) -> System.out.println(k+"="+v));
    }


}
