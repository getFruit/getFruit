package com.get.fruit.bean;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject {

	
    public Order() {
		super();
	}


    
    public Order(User user, Fruit fruit, Integer count) {
		super();
		this.user = user;
		this.fruit = fruit;
		this.count = count;
	}



	//private List<Fruit> fruits;//һ�����
    private User user;
    private Fruit fruit;
    private Integer count;
    private State state;//����״̬
    private SendWay sendway;//���ͷ�ʽ
    private Double sum;//���
    private PayWay payway;//֧����ʽ
    private Boolean pay;//�Ƿ�֧��
    private UserAdress consignee;//�ջ���
    private String messenger;//����
    private String orderid;
    
    public  enum State{
    	
    	�����µ�,�ȴ�֧��,֧��ʧ��,�ȴ�����,�ѷ���,�������,���׹ر�
    }
    
    public enum SendWay{
    	�ͻ�����,��ȡ,���
    }
    public enum PayWay{
    	֧����,΢��֧��
    }
    
    
    
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public Double getSum() {
		return sum;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Fruit getFruit() {
		return fruit;
	}
	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public SendWay getSendway() {
		return sendway;
	}
	public void setSendway(SendWay sendway) {
		this.sendway = sendway;
	}
	public PayWay getPayway() {
		return payway;
	}
	public void setPayway(PayWay payway) {
		this.payway = payway;
	}
	public Boolean getPay() {
		return pay;
	}
	public void setPay(Boolean pay) {
		this.pay = pay;
	}
	public UserAdress getConsignee() {
		return consignee;
	}
	public void setConsignee(UserAdress consignee) {
		this.consignee = consignee;
	}
	public String getMessenger() {
		return messenger;
	}
	public void setMessenger(String messenger) {
		this.messenger = messenger;
	}
    
    
    
    
    
}