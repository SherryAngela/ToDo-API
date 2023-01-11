package com.sherryagustin.api.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
			throw new TodoException(TodoException.INVALID_TASKNAME);}
		if (todo.getTaskName().length() <= 2) {
			throw new TodoException(TodoException.SHORT_TASKNAME);
		}
		if (todo.getTaskName().length() > 30) {
			throw new TodoException(TodoException.LONG_TASKNAME);
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
	}public List<ToDo> getAllUnfinishedItems() {
		return todoRepository.findAllByIsDoneFalse();
	}




	public ToDo updateItem(ToDo todo, long id) throws Exception {
		ToDo todoFromDb = todoRepository.findById(id)
				.orElseThrow(() -> new Exception("Unable to update item. Something went wrong."));

		todoFromDb.setTaskName(todo.getTaskName());
		todoFromDb.setDeadline(todo.getDeadline());

		todoRepository.save(todoFromDb);

		if (todoFromDb.getTaskName().isEmpty()) {
			throw new TodoException(TodoException.INVALID_TASKNAME);}
		if (todoFromDb.getTaskName().length() <= 2) {
			throw new TodoException(TodoException.SHORT_TASKNAME);
		}
		if (todoFromDb.getTaskName().length() > 30) {
			throw new TodoException(TodoException.LONG_TASKNAME);

		}
		return todoFromDb;
	}

	public String deleteItem(long id) throws Exception {
		todoRepository.findById(id).orElseThrow(() -> new Exception("Unable to delete item. Something went wrong."));

		todoRepository.deleteById(id);
		return "Task successfully deleted!";

	}
	private void doneTask(Long id){
		Optional<ToDo> t = todoRepository.findById(id);
		t.ifPresent(todo -> {
			todo.setIsDone(!todo.getIsDone());
			todoRepository.save(t.get());
		});
	}

	private String checkItemCategory(ToDo todo){
		if(todo.getIsDone().equals(true))
			return "redirect:/todos/finished";
		else return "redirect:/todos/unfinished";
	}

//		if (todo.getIsDone(boolean).equals(true))
//			return "redirect:/todos/";
//		else if (todo.equals(ToDo.FINISHED))
//			return "redirect:/todos/finished";
//		else
//			return "redirect:/todos/unfinished";
//	}
}