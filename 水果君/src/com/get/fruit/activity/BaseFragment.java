package com.get.fruit.activity;


import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;
import cn.bmob.im.BmobUserManager;
import cn.bmob.push.a.is;

import com.get.fruit.App;
import com.get.fruit.bean.User;
import com.get.fruit.util.CommonUtils;

/**
 * Fragmenet 基类
 */
public abstract class BaseFragment extends Fragment {

	protected View contentView;

	public LayoutInflater mInflater;

	private Handler handler = new Handler();

	protected BmobUserManager userManager;
	protected User me;
	public void runOnWorkThread(Runnable action) {
		new Thread(action).start();
	}

	public void runOnUiThread(Runnable action) {
		handler.post(action);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		userManager = BmobUserManager.getInstance(getActivity());
		me=App.mInstance.getCurrentUser();
		mInflater = LayoutInflater.from(getActivity());
	}

	public BaseFragment() {

	}

	
	
	public View findViewById(int paramInt) {
		return getView().findViewById(paramInt);
	}
	
	Toast mToast;

	public void ShowToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public void ShowToast(int text) {
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public boolean isNetConnected() {
		boolean isNetConnected = CommonUtils.isNetworkAvailable(getActivity());
		return isNetConnected;
	}

	public void startAnimActivityWithData(Class<?> cla,String key, Serializable value) {
		Intent intent=new Intent(getActivity(), cla);
		intent.putExtra(key, value);
		this.startActivity(intent);
	}

	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(getActivity(), cla));
	}
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}

	// 按钮模拟心脏跳动
	public void playHeartbeatAnimation(final View imageView) {
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f));
		animationSet.addAnimation(new AlphaAnimation(1.0f, 0.4f));

		animationSet.setDuration(200);
		animationSet.setInterpolator(new AccelerateInterpolator());
		animationSet.setFillAfter(true);

		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				AnimationSet animationSet = new AnimationSet(true);
				animationSet.addAnimation(new ScaleAnimation(0.5f, 1.0f, 0.5f,
						1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f));
				animationSet.addAnimation(new AlphaAnimation(0.4f, 1.0f));

				animationSet.setDuration(600);
				animationSet.setInterpolator(new DecelerateInterpolator());
				animationSet.setFillAfter(false);

				// 实现心跳的View
				imageView.startAnimation(animationSet);
			}
		});

		// 实现心跳的View
		imageView.startAnimation(animationSet);
	}

	/**
	 * 打Log ShowLog
	 * 
	 * @return void
	 * @throws
	 */
	public void ShowLog(String msg) {
		Log.i("fruit", msg);
	}
	
	
	
	
	
	
	 /** Fragment当前状态是否可见 */
	
    protected boolean isVisible;
     
     
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(getUserVisibleHint()) {
            isVisible = true;
            onInvisible();
        } else {
            isVisible = false;
            onVisible();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
     
     
    /**
     * 可见
     */
    protected void onVisible() {
        //lazyLoad();     
    }
     
     
    /**
     * 不可见
     */
    protected void onInvisible() {
         
         
    }

     
    /** 
     * 延迟加载
     * 子类必须重写此方法
     */
    //protected abstract void lazyLoad();


}
