package com.cdk8s.sculptor.util;

public class UserInfoContext {

	public UserInfoContext() {
	}

	public static Long getCurrentUserId() {
		return 111111111111111111L;
	}

	public static String getCurrentUsername() {
		return "admin";
	}

	public static String getUsernameByUserId(Long userId) {
		return "admin";
	}

}
