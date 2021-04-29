package com.umf.utils.json;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Package com.umf.utils.json
 * @Author:LiuYuKun
 * @Date:2021/4/26 17:54
 * @Description:
 */
@Accessors(chain = true)
@Data
public class Activity {
    private String posno;
    private String sn;
    private String agentNum;
    private String agentName;
    private String moneyAct;
    private String moneyUp;
    private String taxMoney;
    private String auditdateAct;
    private String auditdateUp;
}
