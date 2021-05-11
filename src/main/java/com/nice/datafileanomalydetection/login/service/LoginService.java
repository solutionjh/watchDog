package com.nice.datafileanomalydetection.login.service;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nice.datafileanomalydetection.login.model.Login;
import com.nice.datafileanomalydetection.member.model.Member;
import com.nice.datafileanomalydetection.member.service.MemberService;
import com.nice.datafileanomalydetection.role.model.Role;
import com.nice.datafileanomalydetection.role.service.RoleService;
import com.nice.datafileanomalydetection.util.SessionUtils;

@Service
public class LoginService{

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
	private  MemberService memberService;

	@Autowired
	private  RoleService roleService;	
	
	public void loginSession(HttpServletRequest request , Authentication auth) throws Exception{
		
		Member member = memberService.getMember(auth.getName());		
		Role role = roleService.getRole(member.getRoleType());		
		Login login = new Login();		
		login.setMemberId(member.getMemberId());
		login.setName(member.getName());
		login.setRole(role);		
		SessionUtils.setLogin(login);
	}
}
