package com.nice.datafileanomalydetection.main.dao;

import com.nice.datafileanomalydetection.main.model.MainGraphInfo;
import com.nice.datafileanomalydetection.main.model.MainItemInfo;
import com.nice.datafileanomalydetection.main.model.StatGraphInfo;
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
public class MainDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getRegDtim () {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT REGDTIM FROM PREDICT_JOB \n")
                .append("WHERE JOBGB = 'PREDICT' \n")
                .append("ORDER BY REGDTIM DESC");

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public List<String> getProjectRegDtim (String projectName) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT REGDTIM FROM PREDICT_JOB \n")
                .append("WHERE JOBGB = 'PREDICT' \n")
                .append("AND PROJECTNAME = :projectName \n")
                .append("ORDER BY REGDTIM DESC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public List<String> getProjectNames () {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME \n")
                .append("FROM PREDICT_JOB \n")
                .append("GROUP BY PROJECTNAME \n")
                .append("ORDER BY PROJECTNAME ASC");

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public List<MainGraphInfo> getAnomalyCount (String fromDtim) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT DTIM, \n")
                .append("SUM(CASE WHEN PSI<='1' THEN 1 ELSE 0 END) AS NORMALCNT, \n")
                .append("SUM(CASE WHEN PSI>'1' THEN 1 ELSE 0 END) AS ANOMALYCNT \n")
                .append("FROM \n")
                .append("(SELECT SUBSTR(REGDTIM,0,10) as DTIM, PSI \n")
                .append("FROM PREDICT_JOB \n")
                .append("WHERE JOBGB = 'PREDICT' \n")
                .append("AND PSI IS NOT NULL \n")
                .append("AND REGDTIM >= :fromDtim) \n")
                .append("GROUP BY DTIM \n")
                .append("ORDER BY DTIM");

        SqlParameterSource namedParameters = new MapSqlParameterSource("fromDtim", fromDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new MainMapper());

    }

    public List<MainGraphInfo> getProjectAnomalyCount (String projectName, String fromDtim) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT DTIM, \n")
                .append("SUM(CASE WHEN PSI<='1' THEN 1 ELSE 0 END) AS NORMALCNT, \n")
                .append("SUM(CASE WHEN PSI>'1' THEN 1 ELSE 0 END) AS ANOMALYCNT \n")
                .append("FROM \n")
                .append("(SELECT SUBSTR(REGDTIM,0,10) as DTIM, PSI \n")
                .append("FROM PREDICT_JOB \n")
                .append("WHERE JOBGB = 'PREDICT' \n")
                .append("AND PROJECTNAME = :projectName \n")
                .append("AND PSI IS NOT NULL \n")
                .append("AND REGDTIM >= :fromDtim) \n")
                .append("GROUP BY DTIM \n")
                .append("ORDER BY DTIM");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("fromDtim", fromDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new MainMapper());
    }

    public List<StatGraphInfo> getStats (String fromDtim) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT SUBSTR(REGDTIM,0,10) AS DTIM, \n")
                .append("AVG(CAST(PSI AS DOUBLE)) AS PSI, \n")
                .append("AVG(CAST(CAR AS INT)) AS CAR, \n")
                .append("AVG(CAST(CHISQCNT AS INT)) AS CHISQCNT, \n")
                .append("AVG(CAST(INPUTDATACNT AS INT)) AS INPUTDATACNT \n")
                .append("FROM PREDICT_JOB \n")
                .append("WHERE JOBGB = 'PREDICT' \n")
                .append("AND REGDTIM >= :fromDtim \n")
                .append("AND PSI IS NOT NULL \n")
                .append("GROUP BY DTIM \n")
                .append("ORDER BY DTIM ASC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("fromDtim", fromDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new StatMapper());
    }

    public List<StatGraphInfo> getProjectStats (String projectName, String fromDtim) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT SUBSTR(REGDTIM,0,10) AS DTIM, \n")
                .append("AVG(CAST(PSI AS DOUBLE)) AS PSI, \n")
                .append("AVG(CAST(CAR AS INT)) AS CAR, \n")
                .append("AVG(CAST(CHISQCNT AS INT)) AS CHISQCNT, \n")
                .append("AVG(CAST(INPUTDATACNT AS INT)) AS INPUTDATACNT \n")
                .append("FROM PREDICT_JOB \n")
                .append("WHERE JOBGB = 'PREDICT' \n")
                .append("AND PROJECTNAME = :projectName \n")
                .append("AND REGDTIM >= :fromDtim \n")
                .append("AND PSI IS NOT NULL \n")
                .append("GROUP BY DTIM \n")
                .append("ORDER BY DTIM ASC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("fromDtim", fromDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new StatMapper());
    }

    // refs #1360 항목값이 상수일 경우, 기준정보의 MEANSQUAREDERROR값이 0.0으로 계산되어 발생하는 division by zero 오류처리 (2020-11-09, kwb)
    public List<MainItemInfo> getAnomalyItemChangeRate (String projectName, String fromDtim) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT FIELDNAME, COUNT(CHANGERATE) as COUNT, AVG(CHANGERATE) as CHANGERATE \n")
                .append("FROM (SELECT a.PROJECTNAME, a.FIELDNAME, b.REGDTIM,  a.MEANSQUAREDERROR AS DEVMSE, b.MEANSQUAREDERROR AS NOWMSE, \n")
                .append("CASEWHEN(A.MEANSQUAREDERROR = '0.0', CASEWHEN(B.MEANSQUAREDERROR = '0.0', '0.0', '1000.0'), ABS(ROUND((B.MEANSQUAREDERROR - A.MEANSQUAREDERROR) / A.MEANSQUAREDERROR * 100, 4))) as CHANGERATE \n")
                .append("FROM DEV_COL_MEANSQUAREDERROR a \n")
                .append("LEFT OUTER JOIN TOT_COL_MEANSQUAREDERROR b \n")
                .append("ON a.PROJECTNAME=b.PROJECTNAME \n")
                .append("AND a.FIELDNAME=b.FIELDNAME \n")
                .append("WHERE a.PROJECTNAME=:projectName \n")
                .append("AND REGDTIM > :fromDtim \n")
                .append("ORDER BY REGDTIM) \n")
                .append("GROUP BY PROJECTNAME, FIELDNAME \n")
                .append("ORDER BY CHANGERATE DESC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("fromDtim", fromDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ItemMapper());
    }

    // refs #1360 항목값이 상수일 경우, 기준정보의 MEANSQUAREDERROR값이 0.0으로 계산되어 발생하는 division by zero 오류처리 (2020-11-09, kwb)
    public List<MainItemInfo> getAnomalyItemDetectCount (String projectName, String fromDtim) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT FIELDNAME, COUNT(CHANGERATE) as COUNT, AVG(CHANGERATE) as CHANGERATE \n")
                .append("FROM (SELECT a.PROJECTNAME, a.FIELDNAME, b.REGDTIM,  a.MEANSQUAREDERROR AS DEVMSE, b.MEANSQUAREDERROR AS NOWMSE, \n")
                .append("CASEWHEN(A.MEANSQUAREDERROR = '0.0', CASEWHEN(B.MEANSQUAREDERROR = '0.0', '0.0', '1000.0'), ABS(ROUND((B.MEANSQUAREDERROR - A.MEANSQUAREDERROR) / A.MEANSQUAREDERROR * 100, 4))) as CHANGERATE \n")
                .append("FROM DEV_COL_MEANSQUAREDERROR a \n")
                .append("LEFT OUTER JOIN TOT_COL_MEANSQUAREDERROR b \n")
                .append("ON a.PROJECTNAME=b.PROJECTNAME \n")
                .append("AND a.FIELDNAME=b.FIELDNAME \n")
                .append("WHERE a.PROJECTNAME=:projectName \n")
                .append("AND REGDTIM > :fromDtim \n")
                .append("ORDER BY REGDTIM) \n")
                .append("WHERE CHANGERATE > 50 \n")
                .append("GROUP BY PROJECTNAME, FIELDNAME \n")
                .append("ORDER BY COUNT DESC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("fromDtim", fromDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ItemMapper());
    }

    public List<String> getProjectNamesFromTable (String tableName) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT PROJECTNAME \n")
                .append("FROM " + tableName + " \n")
                .append("GROUP BY PROJECTNAME \n")
                .append("ORDER BY PROJECTNAME ASC");

        //SqlParameterSource namedParameters = new MapSqlParameterSource("tableName", tableName);

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public int deleteProjectData (String projectName, String tableName) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("DELETE \n")
                .append("FROM " + tableName + " \n")
                .append("WHERE PROJECTNAME=:projectName");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.update(selectSql.toString(), namedParameters);
    }

    public int truncateTableData (String tableName) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("TRUNCATE TABLE " + tableName);

        return this.jdbcTemplate.update(selectSql.toString());
    }

    private static final class MainMapper implements RowMapper<MainGraphInfo> {

        public MainGraphInfo mapRow (ResultSet rs, int rowNum) throws SQLException {
            MainGraphInfo mainGraphInfo = new MainGraphInfo();

            mainGraphInfo.setDtim(rs.getString("DTIM"));
            mainGraphInfo.setAnomalyCnt(rs.getString("ANOMALYCNT"));
            mainGraphInfo.setNormalCnt(rs.getString("NORMALCNT"));

            return mainGraphInfo;
        }
    }

    private static final class StatMapper implements RowMapper<StatGraphInfo> {

        public StatGraphInfo mapRow (ResultSet rs, int rowNum) throws SQLException {
            StatGraphInfo statGraphInfo = new StatGraphInfo();

            statGraphInfo.setDtim(rs.getString("DTIM"));
            statGraphInfo.setPsi(rs.getString("PSI"));
            statGraphInfo.setCar(rs.getString("CAR"));
            statGraphInfo.setChisqcnt(rs.getString("CHISQCNT"));
            statGraphInfo.setInputDataCnt(rs.getString("INPUTDATACNT"));

            return statGraphInfo;
        }
    }

    private static final class ItemMapper implements RowMapper<MainItemInfo> {

        public MainItemInfo mapRow (ResultSet rs, int rowNum) throws SQLException {
            MainItemInfo mainItemInfo = new MainItemInfo();

            mainItemInfo.setFieldName(rs.getString("FIELDNAME"));
            mainItemInfo.setChangeRate(rs.getString("CHANGERATE"));
            mainItemInfo.setDetectedCnt(rs.getString("COUNT"));

            return mainItemInfo;
        }
    }

}
