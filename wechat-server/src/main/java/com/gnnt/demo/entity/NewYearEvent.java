package com.gnnt.demo.entity;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by Song on 2017/2/15.
 * Model 
 */
@Entity
@Table
public class NewYearEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private Long parentId;

	/**0 发起者，1，参与者，2 接收者*/
	private Integer type;

	private String openId;

	private String title;

	private String nickName;

	private String avatarUrl;

	// 0邀请他人 1 发送祝福  2 等待其他人加入 3. 待发送 ，4.结束
	private Integer status;


	private String parentName;

	private Date  createTime;


    private String content;

	private Long contFrim;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getContFrim() {
		return contFrim;
	}

	public void setContFrim(Long contFrim) {
		this.contFrim = contFrim;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
