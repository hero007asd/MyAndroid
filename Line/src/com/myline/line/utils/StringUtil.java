package com.myline.line.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tommy 
 * 2013-3-3 下午5:45:35
 */
public class StringUtil {
	public static boolean isNull(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	public static String nullToString(String str) {
		if (str == null || "".equals(str.trim())) {
			return "";
		}
		return str.trim();
	}
	
	public static boolean isNotNull2(String str){
		if(str != null && !"".equals(str) && !"null".equals(str)){
			return true;
		}
		return false;
	}
	
	//String myString = "2008-09-08";
	/**
	 * 
	
	 * 描述
		是否是今天之前的日期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static boolean isBeforeToDay(String date) throws ParseException{
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(date);
		return	d.before(nowDate);
	}
	/**
	 * 
	
	 * 描述
		市 转换
	 * @param i
	 * @return
	 */
	public static String shiSwitch(int i){
		if(i==0){
			return "午市";
		}else{
			return "晚市";
		}
		
	}
	/**
	 * 
	
	 * 描述
		桌子类型转换
	 * @param i
	 * @return
	 */
	public static String seatSwitch(int i){
		String res = "小桌";
		switch (i) {
		case 0:
			res = "大桌";
			break;
		case 1:
			res = "中桌";
			break;
		case 2:
			res = "小桌";
			break;
		case 3:
			res = "包房";
			break;

		default:
			break;
		}
		return res;
	}
	/**
	 * 午市/晚市判断，如果返回true为午市；返回false为晚市
	 * @return
	 * @throws ParseException 
	 */
	public static boolean isWushi() throws ParseException{
		Date a = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String t = sdf2.format(a)+" 15:00:00";//以每天的15点为界限
		DateFormat df = DateFormat.getDateTimeInstance();
		boolean before = df.parse(sdf.format(a)).before(df.parse(t));//在15点之前算午市，之后为晚市
		return before;
	}
	/**
	 * 获取午市/晚市类型
	 * @return
	 */
	public static int getEatType(){
		int eatType = -1;
		try {
			if(StringUtil.isWushi()){
				eatType =0;//午市
			}else{
				eatType =1;//晚市
			}
		} catch (ParseException e) {
			eatType = -1;
		}
		return eatType;
	}
	public static String mToKm(String distStr)
	{
		String distResult;
		StringBuilder dist = new StringBuilder(distStr);
		if(dist.length() >= 4)
		{
			int length = dist.length();
			dist = dist.insert(length-3, '.');
			
			char[] distArr = dist.toString().toCharArray();
			for(int i=distArr.length-1 ;i>=0; i--)
			{
				if(distArr[i] != '0')
				{
					break;
				}
				else
				{
					length--;
				}
			}
			//distResult = new String(distArr).substring(0,length+1)+"km";
			if(distArr[distArr.length-2] !='0')
			{
			  distResult = new String(distArr).substring(0,dist.length()-2)+"km";//小数点后保留一位
			}
			else
			{
				distResult = new String(distArr).substring(0,dist.length()-4)+"km";//保留整数部分
			}
		}
		else
		{
			distResult = dist+"m";
		}
		return distResult;
		
	}
}
