//$Id$
package com.docable.todo.model;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6843644360632495953L;

	private Long userId;
	private String userName;
	private Long channelId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof User) && (userId != null) ? userId.equals(((User) other).userId) : (other == this);
	}

	@Override
	public int hashCode() {
		return (userId != null) ? (userId.hashCode()) : super.hashCode();
	}

	@Override
	public String toString() {
		return String.format("User[id=%d,Name=%s,ChannelId=%s]", userId, userName, channelId);
	}
}
