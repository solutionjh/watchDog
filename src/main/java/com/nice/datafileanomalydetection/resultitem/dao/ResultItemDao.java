package com.nice.datafileanomalydetection.resultitem.dao;

import com.nice.datafileanomalydetection.resultitem.model.ResultItem;
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
public class ResultItemDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getResultItemRegdtim (String projectName) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT REGDTIM FROM TOT_COL_MEANSQUAREDERROR \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" GROUP BY REGDTIM ORDER BY REGDTIM DESC");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    // refs #1360 항목값이 상수일 경우, 기준정보의 MEANSQUAREDERROR값이 0.0으로 계산되어 발생하는 division by zero 오류처리 (2020-11-09, kwb)
    public List<ResultItem> getProjectItemResult (String projectName, String regdtim, String changeRate) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" SELECT * FROM ( SELECT A.FIELDNAME, A.MEANSQUAREDERROR AS DEVMSE, B.MEANSQUAREDERROR AS NOWMSE , CASEWHEN(A.MEANSQUAREDERROR = '0.0', CASEWHEN(B.MEANSQUAREDERROR = '0.0', '0.0', '1000.0'), ABS(ROUND((B.MEANSQUAREDERROR - A.MEANSQUAREDERROR) / A.MEANSQUAREDERROR * 100, 4))) as CHANGERATE \n")
                .append("   FROM DEV_COL_MEANSQUAREDERROR A \n")
                .append("   LEFT OUTER JOIN TOT_COL_MEANSQUAREDERROR  B \n")
                .append("     ON A.PROJECTNAME = B.PROJECTNAME \n")
                .append("    AND B.REGDTIM     = :regdtim \n")
                .append("    AND A.FIELDNAME   = B.FIELDNAME  \n")
                .append("  WHERE A.PROJECTNAME = :projectName \n")
                .append("  ORDER BY CHANGERATE  DESC )A \n");
                if(!changeRate.equals("0")) selectSql.append("  WHERE CHANGERATE > :changeRate ");
        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regdtim", regdtim).addValue("changeRate", changeRate);
        
        return this.namedParameterJdbcTemplate.query(selectSql.toString(), namedParameters, new ResultItemMapper());
    }

    private static final class ResultItemMapper implements RowMapper<ResultItem> {

        @Override
        public ResultItem mapRow (ResultSet rs, int rowNum) throws SQLException {
            ResultItem resultitem = new ResultItem();

            resultitem.setFieldName(rs.getString("FIELDNAME"));
            resultitem.setDevMse(new BigDecimal(rs.getString("DEVMSE")).setScale(10, RoundingMode.HALF_EVEN).toPlainString());
            resultitem.setNowMse(new BigDecimal(rs.getString("NOWMSE")).setScale(10, RoundingMode.HALF_EVEN).toPlainString());
            resultitem.setChangeRate(rs.getString("CHANGERATE"));

            return resultitem;
        }
    }

}
