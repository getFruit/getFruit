package com.get.fruit.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.get.fruit.R;
import com.get.fruit.activity.fragment.CartFragment;
import com.get.fruit.activity.fragment.CategoryFragment;
import com.get.fruit.activity.fragment.GardenFragment;
import com.get.fruit.activity.fragment.HomeFragment;
import com.get.fruit.activity.fragment.PersonFragment;
import com.get.fruit.view.HeaderLayout.onLeftImageButtonClickListener;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;

public class MainActivity extends BaseActivity {

	ButtonsOnclickListener mButtonsOnclickListener=new ButtonsOnclickListener();
	
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	//private ImageButton mHome,mCategory,mPerson,mCart,mGarden;
	private ImageButton[] mButtons=new ImageButton[5];
	private Fragment fHome,fCategory,fPerson,fCart,fGarden;
	private Fragment[] mFragments;
	private  static int currentSelect;
	private CharSequence address="天津";
	private onLeftImageButtonClickListener homeLeftListener=new onLeftImageButtonClickListener() {
		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			ShowToast("暂时只支持天津地区，更多地区稍后支持！");
			Intent intent =new Intent(MainActivity.this,LocationActivity.class);
			startAnimActivityForResult(intent);
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case RESULT_OK:
		    Bundle b=data.getExtras();
		    address=b.getString("address");
		    mHeaderLayout.setLeftText(address);
		    break;
		default:
		    break;
		    }
		}
	private onLeftImageButtonClickListener baseLeftListener=new onLeftImageButtonClickListener() {
		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			setSelect(currentSelect);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		initView();
		mButtons[0].setSelected(true);
		initEvent();
	}

	/** 
	* @Title: initEvent 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	*/

	public void initView() {
		
		mViewPager=(ViewPager) findViewById(R.id.pager);
		
		mButtons[0]=(ImageButton) findViewById(R.id.ib_home);
		mButtons[1]=(ImageButton) findViewById(R.id.ib_category);
		mButtons[2]=(ImageButton) findViewById(R.id.ib_person);
		mButtons[3]=(ImageButton) findViewById(R.id.ib_cart);
		mButtons[4]=(ImageButton) findViewById(R.id.ib_garden);
		
		
		fHome=new HomeFragment();
		fCategory=new CategoryFragment();
		fPerson=new PersonFragment();
		fCart=new CartFragment();
		fGarden=new GardenFragment();
		
		mFragments=new Fragment[]{fHome,fCategory,fCart,fPerson,fGarden};
		initTopBarForBoth("水果君", R.drawable.base_action_bar_addrees_selector,address,homeLeftListener,
		
		R.drawable.base_action_bar_search_selector,  null, new onRightImageButtonClickListener() {
			
			@Override
			public void onClick() {
				// 点击search按钮 事件响应
				startAnimActivity(SearchActivity.class);
			}
		});
		
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return mFragments[position];
			}

			@Override
			public int getCount() {
				return mFragments.length;
			}
		};

		mViewPager.setAdapter(mAdapter);
		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

		changeFonts(mHeaderLayout, this);
	}
	


	
	private void initEvent() {
		//底部按钮事件
		for(ImageButton b: mButtons){
			b.setOnClickListener(mButtonsOnclickListener);
		}
		
		//viewPager滑动事件
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position,
					float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}

			@Override
			public void onPageSelected(int position) {
				setSelect(mViewPager.getCurrentItem());
			}
		});
}

	
	public void setSelect(int currentItem) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(currentItem);
		
		for(ImageButton b:mButtons){
			b.setSelected(false);
		}
		mButtons[currentItem].setSelected(true);
		//mHeaderLayout.setVisibility(View.VISIBLE);
		switch (currentItem) {
		case 0:
			mHeaderLayout.setTitleAndLeftImageButton("水果君", R.drawable.base_action_bar_addrees_selector,address, homeLeftListener);
			break;
		case 1:
			mHeaderLayout.setTitleAndLeftImageButton("分类", R.drawable.base_action_bar_back_bg_selector,null, baseLeftListener);
			break;
		case 2:
			mHeaderLayout.setTitleAndLeftImageButton("个人中心", R.drawable.base_action_bar_back_bg_selector,null, baseLeftListener);
			break;
		case 3:
			mHeaderLayout.setTitleAndLeftImageButton("购物车", R.drawable.base_action_bar_back_bg_selector,null, baseLeftListener);
			break;
		case 4:
			mHeaderLayout.setTitleAndLeftImageButton("果园", R.drawable.base_action_bar_back_bg_selector,null, baseLeftListener);
			break;
		}
		
	}
	
	

class ButtonsOnclickListener implements OnClickListener{
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ib_home:
			setSelect(0);
			break;
		case R.id.ib_category:
			setSelect(1);
			break;
		case R.id.ib_person:
			setSelect(2);
			break;
		case R.id.ib_cart:
			setSelect(3);
			break;
		case R.id.ib_garden:
			setSelect(4);
			break;
			
		default:
			break;
		}
		
	}
	
}
	
	
	
	
	
	//再按一次退出
	private long mPressedTime = 0;
	private int currentTabIndex;
	@Override
	public void onBackPressed() {
		
		long mNowTime = System.currentTimeMillis();//获取第一次按键时间
		if((mNowTime - mPressedTime) > 2000){//比较两次按键时间差
			mPressedTime = mNowTime;
			ShowToast("再按一次退出程序");
		}else {
			Intent startMain = new Intent(Intent.ACTION_MAIN);     
			startMain.addCategory(Intent.CATEGORY_HOME);     
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
			startActivity(startMain);     
			System.exit(0);  
			//super.onBackPressed();
		}
	}
	

	public void homeIBClick(View arg0) {
		// TODO Auto-generated method stub
		playHeartbeatAnimation(arg0);
		switch (arg0.getId()) {
		case R.id.imageButton1:
			
			break;
			
		case R.id.imageButton2:
			
			break;
			
		case R.id.imageButton3:
			
			break;
			
		case R.id.imageButton4:
			
			break;
			
		case R.id.imageButton5:
			
			break;
			

		default:
			break;
		}
	}
	
	
	@SuppressLint("NewApi")
	public class DepthPageTransformer implements ViewPager.PageTransformer {
		private static final float MIN_SCALE = 0.75f;

		@Override
		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 0) { // [-1,0]
				// Use the default slide transition when moving to the left page
				view.setAlpha(1);
				view.setTranslationX(0);
				view.setScaleX(1);
				view.setScaleY(1);

			} else if (position <= 1) { // (0,1]
				// Fade the page out.
				view.setAlpha(1 - position);

				// Counteract the default slide transition
				view.setTranslationX(pageWidth * -position);

				// Scale the page down (between MIN_SCALE and 1)
				float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
						* (1 - Math.abs(position));
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}
	
	
	public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
		private static final float MIN_SCALE = 0.85f;
		private static final float MIN_ALPHA = 0.5f;

		@Override
		@SuppressLint("NewApi")
		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();
			int pageHeight = view.getHeight();

			if (position < -1) { // [-Infinity,-1)
									// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 1) // a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
			{ // [-1,1]
				// Modify the default slide transition to shrink the page as
				// well
				float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
				float vertMargin = pageHeight * (1 - scaleFactor) / 2;
				float horzMargin = pageWidth * (1 - scaleFactor) / 2;
				if (position < 0) {
					view.setTranslationX(horzMargin - vertMargin / 2);
				} else {
					view.setTranslationX(-horzMargin + vertMargin / 2);
				}

				// Scale the page down (between MIN_SCALE and 1)
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

				// Fade the page relative to its size.
				view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
						/ (1 - MIN_SCALE) * (1 - MIN_ALPHA));

			} else { // (1,+Infinity]
						// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}

}
