package com.nice.datafileanomalydetection.resultitemdist.dao;

import com.nice.datafileanomalydetection.resultitemdist.model.ResultItemDist;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ResultItemDistDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getProjectNames () {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME FROM DEV_ITEM_DISTRIBUTION  \n")
                .append("GROUP BY PROJECTNAME ORDER BY PROJECTNAME ASC");

        return this.jdbcTemplate.queryForList(selectSql.toString(), String.class);
    }

    public List<String> getResultItemDistRegdtim (String projectName) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT REGDTIM FROM TOT_ITEM_DISTRIBUTION \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" GROUP BY REGDTIM ORDER BY REGDTIM DESC");


        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public List<ResultItemDist> getProjectItemDistResult (String projectName, String regdtim) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT FIELDNAME, ITEMVALUE, DEVPROBABILITY, NOWPROBABILITY, NOWPROBABILITY-DEVPROBABILITY AS DIFF \n")
                .append("FROM (SELECT A.FIELDNAME, A.ITEMVALUE, A.PROBABILITY*100 as DEVPROBABILITY, NVL(B.PROBABILITY*100,0) as NOWPROBABILITY \n")
                .append("      FROM DEV_ITEM_DISTRIBUTION A \n")
                .append("      LEFT OUTER JOIN TOT_ITEM_DISTRIBUTION B \n")
                .append("      ON A.PROJECTNAME    = B.PROJECTNAME \n")
                .append("      AND A.FIELDNAME     = B.FIELDNAME \n")
                .append("      AND A.ITEMVALUE     = B.ITEMVALUE \n")
                .append("      WHERE A.PROJECTNAME = :projectName \n")
                .append("      AND B.REGDTIM       = :regdtim \n")
                .append("      UNION \n")
                .append("      SELECT C.FIELDNAME, C.ITEMVALUE, NVL(D.PROBABILITY*100,0) as DEVPROBABILITY, C.PROBABILITY*100 as NOWPROBABILITY \n")
                .append("      FROM TOT_ITEM_DISTRIBUTION C \n")
                .append("      LEFT OUTER JOIN DEV_ITEM_DISTRIBUTION D \n")
                .append("      ON C.PROJECTNAME = D.PROJECTNAME \n")
                .append("      AND C.FIELDNAME   = D.FIELDNAME \n")
                .append("      AND C.ITEMVALUE = D.ITEMVALUE \n")
                .append("      WHERE C.PROJECTNAME = :projectName \n")
                .append("      AND C.REGDTIM     = :regdtim)");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regdtim", regdtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ResultItemDistMapper());
    }

    public List<String> getProjectRegdtimFieldNames (String projectName, String regdtim) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT DISTINCT FIELDNAME \n")
                .append("FROM TOT_ITEM_DISTRIBUTION \n")
                .append("WHERE PROJECTNAME = :projectName \n")
                .append("AND REGDTIM = :regdtim");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regdtim", regdtim);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public List<ResultItemDist> getProjectRegdtimFieldResult (String projectName, String regdtim, String fieldName) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT FIELDNAME, ITEMVALUE, DEVPROBABILITY, NOWPROBABILITY, NOWPROBABILITY-DEVPROBABILITY AS DIFF \n")
                .append("FROM (SELECT A.FIELDNAME, A.ITEMVALUE, A.PROBABILITY*100 as DEVPROBABILITY, NVL(B.PROBABILITY*100,0) as NOWPROBABILITY \n")
                .append("      FROM DEV_ITEM_DISTRIBUTION A \n")
                .append("      LEFT OUTER JOIN TOT_ITEM_DISTRIBUTION B \n")
                .append("      ON A.PROJECTNAME    = B.PROJECTNAME \n")
                .append("      AND A.FIELDNAME     = B.FIELDNAME \n")
                .append("      AND A.ITEMVALUE     = B.ITEMVALUE \n")
                .append("      WHERE A.PROJECTNAME = :projectName \n")
                .append("      AND B.REGDTIM       = :regdtim \n")
                .append("      AND A.FIELDNAME     = :fieldName \n")
                .append("      UNION \n")
                .append("      SELECT C.FIELDNAME, C.ITEMVALUE, NVL(D.PROBABILITY*100,0) as DEVPROBABILITY, C.PROBABILITY*100 as NOWPROBABILITY \n")
                .append("      FROM TOT_ITEM_DISTRIBUTION C \n")
                .append("      LEFT OUTER JOIN DEV_ITEM_DISTRIBUTION D \n")
                .append("      ON C.PROJECTNAME = D.PROJECTNAME \n")
                .append("      AND C.FIELDNAME  = D.FIELDNAME \n")
                .append("      AND C.ITEMVALUE  = D.ITEMVALUE \n")
                .append("      WHERE C.PROJECTNAME = :projectName \n")
                .append("      AND C.REGDTIM       = :regdtim")
                .append("      AND C.FIELDNAME     = :fieldName)");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regdtim", regdtim).addValue("fieldName", fieldName);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ResultItemDistMapper());
    }

    private static final class ResultItemDistMapper implements RowMapper<ResultItemDist> {
        @Override
        public ResultItemDist mapRow (ResultSet rs, int rowNum) throws SQLException {
            ResultItemDist resultitemdist = new ResultItemDist();

            resultitemdist.setFieldName(rs.getString("FIELDNAME"));
            resultitemdist.setItemValue(rs.getString("ITEMVALUE"));
            resultitemdist.setDevProbability(new BigDecimal(rs.getString("DEVPROBABILITY")).setScale(5, RoundingMode.HALF_EVEN).toPlainString());
            resultitemdist.setNowProbability(new BigDecimal(rs.getString("NOWPROBABILITY")).setScale(5, RoundingMode.HALF_EVEN).toPlainString());
            resultitemdist.setDiff(new BigDecimal(rs.getString("DIFF")).setScale(5, RoundingMode.HALF_EVEN).toPlainString());

            return resultitemdist;
        }
    }

}

