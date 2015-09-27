package com.get.fruit.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Fruit extends BmobObject {

	/**
	 * 
	 */

	private String number,name,describe,origin;//���,����,����
	private Category category;//��������
	private Color color;//��ɫ
	private Season season;//����
	private FruitShop shop;//�����̵�
	private Double price;//�۸�
	private double count;//����
	private Integer paynum;//����������ֱ��д��ʵ�壬���ٲ�ѯ��
	private Integer likesNumber;//�ղ�����
	private BmobRelation likes;//�ղص��û�
	private BmobFile picture;//��ͼ
	private String[] pictures;//��ͼ����ѡ��(url)
	private Boolean overdue;//�Ƿ����
	
	
	

	

	
	public Fruit(FruitShop shop) {
		this.shop = shop;
	}



	
	/**
	 * 
	 */
	public Fruit() {
		// TODO Auto-generated constructor stub
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getNumber() {
		return number;
	}



	public void setNumber(String number) {
		this.number = number;
	}



	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
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



	public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}



	public double getCount() {
		return count;
	}



	public void setCount(double count) {
		this.count = count;
	}



	


	public Integer getPaynum() {
		return paynum;
	}




	public void setPaynum(Integer paynum) {
		this.paynum = paynum;
	}




	public BmobRelation getLikes() {
		return likes;
	}



	public void setLikes(BmobRelation likes) {
		this.likes = likes;
	}

	
	public BmobFile getPicture() {
		return picture;
	}



	public void setPicture(BmobFile picture) {
		this.picture = picture;
	}



	public String[] getPictures() {
		return pictures;
	}



	public void setPictures(String[] pictures) {
		this.pictures = pictures;
	}

	
	
	
	
	
	
	
	
	
	
	
	public Integer getLikesNumber() {
		return likesNumber;
	}




	public void setLikesNumber(Integer likesNumber) {
		this.likesNumber = likesNumber;
	}




	public Boolean getOverdue() {
		return overdue;
	}




	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}













	public enum Season{
		
		����, ����, ����, ����;
		public String categoryBy(){
			return "����";
		}
	}
	
	public enum Color{
		
		��, ��, ��, ��, ��, ��, ��, ��;
		public String categoryBy(){
			return "��ɫ";
		}
	}
	
	public enum Origin{
		�Ϸ� ,���� ,���� ,����;
		public String categoryBy(){
			return "����";
		}
	}

}
