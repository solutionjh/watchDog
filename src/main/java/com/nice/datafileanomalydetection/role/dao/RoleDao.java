package com.nice.datafileanomalydetection.role.dao;

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

import com.nice.datafileanomalydetection.main.model.MainGraphInfo;
import com.nice.datafileanomalydetection.role.model.Role;

@Repository
public class RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Role getRole (String roleType) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ROLE \n")
                .append(" WHERE ROLE_TYPE = :roleType");

        SqlParameterSource namedParameters = new MapSqlParameterSource("roleType", roleType);
        
        Role role = new Role();
        try {			
        	role = this.namedParameterJdbcTemplate.queryForObject(selectSql.toString(), namedParameters, new RoleMapper());
		} catch (Exception e) {
			role = null;
		}        
        return role;
    }
    
    public List<Role> getRoleList () {    	
    	StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ROLE");    	
    	return this.jdbcTemplate.query(selectSql.toString(), new RoleMapper());
    }
    
    private static final class RoleMapper implements RowMapper<Role> {
        public Role mapRow (ResultSet rs, int rowNum) throws SQLException {
        	Role role = new Role();
        	role.setRoleType(rs.getString("ROLE_TYPE"));
        	role.setUrl(rs.getString("URL"));
            return role;
        }
    }
}

