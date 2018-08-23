package com.bridgelabz.fundoo.note.services;

import java.util.List;

import com.bridgelabz.fundoo.note.exceptions.GetURLInfoException;
import com.bridgelabz.fundoo.note.models.URLMetaData;

public interface ContentScrappingService {

	public List<URLMetaData> analyseDescription(String description) throws GetURLInfoException ;
}
