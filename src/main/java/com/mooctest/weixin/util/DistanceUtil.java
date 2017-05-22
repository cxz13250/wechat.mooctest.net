package com.mooctest.weixin.util;

public class DistanceUtil {
	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1 纬度
	 * @param lat1 经度
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double getDistance(double lng1, double lat1, double lng2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static void main(String[] args) {
		String location1="118.777223 32.05378";
		String location2="118.777222 32.053722";
		String[] s1=location1.split(" ");
		String[] s2=location2.split(" ");
		double lng1=Double.valueOf(s1[0]);
		double lat1=Double.valueOf(s1[1]);
		double lng2=Double.valueOf(s2[0]);
		double lat2=Double.valueOf(s2[1]);
		double distance = DistanceUtil.getDistance(lng1, lat1, lng2, lat2);
		System.out.println(distance);
	}
}
