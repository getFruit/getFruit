package com.get.fruit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobGeoPoint;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.get.fruit.R;

public class AddressSelectActivity extends Activity {
	public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;

    public BmobGeoPoint address_point=null;
    public WebView mwebview;
    private String five_address="";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_select);
		mwebview=(WebView)findViewById(R.id.webview1);
		 mwebview.getSettings().setJavaScriptEnabled(true);  
		mwebview.loadUrl(" file:///android_asset/jsonp.html ");   
		mwebview.addJavascriptInterface(new Jsway(), "jsway");
		
	}
    
    private final class Jsway{
    	@JavascriptInterface
		public void get_address(String as)
    	{
    		
    		five_address=as;
    		Toast.makeText(AddressSelectActivity.this, as , Toast.LENGTH_LONG).show();  
    	}
    }
    
    
	  private void initBaidu() {
	        // 初始化地图Sdk
	        SDKInitializer.initialize(this);
	        // 初始化定位sdk
	        initBaiduLocClient();
	    }
	
	  private void initBaiduLocClient() {
	        mLocationClient = new LocationClient(this.getApplicationContext());
	        mMyLocationListener = new MyLocationListener();
	        mLocationClient.registerLocationListener(mMyLocationListener);
	        initLocation();
	        mLocationClient.start();
	    }
	  public class MyLocationListener implements BDLocationListener {

	        @Override
	        public void onReceiveLocation(BDLocation location) {
	            // Receive Location
	            double latitude = location.getLatitude();//维度
	            double longtitude = location.getLongitude();//经度
	            String mCity = location.getCity();//城市
	            String mDistrict = location.getDistrict();//区
	            String mStreet=location.getStreet();//街
	            String m_street_number=location.getStreetNumber();//街号
	            TextView tv=(TextView)findViewById(R.id.address_select_test);
	            TextView tv1=(TextView)findViewById(R.id.address_select_test2);
	            TextView tv2=(TextView)findViewById(R.id.address_select_test3);
	            tv.setText(mCity+mDistrict+mStreet+m_street_number);
	            tv1.setText(String.valueOf(latitude));
	            tv2.setText(String.valueOf(longtitude));
	            address_point=new BmobGeoPoint(longtitude, latitude);
	            
	            
	        }
	    }
	  private void initLocation() {
	        LocationClientOption option = new LocationClientOption();
	        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
	        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//	        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
	        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
	        option.setOpenGps(true);//可选，默认false,设置是否使用gps
	        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
	        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
	        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
	        mLocationClient.setLocOption(option);
	    }
	public void getlocation(View view)
	{
		initBaidu();
		mwebview.loadUrl("javascript:getAddress()");
		TextView t1=(TextView)findViewById(R.id.address_select_test4);
		t1.setText(five_address);
	}
}
