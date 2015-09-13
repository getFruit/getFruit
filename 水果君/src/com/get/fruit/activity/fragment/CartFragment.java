package com.get.fruit.activity.fragment;

import java.util.LinkedList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.baidu.platform.comapi.map.u;
import com.get.fruit.R;
import com.get.fruit.activity.BaseFragment;
import com.get.fruit.activity.DetailActivity;
import com.get.fruit.adapter.util.BaseAdapterHelper;
import com.get.fruit.adapter.util.QuickAdapter;
import com.get.fruit.bean.CartItem;
import com.get.fruit.util.CollectionUtils;
import com.get.fruit.view.HeaderLayout;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;
import com.get.fruit.view.listview.XListView;
import com.get.fruit.view.listview.XListView.IXListViewListener;

public class CartFragment extends BaseFragment {
	static boolean inited=false;
	static boolean loaded=false;
	private CartCallBack callBack;
	private XListView mListView;
	private QuickAdapter<CartItem> mQuickAdapter;
	private Button gopay;
	private TextView totalPrice;
	private TextView emptyView;
	private CheckBox checkAll;
	List<Integer> checkedItems=new LinkedList<Integer>();
	List<CartItem> allItems=new LinkedList<CartItem>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ShowLog("onCreate.....cart");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ShowLog("onCreateView....inflater>.cart");
		return inflater.inflate(R.layout.fragment_cart, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ShowLog("onActivityCreated.....cart  to>initview");
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
		ShowLog("initView.....cart");
		// TODO Auto-generated method stub
		initListView();
		initButtomView();
		inited=true;
		/*
		if (!loaded) {
			loadData();
		}
		*/
	}

	/** 
	* @Title: loadData 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	*/
	private void loadData() {
		// TODO Auto-generated method stub
		ShowLog("loadData.....cart");
		BmobQuery<CartItem> query=new BmobQuery<CartItem>();
		query.include("fruit");
		query.addWhereEqualTo("mine", BmobChatUser.getCurrentUser(getActivity()).getObjectId());
		query.findObjects(getActivity(), new FindListener<CartItem>() {
			
			@Override
			public void onSuccess(List<CartItem> arg0) {
				// TODO Auto-generated method stub
				if(CollectionUtils.isNotNull(arg0)){
					emptyView.setVisibility(View.GONE);
					allItems.clear();
					allItems.addAll(arg0);
					
					mQuickAdapter.clear();
					mQuickAdapter.addAll(allItems);
					loaded=true;
					
				}else {
					
				}
				arg0.clear();
				stopRefresh();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				emptyView.setVisibility(View.VISIBLE);
				emptyView.setText("数据获取错误" +arg0+ arg1);
				ShowLog("数据获取错误" +arg0+ arg1);
			}
		});
	}

	/** 
	* @Title: initButtomView 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	*/
	public void initButtomView() {
		// TODO Auto-generated method stub
		checkAll=(CheckBox) findViewById(R.id.cart_all_check);
		gopay=(Button) findViewById(R.id.cart_gopay);
		totalPrice=(TextView) findViewById(R.id.cart_totalprice);
		
		checkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				checkedItems.clear();
				if (isChecked) {
					for (int i = 0; i < allItems.size(); i++) {
						checkedItems.add(i);
					}
				}
				mQuickAdapter.notifyDataSetChanged();
				setTotal();
			}

			
		});
		
		
		gopay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowLog("pay");
			}
		});
		
		
		
	}

	public void setTotal() {
		ShowLog("setTotal");
		float total = 0;
		if (checkedItems.size()!=0) {
			CartItem item;
			for(int i=0; i<checkedItems.size();i++){
				item=allItems.get(checkedItems.get(i));
				total+=(item.getFruit().getPrice()*item.getCount());
			}
		}
		totalPrice.setText(total+"");
	}
	
	public void initListView() {
		mListView=(XListView) findViewById(R.id.cart_listview);
		emptyView=(TextView) findViewById(R.id.listview_empty);
		mQuickAdapter=new QuickAdapter<CartItem>(getActivity(),R.layout.item_cart_list) {
			
			@Override
			protected void convert(final BaseAdapterHelper helper, final CartItem item) {
				// TODO Auto-generated method stub
				helper.setText(R.id.cart_item_price, ""+(item.getFruit().getPrice()*item.getCount()));
				helper.setText(R.id.cart_item_name, item.getFruit().getName());
				helper.setText(R.id.cart_item_describe, item.getFruit().getDescribe());
				helper.setText(R.id.cart_item_count, item.getCount()+"");
				helper.setChecked(R.id.cart_item_check,checkedItems.contains((Integer)helper.getPosition()));
				final ImageView imageView=helper.getView(R.id.cart_item_image);
				helper.setImageBitmapFromBmobFile(R.id.cart_item_image, item.getFruit().getPicture());
				helper.setOnClickListener(R.id.cart_item_image, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startAnimActivityWithData(DetailActivity.class, "fruit", item.getFruit());
					}
				});
				helper.setOnClickListener(R.id.cart_item_add, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						final int count=Integer.valueOf(((EditText)helper.getView(R.id.cart_item_count)).getText().toString())+1;
						if (count>=item.getFruit().getCount()) {
							ShowToast("库存不够！ "+item.getFruit().getCount());
							return;
						}
						item.increment("count");
						item.update(getActivity(), new UpdateListener() {
							
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								helper.setText(R.id.cart_item_count,count+"");
								item.setCount(count);
								helper.setText(R.id.cart_item_price, ""+(item.getFruit().getPrice()*item.getCount()));
								setTotal();
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub
								ShowToast("添加失败");
							}
						});
					}
				});
				helper.setOnClickListener(R.id.cart_item_dec, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						final int count=Integer.valueOf(((EditText)helper.getView(R.id.cart_item_count)).getText().toString())-1;
						if (count<=0) {
							return;
						}
						item.increment("count", -1);
						item.update(getActivity(), new UpdateListener() {
							
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								helper.setText(R.id.cart_item_count,count+"");
								item.setCount(count);
								helper.setText(R.id.cart_item_price, ""+(item.getFruit().getPrice()*item.getCount()));
								setTotal();
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub
								ShowToast("删除失败，稍后再试");
							}
						});
					}
				});
				
				final CheckBox checkBox=helper.getView(R.id.cart_item_check);
				helper.setOnClickListener(R.id.cart_item_check, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (checkBox.isChecked()) {
							checkedItems.add((Integer)helper.getPosition());
						}else {
							checkedItems.remove((Integer)helper.getPosition());
						}
						setTotal();
						mQuickAdapter.notifyDataSetChanged();
					}
				});
			}
		};
		
		mListView.setAdapter(mQuickAdapter);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				ShowLog("onRefresh...");
				loadData();
			}
			
			@Override
			public void onLoadMore() {
				return;
			}
		});

	}
	//停止刷新
	private void stopRefresh() {
		if (mListView.getPullRefreshing()) {
			mListView.stopRefresh();
		}
	}
	//设置删除按钮
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		ShowLog("attach.....cart");
		callBack=(CartCallBack) activity;
		super.onAttach(activity);
	}
	
	
	public interface CartCallBack{
		public HeaderLayout getHeaderLayout();
	}


	
	
	static boolean isVisible=false;
	@Override
	protected void onVisible() {
		isVisible=true;
		ShowLog("onVisible....cart");

		if (inited && !loaded) {
			mListView.pullRefreshing();
			if (!isNetConnected()) {
				emptyView.setText(R.string.network_tips);
				stopRefresh();
			}else {
				loadData();
			}
		}
	}
	
	@Override
	protected void onInvisible() {
		isVisible=false;
	}
	

}
