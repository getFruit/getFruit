package com.get.fruit.activity.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.get.fruit.R;
import com.get.fruit.activity.BaseFragment;
import com.get.fruit.activity.ShowFruitActivity;
import com.get.fruit.adapter.util.BaseAdapterHelper;
import com.get.fruit.adapter.util.QuickAdapter;
import com.get.fruit.bean.HomeAD;
import com.get.fruit.view.Rotate3D;

public class HomeFragment extends BaseFragment{
	int[] imageIds = new int[] 
			{
				R.drawable.a, R.drawable.b,
				R.drawable.c, R.drawable.d
				 };
	ImageView[] views = new ImageView[4];
	ImageSwitcher imswitcher;
	GestureDetector mGestureDetector;
	int i=0;
	Runnable r;
	
	private GridView mGridView;
	private QuickAdapter<HomeAD> mAdapter;
	private HomeAD[] dataAds;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		initView();
	}

	/** 
	* @Title: initView 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	*/
	private void initView() {
		//轮播图
		imswitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
		imswitcher.setFactory(new ViewFactory()
		{
			@Override
			public View makeView()
			{
				ImageView imageView = new ImageView(getActivity());
				imageView.setBackgroundColor(0xff0000);
				imageView.setScaleType(ImageView.ScaleType.FIT_START);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				return imageView;
			}
		});
		imswitcher.setImageResource(R.drawable.a);
		ShowLog("getparent");
		mGestureDetector = new GestureDetector(getActivity(), new MyGestureListener());
		imswitcher.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				imswitcher.getParent().requestDisallowInterceptTouchEvent(true);
				 mGestureDetector.onTouchEvent(event);  
				return true;
			}
		});
		
	
		//按钮
		ImageView v1 = (ImageView) findViewById(R.id.View1);
		ImageView v2 = (ImageView) findViewById(R.id.View2);
		ImageView v3 = (ImageView) findViewById(R.id.View3);
		ImageView v4 = (ImageView) findViewById(R.id.View4);
		views[0]=v1;
		views[1]=v2;
		views[2]=v3;
		views[3]=v4;
		views[0].setSelected(true);
		
		//gridview
		mGridView=(GridView) findViewById(R.id.home_gridView);
		
		mGridView.setAdapter(mAdapter=new QuickAdapter<HomeAD>(getActivity(), R.layout.item_home_gridview) {

			@Override
			protected void convert(BaseAdapterHelper helper, final HomeAD item) {
				// TODO Auto-generated method stub
				helper.setText(R.id.title, item.getName());
				helper.setText(R.id.price, String.valueOf(item.getPrice()));
				helper.setImageBitmapFromBmobFile(R.id.pic, item.getPic());
				
				helper.setOnClickListener(R.layout.item_home_gridview, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ShowToast("item:  "+item.getName());
						
						
						startAnimActivityWithData(ShowFruitActivity.class,"fruit",item);
					}
				});
			}
		});

		//启动轮播
		DownloadTask dTask = new DownloadTask();   
		dTask.execute(100);  
	}
	
	
	//改变圆点颜色
	@SuppressLint("NewApi")
	public void	setDotState(int m)
	{
	
	  for(int i=0;i<views.length;i++)
	  {
		  if(i==m)
		  {
			  ShowLog("i=m: "+i);
			  views[i].setSelected(true);
		  }
		  else
		  {
			  views[i].setSelected(false);
			  
		  }
			  
	  }
	
	}
	//轮播动作
	public void setImageSwitcherState(int direction) {
		float halfWidth=imswitcher.getWidth()/2.0f;  
		 float halfHeight=imswitcher.getHeight()/2.0f;  
		 int duration=500;  
		 int depthz=0;//viewFlipper.getWidth()/2;  
		
		 Rotate3D rdin = new Rotate3D(direction*75,0,0,halfWidth,halfHeight);
		 rdin.setDuration(duration);    
		 rdin.setFillAfter(true);
		 imswitcher.setInAnimation(rdin);   
		 Rotate3D rdout = new Rotate3D(direction*(-15),direction*(-90),0,halfWidth,halfHeight);
		 
		 rdout.setDuration(duration);    
		 rdout.setFillAfter(true);
		 imswitcher.setOutAnimation(rdout);
		 
		 
		 i=(i+direction);
		 
		 Log.i("i的值",String.valueOf(i));
		int p= i%4;
		 Log.i("p的值",String.valueOf(p));
		if(p>=0)
		{
			setDotState(p);
		imswitcher.setImageResource(imageIds[p]);
		
		}else
		{
			
		int	k=4+p;
		setDotState(k);
			imswitcher.setImageResource(imageIds[k]);
			
		}
	}
	private class MyGestureListener implements GestureDetector.OnGestureListener
	{

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			setImageSwitcherState(velocityX >0?-1:1);  
			return true;
		}

		

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			
          int p= i%4;
			
			if(p>=0)
			{
				ShowToast(String.valueOf(p));
			}else
			{
				
				int k =4+p;
				ShowToast(String.valueOf(k));
			}
			return true;
		}

		
		
	}
	
	
	
	 class DownloadTask extends AsyncTask
	 {

		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			
			for(int i=0;i<Integer.MAX_VALUE;i++)
			{
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 publishProgress(null);
				
			}
			
			
			return null;
		}
		
		
		@Override
		protected void onProgressUpdate(Object... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			setImageSwitcherState(1);  
			
		 
		}
		
	 }
	 
	 
}
