package com.sherryagustin.api.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sherryagustin.api.entity.ToDo;
import com.sherryagustin.api.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(TodoController.class)
class TodoControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TodoService service;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void addItemTest() throws Exception {
		ToDo addTodoItem = new ToDo("Review", 2022-12-21);
		when(service.addItem(addTodoItem)).thenReturn(addTodoItem);
		mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(addTodoItem))).andExpect(status().isOk());
}
	@Test
	public  void getTodoListTest() throws Exception {
		List<ToDo> todoList = Arrays.asList(new ToDo("Review", 2022-12-21),
				new ToDo("Check up", 2022-12-21));

		when(service.getAllItems()).thenReturn(todoList);
		mockMvc.perform(get("/todos")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].taskName").value("Review"));
	}
	@Test
	public void getItemByIdTest() throws Exception {
		when(service.getItemById(1L)).thenReturn(new ToDo("Review", 2022-12-21));

		ToDo item = service.getItemById(1L);

		assertEquals("Review", item.getTaskName());
		assertEquals(2022-12-21, item.getDeadline());
}
	@Test
	public void deleteItemTest() throws Exception {

		long todoId = 1;
		List<ToDo> toDo = new ArrayList<>();

		ToDo todo = new ToDo("Review", 2022-12-22);
		todo.setId(todoId);

		toDo.add(todo);

		assertTrue(toDo.contains(todo)); // check if item is added

		when(service.deleteItem(todoId)).thenReturn(true);

		// Act
		if (service.deleteItem(todoId)) {
			toDo.remove(todo);
		}

		// Assert
		assertFalse(toDo.contains(todo)); // check if order is deleted
	}


	@Test
	public void updateUser_whenPutUser() throws Exception {
		long tdId = 1;

		ToDo newItem = new ToDo("Review", 2022-12-21);
		newItem.setId(tdId);

		ToDo updatedItem = new ToDo("Review", 2022-12-22);
		updatedItem.setId(tdId);

		// return the newOrder object when you (mock) add it to the database
		when(service.addItem(newItem)).thenReturn(newItem);

		// the newOrder object's properties will now be updated to be the same as
		// updaterOrder object (just to simulate an actual database update
		// functionality)
		ToDo existingItem = service.addItem(newItem);
		existingItem.setTaskName(updatedItem.getTaskName());
		existingItem.setDeadline(updatedItem.getDeadline());
		existingItem.setId(tdId);

		assertNotNull(existingItem); // check if order is added

		// Act
		when(service.updateItem(updatedItem, existingItem.getId())).thenReturn(existingItem);
		ToDo updatedTodoFromDb = service.updateItem(updatedItem, existingItem.getId());

		// Assert
		assertEquals(updatedTodoFromDb, existingItem);
	}

}
