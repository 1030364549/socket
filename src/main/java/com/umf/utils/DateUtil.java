package com.umf.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private LoFunction lo = new LoFunction();
    /**
     *
     *********************************************************.<br>
     * [方法] getCurrentDateTime <br>
     * [描述] 获取系统时间<br>
     * [参数] 格式<br>
     * [返回] String <br>
     * [作者] UMF
     * [时间] 2019年8月13日 下午4:44:49 <br>
     *********************************************************.<br>
     */
    public static String getCurrentDateTime(String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String strDate = sdf.format(new Date());
        return strDate;
    }

    /**
     *
     *********************************************************.<br>
     * [方法] toStrDate <br>
     * [描述] 将时间、日期拼接成yyyy-MM-dd hh:mm:ss格式 <br>
     * [参数] 时间、日期<br>
     * [返回] String <br>
     * [作者] UMF
     * [时间] 2019年8月13日 下午4:46:08 <br>
     *********************************************************.<br>
     */
    public String toStrDate(String time, String date) throws Exception {
        StringBuffer dateStr = new StringBuffer();
        dateStr.append(getCurrentDateTime("yyyy"));
        dateStr.append("-");
        dateStr.append(date.substring(0, 2));
        dateStr.append("-");
        dateStr.append(date.substring(2));
        dateStr.append(" ");
        dateStr.append(time.substring(0, 2));
        dateStr.append(":");
        dateStr.append(time.substring(2, 4));
        dateStr.append(":");
        dateStr.append(time.substring(4));
        return dateStr.toString();
    }

    /**
     *
     *********************************************************.<br>
     * [方法] MMDD <br>
     * [描述] 将yyyy-MM-dd转换成mmdd<br>
     * [参数] 时间<br>
     * [返回] String <br>
     * [作者] UMF
     * [时间] 2019年8月13日 下午4:48:22 <br>
     *********************************************************.<br>
     */
    public String MMDD(String date) throws Exception {
        String[] dates = date.split("-");
        return (lo.appendField(dates[1], dates[2]));
    }

    /**
     *
     *********************************************************.<br>
     * [方法] HHMMSS <br>
     * [描述] 将hh：mm：ss转换成hhmmss<br>
     * [参数] 时间<br>
     * [返回] String <br>
     * [作者] UMF
     * [时间] 2019年8月13日 下午4:48:45 <br>
     *********************************************************.<br>
     */
    public String HHMMSS(String time) throws Exception {
        String[] times = time.split(":");
        return (lo.appendField(times[0], times[1], times[2]));
    }

    /**
     *
     *********************************************************.<br>
     * [方法] getGroupDateTime <br>
     * [描述] 获取当前yyy-MM-dd,HH:mm:ss<br>
     * [参数] <br>
     * [返回] String[] <br>
     * [作者] UMF
     * [时间] 2019年8月13日 下午4:49:06 <br>
     *********************************************************.<br>
     */
    public static String[] getGroupDateTime() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd|HH:mm:ss");
        String strDate = sdf.format(new Date());
        return strDate.split("\\|");
    }

    /**
     *
     *********************************************************.<br>
     * [方法] getLocaldate <br>
     * [描述] 根据当前时间减去多少天（日期,减去的天数）<br>
     * [参数] 时间,天数<br>
     * [返回] String <br>
     * [作者] UMF
     * [时间] 2019年8月13日 下午4:49:27 <br>
     *********************************************************.<br>
     */
    public static String getLocaldate(String localdate,int dates) throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=sdf.parse(localdate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, dates);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     *
     *********************************************************.<br>
     * [方法] getbooleanDay <br>
     * [描述] 当前时间是否在有效时间内<br>
     * [参数] 时间<br>
     * [返回] boolean <br>
     * [作者] UMF
     * [时间] 2019年8月13日 下午4:49:50 <br>
     *********************************************************.<br>
     */
    public static boolean getbooleanDay(String date) throws Exception {
        DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        Date vip_format = date_format.parse(date);
        String xitong_date = date_format.format(new Date());
        Date xitong_format = date_format.parse(xitong_date);
        if(vip_format.getTime() >=  xitong_format.getTime()) {
            return true;
        }
        return false;
    }

    /** 获得对应格式的时间 */
    public static String formateDate(int style){
        String date="";
        String type="yyyyMMdd";
        switch (style) {
            case 0:
                type="yyyy-MM-dd";
                break;
            case 1:
                type="yyyyMMddHHmmss";
                break;
            case 2:
                type="yyyy-MM-dd HH:mm:ss";
                break;
            case 3:
                type="HH:mm:ss";
                break;
            case 4:
                type="yyyyMMddHHmmssSSS";
                break;
            case 5:
                type="yyyy";
                break;
            case 6:
                type="yyyyMMdd";
                break;
            case 7:
                type="yyyyMMddHHmm";
                break;
            case 8:
                type="MMdd";
                break;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(type);
        date=sdf.format(new Date());
        return date;
    }

    /**
     * 两个日期相隔的天数，date2比date1多的天数
     *
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static Integer diffDays(String dateStr1, String dateStr2) {
        Date date1 = StringToDate(dateStr1, "yyyy-MM-dd HH:mm:ss");
        Date date2 = new Date();
        if(dateStr2 != null && !"".equals(dateStr2)){
            date2 = StringToDate(dateStr2, "yyyy-MM-dd HH:mm:ss");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        // 不同年
        if(year1 != year2) {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++) {
                if(i%4==0 && i%100!=0 || i%400==0) {   //闰年
                    timeDistance += 366;
                } else {   // 不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        } else {  // 同年
            return day2-day1;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static int diffDaysByMill(String dateStr1,String dateStr2) {
        Date date1 = StringToDate(dateStr1, "yyyy-MM-dd HH:mm:ss");
        Date date2 = new Date();
        if(dateStr2 != null && !"".equals(dateStr2)){
            date2 = StringToDate(dateStr2, "yyyy-MM-dd HH:mm:ss");
        }
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 字符串转换到日期格式
     *
     * @param dateStr 需要转换的字符串
     * @param formatStr 需要格式的目标字符串  举例 yyyyMMdd
     * @return Date 返回转换后的时间
     * @throws ParseException 转换异常
     */
    public static Date StringToDate(String dateStr,String formatStr){
        DateFormat sdf=new SimpleDateFormat(formatStr);
        Date date=null;
        try {
            date = new Date(sdf.parse(dateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
