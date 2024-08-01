package com.suyihang.util;


//import java.sql.Date;
//import java.util.Date;
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    // 获取当前时间(yyyy-mm-dd)
    public static Date getCurrentTime(){
        LocalDate currentDate = LocalDate.now();
        return Date.valueOf(currentDate);
    }

    // 比较时间的早晚: 如果d1早于d2,返回-1; 如果d1等于d2,返回0; 如果d1晚于d2,返回1;
    public static int timeCompare(Date d1, Date d2){
        return d1.compareTo(d2);
    }

    // 将String类型的日期转换为Date格式
    public static Date stringToDate(String dateString){
        return Date.valueOf(dateString);
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 两个日期之间的天数差
     */
    public static long calculateDateDifference(Date date1, Date date2) {
        // 获取两个日期的时间戳（毫秒）
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        // 计算两个时间戳之间的差值（毫秒）
        long diffInMillies = Math.abs(time2 - time1);

        // 将毫秒差值转换为天数差值
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diffInDays;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentTime());

        System.out.println(timeCompare(stringToDate("2022-1-2"), stringToDate("2023-1-2")));
    }

}
