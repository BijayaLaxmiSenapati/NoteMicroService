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
import com.bridgelabz.fundoo.note.models.LabelCreateDTO;
import com.bridgelabz.fundoo.note.models.Note;
import com.bridgelabz.fundoo.note.models.NoteCreateDTO;
import com.bridgelabz.fundoo.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoo.note.models.NoteViewDTO;
import com.bridgelabz.fundoo.note.models.ReminderDTO;

public interface NoteService {

	/**
	 * @param token
	 * @param noteCreateDTO
	 * @return
	 * @throws NoteException
	 * @throws EmptyNoteException
	 * @throws InvalidDateException
	 * @throws GetURLInfoException
	 */
	NoteViewDTO createNote(String token, NoteCreateDTO noteCreateDTO)
			throws EmptyNoteException, InvalidDateException, GetURLInfoException;

	/**
	 * @param token
	 * @param noteCreateDTO
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 * @throws GetURLInfoException 
	 */
	void updateNote(String token, NoteUpdateDTO noteCreateDTO) throws NoteNotFoundException, NoteAuthorisationException, GetURLInfoException;

	/**
	 * @param token
	 * @return
	 */
	List<NoteViewDTO> getAllNotes(String token);

	/**
	 * @param token
	 * @param noteId
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	void trashNote(String token, String noteId, boolean trashOrRestore)
			throws NoteNotFoundException, NoteAuthorisationException;

	/**
	 * @param token
	 * @param id
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 * @throws NoteTrashException
	 */
	void permanentlyDeleteNote(String token, String id)
			throws NoteNotFoundException, NoteAuthorisationException, NoteTrashException;

	/**
	 * @param token
	 * @param id
	 * @param remindDate
	 * @throws ParseException
	 * @throws InvalidDateException
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	void addReminder(String token, String id, ReminderDTO reminderDTO)
			throws InvalidDateException, NoteNotFoundException, NoteAuthorisationException;

	void removeReminder(String token, String id) throws NoteNotFoundException, NoteAuthorisationException;

	void addPin(String token, String id, boolean pinOrUnpin)
			throws NoteNotFoundException, NoteAuthorisationException;

	void addToArchive(String token, String id, boolean archiveOrUnarchive)
			throws NoteNotFoundException, NoteAuthorisationException;

	void createLabel(String token, LabelCreateDTO labelCreateDTO) throws LabelException;

	void addLabel(String token, String noteId, LabelAddDTO labelAddDTO)
			throws NoteNotFoundException, LabelException, LabelNotFoundException;

	void editLabel(String token, String currentLabelName, String newLabelName) throws LabelException;

	void deleteLabel(String token, String labelName) throws LabelException;

	List<Note> notesByLabelId(String token, String labelName) throws LabelException;

	List<Label> getAllLabel(String token) throws LabelException;

	List<Note> getAllTrashedNote(String token);

	List<Note> getAllArchivedNote(String token);

	void emptyTrash(String token);

	void removeLabelFromNote(String token, String noteId, String labelName)
			throws NoteNotFoundException, LabelException;

	void addColor(String token, String noteId, ColorDTO colorDTO) throws NoteNotFoundException;

}
