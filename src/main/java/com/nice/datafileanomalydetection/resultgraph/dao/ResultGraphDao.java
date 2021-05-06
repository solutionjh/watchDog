/*
2020.03.18 kwb 
- DEV_MEANSQUAREDERORR 테이블과 TOT_MEANSQUAREDERORR 테이블에서 MEANSQUAREDERORR 컬럼과 RATIO 컬럼의 값을 가져옴
- 기존 ResultItemDao.java 활용
*/


package com.nice.datafileanomalydetection.resultgraph.dao;

import com.nice.datafileanomalydetection.resultgraph.model.ResultGraphModelMseNRatio;
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
public class ResultGraphDao {

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

    public List<String> getResultRegdtim (String projectName) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT REGDTIM FROM TOT_COL_MEANSQUAREDERROR \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" GROUP BY REGDTIM ORDER BY REGDTIM DESC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    // 기준 데이터의 mse값과 ratio값을 가져와 리스트로 return
    public List<ResultGraphModelMseNRatio> getDevMseNRatioList (String projectName) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT MEANSQUAREDERROR, RATIO FROM DEV_MEANSQUAREDERROR \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" ORDER BY MEANSQUAREDERROR ASC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ResultGraphModelMseNRatioMapper());
    }

    // 현재 데이터의 mse값과 ratio값을 가져와 리스트로 return
    public List<ResultGraphModelMseNRatio> getNowMseNRatioList (String projectName, String regdtim) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT MEANSQUAREDERROR, RATIO FROM TOT_MEANSQUAREDERROR \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" AND REGDTIM = :regdtim \n")
                .append(" ORDER BY MEANSQUAREDERROR ASC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regdtim", regdtim);

        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ResultGraphModelMseNRatioMapper());
    }

    private static final class ResultGraphModelMseNRatioMapper implements RowMapper<ResultGraphModelMseNRatio> {
        @Override
        public ResultGraphModelMseNRatio mapRow (ResultSet rs, int rowNum) throws SQLException {
            ResultGraphModelMseNRatio resultGraphMseNRatio = new ResultGraphModelMseNRatio();
            resultGraphMseNRatio.setMse(new BigDecimal(rs.getString("MEANSQUAREDERROR")).setScale(10, RoundingMode.HALF_EVEN).toPlainString());
            resultGraphMseNRatio.setRatio(new BigDecimal(rs.getString("RATIO")).setScale(10, RoundingMode.HALF_EVEN).toPlainString());
            return resultGraphMseNRatio;
        }
    }
}