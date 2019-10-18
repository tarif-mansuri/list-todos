package com.docable.todo.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.docable.todo.servicelayer.TodoService;
import com.docable.todo.serviceutils.ServiceUtils;

/**
 * Servlet implementation class ToDoController
 */
@WebServlet("/ToDoController")
public class ToDoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ToDoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		String task = ServiceUtils.getTaskName(request.getRequestURI());
		if (task.equals("addtodo.do")) {
			// add todo
			String addedTask = TodoService.addTodo(data);
			response.setStatus(200);
			response.setContentType("text/xml");
			PrintWriter writer = response.getWriter();
			writer.append("Added TODO for \" " + addedTask + "\"");
		} else if (task.equals("listtodos.do")) {
			// List all todos
			List<String> tasks = TodoService.fetchAlltodos(data);
			response.setStatus(200);
			response.setContentType("text/xml");
			PrintWriter writer = response.getWriter();
			if (tasks.isEmpty()) {
				writer.append("No TODOs\n");
			} else
				for (String todo : tasks) {
					writer.append(todo + "\n");
				}
		} else {
			// mark todo inactive
			ServiceUtils.printPayLoad(data);
			String markedTask = TodoService.markTodo(data);
			response.setStatus(200);
			response.setContentType("text/xml");
			PrintWriter writer = response.getWriter();
			if (markedTask != null) {
				writer.append("Removed TODO for \"" + markedTask + "\"\n");
			} else {
				writer.append("Todo does not exists\n");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
