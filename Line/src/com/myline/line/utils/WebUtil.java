package com.myline.line.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;

/**
 * 
 * @author yushuangjiang
 * @version 1.0
 * @created 2013-2-20 下午6:58:33 网络帮助类
 */
public class WebUtil {
	// 主url
	public static final String COMMON_URL = "http://www.wetouching.com/";
	/**
	 * 用httpUrlConnection与网络通讯的公共方法,并把返回值交给handler做处理
	 * version1.0 2013-2-20
	 * @param requestUrl 请求的链接
	 * @param paramStr 网络通讯参数
	 * @param msg  message实例
	 * @param msgWhat 网络获取返回成功的消息体标志位
	 */
	public void getInfoByUrl(String requestUrl,Message msg,int msgWhat) {
		String resultStr = null;
		URL url;
		try {
			url = new URL(requestUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			InputStreamReader isr = new InputStreamReader(
					urlConnection.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			resultStr = br.readLine();
			StringBuffer sbStr = new StringBuffer();
			while (resultStr != null) {
				sbStr.append(resultStr);
				resultStr = br.readLine();
			}
			resultStr = sbStr.toString();
			//网络通讯成功返回信息后,设置handler处理标志位
			msg.what = msgWhat;
		} catch (Exception e) {
			//网络通讯失败,设置handler处理标志位
			msg.what = SysConstantUtil.REQUEST_EXCEPTION_ID;
			e.printStackTrace();
		}
		//added by tommy 2013-2-24 网络出错处理，把msg发给handler做处理
		Bundle bundle = new Bundle();
		bundle.putString(SysConstantUtil.NET_BACK_ALIES, resultStr);
		msg.setData(bundle);
		msg.sendToTarget();//交给takeQueueHandler处理
	}

	/**
	 * 用httpClient与网络通讯的公共方法,无图片,并把返回值交给handler做处理
	 * version1.0 2013-2-20
	 * @param requestUrl 请求的链接
	 * @param paramStr 网络通讯参数
	 * @param msg  message实例
	 * @param msgWhat 网络获取返回成功的消息体标志位
	 */
	public void getInfoByHttpClient(String requestUrl,String paramStr,Message msg,int msgWhat){
		String resultStr = null;
		System.out.println("@@@@@@@@@@@@paramStr:"+paramStr);
		final int REQUEST_TIMEOUT = 5*1000;//设置请求超时5秒钟  
		final int SO_TIMEOUT = 5*1000;  //设置等待数据超时时间5秒钟  
		//ADD  BY  YSJ   请求超时处理  2013-02-26
	    BasicHttpParams httpParams = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
	    HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
	    // ADD END
		//post处理paramStr
		HttpPost httpPost = new HttpPost(requestUrl);// 使用HttpPost对象设置发送的URL路径
//		HttpClient client = new DefaultHttpClient();
		HttpClient client = new DefaultHttpClient(httpParams);//增加参数
		
		//如果传递参数比较多可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(SysConstantUtil.RQUEST_ALIES, paramStr));
		// 创建一个浏览器对象，以把POST对象向服务器发送，并返回响应消息
		try {
			//设置请求参数，发送请求体
			httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = client.execute(httpPost);
			 //HttpStatus.SC_OK表示连接成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//取得返回的字符串
				resultStr = EntityUtils.toString(response.getEntity());
				System.out.println("我的返回数据"+resultStr);
				//网络通讯成功返回信息后,设置handler处理标志位
				msg.what = msgWhat;
			}else{
				//网络通讯失败,设置handler处理标志位
				msg.what = SysConstantUtil.REQUEST_EXCEPTION_ID;
			}
		} catch(ConnectTimeoutException e){
			msg.what = SysConstantUtil.REQUEST_TIME_OUT_ID;
		} catch (ClientProtocolException e) {
			msg.what = SysConstantUtil.REQUEST_EXCEPTION_ID;
		} catch (IOException e) {
			msg.what = SysConstantUtil.REQUEST_EXCEPTION_ID;
		}
		/**added by tommy 2013-2-24 网络出错处理，把msg发给handler做处理**/ 
		Bundle bundle = new Bundle();
		bundle.putString(SysConstantUtil.NET_BACK_ALIES, resultStr);
		msg.setData(bundle);
		msg.sendToTarget();
	}

	/**
	 * 用httpClient与网络通讯的公共方法,多媒体请求,并把返回值交给handler做处理
	 * version1.0 2013-2-20
	 * @param requestUrl
	 * @param paramStr
	 * @param msg
	 * @param msgWhat
	 */
//	public void getInfoByMulti(String requestUrl,String paramStr,Message msg,int msgWhat){
//		String strResult = null;
//		//TODO post处理paramStr
//		HttpPost httpPost = new HttpPost(requestUrl);// 使用HttpPost对象设置发送的URL路径
//		HttpResponse response = null;
//		HttpClient client = new DefaultHttpClient();
//		// 封装模拟浏览器请求参数
//		MultipartEntity mEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
////		if (imageFileList != null) {//如果有图片，加入图片文件
//////			if (!imageUrl.equals("")) {
//////			FileBody file = new FileBody(new File(imageUrl));
////			for(int i = 0;i<imageFileList.size();i++){
////				FileBody file = new FileBody(imageFileList.get(i));
////				mEntity.addPart("productPhoto" + i, file);// 添加图片
////			}
////		}
////		try {
////			StringBody pair1 = new StringBody(productName);
////			StringBody pair2 = new StringBody(productProp);
////			mEntity.addPart("productName", pair1);// 添加产品名
////			mEntity.addPart("productProp", pair2);// 添加产品属性
////		} catch (UnsupportedEncodingException e) {
////			e.printStackTrace();
////		}
//		httpPost.setEntity(mEntity);// 发送请求体
//		// 创建一个浏览器对象，以把POST对象向服务器发送，并返回响应消息
//		try {
//			response = client.execute(httpPost);
//			 //HttpStatus.SC_OK表示连接成功
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				Log.i("honest","uploadSuccess");
//				//取得返回的字符串
//                strResult = EntityUtils.toString(response.getEntity());
//			}else{
//				Log.i("honest","uploadFail");
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
////		return strResult;
//	}
//	
}
