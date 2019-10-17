//$Id$
package com.docable.todo.daolayer;

import java.util.List;

import com.docable.todo.exceptions.CustomException;
import com.docable.todo.model.Todo;

public interface TodoDAO {
    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns a list of all todos from the database ordered by todo ID. The list is never null and
     * is empty when the database does not contain any todo.
     * @return A list of all todos from the database ordered by todo ID.
     * @throws CustomException If something fails at database level.
     */
    public List<Todo> listToDos(String channelId) throws CustomException;

    /**
     * Create the given todos in the database. The todos ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given todos.
     * @param todos The todos to be created in the database.
     * @throws IllegalArgumentException If the todos ID is not null.
     * @throws CustomException If something fails at database level.
     */
    public void createTodo(Todo todo) throws IllegalArgumentException, CustomException;

    /**
     * Mark as done the given todo in the database.
     * we can list all done tasks as well
     * @param todo The todo to be marked as done in the database.
     * @throws CustomException If something fails at database level.
     */
    public int markAsDone(String channelId,String todo) throws CustomException;

}
