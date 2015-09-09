package com.get.fruit.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.bmob.v3.listener.DeleteListener;

import com.get.fruit.R;
import com.get.fruit.activity.BaseFragment;
import com.get.fruit.activity.bean.CartItem;
import com.get.fruit.adapter.util.QuickAdapter;
import com.get.fruit.view.HeaderLayout;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;
import com.get.fruit.view.listview.XListView;

public class CartFragment extends BaseFragment {

	private CartCallBack callBack;
	private XListView mListView;
	private QuickAdapter<CartItem> mQuickAdapter;
	private Button gopay;
	private TextView totalPrice;
	private RadioButton checkAll;
	private onRightImageButtonClickListener deleteListener=new onRightImageButtonClickListener() {
		
		@Override
		public void onClick() {
			// TODO Auto-generated method stub
			
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ShowLog("onCreateView.....cart");
		return inflater.inflate(R.layout.fragment_cart, container, false);
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
		// TODO Auto-generated method stub
		
	}

	//设置删除按钮
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		ShowLog("attach.....cart");
		callBack=(CartCallBack) activity;
	}
	
	
/*
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		callBack.getHeaderLayout().setTitleAndRightButton("购物车", null, null, null);
		ShowLog("onpause....cart");
		super.onPause();
		
	}
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		ShowLog("onresume    cart");
		callBack.getHeaderLayout().setTitleAndRightButton("购物车",-1,"删除", deleteListener);
		super.onResume();
		
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		ShowLog("onstop...cart");
		super.onStop();
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		ShowLog("onStart...cart");
		super.onStart();
	}*/



	public interface CartCallBack{
		public HeaderLayout getHeaderLayout();
	}
	
}
