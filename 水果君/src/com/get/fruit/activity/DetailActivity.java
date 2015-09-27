package com.get.fruit.activity;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import cn.bmob.social.share.core.BMShareListener;
import cn.bmob.social.share.core.ErrorInfo;
import cn.bmob.social.share.core.data.BMPlatform;
import cn.bmob.social.share.core.data.ShareData;
import cn.bmob.social.share.view.BMShare;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindStatisticsListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.get.fruit.R;
import com.get.fruit.adapter.ZoomOutPageTransformer;
import com.get.fruit.bean.CartItem;
import com.get.fruit.bean.Fruit;
import com.get.fruit.bean.User;
import com.get.fruit.util.PixelUtil;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;
import com.get.fruit.view.MyViewPager;
import com.get.fruit.view.ZoomImageView;

public class DetailActivity extends BaseActivity {

	private Fruit fruit;
	private List<String> pics=new ArrayList<>();
	private PopupWindow popupwindow;
	private EditText counEditText;
	private TextView  count,origin,describe,price,total,name;
	private ImageButton dec,inc;
	private Button addtocart;
	
	private MyViewPager mViewPager;
	private int[] mImage = new int[]{R.drawable.aa,R.drawable.bb,R.drawable.cc};
	private ImageView[] mImageViews = new ImageView[mImage.length];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		fruit=(Fruit) getIntent().getSerializableExtra("fruit");
		initView();
		initEvent();
		query(fruit);
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
		initTopBarForBoth("详情", R.drawable.base_action_bar_back_login_selector, null, new OnLeftClickListenerFinishMe(), R.drawable.details_menu, null, new onRightImageButtonClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				if (popupwindow != null&&popupwindow.isShowing()) {
					popupwindow.dismiss();
					return;
				} else {
					if(popupwindow==null){
						initShareMenu();
					}
					openPopuWindow(mHeaderLayout.getRightImageButton());
				}
			}
		}, 1);
		count=(TextView) findViewById(R.id.detail_count);
		origin=(TextView) findViewById(R.id.detail_origin);
		describe=(TextView) findViewById(R.id.detail_describe);
		price=(TextView) findViewById(R.id.detail_price);
		total=(TextView) findViewById(R.id.detail_total);
		name=(TextView) findViewById(R.id.detail_name);
		counEditText=(EditText) findViewById(R.id.detail_editText_count);
		dec=(ImageButton) findViewById(R.id.detail_imageButton1);
		inc=(ImageButton) findViewById(R.id.detail_imageButton2);
		addtocart=(Button) findViewById(R.id.detail_addtocart);
		
		initPicViewer();
	}
	

	/** 
	* @Title: initPicViewer 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	*/
	private void initPicViewer() {
		mViewPager = (MyViewPager) findViewById(R.id.detail_imageSwitcher1);
        //mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //mViewPager.setPageTransformer(true, new RotatePageDownTransformer());
        mViewPager.setAdapter(new PagerAdapter() {
			
        	@Override
        	public Object instantiateItem(ViewGroup container, int position) {
        		ZoomImageView imageView = new ZoomImageView(DetailActivity.this);
        		position %= mImageViews.length;
        		imageView.setImageResource(mImage[position]);
        		imageView.setScaleType(ScaleType.FIT_XY);
        		container.addView(imageView);
        		mImageViews[position] = imageView;
        		mViewPager.setViewFromPosition(position, imageView);
        		mViewPager.getImageViewsLength(mImageViews);
        		return imageView;
        	}
        	
        	@Override
        	public void destroyItem(ViewGroup container, int position,
        			Object object) {
        		
        		//container.removeView(mImageViews[position]);
        		
        		mViewPager.removeViewFromPOsition(position);
        	}
        	
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				//return mImageViews.length;
				return Integer.MAX_VALUE;
			}
		});
        
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
		dec.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int c=Integer.valueOf(counEditText.getText().toString())-1;
				if (c==0) {
					return;
				}
				counEditText.setText(c+"");
				total.setText(c*fruit.getPrice()+"");
			}
		});
		inc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int c=Integer.valueOf(counEditText.getText().toString())+1;
				if (c>fruit.getCount()) {
					return;
				}
				counEditText.setText(c+"");
				total.setText(c*fruit.getPrice()+"");
			}
		});
		
		addtocart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (addtocart.getText().equals("查看购物车")) {
					startAnimActivityToFragment(MainActivity.class, 3);
					return;
				}
				int c=Integer.valueOf(counEditText.getText().toString());
				if (c==0) {
					ShowToast("请选择数量");
					return;
				}
				final CartItem cartItem=new CartItem();
				BmobQuery<CartItem> query=new BmobQuery<CartItem>();
				
				cartItem.setMine(me);
				cartItem.setFruit(fruit);
				cartItem.setCount(c);
				cartItem.save(DetailActivity.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						addtocart.setText("查看购物车");
					}
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						ShowToast("添加失败");
					}
				});
			}
		});
	}
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		fruit=(Fruit) intent.getSerializableExtra("fruit");
		if (fruit!=null) {
			setView();
			//query(fruit);
		}
		super.onResume();
	}
	
	
	/** 
	* @Title: query 
	* @Description: TODO
	* @param @param fruit2
	* @return void
	* @throws 
	*/
	private void query(Fruit fruit2) {
		// TODO Auto-generated method stub
		BmobQuery<Fruit> query=new BmobQuery<Fruit>();
		//query.include("Shop");
		query.getObject(this, fruit2.getObjectId(), new GetListener<Fruit>() {
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(Fruit arg0) {
				// TODO Auto-generated method stub
				fruit=arg0;
				downloadPics();
				setView();
			}
		});
		
		BmobQuery<CartItem> query2=new BmobQuery<CartItem>();
		query2.addWhereEqualTo("fruit", fruit.getObjectId());
		query2.addWhereEqualTo("mine", me.getObjectId());
		query2.findStatistics(this, CartItem.class, new FindStatisticsListener() {
			
			@Override
			public void onSuccess(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	//下载图片
	public void downloadPics(){
			String[] pcs=fruit.getPictures();
			pics.clear();
			if(pcs==null||pcs.length==0)return;
			for (int i = 0; i < pcs.length; i++) {
				BmobProFile.getInstance(this).download(pcs[i], new DownloadListener() {
		
			        @Override
			        public void onSuccess(String fullPath) {
			            // TODO Auto-generated method stub
			            ShowLog("下载成功："+fullPath);
			            pics.add(fullPath);
			        }
		
			        @Override
			        public void onProgress(String localPath, int percent) {
			            // TODO Auto-generated method stub
			        	ShowLog("download-->onProgress :"+percent);
			        }
		
			        @Override
			        public void onError(int statuscode, String errormsg) {
			            // TODO Auto-generated method stub
			        	ShowLog("下载出错："+statuscode +"--"+errormsg);
			        }
			    });
			}
		}
		
	public void setView(){
		count.setText(fruit.getCount()+"");
		origin.setText(fruit.getOrigin());
		describe.setText(fruit.getDescribe());
		price.setText(fruit.getPrice()+"");
		total.setText(fruit.getPrice()+"");
		name.setText(fruit.getName());
		counEditText.setText(1+"");
	}

	//分享菜单
	OnClickListener menuItemClick;
	public void initShareMenu(){

		menuItemClick=new OnClickListener() {
			
			boolean collected=false;
			@Override
			public void onClick(final View arg0) {
				// TODO Auto-generated method stub
				switch (arg0.getId()) {
				case R.id.comment:
					
					break;
				case R.id.collect:
					if (fruit==null) {
						return;
					}
					//here
					Fruit temp=new Fruit();
					temp.setObjectId(fruit.getObjectId());
					BmobRelation relation = new BmobRelation();
					User user=new User();
					user.setObjectId(me.getObjectId());
					relation.add(user);
					temp.setLikes(relation);
					temp.update(DetailActivity.this, new UpdateListener() {

					    @Override
					    public void onSuccess() {
					    	arg0.setSelected(!collected);
							collected=!collected;
					    }

					    @Override
					    public void onFailure(int arg0, String arg1) {
					        // TODO Auto-generated method stub
					        ShowToast("收藏失败");
					    }
					});
					break;
				case R.id.share:
					testShare();
					popupwindow.dismiss();
					break;

				default:
					break;
				}
			}
		};
		View menu=getLayoutInflater().inflate(R.layout.include_pop_sharemenu, null,false);
		
		popupwindow=new PopupWindow(menu,PixelUtil.dp2px(120),PixelUtil.dp2px(160));
		popupwindow.setAnimationStyle(R.style.AnimationFade);
		popupwindow.setFocusable(true);  
		popupwindow.setOutsideTouchable(true);  
		popupwindow.setBackgroundDrawable(new BitmapDrawable());  
		menu.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(popupwindow!=null && popupwindow.isShowing()){
					popupwindow.dismiss();
					popupwindow=null;
				}
				return false;
			}
		});
		
		popupwindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams params=DetailActivity.this.getWindow().getAttributes();  
			    params.alpha=1f;  
			    DetailActivity.this.getWindow().setAttributes(params); 
			}
		});
		
		
		TextView comment=(TextView) menu.findViewById(R.id.comment);
		TextView collect=(TextView) menu.findViewById(R.id.collect);
		TextView share=(TextView) menu.findViewById(R.id.share);
		ShowLog(String.valueOf(menuItemClick));
		ShowLog(String.valueOf(comment==null));
		comment.setOnClickListener(menuItemClick);
		collect.setOnClickListener(menuItemClick);
		share.setOnClickListener(menuItemClick);
		
	}
	
	private void testShare() {
		// ShareDataÊ¹ÓÃÄÚÈÝ·ÖÏíÀàÐÍ·ÖÏíÀàÐÍ
		ShareData shareData = new ShareData();
		shareData.setTitle(fruit.getName());
		shareData.setDescription("BmobÉç»á»¯·ÖÏí¹¦ÄÜ");
		shareData.setText("BmobÌá¹©µÄ¶àÆ½Ì¨Éç»á»¯·ÖÏí¹¦ÄÜ£¬Ä¿Ç°Ö§³ÖQQ¡¢QQ¿Õ¼ä¡¢Î¢ÐÅ¡¢Î¢ÐÅÅóÓÑÈ¦¡¢ÌÚÑ¶Î¢²©¡¢ÐÂÀËÎ¢²©¡¢ÈËÈËÍøÆ½Ì¨µÄ·ÖÏí¹¦ÄÜ¡£ ");
		shareData.setTarget_url("http://www.codenow.cn/");
		shareData.setImageUrl("http://assets3.chuangyepu.com/system/startup_contents/logos/000/003/395/medium/data.jpeg");
		
		BMShareListener whiteViewListener = new BMShareListener() {

			@Override
			public void onSuccess() {
					ShowLog("onSuccess");
			}

			@Override
			public void onPreShare() {
					ShowLog("onPreShare");
			}

			@Override
			public void onError(ErrorInfo error) {
					ShowLog("onError"+error.getErrorMessage());
			}

			@Override
			public void onCancel() {
					ShowLog("onCancel");
			}

		};
		
		BMShare share = new BMShare(this);
		share.setShareData(shareData);
		share.addListener(BMPlatform.PLATFORM_WECHAT, whiteViewListener);
		share.addListener(BMPlatform.PLATFORM_WECHATMOMENTS, whiteViewListener);
		share.addListener(BMPlatform.PLATFORM_SINAWEIBO, whiteViewListener);
		share.addListener(BMPlatform.PLATFORM_RENN, whiteViewListener);
		share.addListener(BMPlatform.PLATFORM_TENCENTWEIBO, whiteViewListener);
		share.addListener(BMPlatform.PLATFORM_QQ, whiteViewListener);
		share.addListener(BMPlatform.PLATFORM_QZONE, whiteViewListener);
		share.show();
		ShowLog("share");
	}
	
	/** 
	* @Title: openPopuWindow 
	* @Description: TODO
	* @param View 
	* @return void
	* @throws 
	*/
	@SuppressLint("NewApi")
	private void openPopuWindow(View view) {
		// TODO Auto-generated method stub
		popupwindow.showAsDropDown(view);
		WindowManager.LayoutParams params=DetailActivity.this.getWindow().getAttributes();  
	    params.alpha=0.5f;  
	    DetailActivity.this.getWindow().setAttributes(params); 
	}
}
