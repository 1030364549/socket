package com.umf.Test;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/9/21 16:58
 * @Description:
 */
public class Test6stream {
    public static void main(String[] args) {
//        HashMap<Object, Object> map = Maps.newHashMap();
//        ArrayList<Object> list = Lists.newArrayList();
        List<Student> list = new ArrayList(){{
            add(new Student().setName("hhh").setAge("10").setSex("男"));
            add(new Student().setName("ggg").setAge("11").setSex("男"));
            add(new Student().setName("yyy").setAge("12").setSex("女"));
        }};
        // 将集合中所有的名字转换为集合
        getName(list);
        System.out.println("-----------------------------------------------------------------");
        // 将集合中所有的数据以,分割，并且转换为大写
        collectingAndThenTest(list);
        System.out.println("-----------------------------------------------------------------");
        List<Map> list1 = new ArrayList(){{
            add(new HashMap(){{put("name","hhh");put("age","10");put("sex","男");}});
            add(new HashMap(){{put("name","ggg");put("age","11");put("sex","男");}});
            add(new HashMap(){{put("name","yyy");put("age","12");put("sex","女");}});
        }};
        // 将集合中所有map集合中的name转换为大写并提取到集合中
        name(list1);
        System.out.println("-----------------------------------------------------------------");
        List<String> list2 = new ArrayList(){{
            add("123");add("456");add("789");add("qwer");add("asdf");add("zxcv");add("hhhhhhh");add("gggggggg");add("yyyyyyyyy"); }};
        // groupingBy 分组，将集合中所有数据根据字符串长度进行分组，生成集合默认为Map，内部集合默认为List
        groupingByTest(list2);
        // 根据条件将集合分割为符合条件和不符合条件的两个集合
        partitioningByTest(list2);
    }

    /**
     * 将集合中所有的名字转换为集合
     *
     * @date 2020/9/21 18:04
     * @param list
     * @return void
     */
    public static void getName(List<Student> list){
        List<String> collect = list.stream().map(Student::getName).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 将集合中所有的数据以,分割，并且转换为大写，字符串
     *
     * @date 2020/9/21 18:04
     * @param list
     * @return void
     */
    public static void collectingAndThenTest(List<Student> list){
        String collect1 = list.stream().map(Student::getName).collect(Collectors.collectingAndThen(Collectors.joining(","), String::toUpperCase));
        System.out.println(collect1);
    }

    /**
     * 将集合中所有map集合中的name转换为大写并提取到集合中
     *
     * @date 2020/9/21 18:04
     * @param list
     * @return void
     */
    public static void name(List<Map> list){
        List<String> name = list.stream().map(map -> map.get("name").toString().toUpperCase()).collect(Collectors.toList());
        System.out.println(name);
    }

    /**
     * groupingBy 分组，将集合中所有数据根据字符串长度进行分组，生成集合默认为Map，内部集合默认为List
     *
     * @date 2020/9/21 18:04
     * @param list
     * @return void
     */
    public static void groupingByTest(List<String> list){
        Map<Integer,List<String>> lengthList = list.stream().collect(Collectors.groupingBy(String::length));
        Map<Integer,Set<String>> lengthList1 = list.stream().collect(Collectors.groupingBy(String::length,Collectors.toSet()));
        LinkedHashMap<Integer,Set<String>> lengthList2 = list.stream().collect(Collectors.groupingBy(String::length,LinkedHashMap::new,Collectors.toSet()));
        System.out.println(lengthList);
        System.out.println(lengthList1);
        System.out.println(lengthList2);
    }

    /**
     * 根据条件将集合分割为符合条件和不符合条件的两个集合
     * {false=[], true=[]}
     *
     * @date 2020/9/21 18:04
     * @param list
     * @return void
     */
    public static void partitioningByTest(List<String> list){
        Map<Boolean,List<String>> map = list.stream().collect(Collectors.partitioningBy(e -> e.length() > 5));
        Map<Boolean,Set<String>> map2 = list.stream().collect(Collectors.partitioningBy(e -> e.length() > 7,Collectors.toSet()));
        System.out.println(map);
        System.out.println(map2);
    }
}
