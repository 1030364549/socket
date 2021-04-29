package com.umf.utils;

import lombok.SneakyThrows;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangzw
 * @Description: 日期格式工具
 * @Version: 1.0
 * @Date: 2017/5/9 16:46
 */
public class DateUtils {

    public final static String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public final static String FORMAT_YYYYMMDD = "yyyyMMdd";
    public final static String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
    public final static String FORMAT_YYYYMMDD_STR = "yyyy年MM月dd日";
    public final static String FORMAT_YYYY_MM = "yyyy-MM";
    public final static String FORMATYYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String FORMAT_HH_MM_SS = "HH:mm:ss";
    public final static String FORMAT_HHMMSS = "HHmmss";
    public final static String FORMAT_FULL = "HH:mm:ss.S";
    public final static String FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * @Author: wangzw
     * @Description: 获取昨天日期
     * @Version: 1.0
     * @Date: 2017/2/17 11:38
     */
    public static Date getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    /**
     * Date类型转换为10位时间戳
     *
     * @author: wangzw
     * @version: 1.0
     * @date: 2018/7/25 17:49
     */
    public static String dateToTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return new SimpleDateFormat(DateUtils.FORMAT_YYYYMMDDHHMM).format(cal.getTime());
    }


    /**
     * @Author: wangzw
     * @Description: 获取系统当前时间
     * @Version: 1.0
     * @Date: 2017/2/17 11:41
     */
    public static String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMATYYYYMMDDHHMMSS);
        return simpleDateFormat.format(new Date());
    }

    /**
     * @Author: wangzw
     * @Description: 获取昨天日期
     * @Version: 1.0
     * @Date: 2017/2/17 11:39
     */
    public static String getYesterdayStr() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD).format(cal.getTime());
    }

    /**
     * @Author: wangzw
     * @Description: 获取昨天日期 - 格式YYYYMMDD
     * @Version: 1.0
     * @Date: 2017/2/17 11:39
     */
    public static String getYesterdayStrYdm() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(DateUtils.FORMAT_YYYYMMDD).format(cal.getTime());
    }

    /**
     * @Author: wangzw
     * @Description: 获取明天日期
     * @Version: 1.0
     * @Date: 2017/2/17 11:41
     */
    public static String getTomorrowStr() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD).format(cal.getTime());
    }

    public static String getTomorrowYYYYMMDD() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return new SimpleDateFormat(DateUtils.FORMAT_YYYYMMDD).format(cal.getTime());
    }

    /**
     * @Author: wangzw
     * @Description: 获取系统当前时间
     * @Version: 1.0
     * @Date: 2017/2/17 11:41
     */
    public static String getYmdHsmS() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
        return simpleDateFormat.format(new Date());
    }

    /**
     * @Author: wangzw
     * @Description: 获取系统当前时间
     * @Version: 1.0
     * @Date: 2017/2/17 11:41
     */
    public static String getSysCurDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
        return simpleDateFormat.format(new Date());
    }
    /**
     * @Author: wangzw
     * @Description: 格式化时间
     * @Version: 1.0
     * @Date: 2017/2/17 11:42
     */
    public static String formatDateToStr(String format, Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * @Author: wangzw
     * @Description: 获取系统当前时间数字形式
     * @Version: 1.0
     * @Date: 2017/2/17 11:42
     */
    public static String getNumberDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYYMMDDHHMMSS);
        return simpleDateFormat.format(new Date());
    }

    /**
     * @Author: wangzw
     * @Description: 日期string转Date
     * @Version: 1.0
     * @Date: 2017/2/20 17:16
     */
    public static Date getDateFromStr(String time) {
        try {
            SimpleDateFormat simpleDateFormat = null;
            if (time.length() > 10) {
                simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
            } else {
                simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD);
            }
            return simpleDateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Author: wangzw
     * @Description: 获取系统当前日期, YYYY_MM_DD格式
     * @Version: 1.0
     * @Date: 2017/2/17 11:43
     */
    public static String getSysCurFmtDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD);
        return simpleDateFormat.format(new Date());
    }

    /**
     * @Author: dongjb
     * @Date: 2019/5/14 13:51
     * @Description:   获取系统当前时间, HH_MM_SS格式
     * @Version: 1.0
     */
    public static String getSysHhMmSs() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_HH_MM_SS);
        return simpleDateFormat.format(new Date());
    }

    /**
     * @Author: dongjb
     * @Date: 2019/5/14 13:51
     * @Description:   获取系统当前时间, HH_MM_SSs格式
     * @Version: 1.0
     */
    public static String getSysHhMmSsS() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_FULL);
        return simpleDateFormat.format(new Date());
    }

    public static String getSysHMS() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_HHMMSS);
        return simpleDateFormat.format(new Date());
    }

    /**
     * @Author: dongjb
     * @Date: 2018/6/28 17:08
     * @Description: 获取系统当前年_月
     * @Version: 1.0
     */
    public static String getCurrentMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM);
        return simpleDateFormat.format(new Date());
    }

    /**
     * @Author: wangzw
     * @Description: 获取YYYYMMDD格式
     * @Version: 1.0
     * @Date: 2017/2/17 11:55
     */
    public static String getDateFormatS(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYYMMDD);
        return simpleDateFormat.format(date);
    }

    /**
     * @Author: wangzw
     * @Description: 获取YYYY-MM-DD HH:mm:ss格式
     * @Version: 1.0
     * @Date: 2017/2/17 11:55
     */
    public static String getDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS);
        return simpleDateFormat.format(date);
    }

    /**
     * @Author: wangzw
     * @Description: 获取YYYY年MM月DD日格式
     * @Version: 1.0
     * @Date: 2017/2/17 11:55
     */
    public static String getFmtChineseDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYYMMDD_STR);
        return simpleDateFormat.format(date);
    }

    /**
     * @Author: wangzw
     * @Description: 获取YYYY-MM-DD格式
     * @Version: 1.0
     * @Date: 2017/2/17 11:47
     */
    public static String getFmtDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD);
        return simpleDateFormat.format(date);
    }

    /**
     * @Author: wangzw
     * @Description: 获取YYYY-MM-DD格式
     * @Version: 1.0
     * @Date: 2017/2/17 11:47
     */
    public static String getFmtDateMM(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM);
        return simpleDateFormat.format(date);
    }

    /**
     * @Author: wangzw
     * @Description: 日期加减几个月
     * @Version: 1.0
     * @Date: 2017/2/17 11:56
     */
    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }




    /**
     * @Author: wangzw
     * @Description: 日期加减几天
     * @Version: 1.0
     * @Date: 2017/2/17 13:11
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }


    /**
     * @Author: wangzw
     * @Description: 得到两个日期之间的所有日期，逗号隔开
     * @Version: 1.0
     * @Date: 2017/2/21 10:29
     */
    public static String getStrBetweenDates(String startTime, String endTime) {
        StringBuilder stringBuilder = new StringBuilder();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(DateUtils.getDateFromStr(startTime));
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(DateUtils.getDateFromStr(endTime));
        stringBuilder.append(getDateFormatS(getDateFromStr(startTime))).append(",");
        while (tempStart.before(tempEnd)) {
            stringBuilder.append(DateUtils.getDateFormatS(tempStart.getTime()));
            stringBuilder.append(",");
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        stringBuilder.append(getDateFormatS(getDateFromStr(endTime)));
        return stringBuilder.toString();
    }

    /**
     * @Author: wangzw
     * @Description: 得到两个日期之间的所有日期
     * @Version: 1.0
     * @Date: 2017/2/21 10:29
     */
    public static List<String> getListBetweenDates(String startTime, String endTime) {
        List<String> dateList = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(DateUtils.getDateFromStr(startTime));
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(DateUtils.getDateFromStr(endTime));
        dateList.add(getDateFormatS(getDateFromStr(startTime)));
        while (tempStart.before(tempEnd)) {
            dateList.add(DateUtils.getDateFormatS(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        dateList.add(getDateFormatS(getDateFromStr(endTime)));
        return dateList;
    }

    /**
     * @Author: wangzw
     * @Description: 得到下月的第一天
     * @Version: 1.0
     * @Date: 2017/6/22 17:23
     */
    public static String getFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        return getFmtDate(date);
    }

    /**
     * @Author: wangzw
     * @Description: 得到下月的最后一天
     * @Version: 1.0
     * @Date: 2017/6/22 17:23
     */
    public static String getLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        return getFmtDate(date);
    }

    /**
     * @Author: dongjb
     * @Date: 2018/6/27 20:11
     * @Description: 获取当天是多少号
     * @Version: 1.0
     */
    public static String getDay() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        return String.valueOf(day);
    }

    public static int daysBetween(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(startDate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(endDate));
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算当前时间距离今日凌晨的秒数
     *
     * @author: wangzw
     * @version: 1.0
     * @date: 2018/4/11 14:56
     */
    public static long getSecondsLeftToday() {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        return TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).toNanos());
    }

    /**
     * @Author: dongjb
     * @Date: 2018/7/14 15:28
     * @Description: 判断时间格式大小（yyyy_MM）
     * @Version: 1.0
     */
    public static Long getYearMonth(String t1, String t2) throws Exception {//传入日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM");
        Date d1 = sdf.parse(t1);
        Date d2 = sdf.parse(t2);
        return d1.getTime() - d2.getTime();
    }

    /**
     * @Author: dongjb
     * @Date: 2018/7/14 15:28
     * @Description: 判断时间格式大小（yyyy-MM-dd）
     * @Version: 1.0
     */
    public static Long getYearMonthDay(String t1, String t2) throws Exception {//传入日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse(t1);
        Date d2 = sdf.parse(t2);
        return d1.getTime() - d2.getTime();
    }

    /**
     * @Author: dongjb
     * @Date: 2018/9/6 19:09
     * @Description: 入参是date类型的比较大小
     * @Version: 1.0
     */
    public static int daysBetween(Date d1, Date d2) {
        return daysBetween(getFmtDate(d1), getFmtDate(d2));
    }

    /**
     * @Author: dongjb
     * @Date: 2019/6/20 16:16
     * @Description:   获取本周的开始时间
     * @Version: 1.0
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    /**
     * @Author: dongjb
     * @Date: 2019/6/20 16:16
     * @Description:   获取本周的结束时间
     * @Version: 1.0
     */
    public static Date getEndDayOfWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * @Author: dongjb
     * @Date: 2019/6/20 16:18
     * @Description:   获取某个日期的开始时间
     * @Version: 1.0
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d){
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * @Author: dongjb
     * @Date: 2019/6/20 16:19
     * @Description:   获取某个日期的结束时间
     * @Version: 1.0
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * @Author: dongjb
     * @Date: 2019/7/10 10:08
     * @Description:  投保四小时时间
     * @Version: 1.0
     */
    public static Map<String,String> fourHours(){
        Map maps = new HashMap();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        maps.put("tAppTm",dateFormat.format(now));
        maps.put("startDate",dateFormat.format(now));
        Date newDate = addSeconds(now, 60*60*4-1);
        maps.put("endDate",dateFormat.format(newDate));
        return maps;
    }

    public static Map<String,String> fortyEightHours(){
        Map maps = new HashMap();
        String week = getWeek();
        String datetime;
        if("5".equals(week)){
            datetime = "yyyy-MM-dd";
        }else{
            datetime = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(datetime);
        Date now = new Date();
        maps.put("tAppTm",dateFormat.format(now));
        if("5".equals(week)){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 2);
            maps.put("startDate",new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD).format(cal.getTime())+" 00:00:00");
            Date newDate = addSeconds(cal.getTime(), 60*60*48-1);
            maps.put("endDate",dateFormat.format(newDate)+" 23:59:59");
        }else{
            maps.put("startDate",dateFormat.format(now));
            Date newDate = addSeconds(now,60*60*48-1);
            maps.put("endDate",dateFormat.format(newDate));
        }
        return maps;
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * @Author: dongjb
     * @Date: 2019/7/10 10:56
     * @Description:  获取今天是周几
     * @Version: 1.0
     */
    public static String getWeek() {
        String week = "";
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            week = "1";
        } else if (weekday == 2) {
            week = "2";
        } else if (weekday == 3) {
            week = "3";
        } else if (weekday == 4) {
            week = "4";
        } else if (weekday == 5) {
            week = "5";
        } else if (weekday == 6) {
            week = "6";
        } else if (weekday == 7) {
            week = "7";
        }
        return week;
    }

    /**
     * @Author: dongjb
     * @Date: 2019/7/12 18:48
     * @Description:  校验身份证号，是否符合申请标准
     * @Version: 1.0
     */
    public static boolean checkAge(String idcard) {
        boolean be;
        Integer chkidcard = Integer.parseInt(idcard.toString().substring(6,10));//截取年
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int start = year - 60;
        int end = year - 18 ;
        if((start <= chkidcard) && (chkidcard <= end)){
            be = false;
        }else{
            be = true;
        }
        return be;
    }

    /**
     * @Author: dongjb
     * @Date: 2019/7/10 10:08
     * @Description:  往后延长时间
     * @Version: 1.0
     */
    public static Map<String,String> fourHours(Integer seconds){
        Map maps = new HashMap();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        maps.put("startDate",dateFormat.format(now));
        Date newDate = addSeconds(now, seconds);
        maps.put("endDate",dateFormat.format(newDate));
        return maps;
    }

    /**
     * 字符串转换为时间戳
     *
     * @date 2020/11/4 18:32
     * @param time
     * @return
     */
    public static long getStringToDate(String time) throws ParseException {
        SimpleDateFormat  sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        date = sf.parse(time);
        return date.getTime();
    }

    /**
     * 对日期字符串进行操作
     *
     * @date 2020/11/11 14:14
     * @param date 时间字符串
     * @param code 1-加/减年，2-加/减月，3-加/减日
     * @param num  加/减的数量
     * @return
     */
    @SneakyThrows
    public static String getNextYear(String date, Integer code, Integer num){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = simpleDateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);//设置起时间
        if(code == 1){
            cal.add(Calendar.YEAR, num);    //增加/减少年
        }else if(code ==2){
            cal.add(Calendar.MONTH, num);   //增加/减少月
        }else if(code == 3){
            cal.add(Calendar.DATE, num);    //增加/减少天
        }
        return simpleDateFormat.format(cal.getTime());
    }

    public static void main(String[] args) {
        System.out.println(fourHours(60*30));
    }
}
