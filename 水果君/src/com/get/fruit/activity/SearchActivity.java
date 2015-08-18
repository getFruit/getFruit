package com.get.fruit.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.get.fruit.R;


public class SearchActivity extends BaseActivity implements OnClickListener{

	private EditText inputKeyWord;
	private Button keywordButton;
	private LinearLayout keyWordsLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
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
		initTopBarForLeft("ËÑË÷");
		inputKeyWord=(EditText) findViewById(R.id.et_inputkeyword);
		keywordButton=(Button) findViewById(R.id.keyword);
		keyWordsLayout=(LinearLayout) findViewById(R.id.keywords);
		
				
	}
	
	public Button newKeyWordButton(String text){
		Button button=new Button(this);
		button.setBackgroundDrawable(getResources().getDrawable(R.drawable.keyword_shape));
		button.setTextColor(getResources().getColor(R.color.keyworld_shape_button));
		button.setTextSize(26);
		LayoutParams  mLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		mLayoutParams.setMargins(8, 8, 8, 8);
		button.setPadding(10, 10, 10, 10);
		button.setLayoutParams(mLayoutParams);
		button.setText(text);
		return button;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}



