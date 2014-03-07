package com.myline.line.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;


import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
/**
 * 
 *
 * @author wangshu    
	异步加载图片
 * @version 1.0  

 * @created 2013-3-22 下午1:30:35
 */
public class SyncImageLoader {

	private Object lock = new Object();
	
	private boolean mAllowLoad = true;
	
	private boolean firstLoad = true;
	
	private int mStartLoadLimit = 0;
	
	private int mStopLoadLimit = 0;
	
	final Handler handler = new Handler();
	
	private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();   
	
	public interface OnImageLoadListener {
		public void onImageLoad(String url, Drawable drawable);
		public void onError(String url);
	}
	
	public void setLoadLimit(int startLoadLimit,int stopLoadLimit){
		if(startLoadLimit > stopLoadLimit){
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}
	
	public void restore(){
		mAllowLoad = true;
		firstLoad = true;
	}
		
	public void lock(){
		mAllowLoad = false;
		firstLoad = false;
	}
	
	public void unlock(){
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void loadImage(Integer t, String imageUrl,
			OnImageLoadListener listener) {
		final OnImageLoadListener mListener = listener;
		final String mImageUrl = imageUrl;
		final Integer mt = t;
		
		new Thread(new Runnable() {

			@Override
			public void run() {
//				if(imageCache.containsKey(mImageUrl)){
//					mAllowLoad = false;
//				}
				if(!mAllowLoad){
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(mAllowLoad && firstLoad){
					loadImage(mImageUrl, mt, mListener);
				}
				
				if(mAllowLoad && mt <= mStopLoadLimit && mt >= mStartLoadLimit){
					loadImage(mImageUrl, mt, mListener);
				}
			}

		}).start();
	}
	
	private void loadImage(final String mImageUrl,final Integer mt,final OnImageLoadListener mListener){
		if (imageCache.containsKey(mImageUrl)) {  
            SoftReference<Drawable> softReference = imageCache.get(mImageUrl);  
            final Drawable d = softReference.get();  
            if (d != null) {  
            	handler.post(new Runnable() {
    				@Override
    				public void run() {
    					if(mAllowLoad){
    						mListener.onImageLoad(mImageUrl, d);
    					}
    				}
    			});
                return;  
            }  
        }  
		try {
			final Drawable d = loadImageFromUrl(mImageUrl);
			if(d != null){
                imageCache.put(mImageUrl, new SoftReference<Drawable>(d));
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					if(mAllowLoad){
						mListener.onImageLoad(mImageUrl, d);
					}
				}
			});
		} catch (IOException e) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					mListener.onError(mImageUrl);
				}
			});
			e.printStackTrace();
		}
	}

	public static Drawable loadImageFromUrl(String url) throws IOException {
		// /storage/sdcard0/Wireless_Ordering/8FCCC7829A654DDDCF28AB5A0591B006
		String folder = Environment.getExternalStorageDirectory()+"/Wireless_Ordering/";
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File f = new File(folder);
			if(!f.exists()){
				f.mkdirs();
			}
//			File mfile = new File(folder+ MD5.getMD5(url));
			File mfile = new File(folder+ url);
			if(!mfile.exists()){
				mfile.createNewFile();
			}else{
				FileInputStream fis = new FileInputStream(mfile);
				Drawable d = Drawable.createFromStream(fis, "src");
				return d;
			}
			
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			DataInputStream in = new DataInputStream(i);
			FileOutputStream out = new FileOutputStream(mfile);
			byte[] buffer = new byte[1024];
			int   byteread=0;
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			in.close();
			out.close();
//			Drawable d = Drawable.createFromStream(i, "src");
			return loadImageFromUrl(url);
		}else{
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			Drawable d = Drawable.createFromStream(i, "src");
			return d;
		}
		
	}
}
