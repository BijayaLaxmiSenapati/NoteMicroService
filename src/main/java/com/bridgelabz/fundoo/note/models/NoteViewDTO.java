package com.bridgelabz.fundoo.note.models;

import java.util.Date;
import java.util.List;

public class NoteViewDTO {

	private String id;

	private String title;

	private String description;

	private Date createdAt;

	private Date updatedAt;

	private String color;

	private Date reminder;

	private boolean isTrashed;

	private boolean isPinned;
	
	private boolean isArchived;
	
	private List<Label> labelList;
	
	private List<URLMetaData> listOfURLMetaData;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}

	public boolean isTrashed() {
		return isTrashed;
	}

	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}

	public List<URLMetaData> getListOfURLMetaData() {
		return listOfURLMetaData;
	}

	public void setListOfURLMetaData(List<URLMetaData> listOfURLMetaData) {
		this.listOfURLMetaData = listOfURLMetaData;
	}

}
