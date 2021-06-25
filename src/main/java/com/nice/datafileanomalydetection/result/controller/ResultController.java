package com.nice.datafileanomalydetection.result.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nice.datafileanomalydetection.result.model.ItemAnomalyLevel;
import com.nice.datafileanomalydetection.result.model.Result;
import com.nice.datafileanomalydetection.result.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ResultController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    ResultService resultService;

    @GetMapping(value = "/result")
    public ModelAndView monitoringView (ModelAndView mv) {
        mv.setViewName("/watchDogResult");
        return mv;
    }

    @GetMapping(value = "/result/projectnames")
    public List<String> getProjectNames () {
        return resultService.getProjectNames();
    }

    @GetMapping(value = "/result/results")
    public String getResults() {
        List<Result> ruleList = resultService.getResults();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(ruleList);
    }

    @GetMapping(value = "/result/results/{year}")
    public String getResultsByYear(@PathVariable String year) {
        List<Result> ruleList = resultService.getResultsByYear(year);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(ruleList);
    }

    @GetMapping(value = "/result/results/{projectName}")
    public List<Result> getProjectResult(@PathVariable String projectName) {
        return resultService.getProjectResult(projectName);
    }

    @GetMapping(value = "/result/filename/{projectName}/{regDtim}")
    public List<String> getFileName(@PathVariable String projectName, @PathVariable String regDtim) {
        return resultService.getFileName(projectName, regDtim);
    }

    @GetMapping(value = "/result/anomalyindex/{projectName}/{regDtim}")
    public Map<String, String> getAnomalyIndex(@PathVariable String projectName, @PathVariable String regDtim) {
        return resultService.getAnomalyIndex(projectName, regDtim);
    }

    @GetMapping(value = "/result/itemanomaly/{projectName}/{regDtim}")
    public List<ItemAnomalyLevel> getItemAnomalyLevel(@PathVariable String projectName, @PathVariable String regDtim) {
        return resultService.getItemAnomalyLevels(projectName, regDtim);
    }

}