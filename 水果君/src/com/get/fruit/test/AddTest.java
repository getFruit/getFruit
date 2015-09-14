/**   
* @Title: AddTest.java
* @Package com.get.fruit.test 
* @Description: TODO
* @author LiQinglin 
* @date 2015-8-26 上午12:51:30 
* @version V1.0   
*/
package com.get.fruit.test;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

import com.get.fruit.bean.Category;
import com.get.fruit.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/** 
 * @ClassName: AddTest 
 * @Description: TODO
 * @author LiQinglin
 * @date 2015-8-26 上午12:51:30 
 *  
 */
public class AddTest {

	/** 
	 * @Title: main 
	 * @Description: TODO
	 * @param @param args
	 * @return void
	 * @throws 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	/*	
		ShowLog("批量添加...");
		String ss = FileUtil.readAssets(this, "category.json");
		Gson gson=new Gson();
		ArrayList<BmobObject> arrayList=gson.fromJson(ss, new TypeToken<List<Category>>() { }.getType());
		new BmobObject().insertBatch(this, arrayList, new SaveListener() {
		    @Override
		    public void onSuccess() {
		        // TODO Auto-generated method stub
		    	ShowLog("批量添加成功");
		    }
		    @Override
		    public void onFailure(int code, String msg) {
		        // TODO Auto-generated method stub
		    	ShowLog("批量添加失败:"+msg);
		    }
		});
		
		*
		*/
		
		
	}

}
