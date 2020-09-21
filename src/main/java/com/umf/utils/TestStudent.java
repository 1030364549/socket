package com.umf.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @Package com.yh.controller
 * @Author:LiuYuKun
 * @Date:2020/1/9 9:20
 * @Description:
 */
@Data
/*给类附加equals和hashCode方法，callSuper值为false时，不调用父类的属性，也就是当创建两
个类并设置父类的属性并且不一样的时候也无关，.equals()结果是相同的，默认为false*/
/*@EqualsAndHashCode(callSuper = true)*/
public class TestStudent extends BaseRowModel {

    @ExcelProperty(index = 0)
    private int studentId;
    @ExcelProperty(index = 1)
    private String studentName;
    @ExcelProperty(index = 2)
    private String studentSex;
    @ExcelProperty(index = 3)
    private String studentAge;

//    @ExcelProperty(index = 4)
//    /*标记在时间类型上，可设置时间的格式（在excel中显示的格式）*/
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private String birth;

    public TestStudent() {

    }
    public TestStudent(int studentId, String studentName, String studentSex, String studentAge) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentSex = studentSex;
        this.studentAge = studentAge;
    }
}
