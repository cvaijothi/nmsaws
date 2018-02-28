package com.ale.aws.entity;

public class ApplnConfig {

	private String accessKey;

	private String secretAccess;

	private String awsRegion;

	private String proxyHost;

	private int proxyPort;

	private String s3Bucket;

	private String dynamodbTable;

	private String rdsTable;
	
	private String defaultFolder;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretAccess() {
		return secretAccess;
	}

	public void setSecretAccess(String secretAccess) {
		this.secretAccess = secretAccess;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getS3Bucket() {
		return s3Bucket;
	}

	public void setS3Bucket(String s3Bucket) {
		this.s3Bucket = s3Bucket;
	}

	public String getDynamodbTable() {
		return dynamodbTable;
	}

	public void setDynamodbTable(String dynamodbTable) {
		this.dynamodbTable = dynamodbTable;
	}

	public String getRdsTable() {
		return rdsTable;
	}

	public void setRdsTable(String rdsTable) {
		this.rdsTable = rdsTable;
	}

	public String getAwsRegion() {
		return awsRegion;
	}

	public void setAwsRegion(String awsRegion) {
		this.awsRegion = awsRegion;
	}

	public String getDefaultFolder() {
		return defaultFolder;
	}

	public void setDefaultFolder(String defaultFolder) {
		this.defaultFolder = defaultFolder;
	}

	
}
