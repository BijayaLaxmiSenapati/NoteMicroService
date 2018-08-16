package com.bridgelabz.fundoo.note.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:message.properties")
@ConfigurationProperties(prefix = "message.controller")
@Component
public class MessagePropertyConfig {

	private int successfulStatus;

	private String updateNoteMsg;

	private String trashNoteMsg;

	private String permanentlyDeleteNoteMsgDLT;

	private String restoreNoteMsg;

	private String addReminderMsg;

	private String removeReminderMsg;

	private String addPinMsg;

	private String removePinMsg;

	private String addToArchiveMsg;

	private String removeFromArchive;

	private String createLabelMsg;

	private String addLabelMsg;

	private String editLabelMsg;

	private String deleteLabelMsg;

	private String emptyTrashMsg;
	
	private String removeLabelFromNoteMsg;

	public String getUpdateNoteMsg() {
		return updateNoteMsg;
	}

	public void setUpdateNoteMsg(String updateNoteMsg) {
		this.updateNoteMsg = updateNoteMsg;
	}

	public int getSuccessfulStatus() {
		return successfulStatus;
	}

	public void setSuccessfulStatus(int successfulStatus) {
		this.successfulStatus = successfulStatus;
	}

	public String getTrashNoteMsg() {
		return trashNoteMsg;
	}

	public void setTrashNoteMsg(String trashNoteMsg) {
		this.trashNoteMsg = trashNoteMsg;
	}

	public String getPermanentlyDeleteNoteMsgDLT() {
		return permanentlyDeleteNoteMsgDLT;
	}

	public void setPermanentlyDeleteNoteMsgDLT(String permanentlyDeleteNoteMsgDLT) {
		this.permanentlyDeleteNoteMsgDLT = permanentlyDeleteNoteMsgDLT;
	}

	public String getAddReminderMsg() {
		return addReminderMsg;
	}

	public void setAddReminderMsg(String addReminderMsg) {
		this.addReminderMsg = addReminderMsg;
	}

	public String getRemoveReminderMsg() {
		return removeReminderMsg;
	}

	public void setRemoveReminderMsg(String removeReminderMsg) {
		this.removeReminderMsg = removeReminderMsg;
	}

	public String getAddPinMsg() {
		return addPinMsg;
	}

	public void setAddPinMsg(String addPinMsg) {
		this.addPinMsg = addPinMsg;
	}

	public String getRemovePinMsg() {
		return removePinMsg;
	}

	public void setRemovePinMsg(String removePinMsg) {
		this.removePinMsg = removePinMsg;
	}

	public String getAddToArchiveMsg() {
		return addToArchiveMsg;
	}

	public void setAddToArchiveMsg(String addToArchiveMsg) {
		this.addToArchiveMsg = addToArchiveMsg;
	}

	public String getRemoveFromArchive() {
		return removeFromArchive;
	}

	public void setRemoveFromArchive(String removeFromArchive) {
		this.removeFromArchive = removeFromArchive;
	}

	public String getCreateLabelMsg() {
		return createLabelMsg;
	}

	public void setCreateLabelMsg(String createLabelMsg) {
		this.createLabelMsg = createLabelMsg;
	}

	public String getAddLabelMsg() {
		return addLabelMsg;
	}

	public void setAddLabelMsg(String addLabelMsg) {
		this.addLabelMsg = addLabelMsg;
	}

	public String getEditLabelMsg() {
		return editLabelMsg;
	}

	public void setEditLabelMsg(String editLabelMsg) {
		this.editLabelMsg = editLabelMsg;
	}

	public String getDeleteLabelMsg() {
		return deleteLabelMsg;
	}

	public void setDeleteLabelMsg(String deleteLabelMsg) {
		this.deleteLabelMsg = deleteLabelMsg;
	}

	public String getEmptyTrashMsg() {
		return emptyTrashMsg;
	}

	public void setEmptyTrashMsg(String emptyTrashMsg) {
		this.emptyTrashMsg = emptyTrashMsg;
	}

	public String getRemoveLabelFromNoteMsg() {
		return removeLabelFromNoteMsg;
	}

	public void setRemoveLabelFromNoteMsg(String removeLabelFromNoteMsg) {
		this.removeLabelFromNoteMsg = removeLabelFromNoteMsg;
	}

	public String getRestoreNoteMsg() {
		return restoreNoteMsg;
	}

	public void setRestoreNoteMsg(String restoreNoteMsg) {
		this.restoreNoteMsg = restoreNoteMsg;
	}
}
