package com.bridgelabz.fundoo.note.controllers;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.configurations.MessagePropertyConfig;
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
import com.bridgelabz.fundoo.note.models.ResponseDTO;
import com.bridgelabz.fundoo.note.services.NoteService;

@RestController
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	MessagePropertyConfig messagePropertyConfig;

	@Autowired
	private NoteService noteService;

	/**
	 * @param token
	 * @param noteDTO
	 * @param response
	 * @return
	 * @throws NoteException
	 * @throws EmptyNoteException
	 * @throws InvalidDateException
	 * @throws GetURLInfoException
	 */
	@PostMapping(value = "/create-note")
	public ResponseEntity<NoteViewDTO> createNote(HttpServletRequest request,
			@RequestBody NoteCreateDTO noteCreateDTO, HttpServletResponse response)
			throws EmptyNoteException, InvalidDateException, GetURLInfoException {

		NoteViewDTO noteViewDTO = noteService.createNote(request.getHeader("userId"), noteCreateDTO);
		return new ResponseEntity<>(noteViewDTO, HttpStatus.CREATED);
	}

	/**
	 * @param token
	 * @param noteCreateDTO
	 * @return
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 * @throws GetURLInfoException 
	 */
	@PutMapping(value = "/update-note")
	public ResponseEntity<ResponseDTO> updateNote(HttpServletRequest request,
			@RequestBody NoteUpdateDTO noteUpdateDTO) throws NoteNotFoundException, NoteAuthorisationException, GetURLInfoException {
		noteService.updateNote(request.getHeader("userId"), noteUpdateDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getUpdateNoteMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @return
	 */
	@GetMapping(value = "/get-all-notes")
	public ResponseEntity<List<NoteViewDTO>> getAllNotes(HttpServletRequest request) {

		List<NoteViewDTO> list = noteService.getAllNotes(request.getHeader("userId"));

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	@PutMapping(value = "/trash/{id}/{trashOrRestore}")
	public ResponseEntity<ResponseDTO> trashNote(HttpServletRequest request,
			@PathVariable(value = "id") String id, @PathVariable(value = "trashOrRestore") boolean trashOrRestore)
			throws NoteNotFoundException, NoteAuthorisationException {
		noteService.trashNote(request.getHeader("userId"), id, trashOrRestore);

		ResponseDTO responseDTO = new ResponseDTO();
		if (trashOrRestore) {

			responseDTO.setMessage(messagePropertyConfig.getTrashNoteMsg());

		} else {

			responseDTO.setMessage(messagePropertyConfig.getRestoreNoteMsg());
		}
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 * @throws NoteTrashException
	 */
	@DeleteMapping(value = "/permanently-delete-note/{id}")
	public ResponseEntity<ResponseDTO> permanentlyDeleteNote(HttpServletRequest request,
			@PathVariable(value = "id") String id)
			throws NoteNotFoundException, NoteAuthorisationException, NoteTrashException {
		noteService.permanentlyDeleteNote(request.getHeader("userId"), id);

		ResponseDTO responseDTO = new ResponseDTO();

		responseDTO.setMessage(messagePropertyConfig.getPermanentlyDeleteNoteMsgDLT());// giving null
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @param remindDate
	 * @return
	 * @throws ParseException
	 * @throws InvalidDateException
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	@PutMapping(value = "/add-reminder/{id}")
	public ResponseEntity<ResponseDTO> addReminder(HttpServletRequest request,
			@PathVariable(value = "id") String id, @RequestBody ReminderDTO reminderDTO)
			throws InvalidDateException, NoteNotFoundException, NoteAuthorisationException {

		noteService.addReminder(request.getHeader("userId"), id, reminderDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getAddReminderMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return
	 * @throws ParseException
	 * @throws InvalidDateException
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	@PutMapping(value = "/remove-reminder/{noteId}")
	public ResponseEntity<ResponseDTO> removeReminder(HttpServletRequest request,
			@PathVariable(value = "noteId") String noteId)
			throws ParseException, InvalidDateException, NoteNotFoundException, NoteAuthorisationException {

		noteService.removeReminder(request.getHeader("userId"), noteId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getRemoveReminderMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/add-color/{noteId}")
	public ResponseEntity<ResponseDTO> addColor(HttpServletRequest request,
			@PathVariable(value = "noteId") String noteId, @RequestBody ColorDTO colorDTO)
			throws NoteNotFoundException {

		noteService.addColor(request.getHeader("userId"), noteId, colorDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully color is added");// add to properties
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/pin/{id}/{pinOrUnpin}")
	public ResponseEntity<ResponseDTO> addPin(HttpServletRequest request,
			@PathVariable(value = "id") String id, @PathVariable(value = "pinOrUnpin") boolean pinOrUnpin)
			throws NoteNotFoundException, NoteAuthorisationException {

		noteService.addPin(request.getHeader("userId"), id, pinOrUnpin);

		ResponseDTO responseDTO = new ResponseDTO();
		if (pinOrUnpin) {
			responseDTO.setMessage(messagePropertyConfig.getAddPinMsg());
		} else {
			responseDTO.setMessage(messagePropertyConfig.getRemovePinMsg());
		}
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/archive/{id}/{archiveOrUnarchive}")
	public ResponseEntity<ResponseDTO> addToArchieve(HttpServletRequest request,
			@PathVariable(value = "id") String id,
			@PathVariable(value = "archiveOrUnarchive") boolean archiveOrUnarchive)
			throws NoteNotFoundException, NoteAuthorisationException {

		noteService.addToArchive(request.getHeader("userId"), id, archiveOrUnarchive);

		ResponseDTO responseDTO = new ResponseDTO();
		if (archiveOrUnarchive) {
			responseDTO.setMessage(messagePropertyConfig.getAddToArchiveMsg());
		} else {
			responseDTO.setMessage(messagePropertyConfig.getRemoveFromArchive());
		}
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/create-label")
	public ResponseEntity<ResponseDTO> createLabel(HttpServletRequest request,
			@RequestBody LabelCreateDTO labelCreateDTO) throws LabelException {

		noteService.createLabel(request.getHeader("userId"), labelCreateDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getCreateLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/add-label/{noteId}")
	public ResponseEntity<ResponseDTO> addLabel(HttpServletRequest request,
			@PathVariable(value = "noteId") String noteId, @RequestBody LabelAddDTO labelAddDTO)
			throws NoteNotFoundException, LabelException, LabelNotFoundException {

		noteService.addLabel(request.getHeader("userId"), noteId, labelAddDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getAddLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/edit-label")
	public ResponseEntity<ResponseDTO> editLabel(HttpServletRequest request,
			@RequestParam String currentLabelId, @RequestParam String newLabelName) throws LabelException {

		noteService.editLabel(request.getHeader("userId"), currentLabelId, newLabelName);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getEditLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete-label")
	public ResponseEntity<ResponseDTO> deleteLabel(HttpServletRequest request,
			@RequestParam String labelId) throws LabelException {

		noteService.deleteLabel(request.getHeader("userId"), labelId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getDeleteLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/get-all-label")
	public ResponseEntity<List<Label>> viewAllLabel(HttpServletRequest request)
			throws LabelException {

		List<Label> labelList = noteService.getAllLabel(request.getHeader("userId"));

		return new ResponseEntity<>(labelList, HttpStatus.OK);
	}

	@GetMapping(value = "/notes-by-label-id/{labelId}")
	public ResponseEntity<List<Note>> notesByLabelId(HttpServletRequest request,
			@RequestParam String labelId) throws LabelException {

		List<Note> noteList = noteService.notesByLabelId(request.getHeader("userId"), labelId);

		return new ResponseEntity<>(noteList, HttpStatus.OK);

	}

	@GetMapping(value = "/get-all-trashed-note")
	public ResponseEntity<List<NoteViewDTO>> getAllTrashedNote(HttpServletRequest request)
			throws LabelException {

		List<NoteViewDTO> trashedNoteList = noteService.getAllTrashedNote(request.getHeader("userId"));

		return new ResponseEntity<>(trashedNoteList, HttpStatus.OK);
	}

	@GetMapping(value = "/get-all-archived-note")
	public ResponseEntity<List<NoteViewDTO>> getAllArchivedNote(HttpServletRequest request)
			throws LabelException {

		List<NoteViewDTO> pinnedNoteList = noteService.getAllArchivedNote(request.getHeader("userId"));

		return new ResponseEntity<>(pinnedNoteList, HttpStatus.OK);
	}

	@DeleteMapping(value = "/empty-trash")
	public ResponseEntity<ResponseDTO> emptyTrash(HttpServletRequest request) {

		noteService.emptyTrash(request.getHeader("userId"));

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getEmptyTrashMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/remove-label-from-note/{noteId}")
	public ResponseEntity<ResponseDTO> removeLabelFromNote(HttpServletRequest request,
			@PathVariable(value = "noteId") String noteId, @RequestBody String labelId)
			throws NoteNotFoundException, LabelException {

		noteService.removeLabelFromNote(request.getHeader("userId"), noteId, labelId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getRemoveLabelFromNoteMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/sort-note-by-title")
	public List<NoteViewDTO> sortNoteByTitle(HttpServletRequest request, @RequestParam Boolean ascendingOrDescending) {
		
		return noteService.sortNoteByTitle(request.getHeader("userId"),ascendingOrDescending);
		
	}
	
	@GetMapping(value="/sort-note-by-date")
	public List<NoteViewDTO> sortNoteByDate(HttpServletRequest request, @RequestParam Boolean ascendingOrDescending) {
		
		return noteService.sortNoteByDate(request.getHeader("userId"),ascendingOrDescending);
		
	}
	
	
	@GetMapping(value="/sort-label-by-name")
	public List<Label> sortLabelByName(HttpServletRequest request, @RequestParam Boolean ascendingOrDescending) {
		
		return noteService.sortLabelByName(request.getHeader("userId"),ascendingOrDescending);
		
	}
	
	@GetMapping(value="/sort-label-by-date")
	public List<Label> sortLabelByDate(HttpServletRequest request, @RequestParam Boolean ascendingOrDescending) {
		
		return noteService.sortLabelByDate(request.getHeader("userId"),ascendingOrDescending);
	}

}
