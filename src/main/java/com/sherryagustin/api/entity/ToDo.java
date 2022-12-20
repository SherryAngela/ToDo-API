package com.sherryagustin.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;



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
	@DateTimeFormat( pattern = "mm/dd/yyyy")
	private Date deadline;

//	@Column(name = "task-status")
//	private Boolean isDone;

	public ToDo(String taskName, Date deadline) {
		this.taskName = taskName;
		this.deadline = deadline;
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

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

//	public Boolean getDone() {
//		return isDone;
//	}
//
//	public void setDone(Boolean done) {
//		isDone = done;
//	}
}

//https://stackoverflow.com/a/43295074 (look at the comment here)
//https://stackoverflow.com/a/42620096 (lombok boolean naming issue)