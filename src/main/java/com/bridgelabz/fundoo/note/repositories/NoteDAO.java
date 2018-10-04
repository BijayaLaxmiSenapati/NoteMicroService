package com.bridgelabz.fundoo.note.repositories;

import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoo.note.models.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NoteDAO {

	private final String INDEX = "fundoonotes";
	private final String TYPE = "notes";
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	@Autowired
	private ObjectMapper objectMapper;

	public Note save(Note note) {

		Map dataMap = objectMapper.convertValue(note, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getId()).source(dataMap);
		try {
			IndexResponse response = restHighLevelClient.index(indexRequest);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return note;
	}
}
