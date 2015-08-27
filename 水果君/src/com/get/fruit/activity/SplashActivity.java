package com.get.fruit.activity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.bmob.im.BmobChat;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.get.fruit.App;
import com.get.fruit.Config;
import com.get.fruit.R;
import com.get.fruit.bean.FruitShop;

/**
 * 引导页
 * 
 * @ClassName: SplashActivity
 * @Description: TODO
 * @author smile
 * @date 2014-6-4 上午9:45:43
 */
public class SplashActivity extends BaseActivity {

	private static final int GO_HOME = 100;
	private static final int GO_LOGIN = 200;

	// 定位获取当前用户的地理位置
	private LocationClient mLocationClient;

	private BaiduReceiver mReceiver;// 注册广播接收器，用于监听网络以及验证key

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ShowLog("oncreate");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		if (!isNetConnected()) {
			ShowToast("當前無網絡連接！");
		}
		BmobChat.getInstance(this).init(Config.applicationId);
		if (userManager.getCurrentUser() != null) {
			ShowLog("query...");
			BmobQuery<FruitShop> query=new BmobQuery<FruitShop>();
			query.addWhereEqualTo("owner",userManager.getCurrentUser());
			query.findObjects(mApplication, new FindListener<FruitShop>() {
				
				@Override
				public void onSuccess(List<FruitShop> arg0) {
					// TODO Auto-generated method stub
					if (arg0.size()==0) {
						ShowLog("init shop null");
						return;
					}
					App.setMyshop(arg0.get(0));
					ShowLog("init shop successed");
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					ShowLog("init shop fail");
				}
			});
			// 开启定位
			initLocClient();
			 //注册地图 SDK 广播监听者
			IntentFilter iFilter = new IntentFilter();
			iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
			iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
			mReceiver = new BaiduReceiver();
			registerReceiver(mReceiver, iFilter);
			ShowLog("register");

			
			//updateUserInfos();
			mHandler.sendEmptyMessageDelayed(GO_HOME, 1000);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
		}
		
	}

	/**
	 * 开启定位，更新当前用户的经纬度坐标
	 * @Title: initLocClient
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initLocClient() {
		mLocationClient = App.getInstance().mLocationClient;
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式:高精度模式
		option.setCoorType("bd09ll"); // 设置坐标类型:百度经纬度
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms:低于1000为手动定位一次，大于或等于1000则为定时定位
		option.setIsNeedAddress(false);// 不需要包含地址信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				startAnimActivity(AddFruitActivity.class);//test
				/*
				startAnimActivity(MainActivity.class);
				 */
				finish();
				break;
			case GO_LOGIN:
				startAnimActivity(LoginActivity.class);
				/*
				startAnimActivity(AddFruitActivity.class);//test
				 */
				finish();
				break;
			}
		}
	};

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class BaiduReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				ShowToast("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				ShowToast("当前网络连接不稳定，请检查您的网络设置!");
			}
		}
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		stopLocation();
	}

	public void stopLocation() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
		try {
			unregisterReceiver(mReceiver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		super.onDestroy();
	}

}
