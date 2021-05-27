package com.nice.datafileanomalydetection.jobexctlog.model;

import org.springframework.stereotype.Component;

@Component("jobExctLog")
public class JobExctLog {
	private String jobid;
    private String memberId;
    private String comment;
    private String enddtim;    

    public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getEnddtim() {
		return enddtim;
	}
	public void setEnddtim(String enddtim) {
		this.enddtim = enddtim;
	}
    
}
