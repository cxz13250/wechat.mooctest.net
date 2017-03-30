package com.mooctest.weixin.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnswerValue {
	
	static private Pattern p1 = Pattern.compile("[0-9]+([\\./][0-9]+)?");
	static private Pattern p2 = Pattern.compile("[0-9]+/[0-9]+");
	static private Pattern p3 = Pattern.compile("[0-9]+");
	
	public static boolean containsNumericalValue(String str){
		Matcher m = p1.matcher(str);
		return m.find();
	}
	
	LinkedList<Double> values = new LinkedList<Double>();
	
	public AnswerValue(){
		super();
	}
	
	public AnswerValue(String str) {
		Matcher m = p1.matcher(str);
		while (m.find()) {
			String v = m.group();
			if (p2.matcher(v).matches()) {
				Matcher m3 = p3.matcher(v);
				m3.find();double d1 = Double.valueOf(m3.group());
				m3.find();double d2 = Double.valueOf(m3.group());
				values.add(d1 / d2);
			}else{
				Double d = Double.valueOf(v);
				values.add(d);
			}
		}
	}
	
	int size(){
		return values.size();
	}
	void add(Double d){
		values.add(d);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AnswerValue) {
			AnswerValue av = (AnswerValue) obj;
			if (av.size() != size()) {
				return false;
			}else {
				HashMap<Double, Integer> map = new HashMap<Double, Integer>();
				for (Double d : values) {
					if (map.containsKey(d)) {
						map.put(d, map.get(d) + 1);
					}else {
						map.put(d, 1);
					}
				}
				for (Double d : av.values) {
					if (map.containsKey(d)) {
						int count = map.get(d);
						if (count <= 0) {
							return false;
						}else {
							map.put(d, count - 1);							
						}
					}else {
						return false;
					}
				}
				return true;
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(values.toArray());
	}
	
	public static void main(String[] args) {
		AnswerValue av1 = new AnswerValue("1.1 1.2 1.3");
		AnswerValue av2 = new AnswerValue("1.1 1.2 1.3");
		AnswerValue av3 = new AnswerValue("1.1 1.2 1.4");
		AnswerValue av4 = new AnswerValue("1.1 1.1 1.2 1.3");
		System.out.println(av1);
		System.out.println(av2);
		System.out.println(av3);
		System.out.println(av1.equals(av2));System.out.println(av2.equals(av1));
		System.out.println(av1.equals(av3));System.out.println(av3.equals(av1));
		System.out.println(av1.equals(av4));System.out.println(av4.equals(av1));
		System.out.println(av3.equals(av4));
		
		System.out.println("------");
		
		System.out.println(AnswerValue.containsNumericalValue("123 dsfasdfv"));
		System.out.println(AnswerValue.containsNumericalValue(" dsdfd23.32fasdfv"));
		System.out.println(AnswerValue.containsNumericalValue(" dsdfd23/32fasdfv"));
		System.out.println(AnswerValue.containsNumericalValue(" dsdfd.f/asdfv"));
		System.out.println(AnswerValue.containsNumericalValue(" dsdfd1.f1/asdfv"));
		System.out.println(new AnswerValue(" dsdfd1.f1/asdfv"));
		System.out.println(AnswerValue.containsNumericalValue(" dsdfd.1f/1asdfv"));
		System.out.println(new AnswerValue(" dsdfd.1f/1asdfv"));
	}
}
