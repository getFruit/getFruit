package com.get.fruit.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.get.fruit.R;


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
	private LinearLayout mLayoutMiddleContainer;
	
	private TextView mHtvSubTitle;
	private TextView mLeftText;
	private Button mRightImageButton;
	private SearchView mEditText;
	
	private LinearLayout mLayoutMiddleLayout;
	private LinearLayout mLayoutLeftImageButtonLayout;
	private LinearLayout mLayoutRightImageButtonLayout;
	
	private onRightImageButtonClickListener mRightImageButtonClickListener;
	private onLeftImageButtonClickListener mLeftImageButtonClickListener;

	private int defaultTitle=R.string.app_name;
	private int defaultLeftImageButton=R.drawable.base_action_bar_back_bg_selector;
	
	public enum HeaderStyle {// 头部整体样式
		LEFT_MIDDLE_RIGHT,DEFAULT_TITLE, TITLE_LIFT_IMAGEBUTTON, TITLE_RIGHT_IMAGEBUTTON, TITLE_DOUBLE_IMAGEBUTTON;
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
		mLayoutMiddleContainer = (LinearLayout)findViewByHeaderId(R.id.header_layout_middleview_container);
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
			
		case LEFT_MIDDLE_RIGHT:
			middleView();
			titleLeftImageButton();
			titleRightImageButton();
			getmLayoutLeftImageButtonLayout().setHorizontalGravity(HORIZONTAL);
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
		mLeftText=(TextView) mleftImageButtonView
				.findViewById(R.id.lefttext);
		mLeftText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mLeftImageButtonClickListener != null) {
					mLeftImageButtonClickListener.onClick();
				}
			}
		});
	}
	//中间自定义布局
	private void middleView() {
		// TODO Auto-generated method stub
		View mMiddleView=mInflater.inflate(
				R.layout.common_header_middleview, null);
		mLayoutMiddleContainer.addView(mMiddleView);
		mLayoutMiddleLayout=(LinearLayout) mMiddleView
				.findViewById(R.id.header_layout_imagebuttonlayout);
		mEditText=(SearchView) mMiddleView.findViewById(R.id.middle_search);
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
	
	public void setTitleAndRightButton(CharSequence title, Integer backid,CharSequence charSequence,
			onRightImageButtonClickListener onRightImageButtonClickListener) {
		setDefaultTitle(title);
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		if (mRightImageButton != null&&backid!=null) {
			setRightButtonAndText(backid,charSequence);
			setOnRightImageButtonClickListener(onRightImageButtonClickListener);
		}else {
			mLayoutRightContainer.setVisibility(View.INVISIBLE);
		}
	}
	
	public void setTitleAndLeftImageButton(CharSequence title, int backid,CharSequence leftCharSequence,
			onLeftImageButtonClickListener listener, int direct) {
		setDefaultTitle(title);
		mLayoutLeftContainer.setVisibility(View.VISIBLE);
		setLeftButton(backid,direct);
		setLeftText(leftCharSequence);
		setOnLeftImageButtonClickListener(listener);
	}

	
	public Button getRightImageButton(){
		if(mRightImageButton!=null){
			return mRightImageButton;
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
	public void setLeftButton(int backid,int direct) {
		//1234->left top right,buttom
		if (backid > 0) {
			Drawable drawable= getResources().getDrawable(backid);  
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
			mLeftText.setCompoundDrawables(direct==1?drawable:null, direct==2?drawable:null, direct==3?drawable:null, direct==4?drawable:null);  
			//mLeftText.setCompoundDrawablesWithIntrinsicBounds(direct==1?backid:null, direct==2?backid:null, direct==3?backid:null, direct==4?backid:null);
			mLeftText.setVisibility(View.VISIBLE);
		}else {
			mLeftText.setVisibility(View.GONE);
		}
	}
	public void setLeftText(CharSequence charSequence) {
		if (charSequence==null) {
			mLeftText.setText("");
		}else {
			mLeftText.setText(charSequence);
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

	
	
	
	
	
	public SearchView getmEditText() {
		return mEditText;
	}

	public void setmEditText(SearchView mEditText) {
		this.mEditText = mEditText;
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

	public LinearLayout getmLayoutLeftImageButtonLayout() {
		return mLayoutLeftImageButtonLayout;
	}

}
