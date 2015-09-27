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
	        // ��ʼ����ͼSdk
	        SDKInitializer.initialize(this);
	        // ��ʼ����λsdk
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
	            double latitude = location.getLatitude();//ά��
	            double longtitude = location.getLongitude();//����
	            String mCity = location.getCity();//����
	            String mDistrict = location.getDistrict();//��
	            String mStreet=location.getStreet();//��
	            String m_street_number=location.getStreetNumber();//�ֺ�
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
	        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
	        option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
//	        option.setScanSpan(1000);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
	        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
	        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
	        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
	        option.setIgnoreKillProcess(false);//��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
	        option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
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
