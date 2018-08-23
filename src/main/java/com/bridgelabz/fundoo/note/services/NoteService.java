package com.bridgelabz.fundoo.note.services;

import java.util.List;

import com.bridgelabz.fundoo.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoo.note.exceptions.GetURLInfoException;
import com.bridgelabz.fundoo.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoo.note.exceptions.LabelException;
import com.bridgelabz.fundoo.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoo.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoo.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoo.note.exceptions.NoteTrashException;
import com.bridgelabz.fundoo.note.models.ColorDTO;
import com.bridgelabz.fundoo.note.models.Label;
import com.bridgelabz.fundoo.note.models.LabelAddDTO;
import com.bridgelabz.fundoo.note.models.Note;
import com.bridgelabz.fundoo.note.models.NoteCreateDTO;
import com.bridgelabz.fundoo.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoo.note.models.NoteViewDTO;
import com.bridgelabz.fundoo.note.models.ReminderDTO;

public interface NoteService {

	NoteViewDTO createNote(String token, NoteCreateDTO noteCreateDTO)
			throws EmptyNoteException, InvalidDateException, GetURLInfoException;

	void updateNote(String token, NoteUpdateDTO noteCreateDTO) throws NoteNotFoundException, NoteAuthorisationException, GetURLInfoException;

	void trashNote(String token, String noteId, boolean trashOrRestore)
			throws NoteNotFoundException, NoteAuthorisationException;

	void permanentlyDeleteNote(String token, String id)
			throws NoteNotFoundException, NoteAuthorisationException, NoteTrashException;

	void addReminder(String token, String id, ReminderDTO reminderDTO)
			throws InvalidDateException, NoteNotFoundException, NoteAuthorisationException;

	void removeReminder(String token, String id) throws NoteNotFoundException, NoteAuthorisationException;

	void addPin(String token, String id, boolean pinOrUnpin)
			throws NoteNotFoundException, NoteAuthorisationException;

	void addToArchive(String token, String id, boolean archiveOrUnarchive)
			throws NoteNotFoundException, NoteAuthorisationException;

	List<Note> notesByLabelId(String token, String labelName) throws LabelException;

	List<NoteViewDTO> getAllTrashedNote(String token);

	List<NoteViewDTO> getAllArchivedNote(String token);

	void emptyTrash(String token);

	void removeLabelFromNote(String token, String noteId, String labelName)
			throws NoteNotFoundException, LabelException;

	void addColor(String token, String noteId, ColorDTO colorDTO) throws NoteNotFoundException;

	List<Label> sortLabelByName(String header, Boolean ascendingOrDescending);

	List<Label> sortLabelByDate(String header, Boolean ascendingOrDescending);

	List<NoteViewDTO> getAllNotes(String userId, String sortBy, String sortOrder);
	void addLabel(String userId, String noteId, LabelAddDTO labelAddDTO)
			throws NoteNotFoundException, LabelException, LabelNotFoundException ;

}
