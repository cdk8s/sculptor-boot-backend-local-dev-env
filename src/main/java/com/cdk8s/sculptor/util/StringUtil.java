package com.cdk8s.sculptor.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承StringUtils类
 */
@Slf4j
public final class StringUtil {

	public static final String EMPTY = "";

	//=====================================Apache Common 包 start=====================================

	public static boolean isNotBlank(final String str) {
		return StringUtils.isNotBlank(str);
	}

	public static boolean isBlank(final String str) {
		return StringUtils.isBlank(str);
	}

	public static boolean containsAny(final String str, final CharSequence... search) {
		return StringUtils.containsAny(str, search);
	}

	public static boolean containsIgnoreCase(final String str, final String search) {
		return StringUtils.containsIgnoreCase(str, search);
	}

	/**
	 * 返回字符串第一次出现的下标位置，如果不存在返回 -1。如果存返回从 0 开始的下标。
	 */
	public static int indexOf(final String str, final String search) {
		return StringUtils.indexOf(str, search);
	}

	/**
	 * 根据下标截取字符串
	 */
	public static String substring(final String str, final int start, final int end) {
		return StringUtils.substring(str, start, end);
	}

	/**
	 * 根据前后字符串截取字符
	 */
	public static String substringBetween(final String str, final String open, final String close) {
		return StringUtils.substringBetween(str, open, close);
	}

	/**
	 * 截取第一个 search 匹配到之后的字符串，不包含 search 字符
	 * 比如：(abbccddef,c) 得到的结果就是：cddef
	 */
	public static String substringAfter(final String str, final String search) {
		return StringUtils.substringAfter(str, search);
	}

	/**
	 * 截取第一个 search 匹配到之前的字符串，不包含 search 字符
	 * 比如：(abbccddef,c) 得到的结果就是：abb
	 */
	public static String substringBefore(final String str, final String search) {
		return StringUtils.substringBefore(str, search);
	}

	/**
	 * 截取最后一个 search 匹配到之前的字符串，不包含 search 字符
	 * 比如：(abbccddef,c) 得到的结果就是：abbc
	 */
	public static String substringBeforeLast(final String str, final String search) {
		return StringUtils.substringBeforeLast(str, search);
	}

	/**
	 * 截取最后一个 search 匹配到之后的字符串，不包含 search 字符
	 * 比如：(abbccddef,c) 得到的结果就是：ddef
	 */
	public static String substringAfterLast(final String str, final String search) {
		return StringUtils.substringAfterLast(str, search);
	}

	/**
	 * 删除前后空格
	 */
	public static String trim(final String str) {
		return StringUtils.trim(str);
	}

	public static boolean equals(final String str1, final String str2) {
		return StringUtils.equals(str1, str2);
	}

	public static boolean notEquals(final String str1, final String str2) {
		return !StringUtils.equals(str1, str2);
	}

	public static boolean equalsIgnoreCase(final String str1, final String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	public static boolean notEqualsIgnoreCase(final String str1, final String str2) {
		return !StringUtils.equalsIgnoreCase(str1, str2);
	}

	public static String lowerCase(final String str1) {
		return StringUtils.lowerCase(str1);
	}

	public static String replace(final String text, final String searchString, final String replacement) {
		return StringUtils.replace(text, searchString, replacement);
	}


	//=====================================Apache Common 包  end=====================================

	//=====================================其他包 start=====================================


	//=====================================其他包  end=====================================


	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}



