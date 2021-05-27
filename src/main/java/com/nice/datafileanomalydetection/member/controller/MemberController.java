package com.nice.datafileanomalydetection.member.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nice.datafileanomalydetection.member.model.Member;
import com.nice.datafileanomalydetection.member.model.MemberInfo;
import com.nice.datafileanomalydetection.member.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping(value = {"/api/member"})
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    MemberService memberService;
    
    @GetMapping(value = "/getList")
	@ApiOperation(value = "회원목록조회")
	public List<Member> getMemberList(HttpServletRequest request, Authentication authentication) throws Exception {
		return memberService.getMemberList(authentication);
	}
    
    @GetMapping(value = "/get/{memberId}")
    @ApiOperation(value = "회원조회")
    public Member getMember(@ApiParam(name = "memberId", value = "memberId", required = true) @PathVariable("memberId") String memberId) throws Exception {
    	Member member = memberService.getMember(memberId);
    	member.setPassword(null);
    	return member;
    }
    
    @PostMapping(value = "/insert")
	@ApiOperation(value = "회원등록")
	public String insertMember(@ApiParam(name = "MemberInfo", value = "MemberInfo", required = true) @RequestBody MemberInfo memberInfo, HttpServletRequest request) throws Exception {
		return memberService.insertMember(memberInfo);
	}
    @PutMapping(value = "/update")
    @ApiOperation(value = "회원수정")
    public String updatetMember(@ApiParam(name = "MemberInfo", value = "MemberInfo", required = true) @RequestBody MemberInfo memberInfo, HttpServletRequest request) throws Exception {
    	return memberService.updateMember(memberInfo);
    }
    @DeleteMapping(value = "/delete/{memberId}")
    @ApiOperation(value = "회원삭제")
    public String deletetMember(@ApiParam(name = "memberId", value = "memberId", required = true) @PathVariable("memberId") String memberId) throws Exception {
    	return memberService.deleteMember(memberId);
    }
    @PutMapping(value = "/password/{memberId}/{password}")
    @ApiOperation(value = "비밀번호변경")
    public String updatePassword(
    @ApiParam(name = "memberId", value = "memberId", required = true) @PathVariable("memberId") String memberId,
    @ApiParam(name = "password", value = "password", required = true) @PathVariable("password") String password ) throws Exception {
    	return memberService.updatePassword(memberId,password);
    }
    
    @GetMapping(value = "/getMemberTypeList")
   	@ApiOperation(value = "회원역활목록조회")
   	public List<String> getMemberTypeList(HttpServletRequest request) throws Exception {
   		return memberService.getMemberTypeList();
   	}
}
