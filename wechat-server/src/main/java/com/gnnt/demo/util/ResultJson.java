/*******************************************************************
 * ResultJson.java   2018-4-26
 * Copyright 2015 by GNNT Company. All Rights Reserved.
 * Author:zhangzuoliang
 ******************************************************************/
package com.gnnt.demo.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * ǰ�˵��÷��ص�ʵ����<br/>
 * <p><a href="ResultJson.java.html"><i>view source</i></a></p>
 * @version 0.1
 * @author <a href="mailto:zhangzuol@gnnt.com.cn">������</a>
 * 
 */
public class ResultJson {

	/**
	 * ����������
	 */
	private Long returnCode;
	/**
	 * ����������Ϣ
	 */
	private String msg;
	/**
	 * ������������
	 */
	private Map<String,Object> data = new HashMap<String,Object>();
	
	/**��ȡδ������*/
	private Integer unReadNumber;
	
	public ResultJson(HttpServletRequest request) {
		Integer attribute = (Integer) request.getAttribute("unReadNumber");
		unReadNumber = attribute==null?0:attribute;
	}
	public Long getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(Long returnCode) {
		this.returnCode = returnCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public Integer getUnReadNumber() {
		return unReadNumber==null?0:unReadNumber;
	}
	
}
