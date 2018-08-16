package com.bridgelabz.fundoo.note.exceptions;

public class NoteNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public NoteNotFoundException(String message) {
		super(message);
	}

}
