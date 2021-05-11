package com.nice.datafileanomalydetection.member.model;

import org.springframework.stereotype.Component;

@Component("member")
public class Member {

    private String memberId;
    private String password;
    private String name;
    private String roleType;
    private String regdtim;
    
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
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getRegdtim() {
		return regdtim;
	}
	public void setRegdtim(String regdtim) {
		this.regdtim = regdtim;
	}

}
