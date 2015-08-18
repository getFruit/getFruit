package com.get.fruit.activity;


import h.in;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.get.fruit.R;
import com.get.fruit.activity.BaseActivity.OnLeftButtonClickListener;
import com.get.fruit.view.HeaderLayout.onRightImageButtonClickListener;

public class LocationActivity extends BaseActivity {
    //省份view集合
   List<TextView> prT=new ArrayList<TextView>();
    List<View> prV=new ArrayList<View>();
    //城市view集合
    List<TextView> city_TextView=new ArrayList<TextView>() ;
    List<View> city_View=new ArrayList<View>();
    //区县view集合
    List<TextView> area_TextView=new ArrayList<TextView>();
    List<View> area_View=new ArrayList<View>();
    //json
    String js;
    String full_name;//最后结果储存在这个字符串里
    String[] address ={"","",""};
    Intent intent;
    //copy的DP转px函数
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        intent= this.getIntent();
        initTopBarForBoth("选择地址", R.drawable.base_action_bar_back_bg_selector, null, new OnLeftButtonClickListener() , -1, "确定", new onRightImageButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				if(intent!=null){
					Bundle bundle=new Bundle();
					bundle.putCharSequenceArray("address", address);
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
        //设置灰色箭头
        Drawable city_go=getResources().getDrawable(R.drawable.location_city_go);
        city_go.setBounds(0,0,city_go.getMinimumWidth()+20,city_go.getMinimumHeight());
        final LinearLayout Li=(LinearLayout)findViewById(R.id.Lin);
        //json的解析
        try {
            InputStream is=this.getResources().openRawResource(R.raw.a);
            byte[] buffer=new byte[is.available()];
            is.available();
            is.read(buffer);

            String json=new String(buffer,"GB2312");
            js=new String(buffer,"GB2312");
            final JSONObject jsonObject=new JSONObject(json);
            JSONArray province=jsonObject.getJSONArray("p");
            //创建view
            for (int i = 0; i < province.length();i++)
            {
                TextView prTV=new TextView(this);
                prTV.setText(province.getString(i));
                prTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                prTV.setTextSize(20);
                prTV.setHeight(dip2px(LocationActivity.this, 60));
                prTV.setGravity(Gravity.CENTER_VERTICAL);
                prTV.setTextColor(Color.parseColor("#000000"));
                prTV.setCompoundDrawables(null, null, city_go, null);
                prTV.setId(i);
                prTV.setClickable(true);
                prTV.setFocusable(true);
                prTV.setOnClickListener(handler);
                Li.addView(prTV);
                View v1=new View(LocationActivity.this);
                v1.setBackgroundColor(Color.parseColor("#F1F1F1"));
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4);
                v1.setLayoutParams(params);
                Li.addView(v1);
                prT.add(prTV);
                prV.add(v1);
            }

        }
        catch(Exception e){
        	ShowToast("读取失败");
        }

    }
        //省的绑定事件
    View.OnClickListener handler=new View.OnClickListener(){

        @Override
        public void onClick(View v){
            TextView view=(TextView)findViewById(v.getId());
            String province_name=view.getText().toString();
            address[0]=province_name;
            Drawable city_go=getResources().getDrawable(R.drawable.location_city_go);
            city_go.setBounds(0, 0, city_go.getMinimumWidth()+20, city_go.getMinimumHeight());
            for(int i=0;i<prT.size();i++)
            {
                prT.get(i).setVisibility(View.GONE);
                prV.get(i).setVisibility(View.GONE);
            }
            //直辖市
            if(v.getId()<4)
            {
                try {
                    JSONObject cn_city = new JSONObject(js);
                    JSONObject area=cn_city.getJSONObject("a");
                    String city_name=province_name+"-"+province_name;
                    JSONArray area_name=area.getJSONArray(city_name);
                    final LinearLayout Li=(LinearLayout)findViewById(R.id.Lin);
                    for(int i=0;i<area_name.length();i++) {
                        TextView prTV=new TextView(LocationActivity.this);
                        prTV.setText(area_name.getString(i));
                        prTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        prTV.setTextSize(20);
                        prTV.setHeight(dip2px(LocationActivity.this, 60));
                        prTV.setGravity(Gravity.CENTER_VERTICAL);
                        prTV.setTextColor(Color.parseColor("#000000"));
                        prTV.setClickable(true);
                        prTV.setFocusable(true);
                        prTV.setId(34+i);
                        prTV.setOnClickListener(area_handler);
                        Li.addView(prTV);
                        View v1=new View(LocationActivity.this);
                        v1.setBackgroundColor(Color.parseColor("#F1F1F1"));
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4);
                        v1.setLayoutParams(params);
                        Li.addView(v1);
                        city_TextView.add(prTV);
                        city_View.add(v1);
                    }
                    //返回上一层的button
                    Button re_button=new Button(LocationActivity.this);
                    re_button.setText(LocationActivity.this.getString(R.string.re_back));
                    re_button.setWidth(100);
                    re_button.setHeight(dip2px(LocationActivity.this, 40));
                    re_button.setGravity(Gravity.CENTER);
                    re_button.setBackgroundColor(Color.parseColor("#000000"));
                    re_button.setTextColor(Color.parseColor("#D9D9D9"));
                    re_button.setOnClickListener(back_province_handler);
                    re_button.setId(443 + 556);
                    Li.addView(re_button);

                }
                catch (Exception e) {
                };

            }else//非直辖市
            {
                try {
                    JSONObject cn_city = new JSONObject(js);
                    JSONObject city=cn_city.getJSONObject("c");
                    JSONArray city_name=city.getJSONArray(province_name);
                    final LinearLayout Li=(LinearLayout)findViewById(R.id.Lin);
                    for(int i=0;i<city_name.length();i++) {
                        TextView prTV=new TextView(LocationActivity.this);
                        prTV.setText(city_name.getString(i));
                        prTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        prTV.setTextSize(20);
                        prTV.setHeight(dip2px(LocationActivity.this, 60));
                        prTV.setGravity(Gravity.CENTER_VERTICAL);
                        prTV.setTextColor(Color.parseColor("#000000"));
                        prTV.setClickable(true);
                        prTV.setFocusable(true);
                        prTV.setCompoundDrawables(null, null, city_go, null);
                        prTV.setOnClickListener(city_handler);
                        prTV.setId(34+i);
                        Li.addView(prTV);
                        View v1=new View(LocationActivity.this);
                        v1.setBackgroundColor(Color.parseColor("#F1F1F1"));
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4);
                        v1.setLayoutParams(params);
                        Li.addView(v1);
                        city_TextView.add(prTV);
                        city_View.add(v1);
                    }
                    Button re_button=new Button(LocationActivity.this);
                    re_button.setText(LocationActivity.this.getString(R.string.re_back));
                    re_button.setWidth(100);
                    re_button.setHeight(dip2px(LocationActivity.this, 40));
                    re_button.setGravity(Gravity.CENTER);
                    re_button.setBackgroundColor(Color.parseColor("#000000"));
                    re_button.setTextColor(Color.parseColor("#FFFFFF"));
                    re_button.setOnClickListener(back_province_handler);
                    re_button.setId(443 + 556);
                    Li.addView(re_button);
                }
                catch (Exception e) {
                };
            }
        }
    };
    //城市点击事件
    View.OnClickListener city_handler=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView view=(TextView)findViewById(v.getId());
            String city_name=view.getText().toString();
            address[1]=city_name;
            Button btn=(Button)findViewById(443+556);
            btn.setVisibility(View.GONE);
            for(int i=0;i<city_TextView.size();i++)
            {
                city_TextView.get(i).setVisibility(View.GONE);
                city_View.get(i).setVisibility(View.GONE);
            }
            try{
                JSONObject cn_city = new JSONObject(js);
                JSONObject area=cn_city.getJSONObject("a");
                JSONArray area_name=area.getJSONArray(full_name);
                final LinearLayout Li=(LinearLayout)findViewById(R.id.Lin);
                for(int i=0;i<area_name.length();i++) {
                    TextView prTV=new TextView(LocationActivity.this);
                  prTV.setText(area_name.getString(i));
                      prTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    prTV.setTextSize(20);
                    prTV.setHeight(dip2px(LocationActivity.this, 60));
                    prTV.setGravity(Gravity.CENTER_VERTICAL);
                    prTV.setTextColor(Color.parseColor("#000000"));
                    prTV.setClickable(true);
                    prTV.setFocusable(true);
                    prTV.setOnClickListener(area_handler);
                    prTV.setId(100+i);
                    Li.addView(prTV);
                    View v1=new View(LocationActivity.this);
                    v1.setBackgroundColor(Color.parseColor("#F1F1F1"));
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4);
                    v1.setLayoutParams(params);
                    Li.addView(v1);
                    area_TextView.add(prTV);
                    area_View.add(v1);
                }
                Button re_button=new Button(LocationActivity.this);
                re_button.setText(LocationActivity.this.getString(R.string.re_back));
                re_button.setWidth(100);
                re_button.setHeight(dip2px(LocationActivity.this, 40));
                re_button.setGravity(Gravity.CENTER);
                re_button.setBackgroundColor(Color.parseColor("#000000"));
                re_button.setTextColor(Color.parseColor("#FFFFFF"));
                re_button.setOnClickListener(back_city_handler);
                Li.addView(re_button);

            }catch (Exception e) {
            };
        }
    };
    //区县点击事件
    View.OnClickListener area_handler=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView view=(TextView)findViewById(v.getId());
            view.setTextColor(getResources().getColor(R.color.base_color_blue));
            String area_name=view.getText().toString();
            address[2]=area_name;
        }
    };
    //回到省的返回事件
    Button.OnClickListener back_province_handler=new Button.OnClickListener(){
        @Override
        public void onClick(View v)
        {
            final LinearLayout Li=(LinearLayout)findViewById(R.id.Lin);
            for(int i=0;i<city_TextView.size();i++)
            {
                Li.removeView(city_TextView.get(i));
                Li.removeView(city_View.get(i));
            }
            Li.removeView(v);
            city_TextView.clear();
            city_View.clear();
            for(int i=0;i<prT.size();i++)
            {
                prT.get(i).setVisibility(View.VISIBLE);
                prV.get(i).setVisibility(View.VISIBLE);
            }
            full_name="";
            address[0]="";
            address[1]="";
        }
    };
    //回到市的返回事件
    Button.OnClickListener back_city_handler=new Button.OnClickListener(){
        @Override
        public void onClick(View v)
        {
            final LinearLayout Li=(LinearLayout)findViewById(R.id.Lin);
            for(int i=0;i<area_TextView.size();i++)
            {
                Li.removeView(area_TextView.get(i));
                Li.removeView(area_View.get(i));
            }
            Li.removeView(v);
            area_TextView.clear();
            area_View.clear();
            Button btn=(Button)findViewById(443+556);
            btn.setVisibility(View.VISIBLE);
            for(int i=0;i<city_TextView.size();i++)
            {
                city_TextView.get(i).setVisibility(View.VISIBLE);
                city_View.get(i).setVisibility(View.VISIBLE);
            }
            address[1]="";
            address[2]="";
        }
    };
//热门城市事件 没有返回
    public void hot_city(View v)
    {
        Button btn=(Button)findViewById(v.getId());
        full_name=btn.getTag().toString();
        String json_name;
        if(btn.getText().toString().equals("北京") || btn.getText().toString().equals("上海") ) {
           json_name = full_name+"-"+full_name;
        }else
        {
            json_name=full_name;
        }
        final LinearLayout Li=(LinearLayout)findViewById(R.id.Lin);
        for(int i=0;i<city_TextView.size();i++)
        {
            city_TextView.get(i).setVisibility(View.GONE);
            city_View.get(i).setVisibility(View.GONE);
        }

        for(int i=0;i<prT.size();i++)
        {
            prT.get(i).setVisibility(View.GONE);
            prV.get(i).setVisibility(View.GONE);
        }
        for(int i=0;i<area_TextView.size();i++)
        {
            Li.removeView(area_TextView.get(i));
            Li.removeView(area_View.get(i));
        }
        area_TextView.clear();
        area_View.clear();
        try{
            JSONObject cn_city = new JSONObject(js);
            JSONObject area=cn_city.getJSONObject("a");
            JSONArray area_name=area.getJSONArray(json_name);
            for(int i=0;i<area_name.length();i++) {
                TextView prTV=new TextView(LocationActivity.this);
                prTV.setText(area_name.getString(i));
                prTV.setBackgroundColor(Color.parseColor("#FFFFFF"));
                prTV.setTextSize(20);
                prTV.setHeight(dip2px(LocationActivity.this, 60));
                prTV.setGravity(Gravity.CENTER_VERTICAL);
                prTV.setTextColor(Color.parseColor("#000000"));
                prTV.setClickable(true);
                prTV.setFocusable(true);
                prTV.setOnClickListener(area_handler);
                prTV.setId(100+i);
                Li.addView(prTV);
                View v1=new View(LocationActivity.this);
                v1.setBackgroundColor(Color.parseColor("#F1F1F1"));
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4);
                v1.setLayoutParams(params);
                Li.addView(v1);
                area_TextView.add(prTV);
                area_View.add(v1);
            }
        }
        catch (Exception e) {
        };
    }
}
