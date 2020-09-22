package com.umf.Test;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Package com.umf.Test
 * @Author:LiuYuKun
 * @Date:2020/9/21 16:57
 * @Description:
 */
@Data
@Accessors(chain = true)  // set方法添加返回值（自身类），可并列添加数据    Student student = new Student().setName("name").setSex(1);
public class Student {
    private String name;
    private String age;
    private String sex;

}
