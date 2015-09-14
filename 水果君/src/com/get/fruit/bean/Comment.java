package com.get.fruit.bean;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {

    private String content;//评论内容  

    private Integer point;//评分（）
    
    private User user;//评论的用户，Pointer类型，一对一关系

    private Fruit fruit; //所评论的水果
}