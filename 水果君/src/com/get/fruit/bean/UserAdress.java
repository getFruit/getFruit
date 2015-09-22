/**   
* @Title: UserAdress.java
* @Package com.get.fruit.bean 
* @Description: TODO
* @author LiQinglin 
* @date 2015-9-17 下午11:30:23 
* @version V1.0   
*/
package com.get.fruit.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/** 
 * @ClassName: UserAdress 
 * @Description: TODO
 * @author LiQinglin
 * @date 2015-9-17 下午11:30:23 
 *  
 */
public class UserAdress extends BmobObject {

	private User mine;
	private BmobGeoPoint location;
	//姓名，电话，备用电话，详细地址描述，5级行政区，邮编
	private String name,phone,phone2,detail,region,postcode;
	
}
