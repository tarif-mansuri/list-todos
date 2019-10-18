//$Id$
package com.docable.todo.servicelayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.docable.todo.daolayer.DAOFactory;
import com.docable.todo.daolayer.TodoDAO;
import com.docable.todo.model.Todo;
import com.docable.todo.serviceutils.ServiceUtils;

public class TodoService {
	public static String addTodo(String data) {
		HashMap<String, String> parsedDataMap = ServiceUtils.parsePayLoad(data);

		String channelId = parsedDataMap.get("channel_id");
		String todoText = parsedDataMap.get("text");
		todoText = ServiceUtils.parseString(todoText);

		// Obtain DAOFactory.
		DAOFactory docable = DAOFactory.getInstance("docable.jdbc");
		System.out.println("DAOFactory successfully obtained: " + docable);

		// Obtain TodoDAO.
		TodoDAO todoDAO = docable.getToDoDAO();
		System.out.println("UserDAO successfully obtained: " + todoDAO);

		// Create todo.
		Todo todo = new Todo();
		todo.setChannelId(channelId);
		todo.setDone(false);
		todo.setTodo(todoText);
		todoDAO.createTodo(todo);
		System.out.println("User successfully created: " + todo);
		return todoText;
	}

	public static List<String> fetchAlltodos(String data) {
		HashMap<String, String> parsedDataMap = ServiceUtils.parsePayLoad(data);

		String channelId = parsedDataMap.get("channel_id");
		// Obtain DAOFactory.
		DAOFactory docable = DAOFactory.getInstance("docable.jdbc");
		System.out.println("DAOFactory successfully obtained: " + docable);

		// Obtain TodoDAO.
		TodoDAO todoDAO = docable.getToDoDAO();
		List<Todo> todos = todoDAO.listToDos(channelId);
		List<String> listTodos = new ArrayList<>();
		for (Todo todo : todos) {
			listTodos.add(todo.getTodo());
		}
		return listTodos;
	}

	public static String markTodo(String data) {
		HashMap<String, String> parsedDataMap = ServiceUtils.parsePayLoad(data);
		String channelId = parsedDataMap.get("channel_id");
		String todoText = parsedDataMap.get("text");
		todoText = ServiceUtils.parseString(todoText);

		// Obtain DAOFactory.
		DAOFactory docable = DAOFactory.getInstance("docable.jdbc");
		System.out.println("DAOFactory successfully obtained: " + docable);

		// Obtain TodoDAO.
		TodoDAO todoDAO = docable.getToDoDAO();
		if (todoDAO.markAsDone(channelId, todoText) > 0) {
			return todoText;
		}
		return null;
	}
}