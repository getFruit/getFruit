package com.get.fruit.activity;

import com.get.fruit.R;
import com.get.fruit.R.drawable;
import com.get.fruit.R.layout;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ListFruitsActivity extends BaseActivity {

	private String searchBy;
	private String keyWord;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listfruit);
		searchBy=(String) getIntent().getExtras().getString("searchBy");
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
		initTopBarForBoth("水果君", R.drawable.base_action_bar_back_bg_selector, searchBy, new OnLeftButtonClickListener(), R.drawable.base_action_bar_search_selector, null, new onRightImageButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				Intent intent =new Intent(ListFruitsActivity.this, SearchActivity.class);
				startActivityForResult(intent, 1);
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case RESULT_OK:
		    Bundle b=data.getExtras();
		    keyWord=b.getString("keyWord");
		    mHeaderLayout.setLeftText(keyWord);
		    break;
		default:
		    break;
		    }
		}
}
