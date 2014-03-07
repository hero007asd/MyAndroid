package com.myline.line;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.myline.line.fragment.SyFragment;
import com.myline.line.listener.MyLocationListener;
import com.myline.line.utils.SysFunction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private RadioGroup rg;
	private RadioButton menu_sy,menu_1_2,menu_1_3,menu_1_4;
	private TextView nickNameTv, mApp_title, mMenu_button;
	
	LinearLayout mMenuLayout;
	RelativeLayout mContentLayout;
	LinearLayout.LayoutParams mContentParams;
	TranslateAnimation mSlide;
	int mMarginX, mAnimateFromX, mAnimateToX = 0;
	private int mShadowWidth = 0;
	
	public static Fragment mContent;
	public static int selRadioId;
	Fragment f_sy;
	Fragment f_1_2;   
	Fragment f_1_3;
	Fragment f_1_4;

	boolean mMenuOpen = false;
	
    private GestureDetectorCompat mDetector;
    
    /*baidu location*/
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
//		mDetector = new GestureDetector(this, new MyGestureListener());

        System.out.println("i'm in devuce ooooops");
		Log.i("location", "#####sb:");
        /*baidu location*/
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        locationBaidu(mLocationClient);
        
		initSlideMenu();
	}
	
	@Override
	protected void onDestroy() {
		if(mLocationClient != null){
			mLocationClient.stop();
		}
	}
	
	private void locationBaidu(LocationClient mLocClient){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		option.setScanSpan(5000);
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
		if(mLocClient !=null && mLocClient.isStarted()){
			mLocClient.requestLocation();
		}else{
			Log.i("location", "locClient is null or not started");
		}
	}

	private void initSlideMenu() {
		mShadowWidth = SysFunction.dip2px(MainActivity.this, 4);
		mMenuLayout = (LinearLayout) findViewById(R.id.menu);
		mContentLayout = (RelativeLayout) findViewById(R.id.content);
		mContentParams = (LinearLayout.LayoutParams) mContentLayout
				.getLayoutParams();
		mContentParams.width = getWindowManager().getDefaultDisplay()
				.getWidth() + mShadowWidth;
		mContentParams.leftMargin = -(mMenuLayout.getLayoutParams().width)
				- mShadowWidth;
		mContentLayout.setLayoutParams(mContentParams);

		mApp_title = (TextView) findViewById(R.id.app_title);

		mMenu_button = (TextView) findViewById(R.id.menu_button);
		rg = (RadioGroup) this.findViewById(R.id.radioGroup);
		//radioButton
		menu_sy = (RadioButton) findViewById(R.id.menu_sy);
		menu_1_2 = (RadioButton) findViewById(R.id.menu_1_2);
		menu_1_3 = (RadioButton) findViewById(R.id.menu_1_3);
		menu_1_4 = (RadioButton) findViewById(R.id.menu_1_4);
		//click
		menu_sy.setOnClickListener(this);
		menu_1_2.setOnClickListener(this);
		menu_1_3.setOnClickListener(this);
		menu_1_4.setOnClickListener(this);
		mMenu_button.setOnClickListener(this);

		f_sy = new SyFragment();
//		f_1_2 = new MenuWdfb();
//		f_1_3 = new MenuWdqh();
//		f_1_4 = new MenuSysm();
		//首次进入
		if (mContent == null) {
			mContent = f_sy;
			selRadioId = R.id.menu_sy;
		} 
//		//从发布信息页面返回时
		else {
			switch (selRadioId) {
			case R.id.menu_sy:
				mContent = f_sy;
				break;
			case R.id.menu_1_2:
				mContent = f_1_2;
				break;
			case R.id.menu_1_3:
				mContent = f_1_3;
				break;
			case R.id.menu_1_4:
				mContent = f_1_4;
				break;
			}
//			mApp_title.setText(mTitleName);
			rg.check(selRadioId);
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.contentView, mContent).commit();
	}
	
	

	/**
	 * 向相反的方向滑动
	 */
	public void moveIn() {
		if (mContentParams.leftMargin == -(mMenuLayout.getLayoutParams().width)
				- mShadowWidth) {
		//if (!mMenuOpen) {
			mAnimateFromX = 0;
			mAnimateToX = (mMenuLayout.getLayoutParams().width);
			mMarginX = 0;
			mMenuOpen = true;
		} else {
			mAnimateFromX = 0;
			mAnimateToX = -(mMenuLayout.getLayoutParams().width);
			mMarginX = -(mMenuLayout.getLayoutParams().width) - mShadowWidth;
			mMenuOpen = false;
		}
		slideMenuIn(mAnimateFromX, mAnimateToX, mMarginX);
	}

	/**
	 * 滑动
	 */
	public void slideMenuIn(int mAnimateFromX, int mAnimateToX,
			final int mMarginX) {

		mSlide = new TranslateAnimation(mAnimateFromX, mAnimateToX, 0, 0);
		mSlide.setDuration(300);
		mSlide.setFillEnabled(true);
		mSlide.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				mContentParams.setMargins(mMarginX, 0, 0, 0);
				mContentLayout.setLayoutParams(mContentParams);
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationStart(Animation animation) {
			}
		});
		mContentLayout.startAnimation(mSlide);
	}
	/**
	 * 暂时弃用
	 * 使用软编码，调用anim的xml文件
	 */
//	public void moveIn(){
//		Animation animOpen = AnimationUtils.loadAnimation(this, R.anim.slide_open);
//		Animation animClose = AnimationUtils.loadAnimation(this, R.anim.slide_close);
//		
//		animOpen.setAnimationListener(new AnimationListener() {
//			public void onAnimationEnd(Animation animation) {
//				mContentLayout.clearAnimation();
//				mContentParams.setMargins(0, 0, 0, 0);
//				mContentLayout.setLayoutParams(mContentParams);
//			}
//
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			public void onAnimationStart(Animation animation) {
//			}
//		});
//		animClose.setAnimationListener(new AnimationListener() {
//			public void onAnimationEnd(Animation animation) {
//				mContentLayout.clearAnimation();
//				mContentParams.setMargins(-(mMenuLayout.getLayoutParams().width) - mShadowWidth, 0, 0, 0);
//				mContentLayout.setLayoutParams(mContentParams);
//			}
//
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			public void onAnimationStart(Animation animation) {
//			}
//		});
//		if (mContentParams.leftMargin == -(mMenuLayout.getLayoutParams().width)
//				- mShadowWidth) {
//			mMenuOpen = true;
//			mContentLayout.startAnimation(animOpen);
//			
//		}else{
//			mMenuOpen = false;
//			mContentLayout.startAnimation(animClose);
//			
//		}
//	}
	/**
	 * 跳转
	 */
	public void switchContent(Fragment from, Fragment to) {
		if (from != to) {
			mContent = to;
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(from).add(R.id.contentView, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_button:
			moveIn();
			break;
		case R.id.menu_sy:
			switchContent(mContent,f_sy);
			moveIn();
			break;

		default:
			break;
		}
	}

	/**
	 * gesture==========================================
	 */
    @Override 
    public boolean onTouchEvent(MotionEvent event){ 
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	this.mDetector.onTouchEvent(ev);
    	return super.dispatchTouchEvent(ev);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
//        private static final String DEBUG_TAG = "Gestures"; 
    	private int verticalMinDistance = 20;
    	private int minVelocity = 0;
        @Override
        public boolean onDown(MotionEvent e) {
        	return true;
        }
        
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velocityX, float velocityY) {
        	if(event1.getX()-event2.getX() >verticalMinDistance && 
        			Math.abs(velocityX)>minVelocity){
        		Log.d("debug--------------", "bbb");
        		if(mMenuOpen){
                	moveIn();
        		}
        	}else if(event2.getX() - event1.getX()>verticalMinDistance &&
        			Math.abs(velocityX)>minVelocity){
        		Log.d("debug--------------", "aaa");
        		if(!mMenuOpen){
        			moveIn();
        		}
        	}
            return true;
        }
    }
}
