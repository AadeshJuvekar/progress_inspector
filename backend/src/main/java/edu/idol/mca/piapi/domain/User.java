package edu.idol.mca.piapi.domain;

import java.util.ArrayList;

/**
 * This User domain is used as data transfer object between layers.
 */
public class User {

	private long id;
	private String name;
	private String loginName;
	private String pwd;
	private String userType;
	private Task task;
	private ArrayList<Task> tasks;
	
}
