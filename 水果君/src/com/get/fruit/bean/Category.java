/**   
* @Title: Category.java
* @Package com.get.fruit.bean 
* @Description: TODO
* @author LiQinglin 
* @date 2015-8-13 下午9:48:38 
* @version V1.0   
*/
package com.get.fruit.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/** 
 * @ClassName: Category 
 * @Description: TODO
 * @author LiQinglin
 * @date 2015-8-13 下午9:48:38 
 *  
 */

public class Category  extends BmobObject implements Serializable{
	
	private static final long serialVersionUID = 85208520L;
	
	private String categoryName;
	private String[] functions;
	private Taste taste;
	
	
	
	
	public Taste getTaste() {
		return taste;
	}
	public void setTaste(Taste taste) {
		this.taste = taste;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String[] getFunctions() {
		return functions;
	}
	public void setFunctions(String[] functions) {
		this.functions = functions;
	}
	public Category(String categoryName, String[] functions,Taste taste) {
		super();
		this.categoryName = categoryName;
		this.functions = functions;
		this.taste=taste;
	}
	
	public Category() {
		super();
	}
	
	
	
	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}



	public enum Taste{
	
		酸酸的,甜甜的,小清新,重口味;
		
	}
	
	/*public enum Taste {

		SOUR("酸酸的"), SWEET("甜甜的"), LIGHT("小清新"), STRONG("重口味");
		
		private String describeString;
		private Taste(String taste) {
			// TODO Auto-generated constructor stub
			this.describeString=taste;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.describeString;
		}
	}*/

}
