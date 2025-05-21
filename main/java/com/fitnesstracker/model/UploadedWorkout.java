package com.fitnesstracker.model;

public class UploadedWorkout {
	private int id;
	private String name;
	private String type;
	private String duration;

	public UploadedWorkout(int id, String name, String type, String duration) {
	    this.id = id;
	    this.name = name;
	    this.type = type;
	    this.duration = duration;
	}

	public UploadedWorkout() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
}
