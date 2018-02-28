package com.ale.aws.controller;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ale.aws.entity.ApplnConfig;
import com.ale.aws.entity.FileInput;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@RestController
@RequestMapping("/api/nms/aws/s3")
public class NmAwsS3RestController {

	@Autowired
	ApplnConfig applnConfig;

	private AmazonS3 amazonS3;

	@PostConstruct
	@SuppressWarnings("deprecation")
	public void initConfiguration() {

		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(applnConfig.getAccessKey(),
				applnConfig.getSecretAccess());

		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setProtocol(Protocol.HTTPS);

		// setup proxy connection:
		clientConfiguration.setProxyHost(applnConfig.getProxyHost());
		clientConfiguration.setProxyPort(applnConfig.getProxyPort());

		amazonS3 = new AmazonS3Client(awsCredentials, clientConfiguration);

		if (applnConfig.getAwsRegion() != null && !applnConfig.getAwsRegion().isEmpty()) {
			amazonS3.setRegion(Region.getRegion(Regions.fromName(applnConfig.getAwsRegion())));
		} else {
			amazonS3.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
		}

	}

	@RequestMapping(value = "/{bucketName}/object", method = RequestMethod.GET)
	public ObjectListing listObjects(@PathVariable String bucketName) {

		String bucketNameList = bucketName;

		if (bucketNameList == null || bucketNameList.isEmpty()) {
			bucketNameList = applnConfig.getS3Bucket();
		}

		ObjectListing objectListing = amazonS3.listObjects(new ListObjectsRequest().withBucketName(bucketNameList));
		return objectListing;
	}

	@RequestMapping(value = "/bucket/{bucketName}", method = RequestMethod.POST)
	public String createBucket(@PathVariable String bucketName) {

		String bucketNameCreate = bucketName;

		if (bucketNameCreate == null || bucketNameCreate.isEmpty()) {
			bucketNameCreate = applnConfig.getS3Bucket();
		}
		try {
			amazonS3.createBucket(bucketNameCreate);

		} catch (AmazonS3Exception s3Exception) {
			if (s3Exception.getStatusCode() == 409) {
				System.out.println("Bucket is already there");
			} else {
				return "FAILURE to create bucket" + bucketNameCreate;
			}
		}
		return "SUCCESS";
	}

	@RequestMapping(value = "/bucket/{bucketName}", method = RequestMethod.DELETE)
	public String deleteBucket(@PathVariable String bucketName) {

		try {
			if (bucketName != null) {
				amazonS3.deleteBucket(bucketName);
			}

		} catch (AmazonS3Exception s3Exception) {
			return "FAILURE to delete bucket" + bucketName;
		}
		return "SUCCESS";
	}

	@RequestMapping(value = "/bucket/object", method = RequestMethod.POST)
	public String uploadObject(@RequestBody FileInput fileInput) {

		PutObjectResult putObjectResult = null;

		String folderName = fileInput.getFolderPath();
		if (folderName == null || folderName.isEmpty()) {
			folderName = applnConfig.getDefaultFolder();
		}

		String fullFileName = folderName + "\\" + fileInput.getFileName();
		File fileToUpload = new File(fullFileName);

		if (fileToUpload.exists()) {

			String bucketName = fileInput.getBucketName();

			if (bucketName == null || bucketName.isEmpty()) {
				bucketName = applnConfig.getS3Bucket();
			}

			putObjectResult = amazonS3
					.putObject(new PutObjectRequest(bucketName, fileInput.getFileName(), fileToUpload));
			System.out.println(putObjectResult);
		} else {
			return "FILE_NOT_EXISTS in " + fullFileName;
		}

		return "SUCCESS";
	}

	@RequestMapping(value = "/bucket/object", method = RequestMethod.DELETE)
	public String deleteObject(@RequestBody FileInput fileInput) {

		try {

			String bucketName = fileInput.getBucketName();

			if (bucketName == null || bucketName.isEmpty()) {
				bucketName = applnConfig.getS3Bucket();
			}
			amazonS3.deleteObject(bucketName, fileInput.getFileName());
		} catch (Exception exception) {
			System.out.println(exception);
			return "FAILURE to delete file " + fileInput.getFileName();
		}

		return "SUCCESS";
	}
}