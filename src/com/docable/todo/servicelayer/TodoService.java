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

public class TodoService {
	public static String addTodo(String data) {
		HashMap<String, String> parsedDataMap = parsePayLoad(data);

		String channelId = parsedDataMap.get("channel_id");
		String todoText = parsedDataMap.get("text");
		todoText = parseString(todoText);

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

	private static String parseString(String todoText) {
		todoText = todoText.replaceAll("%2C", ",");
		todoText = todoText.replaceAll("[+]", " ");
		todoText = todoText.replaceAll("%27", "'");
		todoText = todoText.replaceAll("%22", "\"");
		todoText = todoText.replaceAll("%26", "&");
		todoText = todoText.replaceAll("%3F", "?");
		return todoText;
	}

	public static List<String> fetchAlltodos(String data) {
		HashMap<String, String> parsedDataMap = parsePayLoad(data);

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
		HashMap<String, String> parsedDataMap = parsePayLoad(data);
		String channelId = parsedDataMap.get("channel_id");
		String todoText = parsedDataMap.get("text");
		todoText = parseString(todoText);

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

	public static void printPayLoad(String payLod) throws IOException {
		System.out.println("DATA:" + payLod);
		HashMap<String, String> payLoadMap = parsePayLoad(payLod);
		for (Entry<String, String> entry : payLoadMap.entrySet()) {
			System.out.println(entry.getKey() + ":      " + entry.getValue());
		}
		return;
	}

	private static HashMap<String, String> parsePayLoad(String payLod) {
		String[] parsedString = payLod.split("&");
		HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < parsedString.length; i++) {
			String[] keyPair = parsedString[i].split("=");
			if (keyPair.length == 2) {
				map.put(keyPair[0], keyPair[1]);
			}
		}
		return map;
	}

	public static String getTaskName(String requestURI) {
		String[] uriPieces = requestURI.split("/");
		return uriPieces[uriPieces.length - 1];
	}
}