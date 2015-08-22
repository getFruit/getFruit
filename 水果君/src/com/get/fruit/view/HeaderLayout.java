package com.get.fruit.view;

import org.apache.http.auth.NTCredentials;

import android.R.integer;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.get.fruit.App;
import com.get.fruit.R;
import com.get.fruit.util.PixelUtil;


/** 自定义头部布局
  * @ClassName: HeaderLayout
  * @Description: TODO
  * @author smile
  * @date 2014-5-19 下午2:30:30
  */
public class HeaderLayout extends LinearLayout {
	private LayoutInflater mInflater;
	private View mHeader;
	private LinearLayout mLayoutLeftContainer;
	private LinearLayout mLayoutRightContainer;
	private TextView mHtvSubTitle;
	private TextView mLeftText;
	private LinearLayout mLayoutRightImageButtonLayout;
	private Button mRightImageButton;
	private onRightImageButtonClickListener mRightImageButtonClickListener;
	
	private LinearLayout mLayoutLeftImageButtonLayout;
	private ImageButton mLeftImageButton;
	private onLeftImageButtonClickListener mLeftImageButtonClickListener;

	private int defaultTitle=R.string.app_name;
	private int defaultLeftImageButton=R.drawable.base_action_bar_back_bg_selector;
	public enum HeaderStyle {// 头部整体样式
		DEFAULT_TITLE, TITLE_LIFT_IMAGEBUTTON, TITLE_RIGHT_IMAGEBUTTON, TITLE_DOUBLE_IMAGEBUTTON;
	}

	public HeaderLayout(Context context) {
		super(context);
		init(context);
	}

	public HeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		mInflater = LayoutInflater.from(context);
		mHeader = mInflater.inflate(R.layout.common_header, null);
		addView(mHeader);
		initViews();
	}

	public void initViews() {
		mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
		// mLayoutMiddleContainer = (LinearLayout)
		// findViewByHeaderId(R.id.header_layout_middleview_container);中间部分添加搜索或者其他按钮时可打开
		mLayoutRightContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_container);
		mHtvSubTitle = (TextView) findViewByHeaderId(R.id.header_htv_subtitle);
		
		

	}

	public View findViewByHeaderId(int id) {
		return mHeader.findViewById(id);
	}

	public void init(HeaderStyle hStyle) {
		switch (hStyle) {
		case DEFAULT_TITLE:
			defaultTitle();
			break;

		case TITLE_LIFT_IMAGEBUTTON:
			defaultTitle();
			titleLeftImageButton();
			break;

		case TITLE_RIGHT_IMAGEBUTTON:
			defaultTitle();
			titleRightImageButton();
			break;

		case TITLE_DOUBLE_IMAGEBUTTON:
			defaultTitle();
			titleLeftImageButton();
			titleRightImageButton();
			break;
		}
	}

	// 默认文字标题
	private void defaultTitle() {
		mLayoutLeftContainer.removeAllViews();
		mLayoutRightContainer.removeAllViews();
	}

	// 左侧自定义按钮
	private void titleLeftImageButton() {
		View mleftImageButtonView = mInflater.inflate(
				R.layout.common_header_leftbuttonwithtext, null);
		mLayoutLeftContainer.addView(mleftImageButtonView);
		mLayoutLeftImageButtonLayout = (LinearLayout) mleftImageButtonView
				.findViewById(R.id.header_layout_imagebuttonlayout);
		mLeftImageButton = (ImageButton) mleftImageButtonView
				.findViewById(R.id.headerimagebutton);
		mLeftText=(TextView) mleftImageButtonView
				.findViewById(R.id.lefttext);
		mLeftImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if (mLeftImageButtonClickListener != null) {
					Log.d("deeee", "leftclick");
					mLeftImageButtonClickListener.onClick();
				}
			}
		});
	}

	// 右侧自定义按钮
	private void titleRightImageButton() {
		View mRightImageButtonView = mInflater.inflate(
				R.layout.common_header_rightbutton, null);
		mLayoutRightContainer.addView(mRightImageButtonView);
		mLayoutRightImageButtonLayout = (LinearLayout) mRightImageButtonView
				.findViewById(R.id.header_layout_imagebuttonlayout);
		mRightImageButton = (Button) mRightImageButtonView
				.findViewById(R.id.headerimagebutton);
		mRightImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
	}
	
	
	
	
	
	public void setTitleAndRightButton(CharSequence title, int backid,CharSequence charSequence,
			onRightImageButtonClickListener onRightImageButtonClickListener) {
		setDefaultTitle(title);
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		if (mRightImageButton != null) {
			setRightButtonAndText(backid,charSequence);
			setOnRightImageButtonClickListener(onRightImageButtonClickListener);
		}else {
			mLayoutRightContainer.setVisibility(View.GONE);
		}
	}
	
	public void setTitleAndLeftImageButton(CharSequence title, int backid,CharSequence leftCharSequence,
			onLeftImageButtonClickListener listener) {
		setDefaultTitle(title);
		mLayoutLeftContainer.setVisibility(View.VISIBLE);
		if (mLeftImageButton != null) {
			Log.d("deeee", "slis");
			setOnLeftImageButtonClickListener(listener);
			setLeftButton(backid);
			setLeftText(leftCharSequence);
		}else {
			mLayoutLeftContainer.setVisibility(View.GONE);
		}
	}

	
	
	
	
	
	
	
	
	
	
	public Button getRightImageButton(){
		if(mRightImageButton!=null){
			return mRightImageButton;
		}
		return null;
	}
	public ImageButton getLefttImageButton(){
		if(mLeftImageButton!=null){
			return mLeftImageButton;
		}
		return null;
	}
	public void setDefaultTitle(CharSequence title) {
		if (title != null) {
			mHtvSubTitle.setText(title);
		} else {
			mHtvSubTitle.setVisibility(View.GONE);
		}
	}
	public void setRightButtonAndText(int backid,CharSequence charSequence) {
		if(backid < 0){
			mRightImageButton.setBackgroundColor(getResources().getColor(R.color.base_color_all_transparent));
		}else{
			mRightImageButton.setBackgroundResource(backid);
		}
		if(charSequence!=null){
			mRightImageButton.setText(charSequence);
		}
	}
	public void setLeftButton(int backid) {
		if (backid > 0) {
		Log.d("left", "le");
			mLeftImageButton.setImageResource(backid);
		}else {
			mLeftImageButton.setVisibility(View.GONE);
		}
	}
	public void setLeftText(CharSequence charSequence) {
		if (charSequence!=null) {
			mLeftText.setVisibility(View.VISIBLE);
			mLeftText.setText(charSequence);
		}else {
			mLeftText.setVisibility(View.GONE);
		}
	}

	
	public void setOnRightImageButtonClickListener(
			onRightImageButtonClickListener listener) {
		mRightImageButtonClickListener = listener;
	}

	public interface onRightImageButtonClickListener {
		void onClick();
	}

	public void setOnLeftImageButtonClickListener(
			onLeftImageButtonClickListener listener) {
		mLeftImageButtonClickListener = listener;
	}

	public interface onLeftImageButtonClickListener {
		void onClick();
	}

	
	
	
	
	
	public TextView getmHtvSubTitle() {
		return mHtvSubTitle;
	}

	public void setmHtvSubTitle(TextView mHtvSubTitle) {
		this.mHtvSubTitle = mHtvSubTitle;
	}

	public TextView getmLeftText() {
		return mLeftText;
	}

	public void setmLeftText(TextView mLeftText) {
		this.mLeftText = mLeftText;
	}

	public LinearLayout getmLayoutLeftContainer() {
		return mLayoutLeftContainer;
	}

}
