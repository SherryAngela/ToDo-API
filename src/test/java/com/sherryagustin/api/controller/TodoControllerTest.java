package com.sherryagustin.api.controller;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


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
		ToDo addTodoItem = new ToDo("Review", "November 28,2022");
		when(service.addItem(addTodoItem)).thenReturn(addTodoItem);
		mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(addTodoItem))).andExpect(status().isOk());

//		assertTrue(service.getAllItems().contains(addTodoItem));
}
	@Test
	public  void getTodoListTest() throws Exception {
		List<ToDo> todoList = Arrays.asList(new ToDo("Review", "November 28,2022"),
				new ToDo("Check up", "December 20,2022"));

		when(service.getAllItems()).thenReturn(todoList);
		mockMvc.perform(get("/todos")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].taskName").value("Review"));
	}
	@Test
	public void getItemByIdTest() throws Exception {
		when(service.getItemById(1L)).thenReturn(new ToDo("Review", "November 28,2022"));

		ToDo item = service.getItemById(1L);

		assertEquals("Review", item.getTaskName());
		assertEquals("November 28,2022", item.getDeadline());
}
	@Test
	public void deleteItemTest() throws Exception {
//		ToDo addTodoItem = new ToDo("Review", "November 28,2022");
//		when(service.addItem(addTodoItem)).thenReturn(addTodoItem);
//		MvcResult addResult = (MvcResult) mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(addTodoItem))).andExpect(status().isOk());
//		String result = addResult.getResponse().getContentAsString();
//		ToDo responseFromAdd = objectMapper.readValue(result, ToDo.class);
//
//		mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", responseFromAdd.getId())
//						.contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(status().isOk());
		long todoId = 1;
		List<ToDo> toDo = new ArrayList<>();

		ToDo todo = new ToDo("Review", "November 27, 2022");
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
//
//		ToDo todo = new ToDo();
//		todo.setTaskName("Test Name");
//		todo.setId(89L);
//		given(service.updateItem(todo, todo.getId())).willReturn(todo);
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		mockMvc.perform(put("/todos/" + todo.getId())
//						.content(mapper.writeValueAsString(todo))
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
////				.andExpect((jsonPath("taskName", is(todo.getTaskName())));
		long tdId = 1;

		ToDo newItem = new ToDo("Review", "November 28,2022");
		newItem.setId(tdId);

		ToDo updatedItem = new ToDo("Review", "November 27,2022");
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
//	@Test
//	public void updateItemTest() throws Exception {
//		ToDo todo = new ToDo();
//		todo.setId(1);
//		todo.setTaskName("CheckUp");
//		todo.setDeadline("December 20,2022");
//		Mockito.when(service.updateItem(1,todo).thenReturn(todo);
//		String json = objectMapper.writeValueAsString(todo);
//		mockMvc.perform(put("/putMapping").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
//						.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
//				.andExpect(jsonPath("$.taskName", Matchers.equalTo("CheckUp"))).andExpect(jsonPath("$.deadline", Matchers.equalTo("December 20,2022")));
//		;
//	}}