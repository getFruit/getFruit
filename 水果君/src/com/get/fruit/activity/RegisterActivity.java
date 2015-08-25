package com.get.fruit.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.im.util.BmobLog;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;

import com.get.fruit.BmobConstants;
import com.get.fruit.Config;
import com.get.fruit.R;
import com.get.fruit.bean.User;
import com.get.fruit.util.CommonUtils;
import com.get.fruit.util.StringUtils;
import com.get.fruit.view.HeaderLayout;
import com.get.fruit.view.HeaderLayout.HeaderStyle;
import com.get.fruit.view.TimeButton;


public class RegisterActivity extends BaseActivity implements OnClickListener{
	private LinearLayout step1,step2,step3;
    private EditText et_username,et_password,et_password2,et_phone,et_code;
    private Button registerButton;
    private TimeButton getcodeButton;
    private TextView tt_showmessage;
    int step=1;
    Bundle msavedInstanceState;
    String phoneNumber="";
    	@Override
    	protected void onCreate(Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.activity_register);
    		BmobSMS.initialize(this,Config.applicationId);
    		initTopBarForLeft("注册");
    		initView();
    		/*
    		//for test
    		step1.setVisibility(View.GONE);
    		step2.setVisibility(View.GONE);
	        step3.setVisibility(View.VISIBLE);
	        phoneNumber="13312345353";
	        step=3;*/
    		msavedInstanceState=savedInstanceState;
    	}
		public void initView() {
			step1=(LinearLayout) findViewById(R.id.step1);
			step2=(LinearLayout) findViewById(R.id.step2);
			step3=(LinearLayout) findViewById(R.id.step3);
			et_username = (EditText) findViewById(R.id.editText_username);
    		et_password = (EditText) findViewById(R.id.editText_password);
    		et_password2 = (EditText) findViewById(R.id.editText_password2);
    		et_phone = (EditText) findViewById(R.id.editText_phone);
    		et_code=(EditText) findViewById(R.id.editText_code);
    		tt_showmessage=(TextView) findViewById(R.id.textView_showmessage);
    		registerButton = (Button) findViewById(R.id.bt_register);
    		getcodeButton = (TimeButton) findViewById(R.id.bt_getcode);
    		registerButton.setOnClickListener(this);
		}
    	private void register(){
    		String name = et_username.getText().toString();
    		String password = et_password.getText().toString();
    		String password2 = et_password2.getText().toString();
    		
    		
    		if (TextUtils.isEmpty(name)) {
    			ShowToast(R.string.toast_error_username_null);
    			return;
    		}

    		if (TextUtils.isEmpty(password)) {
    			ShowToast(R.string.toast_error_password_null);
    			return;
    		}
    		if (!password2.equals(password)) {
    			ShowToast(R.string.toast_error_comfirm_password);
    			return;
    		}
    		
    		boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
    		if(!isNetConnected){
    			ShowToast(R.string.network_tips);
    			return;
    		}
    		
    		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
    		progress.setMessage("正在注册...");
    		progress.setCanceledOnTouchOutside(false);
    		progress.show();
    		final User bu = new User();
    		bu.setUsername(name);
    		bu.setPassword(password);
    		bu.setMobilePhoneNumber(phoneNumber);
    		bu.setMobilePhoneNumberVerified(true);
    		//将user和设备id进行绑定
    		bu.setDeviceType("android");
    		bu.setInstallId(BmobInstallation.getInstallationId(this));
    		bu.signUp(RegisterActivity.this, new SaveListener() {

    			@Override
    			public void onSuccess() {
    				// TODO Auto-generated method stub
    				progress.dismiss();
    				ShowToast("注册成功");
    				// 将设备与username进行绑定
    				userManager.bindInstallationForRegister(bu.getUsername());
    				sendBroadcast(new Intent(BmobConstants.ACTION_REGISTER_SUCCESS_FINISH));
    				// 启动主页
    				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
    				startActivity(intent);
    				finish();
    				
    			}

    			@Override
    			public void onFailure(int arg0, String arg1) {
    				// TODO Auto-generated method stub
    				BmobLog.i(arg1);
    				ShowToast("注册失败:" + arg0+"-"+arg1);
    				progress.dismiss();
    			}
    		});
    	}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View arg0) {
			ShowLog(step+"");

			switch (step) {
			case 1:
				phoneNumber = et_phone.getText().toString();
				if(!StringUtils.isPhoneNumberValid(phoneNumber)){
					ShowToast("电话号码不正确！");
					break;
				}
				BmobSMS.requestSMSCode(this, phoneNumber,"水果君验证码",new RequestSMSCodeListener() {

				    @Override
				    public void done(Integer smsId,BmobException ex) {
				        // TODO Auto-generated method stub
				        if(ex!=null){
				        	ShowToast("验证码发送失败！");
				        	return;
				        }
				        step1.setVisibility(View.GONE);
				        step2.setVisibility(View.VISIBLE);
				        tt_showmessage.setText("已经向 "+phoneNumber+"发送了验证码，请注意查收！");
				        ((TimeButton) getcodeButton).restartTimer();
				        getcodeButton.setTextAfter("秒后重新获取").setTextBefore("重新获取").setLenght(30 * 1000);
				        getcodeButton.onCreate(msavedInstanceState);
				        getcodeButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								ShowLog("reeeeeeeee");
								// TODO Auto-generated method stub
								if(requestCode(phoneNumber)){
									return;
								}
							}
						});
				        step=2;
				    }
				});
				break;
			case 2:
				final String code = et_code.getText().toString();
				if(TextUtils.isEmpty(code)){
					ShowToast("验证码为空！");
					return;
				}
				
				BmobSMS.verifySmsCode(this,phoneNumber, code, new VerifySMSCodeListener() {

				    @Override
				    public void done(BmobException ex) {
				        // TODO Auto-generated method stub
				        if(ex!=null){//短信验证码已验证成功
				            tt_showmessage.setText("验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
				            return;
				        }
				        step2.setVisibility(View.GONE);
				        step3.setVisibility(View.VISIBLE);
				        step=3;
				    }
				});
				
				break;
			case 3:
				register();
				break;

			default:
				break;
			}
		}

		
		static boolean f=true;
		public boolean requestCode(final String phoneNumber) {
			BmobSMS.requestSMSCode(this, phoneNumber,"水果君验证码",new RequestSMSCodeListener() {

			    @Override
			    public void done(Integer smsId,BmobException ex) {
			        // TODO Auto-generated method stub
			        if(ex!=null){
			        	ShowToast("验证码发送失败！");
			        	f=false;
			        	return ;
			        }
			   }
			});
			
			return f;
		}
		
		
		public void initTopBarForLeft(String titleName) {
			mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
			mHeaderLayout.init(HeaderStyle.TITLE_LIFT_IMAGEBUTTON);
			mHeaderLayout.setTitleAndLeftImageButton(
					titleName,
					R.drawable.base_action_bar_back_login_selector,
					null,
					new OnLeftButtonClickListener());
		}
    }



