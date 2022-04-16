package edu.idol.mca.piapi.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This Remark Domain is used as data transfer object between layers
 *
 */
@Entity
public class Remark {	
	/**
	 * Primary Key for Remark entity
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Remark Identifier given as a remark. It cannot be blank
	 */
	@NotBlank(message = "Remark Identifier is required")
	@Column(unique=true, updatable=false)
	@Size(min=2, max=4, message = "Please enter valid task indentifier size(min=2 and max=4)")
	private String remarkIdentifier;
	/**
	 * Description given as a remark. It cannot be blank
	 */
	@NotBlank(message = "Description is Required")
	private String description;
	/**
	 * Name of the user who added the remark. It cannot be blank
	 */
	@NotBlank(message = "Name is Required")
	private String givenBy;
	
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Asia/Kolkata")
	private Date createdAt;
	
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Asia/Kolkata")
	private Date updatedAt;
	
	/**
	 * Many remarks are assigned to the task
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "task_id", updatable= false, nullable= false)	
	private Task task;

	public Remark() {
		super();
	}

	/**
	 * @param description
	 * @param givenBy
	 * @param task
	 */
	public Remark(String description, String remarkIdentifier,String givenBy, Task task) {
		super();
		this.remarkIdentifier = remarkIdentifier;
		this.description = description;
		this.givenBy = givenBy;
		this.task = task;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getRemarkIdentifier() {
		return remarkIdentifier;
	}

	public void setRemarkIdentifier(String remarkIdentifier) {
		this.remarkIdentifier = remarkIdentifier;
	}

	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getGivenBy() {
		return givenBy;
	}



	public void setGivenBy(String givenBy) {
		this.givenBy = givenBy;
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



	public Task getTask() {
		return task;
	}



	public void setTask(Task task) {
		this.task = task;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt=new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt=new Date();
	}
}
