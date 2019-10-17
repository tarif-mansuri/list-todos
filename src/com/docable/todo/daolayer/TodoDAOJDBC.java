//$Id$
package com.docable.todo.daolayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.docable.todo.exceptions.CustomException;
import com.docable.todo.model.Todo;
import com.docable.todo.util.DAOUtil;

public class TodoDAOJDBC implements TodoDAO {

	// Constants
	// ----------------------------------------------------------------------------------

	private static final String SQL_LIST_TODOS = "SELECT todo_id, todo, done FROM todos where done = ? AND channel_id = ?";
	private static final String SQL_INSERT_TODOS = "INSERT INTO todos (todo,channel_id, done) VALUES (?, ?, ?)";
	private static final String SQL_MARK_DONE = "UPDATE todos SET done=true WHERE todo = ? AND channel_id = ?";

	private DAOFactory daoFactory;

	public TodoDAOJDBC(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<Todo> listToDos(String channelId) throws CustomException {
		List<Todo> toDoList = new ArrayList<Todo>();
		Todo todo = null;

		Object[] values = { false, channelId };
		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatementFetch(connection, SQL_LIST_TODOS, false, values);
				ResultSet resultSet = statement.executeQuery();) {
			while (resultSet.next()) {
				todo = mapFields(resultSet);
				toDoList.add(todo);
			}
		} catch (SQLException e) {
			throw new CustomException(e);
		}
		return toDoList;
	}

	@Override
	public void createTodo(Todo todo) throws IllegalArgumentException, CustomException {
		if (todo.getTodoId() != null) {
			throw new IllegalArgumentException("User is already created, the user ID is not null.");
		}

		Object[] values = { todo.getTodo(), todo.getChannelId(), todo.isDone() };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatementInsert(connection, SQL_INSERT_TODOS, true,
						values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new CustomException("Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					todo.setTodoId(generatedKeys.getLong(1));
				} else {
					throw new CustomException("Creating user failed, no generated key obtained.");
				}
			}
		} catch (SQLException e) {
			throw new CustomException(e);
		}

	}

	@Override
	public int markAsDone(String channelId, String todo) throws CustomException {
		if (todo == null) {
			throw new IllegalArgumentException("Task string cant be null.");
		}

		int affectedRows = 0;

		Object[] values = { todo, channelId };

		try (Connection connection = daoFactory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatementInsert(connection, SQL_MARK_DONE, false,
						values);) {
			affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new CustomException("Marking Task failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new CustomException(e);
		}

		return affectedRows;
	}

	private Todo mapFields(ResultSet resultSet) throws SQLException {
		Todo toDo = new Todo();
		toDo.setTodoId(resultSet.getLong("todo_id"));
		toDo.setTodo(resultSet.getString("todo"));
		toDo.setDone(resultSet.getBoolean("done"));
		return toDo;
	}

}
