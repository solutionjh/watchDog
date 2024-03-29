CREATE TABLE BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;

CREATE TABLE BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	START_TIME TIMESTAMP DEFAULT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	TYPE_CD VARCHAR(6) NOT NULL ,
	KEY_NAME VARCHAR(100) NOT NULL ,
	STRING_VAL VARCHAR2(65535) ,
	DATE_VAL TIMESTAMP DEFAULT NULL ,
	LONG_VAL BIGINT ,
	DOUBLE_VAL DOUBLE PRECISION ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	START_TIME TIMESTAMP NOT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT LONGVARCHAR ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT LONGVARCHAR ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ;
CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ;
CREATE SEQUENCE BATCH_JOB_SEQ;

-- Anomaly Detection Job Management Table
CREATE TABLE PREDICT_JOB (
    PROJECTNAME       VARCHAR2(100) NOT NULL,
    REGDTIM           VARCHAR2(20)  NOT NULL,
    JOBID             VARCHAR2(10),
    JOBGB             VARCHAR2(10),
    INPUTFILENAME     VARCHAR2(255) NOT NULL,
    COLUMNCALULATEYN  VARCHAR2(10)  NOT NULL,
    INPUTDATACNT      LONG(10)      NOT NULL,
    ENDDTIM           VARCHAR2(20),
    PSI               VARCHAR2(20),
    CAR               VARCHAR2(20),
    CHISQCNT          VARCHAR2(20),
    COMMENT           VARCHAR2(255),
    PRIMARY KEY(PROJECTNAME, REGDTIM),
    constraint PREDICT_JOB_UN unique (PROJECTNAME, REGDTIM)
) ;

CREATE TABLE PREDICT_ROW_SQUAREDERROR (
    PROJECTNAME       VARCHAR2(100) NOT NULL,
    REGDTIM           VARCHAR2(20)  NOT NULL,
    FIELDNAME         VARCHAR2(100)  NOT NULL,
    SQUAREDERROR       DOUBLE  NOT NULL,
    CNT       DOUBLE  NOT NULL,
    PRIMARY KEY(PROJECTNAME, REGDTIM, FIELDNAME),
    constraint PREDICT_ROW_SQUAREDERROR_UN unique (PROJECTNAME, REGDTIM, FIELDNAME)
) ;


CREATE TABLE TOT_COL_MEANSQUAREDERROR (
    PROJECTNAME       VARCHAR2(100) NOT NULL,
    REGDTIM           VARCHAR2(20)  NOT NULL,
    FIELDNAME         VARCHAR2(100)  NOT NULL,
    MEANSQUAREDERROR   DOUBLE  NOT NULL
) ;

CREATE INDEX IDX_TOT_COL_MEANSQUAREDERROR ON TOT_COL_MEANSQUAREDERROR(PROJECTNAME, REGDTIM, FIELDNAME);

CREATE TABLE PREDICT_ROW_MEANSQUAREDERROR (
    PROJECTNAME       VARCHAR2(100) NOT NULL,
    REGDTIM           VARCHAR2(20)  NOT NULL,
    MEANSQUAREDERROR   DOUBLE  NOT NULL
) ;

CREATE INDEX IDX_PREDICT_ROW_MEANSQUAREDERROR ON PREDICT_ROW_MEANSQUAREDERROR(PROJECTNAME, REGDTIM);

CREATE TABLE DEV_MEANSQUAREDERROR (
    PROJECTNAME        VARCHAR2(100) NOT NULL,
    MEANSQUAREDERROR   DOUBLE        NOT NULL,
    CNT                LONG(10)      NOT NULL,
    RATIO              DOUBLE        NOT NULL,
    PRIMARY KEY(PROJECTNAME, MEANSQUAREDERROR),
    constraint DEV_MEANSQUAREDERROR_UN unique (PROJECTNAME, MEANSQUAREDERROR)    
) ;

CREATE TABLE DEV_COL_MEANSQUAREDERROR (
    PROJECTNAME       VARCHAR2(100) NOT NULL,
    FIELDNAME         VARCHAR2(100)  NOT NULL,
    MEANSQUAREDERROR   DOUBLE  NOT NULL,
    PRIMARY KEY(PROJECTNAME, FIELDNAME),
    constraint DEV_COL_MEANSQUAREDERROR_UN unique (PROJECTNAME, FIELDNAME)    
) ;

CREATE TABLE TOT_MEANSQUAREDERROR (
    PROJECTNAME        VARCHAR2(100) NOT NULL,
    REGDTIM            VARCHAR2(20)  NOT NULL,
    MEANSQUAREDERROR   DOUBLE        NOT NULL,
    CNT                LONG(10)      NOT NULL,
    RATIO              DOUBLE        NOT NULL,
    PRIMARY KEY(PROJECTNAME, REGDTIM, MEANSQUAREDERROR),
    constraint TOT_MEANSQUAREDERROR_UN unique (PROJECTNAME, REGDTIM, MEANSQUAREDERROR)    
) ;

CREATE TABLE DEV_ITEM_DISTRIBUTION (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	FIELDNAME          VARCHAR2(100) NOT NULL,
	ITEMVALUE          VARCHAR2(100) NOT NULL,
	CNT                LONG(10)      NOT NULL,
	PROBABILITY        DOUBLE        NOT NULL,
	PRIMARY KEY(PROJECTNAME, FIELDNAME, ITEMVALUE),
	constraint DEV_ITEM_DISTRIBUTION_UN unique (PROJECTNAME, FIELDNAME, ITEMVALUE)
) ;

CREATE TABLE TOT_ITEM_DISTRIBUTION (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	REGDTIM            VARCHAR2(20)  NOT NULL,
	FIELDNAME          VARCHAR2(100) NOT NULL,
	ITEMVALUE          VARCHAR2(100) NOT NULL,
	CNT                LONG(10)      NOT NULL,
	PROBABILITY        DOUBLE        NOT NULL,
	PRIMARY KEY(PROJECTNAME, REGDTIM, FIELDNAME, ITEMVALUE),
	constraint TOT_ITEM_DISTRIBUTION_UN unique (PROJECTNAME, REGDTIM, FIELDNAME, ITEMVALUE)
) ;

CREATE TABLE PREDICT_CHISQ (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	REGDTIM            VARCHAR2(20)  NOT NULL,
	FIELDNAME          VARCHAR2(100) NOT NULL,
	DOF                LONG(10)      NOT NULL,
	CHISQSTAT          DOUBLE        NOT NULL,
	PVALUE             DOUBLE        NOT NULL,
	NEWVALUECNT        LONG(10)      NOT NULL,
	ANOMALYRSLT        INT           NOT NULL,
	PRIMARY KEY(PROJECTNAME, REGDTIM, FIELDNAME),
	constraint PREDICT_CHISQ_UN unique (PROJECTNAME, REGDTIM, FIELDNAME)
) ;

CREATE TABLE MODEL_DATA_SUMMARY (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	FIELDNAME          VARCHAR2(100) NOT NULL,
	FIELDTYPE          VARCHAR2(20)  NOT NULL,
	MODELUSE           VARCHAR2(1)   NOT NULL,
	MEAN               DOUBLE        NOT NULL,
	STANDARD_DEVIATION DOUBLE        NOT NULL,
	MINIMUM            DOUBLE        NOT NULL,
	LOWER_QUANTILE     DOUBLE        NOT NULL,
	MEDIAN             DOUBLE        NOT NULL,
	UPPER_QUANTILE     DOUBLE        NOT NULL,
	MAXIMUM            DOUBLE        NOT NULL,
	IQR                DOUBLE        NOT NULL,
	NA_COUNT           LONG(10)      NOT NULL,
	NONZERO_COUNT      LONG(10)      NOT NULL,
	MODE               VARCHAR2(20)  NOT NULL,
	PRIMARY KEY(PROJECTNAME, FIELDNAME),
	CONSTRAINT MODEL_DATA_SUMMARY_UN UNIQUE (PROJECTNAME, FIELDNAME)
) ;

CREATE TABLE PREDICT_DATA_SUMMARY (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	REGDTIM            VARCHAR2(20)  NOT NULL,
	FIELDNAME          VARCHAR2(100) NOT NULL,
	FIELDTYPE          VARCHAR2(20)  NOT NULL,
	MEAN               DOUBLE        NOT NULL,
	STANDARD_DEVIATION DOUBLE        NOT NULL,
	MINIMUM            DOUBLE        NOT NULL,
	LOWER_QUANTILE     DOUBLE        NOT NULL,
	MEDIAN             DOUBLE        NOT NULL,
	UPPER_QUANTILE     DOUBLE        NOT NULL,
	MAXIMUM            DOUBLE        NOT NULL,
	IQR                DOUBLE        NOT NULL,
	NA_COUNT           LONG(10)      NOT NULL,
	NONZERO_COUNT      LONG(10)      NOT NULL,
	MODE               VARCHAR2(20)  NOT NULL,
	PRIMARY KEY(PROJECTNAME, REGDTIM, FIELDNAME),
	CONSTRAINT PREDICT_DATA_SUMMARY_UN UNIQUE (PROJECTNAME, REGDTIM, FIELDNAME)
) ;

CREATE TABLE PREDICT_ROW_MEANPROBABILITY (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	REGDTIM            VARCHAR2(20)  NOT NULL,
	MEANPROBABILITY    DOUBLE        NOT NULL
) ;

CREATE TABLE TOT_ITEM_MEANPROBABILITY (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	PKGID              VARCHAR2(100) NOT NULL,
	INPUTDATE          VARCHAR2(20)  NOT NULL,
	REGDTIM            VARCHAR2(20)  NOT NULL,
	FIELDNAME          VARCHAR2(100) NOT NULL,
	MEANPROBABILITY    DOUBLE        NOT NULL,
	PRIMARY KEY(PROJECTNAME, REGDTIM, FIELDNAME),
	CONSTRAINT TOT_ITEM_MEANPROBABILITY_UN UNIQUE (PROJECTNAME, REGDTIM, FIELDNAME)
) ;

CREATE TABLE TOT_ROWPROB_STAT (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	PKGID              VARCHAR2(100) NOT NULL,
	INPUTDATE          VARCHAR2(20)  NOT NULL,
	REGDTIM            VARCHAR2(20)  NOT NULL,
	MEANPROB           DOUBLE        NOT NULL,
	MINPROB            DOUBLE        NOT NULL,
	PCNTL25PROB        DOUBLE        NOT NULL,
	MEDIANPROB         DOUBLE        NOT NULL,
	PCNTL75PROB        DOUBLE        NOT NULL,
	PCNTL90PROB        DOUBLE        NOT NULL,
	PCNTL95PROB        DOUBLE        NOT NULL,
	PCNTL99PROB        DOUBLE        NOT NULL,		
	MAXPROB            DOUBLE        NOT NULL,
	PRIMARY KEY(PROJECTNAME, REGDTIM),
	CONSTRAINT TOT_ROWPROB_STAT_UN UNIQUE (PROJECTNAME, REGDTIM)
);

CREATE TABLE PREDICT_ONLINE_RESULT (
    PROJECTNAME        VARCHAR2(100) NOT NULL,
    PKGID              VARCHAR2(100) NOT NULL,
    INPUTDATE          VARCHAR2(20)  NOT NULL,
    MODELITEMCNT       INT           NOT NULL,
    REGDTIM            VARCHAR2(20)  NOT NULL,
    ENDDTIM            VARCHAR2(20)  NOT NULL,
    INPUTFILENAME      VARCHAR2(255) NOT NULL,
    DATACNT            LONG(10)      NOT NULL,
    ANOMALYMEANPROB    DOUBLE        NOT NULL,
    ANOMALYRATIO       DOUBLE        NOT NULL,
    ANOMALYITEMCNT     INT           NOT NULL,
    ANOMALYRESULT      INT           NOT NULL,
    COMMENT            VARCHAR2(255)         ,
    PRIMARY KEY(PROJECTNAME, REGDTIM),
    CONSTRAINT PREDICT_ONLINE_RESULT_UN UNIQUE(PROJECTNAME, REGDTIM)
);

CREATE TABLE TOT_ROWPROB_DIST (
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	PKGID              VARCHAR2(100) NOT NULL,
	INPUTDATE          VARCHAR2(20)  NOT NULL,
	REGDTIM            VARCHAR2(20)  NOT NULL,
	PROB0TO5           DOUBLE        NOT NULL,
	PROB5TO10          DOUBLE        NOT NULL,
	PROB10TO15         DOUBLE        NOT NULL,
	PROB15TO20         DOUBLE        NOT NULL,
	PROB20TO25         DOUBLE        NOT NULL,
	PROB25TO30         DOUBLE        NOT NULL,
	PROB30TO35         DOUBLE        NOT NULL,
	PROB35TO40         DOUBLE        NOT NULL,
	PROB40TO45         DOUBLE        NOT NULL,
	PROB45TO50         DOUBLE        NOT NULL,
	PROB50TO55         DOUBLE        NOT NULL,
	PROB55TO60         DOUBLE        NOT NULL,
	PROB60TO65         DOUBLE        NOT NULL,
	PROB65TO70         DOUBLE        NOT NULL,
	PROB70TO75         DOUBLE        NOT NULL,
	PROB75TO80         DOUBLE        NOT NULL,
	PROB80TO85         DOUBLE        NOT NULL,
	PROB85TO90         DOUBLE        NOT NULL,
	PROB90TO95         DOUBLE        NOT NULL,
	PROB95TO100        DOUBLE        NOT NULL,
	PRIMARY KEY(PROJECTNAME, REGDTIM),
	CONSTRAINT TOT_ROWPROB_DIST_UN UNIQUE(PROJECTNAME, REGDTIM)
);

CREATE TABLE DEV_ROWPROB_DIST(
	PROJECTNAME        VARCHAR2(100) NOT NULL,
	PKGID              VARCHAR2(100) NOT NULL,
	PROB0TO5           DOUBLE        NOT NULL,
	PROB5TO10          DOUBLE        NOT NULL,
	PROB10TO15         DOUBLE        NOT NULL,
	PROB15TO20         DOUBLE        NOT NULL,
	PROB20TO25         DOUBLE        NOT NULL,
	PROB25TO30         DOUBLE        NOT NULL,
	PROB30TO35         DOUBLE        NOT NULL,
	PROB35TO40         DOUBLE        NOT NULL,
	PROB40TO45         DOUBLE        NOT NULL,
	PROB45TO50         DOUBLE        NOT NULL,
	PROB50TO55         DOUBLE        NOT NULL,
	PROB55TO60         DOUBLE        NOT NULL,
	PROB60TO65         DOUBLE        NOT NULL,
	PROB65TO70         DOUBLE        NOT NULL,
	PROB70TO75         DOUBLE        NOT NULL,
	PROB75TO80         DOUBLE        NOT NULL,
	PROB80TO85         DOUBLE        NOT NULL,
	PROB85TO90         DOUBLE        NOT NULL,
	PROB90TO95         DOUBLE        NOT NULL,
	PROB95TO100        DOUBLE        NOT NULL,
	PRIMARY KEY(PROJECTNAME),
	CONSTRAINT DEV_ROWPROB_DIST_UN UNIQUE(PROJECTNAME)
);

-- ROLE, MEMBER TABLE ADD  20210507

CREATE TABLE MEMBER(
	MEMBER_ID VARCHAR(20) NOT NULL PRIMARY KEY ,
	PASSWORD VARCHAR(255) NOT NULL ,
	NAME VARCHAR(100) ,
	MEMBER_TYPE VARCHAR2(20) ,
	LAST_ACCESS TIMESTAMP,
	REGDTIM TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	constraint MEMBER_TYPE_FK foreign key (MEMBER_TYPE)
	references MEMBER_TYPE(MEMBER_TYPE)
) ;
CREATE TABLE MEMBER_TYPE(
	MEMBER_TYPE VARCHAR(20) NOT NULL PRIMARY KEY 
) ;

CREATE TABLE ROLE(
	ROLE_TYPE VARCHAR(20) NOT NULL PRIMARY KEY ,
	URL VARCHAR(255) 
) ;

CREATE TABLE PERSISTENT_LOGINS(
	USERNAME VARCHAR(64) NOT NULL,
    SERIES VARCHAR(64) PRIMARY KEY,
    TOKEN VARCHAR(64) NOT NULL,
    LAST_USED TIMESTAMP NOT NULL
 );
 
 CREATE TABLE JOB_EXCUTION_LOG(
	JOBID        	   VARCHAR2(10) NOT NULL PRIMARY KEY,
	MEMBER_ID          VARCHAR2(20) NOT NULL,
	COMMENT            VARCHAR2(255) NOT NULL,
	ENDDTIM            VARCHAR2(20) NOT NULL,
	constraint JOBID_FK foreign key (JOBID)
	references PREDICT_JOB (JOBID)
);


