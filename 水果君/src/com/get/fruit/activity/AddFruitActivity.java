package com.get.fruit.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

import com.get.fruit.BmobConstants;
import com.get.fruit.R;
import com.get.fruit.adapter.util.BaseAdapterHelper;
import com.get.fruit.adapter.util.QuickAdapter;
import com.get.fruit.util.PhotoUtil;

public class AddFruitActivity extends BaseActivity implements OnClickListener {

	//private TextView addCategoryTextView,addSeasonTextView,addOriginTextView,addColorTextView;
	private TextView addTextViews[];
	private EditText addPriceEditText,addCounEditText,addDescribeEditText;
	//private ImageButton addPicChoose,addCategoryChoose,addSeasonChoose,addOriginCHoose,addColorChoose;
	private ImageButton chooseButtons[];
	String from = "";
	private GridView mGridView;
	private QuickAdapter<Bitmap> mQuickAdapter;
	private List<String> pics=new LinkedList<String>();
	private ScrollView parentScroll;
	private ScrollView childScroll;
	public static int currentClickedItem=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfruit);
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
		initTopBarForLeft("添加");
		layout_all = (LinearLayout) findViewById(R.id.layout_all);
		
		chooseButtons[0]=(ImageButton) findViewById(R.id.add_category_choose);
		chooseButtons[1]=(ImageButton) findViewById(R.id.add_season_choose);
		chooseButtons[2]=(ImageButton) findViewById(R.id.add_origin_choose);
		chooseButtons[3]=(ImageButton) findViewById(R.id.add_color_choose);
		chooseButtons[4]=(ImageButton) findViewById(R.id.add_taste_choose);
		
		addTextViews[0]=(EditText) findViewById(R.id.add_category);
		addTextViews[1]=(EditText) findViewById(R.id.add_season);
		addTextViews[2]=(EditText) findViewById(R.id.add_origin);
		addTextViews[3]=(EditText) findViewById(R.id.add_color);
		addTextViews[4]=(EditText) findViewById(R.id.add_taste);
		for(View v:chooseButtons){
			v.setOnClickListener(this);
		}
		
		mGridView =(GridView) findViewById(R.id.add_gridView);
		mGridView.setAdapter(mQuickAdapter=new QuickAdapter<Bitmap>(AddFruitActivity.this,R.layout.add_gridview_item ) {

			@Override
			protected void convert(final BaseAdapterHelper helper, final Bitmap item) {
				helper.setImageBitmap(R.id.item_image, item);

				/*//删除按钮
				helper.setOnClickListener(R.id.item_CH, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
					}
				});
				*/
				//添加按钮
				helper.setOnClickListener(R.id.item_image, new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						currentClickedItem=helper.getPosition();
						showAvatarPop();
					}
				});
				
				//长按事件
				helper.setOnLongClickListener(R.id.item_image, new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View arg0) {
						ShowLog(helper.getPosition()+"p");
						ShowLog(mQuickAdapter.getCount()+"c");
						if (helper.getPosition()==mQuickAdapter.getCount()-1) {
							return false;
						}
						mQuickAdapter.remove(item);
						pics.remove(helper.getPosition());
						return false;
					}
					
					
					
					
				});
				
			
			}
		});
		mQuickAdapter.add(BitmapFactory.decodeResource(getResources(), R.id.item_image));
	}
	
	
	
	RelativeLayout layout_choose;
	RelativeLayout layout_photo;
	PopupWindow avatorPop;

	public String filePath = "";
	private View layout_all;

	private void showAvatarPop() {
		View view = LayoutInflater.from(this).inflate(R.layout.pop_showavator,
				null);
		layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
		layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);
		layout_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ShowLog("点击拍照");
				// TODO Auto-generated method stub
				layout_choose.setBackgroundColor(getResources().getColor(
						R.color.base_color_white));
				layout_photo.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.pop_bg_press));
				File dir = new File(BmobConstants.MyFruitDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				// 原图
				File file = new File(dir, new SimpleDateFormat("yyMMddHHmmss")
						.format(new Date()));
				filePath = file.getAbsolutePath();// 获取相片的保存路径
				Uri imageUri = Uri.fromFile(file);

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent,
						BmobConstants.REQUESTCODE_UPLOADAVATAR_CAMERA);
			}
		});
		layout_choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ShowLog("点击相册");
				layout_photo.setBackgroundColor(getResources().getColor(
						R.color.base_color_white));
				layout_choose.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.pop_bg_press));
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent,
						BmobConstants.REQUESTCODE_UPLOADAVATAR_LOCATION);
			}
		});

		avatorPop = new PopupWindow(view, mScreenWidth, 600);
		avatorPop.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					avatorPop.dismiss();
					return true;
				}
				return false;
			}
		});

		avatorPop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		avatorPop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		avatorPop.setTouchable(true);
		avatorPop.setFocusable(true);
		avatorPop.setOutsideTouchable(true);
		avatorPop.setBackgroundDrawable(new BitmapDrawable());
		// 动画效果 从底部弹起
		avatorPop.setAnimationStyle(R.style.Animations_GrowFromBottom);
		avatorPop.showAtLocation(layout_all, Gravity.BOTTOM, 0, 0);
	}

	
	/**
	 * @Title: startImageAction
	 * @return void
	 * @throws
	 */
	private void startImageAction(Uri uri, int outputX, int outputY,
			int requestCode, boolean isCrop) {
		Intent intent = null;
		if (isCrop) {
			intent = new Intent("com.android.camera.action.CROP");
		} else {
			intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		}
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}

	Bitmap newBitmap;
	boolean isFromCamera = false;// 区分拍照
	int degree = 0;//旋转

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case BmobConstants.REQUESTCODE_UPLOADAVATAR_CAMERA:// 拍照修改头像
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					ShowToast("SD不可用");
					return;
				}
				isFromCamera = true;
				File file = new File(filePath);
				degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());
				Log.i("life", "拍照后的角度：" + degree);
				startImageAction(Uri.fromFile(file), 200, 200,
						BmobConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
			}
			break;
		case BmobConstants.REQUESTCODE_UPLOADAVATAR_LOCATION:// 本地修改头像
			if (avatorPop != null) {
				avatorPop.dismiss();
			}
			Uri uri = null;
			if (data == null) {
				return;
			}
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					ShowToast("SD不可用");
					return;
				}
				isFromCamera = false;
				uri = data.getData();
				startImageAction(uri, 200, 200,
						BmobConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
			} else {
				ShowToast("照片获取失败");
			}

			break;
		case BmobConstants.REQUESTCODE_UPLOADAVATAR_CROP:// 裁剪头像返回
			// TODO sent to crop
			if (avatorPop != null) {
				avatorPop.dismiss();
			}
			if (data == null) {
				//取消选择
				return;
			} else {
				saveCropAvator(data);
			}
			// 初始化文件路径
			filePath = "";
			// 上传头像
			//uploadAvatar();
			break;
			
		case BmobConstants.REQUESTCODE_FROM_ADDFRUIT:
			if (resultCode == RESULT_OK) {
				if (data==null) {
					break;
				}
				data.getIntExtra("witch",-1);
				//hear
			}
			
			break;
		default:
			break;

		}
	}

	private void uploadAvatar() {
		BmobLog.i("头像地址：" + path);
		final BmobFile bmobFile = new BmobFile(new File(path));
		bmobFile.upload(this, new UploadFileListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				String url = bmobFile.getFileUrl();
				// 更新BmobUser对象
			}

			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(int arg0, String msg) {
				// TODO Auto-generated method stub
				ShowToast("头像上传失败：" + msg);
			}
		});
	}


	
	String path;

	/**
	 * 保存裁剪的头像
	 * 
	 * @param data
	 */
	private void saveCropAvator(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap bitmap = extras.getParcelable("data");
			Log.i("life", "avatar - bitmap = " + bitmap);
			if (bitmap != null) {
				String filename = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
				path = BmobConstants.MyAvatarDir + filename;
				PhotoUtil.saveBitmap(BmobConstants.MyTempDir, filename,bitmap, true);
				pics.add(currentClickedItem, path);
				bitmap = PhotoUtil.toRoundCorner(bitmap, 40);
				if (currentClickedItem!=mQuickAdapter.getCount()-1) {
					
					mQuickAdapter.addTo(currentClickedItem, bitmap);
					mQuickAdapter.remove(currentClickedItem+1);
				}else {
					mQuickAdapter.addTo(mQuickAdapter.getCount()-1, bitmap);
				}
				if (isFromCamera && degree != 0) {
					bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
				}
				ShowLog((String.valueOf(bitmap==null)));
				if (bitmap != null && bitmap.isRecycled()) {
					bitmap.recycle();
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.add_category_choose:
			//startActivityForResult(intent, requestCode, options)
			break;

		default:
			break;
		}
	}

}
