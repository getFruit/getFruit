package com.get.fruit;

import java.io.File;
import java.util.HashMap;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import cn.bmob.im.BmobChat;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.get.fruit.bean.FruitShop;
import com.get.fruit.bean.User;
import com.get.fruit.util.SharePreferenceUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * �Զ���ȫ��Applcation��
 * 
 * @ClassName: App
 * @Description: TODO
 * @date 2015-7-27 ����5:05:00
 */
public class App extends Application {

	public static App mInstance; 
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;

	public static BmobGeoPoint lastPoint = null;// ��һ�ζ�λ���ľ�γ��

	private static FruitShop myshop=null;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// �Ƿ���debugģʽ--Ĭ�Ͽ���״̬
		BmobChat.DEBUG_MODE = true;
		mInstance = this;
		init();
	}

	private void init() {
		mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		initImageLoader(getApplicationContext());
		initBmobPro();
		initBaidu();
	}

	/** 
	* @Title: initBmobPro 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	*/
	private void initBmobPro() {
		// TODO Auto-generated method stub
		BmobConfiguration config = new BmobConfiguration.Builder(mInstance).customExternalCacheDir("ˮ����").build();
		BmobPro.getInstance(mInstance).initConfig(config);
	}

	public User getCurrentUser() {
		User user = BmobUser.getCurrentUser(mInstance, User.class);
		if (user != null) {
			return user;
		}
		return null;
	}

	public static FruitShop getMyshop() {
		return myshop;
	}

	public static void setMyshop(FruitShop myshop) {
		App.mInstance.getSpUtil();
		App.myshop = myshop;
	}

	/**
	 * ��ʼ���ٶ����sdk initBaidumap
	 * 
	 * @Title: initBaidumap
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initBaidu() {
		// ��ʼ����ͼSdk
		SDKInitializer.initialize(this);
		// ��ʼ����λsdk
		initBaiduLocClient();
	}

	/**
	 * ��ʼ���ٶȶ�λsdk
	 * 
	 * @Title: initBaiduLocClient
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initBaiduLocClient() {
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
	}

	/**
	 * ʵ�ְٶȶ�λ�ص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			double latitude = location.getLatitude();
			double longtitude = location.getLongitude();
			if (lastPoint != null) {
				if (lastPoint.getLatitude() == location.getLatitude()
						&& lastPoint.getLongitude() == location.getLongitude()) {
					// BmobLog.i("���λ�ȡ������ͬ");// �����������ȡ���ĵ���λ����������ͬ�ģ����ٶ�λ
					mLocationClient.stop();
					return;
				}
			}
			lastPoint = new BmobGeoPoint(longtitude, latitude);
		}
	}

	/** ��ʼ��ImageLoader */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,BmobConstants.MyTempDir);// ��ȡ�������Ŀ¼��ַ
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// �̳߳��ڼ��ص�����
				.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// �������ʱ���URI������MD5 ����
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))// �Զ��建��·��
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// ȫ�ֳ�ʼ��������
	}

	
	
	public static App getInstance() {
		return mInstance;
	}

	SharePreferenceUtil mSpUtil;
	public static final String PREFERENCE_NAME = "_sharedinfo";
	public static HashMap<String, Long> map = new HashMap<String, Long>();
	public synchronized SharePreferenceUtil getSpUtil() {
		if (mSpUtil == null) {
			String currentId = BmobUserManager.getInstance(
					getApplicationContext()).getCurrentUserObjectId();
			String sharedName = currentId + PREFERENCE_NAME;
			mSpUtil = new SharePreferenceUtil(this, sharedName);
		}
		return mSpUtil;
	}

	NotificationManager mNotificationManager;

	public NotificationManager getNotificationManager() {
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}

	MediaPlayer mMediaPlayer;

	public synchronized MediaPlayer getMediaPlayer() {
		if (mMediaPlayer == null)
			mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
		return mMediaPlayer;
	}

	public final String PREF_LONGTITUDE = "longtitude";// ����
	private String longtitude = "";

	/**
	 * ��ȡ����//
	 * 
	 * @return
	 */
	public String getLongtitude() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		longtitude = preferences.getString(PREF_LONGTITUDE, "");
		return longtitude;
	}

	/**
	 * ���þ���
	 * 
	 * @param pwd
	 */
	public void setLongtitude(String lon) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_LONGTITUDE, lon).commit()) {
			longtitude = lon;
		}
	}
	
	

	public final String PREF_LATITUDE = "latitude";// ����
	private String latitude = "";

	/**
	 * ��ȡγ��
	 * 
	 * @return
	 */
	public String getLatitude() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		latitude = preferences.getString(PREF_LATITUDE, "");
		return latitude;
	}

	/**
	 * ����ά��
	 * 
	 * @param pwd
	 */
	public void setLatitude(String lat) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_LATITUDE, lat).commit()) {
			latitude = lat;
		}
	}

	/**
	 * �˳���¼,��ջ�������
	 */
	public void logout() {
		BmobUserManager.getInstance(getApplicationContext()).logout();
		setLatitude(null);
		setLongtitude(null);
	}
	
	
	public static void ShowLog(String msg){
		BmobLog.i(msg);
	}

	
}
