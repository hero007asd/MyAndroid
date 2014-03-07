package com.myline.line.listener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener {
	private Context ctx;
	public MyLocationListener(Context ctx) {
		this.ctx = ctx;
	}
	
	
	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null) {
			return;
		}
		StringBuffer sb = new StringBuffer(256);

		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("error code : ");
		sb.append(location.getLocType());
		sb.append("latitude : ");
		sb.append(location.getLatitude());
		sb.append("lontitude : ");
		sb.append(location.getLongitude());
		sb.append("radius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			sb.append("speed : ");
			sb.append(location.getSpeed());
			sb.append("satellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("addr : ");
			sb.append(location.getAddrStr());
		}
		Toast.makeText(ctx, sb.toString(), Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onReceivePoi(BDLocation arg0) {
		// TODO Auto-generated method stub

	}

}
