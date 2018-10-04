package com.bridgelabz.fundoo.note.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

	public void createFolder(String bucketName, String folderName);

	void deleteFolder(String folderName);

	//void uploadFile(String folderName, String fileLocation);

	void deleteFile(String folderName, String deleteFileName);

	void uploadFile(String folderName, MultipartFile MultipartFile);

	String getFile(String folderName, String fileName);

}
