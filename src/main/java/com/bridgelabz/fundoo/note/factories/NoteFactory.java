package com.bridgelabz.fundoo.note.factories;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.note.models.Note;
import com.bridgelabz.fundoo.note.models.NoteViewDTO;


@Component
public class NoteFactory {

	public Note getNoteFromNoteViewDTO(NoteViewDTO noteCreateDTO) {
		
		Note note=new Note();
		note.setId(noteCreateDTO.getId());
		note.setColor(noteCreateDTO.getColor());
		note.setCreatedAt(noteCreateDTO.getCreatedAt());
		note.setDescription(noteCreateDTO.getDescription());
		note.setLabelList(noteCreateDTO.getLabelList());
		note.setReminder(noteCreateDTO.getReminder());
		note.setTitle(noteCreateDTO.getTitle());
		note.setUpdatedAt(noteCreateDTO.getUpdatedAt());
		return note;
	}
	
	public NoteViewDTO getNoteViewDTOFromNote(Note note) {
		NoteViewDTO noteViewDTO=new NoteViewDTO();
		noteViewDTO.setId(note.getId());
		noteViewDTO.setArchived(note.isArchived());
		noteViewDTO.setColor(note.getColor());
		noteViewDTO.setCreatedAt(note.getCreatedAt());
		noteViewDTO.setDescription(note.getDescription());
		noteViewDTO.setLabelList(note.getLabelList());
		noteViewDTO.setPinned(note.isPinned());
		noteViewDTO.setReminder(note.getReminder());
		noteViewDTO.setTitle(note.getTitle());
		noteViewDTO.setTrashed(note.isTrashed());
		noteViewDTO.setUpdatedAt(note.getUpdatedAt());
		noteViewDTO.setListOfURLMetaData(note.getListOfURLMetaData());
		return noteViewDTO;
	}
}
