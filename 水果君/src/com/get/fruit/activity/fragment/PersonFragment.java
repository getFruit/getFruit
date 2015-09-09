package com.get.fruit.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.get.fruit.R;
import com.get.fruit.activity.AddFruitActivity;
import com.get.fruit.activity.BaseFragment;

public class PersonFragment extends BaseFragment {

	private Button me;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ShowLog("onCreateView....per");
		return inflater.inflate(R.layout.fragment_person, container, false);
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
		me=(Button) findViewById(R.id.me);
		me.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startAnimActivity(AddFruitActivity.class);
			}
		});
	}

	
	
	
}
