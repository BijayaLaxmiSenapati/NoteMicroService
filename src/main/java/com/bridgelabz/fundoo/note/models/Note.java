package com.bridgelabz.fundoo.note.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "fundoonotes", type = "notes")
// @Document(collection = "Notes")
public class Note {

	private String userId;

	@Id
	private String id;

	private String title;

	private String description;

	private Date createdAt;

	private Date updatedAt;

	private String color;

	private Date reminder;

	private boolean trashed;

	private boolean pinned;

	private boolean archived;

	private List<Label> labelList;
	
	private List<URLMetaData> listOfURLMetaData;

	public Note() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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
		return trashed;
	}

	public void setTrashed(boolean trashed) {
		this.trashed = trashed;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
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

	@Override
	public String toString() {
		return "Note [userId=" + userId + ", id=" + id + ", title=" + title + ", description=" + description
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", color=" + color + ", reminder="
				+ reminder + ", trashed=" + trashed + ", pinned=" + pinned + ", archived=" + archived + ", labelList="
				+ labelList + ", listOfURLMetaData=" + listOfURLMetaData + "]";
	}
}
