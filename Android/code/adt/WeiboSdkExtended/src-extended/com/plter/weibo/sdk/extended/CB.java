package com.plter.weibo.sdk.extended;


/**
 * 
 * @author plter 
 * @website	http://plter.com 
 * @webshop	http://plter.taobao.com 
 * @blog	http://blog.plter.com
 *
 */
public interface CB<T> {
	void suc(T obj);
	void fail();
}
