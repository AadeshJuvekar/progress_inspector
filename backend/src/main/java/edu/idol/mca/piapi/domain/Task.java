package edu.idol.mca.piapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message= "Task title is required")
	private String title;
	
	@NotBlank(message = "Task Identifier is required")
	@Column(unique=true, updatable=false)
	@Size(min=2, max=4, message = "Please enter valid task indentifier size(min=2 and max=4)")
	private String taskIdentifier;
	
	@NotBlank(message= "Task description is required")
	private String description;
	
	private String progress;
		
	
	@JsonIgnore
	@ManyToMany(mappedBy = "assignedTasks")
	private Set<User> users= new HashSet<>();
	
	//@JsonIgnore
	@OneToMany(mappedBy = "task",cascade  = CascadeType.ALL, orphanRemoval = false)
	private Set<Remark> remarks= new HashSet<>();

	
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Kolkata")
	private Date createdAt;
	
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Kolkata")
	private Date updatedAt;
	
	public Task() {
		super();
	}	
	
	public Task(long id, String title,
			String taskIdentifier,
			String description, String progress, Set<User> users) {
		super();
		this.id = id;
		this.title = title;
		this.taskIdentifier = taskIdentifier;
		this.description = description;
		this.progress = progress;
		this.users = users;
	}

	public Task(String title, String taskIdentifer, String description, String progress) {
		super();
		this.title = title;
		this.taskIdentifier = taskIdentifer;
		this.description = description;
		this.progress = progress;
	}

	/**
	 * @param title
	 * @param taskIdentifer
	 * @param description
	 * @param progress
	 * @param user
	 * @param remarks
	 */
	public Task(long id, String title,
			String taskIdentifier,
			String description, String progress, Set<User> users,
			Set<Remark> remarks) {
		super();
		this.title = title;
		this.taskIdentifier = taskIdentifier;
		this.description = description;
		this.progress = progress;
		this.users = users;
		this.remarks = remarks;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt=new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt=new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getTaskIdentifier() {
		return taskIdentifier;
	}

	public void setTaskIdentifier(String taskIdentifier) {
		this.taskIdentifier = taskIdentifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<Remark> getRemarks() {
		return remarks;
	}

	public void setRemarks(Set<Remark> remarks) {
		this.remarks = remarks;
	}
	public void addUser(User user) {
		this.users.add(user);
		user.getAssignedTasks().add(this);
	}
	public void removeUser(User user) {
		this.users.remove(user);
		user.getAssignedTasks().remove(this);
	}
	public void addRemark(Remark remark) {
		this.remarks.add(remark);
		remark.setTask(this);
	}
	public void removeRemark(Remark remark) {
		this.remarks.remove(remark);
		remark.setTask(null);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", taskIdentifier=" + taskIdentifier + ", description="
				+ description + ", progress=" + progress + ", users=" + users + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", remarks=" + remarks + "]";
	}

	
	
	
}
