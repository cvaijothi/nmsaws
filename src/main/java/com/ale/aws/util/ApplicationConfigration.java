package com.ale.aws.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import com.ale.aws.entity.ApplnConfig;

@Configuration
@PropertySources({
		@PropertySource(value = "file:/${NMC_HOME}/aws/config.properties")})
public class ApplicationConfigration {

	@Autowired
	private Environment env;

	@Bean
    public ApplnConfig applnConfig() {
		ApplnConfig resource = new ApplnConfig();
		resource.setAccessKey(env.getProperty("aws_access_key_id"));
		resource.setSecretAccess(env.getProperty("aws_secret_access_key"));
		resource.setAwsRegion(env.getProperty("aws_region"));
		resource.setProxyHost(env.getProperty("proxy_host"));
		String proxyPort = env.getProperty("proxy_port");
		if(proxyPort != null && !proxyPort.isEmpty()){
			resource.setProxyPort(Integer.parseInt(proxyPort));
		}
		resource.setS3Bucket(env.getProperty("s3_bucket"));
		resource.setDefaultFolder(env.getProperty("s3_default_folder"));
		resource.setDynamodbTable(env.getProperty("dynamodb_table"));
		resource.setRdsTable(env.getProperty("rds_table"));
		return resource;
    }

}

