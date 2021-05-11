package com.nice.datafileanomalydetection.login.model;

import org.springframework.stereotype.Component;

import com.nice.datafileanomalydetection.role.model.Role;

@Component("login")
public class Login {
	private String memberId;    
	private String name;    
    private Role role;
    
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
