//$Id$
package com.docable.todo.servicelayer;

import com.docable.todo.daolayer.DAOFactory;
import com.docable.todo.daolayer.TodoDAO;
import com.docable.todo.model.Todo;

public class DAOTest {

    public static void main(String[] args) throws Exception {
        // Obtain DAOFactory.
        DAOFactory docable = DAOFactory.getInstance("docable.jdbc");
        System.out.println("DAOFactory successfully obtained: " + docable);

        // Obtain TodoDAO.
        TodoDAO todoDAO = docable.getToDoDAO();
        System.out.println("UserDAO successfully obtained: " + todoDAO);

       // Create todo.
        Todo todo = new Todo();
        //todo.setChannelId(1L);
        todo.setDone(false);
        todo.setTodo("This is testing text Not a Task");
        todoDAO.createTodo(todo);
        System.out.println("User successfully created: " + todo);

    }

}
