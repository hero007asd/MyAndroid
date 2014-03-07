package com.myline.line.utils;

/**  
 *
 * @author yushuangjiang    

 * @version 1.0  

 * @created 2013-2-20 下午6:53:57 
 * 常量帮助类
 */

public class SysConstantUtil {
	
	//——————————————按钮上的文本常量 start————————————————
	public static final String CANCEL_BUTTON = "取消";
	public static final String CONFIRM_BUTTON = "确认";
	public static final String SET_BUTTON = "设置";
	public static final String BACK_BUTTON = "返回";
	//——————————————按钮上的文本常量 end————————————————
	
	public static final String WARN_BUTTON = "警告";
	//------------自动滚动PAGEVIEW----------
	public static final int AUTO_SCROLL = 20;
	//+++++++++++++网络处理常量 start+++++++++++++++++
	public static final String PROGRESS_INFO = "载入中。。。";
	public static final String NET_BAD = "网络有些问题哦，请查看网络设置";
	
	/**
	 * HttpClient请求网络出错号码
	 */
	public static final int REQUEST_EXCEPTION_ID = 1000;
	public static final int REQUEST_TIME_OUT_ID = 1002;
	public static final int REQUEST_OK_ID = 1001;
	public static final int POSITON_DEL_OK = 1003;
	/**
	 * HttpClient请求网络出错显示信息
	 */
	public static final String REQUEST_EXCEPTION_INFO = "网络获取数据出错，请联系我们！";
	public static final String REQUEST_TIMEOUT_INFO = "网络连接超时！";
    public static final String REQUEST_LATER_INFO = "网络连接超时,请稍候访问！";
	
	public static final String NET_BACK_ALIES = "netReturnResult";
	public static final String RQUEST_ALIES = "requestInfo";
}
