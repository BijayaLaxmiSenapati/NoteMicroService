package com.bridgelabz.fundoo.note.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoo.note.models.NoteCreateDTO;

/**
 * @author adminstrato
 *
 */
public class Utility {

	/**
	 * @param noteCreateDTO
	 * @throws NoteException
	 * @throws EmptyNoteException
	 */
	public static void validateNoteWhileCreating(NoteCreateDTO noteCreateDTO) throws EmptyNoteException {

		if ((noteCreateDTO.getTitle() == null || noteCreateDTO.getTitle().trim().isEmpty())
				&& (noteCreateDTO.getDescription() == null || noteCreateDTO.getDescription().trim().isEmpty())) {
			throw new EmptyNoteException("Both title and description fields should not be empty");
		}
	}
	
	public static File convert(MultipartFile file)
	{    
	    File convFile = new File(file.getOriginalFilename());
	    try {
			convFile.createNewFile();
			 FileOutputStream fos;
			fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	    return convFile;
	}

}
