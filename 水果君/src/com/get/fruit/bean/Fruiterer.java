/**   
* @Title: Fruiterer.java
* @Package com.get.fruit.bean 
* @Description: TODO
* @author LiQinglin 
* @date 2015-8-1 ионГ4:19:17 
* @version V1.0   
*/
package com.get.fruit.bean;

/** 
 * @ClassName: Fruiterer 
 * @Description: TODO
 * @author LiQinglin
 * @date 2015-8-1 ионГ4:19:17 
 *  
 */
public class Fruiterer extends User{
	private String id,name,address;
	private int consume;
	private double  sale;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getConsume() {
		return consume;
	}
	public void setConsume(int consume) {
		this.consume = consume;
	}
	public double getSale() {
		return sale;
	}
	public void setSale(double sale) {
		this.sale = sale;
	}
	
	
}
