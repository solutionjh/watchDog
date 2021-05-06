package com.nice.datafileanomalydetection.result.dao;

import com.nice.datafileanomalydetection.result.model.Result;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ResultDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getProjectNames () {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME FROM DEV_MEANSQUAREDERROR  \n")
                .append("GROUP BY PROJECTNAME ORDER BY PROJECTNAME ASC");

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public List<Result> getResults () {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME, REGDTIM, ENDDTIM, INPUTFILENAME, INPUTDATACNT, PSI, CAR, CHISQCNT \n")
                .append("  FROM PREDICT_JOB \n")
                .append(" WHERE JOBGB = 'PREDICT' \n")
                .append(" ORDER BY REGDTIM DESC");
        return this.jdbcTemplate.query(selectSql.toString(), new ResultMapper());
    }

    public List<Result> getProjectResult (String projectName) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME, REGDTIM, ENDDTIM, INPUTFILENAME, INPUTDATACNT, PSI, CAR, CHISQCNT \n")
                .append("  FROM PREDICT_JOB \n")
                .append(" WHERE PROJECTNAME = :projectName AND JOBGB = 'PREDICT'\n")
                .append(" ORDER BY REGDTIM DESC");
        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ResultMapper());
    }

    private static final class ResultMapper implements RowMapper<Result> {
        @Override
        public Result mapRow (ResultSet rs, int rowNum) throws SQLException {
            Result result = new Result();
            result.setProjectName(rs.getString("PROJECTNAME"));
            result.setRegDtim(rs.getString("REGDTIM"));
            result.setEndDtim(rs.getString("ENDDTIM"));
            result.setInputFilename(FilenameUtils.getName(rs.getString("INPUTFILENAME")));
            result.setInputDatacnt(rs.getString("INPUTDATACNT"));
            result.setPsi(rs.getString("PSI"));
            result.setCar(rs.getString("CAR"));
            result.setChisqCnt(rs.getString("CHISQCNT"));
            return result;
        }
    }

}



