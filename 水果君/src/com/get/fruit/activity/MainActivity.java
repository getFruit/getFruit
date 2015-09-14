package com.get.fruit.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

import com.get.fruit.R;
import com.get.fruit.activity.fragment.CartFragment;
import com.get.fruit.activity.fragment.CartFragment.CartCallBack;
import com.get.fruit.activity.fragment.CategoryFragment;
import com.get.fruit.activity.fragment.GardenFragment;
import com.get.fruit.activity.fragment.HomeFragment;
import com.get.fruit.activity.fragment.PersonFragment;
import com.get.fruit.util.StringUtils;
import com.get.fruit.view.HeaderLayout;
import com.get.fruit.view.HeaderLayout.onLeftImageButtonClickListener;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;

public class MainActivity extends BaseActivity implements OnClickListener,
		 CartCallBack {

	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private ImageButton[] mButtons = new ImageButton[5];
	private Fragment fHome, fCategory, fPerson, fCart, fGarden;
	private Fragment[] mFragments;
	private static int currentSelect;
	private CharSequence address = "天津";
	private Intent intent;
	private int to = 0;// 需要前往的fragment

	private onLeftImageButtonClickListener homeLeftListener = new onLeftImageButtonClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			ShowToast("暂时只支持天津地区，更多地区稍后支持！");
			Intent intent = new Intent(MainActivity.this,
					LocationActivity.class);
			startAnimActivityForResult(intent);
		}
	};
	private onLeftImageButtonClickListener baseLeftListener = new onLeftImageButtonClickListener() {
		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			changePage(currentSelect);
		}
	};
	private onRightImageButtonClickListener mRightButtonSaerch=new onRightImageButtonClickListener() {

		@Override
		public void onClick() {
			// 点击search按钮 事件响应
			startAnimActivity(ListFruitsActivity.class);
		}
	};
	private onRightImageButtonClickListener deleteListener=null;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			Bundle b = data.getExtras();
			address = b.getString("address");
			mHeaderLayout.setLeftText(address);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
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

		initTopBarForBoth("水果君", R.drawable.base_action_bar_addrees_selector,
				address, homeLeftListener,

				R.drawable.base_action_bar_search_selector, null,
				mRightButtonSaerch, 4);

		mButtons[0] = (ImageButton) findViewById(R.id.ib_home);
		mButtons[1] = (ImageButton) findViewById(R.id.ib_category);
		mButtons[2] = (ImageButton) findViewById(R.id.ib_person);
		mButtons[3] = (ImageButton) findViewById(R.id.ib_cart);
		mButtons[4] = (ImageButton) findViewById(R.id.ib_garden);

		fHome = new HomeFragment();
		fCategory = new CategoryFragment();
		fPerson = new PersonFragment();
		fCart = new CartFragment();
		fGarden = new GardenFragment();

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mFragments = new Fragment[] { fHome, fCategory, fPerson, fCart, fGarden };
		mViewPager.setOffscreenPageLimit(3);
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

		// changeFonts(mHeaderLayout, this);
	}

	private void initEvent() {
		// 底部按钮事件
		for (ImageButton b : mButtons) {
			b.setOnClickListener(this);
		}

		// viewPager滑动事件
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
					}

					@Override
					public void onPageScrollStateChanged(int state) {
					}

					@Override
					public void onPageSelected(int position) {
						ShowLog("onPageSelected  " + position);
						//setSelect(mViewPager.getCurrentItem());

						setSelect(position);
					}

					
				});
	}

	public void setSelect(int position) {
		ShowLog("setSelect");
		for (ImageButton b : mButtons) {
			b.setSelected(false);
		}
		mButtons[position].setSelected(true);
		
		switch (position) {
		case 0:
			mHeaderLayout.setTitleAndLeftImageButton("水果君",
					R.drawable.base_action_bar_addrees_selector, address,
					homeLeftListener, 4);
			mHeaderLayout.setRightButtonAndText(R.drawable.base_action_bar_search_selector, "");
			mHeaderLayout.setOnRightImageButtonClickListener(mRightButtonSaerch);
			
			break;
		case 1:
			mHeaderLayout.setTitleAndLeftImageButton("分类",
					R.drawable.base_action_bar_back_bg_selector, null,
					baseLeftListener, 1);
			mHeaderLayout.setRightButtonAndText(R.drawable.base_action_bar_search_selector, "");
			mHeaderLayout.setOnRightImageButtonClickListener(mRightButtonSaerch);
			break;
		case 2:
			ShowLog("person");
			mHeaderLayout.setTitleAndLeftImageButton("个人中心",
					R.drawable.base_action_bar_back_bg_selector, null,
					baseLeftListener, 1);
			mHeaderLayout.setRightButtonAndText(R.drawable.base_action_bar_search_selector, "");
			mHeaderLayout.setOnRightImageButtonClickListener(mRightButtonSaerch);
			
			break;
		case 3:
			ShowLog("cart");
			mHeaderLayout.setTitleAndLeftImageButton("购物车",
					R.drawable.base_action_bar_back_bg_selector, null,
					baseLeftListener, 1);
			break;
		case 4:
			mHeaderLayout.setTitleAndLeftImageButton("果园",
					R.drawable.base_action_bar_back_bg_selector, null,
					baseLeftListener, 1);
			mHeaderLayout.setRightButtonAndText(R.drawable.base_action_bar_search_selector, "");
			mHeaderLayout.setOnRightImageButtonClickListener(mRightButtonSaerch);
			break;
		default:
			break;
		}
	}
	public void changePage(int currentItem) {
		// TODO Auto-generated method stub
		ShowLog("changePage  " + currentItem);
		mViewPager.setCurrentItem(currentItem);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ib_home:
			changePage(0);
			break;
		case R.id.ib_category:
			changePage(1);
			break;
		case R.id.ib_person:
			changePage(2);
			break;
		case R.id.ib_cart:
			changePage(3);
			break;
		case R.id.ib_garden:
			changePage(4);
			break;

		default:
			break;
		}

	}

	// 再按一次退出
	private long mPressedTime = 0;
	private int currentTabIndex;

	@Override
	public void onBackPressed() {

		long mNowTime = System.currentTimeMillis();// 获取第一次按键时间
		if ((mNowTime - mPressedTime) > 2000) {// 比较两次按键时间差
			mPressedTime = mNowTime;
			ShowToast("再按一次退出程序");
		} else {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			System.exit(0);
			// super.onBackPressed();
		}
	}

	// here
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		intent = getIntent();
		to = intent.getIntExtra("to", 0);
		ShowLog("onResume>>   to..." + to);
		this.onClick(mButtons[to]);
		super.onResume();
	}

	// homeFragment button 点击事件
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

	// categoryFragment button点击事件
	public void iconClick(View v) {
		playHeartbeatAnimation(v);
		String categoryBy = (String) v.getTag();
		ShowToast("click: " + categoryBy);
		if (!StringUtils.isEmpty(categoryBy)) {
			startAnimActivityWithData(CategorySelectActivity.class,
					"categoryBy", categoryBy);
		}
	}
	/*

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自动生成的方法存根
		int Action = event.getAction();
		if (Action == MotionEvent.ACTION_DOWN) {
			playHeartbeatAnimation(v);
		}
		return false;
	}
*/
	
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.get.fruit.activity.fragment.CartFragment.CartCallBack#getRightButton
	 * ()
	 */
	@Override
	public HeaderLayout getHeaderLayout() {
		// TODO Auto-generated method stub
		return mHeaderLayout;
	}
}
