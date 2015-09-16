package com.get.fruit.activity;


import android.R.integer;
import android.annotation.SuppressLint;
import android.app.DownloadManager.Query;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

import com.get.fruit.R;
import com.get.fruit.bean.CartItem;
import com.get.fruit.bean.Fruit;
import com.get.fruit.util.PixelUtil;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;

public class DetailActivity extends BaseActivity {

	private Fruit fruit;
	private PopupWindow popupwindow;

	private EditText counEditText;
	private TextView  count,origin,describe,price,total,name;
	private ImageButton dec,inc;
	private Button addtocart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initView();
		initEvent();
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
		initTopBarForBoth("详情", R.drawable.base_action_bar_back_login_selector, null, new OnLeftClickListenerFinishMe(), R.drawable.details_menu, null, new onRightImageButtonClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				if (popupwindow != null&&popupwindow.isShowing()) {
					popupwindow.dismiss();
					return;
				} else {
					if(popupwindow==null){
						initShareMenu();
					}
					openPopuWindow(mHeaderLayout.getRightImageButton());
				}
			}
		}, 1);
	
		count=(TextView) findViewById(R.id.detail_count);
		origin=(TextView) findViewById(R.id.detail_origin);
		describe=(TextView) findViewById(R.id.detail_describe);
		price=(TextView) findViewById(R.id.detail_price);
		total=(TextView) findViewById(R.id.detail_total);
		name=(TextView) findViewById(R.id.detail_name);
		counEditText=(EditText) findViewById(R.id.detail_editText_count);
		dec=(ImageButton) findViewById(R.id.detail_imageButton1);
		inc=(ImageButton) findViewById(R.id.detail_imageButton2);
		addtocart=(Button) findViewById(R.id.detail_addtocart);
	}
	

	/** 
	* @Title: initEvent 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	*/
	private void initEvent() {
		// TODO Auto-generated method stub
		dec.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int c=Integer.valueOf((String) count.getText())-1;
				if (c==0) {
					return;
				}
				count.setText(c+"");
			}
		});
		inc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int c=Integer.valueOf((String) count.getText());
				if (c==fruit.getCount()) {
					return;
				}
				count.setText(c+"");
			}
		});
		addtocart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (addtocart.getText().equals("查看购物车")) {
					startAnimActivityToFragment(MainActivity.class, 3);
				}
				int c=Integer.valueOf((String) count.getText());
				if (c==0) {
					ShowToast("请选择数量");
					return;
				}
				final CartItem cartItem=new CartItem();
				BmobQuery<CartItem> query=new BmobQuery<CartItem>();
				
				cartItem.setMine(me);
				cartItem.setFruit(fruit);
				cartItem.setCount(c);
				cartItem.save(DetailActivity.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						addtocart.setText("查看购物车");
					}
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						ShowToast("添加失败");
					}
				});
			}
		});
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		fruit=(Fruit) intent.getSerializableExtra("fruit");
		query(fruit);
		super.onResume();
	}
	
	
	/** 
	* @Title: query 
	* @Description: TODO
	* @param @param fruit2
	* @return void
	* @throws 
	*/
	private void query(Fruit fruit2) {
		// TODO Auto-generated method stub
		BmobQuery<Fruit> query=new BmobQuery<Fruit>();
		query.include("Shop");
		query.getObject(this, fruit2.getObjectId(), new GetListener<Fruit>() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(Fruit arg0) {
				// TODO Auto-generated method stub
				fruit=arg0;
			}
		});
	}

	public void setView(){
		
	}

	//分享菜单
	OnClickListener menuItemClick;
	public void initShareMenu(){

		menuItemClick=new OnClickListener() {
			
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
		View menu=getLayoutInflater().inflate(R.layout.include_pop_sharemenu, null,false);
		
		popupwindow=new PopupWindow(menu,PixelUtil.dp2px(120),PixelUtil.dp2px(160));
		popupwindow.setAnimationStyle(R.style.AnimationFade);
		popupwindow.setFocusable(true);  
		popupwindow.setOutsideTouchable(true);  
		popupwindow.setBackgroundDrawable(new BitmapDrawable());  
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
		
		popupwindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams params=DetailActivity.this.getWindow().getAttributes();  
			    params.alpha=1f;  
			    DetailActivity.this.getWindow().setAttributes(params); 
			}
		});
		
		
		TextView comment=(TextView) menu.findViewById(R.id.comment);
		TextView collect=(TextView) menu.findViewById(R.id.collect);
		TextView share=(TextView) menu.findViewById(R.id.share);
		ShowLog(String.valueOf(menuItemClick));
		ShowLog(String.valueOf(comment==null));
		comment.setOnClickListener(menuItemClick);
		collect.setOnClickListener(menuItemClick);
		share.setOnClickListener(menuItemClick);
		
	}
	
	
	/** 
	* @Title: openPopuWindow 
	* @Description: TODO
	* @param View 
	* @return void
	* @throws 
	*/
	@SuppressLint("NewApi")
	private void openPopuWindow(View view) {
		// TODO Auto-generated method stub
		popupwindow.showAsDropDown(view);
		WindowManager.LayoutParams params=DetailActivity.this.getWindow().getAttributes();  
	    params.alpha=0.5f;  
	    DetailActivity.this.getWindow().setAttributes(params); 
	}
}
