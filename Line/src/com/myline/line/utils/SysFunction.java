package com.myline.line.utils;

import android.content.Context;

public class SysFunction {

	/**
	 * dip to px
	 */
	public static int dip2px(Context content, float dpValue) {
		final float scale = content.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
