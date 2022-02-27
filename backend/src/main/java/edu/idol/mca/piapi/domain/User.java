package edu.idol.mca.piapi.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * This User domain is used as data transfer object between layers.
 */

@Entity
@Table(name= "Users")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Please Enter Name")
	private String name;
	
	@NotBlank(message = "Please Enter Login Name")
	@Column(unique = true, updatable = false)
	private String loginName;
	
	@NotBlank(message = "Please Enter Password")
	@Size(min=8, max=20, message = "Please enter password of size minimum 8 and maximum 20")
	private String pwd;
	
	@NotBlank(message = "Please Select User Type")
	private String userType;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user")
	private List<Task> tasks= new ArrayList<>();

	public User() {
		super();
	}

	/**
	 * @param name
	 * @param loginName
	 * @param pwd
	 * @param userType
	 * @param tasks
	 */
	public User(String name, String loginName, String pwd, String userType, List<Task> tasks) {
		super();
		this.name = name;
		this.loginName = loginName;
		this.pwd = pwd;
		this.userType = userType;
		this.tasks = tasks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
	
}
