package com.ale.aws.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ale.aws.entity.ApplnConfig;
import com.ale.aws.entity.S3Object;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@RestController
@RequestMapping("/hello")
public class NmAwsDynamoDbRestController {
	
	@Autowired
	ApplnConfig applnConfig;
	
	private AmazonDynamoDBClient amazonDynamoDBClient;
	
	@PostConstruct
	@SuppressWarnings("deprecation")
	public void initConfiguration() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(applnConfig.getAccessKey(),
				applnConfig.getSecretAccess());

		amazonDynamoDBClient = new AmazonDynamoDBClient(awsCredentials);

		//amazonDynamoDBClient.setRegion(Region.getRegion(Regions.fromName(applnConfig.getAwsRegion())));
		amazonDynamoDBClient.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
		
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public S3Object hello(@PathVariable String name) {
		String result = "Hello " + name;
		S3Object s3Obj = new S3Object();
		s3Obj.setAccess("READ_ONLY");
		s3Obj.setFileName(result);
		//s3Obj.setApplnConfig(applnConfig);
		return s3Obj;
	}
}