package com.fuanna.h5.buy.util;

import java.util.Random;

public class RandomUtil {

	private static final String[] code = "0,1,2,3,4,5,6,7,8,9".split(",");
	
	private static final Random random = new Random();
	
	public static String getRandomCode(int figure){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < figure; i ++) {
			sb.append(code[random.nextInt(10)]);
		}
		return sb.toString();
	}
}
