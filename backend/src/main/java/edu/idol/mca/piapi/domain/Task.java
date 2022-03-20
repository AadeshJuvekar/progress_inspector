package edu.idol.mca.piapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "user_id", updatable = false, nullable = false)
	private User user;
	
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Kolkata")
	private Date createdAt;
	
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Kolkata")
	private Date updatedAt;
	
	
	@OneToMany
	private List<Remark> remarks=new ArrayList<>();
	
	public Task() {
		super();
	}

	public Task(String title, String taskIdentifer, String description, String progress, User user) {
		super();
		this.title = title;
		this.taskIdentifier = taskIdentifer;
		this.description = description;
		this.progress = progress;
		this.user = user;
	}

	/**
	 * @param title
	 * @param taskIdentifer
	 * @param description
	 * @param progress
	 * @param user
	 * @param remarks
	 */
	public Task(String title, String taskIdentifer, String description, String progress, User user,
			List<Remark> remarks) {
		super();
		this.title = title;
		this.taskIdentifier = taskIdentifer;
		this.description = description;
		this.progress = progress;
		this.user = user;
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

	public String getTaskIdentifer() {
		return taskIdentifier;
	}

	public void setTaskIdentifer(String taskIdentifer) {
		this.taskIdentifier = taskIdentifer;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<Remark> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<Remark> remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", taskIdentifer=" + taskIdentifier + ", description="
				+ description + ", progress=" + progress + ", user=" + user + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", remarks=" + remarks + "]";
	}

    public Object getTaskIdentifier() {
        return null;
    }
	
	
}
