package com.get.fruit.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.SaveListener;

import com.get.fruit.BmobConstants;
import com.get.fruit.R;
import com.get.fruit.view.HeaderLayout.onLeftImageButtonClickListener;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;


public class LoginActivity extends BaseActivity{
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    
    
    BmobChatUser currentUser;
	private MyBroadcastReceiver receiver = new MyBroadcastReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initTopBarForBoth("µÇÂ½", R.drawable.base_action_bar_back_login_selector,null,
				new onLeftImageButtonClickListener() {

					@Override
					public void onClick() {
						startAnimActivity(MainActivity.class);
						finish();
					}
				},
				-1, "×¢²á", 
				new onRightImageButtonClickListener() {
		
					@Override
					public void onClick() {
						startAnimActivity(RegisterActivity.class);
					}
			}
		);
		
				
		init();
		//×¢²áÍË³ö¹ã²¥
		IntentFilter filter = new IntentFilter();
		filter.addAction(BmobConstants.ACTION_REGISTER_SUCCESS_FINISH);
		registerReceiver(receiver, filter);
		
	}


	
	private void init() {
		usernameEditText = (EditText) findViewById(R.id.editText_username_login);
		passwordEditText = (EditText) findViewById(R.id.editText_password_login);
		loginButton = (Button) findViewById(R.id.bt_login);
		
	}

	public class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && BmobConstants.ACTION_REGISTER_SUCCESS_FINISH.equals(intent.getAction())) {
				finish();
			}
		}

	}
	

	private void login(){
		String name = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();

		if (TextUtils.isEmpty(name)) {
			ShowToast(R.string.toast_error_username_null);
			return;
		}

		if (TextUtils.isEmpty(password)) {
			ShowToast(R.string.toast_error_password_null);
			return;
		}

		final ProgressDialog progress = new ProgressDialog(
				LoginActivity.this);
		progress.setMessage("ÕýÔÚµÇÂ½...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		userManager.login(name, password, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						progress.setMessage("µÇÂ½³É¹¦£¡£¡£¡");
					}
				});
				progress.dismiss();
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onFailure(int errorcode, String arg0) {
				// TODO Auto-generated method stub
				progress.dismiss();
				BmobLog.i(arg0);
				ShowToast(arg0);
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
}



