package com.ale.aws.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileInput {

	@JsonProperty(value = "folder", required = false)
	private String folderPath;

	@JsonProperty("filename")
	private String fileName;

	@JsonProperty(value = "bucketname", required = false)
	private String bucketName;

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

}
