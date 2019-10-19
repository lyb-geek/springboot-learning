package com.github.lybgeek.common.util;

import com.github.lybgeek.common.enu.DateType;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

@Slf4j
public class DateUtil {

  public static final String DATE_PATTERN = "yyyy-MM-dd";

  public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final String DATE_MONTH_PATTERN = "yyyy-MM";

  public static final String DATE_HOUR_MINUTE_PATTERN = "HH:mm";

  public static final String DATE_HOUR_MINUTE_SECOND_PATTERN = "HH:mm:ss";

  public static final String DATE_TIME_WITHOUT_SECOND_PATTERN = "yyyy-MM-dd HH:mm";

  public static final String DATE_TIME_WITH_HOUR_PATTERN = "yyyy-MM-dd HH:00:00";

  public static final String DATE_TIME_START_PATTERN = "yyyy-MM-dd 00:00:00";

  public static final String DATE_TIME_END_PATTERN = "yyyy-MM-dd 23:59:59";




  /**
   * 设置一周的第一天是什么; 例如美国的是SUNDAY，中国的是MONDAY。
   */
  private static final int firstDayOfWeek = Calendar.MONDAY;
  /**
   * 一周有几天
   */
  public static final int wholeDayOfWeek = 7;

  public static void main(String[] args){

  }


  public static Integer dateToTimestamp(Date time) {
    Timestamp ts = new Timestamp(time.getTime());

    return (int) ((ts.getTime()) / 1000);
  }


  public static Integer getCurrentTimeStamp(){
    Timestamp ts = new Timestamp(new Date().getTime());

    return (int) ((ts.getTime()) / 1000);
  }

  public static Integer getTimeStamp(Date date){
    Timestamp ts = new Timestamp(date.getTime());

    return (int) ((ts.getTime()) / 1000);
  }


  public static String getDateStr(Date date){
    return FastDateFormat.getInstance(DATE_PATTERN).format(date);
  }

  public static String getStartDate(String startDate){
    Date start = strToDate(startDate,DATE_PATTERN);
    return dateToStr(start,DATE_TIME_START_PATTERN);
  }

  public static String getEndDate(String endDate){
    Date end = strToDate(endDate,DATE_PATTERN);
    return dateToStr(end,DATE_TIME_END_PATTERN);
  }

  public static String getHourMinuteStr(Date date){
    return  DateFormat.getTimeInstance().format(date);
  }

  public static String getDateTimeStr(Date date){
    return FastDateFormat.getInstance(DATE_TIME_PATTERN).format(date);
  }

  public static String getHourMinuteStr(String time){
     Date date = strToDate(time,DATE_HOUR_MINUTE_PATTERN);
     return dateToStr(date,DATE_HOUR_MINUTE_PATTERN);
  }

  /**
   * 日期转字符串
   *
   * @param date
   * @return
   */
  public static String dateToStr(Date date) {
    return dateToStr(date, null);
  }

  /**
   * 日期转字符串
   *
   * @param date
   * @param pattern
   * @return
   */
  public static String dateToStr(Date date, String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    if (pattern != null) {
      sdf.applyPattern(pattern);
    }
    return sdf.format(date);
  }

  /**
   * 字符串转日期
   *
   * @param dateStr
   * @param pattern
   * @return
   */
  public static Date strToDate(String dateStr, String pattern) {
    Date date = null;
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      date = sdf.parse(dateStr);
    } catch (ParseException e) {
      log.error(e.getMessage(),e);
    }
    return date;
  }

  public static String dateToStrByType(Date date, DateType dateType) {
    String dateStr;
    switch (dateType) {
      case DAY:
        dateStr = dateToStr(date, "yyyy-MM-dd");
        break;
      case MONTH:
        dateStr = dateToStr(date, "yyyy-MM");
        break;
      case YEAR:
        dateStr = dateToStr(date, "yyyy");
        break;
      case YEAR_WEEK:
        dateStr = dateToStr(date, "yyyy") + "|" + String.format("%02d", getWhichWeekOfYear(date));
        break;
      case MONTH_WEEK:
        dateStr = dateToStr(date, "yyyy-MM") + "|" + getWhichWeekOfMonth(date);
        break;
      default:
        dateStr = null;
        break;
    }
    return dateStr;
  }

  public static Date strToDateByType(String dateStr, DateType dateType) {
    Date date;
    Calendar c;
    switch (dateType) {
      case DAY:
        date = strToDate(dateStr, "yyyy-MM-dd");
        break;
      case MONTH:
        date = strToDate(dateStr, "yyyy-MM");
        break;
      case YEAR:
        date = strToDate(dateStr, "yyyy");
        break;
      case YEAR_WEEK:
        String[] arr = StringUtils.split(dateStr, "|");
        int year = Integer.parseInt(arr[0]);
        c = Calendar.getInstance();
        c.setFirstDayOfWeek(firstDayOfWeek);
        c.setTime(strToDate(arr[0], "yyyy"));
        c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(arr[1]));
        // 2012年1月1日为星期日，则Java某一周转换为日期后都会是星期日，所以依然会出现跨年周
        // 如：2102|54周会转成2013年1月6日星期日，实际2012年12月31日星期一才是第54周。
        if (c.get(Calendar.YEAR) > year) {
          c.setTime(strToDate(arr[0], "yyyy"));
          c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        }
        date = c.getTime();
        break;
      case MONTH_WEEK:
        arr = StringUtils.split(dateStr, "|");
        String[] ym = StringUtils.split(arr[0], "-");
        year = Integer.parseInt(ym[0]);
        int month = Integer.parseInt(ym[1]);
        c = Calendar.getInstance();
        c.setFirstDayOfWeek(firstDayOfWeek);
        c.setTime(strToDate(arr[0], "yyyy-MM"));
        c.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(arr[1]));
        // 2012年12月1日为星期六，则Java某一周转换为日期后都会是星期六，所以依然会出现跨月周
        // 如：2102-12|6周会转成2013年1月5日星期六，实际2012年12月31日星期一才是第6周。
        if (c.get(Calendar.YEAR) > year || c.get(Calendar.MONTH) + 1 > month) {
          c.setTime(strToDate(arr[0], "yyyy-MM"));
          c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        date = c.getTime();
        break;
      default:
        date = null;
        break;
    }
    return date;
  }

  /**
   * 获取起始时间（日周月年）
   *
   * @param date
   * @param dateType
   * @return
   */
  public static Date getStartDate(Date date, DateType dateType) {
    return getStartDate(date, dateType, 0);
  }

  /**
   * 获取起始时间（日周月年）
   *
   * @param date
   * @param dateType
   * @param interval
   * @return
   */
  public static Date getStartDate(Date date, DateType dateType, int interval) {
    Calendar c = Calendar.getInstance();
    c.setFirstDayOfWeek(firstDayOfWeek);
    c.setTime(date);
    Calendar temp;
    switch (dateType) {
      case DAY:
        c.add(Calendar.DAY_OF_MONTH, interval);
        break;
      case MONTH:
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.add(Calendar.MONTH, interval);
        break;
      case YEAR:
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.MONTH, c.getActualMinimum(Calendar.MONTH));
        c.add(Calendar.YEAR, interval);
        break;
      case YEAR_WEEK:
        date = getYearWeekDateByInterval(date, interval);
        c.setTime(date);
        int tempYear = c.get(Calendar.YEAR);
        // 设置周的起始时间
        c.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        // 跨年周处理
        if (c.get(Calendar.YEAR) != tempYear) {
          temp = (Calendar) c.clone();
          temp.add(Calendar.DAY_OF_WEEK, wholeDayOfWeek - 1);
          if (c.get(Calendar.YEAR) < temp.get(Calendar.YEAR)) {
            c = temp;
            c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
          }
        }
        break;
      case MONTH_WEEK:
        date = getMonthWeekDateByInterval(date, interval);
        c.setTime(date);
        tempYear = c.get(Calendar.YEAR);
        int tempMonth = c.get(Calendar.MONTH);
        // 设置周的起始时间
        c.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        // 跨月周处理
        if (c.get(Calendar.YEAR) != tempYear || c.get(Calendar.MONTH) != tempMonth) {
          temp = (Calendar) c.clone();
          temp.add(Calendar.DAY_OF_WEEK, wholeDayOfWeek - 1);
          if (c.get(Calendar.YEAR) < temp.get(Calendar.YEAR) || c.get(Calendar.MONTH) < temp.get(Calendar.MONTH)) {
            c = temp;
            c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
          }
        }
        break;
      case UP_DAY_FOR_YEAR:
        if (interval != 0) {
          date = getStartDate(date, DateType.DAY, interval);
        }
        return getStartDate(date, DateType.YEAR);
      case UP_MONTH_FOR_YEAR:
        if (interval != 0) {
          date = getStartDate(date, DateType.MONTH, interval);
        }
        return getStartDate(date, DateType.YEAR);
      case UP_YEAR_WEEK_FOR_YEAR:
        if (interval != 0) {
          date = getStartDate(date, DateType.YEAR_WEEK, interval);
        }
        return getStartDate(date, DateType.YEAR);
      case UP_MONTH_WEEK_FOR_YEAR:
        if (interval != 0) {
          date = getStartDate(date, DateType.MONTH_WEEK, interval);
        }
        return getStartDate(date, DateType.YEAR);
      default:
        break;
    }
    c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
    c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
    c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
    c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
    return c.getTime();
  }

  /**
   * 获取结束时间（日周月年）
   *
   * @param date
   * @param dateType
   * @return
   */
  public static Date getEndDate(Date date, DateType dateType) {
    return getEndDate(date, dateType, 0);
  }

  /**
   * 获取结束时间（日周月年）
   *
   * @param date
   * @param dateType
   * @param interval
   * @return
   */
  public static Date getEndDate(Date date, DateType dateType, int interval) {
    Calendar c = Calendar.getInstance();
    c.setFirstDayOfWeek(firstDayOfWeek);
    c.setTime(date);
    Calendar temp;
    switch (dateType) {
      case DAY:
      case UP_DAY_FOR_YEAR:
        c.add(Calendar.DAY_OF_MONTH, interval);
        break;
      case MONTH:
      case UP_MONTH_FOR_YEAR:
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.add(Calendar.MONTH, interval);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        break;
      case YEAR:
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.MONTH, c.getActualMinimum(Calendar.MONTH));
        c.add(Calendar.YEAR, interval);
        c.set(Calendar.MONTH, c.getActualMaximum(Calendar.MONTH));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        break;
      case YEAR_WEEK:
      case UP_YEAR_WEEK_FOR_YEAR:
        date = getYearWeekDateByInterval(date, interval);
        c.setTime(date);
        int tempYear = c.get(Calendar.YEAR);
        // 设置周的截止时间
        c.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        c.add(Calendar.DAY_OF_WEEK, wholeDayOfWeek - 1);
        // 跨年周处理
        if (c.get(Calendar.YEAR) != tempYear) {
          temp = (Calendar) c.clone();
          temp.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
          if (c.get(Calendar.YEAR) > temp.get(Calendar.YEAR)) {
            c = temp;
            c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
          }
        }
        break;
      case MONTH_WEEK:
      case UP_MONTH_WEEK_FOR_YEAR:
        date = getMonthWeekDateByInterval(date, interval);
        c.setTime(date);
        tempYear = c.get(Calendar.YEAR);
        int tempMonth = c.get(Calendar.MONTH);
        // 设置周的截止时间
        c.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        c.add(Calendar.DAY_OF_WEEK, wholeDayOfWeek - 1);
        // 跨月周处理
        if (c.get(Calendar.YEAR) != tempYear || c.get(Calendar.MONTH) != tempMonth) {
          temp = (Calendar) c.clone();
          temp.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
          if (c.get(Calendar.YEAR) > temp.get(Calendar.YEAR) || c.get(Calendar.MONTH) > temp.get(Calendar.MONTH)) {
            c = temp;
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
          }
        }
        break;
      default:
        break;
    }
    c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
    c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
    c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
    c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
    return c.getTime();
  }

  /**
   * 获取两个日期的区间天数（例：6号~8号，其区间天数为三天）
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static int getIntervalDays(Date startDate, Date endDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(startDate);
    c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
    c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
    c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
    c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
    long start = c.getTimeInMillis();
    c.setTime(endDate);
    c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
    c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
    c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
    c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
    long end = c.getTimeInMillis();
    int days = (int) ((end - start) / (24 * 60 * 60 * 1000));
    days = Math.abs(days) + 1;
    return days;
  }

  /**
   * 获取某个日期所在月份的每一天
   *
   * @param date
   * @return
   */
  public static List<Date> listDateOfMonth(Date date) {
    Date startDate = getStartDate(date, DateType.MONTH);
    Date endDate = getEndDate(date, DateType.MONTH);
    return listDateOfInterval(startDate, endDate);
  }


  /**
   * 获取某个日期所在周的每一天
   *
   * @param date
   * @return
   */
  public static List<Date> listDateOfWeek(Date date) {
    Date startDate = getStartDate(date, DateType.MONTH_WEEK);
    Date endDate = getEndDate(date, DateType.MONTH_WEEK);
    return listDateOfInterval(startDate, endDate);
  }


  /**
   * 获取某段时间的每一天
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static List<Date> listDateOfInterval(Date startDate, Date endDate) {
    List<Date> days = new ArrayList<>();
    Calendar c = Calendar.getInstance();
    c.setTime(startDate);
    while (c.getTimeInMillis() <= endDate.getTime()) {
      days.add(c.getTime());
      c.add(Calendar.DAY_OF_MONTH, 1);
    }
    return days;
  }

  /**
   * 根据日期类型获取时间段内格式化后的日期字符串集合
   *
   * @param startDate
   * @param endDate
   * @param dateType
   * @return
   */
  public static List<String> listFormatDate(Date startDate, Date endDate, DateType dateType) {
    List<String> dates = new ArrayList<>();
    endDate = DateUtil.getStartDate(endDate, dateType);
    int interval = 0;
    while (true) {
      Date tempDate = DateUtil.getStartDate(startDate, dateType, interval);
      String tempDateStr = dateToStrByType(tempDate, dateType);
      dates.add(tempDateStr);
      // 中断条件
      if (tempDate.getTime() == endDate.getTime()) {
        break;
      } else if (tempDate.getTime() > endDate.getTime()) {
        interval--;
      } else if (tempDate.getTime() < endDate.getTime()) {
        interval++;
      }
    }
    return dates;
  }

  /**
   * 获取年份周的前几周或后几周的时间
   *
   * @param date
   * @param interval
   * @return
   */
  public static Date getYearWeekDateByInterval(Date date, int interval) {
    if (interval < 0) {
      int minWeekOfYear = 1;

      Calendar c = Calendar.getInstance();
      c.setFirstDayOfWeek(firstDayOfWeek);
      c.setTime(date);
      int weekOfYear = (int) Math.ceil((c.get(Calendar.DAY_OF_YEAR) + wholeDayOfWeek - getDayOfWeek(c.getTime())) / (double) wholeDayOfWeek);
      int tempInterval = (weekOfYear + interval) - minWeekOfYear;
      if (tempInterval < 0) {
        c.add(Calendar.YEAR, -1);
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        date = getYearWeekDateByInterval(c.getTime(), tempInterval + 1);
      } else if (tempInterval > 0) {
        c.add(Calendar.WEEK_OF_YEAR, interval);
        date = c.getTime();
      } else {
        c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
        date = c.getTime();
      }
    } else if (interval > 0) {
      Calendar temp = Calendar.getInstance();
      temp.setFirstDayOfWeek(firstDayOfWeek);
      temp.setTime(date);
      temp.set(Calendar.DAY_OF_YEAR, temp.getActualMaximum(Calendar.DAY_OF_YEAR));
      int maxWeekOfYear = (int) Math.ceil((temp.get(Calendar.DAY_OF_YEAR) + wholeDayOfWeek - getDayOfWeek(temp.getTime())) / (double) wholeDayOfWeek);

      Calendar c = Calendar.getInstance();
      c.setFirstDayOfWeek(firstDayOfWeek);
      c.setTime(date);
      int weekOfYear = (int) Math.ceil((c.get(Calendar.DAY_OF_YEAR) + wholeDayOfWeek - getDayOfWeek(c.getTime())) / (double) wholeDayOfWeek);
      int tempInterval = (weekOfYear + interval) - maxWeekOfYear;
      if (tempInterval > 0) {
        c.add(Calendar.YEAR, 1);
        c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
        date = getYearWeekDateByInterval(c.getTime(), tempInterval - 1);
      } else if (tempInterval < 0) {
        c.add(Calendar.WEEK_OF_YEAR, interval);
        date = c.getTime();
      } else {
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        date = c.getTime();
      }
    }
    return date;
  }

  /**
   * 获取月份周的前几周或后几周的时间
   *
   * @param date
   * @param interval
   * @return
   */
  public static Date getMonthWeekDateByInterval(Date date, int interval) {
    if (interval < 0) {
      Calendar c = Calendar.getInstance();
      c.setFirstDayOfWeek(firstDayOfWeek);
      c.setTime(date);
      int minWeekOfMonth = 1;
      int weekOfMonth = c.get(Calendar.WEEK_OF_MONTH);
      int tempInterval = (weekOfMonth + interval) - minWeekOfMonth;
      if (tempInterval < 0) {
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        date = getMonthWeekDateByInterval(c.getTime(), tempInterval + 1);
      } else if (tempInterval > 0) {
        c.add(Calendar.WEEK_OF_MONTH, interval);
        date = c.getTime();
      } else {
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        date = c.getTime();
      }
    } else if (interval > 0) {
      Calendar c = Calendar.getInstance();
      c.setFirstDayOfWeek(firstDayOfWeek);
      c.setTime(date);
      int maxWeekOfMonth = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
      int weekOfMonth = c.get(Calendar.WEEK_OF_MONTH);
      int tempInterval = (weekOfMonth + interval) - maxWeekOfMonth;
      if (tempInterval > 0) {
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        date = getMonthWeekDateByInterval(c.getTime(), tempInterval - 1);
      } else if (tempInterval < 0) {
        c.add(Calendar.WEEK_OF_MONTH, interval);
        date = c.getTime();
      } else {
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        date = c.getTime();
      }
    }
    return date;
  }

  /**
   * 获取当前日期为年的第几周
   *
   * @param date
   * @return
   */
  public static int getWhichWeekOfYear(Date date) {
    String year = dateToStr(date, "yyyy");
    Calendar c = Calendar.getInstance();
    c.setFirstDayOfWeek(firstDayOfWeek);
    c.setTime(date);
    int weekOfYear = (int) Math.ceil((c.get(Calendar.DAY_OF_YEAR) + wholeDayOfWeek - getDayOfWeek(c.getTime())) / (double) wholeDayOfWeek);
    return weekOfYear;
  }

  /**
   * 获取一年共有几周
   *
   * @param date
   * @return
   */
  public static int getWholeWeekOfYear(Date date) {
    Date endDateOfMonth = DateUtil.getEndDate(date, DateType.YEAR);
    return getWhichWeekOfYear(endDateOfMonth);
  }

  /**
   * 获取当前日期为月的第几周
   *
   * @param date
   * @return
   */
  public static int getWhichWeekOfMonth(Date date) {
    Calendar c = Calendar.getInstance();
    c.setFirstDayOfWeek(firstDayOfWeek);
    c.setTime(date);
    int whichWeek = c.get(Calendar.WEEK_OF_MONTH);
    return whichWeek;
  }

  /**
   * 获取一个月共有几周
   *
   * @param date
   * @return
   */
  public static int getWholeWeekOfMonth(Date date) {
    Date endDateOfMonth = DateUtil.getEndDate(date, DateType.MONTH);
    return getWhichWeekOfMonth(endDateOfMonth);
  }

  /**
   * 获取当前日期为一周的第几天
   *
   * @param date
   * @return
   */
  public static int getDayOfWeek(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek + 1;
    if (dayOfWeek <= 0) {
      dayOfWeek += wholeDayOfWeek;
    }
    return dayOfWeek;
  }

  /**
   * 获取月的某一周实际有几天
   *
   * @param year
   * @param month
   * @param week
   * @return
   */
  public static int getWholeDayOfMonthWeek(int year, int month, int week) {
    String dateStr = year + "-" + month;
    Calendar c = Calendar.getInstance();
    c.setFirstDayOfWeek(firstDayOfWeek);

    /*
     * c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH)); c.set(Calendar.MONTH, month - 1); System.out.println(c.getTime()); // 这一行难道可以刷新设置月份后的时间
     * c.set(Calendar.YEAR, year);
     */
    c.setTime(strToDate(dateStr, "yyyy-M"));
    c.set(Calendar.WEEK_OF_MONTH, week);
    if (c.get(Calendar.YEAR) > year || c.get(Calendar.MONTH) + 1 > month) {
      /*
       * c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH)); c.set(Calendar.MONTH, month - 1); c.set(Calendar.YEAR, year);
       */
      c.setTime(strToDate(dateStr, "yyyy-M"));
      c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    }
    Date date = c.getTime();
    Date startDate = getStartDate(date, DateType.MONTH_WEEK);
    Date endDate = getEndDate(date, DateType.MONTH_WEEK);
    int days = getIntervalDays(startDate, endDate);
    return days;
  }

  /**
   * 获取backupMonth，如果传入日期的截止时间大于当前月份第一天则返回空字符串，否则以结尾时间的月份作为backupMonth
   *
   * @param date
   * @param dateType
   * @return
   */
  public static String getBackupMonth(Date date, DateType dateType) {
    date = getEndDate(date, dateType);
    Date currentMonth = DateUtil.getStartDate(new Date(), DateType.MONTH);
    String dateStr = DateUtil.dateToStr(date, "yyyy-MM");
    if (date.compareTo(currentMonth) >= 0) {
      dateStr = "";
    }
    return dateStr;
  }


  public static String getDateStrByDate(Date d, SimpleDateFormat sf) {
    String str = null;
    try {
      str = sf.format(d);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  public static String getDateStrByDate(Date d) {
    SimpleDateFormat yyyy_mm_ddFormat = new SimpleDateFormat("yyyy-MM-dd");
    return getDateStrByDate(d, yyyy_mm_ddFormat);
  }

  public static String getDateMonthStrByDate(Date d) {
    SimpleDateFormat yyyy_mmFormat = new SimpleDateFormat("yyyy-MM");
    return getDateStrByDate(d, yyyy_mmFormat);
  }

  public static Date getDateByStr(String str) {
    SimpleDateFormat yyyy_mm_ddFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date d = null;
    try {
      d = yyyy_mm_ddFormat.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return d;
  }

  public static Date getMaxMonthDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    return calendar.getTime();
  }

  public static Date getMinMonthDate(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    return calendar.getTime();
  }

  public static String getYesterday(SimpleDateFormat sd) {
    Date date = new Date();// 取时间
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, -1);// 把日期往前减少一天，若想把日期向后推一天则将负数改为正数
    date = calendar.getTime();
    if (sd == null)
      sd = new SimpleDateFormat("yyyy-MM-dd");
    String dateStr = sd.format(date);
    // System.out.println("*******得到明天的日期*******" + dateStr);
    return dateStr;
  }

  /**
   * 得到几天前的时间
   *
   * @param d
   * @param day
   * @return
   */
  public static Date getDateBefore(Date d, int day) {
    Calendar now = Calendar.getInstance();
    now.setTime(d);
    now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
    return now.getTime();
  }

  /**
   * 得到几天后的时间
   *
   * @param d
   * @param day
   * @return
   */
  public static Date getDateAfter(Date d, int day) {
    Calendar now = Calendar.getInstance();
    now.setTime(d);
    now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
    return now.getTime();
  }

  /**
   * 得到几个月前的时间
   *
   * @param d
   * @param month
   * @return
   */
  public static Date getDateBeforeMonth(Date d, int month) {
    d = getDateFirstDayOfMonth(d.getYear() + 1900, d.getMonth() + 1, null);
    Calendar now = Calendar.getInstance();
    now.setTime(d);
    now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
    return now.getTime();
  }

  /**
   * 得到几个月后的时间
   *
   * @param d
   * @param month
   * @return
   */
  public static Date getDateAfterMonth(Date d, int month) {
    d = getDateFirstDayOfMonth(d.getYear() + 1900, d.getMonth()+1, null);
    Calendar now = Calendar.getInstance();
    now.setTime(d);
    now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
    return now.getTime();
  }

  public static String getFirstDayOfMonth(int year, int month, SimpleDateFormat sd) {
    if (sd == null)
      sd = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    Date date = calendar.getTime();
    return sd.format(date);
  }

  public static Date getDateFirstDayOfMonth(int year, int month, SimpleDateFormat sd) {
    if (sd == null)
      sd = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    Date date = calendar.getTime();
    return date;
  }

  public static Date getFirstDayOfMonth(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  public static int getDaysOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  public static boolean isThisYear(Date date) {
    Calendar now = Calendar.getInstance();
    Calendar tmp = Calendar.getInstance();
    tmp.setTime(date);
    return tmp.get(Calendar.YEAR) == now.get(Calendar.YEAR);
  }

  public static Date getDate(String dateStr, String pattern) {
    try {
      String time = dateStr.replaceAll("/", "-");
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      return sdf.parse(time);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * 获取过去7天的日期
   *
   * @param past
   * @param customDate
   * @return
   */
  public static String getPastDateStr(int past, String customDate) {
    Calendar calendar = Calendar.getInstance();
    Date paramDate = DateUtil.getDateByStr(customDate);
    calendar.setTime(paramDate);
    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
    Date day = calendar.getTime();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String startTime = format.format(day);
    return startTime;
  }

  /**
   * 获取过去n天的日期max time
   *
   * @param past
   * @param customDate
   * @return
   */
  public static String getMaxPastDateStr(int past, String customDate) {
    String endTime = getPastDateStr(past, customDate) + " 23:59:59";
    return endTime;
  }



}
