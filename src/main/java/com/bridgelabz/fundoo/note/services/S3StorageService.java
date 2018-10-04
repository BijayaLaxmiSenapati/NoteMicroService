package com.bridgelabz.fundoo.note.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.bridgelabz.fundoo.note.utility.Utility;

@Service
public class S3StorageService implements ImageStorageService {

	/*//@Value("${s3AccessKeyId}")
	String accessKeyID=System.getenv("accessKeyID");

	//@Value("${s3SecretAccessKey}")
	String secretAccessKey=System.getenv("secretAccessKey");*/
	
	@Value("${s3AccessKeyId}")
	String s3AccessKeyId;
	
	@Value("${s3SecretAccessKey}")
	String s3SecretAccessKey;

	@Value("${bucketName}")
	String bucketName;

	public static final String SUFFIX = "/";

	public AmazonS3 createS3Client() {

		AWSCredentials credentials = new BasicAWSCredentials(s3AccessKeyId, s3SecretAccessKey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).build();

		return s3client;
	}

	@Override
	public void createFolder(String bucketName, String folderName) {

		AmazonS3 s3client = createS3Client();

		ObjectMetadata metadata = new ObjectMetadata();

		metadata.setContentLength(0);

		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent,
				metadata);
		s3client.putObject(putObjectRequest);
	}

	@Override
	public void deleteFolder(String folderName) {
		AmazonS3 s3client = createS3Client();

		List<S3ObjectSummary> fileList = s3client.listObjects(bucketName, folderName).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			s3client.deleteObject(bucketName, file.getKey());
		}
		s3client.deleteObject(bucketName, folderName);
	}

	@Override
	public void uploadFile(String folderName, MultipartFile multipartFile) {
		AmazonS3 s3client = createS3Client();

		String objectKey = folderName + SUFFIX + multipartFile.getOriginalFilename();

		File file = Utility.convert(multipartFile);

		s3client.putObject(
				new PutObjectRequest(bucketName, objectKey, file).withCannedAcl(CannedAccessControlList.PublicRead));

	}

	@Override
	public void deleteFile(String folderName, String imageName) {
		AmazonS3 s3client = createS3Client();

		String objectKey = folderName + SUFFIX + imageName;

		s3client.deleteObject(bucketName, objectKey);
	}

	@Override
	public String getFile(String folderName, String imageName) {

		AmazonS3 s3client = createS3Client();

		String objectKey = folderName + SUFFIX + imageName;

		String url = ((AmazonS3Client) s3client).getResourceUrl(bucketName, objectKey);

		return url;
	}

}
