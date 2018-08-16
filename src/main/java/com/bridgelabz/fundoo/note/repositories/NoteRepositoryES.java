package com.bridgelabz.fundoo.note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.fundoo.note.models.Note;
public interface NoteRepositoryES extends ElasticsearchRepository< Note, String > {

	public Optional<List<Note>> findAllByUserId(String userId);
	
	public Optional<List<Note>> findAllByUserIdAndTrashed(String userId, boolean trash);
		
	public Optional<List<Note>> findAllByUserIdAndArchived(String userId, boolean archive);
	
	public void deleteAllByUserIdAndTrashed(String userId, boolean trash);

	public Optional<Note> findByIdAndUserId(String noteId, String userId);

	//public Optional<List<Note>> findAllByUserIdAndLabelId(String userId, String labelId);

	/*@Query(value="{'id':?0, 'labelList.name':?1}")
	public Note findByIdAndLabelName(String id, String labelName);

	@Query(value="{'userId':?0, 'labelList.id':?1}")
	public List<Note> findAllByUserIdAndLabelId(String userIdFromToken, String currentLabelId);*/
}
