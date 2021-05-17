package com.nice.datafileanomalydetection.constant;

public class LoginConstant {
	
	//ignore resources
	public static String SCHEMA = "/schema/**";
	public static String ASSETS = "/assets/**";
	public static String WEBJARS = "/webjars/**";
	
	// login
	public static String LOGIN = "/login";
	public static String H2CONSOLE = "/h2console/**";
	public static String LOGIN_PROCESS = "/loginProcess";
	public static String USERNAME = "memberId";
	public static String PASSWORD = "password";
	public static String LOGOUT = "/logout";
	public static String ACCESS_DENIED = "/accessDenied";	
	public static String LOGIN_SUCCESS = "/";
	public static String LOGIN_FAIL = "/loginFail";
	
	// etc
	public static String ROLE_PRIFIX = "ROLE_";	
	public static String SEPARATE = ",";
	public static char URL_SEPARATE ='/';
	
	//remember
	public static String REMEMBER_KEY = "watchdog"; // KEY
	public static int REMEMBER_SECOND = 604800; // 1주일
	
	
	
}
