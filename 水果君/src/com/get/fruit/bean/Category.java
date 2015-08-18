/**   
* @Title: Category.java
* @Package com.get.fruit.bean 
* @Description: TODO
* @author LiQinglin 
* @date 2015-8-13 обнГ9:48:38 
* @version V1.0   
*/
package com.get.fruit.bean;

/** 
 * @ClassName: Category 
 * @Description: TODO
 * @author LiQinglin
 * @date 2015-8-13 обнГ9:48:38 
 *  
 */
public class Category {
	private String categoryName;
	private String[] functions;
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
	public Category(String categoryName, String[] functions) {
		super();
		this.categoryName = categoryName;
		this.functions = functions;
	}
	
	public Category() {
		super();
	}
	
	

}
