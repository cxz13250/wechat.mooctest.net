package com.mooctest.weixin.session;

import java.util.WeakHashMap;

public class Session {
	private static WeakHashMap<String, SessionItem> mDic = new WeakHashMap<String, SessionItem>();

	public static void add(String key, SessionItem value) {
		mDic.put(key, value);
	}

	public static void set(String key, SessionItem value) {
		mDic.put(key, value);
	}

	public static SessionItem get(String key) {
		if (contains(key)){
			return mDic.get(key);
		}else{
			return new SessionItem(Operation.OPER0, Operation.STAGE0);
		}
	}

	public static void remove(String key) {
		if (Session.contains(key)) {
			mDic.remove(key);
		}
	}

	public static boolean contains(String key) {
		return mDic.containsKey(key);
	}

	public static void clear() {
		mDic.clear();
	}
}