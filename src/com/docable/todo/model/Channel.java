//$Id$
package com.docable.todo.model;

import java.io.Serializable;

public class Channel implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -630407060117859552L;
	
	private Long channelId;
	private String channelName;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public boolean equals(Object other) {
		return other != null && (other instanceof Channel) ? (channelId == ((Channel) other).channelId)
				: (other == this);
	}

	@Override
	public int hashCode() {
		return channelId == null ? super.hashCode() : channelId.hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("Chanel[id=%d,ChannelName=%s]", channelId, channelId);
	}

}
