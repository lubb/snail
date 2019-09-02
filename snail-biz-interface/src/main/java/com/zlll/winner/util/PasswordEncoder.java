package com.zlll.winner.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Copyright (c) 2016 Hengte Technology Co.,Ltd.
 * All Rights Reserved.<br />
 * created on 7/14/16
 *
 * MD5( password  + salt )
 *
 * @author lcs
 * @version 1.0
 */

public final class PasswordEncoder {

	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static final int HEX_RIGHT_SHIFT_COEFFICIENT = 4;
	private static final int HEX_HIGH_BITS_BITWISE_FLAG = 0x0f;

	private static final String ENCODING_ALGORITHM = "MD5";

	private static final String CHARACTER_ENCODING = "utf-8";

	/**
	 * 密码加密运算
	 * MD5( password  + salt )
	 * @date    2016-08-03
	 * @author  lcs
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String encode(final String password , final String salt) {
		if (password == null) {
			return null;
		}
		try {

			final MessageDigest messageDigest = MessageDigest.getInstance(ENCODING_ALGORITHM);

			messageDigest.update((password + (salt == null ? "" : salt)).getBytes(CHARACTER_ENCODING));

			final byte[] digest = messageDigest.digest();
			final String encodePassword = getFormattedText(digest);

			return encodePassword;
		} catch (final NoSuchAlgorithmException e) {

			throw new SecurityException(e);
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(final byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);

		for (int j = 0; j < bytes.length; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> HEX_RIGHT_SHIFT_COEFFICIENT) & HEX_HIGH_BITS_BITWISE_FLAG]);
			buf.append(HEX_DIGITS[bytes[j] & HEX_HIGH_BITS_BITWISE_FLAG]);
		}
		return buf.toString();
	}

	/**
	 * 生成随机密码
	 * 生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum() {
		int pwdLen = 12;
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwdLen) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	public static void main(String[] args) {
		System.out.println(PasswordEncoder.encode("wqq123", "DHMQzN44OVYNGsbguoxTMw=="));
	}
}
