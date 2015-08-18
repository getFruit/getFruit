/**   
* @Title: HomeAD.java
* @Package com.get.fruit.bean 
* @Description: TODO
* @author LiQinglin 
* @date 2015-8-16 ÏÂÎç5:10:09 
* @version V1.0   
*/
package com.get.fruit.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** 
 * @ClassName: HomeAD 
 * @Description: TODO
 * @author LiQinglin
 * @date 2015-8-16 ÏÂÎç5:10:09 
 *  
 */
public class HomeAD extends BmobObject implements Serializable {
	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 7028325953020366322L;
	private String name;
	private float price;
	private BmobFile pic;
	private Fruit fruit;
	public HomeAD(String name, float price, BmobFile pic, Fruit fruit) {
		super();
		this.name = name;
		this.price = price;
		this.pic = pic;
		this.fruit = fruit;
	}
	public HomeAD() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public BmobFile getPic() {
		return pic;
	}
	public void setPic(BmobFile pic) {
		this.pic = pic;
	}
	public Fruit getFruit() {
		return fruit;
	}
	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
	}
	
	
}
