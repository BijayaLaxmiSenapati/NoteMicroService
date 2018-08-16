package com.bridgelabz.fundoo.note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.bridgelabz.fundoo.note.models.Label;
public interface LabelRepository extends MongoRepository<Label, String> {
	
	public Optional<Label> findByUserIdAndLabelName(String userId, String name);
	public Optional<Label> findByUserIdAndLabelId(String userId, String labelId);
	public Optional<List<Label>> findAllByUserId(String userId);
	public Optional<Label> findByLabelIdAndUserId(String labelId,String userId);
}