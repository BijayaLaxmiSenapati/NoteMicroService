package com.bridgelabz.fundoo.note.exceptionhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoo.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoo.note.exceptions.GetURLInfoException;
import com.bridgelabz.fundoo.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoo.note.exceptions.LabelException;
import com.bridgelabz.fundoo.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoo.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoo.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoo.note.exceptions.NoteTrashException;
import com.bridgelabz.fundoo.note.models.ResponseDTO;

@ControllerAdvice
public class NoteExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteExceptionHandler.class);
	
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(EmptyNoteException.class)
	public ResponseEntity<ResponseDTO> handleEmptyNoteException(EmptyNoteException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-20);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoteAuthorisationException.class)
	public ResponseEntity<ResponseDTO> handleNoteAuthorisationException(NoteAuthorisationException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-30);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<ResponseDTO> handleNoteNotFoundException(NoteNotFoundException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-40);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ResponseDTO> handleInvalidDateException(InvalidDateException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-60);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoteTrashException.class)
	public ResponseEntity<ResponseDTO> handleNoteTrashException(NoteTrashException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-70);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LabelException.class)
	public ResponseEntity<ResponseDTO> handleLabelException(LabelException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-100);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LabelNotFoundException.class)
	public ResponseEntity<ResponseDTO> handleLabelNotFoundException(LabelNotFoundException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-110);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(GetURLInfoException.class)
	public ResponseEntity<ResponseDTO> handleGetURLInfoException(GetURLInfoException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-120);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

}
