package com.nice.datafileanomalydetection.member.dao;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.nice.datafileanomalydetection.member.model.Member;
import com.nice.datafileanomalydetection.member.model.MemberInfo;

@Repository
public class MemberDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Member> getMemberList() {
    	StringBuilder selectSql = new StringBuilder();
    	selectSql.append("SELECT * FROM MEMBER");    	
    	return  this.jdbcTemplate.query(selectSql.toString(),  new MemberListMapper());
    }
    
    public Member getMember (String memberId) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM MEMBER \n")
                .append(" WHERE MEMBER_ID = :memberId");

        SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId);
        Member member = new Member();
        try {
        	member = this.namedParameterJdbcTemplate.queryForObject(selectSql.toString(), namedParameters, new MemberMapper());
		} catch (Exception e) {
			member = null;
		}
        
        return member;
    }
    
    public void insertMember (MemberInfo memberInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO MEMBER (MEMBER_ID, PASSWORD, NAME, ROLE_TYPE, LAST_ACCESS) \n")
        		 .append("VALUES (:memberId, :password, :name, :roleType, CURRENT_TIMESTAMP())");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(memberInfo);
        this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);       
    }
    
    public void updateMemberAccess (String memberId) {    	    	
    	StringBuilder updateSql = new StringBuilder();
    	updateSql.append("UPDATE MEMBER  \n")
    	.append("SET LAST_ACCESS = CURRENT_TIMESTAMP() \n")
    	.append("WHERE MEMBER_ID  = :memberId");
    	
    	SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId);
    	this.namedParameterJdbcTemplate.update(updateSql.toString(), namedParameters);    	
    }
    
    
    public void updateMember (MemberInfo memberInfo) {    	    	
    	StringBuilder updateSql = new StringBuilder();
    	updateSql.append("UPDATE MEMBER  \n")
		    	 .append("SET NAME = :name \n")
		    	 .append("   ,ROLE_TYPE = :roleType \n")
		         .append("WHERE MEMBER_ID  = :memberId");
    			 
    	SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(memberInfo);
    	this.namedParameterJdbcTemplate.update(updateSql.toString(), namedParameters);    	
    }
    
    public void deleteMember (String memberId) {    	    	
    	StringBuilder deleteSql = new StringBuilder();
    	deleteSql.append("DELETE FROM MEMBER  \n")
    	.append("WHERE MEMBER_ID  = :memberId");    	
    	SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId);
        this.namedParameterJdbcTemplate.update(deleteSql.toString(), namedParameters);   	
    }
    
    public void updatePassword (String memberId, String password) {    	    	
    	StringBuilder updateSql = new StringBuilder();
    	updateSql.append("UPDATE MEMBER  \n")
		    	 .append("SET PASSWORD = :password \n")
		         .append("WHERE MEMBER_ID  = :memberId");
    			 
    	SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId).addValue("password", password);
    	this.namedParameterJdbcTemplate.update(updateSql.toString(), namedParameters);    	
    }
        
    
    private static final class MemberMapper implements RowMapper<Member> {
        public Member mapRow (ResultSet rs, int rowNum) throws SQLException {
        	Member member = new Member();
        	member.setMemberId(rs.getString("MEMBER_ID"));
        	member.setName(rs.getString("NAME"));
        	member.setPassword(rs.getString("PASSWORD"));
        	member.setRoleType(rs.getString("ROLE_TYPE"));
        	member.setRegdtim(rs.getTimestamp("REGDTIM"));        	
        	member.setLastAccess(rs.getTimestamp("LAST_ACCESS"));        	
            return member;
        }
    }
    
    private static final class MemberListMapper implements RowMapper<Member> {
    	public Member mapRow (ResultSet rs, int rowNum) throws SQLException {
    		Member member = new Member();
    		member.setMemberId(rs.getString("MEMBER_ID"));
    		member.setName(rs.getString("NAME"));
    		member.setPassword(null);
    		member.setRoleType(rs.getString("ROLE_TYPE"));
    		member.setRegdtim(rs.getTimestamp("REGDTIM"));        	
    		member.setLastAccess(rs.getTimestamp("LAST_ACCESS"));        	
    		return member;
    	}
    }
}

