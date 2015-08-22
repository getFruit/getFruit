package com.get.fruit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;

public class StringUtils {

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {

		boolean isValid = false;

		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
	
	
	/**
	 * 规范内容长度
	 * 
	 * @param s
	 *            输入的字符
	 * @return
	 */
	public static int getWordCountRegex(String s) {
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}
	
	/**
	 * 校验整数
	 * @param text
	 * @return
	 */
	protected static boolean isNumeric(String text) {
		return TextUtils.isDigitsOnly(text);
	}
	
	protected static boolean isAlphaNumeric(String text) {
		return matches(text, "[a-zA-Z0-9 \\./-]*");
	}
	
	
	protected static boolean isDomain(String text) {
		return matches(text, Build.VERSION.SDK_INT>=8?Patterns.DOMAIN_NAME:Pattern.compile(".*"));
	}
	
	protected static boolean isEmail(String text) {
		return matches(text, Build.VERSION.SDK_INT>=8?Patterns.DOMAIN_NAME:Pattern.compile(
	            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	            "\\@" +
	            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	            "(" +
	                "\\." +
	                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	            ")+"
	        ));
	}
	
	protected static boolean isIpAddress(String text) {
		return matches(text, Build.VERSION.SDK_INT>=8?Patterns.DOMAIN_NAME:Pattern.compile(
	            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
	            + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
	            + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
	            + "|[1-9][0-9]|[0-9]))"));
	}
	
	protected static boolean isWebUrl(String text) {
		//TODO: Fix the pattern for api level < 8
		return matches(text, Build.VERSION.SDK_INT>=8?Patterns.WEB_URL:Pattern.compile(".*"));
	}
	
	
	protected static boolean find(String text,String regex) {
		return Pattern.compile(regex).matcher(text).find();
	}
	
	protected static boolean matches(String text,String regex) {
		Pattern pattern = Pattern.compile(".*");
		return pattern.matcher(text).matches();
	}
	
	protected static boolean matches(String text,Pattern pattern) {
		return pattern.matcher(text).matches();
	}
}
