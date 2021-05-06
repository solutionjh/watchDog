package com.nice.datafileanomalydetection.online.dao;

import com.nice.datafileanomalydetection.online.model.OnlineItemResult;
import com.nice.datafileanomalydetection.online.model.OnlineResult;
import com.nice.datafileanomalydetection.online.model.OnlineRowDistResult;
import com.nice.datafileanomalydetection.online.model.OnlineRowResult;
import com.nice.datafileanomalydetection.predict.model.KeyInfo;
import org.apache.commons.io.FilenameUtils;
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

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class OnlineDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getResultProjectNames () {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME FROM PREDICT_ONLINE_RESULT \n")
                .append("GROUP BY PROJECTNAME ORDER BY PROJECTNAME ASC");

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public List<OnlineResult> getResults () {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * \n")
                .append("  FROM PREDICT_ONLINE_RESULT \n")
                .append(" ORDER BY REGDTIM DESC");

        return this.jdbcTemplate.query(selectSql.toString(), new OnlineResultMapper());
    }

    public List<OnlineResult> getProjectResult (String projectName) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * \n")
                .append("  FROM PREDICT_ONLINE_RESULT \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" ORDER BY REGDTIM DESC");
        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineResultMapper());
    }

    public List<String> getRowResultProjectNames () {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME FROM TOT_ROWPROB_STAT \n")
                .append("GROUP BY PROJECTNAME ORDER BY PROJECTNAME ASC");

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public List<String> getRowResultRegdtim (String projectName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT REGDTIM FROM TOT_ROWPROB_STAT \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" GROUP BY REGDTIM ORDER BY REGDTIM DESC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public List<OnlineRowResult> getProjectRowResult (String projectName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" SELECT * \n")
                .append("   FROM TOT_ROWPROB_STAT \n")
                .append("  WHERE PROJECTNAME = :projectName \n")
                .append("  ORDER BY REGDTIM \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineRowResultMapper());
    }

    public List<OnlineRowResult> getProjectRegdtimRowResult (String projectName, String regDtim) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" SELECT * \n")
                .append("   FROM TOT_ROWPROB_STAT \n")
                .append("  WHERE PROJECTNAME = :projectName \n")
                .append("    AND REGDTIM     = :regDtim \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineRowResultMapper());
    }

    public List<String> getItemResultProjectNames () {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME FROM TOT_ITEM_MEANPROBABILITY \n")
                .append("GROUP BY PROJECTNAME ORDER BY PROJECTNAME ASC");

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public List<String> getItemResultRegdtim (String projectName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT REGDTIM FROM TOT_ITEM_MEANPROBABILITY \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" GROUP BY REGDTIM ORDER BY REGDTIM DESC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public List<String> getProjectItemFieldName (String projectName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT DISTINCT FIELDNAME \n")
                .append("FROM TOT_ITEM_MEANPROBABILITY \n")
                .append("WHERE PROJECTNAME = :projectName \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public List<String> getItemResultFieldName (String projectName, String regDtim) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT DISTINCT FIELDNAME \n")
                .append("FROM TOT_ITEM_MEANPROBABILITY \n")
                .append("WHERE PROJECTNAME = :projectName \n")
                .append("AND REGDTIM = :regDtim");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public List<OnlineItemResult> getProjectRegdtimItemResult (String projectName, String regDtim) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" SELECT * \n")
                .append("   FROM TOT_ITEM_MEANPROBABILITY \n")
                .append("  WHERE PROJECTNAME = :projectName \n")
                .append("    AND REGDTIM     = :regDtim \n")
                .append("  ORDER BY MEANPROBABILITY DESC, FIELDNAME");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineItemResultMapper());
    }

    public List<OnlineItemResult> getProjectRegdtimFieldItemResult (String projectName, String regDtim, String fieldName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" SELECT * \n")
                .append("   FROM TOT_ITEM_MEANPROBABILITY \n")
                .append("  WHERE PROJECTNAME = :projectName \n")
                .append("    AND REGDTIM     = :regDtim \n")
                .append("    AND FIELDNAME   = :fieldName \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName)
                .addValue("regDtim", regDtim)
                .addValue("fieldName", fieldName);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineItemResultMapper());

    }

    public List<OnlineItemResult> getProjectFieldItemResult (String projectName, String fieldName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" SELECT * \n")
                .append("   FROM TOT_ITEM_MEANPROBABILITY \n")
                .append("  WHERE PROJECTNAME = :projectName \n")
                .append("    AND FIELDNAME   = :fieldName \n")
                .append("  ORDER BY REGDTIM \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName)
                .addValue("fieldName", fieldName);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineItemResultMapper());

    }

    public List<OnlineResult> selectOnlinePredictResult (KeyInfo keyInfo) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * \n")
                .append("FROM PREDICT_ONLINE_RESULT \n")
                .append("WHERE PROJECTNAME=:projectName \n")
                .append("AND REGDTIM=:regDtim");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(keyInfo);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineResultMapper());
    }

    public List<OnlineRowResult> selectOnlineRowResult (KeyInfo keyInfo) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * \n")
                .append("FROM TOT_ROWPROB_STAT  \n")
                .append("WHERE PROJECTNAME=:projectName \n")
                .append("AND REGDTIM=:regDtim");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(keyInfo);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineRowResultMapper());
    }

    public List<OnlineItemResult> selectOnlineItemResult (KeyInfo keyInfo) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * \n")
                .append("FROM TOT_ITEM_MEANPROBABILITY   \n")
                .append("WHERE PROJECTNAME=:projectName \n")
                .append("AND REGDTIM=:regDtim");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(keyInfo);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineItemResultMapper());
    }

    public List<OnlineRowDistResult> selectOnlineRowDistResult (KeyInfo keyInfo) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * \n")
                .append("FROM TOT_ROWPROB_DIST   \n")
                .append("WHERE PROJECTNAME=:projectName \n")
                .append("AND REGDTIM=:regDtim");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(keyInfo);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineRowDistResultMapper());
    }

    public List<String> getRowDistProjectNames () {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME FROM TOT_ROWPROB_DIST \n")
                .append("GROUP BY PROJECTNAME ORDER BY PROJECTNAME ASC");

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public List<String> getRowDistProjectRegdtim (String projectName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT REGDTIM FROM TOT_ROWPROB_DIST \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" GROUP BY REGDTIM ORDER BY REGDTIM DESC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public List<OnlineRowDistResult> getDevRowDistResult (String projectName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME,PKGID,'' AS INPUTDATE, '' AS REGDTIM, \n")
                .append("PROB0TO5,PROB5TO10,PROB10TO15,PROB15TO20,PROB20TO25,PROB25TO30,PROB30TO35,PROB35TO40,PROB40TO45,PROB45TO50,PROB50TO55, \n")
                .append("PROB55TO60,PROB60TO65,PROB65TO70,PROB70TO75,PROB75TO80,PROB80TO85,PROB85TO90,PROB90TO95,PROB95TO100 \n")
                .append("FROM DEV_ROWPROB_DIST   \n")
                .append("WHERE PROJECTNAME=:projectName \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new OnlineRowDistResultMapper());
    }

    private static final class OnlineResultMapper implements RowMapper<OnlineResult> {
        @Override
        public OnlineResult mapRow (ResultSet rs, int rowNum) throws SQLException {
            OnlineResult onlineResult = new OnlineResult();
            onlineResult.setProjectName(rs.getString("PROJECTNAME"));
            onlineResult.setPkgId(rs.getString("PKGID"));
            onlineResult.setInputDate(rs.getString("INPUTDATE"));
            onlineResult.setRegDtim(rs.getString("REGDTIM"));
            onlineResult.setEndDtim(rs.getString("ENDDTIM"));
            onlineResult.setInputFileName(FilenameUtils.getName(rs.getString("INPUTFILENAME")));
            onlineResult.setDataCnt(rs.getLong("DATACNT"));
            onlineResult.setAnomalyMeanProb(rs.getDouble("ANOMALYMEANPROB"));
            onlineResult.setAnomalyRatio(rs.getDouble("ANOMALYRATIO"));
            onlineResult.setAnomalyItemCnt(rs.getInt("ANOMALYITEMCNT"));
            onlineResult.setAnomalyResult(rs.getInt("ANOMALYRESULT"));
            onlineResult.setComment(rs.getString("COMMENT"));

            return onlineResult;
        }
    }

    private static final class OnlineRowResultMapper implements RowMapper<OnlineRowResult> {
        @Override
        public OnlineRowResult mapRow (ResultSet rs, int rowNum) throws SQLException {
            OnlineRowResult onlineRowResult = new OnlineRowResult();
            onlineRowResult.setProjectName(rs.getString("PROJECTNAME"));
            onlineRowResult.setPkgId(rs.getString("PKGID"));
            onlineRowResult.setInputDate(rs.getString("INPUTDATE"));
            onlineRowResult.setRegDtim(rs.getString("REGDTIM"));
            onlineRowResult.setMeanProb(rs.getDouble("MEANPROB"));
            onlineRowResult.setMinProb(rs.getDouble("MINPROB"));
            onlineRowResult.setPcntl25Prob(rs.getDouble("PCNTL25PROB"));
            onlineRowResult.setMedianProb(rs.getDouble("MEDIANPROB"));
            onlineRowResult.setPcntl75Prob(rs.getDouble("PCNTL75PROB"));
            onlineRowResult.setPcntl90Prob(rs.getDouble("PCNTL90PROB"));
            onlineRowResult.setPcntl95Prob(rs.getDouble("PCNTL95PROB"));
            onlineRowResult.setPcntl99Prob(rs.getDouble("PCNTL99PROB"));
            onlineRowResult.setMaxProb(rs.getDouble("MAXPROB"));

            return onlineRowResult;
        }
    }

    private static final class OnlineItemResultMapper implements RowMapper<OnlineItemResult> {
        @Override
        public OnlineItemResult mapRow (ResultSet rs, int rowNum) throws SQLException {
            OnlineItemResult onlineItemResult = new OnlineItemResult();
            onlineItemResult.setProjectName(rs.getString("PROJECTNAME"));
            onlineItemResult.setPkgId(rs.getString("PKGID"));
            onlineItemResult.setInputDate(rs.getString("INPUTDATE"));
            onlineItemResult.setRegDtim(rs.getString("REGDTIM"));
            onlineItemResult.setFieldName(rs.getString("FIELDNAME"));
            onlineItemResult.setMeanProb(rs.getDouble("MEANPROBABILITY"));

            return onlineItemResult;
        }
    }

    private static final class OnlineRowDistResultMapper implements RowMapper<OnlineRowDistResult> {
        @Override
        public OnlineRowDistResult mapRow (ResultSet rs, int rowNum) throws SQLException {
            OnlineRowDistResult onlineRowDistResult = new OnlineRowDistResult();
            onlineRowDistResult.setProjectName(rs.getString("PROJECTNAME"));
            onlineRowDistResult.setPkgId(rs.getString("PKGID"));
            onlineRowDistResult.setInputDate(rs.getString("INPUTDATE"));
            onlineRowDistResult.setRegDtim(rs.getString("REGDTIM"));
            onlineRowDistResult.setProb0To5(rs.getDouble("PROB0TO5"));
            onlineRowDistResult.setProb5To10(rs.getDouble("PROB5TO10"));
            onlineRowDistResult.setProb10To15(rs.getDouble("PROB10TO15"));
            onlineRowDistResult.setProb15To20(rs.getDouble("PROB15TO20"));
            onlineRowDistResult.setProb20To25(rs.getDouble("PROB20TO25"));
            onlineRowDistResult.setProb25To30(rs.getDouble("PROB25TO30"));
            onlineRowDistResult.setProb30To35(rs.getDouble("PROB30TO35"));
            onlineRowDistResult.setProb35To40(rs.getDouble("PROB35TO40"));
            onlineRowDistResult.setProb40To45(rs.getDouble("PROB40TO45"));
            onlineRowDistResult.setProb45To50(rs.getDouble("PROB45TO50"));
            onlineRowDistResult.setProb50To55(rs.getDouble("PROB50TO55"));
            onlineRowDistResult.setProb55To60(rs.getDouble("PROB55TO60"));
            onlineRowDistResult.setProb60To65(rs.getDouble("PROB60TO65"));
            onlineRowDistResult.setProb65To70(rs.getDouble("PROB65TO70"));
            onlineRowDistResult.setProb70To75(rs.getDouble("PROB70TO75"));
            onlineRowDistResult.setProb75To80(rs.getDouble("PROB75TO80"));
            onlineRowDistResult.setProb80To85(rs.getDouble("PROB80TO85"));
            onlineRowDistResult.setProb85To90(rs.getDouble("PROB85TO90"));
            onlineRowDistResult.setProb90To95(rs.getDouble("PROB90TO95"));
            onlineRowDistResult.setProb95To100(rs.getDouble("PROB95TO100"));

            return onlineRowDistResult;
        }
    }

}
