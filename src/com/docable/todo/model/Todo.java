//$Id$
package com.docable.todo.model;

import java.io.Serializable;

public class Todo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1432422662325930475L;
	private Long todoId;
	private String todo;
	private boolean done;
	private String channelId;

	public Long getTodoId() {
		return todoId;
	}

	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof Todo) && todo != null ? (todo.equals(((Todo) other).getTodo())) : other == this;
	}

	@Override
	public int hashCode() {
		return todo != null ? todo.hashCode() : super.hashCode();
	}

	@Override
	public String toString() {
		return String.format("Todo[id=%d,todo=%s,Done=%s,ChannelId=%s]", todoId, todo, done, channelId);
	}
}
