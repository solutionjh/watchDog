package com.nice.datafileanomalydetection;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
public class DatafileAnomalyDetectionApp 
{
	public static void main(String[] args) {
		SpringApplication.run(DatafileAnomalyDetectionApp.class, args);
	}

	@Bean
    public HttpMessageConverter<String> responseBodyConverter(){
        return new StringHttpMessageConverter(Charset.forName(StandardCharsets.UTF_8.toString()));
    }
    
    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.toString());
        characterEncodingFilter.setForceEncoding(Boolean.TRUE);
        return characterEncodingFilter;
    }
    
    // 프로그램 시작 시, 임시 폴더 내용 삭제하도록 수정 (2020-07-06, kty)
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
    	String baseDir = System.getProperty("user.dir").replace("\\", "/");
    	File tempDirectory = new File(baseDir+"/tmp");
    	if(!tempDirectory.exists()) tempDirectory.mkdir();
    	try {
			FileUtils.cleanDirectory(tempDirectory);
			//System.out.println("Temporary Files at "+ tempDirectory.toString() + " Deleted");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	File modelDir = new File(baseDir + "/model");
    	if(!modelDir.exists()) modelDir.mkdir();
    	
    	File propertyDir = new File(baseDir + "/properties");
    	if(!propertyDir.exists()) propertyDir.mkdir();
    }
}
