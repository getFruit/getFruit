package com.get.fruit.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.get.fruit.R;

public class OrderEditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_edit);
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
	private void initEvent() {
		// TODO Auto-generated method stub
		
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
	private String[] method_item={"送货上门","自取","代取","快递"};
	private String[] payment_item={"现金支付","微信支付","支付宝支付"};
	
	//送货方式函数
	public void send_method(View view)
	{
		
		
		
		new AlertDialog.Builder(this)
	 	.setTitle("请选择")
	 	.setIcon(android.R.drawable.ic_dialog_info)                
	 	.setSingleChoiceItems(method_item, 0, 
	 	  new DialogInterface.OnClickListener() {
	 	                              
	 	     public void onClick(DialogInterface dialog, int which) {
	 	    	TextView method_view=(TextView) findViewById(R.id.order_edit_method);
	 	    	method_view.setText(method_item[which]);
	 	    	dialog.cancel();
	 	     }
	 	  }
	 	)
	 	.setNegativeButton("取消", null)
	 	.show();
	}
	//付款方式函数
	public void select_payment(View view)
	{
		
		
		
		new AlertDialog.Builder(this)
	 	.setTitle("请选择")
	 	.setIcon(android.R.drawable.ic_dialog_info)                
	 	.setSingleChoiceItems(payment_item, 0, 
	 	  new DialogInterface.OnClickListener() {
	 	                              
	 	     public void onClick(DialogInterface dialog, int which) {
	 	    	TextView method_view=(TextView) findViewById(R.id.order_edit_payment);
	 	    	method_view.setText(payment_item[which]);
	 	    	dialog.cancel();
	 	    	
	 	     }
	 	  }
	 	)
	 	.setNegativeButton("取消", null)
	 	.show();
	}
	public void order_address(View view)
	{
		final EditText edit_text=new EditText(this);
		new AlertDialog.Builder(this)
	 	.setTitle("请输入送货地址")
	 	.setIcon(android.R.drawable.ic_dialog_info)
	 	.setView(edit_text)
	 	.setPositiveButton("确定", new DialogInterface.OnClickListener() {                
	 	    @Override  
	 	    public void onClick(DialogInterface dialog, int which) {  
	 	        // TODO Auto-generated method stub  
	 	         TextView address_view=(TextView)findViewById(R.id.order_edit_address);
	 	        address_view.setText(edit_text.getText());
	 	    }})  
	 	.setNegativeButton("取消", null)
	 	.show();
	}
	//留言函数
	public void order_words(View view)
	{
		final EditText edit_text=new EditText(this);
		new AlertDialog.Builder(this)
	 	.setTitle("请输入订单留言")
	 	.setIcon(android.R.drawable.ic_dialog_info)
	 	.setView(edit_text)
	 	.setPositiveButton("确定", new DialogInterface.OnClickListener() {                
	 	    @Override  
	 	    public void onClick(DialogInterface dialog, int which) {  
	 	        // TODO Auto-generated method stub  
	 	         TextView words_view=(TextView)findViewById(R.id.order_edit_words);
	 	        words_view.setText(edit_text.getText());
	 	    }})  
	 	.setNegativeButton("取消", null)
	 	.show();
	}
	public void submit_order(View view)
	{
		
	}
}
