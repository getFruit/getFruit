package com.get.fruit.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.get.fruit.R;
import com.get.fruit.bean.Fruit;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;

public class DetailActivity extends BaseActivity {

	private Fruit fruit;
	private PopupWindow popupwindow;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
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
		// TODO Auto-generated method stub
		initTopBarForBoth("œÍ«È", R.drawable.base_action_bar_back_login_selector, null, new OnLeftClickListenerFinishMe(), R.drawable.details_menu, null, new onRightImageButtonClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				if (popupwindow != null&&popupwindow.isShowing()) {
					popupwindow.dismiss();
					return;
				} else {
					initShareMenu();
					popupwindow.showAsDropDown(mHeaderLayout, 0, 1,Gravity.RIGHT);
				}
			}
		}, 1);
	}
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		fruit=(Fruit) intent.getSerializableExtra("fruit");
		super.onResume();
	}
	
	
	//∑÷œÌ≤Àµ•
	public void initShareMenu(){

		View menu=getLayoutInflater().inflate(R.layout.include_pop_sharemenu, null,false);
		
		popupwindow=new PopupWindow(menu);
		popupwindow.setAnimationStyle(R.style.AnimationFade);
		menu.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(popupwindow!=null && popupwindow.isShowing()){
					popupwindow.dismiss();
					popupwindow=null;
				}
				return false;
			}
		});
		
		
		OnClickListener menuItemClick=new OnClickListener() {
		
			boolean collected=false;
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switch (arg0.getId()) {
				case R.id.comment:
					
					break;
				case R.id.collect:
					arg0.setSelected(!collected);
					collected=!collected;
					break;
				case R.id.share:
					
					break;

				default:
					break;
				}
			}
		};
		TextView comment=(TextView) findViewById(R.id.comment);
		TextView collect=(TextView) findViewById(R.id.collect);
		TextView share=(TextView) findViewById(R.id.share);
		comment.setOnClickListener(menuItemClick);
		collect.setOnClickListener(menuItemClick);
		share.setOnClickListener(menuItemClick);
		
	}
}
