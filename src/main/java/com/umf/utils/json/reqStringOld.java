package com.umf.utils.json;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;

public class reqStringOld {

    public static List<Container> JsongToString(List<Container> datas, Map<String, Object> map) {

        for (int i = 0; i < datas.size(); i++) {
            List<ListChild> businessList = datas.get(i).getList();
            for (String key : map.keySet()) {
                if (datas.get(i).getKey().equals(key)) {
                    Map<String, String> childMap = JSONObject.parseObject(JSONObject.toJSONString(map.get(key)), Map.class);
                    for (int j = 0; j < businessList.size(); j++) {
                        ListChild child = businessList.get(j);
                        if (!childMap.containsKey(child.getKey())) {
                            businessList.remove(child);
                            j--;
                        } else if (child.getMapping() != null && child.getMapping().size() > 0) {
                            child.setValue(String.valueOf(childMap.get(child.getKey())));
                            if (child.getMapping().get(child.getValue()) != null
                                    && !child.getMapping().get(child.getValue()).equals("") && !child.getMapping().get(child.getValue()).equals("null")) {
                                child.setValue(String.valueOf(child.getMapping().get(child.getValue())));
                            }
                        } else {
                            if (childMap.get(child.getKey()) == null || "".equals(childMap.get(child.getKey())) || "null".equals(childMap.get(child.getKey()))) {
                                childMap.remove(child.getKey());
                                businessList.remove(child);
                                j--;
                            } else {
                                child.setValue(String.valueOf(childMap.get(child.getKey())));
                            }
                        }
                    }
                }
            }
        }
        return datas;
    }
}