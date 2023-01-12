package com.sherryagustin.api.entity;

import com.sherryagustin.api.exception.TodoException;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "todo")
public class ToDo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "todo-id")
	private long id;

	@Column(name = "task-name")
	@NotNull
	private String taskName;

	@Column(name = "task-deadline")
	@NotNull
	private LocalDate deadline;

	@Column(name = "task-status")
	private Boolean isDone;

	@Column(name = "task-comment")
	private String taskComment;


	public ToDo(String taskName, LocalDate deadline, Boolean isDone, String taskComment) {
		this.taskName = taskName;
		this.deadline = deadline;
		this.isDone = isDone;
		this.taskComment = taskComment;
	}

	public long getId(ToDo item) {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public ToDo setTaskName(@RequestBody String taskName) throws TodoException {
		this.taskName = taskName;
		return null;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	private Boolean getDone() {
		return isDone;
	}

	private void setDone(Boolean done) {
		isDone = done;
	}

	public String getTaskComment() {
		return taskComment;
	}

	public ToDo setTaskComment(String taskComment) {
		this.taskComment = taskComment;
		return null;
	}




}
