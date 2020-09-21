package com.umf.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*将从数据库中查询的数据赋值给实体类的变量（映射实体类）*/
public class ResultSetUntil {
    public <T> List<T> getEntityList(ResultSet rs,Class<T> clazz) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        /*创建存储实体类的集合*/
        List<T> entityList = new ArrayList();
        /*创建存储rs结果集的集合*/
        List<Map<String,Object>> resultSetList = new ArrayList<>();
        /*判断结果集是否为空*/
        if(rs!=null){
            /*ResultSet中列的名称和类型的信息，可用于获取关于ResultSet对象中列的类型和属性信息的对象。*/
            ResultSetMetaData metaData = rs.getMetaData();
            /*获取所有字段的数量（列的数量）*/
            int columnCount = metaData.getColumnCount();
            /*一行一行遍历rs结果集*/
            while(rs.next()){
                /*创建Map集合存储每一行的数据*/
                Map<String,Object> map = new HashMap<>();
                /*遍历所有的字段（列）*/
                for(int i=0;i<columnCount;i++){
                    /*每一列的字段名*/
                    String columnName = metaData.getColumnName(i+1);
                    /*对应列名的字段值*/
                    Object value = rs.getObject(i+1);
                    /*将每一列的字段名（变成小写）和值装进map集合中*/
                    map.put(columnName.toLowerCase(),value);
                }
                /*将每一行的数据存入list集合中*/
                resultSetList.add(map);
            }

            /*创建存储实体类变量的集合*/
            List<String> fieldsNameList = new ArrayList<>();
            /*取出类中的所有的变量*/
            Field[] fields = clazz.getDeclaredFields();
            /*遍历数组，取出变量*/
            for(Field field:fields){
                /*将实体类中所有的变量存储到集合中*/
                fieldsNameList.add(field.getName());
            }

            /*遍历rs的结果集变量数组*/
            for(Map<String,Object> itemMap:resultSetList){
                /*每一个map对应着一个实体类*/
                T entity = clazz.newInstance();
                /*遍历map集合，获取key对应的set方法，存储值*/
                for(Map.Entry<String,Object> itemEntry : itemMap.entrySet()){
                    /*获取键*/
                    String key = itemEntry.getKey();
                    /*判断变量集合中是否有该字段名，也就是判断实体类是否有该属性*/
                    if(fieldsNameList.contains(key)){
                        /*获取值*/
                        Object value = itemEntry.getValue();
                        Method method = null;
                        /*判断属于那种类型*/
                        if(value instanceof String){
                            method = clazz.getMethod("set"+key.substring(0,1).toUpperCase()+key.substring(1),String.class);
                        }else if(value instanceof Integer){
                            method = clazz.getMethod("set"+key.substring(0,1).toUpperCase()+key.substring(1),Integer.class);
                        }
                        method.invoke(entity,value);
                    }
                }
                /*将一组map集合读出的实体类放入实体类集合中，也就是将一行的数据放入集合中*/
                entityList.add(entity);
            }
        }
        return entityList;
    }
}
