package com.mooctest.weixin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizAnswerFormat {
	
	private static Pattern answer_pattern = Pattern.compile("[0-9]+|[a-zA-Z]");
	
	public static String formatSingleQuizAnswer(String answer) {
		Matcher matcher = answer_pattern.matcher(answer);
		String result = "";
        if (matcher.find()) {
        	result = matcher.group();
        }
		return result.toUpperCase();
	}
	
	public static String formatMultipleQuizAnswer(String answer) {
		Matcher matcher = answer_pattern.matcher(answer);
		String result = "";
        while (matcher.find()) {
        	result += matcher.group() + " ";
        }
		return result.toUpperCase();
	}
	
	public static boolean isValidAnswer(String answer) {
		return answer_pattern.matcher(answer).find();
	}
	
}
