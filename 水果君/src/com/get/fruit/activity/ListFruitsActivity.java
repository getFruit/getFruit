package com.get.fruit.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.get.fruit.R;
import com.get.fruit.adapter.util.BaseAdapterHelper;
import com.get.fruit.adapter.util.QuickAdapter;
import com.get.fruit.bean.Cityinfo;
import com.get.fruit.bean.Fruit;
import com.get.fruit.util.CitycodeUtil;
import com.get.fruit.util.CollectionUtils;
import com.get.fruit.util.FileUtil;
import com.get.fruit.util.PixelUtil;
import com.get.fruit.util.StringUtils;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;
import com.get.fruit.view.citypicker.JSONParser;
import com.get.fruit.view.listview.XListView;
import com.get.fruit.view.listview.XListView.IXListViewListener;

public class ListFruitsActivity extends BaseActivity {
	
	static CachePolicy policy=CachePolicy.CACHE_ELSE_NETWORK;//缓存策略
	static int limit=15;//每页条数
	static int currentPage=0;//当前页
	static String provinceString="";//产地省份
	static String cityString="";//产地城市
	static String searchBy;//搜索方式
	static String searchValue;//搜索条件
	static String keyWord;//搜索关键字
	
	private SearchView keyWordSearch;
	private XListView mListView;
	private QuickAdapter<Fruit> mqQuickAdapter;
	private TextView[] tabs;
	private Intent intent;
	private PopupWindow popupwindow;
	
	
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
		initTopBarForMiddleView(null, R.drawable.base_action_bar_back_bg_selector, searchBy, new OnLeftClickListenerFinishMe(), R.drawable.base_action_bar_person_selector, null, new onRightImageButtonClickListener() {
		
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				Intent intent =new Intent(ListFruitsActivity.this, MainActivity.class);
				intent.putExtra("to", 2);
				startAnimActivity(intent);
			}
		},1);
	
		keyWordSearch=mHeaderLayout.getmEditText();
		keyWordSearch.setSubmitButtonEnabled(true);
		keyWordSearch.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// TODO Auto-generated method stub
				keyWord=arg0;
				query();
				//隐藏输入法
				if(keyWordSearch!=null){
					InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					if (inputMethodManager!=null) {
						inputMethodManager.hideSoftInputFromWindow(keyWordSearch.getWindowToken(), 0);
					}
					keyWordSearch.clearFocus();
				}
				
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				keyWord=arg0;
				return false;
			}
		});
		/*keyWordSearch.setSuggestionsAdapter(new CursorAdapter(ListFruitsActivity.this, new Cursor, true) {
			
			@Override
			public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void bindView(View arg0, Context arg1, Cursor arg2) {
				// TODO Auto-generated method stub
				
			}
		});*/
		
		tabs=new TextView[]{
			(TextView) findViewById(R.id.textView0),(TextView) findViewById(R.id.textView1),(TextView) findViewById(R.id.textView2),(TextView) findViewById(R.id.textView3)	};
		
		initListView();
		
		tabsClick(tabs[0]);
	}

	
	public void initListView() {
		mListView=(XListView) findViewById(R.id.xListView1);
		
		mqQuickAdapter=new QuickAdapter<Fruit>(ListFruitsActivity.this,R.layout.item_listfruit){
			
			@Override
			protected void convert(final BaseAdapterHelper helper, final Fruit item) {
				// TODO Auto-generated method stub
				ShowLog(item.getName());
				
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
		mListView.setAdapter(mqQuickAdapter);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				ShowLog("onRefresh...");
				currentPage=0;
				query();
			}
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				ShowLog("onLoadMore...");
				query();
			}
		});

		mListView.pullRefreshing();
		if (!isNetConnected()) {
			ShowToast(R.string.network_tips);
			stopRefresh();
			return;
		}
		query();

	}
	
	//列表查询
	public void query(){
		ShowLog("query");
		
		BmobQuery<Fruit> query = new BmobQuery<Fruit>();
		query.setCachePolicy(policy); 
		query.include("category");
		query.setLimit(limit);
		query.setSkip(limit*currentPage);
		query.order(order);
		
		if(!(StringUtils.isEmpty(searchBy)&&StringUtils.isEmpty(searchValue)))
			query.addWhereEqualTo(searchBy, searchValue);
		/*if(!StringUtils.isEmpty(keyWordSearch.getQuery().toString()))
			query.addWhereContains("name",keyWordSearch.getQuery().toString() );
		*/
		if(!StringUtils.isEmpty(keyWord))
			query.addWhereContains("name", keyWord);
		if(lower!=null&&!StringUtils.isEmpty(lower.getText().toString()))
			query.addWhereGreaterThanOrEqualTo("price", Float.valueOf(lower.getText().toString()));
		if(higher!=null&&!StringUtils.isEmpty(higher.getText().toString()))
			query.addWhereLessThanOrEqualTo("price", Float.valueOf(higher.getText().toString()));
		if (!StringUtils.isEmpty(provinceString)) 
			query.addWhereStartsWith("origin", provinceString+"-"+cityString);
		
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
	@SuppressLint({ "ResourceAsColor", "NewApi" })
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
			current=3;
			if (popupwindow != null&&popupwindow.isShowing()) {
				popupwindow.dismiss();
				return;
			} else {
				if(popupwindow==null){
					initShareMenu();
				}
				popupwindow.showAsDropDown(view, 25, 1);
			}
			break;

		case R.id.textView0:
			current=0;
			order=reversed+"paynum,createdAt";
			break;
			
		case R.id.textView1:
			current=1;
			order=reversed+"paynum";
			break;
		case R.id.textView2:
			current=2;
			order=reversed+"price";
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
	
	//initMenu
	private Spinner province,city;
	private EditText lower;
	private EditText higher;
	private ArrayList<String> list=new ArrayList<String>();//城市
	private ArrayList<String> clist=new ArrayList<String>();//子城市
	private List<Cityinfo> province_list;
	private HashMap<String, List<Cityinfo>> city_map;
	public void initShareMenu(){

		//menu
		View menu=getLayoutInflater().inflate(R.layout.include_pop_conditionmenu, null,false);
		popupwindow=new PopupWindow(menu,PixelUtil.dp2px(180),PixelUtil.dp2px(200));
		popupwindow.setAnimationStyle(R.style.AnimationFade);
		popupwindow.setFocusable(true);  
		popupwindow.setOutsideTouchable(true);  
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响背景  
		popupwindow.setBackgroundDrawable(new BitmapDrawable());  
		menu.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(popupwindow!=null && popupwindow.isShowing()){
					popupwindow.dismiss();
				}
				return false;
			}
		});
		
		//产地
		//menu.findViewById()....
		province=(Spinner) menu.findViewById(R.id.spinner1);
		city=(Spinner) menu.findViewById(R.id.spinner2);
		initSpinnerData();
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(ListFruitsActivity.this, android.R.layout.simple_spinner_item,list);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		province.setAdapter(adapter2);
		province.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				provinceString=list.get(arg2);
				clist.clear();
				clist=CitycodeUtil.getSingleton().getCity(city_map, province_list.get(arg2).getId());
				clist.add("");
				ArrayAdapter<String> adapter=new ArrayAdapter<String>
				(ListFruitsActivity.this, android.R.layout.simple_spinner_item, clist);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    city.setAdapter(adapter);
			    city.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						cityString=clist.get(arg2);					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
				});
		}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//价格区间
		lower=(EditText) menu.findViewById(R.id.low);
		higher=(EditText) menu.findViewById(R.id.high);
		
		TextView textView=(TextView) menu.findViewById(R.id.clear);
		textView.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				provinceString="";
				cityString="";
				lower.setText("");
				higher.setText("");
			}
		});
		
	}

	/** 
	* @Title: initSpinnerData 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	*/
	private void initSpinnerData() {
		// TODO Auto-generated method stub
		// 获取城市信息
		JSONParser parser = new JSONParser();
		String area_str = FileUtil.readAssets(ListFruitsActivity.this, "area.json");
		province_list = parser.getJSONParserResult(area_str, "area0");
		city_map = parser.getJSONParserResultArray(area_str, "area1");
		list=CitycodeUtil.getSingleton().getProvince(province_list);
	}
}
