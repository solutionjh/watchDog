package com.nice.datafileanomalydetection.resultitem.controller;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.nice.datafileanomalydetection.resultgraph.model.BasicStatInfo;
import com.nice.datafileanomalydetection.resultitem.model.ResultItem;
import com.nice.datafileanomalydetection.resultitem.service.ResultItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ResultItemController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    ResultItemService resultItemService;

    @GetMapping(value = "/resultitem")
    public ModelAndView monitoringView (ModelAndView mv) {
        mv.setViewName("/watchDogResultItem");
        return mv;
    }

    @GetMapping(value = "/resultitem/{projectName}/regdtim/")
    public List<String> getResultItemRegdtim(@PathVariable String projectName) {
        return resultItemService.getResultItemRegdtim(projectName);
    }

    @GetMapping(value = "/resultitem/{projectName}/{regdtim}/{changeRate}")
    public List<ResultItem> getProjectItemResult(@PathVariable String projectName, @PathVariable String regdtim, @PathVariable String changeRate) {
        return resultItemService.getProjectItemResult(projectName, regdtim, changeRate);
    }

    @GetMapping(value = "/resultitemhist/{projectName}/{baseDtim}/{period}/{fieldName}")
    public List<BasicStatInfo> getProjectItemHistResult(@PathVariable String projectName, @PathVariable String baseDtim, @PathVariable String period, @PathVariable String fieldName) {
        List<BasicStatInfo> listResult = new ArrayList<>();
        listResult.add(new BasicStatInfo(projectName, "2021-06-21 01:01:01", "", fieldName, "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-22 01:01:01", "", fieldName, "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-23 01:01:01", "", fieldName, "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-24 01:01:01", "", fieldName, "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-25 01:01:01", "", fieldName, "10", "2", "1", "100", "12", "6", "17"));

        return listResult;
    }
}