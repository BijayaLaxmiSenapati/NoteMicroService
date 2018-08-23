package com.bridgelabz.fundoo.note.services;

import java.util.List;

import com.bridgelabz.fundoo.note.exceptions.LabelException;
import com.bridgelabz.fundoo.note.models.Label;
import com.bridgelabz.fundoo.note.models.LabelCreateDTO;

public interface LabelService {

	public void createLabel(String userId, LabelCreateDTO labelCreateDTO) throws LabelException;

	public void editLabel(String userId, String currentLabelId, String newLabelName) throws LabelException;

	public void deleteLabel(String userId, String labelId) throws LabelException;

	public List<Label> getAllLabel(String userId) throws LabelException;
}
