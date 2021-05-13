package com.nice.datafileanomalydetection.login.model;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("Remember")
public class Remember {
	private String memberId;    
	private String series;    
    private String token;
    private Timestamp lastUpdated;
    
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
    
}
