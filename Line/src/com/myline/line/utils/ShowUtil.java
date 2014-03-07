package com.myline.line.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;
/**
 * @author tommy 
 * 2013-2-21 下午1:55:59
 * 展示帮助类
 */
public class ShowUtil {
	private Context context;
	public static final String CANCEL_BUTTON = "取消";
	public static final String CONFIRM_BUTTON = "确认";
	public static final String SET_BUTTON = "设置";
	public static final String BACK_BUTTON = "返回";
	
	public ShowUtil(Context context){
		this.context = context;
	}

	/**
	 * 显示提示信息
	 * 修改  Activity--->Context
	 * @param activity
	 * @param msg
	 */
	public void showLongToast(CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 显示提示信息
	 * 修改  Activity--->Context
	 * @param activity
	 * @param msg
	 */
	public void showShortToast(CharSequence msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * added by tommy 2013-2-24
	 * 描述：增加界面显示dialog
	 * @param msgInfo
	 */
	public void showDialog(String msgInfo){
		new AlertDialog.Builder(context).setTitle("").setMessage(msgInfo).
			setNeutralButton(BACK_BUTTON, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show().setCanceledOnTouchOutside(false);
	}
	
	/**
	 * 带跳转的dialog
	 * @param msgInfo
	 * @param intent
	 */
	public void showDialog(String msgInfo,Intent intent){
		final Intent i = intent;
		new AlertDialog.Builder(context).setTitle("").setMessage(msgInfo).
		setNeutralButton(BACK_BUTTON, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				context.startActivity(i);
			}
		}).show().setCanceledOnTouchOutside(false);
	}
	
//	/**
//	 * add by tommy 2013-3-6
//	 * 自定义alertDialog
//	 * @param msg
//	 * @return Button 返回一个确认按钮，让每个用到地方自己处理click事件
//	 */
//	public Button getMyConfirmButton(String msg){
//		final AlertDialog dialog = new AlertDialog.Builder(context).create();
//		dialog.show();
//		Window window = dialog.getWindow();
//        // 设置窗口的内容页面,dialog_my.xml文件中定义view内容
//		window.setContentView(R.layout.dialog_my);
//		final Button a = (Button) window.findViewById(R.id.btn_my_confirm);
//		final Button b = (Button) window.findViewById(R.id.btn_my_cancel);
//		TextView tv = (TextView) window.findViewById(R.id.textview_info);
//		a.setText("确定");
//		b.setText("取消");
//		tv.setText(msg);
//		b.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
//		return a;
//	}
	
	/**add by tommy 2013-3-10
	 * 返回一个按钮的对话框
	 * @param msg
	 * @return
	 */
//	public Button getOneButton(String msg){
//		final AlertDialog dialog = new AlertDialog.Builder(context).create();
//		dialog.show();
//		Window window = dialog.getWindow();
//        // 设置窗口的内容页面,dialog_my.xml文件中定义view内容
//		window.setContentView(R.layout.dialog_my);
//		final Button a = (Button) window.findViewById(R.id.btn_my_confirm);
//		final Button b = (Button) window.findViewById(R.id.btn_my_cancel);
//		TextView tv = (TextView) window.findViewById(R.id.textview_info);
//		a.setText("确定");
//		b.setVisibility(View.GONE);
//		tv.setText(msg);
//		return a;
//	}
	
	/**
	 * added by tommy 2013-2-24
	 * 描述：增加界面显示dialog
	 * @param msgInfo
	 */
	public void getOneButton(String msgInfo){
		new AlertDialog.Builder(context).setTitle("").setMessage(msgInfo).
		setNeutralButton(BACK_BUTTON, new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
		}).show().setCanceledOnTouchOutside(false);
	}
	
	/**
	 * ，按确认退出按钮后退出应用
	 * @param ctx
	 * @param msg
	 */
	public void exitApp(final Context ctx){
//		getMyConfirmButton("确认退出程序么？").setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//退出,回到主屏幕没有完全退出      
//				Intent intent = new Intent(Intent.ACTION_MAIN);  
//	            intent.addCategory(Intent.CATEGORY_HOME);  
//	            ctx.startActivity(intent);
//	            android.os.Process.killProcess(android.os.Process.myPid());
//	            System.exit(0);  
//			}
//		});
//		
//		
//		AlertDialog dialog = new AlertDialog.Builder(context).create();
//		dialog.show();

		new AlertDialog.Builder(ctx).setMessage("确认退出程序么？").
		setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//退出,回到主屏幕没有完全退出      
				Intent intent = new Intent(Intent.ACTION_MAIN);  
	            intent.addCategory(Intent.CATEGORY_HOME);  
	            ctx.startActivity(intent);
	            android.os.Process.killProcess(android.os.Process.myPid());
	            System.exit(0);  
			
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	
	}
	


}
