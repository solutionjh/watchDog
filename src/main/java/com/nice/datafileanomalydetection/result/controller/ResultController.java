package com.nice.datafileanomalydetection.result.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nice.datafileanomalydetection.result.model.Result;
import com.nice.datafileanomalydetection.result.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.lang.invoke.MethodHandles;
import java.util.List;


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
    public String getResults () {
        List<Result> ruleList = resultService.getResults();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(ruleList);
    }

    @GetMapping(value = "/result/results/{projectName}")
    public List<Result> getProjectResult (@PathVariable String projectName) {
        return resultService.getProjectResult(projectName);
    }

}
