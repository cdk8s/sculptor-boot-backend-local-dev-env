package com.cdk8s.sculptor.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时间工具类
 */
public final class DatetimeUtil {


	private static final String STRING_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat SDF_YY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat SDF_YY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");


	//=====================================JDK8 包 start=====================================

	public static long currentEpochMilli() {
		return Instant.now().toEpochMilli();
	}

	public static long currentEpochSecond() {
		return Instant.now().getEpochSecond();
	}

	public static LocalDateTime getNowLocalDateTime() {
		return LocalDateTime.now();
	}

	public static Instant getNowInstant() {
		return Instant.now();
	}

	public static long toEpochMilli(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static long toEpochMilli(String str) {
		return toLocalDateTime(str).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static String toDatetimeString() {
		return toDatetimeString(toLocalDateTime(currentEpochMilli()), null);
	}

	public static String toDatetimeString(LocalDateTime localDateTime, String pattern) {
		if (StringUtil.isBlank(pattern)) {
			pattern = STRING_PATTERN;
		}
		return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
	}

	public static String toDatetimeString(long epochMill) {
		return toDatetimeString(toLocalDateTime(epochMill), null);
	}

	public static String toDatetimeString(long epochMill, String pattern) {
		return toDatetimeString(toLocalDateTime(epochMill), pattern);
	}

	public static LocalDateTime toLocalDateTime(String str) {
		return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(STRING_PATTERN));
	}

	public static LocalDateTime toLocalDateTime(long epochMill) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMill), ZoneId.systemDefault());
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * time1 如果在 time2 之前的日期，则返回 true
	 *
	 * @param time1，2018-04-23 23:24:04
	 * @param time2，2018-04-27 23:24:04
	 * @return
	 */
	public static boolean isBefore(LocalDateTime time1, LocalDateTime time2) {
		return time1.isBefore(time2);
	}

	/**
	 * time1 如果在 time2 之后的日期，则返回 true
	 *
	 * @param time1，2018-04-27 23:24:04
	 * @param time2，2018-04-23 23:24:04
	 * @return
	 */
	public static boolean isAfter(LocalDateTime time1, LocalDateTime time2) {
		return time1.isBefore(time2);
	}

	/**
	 * 如果 time1 和 time2 在日期上是一样的，则返回 true
	 *
	 * @param time1，2018-04-27 23:24:04
	 * @param time2，2018-04-27 23:24:04
	 * @return
	 */
	public static boolean isEqual(LocalDateTime time1, LocalDateTime time2) {
		return time1.isEqual(time2);
	}

	/**
	 * 比较时间差值
	 * 如果 time1 早于 time2 结果是正数：96
	 * time1 = 2018-04-22 23:24:04
	 * time2 = 2018-04-26 23:24:04
	 * 如果 time1 晚于 time2 结果是负数：-96
	 * time1 = 2018-04-26 23:24:04
	 * time2 = 2018-04-22 23:24:04
	 *
	 * @param time1
	 * @param time2
	 * @param chronoUnit 有：ChronoUnit.MILLIS ChronoUnit.SECONDS ChronoUnit.MINUTES ChronoUnit.HOURS ChronoUnit.DAYS 等等
	 * @return
	 */
	public static long between(LocalDateTime time1, LocalDateTime time2, ChronoUnit chronoUnit) {
		return chronoUnit.between(time1, time2);
	}

	public static long betweenNowAndFutureTime(LocalDateTime time2, ChronoUnit chronoUnit) {
		LocalDateTime time1 = getNowLocalDateTime();
		return between(time1, time2, chronoUnit);
	}

	public static long betweenNowAndPastTime(LocalDateTime time1, ChronoUnit chronoUnit) {
		LocalDateTime time2 = getNowLocalDateTime();
		return between(time1, time2, chronoUnit);
	}

	/**
	 * 获取指定时间当天的开始时间
	 */
	public static LocalDateTime getLocalDateTimeStart(Long epochMilli) {
		if (epochMilli == null) {
			return null;
		}

		LocalDateTime localDateTime = toLocalDateTime(epochMilli);
		LocalDateTime localDateTimeStart = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), 0, 0, 0);
		return localDateTimeStart;

	}

	/**
	 * 获取指定时间当天的结束时间
	 */
	public static LocalDateTime getLocalDateTimeEnd(Long epochMilli) {
		if (epochMilli == null) {
			return null;
		}

		LocalDateTime localDateTime = toLocalDateTime(epochMilli);
		LocalDateTime localDateTimeEnd = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), 23, 59, 59);
		return localDateTimeEnd;
	}

	/**
	 * 获取指定时间当天的开始时间戳
	 */
	public static Long getEpochMilliStart(Long epochMilli) {
		if (epochMilli == null) {
			return null;
		}

		return toEpochMilli(getLocalDateTimeStart(epochMilli));
	}

	/**
	 * 获取指定时间当天的结束时间戳
	 */
	public static Long getEpochMilliEnd(Long epochMilli) {
		if (epochMilli == null) {
			return null;
		}

		return toEpochMilli(getLocalDateTimeEnd(epochMilli));
	}


	//=====================================JDK8 包  end=====================================

	//=====================================其他包 start=====================================


	public static String formatDate(final Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}


	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(final Date date, Object... pattern) {
		String formatDate;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}


	public static String formatDateTime(final Date date) {
		return formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateTime(final Date date, Object... pattern) {
		return formatDate(date, pattern);
	}


	/**
	 * 以当前时间为路径（到小时）
	 *
	 * @return
	 */
	public static String getCurrentDateTimeByFilePath() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("yyyy");
		stringBuilder.append(File.separator);
		stringBuilder.append("MM");
		stringBuilder.append(File.separator);
		stringBuilder.append("dd");
		stringBuilder.append(File.separator);
		stringBuilder.append("HH");
		return formatDate(new Date(), stringBuilder.toString());
	}


	//=====================================其他包  end=====================================


	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}



