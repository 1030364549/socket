package com.umf.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class reqString {

    /**
     * 将数据添加到json格式的模板中
     *
     * @date 2020/11/17 17:08
     * @param datas json字符串转换成的List集合
     * @param map   填入集合中的数据
     * @return
     */
    public static List<Container> JsongToString(List<Container> datas,Map<String, Object> map){
        List<Container> list = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            // 获取当前模板的list数据
            List<ListChild> businessList = datas.get(i).getList();
            for (String key : map.keySet()) {
                if (datas.get(i).getKey().equals(key)) {
                    // 将当前数据转换为Json字符串再转换为Map集合
                    List<Map<String, String>> childList = JSONObject.parseObject(JSONObject.toJSONString(map.get(key)), List.class);
                    for (Map<String, String> childMap : childList) {
                        // 遍历list集合中的字段数据
                        for (int j = 0; j < businessList.size(); j++) {
                            ListChild child = businessList.get(j);
                            // 数据中不存在模板的数据
                            if(!childMap.containsKey(child.getKey())){
                                // 删除模板的当前数据，且下标减一，否则不会遍历下条数据
                                businessList.remove(child);
                                j--;
                                // 当mapping存在数据时
                                /*"mapping": {
                                       "0": "标准类",
                                       "1": "优惠类",
                                       "2": "减免类"
                                }*/
                            } else if (child.getMapping() != null && child.getMapping().size() > 0) {
                                child.setValue(String.valueOf(childMap.get(child.getKey())));
                                // 当mapping中存在当前value时，将mapping中的value取出替换存储的value，替换对应的数据
                                if (child.getMapping().get(child.getValue()) != null
                                        && !child.getMapping().get(child.getValue()).equals("") && !child.getMapping().get(child.getValue()).equals("null")) {
                                    child.setValue(String.valueOf(child.getMapping().get(child.getValue())));
                                }
                                // 正常情况
                            } else {
                                // 数据中不存在模板的数据或者为空时
                                if (childMap.get(child.getKey()) == null || "".equals(childMap.get(child.getKey())) || "null".equals(childMap.get(child.getKey()))) {
                                    // 删除数据和模板的当前数据，更新下标
//                                    childMap.remove(child.getKey());
                                    businessList.remove(child);
                                    j--;
                                } else {
                                    // 添加对应数据
                                    child.setValue(String.valueOf(childMap.get(child.getKey())));
                                }
                            }
                        }
                        List<ListChild> li = new ArrayList<>();
                        for (ListChild listChild : businessList) {
                            ListChild l = new ListChild();
                            l.setKey(listChild.getKey());
                            l.setName(listChild.getName());
                            l.setValue(listChild.getValue());
                            l.setMapping(listChild.getMapping());
                            li.add(l);
//                            System.out.println("~~~~~~~~~~~~~~");
//                            System.out.println(listChild.getKey());
//                            System.out.println(listChild.getName());
//                            System.out.println(listChild.getValue());
//                            System.out.println("~~~~~~~~~~~~~~");
                        }
                        Container container = new Container();
                        container.setKey(datas.get(i).getKey());
                        container.setName(datas.get(i).getName());
                        container.setList(li);
//                        Container container = datas.get(i);
//                        container.setList(li);
                        list.add(container);
//                        System.out.println("结束");
                    }
                }
            }
        }
        return list;
    }
//    /**
//     * @Author: dongjb
//     * @Date: 2019/6/8 0:07
//     * @Description:   获取交易信息列表
//     * @Version: 1.0
//     */
//    public static List<Container> jsonToAgentInfo(String json, AgentInfo agentInfo, Accounts accounts){
//        try {
//            List<Container> datas = JSON.parseArray(json, Container.class);
//            BusinessInfoResult businessInfoResult = new BusinessInfoResult();
//            businessInfoResult.setAgentInfo(agentInfo);
//            businessInfoResult.setAccounts(accounts);
//            String resultJson = JSONObject.toJSONString(businessInfoResult);
//            Map<String, Object> map = JSONObject.parseObject(resultJson, Map.class);
//            return JsongToString(datas,map);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
