package com.example.interviewdemo;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 
 * Toast替换
 * 
 */
public class MToast {
	private static Toast mToast;

	public static void showToast(Context context, String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, TextUtils.isEmpty(text) ? "当前网络不可用，请检查你的网络设置！" : text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(TextUtils.isEmpty(text) ? "当前网络不可用，请检查你的网络设置！" : text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
	}

	public static void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

}