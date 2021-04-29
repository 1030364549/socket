package com.umf.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package com.umf.utils.json
 * @Author:LiuYuKun
 * @Date:2021/4/26 17:52
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        List<Container> datas = JSON.parseArray("[{\"key\":\"activity\",\"list\":[{\"detail\":\"\",\"key\":\"posno\",\"name\":\"终端编号\"},{\"detail\":\"\",\"key\":\"sn\",\"name\":\"SN编号\"},{\"detail\":\"\",\"key\":\"agentNum\",\"name\":\"终端所属代理商编号\"},{\"detail\":\"\",\"key\":\"agentName\",\"name\":\"终端所属代理商名称\"},{\"detail\":\"\",\"key\":\"moneyAct\",\"name\":\"激活返现金额（元）\"},{\"detail\":\"\",\"key\":\"moneyUp\",\"name\":\"达标返现金额（元）\"},{\"detail\":\"\",\"key\":\"taxMoney\",\"name\":\"收益服务费金额（元）\"},{\"detail\":\"\",\"key\":\"auditdateAct\",\"name\":\"激活返现时间\"},{\"detail\":\"\",\"key\":\"auditdateUp\",\"name\":\"达标返现时间\"}],\"name\":\"活动奖励\"}]"
                , Container.class);
        List<Activity> activityList = new ArrayList(){{
            add(new Activity()
                    .setPosno("100")
                    .setSn("111")
                    .setAgentNum("100033008")
                    .setAgentName("刘宇昆")
                    .setMoneyAct("120")
                    .setTaxMoney("0")
                    .setAuditdateAct("2021-04-26"));

            add(new Activity().setPosno("200")
                    .setSn("222")
                    .setAgentNum("100033008")
                    .setAgentName("刘宇昆")
                    .setMoneyUp("18")
                    .setTaxMoney("2")
                    .setAuditdateUp("2021-04-26"));
        }};

        BusinessInfoResult bus = new BusinessInfoResult();
        bus.setActivity(activityList);
        String resultJson = JSONObject.toJSONString(bus);
        Map<String, Object> map = JSONObject.parseObject(resultJson, Map.class);
        List<Container> containers = reqString.JsongToString(datas, map);
        for (Container container : containers) {
            System.out.println(container.getKey());
            System.out.println(container.getName());
            List<ListChild> list = container.getList();
            for (ListChild listChild : list) {
                System.out.println("------------");
                System.out.println(listChild.getKey());
                System.out.println(listChild.getName());
                System.out.println(listChild.getValue());
                System.out.println(listChild.getMapping());
                System.out.println("------------");
            }
            System.out.println("------------------------------------------------------------");
        }
        String s = JSON.toJSONString(containers);
        System.out.println(s);
    }
}
