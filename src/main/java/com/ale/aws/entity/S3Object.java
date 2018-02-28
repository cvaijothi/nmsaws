package com.ale.aws.entity;

public class S3Object {

	private String fileName;

	private String access;
	
	private ApplnConfig applnConfig;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public ApplnConfig getApplnConfig() {
		return applnConfig;
	}

	public void setApplnConfig(ApplnConfig applnConfig) {
		this.applnConfig = applnConfig;
	}
}
