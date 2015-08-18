/**   
* @Title: MyViewPagerAdapter.java
* @Package com.get.fruit.adapter 
* @Description: TODO
* @author LiQinglin 
* @date 2015-8-14 上午10:05:26 
* @version V1.0   
*//*
package com.get.fruit.adapter;

*//** 
 * @ClassName: MyViewPagerAdapter 
 * @Description: TODO
 * @author LiQinglin
 * @date 2015-8-14 上午10:05:26 
 *  
 *//*
public class MyViewPagerAdapter {

	//继承PagerAdapter类需要重写五个方法：


	public int getCount() {
		return pages.size();
	}
	//返回page的长度




	public boolean isViewFromObject(View arg0, Object arg1) {
	return arg0==arg1;
	}
	//判断instantiateItem(ViewGroup container, int position)返回的要加载的pager对象是不是view视图，是则返回true并显示，不是返回false不显示。




	public CharSequence getPageTitle(int position) {
	return super.getPageTitle(position);
	}
	//获取对应position的tab区域要显示的文字。




	public void destroyItem(ViewGroup container, int position, Object object) {
	container.removeView(pages.get(position));
	}
	销毁多余3个的pager对象。
	container vp对象
	position vp对象的下标






	public Object instantiateItem(ViewGroup container, int position) {
	container.addView(pages.get(position));
	return pages.get(position);
	}
	装填pager的方法，/container：用来装填page的ViewPager对象
	position：装填过程中给每个pager的下标
	返回的对象就是当前填充进去的pager对象










	//ViewPager的点击事件方法：
	setOnPageChangeListener();


	public void onPageScrollStateChanged(int arg0) {}
	//页面滑动状态改变的时候被调用，arg0就是当前显示pager的position


	public void onPageScrolled(int arg0, float arg1, int arg2) {}
	//页面滑动起来的时候调用。arg0为当前显示的pager的position，arg1是滑动百分比，arg2是滑动以后的position


	public void onPageSelected(int arg0) {}
	//滑动完成以后的事件方法，arg0为滑动以后的position
}
*/