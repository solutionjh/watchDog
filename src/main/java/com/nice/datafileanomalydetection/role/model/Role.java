package com.nice.datafileanomalydetection.role.model;

import org.springframework.stereotype.Component;

@Component("role")
public class Role {
	private String roleType;    
    private String url;
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
