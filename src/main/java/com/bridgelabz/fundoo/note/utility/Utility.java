package com.bridgelabz.fundoo.note.utility;

import com.bridgelabz.fundoo.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoo.note.models.NoteCreateDTO;

/**
 * @author adminstrato
 *
 */
public class Utility {

	/**
	 * @param noteCreateDTO
	 * @throws NoteException
	 * @throws EmptyNoteException
	 */
	public static void validateNoteWhileCreating(NoteCreateDTO noteCreateDTO) throws EmptyNoteException {

		if ((noteCreateDTO.getTitle() == null || noteCreateDTO.getTitle().trim().isEmpty())
				&& (noteCreateDTO.getDescription() == null || noteCreateDTO.getDescription().trim().isEmpty())) {
			throw new EmptyNoteException("Both title and description fields should not be empty");
		}
	}
}
