package com.sherryagustin.api.controller;

import java.util.List;

import com.sherryagustin.api.entity.ToDo;
import com.sherryagustin.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
	@Autowired
	TodoService todoService;

	@PostMapping("/todos")
	public ResponseEntity<ToDo> addItem(@Validated @RequestBody ToDo todo) throws Exception {
		return ResponseEntity.ok(todoService.addItem(todo));
	}

	@GetMapping("/todos")
	public ResponseEntity<List<ToDo>> getItemList() {
		return ResponseEntity.ok(todoService.getAllItems());
	}
	@GetMapping("/todos/{id}")
	public ResponseEntity<ToDo> getItemById(@PathVariable Long id) {
		return ResponseEntity.ok(todoService.getItemById(id));
	}
	@PutMapping("/todos/{id}")
	public ToDo updateItem(@PathVariable long id, @RequestBody ToDo todo) throws Exception {
		return todoService.updateItem(todo, id);
	}

	@DeleteMapping("todos/{id}")
	public boolean deleteItem(@PathVariable long id) throws Exception{
		return todoService.deleteItem(id);
	}

}
