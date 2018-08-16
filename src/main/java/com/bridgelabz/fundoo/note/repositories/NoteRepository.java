package com.bridgelabz.fundoo.note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bridgelabz.fundoo.note.models.Note;

/**
 * @author adminstrato
 *
 */
public interface NoteRepository extends MongoRepository<Note, String> {
	
	/**
	 * @param userId
	 * @return
	 */
	public List<Note> findAllByUserId(String userId);
	
	/*@Query(value="{'id':?0, 'labelList.name':?1}")
	public Note findByIdAndLabelName(String id, String labelName);*/
	
	@Query(value="{'id':?0, 'labelList.labelId':?1}")
	public Optional<Note> findByIdAndLabelId(String noteId, String labelId);

	@Query(value="{'userId':?0, 'labelList.labelId':?1}")
	public Optional<List<Note>> findAllByUserIdAndLabelId(String userIdFromToken, String currentLabelId);

	public void deleteAllByUserIdAndTrashed(String userId, boolean trash);

}
