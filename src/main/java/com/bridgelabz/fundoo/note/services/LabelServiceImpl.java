package com.bridgelabz.fundoo.note.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.exceptions.LabelException;
import com.bridgelabz.fundoo.note.models.Label;
import com.bridgelabz.fundoo.note.models.LabelCreateDTO;
import com.bridgelabz.fundoo.note.models.Note;
import com.bridgelabz.fundoo.note.repositories.LabelRepository;
import com.bridgelabz.fundoo.note.repositories.LabelRepositoryES;
import com.bridgelabz.fundoo.note.repositories.NoteRepository;
import com.bridgelabz.fundoo.note.repositories.NoteRepositoryES;

@Service
public class LabelServiceImpl implements LabelService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NoteRepositoryES noteRepositoryES;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private LabelRepositoryES labelRepositoryES;


	@Override
	public void createLabel(String userId, LabelCreateDTO labelCreateDTO) throws LabelException {

		if (labelCreateDTO.getLabelName() == null) {
			throw new LabelException("for creating a label \"labelName\" should have given");
		}

		if (labelRepositoryES.findByUserIdAndLabelName(userId, labelCreateDTO.getLabelName()).isPresent()) {
			throw new LabelException("label name already present");
		}

		Label label = new Label();
		label.setUserId(userId);
		label.setLabelName(labelCreateDTO.getLabelName());

		labelRepository.insert(label);
		labelRepositoryES.save(label);

	}

	

	@Override
	public void editLabel(String userId, String currentLabelId, String newLabelName) throws LabelException {

		Optional<Label> labelOfUser = labelRepositoryES.findByLabelIdAndUserId(currentLabelId, userId);
		if (!labelOfUser.isPresent()) {
			throw new LabelException("given label not found");
		}

		if (labelRepositoryES.findByUserIdAndLabelName(userId, newLabelName).isPresent()) {
			throw new LabelException("the given new label name is already present");
		}

		Optional<List<Note>> listOfNotes = noteRepository.findAllByUserIdAndLabelId(userId, currentLabelId);

		if (listOfNotes.isPresent()) {
			for (Note note : listOfNotes.get()) {
				for (Label label : note.getLabelList()) {

					if (label.getLabelId().equals(currentLabelId)) {

						label.setLabelName(newLabelName);

						noteRepository.save(note);
						noteRepositoryES.save(note);
					}
				}
			}
		}

		labelOfUser.get().setLabelName(newLabelName);
		labelRepository.save(labelOfUser.get());
		labelRepositoryES.save(labelOfUser.get());
	}

	@Override
	public void deleteLabel(String userId, String labelId) throws LabelException {

		Optional<Label> labelOfUser = labelRepositoryES.findByLabelIdAndUserId(labelId, userId);
		if (!labelOfUser.isPresent()) {
			throw new LabelException("given label not found");
		}

		Optional<List<Note>> listOfNotes = noteRepository.findAllByUserIdAndLabelId(userId, labelId);

		if (listOfNotes.isPresent()) {
			for (Note note : listOfNotes.get()) {
				for (Label label : note.getLabelList()) {

					if (label.getLabelId().equals(labelId)) {

						note.getLabelList().remove(label);

						noteRepository.save(note);
						noteRepositoryES.save(note);
					}
				}
			}
		}
		labelRepository.delete(labelOfUser.get());
		labelRepositoryES.delete(labelOfUser.get());

	}

	@Override
	public List<Label> getAllLabel(String userId) throws LabelException {

		Optional<List<Label>> labelsOfUser = labelRepositoryES.findAllByUserId(userId);

		return labelsOfUser.get();
	}

}
