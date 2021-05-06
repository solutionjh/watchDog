package com.nice.datafileanomalydetection.predict.dao;

import com.nice.datafileanomalydetection.online.model.OnlineResult;
import com.nice.datafileanomalydetection.predict.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

@Repository
public class PredictDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertJob (PredictBatchInfo predictBatchInfo) {

        StringBuilder updateSql = new StringBuilder();
        updateSql.append("INSERT INTO PREDICT_JOB (PROJECTNAME, REGDTIM, JOBID, INPUTFILENAME, COLUMNCALULATEYN, INPUTDATACNT, ENDDTIM, COMMENT) \n");
        updateSql.append("       VALUES (:projectName, :regDtim, :jobId, :inputDataFile, :columnCalculateYN, :inputDataCount, :endDtim, :comment) \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(predictBatchInfo);

        int insertCnt = namedParameterJdbcTemplate.update(updateSql.toString(), namedParameters);

        if (insertCnt == 1) {
            logger.debug("Insert Job Success. {}", predictBatchInfo);
        } else {
            logger.warn("{} Rows are Updated. Please check Information", insertCnt);
            logger.debug("Insert Job Failed. {}", predictBatchInfo);
        }
    }

    public int updateJobStatus (PredictBatchInfo predictBatchInfo) {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE PREDICT_JOB\n")
                .append("   SET ENDDTIM     = :endDtim\n")
                .append("     , COMMENT     = :comment\n")
                .append("     , JOBGB       = :jobGb\n")
                .append("     , PSI         = :psi\n")
                .append("     , CAR         = :car\n")
                .append("     , CHISQCNT    = :chiSqCnt\n")
                .append(" WHERE PROJECTNAME = :projectName\n")
                .append("   AND REGDTIM     = :regDtim\n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(predictBatchInfo);

        return this.namedParameterJdbcTemplate.update(updateSql.toString(), namedParameters);
    }

    public int updateJobComment (PredictBatchInfo predictBatchInfo) {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE PREDICT_JOB\n")
                .append("   SET COMMENT     = :comment\n")
                .append(" WHERE PROJECTNAME = :projectName\n")
                .append("   AND REGDTIM     = :regDtim\n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(predictBatchInfo);

        return this.namedParameterJdbcTemplate.update(updateSql.toString(), namedParameters);
    }

    public int insertRowSquardError (RowSquaredErrorInfo rowSquardErrorInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO PREDICT_ROW_SQUAREDERROR (PROJECTNAME, REGDTIM, FIELDNAME, SQUAREDERROR, CNT)  \n")
                .append("                             VALUES (:projectName, :regDtim, :fieldName, :squaredError, :count)  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(rowSquardErrorInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int updateRowSquardError (RowSquaredErrorInfo rowSquardErrorInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("UPDATE PREDICT_ROW_SQUAREDERROR  \n")
                .append("   SET SQUAREDERROR = SQUAREDERROR + :squaredError \n")
                .append("     , CNT          = CNT + 1 \n")
                .append(" WHERE PROJECTNAME  = :projectName \n")
                .append("   AND REGDTIM      = :regDtim \n")
                .append("   AND FIELDNAME    = :fieldName \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(rowSquardErrorInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int deleteRowSquardError (RowSquaredErrorInfo rowSquardErrorInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("DELETE FROM PREDICT_ROW_SQUAREDERROR \n")
                .append("      WHERE PROJECTNAME=:projectName \n")
                .append("        AND REGDTIM=:regDtim  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(rowSquardErrorInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertRowMeanSquardError (RowMeanSquaredErrorInfo rowMeanSquardErrorInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO PREDICT_ROW_MEANSQUAREDERROR (PROJECTNAME, REGDTIM, MEANSQUAREDERROR)  \n")
                .append("                             VALUES (:projectName, :regDtim, :meanSquaredError)  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(rowMeanSquardErrorInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int deleteRowMeanSquardError (RowMeanSquaredErrorInfo rowMeanSquardErrorInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("DELETE FROM PREDICT_ROW_MEANSQUAREDERROR \n")
                .append("      WHERE PROJECTNAME = :projectName ")
                .append("        AND REGDTIM     = :regDtim ");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(rowMeanSquardErrorInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int truncateRowMeanSquardError () {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("TRUNCATE TABLE PREDICT_ROW_MEANSQUAREDERROR \n");

        return this.jdbcTemplate.update(insertSql.toString());
    }

    public int truncateRowSquardError () {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("TRUNCATE TABLE PREDICT_ROW_SQUAREDERROR \n");

        return this.jdbcTemplate.update(insertSql.toString());
    }

    public int insertDevMeanSquardError (String projectName, String regDtim) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" INSERT INTO DEV_MEANSQUAREDERROR  \n")
                .append(" SELECT PROJECTNAME, ROUND(MEANSQUAREDERROR ,3), COUNT(*) , COUNT(*) / SUM(COUNT(*)) OVER (PARTITION BY PROJECTNAME) * 100 \n")
                .append("   FROM PREDICT_ROW_MEANSQUAREDERROR  \n")
                .append("  WHERE PROJECTNAME  = :projectName  \n")
                .append("    AND REGDTIM      = :regDtim  \n")
                .append("  GROUP BY PROJECTNAME, ROUND(MEANSQUAREDERROR ,3)  \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertDevColMeanSquardError (String projectName, String regDtim) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" INSERT INTO DEV_COL_MEANSQUAREDERROR (PROJECTNAME ,FIELDNAME ,MEANSQUAREDERROR) \n")
                .append(" SELECT PROJECTNAME, FIELDNAME, (SQUAREDERROR/CNT) as MEANSQUAREDERROR \n")
                .append("   FROM PREDICT_ROW_SQUAREDERROR   \n")
                .append("  WHERE PROJECTNAME  = :projectName  \n")
                .append("    AND REGDTIM  = :regDtim  \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertDevItemDistribution (ItemDistInfo itemDistInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" INSERT INTO DEV_ITEM_DISTRIBUTION (PROJECTNAME, FIELDNAME, ITEMVALUE, CNT, PROBABILITY) \n")
                .append("                            VALUES (:projectName, :fieldName, :itemValue, :itemCount, :itemProb)  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(itemDistInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertTotItemDistribution (ItemDistInfo itemDistInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" INSERT INTO TOT_ITEM_DISTRIBUTION (PROJECTNAME, REGDTIM, FIELDNAME, ITEMVALUE, CNT, PROBABILITY) \n")
                .append("                            VALUES (:projectName, :regDtim, :fieldName, :itemValue, :itemCount, :itemProb)  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(itemDistInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int deleteDevMeanSquardError (String projectName) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" DELETE FROM DEV_MEANSQUAREDERROR WHERE PROJECTNAME = :projectName  \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int deleteDevColMeanSquardError (String projectName) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" DELETE FROM DEV_COL_MEANSQUAREDERROR WHERE PROJECTNAME = :projectName  \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int deleteDevItemDistribution (PredictInputInfo predictInputInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" DELETE FROM DEV_ITEM_DISTRIBUTION WHERE PROJECTNAME = :projectName  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(predictInputInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertTotMeanSquardError (String projectName, String regDtim) {
        logger.debug("Insert Total MSE.");
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" INSERT INTO TOT_MEANSQUAREDERROR  \n")
                .append(" SELECT PROJECTNAME, REGDTIM, ROUND(MEANSQUAREDERROR ,3), COUNT(*), COUNT(*) / SUM(COUNT(*)) OVER (PARTITION BY PROJECTNAME, REGDTIM) * 100  \n")
                .append("   FROM PREDICT_ROW_MEANSQUAREDERROR  \n")
                .append("  WHERE PROJECTNAME = :projectName  \n")
                .append("    AND REGDTIM     = :regDtim  \n")
                .append("  GROUP BY  PROJECTNAME, REGDTIM, ROUND(MEANSQUAREDERROR ,3)  \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertTotColMeanSquardError (String projectName, String regDtim) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" INSERT INTO TOT_COL_MEANSQUAREDERROR (PROJECTNAME ,REGDTIM ,FIELDNAME ,MEANSQUAREDERROR )  \n")
                .append(" SELECT PROJECTNAME, REGDTIM, FIELDNAME, (SQUAREDERROR/CNT) as MEANSQUAREDERROR  \n")
                .append("   FROM PREDICT_ROW_SQUAREDERROR  \n")
                .append("  WHERE PROJECTNAME = :projectName  \n")
                .append("    AND REGDTIM     = :regDtim  \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);

    }

    public int deleteTotMeanSquardError (String projectName, String regDtim) {
        logger.debug("Delete Total MSE.");
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" DELETE FROM TOT_MEANSQUAREDERROR WHERE PROJECTNAME = :projectName AND REGDTIM  = :regDtim \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.update(deleteSql.toString(), namedParameters);
    }

    public int deleteTotItemDistribution (String projectName, String regDtim) {
        logger.info("항목 분포 기존 결과정보 삭제.");
        logger.debug("Delete Total Item Distribution.");
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" DELETE FROM TOT_ITEM_DISTRIBUTION WHERE PROJECTNAME = :projectName AND REGDTIM  = :regDtim \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.update(deleteSql.toString(), namedParameters);

    }

    public int deleteTotColMeanSquardError (String projectName, String regDtim) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" DELETE FROM TOT_COL_MEANSQUAREDERROR WHERE PROJECTNAME = :projectName AND REGDTIM  = :regDtim \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.update(deleteSql.toString(), namedParameters);
    }

    public double calculatePSI (String projectName, String regDtim) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SUM(X.RATIODIFF) as PSI \n")
                .append("  FROM ( \n")
                .append("        SELECT A.MEANSQUAREDERROR, ABS(A.RATIO - IFNULL(B.RATIO, 0)) AS RATIODIFF \n")
                .append("          FROM DEV_MEANSQUAREDERROR A \n")
                .append("          LEFT JOIN TOT_MEANSQUAREDERROR B \n")
                .append("            ON B.PROJECTNAME      = :projectName \n")
                .append("           AND B.REGDTIM          = :regDtim \n")
                .append("           AND A.MEANSQUAREDERROR = B.MEANSQUAREDERROR \n")
                .append("         WHERE A.PROJECTNAME      = :projectName \n")
                .append("         UNION  \n")
                .append("        SELECT A.MEANSQUAREDERROR, ABS(IFNULL(B.RATIO, 0) - A.RATIO) AS RATIODIFF \n")
                .append("          FROM TOT_MEANSQUAREDERROR A \n")
                .append("          LEFT JOIN DEV_MEANSQUAREDERROR B \n")
                .append("            ON B.PROJECTNAME      = :projectName \n")
                .append("           AND A.MEANSQUAREDERROR = B.MEANSQUAREDERROR  \n")
                .append("         WHERE A.PROJECTNAME      = :projectName \n")
                .append("           AND A.REGDTIM          = :regDtim \n")
                .append("       ) X \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim);

        return this.namedParameterJdbcTemplate.queryForObject(sql.toString(), namedParameters, Double.class);
    }

    // refs #1360 항목값이 상수일 경우, 기준정보의 MEANSQUAREDERROR값이 0.0으로 계산되어 발생하는 division by zero 오류처리 (2020-11-09, kwb)
    public int calculateCAR (String projectName, String regDtim, String carThreshold) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT(*) as CARCNT \n")
                .append("  FROM ( \n")
                .append("        SELECT A.FIELDNAME, CASEWHEN(A.MEANSQUAREDERROR = '0.0', CASEWHEN(B.MEANSQUAREDERROR = '0.0', '0.0', '1000.0'), ABS(ROUND((B.MEANSQUAREDERROR - A.MEANSQUAREDERROR) / A.MEANSQUAREDERROR * 100, 4))) as CHANGERATE \n")
                .append("          FROM DEV_COL_MEANSQUAREDERROR A \n")
                .append("          LEFT OUTER JOIN TOT_COL_MEANSQUAREDERROR  B \n")
                .append("            ON A.PROJECTNAME = B.PROJECTNAME \n")
                .append("           AND B.REGDTIM     = :regDtim \n")
                .append("           AND A.FIELDNAME   = B.FIELDNAME \n")
                .append("         WHERE A.PROJECTNAME = :projectName \n")
                .append("       ) X \n")
                .append(" WHERE CHANGERATE > :carThreshold \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim).addValue("carThreshold", carThreshold);

        return this.namedParameterJdbcTemplate.queryForObject(sql.toString(), namedParameters, Integer.class);
    }

    public Map<String, Object> calculateChiSqStatistic (ItemDistInfo itemDistInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUM(CHISQ) as CHISQSUM, SUM(CASE WHEN CHISQ IS NULL THEN 1 ELSE 0 END) AS NEWVALCNT \n")
                .append("FROM ( \n")
                .append("      SELECT X.ITEMVALUE, ((X.NOWCNT - X.EXPECTATION) * (X.NOWCNT - X.EXPECTATION)) / NULLIF(X.EXPECTATION,0) as CHISQ \n")
                .append("      FROM ( \n")
                .append("            SELECT PROJECTNAME, FIELDNAME, ITEMVALUE, NOWCNT, (DEVPROBABILITY * :totalDataCnt) AS EXPECTATION \n")
                .append("            FROM (SELECT A.PROJECTNAME, A.FIELDNAME, A.ITEMVALUE, B.CNT AS NOWCNT, A.PROBABILITY as DEVPROBABILITY \n")
                .append("                    FROM DEV_ITEM_DISTRIBUTION A \n")
                .append("                    LEFT OUTER JOIN TOT_ITEM_DISTRIBUTION B \n")
                .append("                      ON A.PROJECTNAME = B.PROJECTNAME \n")
                .append("                     AND A.FIELDNAME   = B.FIELDNAME \n")
                .append("                     AND A.ITEMVALUE   = B.ITEMVALUE \n")
                .append("                   WHERE A.PROJECTNAME = :projectName \n")
                .append("                     AND B.REGDTIM     = :regDtim \n")
                .append("                     AND A.FIELDNAME   = :fieldName \n")
                .append("                  UNION \n")
                .append("                  SELECT C.PROJECTNAME, C.FIELDNAME, C.ITEMVALUE, C.CNT AS NOWCNT, NVL(D.PROBABILITY,0) as DEVPROBABILITY \n")
                .append("                    FROM TOT_ITEM_DISTRIBUTION C \n")
                .append("                    LEFT OUTER JOIN DEV_ITEM_DISTRIBUTION D \n")
                .append("                      ON C.PROJECTNAME = D.PROJECTNAME \n")
                .append("                     AND C.FIELDNAME   = D.FIELDNAME \n")
                .append("                     AND C.ITEMVALUE   = D.ITEMVALUE \n")
                .append("                   WHERE C.PROJECTNAME = :projectName \n")
                .append("                     AND C.REGDTIM     = :regDtim \n")
                .append("                     AND C.FIELDNAME   = :fieldName) \n")
                .append("           ) X \n")
                .append("    )");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(itemDistInfo);

        return this.namedParameterJdbcTemplate.queryForMap(sql.toString(), namedParameters);
    }

    public List<String> checkMeta (String projectName) {

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT PROJECTNAME FROM DEV_MEANSQUAREDERROR \n")
                .append(" WHERE PROJECTNAME = :projectName \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public int insertChisqResult (ChisqInfo chisqInfo) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append(" INSERT INTO PREDICT_CHISQ (PROJECTNAME, REGDTIM, FIELDNAME, DOF, CHISQSTAT, PVALUE, NEWVALUECNT, ANOMALYRSLT) \n")
                .append("                            VALUES (:projectName, :regDtim, :fieldName, :dof, :chisqStat, :pValue, :newValueCnt, :anomalyRslt)  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(chisqInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertTrainDataSummary (ModelDataInfo modelDataInfo) {
        StringBuilder insertSql = new StringBuilder();

        insertSql.append("INSERT INTO MODEL_DATA_SUMMARY (PROJECTNAME,FIELDNAME,FIELDTYPE,MODELUSE,MEAN,STANDARD_DEVIATION,MINIMUM,LOWER_QUANTILE,MEDIAN,UPPER_QUANTILE,MAXIMUM,IQR,NA_COUNT,NONZERO_COUNT,MODE) \n")
                .append("                      VALUES (:projectName,:fieldName,:fieldType,:modelUse,:mean,:stddev,:minimum,:lq,:median,:uq,:maximum,:iqr,:naCnt,:nzCnt, :mode) \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(modelDataInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int deleteTrainDataSummary (String projectName) {
        StringBuilder deleteSql = new StringBuilder();

        deleteSql.append("DELETE FROM MODEL_DATA_SUMMARY \n")
                .append("      WHERE PROJECTNAME = :projectName \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.update(deleteSql.toString(), namedParameters);
    }

    public int updateTrainDataSummary (ModelDataInfo modelDataInfo) {
        StringBuilder updateSql = new StringBuilder();

        updateSql.append("UPDATE MODEL_DATA_SUMMARY \n")
                .append("   SET MODELUSE    = :modelUse \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append("   AND FIELDNAME   = :fieldName \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(modelDataInfo);

        return this.namedParameterJdbcTemplate.update(updateSql.toString(), namedParameters);
    }

    public List<String> selectModelFields (String projectName) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT FIELDNAME FROM MODEL_DATA_SUMMARY \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append("   AND MODELUSE    = 'Y' \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public int insertRowMeanProb (RowMeanProbInfo rowMeanProbability) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO PREDICT_ROW_MEANPROBABILITY (PROJECTNAME, REGDTIM, MEANPROBABILITY)  \n")
                .append("                             VALUES (:projectName, :regDtim, :rowMeanProbability)  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(rowMeanProbability);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int truncateRowMeanProb () {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("TRUNCATE TABLE PREDICT_ROW_MEANPROBABILITY \n");

        return this.jdbcTemplate.update(insertSql.toString());
    }

    public Map<String, Object> calculateAnomalyRate (String projectName, String regDtim, double probThreshold) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT SUM(CASE WHEN MEANPROBABILITY > :probThreshold THEN 1 ELSE 0 END) / CAST(COUNT(*) AS DOUBLE) AS ANOMALY_RATIO\n")
                .append("     , AVG(MEANPROBABILITY) AS ANOMALY_MEANPROB \n")
                .append("  FROM PREDICT_ROW_MEANPROBABILITY \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append("   AND REGDTIM     = :regDtim");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim)
                .addValue("probThreshold", probThreshold);

        return this.namedParameterJdbcTemplate.queryForMap(selectSql.toString(), namedParameters);
    }

    public int insertItemSEMeanProb (String projectName, String pkgId, String inputDate, String regDtim, String fieldName, double meanSEProb) {
        StringBuilder insertSql = new StringBuilder();

        insertSql.append("INSERT INTO TOT_ITEM_MEANPROBABILITY (PROJECTNAME,PKGID,INPUTDATE, REGDTIM, FIELDNAME, MEANPROBABILITY) \n")
                .append("                                  VALUES (:projectName,:pkgId,:inputDate, :regDtim, :fieldName, :meanSEProb) \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim)
                .addValue("fieldName", fieldName)
                .addValue("meanSEProb", meanSEProb)
                .addValue("pkgId", pkgId)
                .addValue("inputDate", inputDate);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public List<String> checkItemAnomaly (String projectName, String regDtim, double probThreshold) {
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("SELECT FIELDNAME \n")
                .append("  FROM TOT_ITEM_MEANPROBABILITY \n")
                .append(" WHERE PROJECTNAME     = :projectName \n")
                .append("   AND REGDTIM         = :regDtim \n")
                .append("   AND MEANPROBABILITY > :probThreshold \n");

        SqlParameterSource namedParameters = new MapSqlParameterSource("projectName", projectName).addValue("regDtim", regDtim)
                .addValue("probThreshold", probThreshold);

        return this.namedParameterJdbcTemplate.queryForList(selectSql.toString(), namedParameters, String.class);
    }

    public int insertRowProbStat (KeyInfo keyInfo) {
        StringBuilder insertSql = new StringBuilder();

        insertSql.append("INSERT INTO TOT_ROWPROB_STAT (PROJECTNAME,PKGID,INPUTDATE,REGDTIM,MEANPROB,MINPROB,PCNTL25PROB,MEDIANPROB,PCNTL75PROB,PCNTL90PROB,PCNTL95PROB,PCNTL99PROB,MAXPROB)\n")
                .append("SELECT PROJECTNAME,:pkgId AS PKGID, :inputDate AS INPUTDATE, REGDTIM, \n")
                .append("AVG(MEANPROBABILITY) AS MEANPROB, \n")
                .append("MIN(MEANPROBABILITY) AS MINPROB, \n")
                .append("PERCENTILE_DISC(0.25) WITHIN GROUP (ORDER BY MEANPROBABILITY) AS PCNTL25PROB, \n")
                .append("MEDIAN(MEANPROBABILITY) AS MEDIANPROB, \n")
                .append("PERCENTILE_DISC(0.75) WITHIN GROUP (ORDER BY MEANPROBABILITY) AS PCNTL75PROB, \n")
                .append("PERCENTILE_DISC(0.9) WITHIN GROUP (ORDER BY MEANPROBABILITY) AS PCNTL90PROB, \n")
                .append("PERCENTILE_DISC(0.95) WITHIN GROUP (ORDER BY MEANPROBABILITY) AS PCNTL95PROB, \n")
                .append("PERCENTILE_DISC(0.99) WITHIN GROUP (ORDER BY MEANPROBABILITY) AS PCNTL99PROB, \n")
                .append("MAX(MEANPROBABILITY) AS MAXPROB \n")
                .append("  FROM PREDICT_ROW_MEANPROBABILITY \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append("   AND REGDTIM     = :regDtim \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(keyInfo);
        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertRowProbDist (KeyInfo keyInfo) {
        StringBuilder insertSql = new StringBuilder();

        insertSql.append("INSERT INTO TOT_ROWPROB_DIST (PROJECTNAME,PKGID,INPUTDATE,REGDTIM,PROB0TO5,PROB5TO10,PROB10TO15,PROB15TO20,PROB20TO25,PROB25TO30,PROB30TO35,PROB35TO40,PROB40TO45,PROB45TO50,PROB50TO55,PROB55TO60,PROB60TO65,PROB65TO70,PROB70TO75,PROB75TO80,PROB80TO85,PROB85TO90,PROB90TO95,PROB95TO100) \n")
                .append("SELECT PROJECTNAME,:pkgId AS PKGID,:inputDate AS INPUTDATE,REGDTIM,")
                .append("COUNT(CASE WHEN MEANPROBABILITY >=0.0 AND MEANPROBABILITY <= 0.05 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB0TO5, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.05 AND MEANPROBABILITY <= 0.10 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB5TO10, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.1 AND MEANPROBABILITY <= 0.15 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB10TO15, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.15 AND MEANPROBABILITY <= 0.2 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB15TO20, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.2 AND MEANPROBABILITY <= 0.25 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB20TO25, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.25 AND MEANPROBABILITY <= 0.3 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB25TO30, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.3 AND MEANPROBABILITY <= 0.35 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB30TO35, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.35 AND MEANPROBABILITY <= 0.4 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB35TO40, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.4 AND MEANPROBABILITY <= 0.45 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB40TO45, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.45 AND MEANPROBABILITY <= 0.5 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB45TO50, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.5 AND MEANPROBABILITY <= 0.55 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB50TO55, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.55 AND MEANPROBABILITY <= 0.6 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB55TO60, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.6 AND MEANPROBABILITY <= 0.65 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB60TO65, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.65 AND MEANPROBABILITY <= 0.7 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB65TO70, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.7 AND MEANPROBABILITY <= 0.75 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB70TO75, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.75 AND MEANPROBABILITY <= 0.8 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB75TO80, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.8 AND MEANPROBABILITY <= 0.85 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB80TO85, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.85 AND MEANPROBABILITY <= 0.9 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB85TO90, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.9 AND MEANPROBABILITY <= 0.95 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB90TO95, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.95 AND MEANPROBABILITY <= 1.0 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB95TO100 \n")
                .append("FROM PREDICT_ROW_MEANPROBABILITY \n")
                .append("  WHERE PROJECTNAME =:projectName \n")
                .append("    AND REGDTIM     =:regDtim \n")
                .append("  GROUP BY PROJECTNAME, REGDTIM \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(keyInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertOnlineResult (OnlineResult onlineResult) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO PREDICT_ONLINE_RESULT (PROJECTNAME,PKGID,INPUTDATE,MODELITEMCNT,REGDTIM,ENDDTIM,INPUTFILENAME,DATACNT,ANOMALYMEANPROB,ANOMALYRATIO,ANOMALYITEMCNT,ANOMALYRESULT,COMMENT)  \n")
                .append("                           VALUES (:projectName,:pkgId,:inputDate,:modelItemCnt, :regDtim, :endDtim, :inputFileName, :dataCnt, :anomalyMeanProb, :anomalyRatio, :anomalyItemCnt, :anomalyResult, :comment)  \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(onlineResult);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int insertDevRowProbDist (KeyInfo keyInfo) {
        StringBuilder insertSql = new StringBuilder();

        insertSql.append("INSERT INTO DEV_ROWPROB_DIST (PROJECTNAME,PKGID,PROB0TO5,PROB5TO10,PROB10TO15,PROB15TO20,PROB20TO25,PROB25TO30,PROB30TO35,PROB35TO40,PROB40TO45,PROB45TO50,PROB50TO55,PROB55TO60,PROB60TO65,PROB65TO70,PROB70TO75,PROB75TO80,PROB80TO85,PROB85TO90,PROB90TO95,PROB95TO100) \n")
                .append("SELECT PROJECTNAME,:pkgId as PKGID,")
                .append("COUNT(CASE WHEN MEANPROBABILITY >=0.0  AND MEANPROBABILITY <= 0.05 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB0TO5, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.05 AND MEANPROBABILITY <= 0.10 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB5TO10, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.1  AND MEANPROBABILITY <= 0.15 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB10TO15, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.15 AND MEANPROBABILITY <= 0.2  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB15TO20, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.2  AND MEANPROBABILITY <= 0.25 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB20TO25, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.25 AND MEANPROBABILITY <= 0.3  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB25TO30, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.3  AND MEANPROBABILITY <= 0.35 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB30TO35, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.35 AND MEANPROBABILITY <= 0.4  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB35TO40, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.4  AND MEANPROBABILITY <= 0.45 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB40TO45, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.45 AND MEANPROBABILITY <= 0.5  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB45TO50, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.5  AND MEANPROBABILITY <= 0.55 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB50TO55, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.55 AND MEANPROBABILITY <= 0.6  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB55TO60, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.6  AND MEANPROBABILITY <= 0.65 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB60TO65, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.65 AND MEANPROBABILITY <= 0.7  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB65TO70, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.7  AND MEANPROBABILITY <= 0.75 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB70TO75, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.75 AND MEANPROBABILITY <= 0.8  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB75TO80, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.8  AND MEANPROBABILITY <= 0.85 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB80TO85, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.85 AND MEANPROBABILITY <= 0.9  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB85TO90, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.9  AND MEANPROBABILITY <= 0.95 THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB90TO95, \n")
                .append("COUNT(CASE WHEN MEANPROBABILITY > 0.95 AND MEANPROBABILITY <= 1.0  THEN 1 END) / CAST(COUNT(*) AS DOUBLE) AS PROB95TO100 \n")
                .append("  FROM PREDICT_ROW_MEANPROBABILITY \n")
                .append(" WHERE PROJECTNAME = :projectName \n")
                .append(" GROUP BY PROJECTNAME \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(keyInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }

    public int deleteDevRowProbDist (KeyInfo keyInfo) {
        StringBuilder insertSql = new StringBuilder();

        insertSql.append("DELETE FROM DEV_ROWPROB_DIST \n")
                .append(" WHERE PROJECTNAME = :projectName \n");

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(keyInfo);

        return this.namedParameterJdbcTemplate.update(insertSql.toString(), namedParameters);
    }
}
