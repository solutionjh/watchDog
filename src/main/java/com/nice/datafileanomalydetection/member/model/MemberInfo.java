package com.nice.datafileanomalydetection.member.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("memberInfo")
public class MemberInfo {

    private String memberId;
    private String password;
    private String name;
    private String memberType;
    
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
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();		
		if(password != null) this.password = encoder.encode(password);
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
}
