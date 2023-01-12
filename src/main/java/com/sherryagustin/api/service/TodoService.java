package com.sherryagustin.api.service;

import java.util.List;
import java.util.regex.Pattern;

import com.sherryagustin.api.entity.ToDo;
import com.sherryagustin.api.exception.TodoException;
import com.sherryagustin.api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;

	public ToDo addItem(ToDo todo) throws TodoException {

		if (todo.getTaskName().isEmpty()) {
			throw new TodoException(TodoException.INVALID_TASKNAME);
		}
		if (todo.getTaskName().length() <= 3) {
			throw new TodoException(TodoException.SHORT_TASKNAME);
		}
		if (todo.getTaskName().length() > 30) {
			throw new TodoException(TodoException.LONG_TASKNAME);
		}
		if (todo.getTaskComment().length() > 30){
			throw new TodoException(TodoException.LONG_COMMENT);
		}
		if (todo.getTaskComment().length() <= 3) {
			throw new TodoException(TodoException.SHORT_COMMENT);
		}
		if (todo.getTaskComment().isEmpty()){
			throw new TodoException(TodoException.INVALID_COMMENT);
		}
		if (Pattern.matches("[\\s]*", todo.getTaskName())) {
			throw new TodoException(TodoException.INVALID_TASKNAME);
		}
		if (Pattern.matches("[\\s]*", todo.getTaskComment())) {
			throw new TodoException(TodoException.INVALID_COMMENT);
		}


		return todoRepository.save(todo);
	}

	public List<ToDo> getAllItems() {
		return todoRepository.findAll();
	}

	public ToDo getItemById(Long id) {
		return todoRepository.findById(id).get();
	}

	public List<ToDo> getAllFinishedItems() {

		return todoRepository.findAllByIsDoneTrue();
	}

	public List<ToDo> getAllUnfinishedItems() {
		return todoRepository.findAllByIsDoneFalse();
	}


	public ToDo updateItem(ToDo todo, long id) throws Exception {
		ToDo todoFromDb = todoRepository.findById(id)
				.orElseThrow(() ->
						new Exception("Unable to update item. Something went wrong."));

		todoFromDb.setTaskName(todo.getTaskName());
		todoFromDb.setDeadline(todo.getDeadline());

		todoRepository.save(todoFromDb);

		if (todoFromDb.getTaskComment().length() > 30){
			throw new TodoException(TodoException.LONG_COMMENT);
		}
		if (todoFromDb.getTaskComment().isEmpty()){
			throw new TodoException(TodoException.INVALID_COMMENT);
		}
		if (Pattern.matches("[\\s]*", todoFromDb.getTaskComment())) {
			throw new TodoException(TodoException.INVALID_COMMENT);
		}
		if (Pattern.matches("[\\s]*", todoFromDb.getTaskName())) {
			throw new TodoException(TodoException.INVALID_TASKNAME);
		}
		return todoFromDb;
	}

	public String deleteItem(long id) throws Exception {
		todoRepository.findById(id).orElseThrow(() ->
				new Exception("Unable to delete item. Something went wrong."));

		todoRepository.deleteById(id);
		return "Task successfully deleted!";

	}
}