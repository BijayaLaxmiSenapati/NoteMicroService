package com.bridgelabz.fundoo.note.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoo.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoo.note.exceptions.GetURLInfoException;
import com.bridgelabz.fundoo.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoo.note.exceptions.LabelException;
import com.bridgelabz.fundoo.note.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoo.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoo.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoo.note.exceptions.NoteTrashException;
import com.bridgelabz.fundoo.note.factories.NoteFactory;
import com.bridgelabz.fundoo.note.models.ColorDTO;
import com.bridgelabz.fundoo.note.models.Label;
import com.bridgelabz.fundoo.note.models.LabelAddDTO;
import com.bridgelabz.fundoo.note.models.Note;
import com.bridgelabz.fundoo.note.models.NoteCreateDTO;
import com.bridgelabz.fundoo.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoo.note.models.NoteViewDTO;
import com.bridgelabz.fundoo.note.models.ReminderDTO;
import com.bridgelabz.fundoo.note.models.URLMetaData;
import com.bridgelabz.fundoo.note.repositories.LabelRepositoryES;
import com.bridgelabz.fundoo.note.repositories.NoteRepository;
import com.bridgelabz.fundoo.note.repositories.NoteRepositoryES;
import com.bridgelabz.fundoo.note.utility.Utility;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NoteRepositoryES noteRepositoryES;

	@Autowired
	private LabelRepositoryES labelRepositoryES;

	@Autowired
	private NoteFactory noteFactory;

	@Autowired
	private ContentScrappingService contentScrappingService;

	@Autowired
	private ImageStorageService imageStorageService;

	public static final String SUFFIX = "/";
	
	@Override
	public NoteViewDTO createNote(String userId, NoteCreateDTO noteCreateDTO)
			throws EmptyNoteException, InvalidDateException, GetURLInfoException {

		Utility.validateNoteWhileCreating(noteCreateDTO);

		Note note = new Note();
		note.setTitle(noteCreateDTO.getTitle());
		note.setDescription(noteCreateDTO.getDescription());

		List<URLMetaData> listOfURLMetaData = contentScrappingService
				.analyseDescription(noteCreateDTO.getDescription());
		note.setListOfURLMetaData(listOfURLMetaData);

		if (noteCreateDTO.getReminder() != null) {
			if (noteCreateDTO.getReminder().before(new Date())) {
				throw new InvalidDateException("reminder should not be earlier from now");
			}
			note.setReminder(noteCreateDTO.getReminder());
		}

		if (noteCreateDTO.getColor() != null)
			note.setColor(noteCreateDTO.getColor());
		else
			note.setColor("white");

		if (noteCreateDTO.isArchived()) {
			note.setArchived(true);
		}

		if (noteCreateDTO.isPinned()) {
			note.setPinned(true);
		}

		if (noteCreateDTO.getLabelId() != null) {

			List<Label> list = new ArrayList<Label>();

			Optional<Label> labelOfUser = labelRepositoryES.findByUserIdAndLabelId(userId, noteCreateDTO.getLabelId());

			if (labelOfUser.isPresent()) {

				list.add(labelOfUser.get());
				note.setLabelList(list);
			}
		}

		note.setCreatedAt(new Date());

		note.setUserId(userId);

		noteRepository.insert(note);
		noteRepositoryES.save(note);

		return noteFactory.getNoteViewDTOFromNote(note);
	}

	@Override
	public void updateNote(String userId, NoteUpdateDTO noteUpdateDTO)
			throws NoteNotFoundException, NoteAuthorisationException, GetURLInfoException {

		Note note = validate(userId, noteUpdateDTO.getId());
		if (noteUpdateDTO.getTitle() != null) {
			note.setTitle(noteUpdateDTO.getTitle());
		}
		if (noteUpdateDTO.getDescription() != null) {
			note.setDescription(noteUpdateDTO.getDescription());

			List<URLMetaData> listOfURLMetaData = contentScrappingService
					.analyseDescription(noteUpdateDTO.getDescription());
			note.setListOfURLMetaData(listOfURLMetaData);
		}
		note.setUpdatedAt(new Date());

		noteRepository.save(note);
		noteRepositoryES.save(note);
	}

	@Override
	public List<NoteViewDTO> getAllNotes(String userId, String sortBy, String sortOrder) {

		List<Note> noteList = noteRepositoryES.findAllByUserId(userId).get();
		if (noteList != null) {

			if (sortBy == null && sortOrder == null) {

				return noteList.stream().map(noteFactory::getNoteViewDTOFromNote).collect(Collectors.toList());
			}
			if (sortBy != null && sortBy.equalsIgnoreCase("byTitle")) {
				if (sortOrder == null || sortOrder.equalsIgnoreCase("ascending")) {
					return noteList.stream().sorted(Comparator.comparing(Note::getTitle))
							.map(noteFactory::getNoteViewDTOFromNote).collect(Collectors.toList());
				} else {
					return noteList.stream().sorted(Comparator.comparing(Note::getTitle).reversed())
							.map(noteFactory::getNoteViewDTOFromNote).collect(Collectors.toList());
				}

			} else if (sortBy != null && sortBy.equalsIgnoreCase("byDate")) {
				if (sortOrder == null || sortOrder.equalsIgnoreCase("ascending")) {
					return noteList.stream().sorted(Comparator.comparing(Note::getCreatedAt))
							.map(noteFactory::getNoteViewDTOFromNote).collect(Collectors.toList());
				} else {
					return noteList.stream().sorted(Comparator.comparing(Note::getCreatedAt).reversed())
							.map(noteFactory::getNoteViewDTOFromNote).collect(Collectors.toList());
				}
			}
		}
		return new ArrayList<>();
	}

	@Override
	public void trashNote(String userId, String noteId, boolean trashOrRestore)

			throws NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(userId, noteId);

		note.setTrashed(trashOrRestore);

		noteRepository.save(note);
		noteRepositoryES.save(note);
	}

	@Override
	public void permanentlyDeleteNote(String userId, String id)
			throws NoteNotFoundException, NoteAuthorisationException, NoteTrashException {

		Note note = validate(userId, id);

		if (!note.isTrashed()) {
			throw new NoteTrashException("Note not trashed yet!");
		}

		noteRepository.deleteById(note.getId());
		noteRepositoryES.deleteById(note.getId());

	}

	private Note validate(String userId, String noteId) throws NoteNotFoundException, NoteAuthorisationException {

		System.out.println(userId);
		System.out.println(noteId);
		Optional<Note> optionalnote = noteRepositoryES.findByIdAndUserId(noteId, userId);
		if (!optionalnote.isPresent()) {
			throw new NoteNotFoundException("given note-id to delete is not present");
		}

		if (!userId.equals(optionalnote.get().getUserId())) {
			throw new NoteAuthorisationException("Unauthorised access of note to delete");
		}
		return optionalnote.get();
	}

	@Override
	public void addReminder(String userId, String noteId, ReminderDTO reminderDTO)
			throws InvalidDateException, NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(userId, noteId);

		if (reminderDTO.getReminder().before(new Date())) {
			throw new InvalidDateException("reminder should not be earlier from now");
		}
		note.setReminder(reminderDTO.getReminder());

		noteRepository.save(note);
		noteRepositoryES.save(note);
	}

	@Override
	public void removeReminder(String userId, String noteId) throws NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(userId, noteId);

		note.setReminder(null);

		noteRepository.save(note);
		noteRepositoryES.save(note);
	}

	@Override
	public void addPin(String userId, String noteId, boolean pinOrUnpin)
			throws NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(userId, noteId);

		if (pinOrUnpin) {
			note.setArchived(false);

			note.setPinned(true);
		} else {
			note.setPinned(false);
		}

		noteRepository.save(note);
		noteRepositoryES.save(note);
	}

	@Override
	public void addToArchive(String userId, String noteId, boolean archiveOrUnarchive)
			throws NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(userId, noteId);

		note.setArchived(archiveOrUnarchive);
		noteRepository.save(note);
		noteRepositoryES.save(note);
	}

	@Override
	public List<NoteViewDTO> getAllTrashedNote(String userId) {

		Optional<List<Note>> noteList = noteRepositoryES.findAllByUserIdAndTrashed(userId, true);

		List<NoteViewDTO> noteViewDTO = noteList.get().stream().map(noteFactory::getNoteViewDTOFromNote)
				.collect(Collectors.toList());
		return noteViewDTO;
	}

	@Override
	public List<NoteViewDTO> getAllArchivedNote(String userId) {

		Optional<List<Note>> noteList = noteRepositoryES.findAllByUserIdAndArchived(userId, true);

		List<NoteViewDTO> noteViewDTO = noteList.get().stream().map(noteFactory::getNoteViewDTOFromNote)
				.collect(Collectors.toList());
		return noteViewDTO;
	}

	@Override
	public void emptyTrash(String userId) {

		noteRepository.deleteAllByUserIdAndTrashed(userId, true);
		noteRepositoryES.deleteAllByUserIdAndTrashed(userId, true);

	}

	@Override
	public void addLabel(String userId, String noteId, LabelAddDTO labelAddDTO)
			throws NoteNotFoundException, LabelException, LabelNotFoundException {

		if (labelAddDTO.getLabelId() == null) {
			throw new LabelException("for creating a label \"labelName\" should have given");
		}

		Optional<Note> optionalNote = noteRepositoryES.findByIdAndUserId(noteId, userId);

		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("given note-id to which label had to add is not present");
		}

		Optional<Label> labelOfUser = labelRepositoryES.findByUserIdAndLabelId(labelAddDTO.getLabelId(), userId);

		if (!labelOfUser.isPresent()) {

			throw new LabelNotFoundException("the label id given for adding to the note is invalid");
		}
		System.out.println(noteRepository.findByIdAndLabelId(optionalNote.get().getId(), labelAddDTO.getLabelId()));////////////////////////
		List<Label> labelListInNote = optionalNote.get().getLabelList();
		if (labelListInNote != null) {
			if (!noteRepository.findByIdAndLabelId(optionalNote.get().getId(), labelAddDTO.getLabelId()).isPresent()) {///////////////////////

				optionalNote.get().getLabelList().add(labelOfUser.get());
			}
		} else {
			List<Label> list = new ArrayList<Label>();
			list.add(labelOfUser.get());
			optionalNote.get().setLabelList(list);
		}
		noteRepository.save(optionalNote.get());
		noteRepositoryES.save(optionalNote.get());

	}

	@Override
	public List<Note> notesByLabelId(String userId, String labelId) throws LabelException {

		Optional<Label> labelOfUser = labelRepositoryES.findByLabelIdAndUserId(labelId, userId);
		if (!labelOfUser.isPresent()) {
			throw new LabelException("given label not found");
		}

		return noteRepository.findAllByUserIdAndLabelId(userId, labelId).get();
	}

	@Override
	public void removeLabelFromNote(String userId, String noteId, String labelId)
			throws NoteNotFoundException, LabelException {

		Optional<Note> optionalNote = noteRepositoryES.findById(noteId);
		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("Given note is not present");
		}

		Optional<Label> label = labelRepositoryES.findByUserIdAndLabelName(userId, labelId);
		if (!label.isPresent()) {
			throw new LabelException("label not found for the note");
		}

		Note note = optionalNote.get();

		note.getLabelList().remove(label.get());

		noteRepository.save(note);
		noteRepositoryES.save(note);
	}

	@Override
	public void addColor(String userId, String noteId, ColorDTO colorDTO) throws NoteNotFoundException {

		Optional<Note> optionalNote = noteRepositoryES.findByIdAndUserId(noteId, userId);
		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("Given note is not present");
		}
		optionalNote.get().setColor(colorDTO.getColor());
		noteRepository.save(optionalNote.get());
		noteRepositoryES.save(optionalNote.get());
	}

	@Override
	public List<Label> sortLabelByName(String userId, Boolean ascendingOrDescending) {

		List<Label> labelList = labelRepositoryES.findAllByUserId(userId).get();

		if (ascendingOrDescending) {
			return labelList.stream().sorted(Comparator.comparing(Label::getLabelName)).collect(Collectors.toList());
		} else {
			return labelList.stream().sorted(Comparator.comparing(Label::getLabelName).reversed())
					.collect(Collectors.toList());
		}
	}

	@Override
	public List<Label> sortLabelByDate(String userId, Boolean ascendingOrDescending) {

		List<Label> labelList = labelRepositoryES.findAllByUserId(userId).get();

		if (ascendingOrDescending) {
			return labelList.stream().sorted(Comparator.comparing(Label::getCreatedAt)).collect(Collectors.toList());
		} else {
			return labelList.stream().sorted(Comparator.comparing(Label::getCreatedAt).reversed())
					.collect(Collectors.toList());
		}
	}

	@Override
	public void addImage(String userId, String noteId, MultipartFile multipartFile) {

		String folderName = userId + SUFFIX + noteId;

		imageStorageService.uploadFile(folderName, multipartFile);

		String imageUrl = imageStorageService.getFile(folderName, multipartFile.getOriginalFilename());

		Note note = noteRepositoryES.findById(noteId).get();

		List<String> imageUrls = note.getListOfImageUrl();
		imageUrls.add(imageUrl);
		note.setListOfImageUrl(imageUrls);

		noteRepository.save(note);
		noteRepositoryES.save(note);
	}

	@Override
	public void removeImage(String userId, String noteId, String imageUrl) {

		System.out.println(imageUrl+"..........................");
		String folderName = userId + SUFFIX + noteId;

		String[] images=imageUrl.split("/");
		imageStorageService.deleteFile(folderName, images[images.length-1]);

		Note note = noteRepositoryES.findById(noteId).get();

		List<String> imageUrls = note.getListOfImageUrl();

		System.out.println(imageUrls.size());
		/*for (String image : imageUrls) {
			if (image.equals(imageUrl)) {
				imageUrls.remove(image);
			}
		}*/
		for(int i=0;i<imageUrls.size();i++) {
			if(imageUrls.get(i).equals(imageUrl)) {
				imageUrls.remove(i);
			}
		}
		note.setListOfImageUrl(imageUrls);

		noteRepository.save(note);
		noteRepositoryES.save(note);

	}

}
