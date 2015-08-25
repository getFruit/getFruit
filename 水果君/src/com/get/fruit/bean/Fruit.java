package com.get.fruit.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Fruit extends BmobObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String number,describe,origin;//编号,描述,产地
	private CategoryName categoryName;//种类名称
	private Color color;//颜色
	private Season season;//季节
	private FruitShop shop;//所属商店
	private float price;//价格
	private double count;//数量
	private Integer favorite;//收藏数
	private BmobFile picture;//主图
	private BmobFile[] pictures;//附图（可选）
	
	
	


	
	public String getNumber() {
		return number;
	}



	public void setNumber(String number) {
		this.number = number;
	}



	public CategoryName getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(CategoryName categoryName) {
		this.categoryName = categoryName;
	}



	public Color getColor() {
		return color;
	}



	public void setColor(Color color) {
		this.color = color;
	}



	public Season getSeason() {
		return season;
	}



	public void setSeason(Season season) {
		this.season = season;
	}



	public String getOrigin() {
		return origin;
	}



	public void setOrigin(String origin) {
		this.origin = origin;
	}



	public String getDescribe() {
		return describe;
	}



	public void setDescribe(String describe) {
		this.describe = describe;
	}




	public FruitShop getShop() {
		return shop;
	}



	public void setShop(FruitShop shop) {
		this.shop = shop;
	}



	public float getPrice() {
		return price;
	}



	public void setPrice(float price) {
		this.price = price;
	}



	public double getCount() {
		return count;
	}



	public void setCount(double count) {
		this.count = count;
	}



	public Integer getFavorite() {
		return favorite;
	}



	public void setFavorite(Integer favorite) {
		this.favorite = favorite;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public BmobFile getPicture() {
		return picture;
	}



	public void setPicture(BmobFile picture) {
		this.picture = picture;
	}



	public BmobFile[] getPictures() {
		return pictures;
	}



	public void setPictures(BmobFile[] pictures) {
		this.pictures = pictures;
	}

	
	
	
	
	
	
	
	
	
	
	
	public enum Season{
		
		春天, 夏天, 秋天, 冬天
	}
	
	public enum Color{
		
		红, 黄, 蓝, 绿, 黑, 白, 紫, 橙
	}
	
	public enum Origin{
		南方 ,北方 ,西部 ,进口
	}
	
	public enum CategoryName{
		西瓜, 苹果, 柠檬, 柑橘, 猕猴桃, 樱桃, 葡萄, 草莓, 菠萝, 哈密瓜, 甘蔗, 橙子, 荔枝, 蓝莓, 榴莲, 柚子, 杨桃, 火龙果, 石榴, 香蕉, 木瓜, 梨, 树莓, 杏, 芒果, 桃, 山竹;
	}

}
