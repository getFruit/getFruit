package com.get.fruit.activity;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.get.fruit.R;
import com.get.fruit.bean.Category.CategoryName;
import com.get.fruit.bean.Fruit;
import com.get.fruit.bean.Category.Function;
import com.get.fruit.bean.Category.Taste;
import com.get.fruit.bean.Fruit.Color;
import com.get.fruit.bean.Fruit.Origin;
import com.get.fruit.bean.Fruit.Season;
import com.get.fruit.view.tree.Node;
import com.get.fruit.view.tree.SimpleTreeListViewAdapter;
import com.get.fruit.view.tree.TreeListViewAdapter.OnTreeNodeClickListener;
import com.get.fruit.view.tree.TreeNodeId;
import com.get.fruit.view.tree.TreeNodeLabel;
import com.get.fruit.view.tree.TreeNodePid;

public class CategorySelectActivity extends Activity {

	private ListView mTree;
	private SimpleTreeListViewAdapter<CategoryNode> mAdapter;
	private List<CategoryNode> mDatas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_select);
		
		
		mTree = (ListView) findViewById(R.id.listView);

		initDatas();
		try
		{
			mAdapter = new SimpleTreeListViewAdapter<CategoryNode>(mTree, this,
					mDatas, 0);
			mTree.setAdapter(mAdapter);
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}

		initEvent();
	}

	private void initEvent()
	{
		mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener()
		{
			@Override
			public void onClick(Node node, int position)
			{
				if (node.isLeaf())
				{
				}
			}
		});
	}
	
	
	private void initDatas()
	{
		int i=1;
		mDatas = new ArrayList<CategoryNode>();
		CategoryNode 
		node=new CategoryNode(i, 0, "种类");
		mDatas.add(node);
		i++;
		node=new CategoryNode(i, 0, "季节");
		mDatas.add(node);
		i++;
		node=new CategoryNode(i, 0, "产地");
		mDatas.add(node);
		i++;
		node=new CategoryNode(i, 0, "功能");
		mDatas.add(node);
		i++;
		node=new CategoryNode(i, 0, "颜色");
		mDatas.add(node);
		i++;
		node=new CategoryNode(i, 0, "口味");
		mDatas.add(node);
		i++;
		
		for(CategoryName item:CategoryName.values()){
			node=new CategoryNode(i, 1, item.toString());
			mDatas.add(node);
			i++;
		}
		for(Season item:Season.values()){
			node=new CategoryNode(i, 2, item.toString());
			mDatas.add(node);
			i++;
		}
		for(Origin item:Origin.values()){
			node=new CategoryNode(i, 3, item.toString());
			mDatas.add(node);
			i++;
		}
		for(Function item:Function.values()){
			node=new CategoryNode(i, 4, item.toString());
			mDatas.add(node);
			i++;
		}
		for(Color item:Color.values()){
			node=new CategoryNode(i, 5, item.toString());
			mDatas.add(node);
			i++;
		}
		for(Taste item:Taste.values()){
			node=new CategoryNode(i, 6, item.toString());
			mDatas.add(node);
			i++;
		}
	}
	
	
	public class CategoryNode{
		@TreeNodeId
		private int _id;
		@TreeNodePid
		private int parentId;
		@TreeNodeLabel
		private String name;
		
		public CategoryNode(int _id, int parentId, String name)
		{
			this._id = _id;
			this.parentId = parentId;
			this.name = name;
		}

		public int get_id()
		{
			return _id;
		}

		public void set_id(int _id)
		{
			this._id = _id;
		}

		public int getParentId()
		{
			return parentId;
		}

		public void setParentId(int parentId)
		{
			this.parentId = parentId;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}
}
