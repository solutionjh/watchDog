package com.nice.datafileanomalydetection.member.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component("member")
public class Member {

    private String memberId;
    private String password;
    private String name;
    private String memberType;
    private Timestamp regdtim;
    private Timestamp lastAccess;
    
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public Timestamp getRegdtim() {
		return regdtim;
	}
	public void setRegdtim(Timestamp regdtim) {
		this.regdtim = regdtim;
	}
	public Timestamp getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(Timestamp lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	public String getRegDt() {
		if(this.regdtim == null) return null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ", Locale.KOREA);
		String s1 = simpledateformat.format(this.regdtim);
		return s1;
	}
	
	public String getLastAccessDt() {
		if(this.lastAccess == null) return null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ", Locale.KOREA);
		String s1 = simpledateformat.format(this.lastAccess);
		return s1;
	}
	

}
