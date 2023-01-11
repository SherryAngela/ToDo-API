package com.sherryagustin.api.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sherryagustin.api.entity.ToDo;
import com.sherryagustin.api.exception.TodoException;
import com.sherryagustin.api.service.TodoService;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(TodoController.class)
class TodoControllerTest {
	private static final String INVALID_TASKNAME = "ERROR : Invalid Taskname - Taskname is null";
	private static final String SHORT_TASKNAME = "ERROR : Invalid Taskname - Taskname is too short";
	private static final String LONG_TASKNAME = "ERROR : Invalid Taskname - Taskname is too long";
	private static final String INVALID_COMMENT = "ERROR : Invalid Comment - Taskname is too null";
	private static final String LONG_COMMENT = "ERROR : Invalid Comment - Taskname is too long";

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TodoService service;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void addItemTest() throws Exception {
		LocalDate deadline = LocalDate.parse("2022-12-21");
		ToDo addTodoItem = new ToDo("Review", deadline,true,"Completed!");
		when(service.addItem(addTodoItem)).thenReturn(addTodoItem);
		mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(addTodoItem))).andExpect(status().isOk());
	}
	@Test
	public  void getAllItemsTest()  {
		LocalDate deadline1 = LocalDate.parse("2022-12-21");
		LocalDate deadline2 = LocalDate.parse("2023-01-15");
		List<ToDo> todoList = Arrays.asList(new ToDo("Review", deadline1, true, "Completed!"),
				new ToDo("Check up", deadline2, false,"Appointment set."));

		when(service.getAllItems()).thenReturn(todoList);
		try {
			mockMvc.perform(get("/todos/")
							.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$[0].taskName").value("Review"))
					.andExpect(jsonPath("$[1].taskName").value("Check up"));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	public  void getAllFinishedItemsTest() {
		LocalDate deadline1 = LocalDate.parse("2022-12-21");
		LocalDate deadline2 = LocalDate.parse("2023-01-15");
		List<ToDo> todoList = Arrays.asList(new ToDo("Review", deadline1, true, "Completed!"),
				new ToDo("Check up", deadline2, false,"Appointment set."));

		when(service.getAllFinishedItems()).thenReturn(todoList);
		try {
			mockMvc.perform(get("/todos/finished")
							.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public  void getAllUnfinishedItemsTest() {
		LocalDate deadline1 = LocalDate.parse("2022-12-21");
		LocalDate deadline2 = LocalDate.parse("2023-01-15");
		List<ToDo> todoList = Arrays.asList(new ToDo("Review", deadline1, true, "Completed!"),
				new ToDo("Check up", deadline2, false,"Appointment set."));

		when(service.getAllUnfinishedItems()).thenReturn(todoList);
		try {
			mockMvc.perform(get("/todos/unfinished")
							.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	public void getItemByIdTest() throws Exception {
		LocalDate deadline = LocalDate.parse("2022-12-21");
		ToDo todo = new ToDo("Review", deadline,true, "Completed!");
		when(service.getItemById(1L)).thenReturn(todo);
		mockMvc.perform(get("/todos/{id}","1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(todo))).andExpect(status().isOk());

		ToDo item = service.getItemById(1L);

		assertEquals("Review", item.getTaskName());
		assertEquals(deadline, item.getDeadline());
		assertEquals(true, item.getIsDone());
		assertEquals("Completed!", item.getTaskComment());
}

	@Test
	public void updateItemTest() throws Exception {
		long tdId = 1;
		LocalDate deadline = LocalDate.parse("2022-12-21");
		ToDo newItem = new ToDo("Review", deadline,true,"Completed!");
		newItem.setId(tdId);

		ToDo updatedItem = new ToDo("Review", deadline,false,"Cancelled!");
		updatedItem.setId(tdId);
		when(service.addItem(newItem)).thenReturn(newItem);
		mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(newItem))).andExpect(status().isOk());


		ToDo existingItem = service.addItem(newItem);
		existingItem.setTaskName(updatedItem.getTaskName());
		existingItem.setDeadline(updatedItem.getDeadline());
		existingItem.setId(tdId);

		assertNotNull(existingItem);

		when(service.updateItem(updatedItem, existingItem.getId())).thenReturn(existingItem);
		mockMvc.perform(put("/todos/{id}",tdId).contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(existingItem))).andExpect(status().isOk());

		ToDo updatedTodoFromDb = service.updateItem(updatedItem, existingItem.getId());

		assertEquals(updatedTodoFromDb, existingItem);
	}
	@Test
	public void deleteItemTest() throws Exception {
		LocalDate deadline = LocalDate.parse("2022-12-21");
		long todoId = 1;
		List<ToDo> toDo = new ArrayList<>();

		ToDo todo = new ToDo("Review", deadline, true,"Completed!");
		todo.setId(todoId);

		toDo.add(todo);

		assertTrue(toDo.contains(todo));

		when(service.deleteItem(todoId)).thenReturn("Task successfully deleted!");
		mockMvc.perform(delete("/todos/{id}","1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(todo))).andExpect(status().isOk());
	}
	@Test()
	void throwsInvalidTaskNameExceptionTest() throws Exception {
		LocalDate deadline = LocalDate.parse("2022-12-21");
		ToDo addTodoItem = new ToDo("", deadline, true, "Completed!");

		when(service.addItem(addTodoItem)).thenThrow(TodoException.class);
		mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(addTodoItem))).andExpect(status().isBadRequest()).andReturn();

	}}
