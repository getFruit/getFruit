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
	private String[] method_item={"�ͻ�����","��ȡ","��ȡ","���"};
	private String[] payment_item={"�ֽ�֧��","΢��֧��","֧����֧��"};
	
	//�ͻ���ʽ����
	public void send_method(View view)
	{
		
		
		
		new AlertDialog.Builder(this)
	 	.setTitle("��ѡ��")
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
	 	.setNegativeButton("ȡ��", null)
	 	.show();
	}
	//���ʽ����
	public void select_payment(View view)
	{
		
		
		
		new AlertDialog.Builder(this)
	 	.setTitle("��ѡ��")
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
	 	.setNegativeButton("ȡ��", null)
	 	.show();
	}
	public void order_address(View view)
	{
		final EditText edit_text=new EditText(this);
		new AlertDialog.Builder(this)
	 	.setTitle("�������ͻ���ַ")
	 	.setIcon(android.R.drawable.ic_dialog_info)
	 	.setView(edit_text)
	 	.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {                
	 	    @Override  
	 	    public void onClick(DialogInterface dialog, int which) {  
	 	        // TODO Auto-generated method stub  
	 	         TextView address_view=(TextView)findViewById(R.id.order_edit_address);
	 	        address_view.setText(edit_text.getText());
	 	    }})  
	 	.setNegativeButton("ȡ��", null)
	 	.show();
	}
	//���Ժ���
	public void order_words(View view)
	{
		final EditText edit_text=new EditText(this);
		new AlertDialog.Builder(this)
	 	.setTitle("�����붩������")
	 	.setIcon(android.R.drawable.ic_dialog_info)
	 	.setView(edit_text)
	 	.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {                
	 	    @Override  
	 	    public void onClick(DialogInterface dialog, int which) {  
	 	        // TODO Auto-generated method stub  
	 	         TextView words_view=(TextView)findViewById(R.id.order_edit_words);
	 	        words_view.setText(edit_text.getText());
	 	    }})  
	 	.setNegativeButton("ȡ��", null)
	 	.show();
	}
	public void submit_order(View view)
	{
		
	}
}
