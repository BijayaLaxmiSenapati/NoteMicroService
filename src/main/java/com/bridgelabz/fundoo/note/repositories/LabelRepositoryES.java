package com.bridgelabz.fundoo.note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.fundoo.note.models.Label;

public interface LabelRepositoryES extends ElasticsearchRepository<Label, String> {

	public Optional<Label> findByUserIdAndLabelName(String userId, String name);
	public Optional<Label> findByUserIdAndLabelId(String userId, String labelId);
	public Optional<List<Label>> findAllByUserId(String userId);
	public Optional<Label> findByLabelIdAndUserId(String currentLabelId, String userIdFromToken);
}
