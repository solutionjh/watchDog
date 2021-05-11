package com.nice.datafileanomalydetection.member.dao;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.nice.datafileanomalydetection.member.model.Member;
import com.nice.datafileanomalydetection.role.model.Role;

@Repository
public class MemberDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

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
    
    private static final class MemberMapper implements RowMapper<Member> {
        public Member mapRow (ResultSet rs, int rowNum) throws SQLException {
        	Member member = new Member();
        	member.setMemberId(rs.getString("MEMBER_ID"));
        	member.setName(rs.getString("NAME"));
        	member.setPassword(rs.getString("PASSWORD"));
        	member.setRoleType(rs.getString("ROLE_TYPE"));
        	member.setRegdtim(rs.getString("REGDTIM"));        	
            return member;
        }
    }
}

