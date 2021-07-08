package com.nice.datafileanomalydetection.jobexctlog.dao;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.nice.datafileanomalydetection.jobexctlog.model.JobExctLog;

@Repository
public class JobExctLogDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public List<JobExctLog> getJobExctLogList (String memberId) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM JOB_EXECUTION_LOG \n")
                .append(" WHERE MEMBER_ID = :memberId");
        SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId);        
        return this.namedParameterJdbcTemplate.query(selectSql.toString(),namedParameters,  new JobExctLogMapper());
    }
        
        
    public void deleteJobExctLog (String memberId) {    	    	
    	StringBuilder deleteSql = new StringBuilder();
    	deleteSql.append("DELETE FROM JOB_EXECUTION_LOG  \n")
    	.append("WHERE MEMBER_ID  = :memberId");    	
    	SqlParameterSource namedParameters = new MapSqlParameterSource("memberId", memberId);
        this.namedParameterJdbcTemplate.update(deleteSql.toString(), namedParameters);   	
    }
        
    
    private static final class JobExctLogMapper implements RowMapper<JobExctLog> {
    	@Override
        public JobExctLog mapRow (ResultSet rs, int rowNum) throws SQLException {
        	JobExctLog jobExctLog = new JobExctLog();
        	jobExctLog.setJobid(rs.getString("JOBID"));
        	jobExctLog.setMemberId(rs.getString("MEMBER_ID"));	
        	jobExctLog.setComment(rs.getString("COMMENT"));
        	jobExctLog.setEnddtim(rs.getString("ENDDTIM"));       	
            return jobExctLog;
        }
    }
}

