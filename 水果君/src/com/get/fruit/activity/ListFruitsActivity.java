package com.get.fruit.activity;

import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.get.fruit.R;
import com.get.fruit.adapter.util.BaseAdapterHelper;
import com.get.fruit.adapter.util.QuickAdapter;
import com.get.fruit.bean.Fruit;
import com.get.fruit.util.CollectionUtils;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;
import com.get.fruit.view.listview.XListView;
import com.get.fruit.view.listview.XListView.IXListViewListener;

public class ListFruitsActivity extends BaseActivity {
	static int limit=15;
	static int currentPage=1;
	
	
	private String searchBy;
	private String keyWord;
	private EditText keyWordEditText;
	private XListView mListView;
	private QuickAdapter<Fruit> mqQuickAdapter;
	private TextView[] tabs;
	private Intent intent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listfruit);
		intent=getIntent();
		searchBy=intent.getStringExtra("searchBy");
		ShowLog(searchBy);
		keyWord=intent.getStringExtra("keyWord");
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
		initTopBarForMiddleView(null, R.drawable.base_action_bar_back_bg_selector, searchBy, new OnLeftButtonClickListener(), R.drawable.base_action_bar_person_selector, null, new onRightImageButtonClickListener() {
		
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				Intent intent =new Intent(ListFruitsActivity.this, MainActivity.class);
				intent.putExtra("to", 2);
				startAnimActivity(intent);
			}
		},1);
	
		keyWordEditText=mHeaderLayout.getmEditText();
		tabs=new TextView[]{
			(TextView) findViewById(R.id.textView0),(TextView) findViewById(R.id.textView1),(TextView) findViewById(R.id.textView2),(TextView) findViewById(R.id.textView3)	};
		
		initListView();
		
		tabsClick(tabs[0]);
	}

	
	public void initListView() {
		ShowLog("initListView"); 
		mListView=(XListView) findViewById(R.id.xListView1);
		
		mqQuickAdapter=new QuickAdapter<Fruit>(ListFruitsActivity.this,R.layout.item_listfruit){
			
			@Override
			protected void convert(final BaseAdapterHelper helper, final Fruit item) {
				// TODO Auto-generated method stub
				
				ShowLog("convert"); 
				ShowLog(item.getName());
				ShowLog("file==null        "+String.valueOf(item.getPicture()==null));
				
				
				helper.setText(R.id.list_item_name, item.getName());
				helper.setText(R.id.list_item_address, item.getOrigin());
				if(item.getCategory().getFunctions()!=null){
					
					StringBuilder stringBuilder=new StringBuilder();
					for (String string:item.getCategory().getFunctions()) {
						stringBuilder.append(string);
						stringBuilder.append("  ");
					}
					helper.setText(R.id.list_item_functions, stringBuilder.toString());
				}
				helper.setText(R.id.list_item_price, "￥ "+item.getPrice());
				helper.setText(R.id.list_item_buynum,item.getPaynum()+"");
				if (item.getPicture()!=null) {
					ShowLog(item.getPicture().getFilename());
					helper.setImageBitmapFromBmobFile(R.id.list_item_image, item.getPicture());
				}
				
				helper.setOnClickListener(R.id.list_addto, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						//addToCart();
						}
				});
				
				helper.setOnClickListener(R.id.list_item_image, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(ListFruitsActivity.this,DetailActivity.class);	
						intent.putExtra("fruit", item);
						startAnimActivity(intent);
					}
				});
			}
			
		};
		
		if (!isNetConnected()) {
			ShowToast(R.string.network_tips);
			return;
		}
		mListView.setAdapter(mqQuickAdapter);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				ShowLog("onRefresh...");
				currentPage=1;
				query();
			}
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				ShowLog("onLoadMore...");
				query();
			}
		});
		//
		mListView.pullRefreshing();
		query();

	}
	
	
	public void query(){
		ShowLog("query");
		BmobQuery<Fruit> query = new BmobQuery<Fruit>();
		query.order(order);
		query.setLimit(limit);
		query.setSkip(limit*currentPage);
		query.findObjects(ListFruitsActivity.this, new FindListener<Fruit>() {
			
			@Override
			public void onSuccess(List<Fruit> arg0) {
				// TODO Auto-generated method stub
				ShowLog("onSuccess："+arg0.size());
				
				if (CollectionUtils.isNotNull(arg0)) {
					mqQuickAdapter.clear();
					mqQuickAdapter.addAll(arg0);
					mListView.setPullLoadEnable(arg0.size() >=limit);
					currentPage+=1;
				}else {
					
					ShowToast("暂无数据!");
				}
				
				stopRefresh();
				stopLoadMore();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowLog("数据获取错误" +arg0+ arg1);
				mListView.setPullLoadEnable(false);
				stopLoadMore();
			}
		});
		
	}
	
	
	static int current=3;
	static String[] reversed=new String[]{"","",""}; 
	static String order="";
	
	@SuppressLint("ResourceAsColor")
	public void tabsClick(View view){
		
		for(TextView textView:tabs){
			textView.setTextColor(getResources().getColor(R.color.text_color_gray));
		}
		ShowLog("kkk");
		((TextView) view).setTextColor(getResources().getColor(R.color.text_color_green));
		
		if(current<3&&view.getId()==tabs[current].getId()){
			if(reversed[current].equals("")){
				tabs[current].setSelected(true);
				reversed[current]="-";
			}else {
				tabs[current].setSelected(false);
				reversed[current]="";
			}
		}
		
		
		switch (view.getId()) {
		
		case R.id.textView3:
			current=4;
			break;

		case R.id.textView0:
			current=0;
			order="";
			break;
			
		case R.id.textView1:
			current=1;
			order="";
			break;
		case R.id.textView2:
			current=2;
			order="price";
			break;
		
		default:
			break;
		}
		
	}
	
	private void stopLoadMore() {
		if (mListView.getPullLoading()) {
			mListView.stopLoadMore();
		}
	}
	private void stopRefresh() {
		if (mListView.getPullRefreshing()) {
			mListView.stopRefresh();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
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
