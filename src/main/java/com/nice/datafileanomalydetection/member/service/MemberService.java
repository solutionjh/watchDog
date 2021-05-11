package com.nice.datafileanomalydetection.member.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nice.datafileanomalydetection.member.dao.MemberDao;
import com.nice.datafileanomalydetection.member.model.Member;
import com.nice.datafileanomalydetection.message.ReloadMessageSource;

@Service
public class MemberService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    MemberDao memberDao;
    
    @Autowired
	private ReloadMessageSource message;

    @Override
	public UserDetails loadUserByUsername(String memberId)throws UsernameNotFoundException{
		Member member = memberDao.getMember(memberId);
		
		if(member == null) {
			throw new InternalAuthenticationServiceException(message.getMessage("err.login.001"));
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new User(member.getMemberId(), member.getPassword(), authorities);
	}
    
    public Member getMember(String memberId){
    	Member member = memberDao.getMember(memberId);
    	
    	if(member == null) {
    		throw new InternalAuthenticationServiceException(message.getMessage("err.login.001"));
    	}    	
    	return member;
    }
    
    
}
