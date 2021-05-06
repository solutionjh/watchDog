package com.nice.datafileanomalydetection.predict.service;

public class PredictServiceConfig {

    public static String ROW_SQUARD_ERROR_LIST_MAP_KEY = "RowSquardErrorList";
    public static String ROW_MEAN_SQUARD_ERROR_MAP_KEY = "RowMeanSquardError";
    // refs #1283 online 이상탐지를 위하여 표준화한 확률값 계산 저장 key(2020-06-24, kty)
    public static String ROW_MEAN_PROB_MAP_KEY = "RowMeanProbability";
    public static String ITEM_STDED_PROB_MAP_KEY = "ItemStdedProbability";

    public static String getModelFileFullPath(String projectName) {

        String rootPath = System.getProperty("user.dir");
        String projectPath = rootPath + "/model/" + projectName;

        return projectPath;
    }

}
