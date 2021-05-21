package com.nice.datafileanomalydetection.member.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nice.datafileanomalydetection.member.dao.MemberDao;
import com.nice.datafileanomalydetection.member.model.Member;
import com.nice.datafileanomalydetection.member.model.MemberInfo;
import com.nice.datafileanomalydetection.message.ReloadMessageSource;
import com.nice.datafileanomalydetection.util.SessionUtils;

@Service
public class MemberService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    MemberDao memberDao;
    
    @Autowired
	private ReloadMessageSource message;
    
    public List<Member> getMemberList (Authentication authentication) throws Exception {    	
    	System.out.println("auth::::::"+authentication.getAuthorities());
    	return memberDao.getMemberList(); 
    }    
    
    public String insertMember (MemberInfo memberInfo) throws Exception {    	
    	Member member = memberDao.getMember(memberInfo.getMemberId());
    	if(member != null) {
    		throw new Exception(message.getMessage("aleady.exists.id"));
    	}
    	memberDao.insertMember(memberInfo);    	
        return message.getMessage("db.success.insert");
    }    
    
    public void updateMemberAccess (String memberId) throws Exception {     	
    	memberDao.updateMemberAccess(memberId);
    }    
    
    public String updateMember (MemberInfo memberInfo) throws Exception {    	
    	Member member = memberDao.getMember(memberInfo.getMemberId());
    	if(member == null) {
    		throw new Exception(message.getMessage("aleady.not.exists.id"));
    	}
    	memberDao.updateMember(memberInfo);    	
    	return message.getMessage("db.success.update");
    }    
    
    public String deleteMember (String memberId) throws Exception {    	
    	Member member = memberDao.getMember(memberId);
    	if(member == null) {
    		throw new Exception(message.getMessage("aleady.not.exists.id"));
    	}
    	memberDao.deleteMember(memberId);    	
    	return message.getMessage("db.success.delete");
    }    
    
    public String updatePassword (String memberId, String password) throws Exception {    	
    	Member member = memberDao.getMember(memberId);
    	if(member == null) {
    		throw new Exception(message.getMessage("aleady.not.exists.id"));
    	}
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	String newPassword = encoder.encode(password);
    	memberDao.updatePassword(memberId, newPassword);    	
    	return message.getMessage("db.success.update.password");
    }    

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
