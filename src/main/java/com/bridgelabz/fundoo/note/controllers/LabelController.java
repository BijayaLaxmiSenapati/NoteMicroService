package com.bridgelabz.fundoo.note.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.configurations.MessagePropertyConfig;
import com.bridgelabz.fundoo.note.exceptions.LabelException;
import com.bridgelabz.fundoo.note.models.Label;
import com.bridgelabz.fundoo.note.models.LabelCreateDTO;
import com.bridgelabz.fundoo.note.models.ResponseDTO;
import com.bridgelabz.fundoo.note.services.LabelService;

@RefreshScope
@RestController
@RequestMapping("/notes")
public class LabelController {

	@Autowired
	MessagePropertyConfig messagePropertyConfig;

	@Autowired
	private LabelService labelService;
	
	@PostMapping(value = "/create-label")
	public ResponseEntity<ResponseDTO> createLabel(HttpServletRequest request,
			@RequestBody LabelCreateDTO labelCreateDTO) throws LabelException {

		labelService.createLabel(request.getHeader("userId"), labelCreateDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getCreateLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/edit-label")
	public ResponseEntity<ResponseDTO> editLabel(HttpServletRequest request,
			@RequestParam String currentLabelId, @RequestParam String newLabelName) throws LabelException {

		labelService.editLabel(request.getHeader("userId"), currentLabelId, newLabelName);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getEditLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete-label")
	public ResponseEntity<ResponseDTO> deleteLabel(HttpServletRequest request,
			@RequestParam String labelId) throws LabelException {

		labelService.deleteLabel(request.getHeader("userId"), labelId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getDeleteLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/get-all-label")
	public ResponseEntity<List<Label>> viewAllLabel(HttpServletRequest request)
			throws LabelException {

		List<Label> labelList = labelService.getAllLabel(request.getHeader("userId"));

		return new ResponseEntity<>(labelList, HttpStatus.OK);
	}

}
