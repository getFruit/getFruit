/**   
* @Title: CartItem.java
* @Package com.get.fruit.activity.bean 
* @Description: TODO
* @author LiQinglin 
* @date 2015-9-4 обнГ3:29:17 
* @version V1.0   
*/
package com.get.fruit.bean;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

import cn.bmob.v3.BmobObject;

/** 
 * @ClassName: CartItem 
 * @Description: TODO
 * @author LiQinglin
 * @date 2015-9-4 обнГ3:29:17 
 *  
 */
public class CartItem extends BmobObject{

	private User mine;
	private Fruit fruit;
	private Integer count=new Integer(1);
	public User getMine() {
		return mine;
	}
	public void setMine(User mine) {
		this.mine = mine;
	}
	public Fruit getFruit() {
		return fruit;
	}
	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
}
