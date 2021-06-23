package com.nice.datafileanomalydetection.result.dao;

import com.nice.datafileanomalydetection.result.model.ItemAnomalyLevel;
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
import java.util.Map;

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

    public List<String> getFileName(String projectName, String regDtim) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT INPUTFILENAME FROM PREDICT_JOB \n")
                .append("WHERE JOBGB='PREDICT' \n")
                .append("AND PROJECTNAME=:projectName \n")
                .append("AND REGDTIM=:regDtim");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public Map<String, Object> getAnomalyIndex(String projectName, String regDtim) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME, REGDTIM, CASE WHEN PSI > 1.0 THEN 1 ELSE 0 END AS DISTINDEX, CASE WHEN CAR > 0 THEN 1 ELSE 0 END AS ITEMINDEX \n")
                .append("FROM PREDICT_JOB \n")
                .append("WHERE JOBGB='PREDICT' \n")
                .append("AND PSI IS NOT NULL \n")
                .append("AND CAR IS NOT NULL \n")
                .append("AND PROJECTNAME=:projectName \n")
                .append("AND REGDTIM=:regDtim \n")
                .append("UNION \n")
                .append("SELECT PROJECTNAME, REGDTIM, CASE WHEN ANOMALYRESULT > 0 THEN 1 ELSE 0 END AS DISTINDEX, CASE WHEN ANOMALYITEMCNT > 0 THEN 1 ELSE 0 END AS ITEMINDEX \n")
                .append("FROM PREDICT_ONLINE_RESULT \n")
                .append("WHERE PROJECTNAME=:projectName \n")
                .append("AND REGDTIM=:regDtim");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.queryForMap(selectSql.toString(), namedParameters);
    }

    public List<ItemAnomalyLevel> getItemAnomalyLevels(String projectName, String regDtim, String formatGb) {
        StringBuilder selectSql = new StringBuilder();
        if ("online".equals(formatGb)) {
            selectSql.append("SELECT FIELDNAME, MEANPROBABILITY * 100 AS ITEMANOMALYLEVEL \n")
                    .append("FROM TOT_ITEM_MEANPROBABILITY \n")
                    .append("WHERE PROJECTNAME=:projectName \n")
                    .append("AND REGDTIM=:regDtim");
        } else {
            selectSql.append("SELECT A.FIELDNAME, CASEWHEN(A.MEANSQUAREDERROR = '0.0', CASEWHEN(B.MEANSQUAREDERROR = '0.0', '0.0', '1000.0'), ABS(ROUND((B>MEANSQUAREDERROR - A.MEANSQUAREDERROR) / A.MEANSQUAREDERROR * 100, 4))) AS ITEMANOMALYLEVEL \n")
                    .append("FROM DEV_COL_MEANSQUAREDERROR A \n")
                    .append("LEFT OUTER JOIN TOT_COL_MEANSQUAREDERROR B \n")
                    .append("ON A.PROJECTNAME = B.PROJECTNAME \n")
                    .append("AND B.REGDTIM = :regDtim \n")
                    .append("AND A.FIELDNAME = B.FIELDNAME \n")
                    .append("WHERE A.PROJECTNAME = :projectName");



        }

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ItemAnomalyLevelMapper());
    }

    private static final class ItemAnomalyLevelMapper implements RowMapper<ItemAnomalyLevel> {
        @Override
        public ItemAnomalyLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemAnomalyLevel itemAnomalyLevel = new ItemAnomalyLevel();

            itemAnomalyLevel.setFieldName(rs.getString("FIELDNAME"));
            itemAnomalyLevel.setItemAnomalyLevel(rs.getDouble("ITEMANOMALYLEVEL"));

            return itemAnomalyLevel;
        }
    }

}



