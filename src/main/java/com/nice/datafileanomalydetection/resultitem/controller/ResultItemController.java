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

    @GetMapping(value = "/resultitemhist/{projectName}/{baseDtim}/{period}")
    public List<BasicStatInfo> getAllProjectItemHistResult(@PathVariable String projectName, @PathVariable String baseDtim, @PathVariable String period) {
        List<BasicStatInfo> listResult = new ArrayList<>();
        listResult.add(new BasicStatInfo(projectName, "2021-06-21 01:01:01", "File1", "Field1", "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-22 01:01:01", "File2", "Field1", "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-23 01:01:01", "File3", "Field1", "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-24 01:01:01", "File4", "Field1", "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-25 01:01:01", "File5", "Field1", "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-21 01:01:01", "File1", "Field2", "1000", "2", "1", "10000", "999", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-22 01:01:01", "File2", "Field2", "1005", "3", "1", "100000", "1005", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-23 01:01:01", "File3", "Field2", "1010", "1.5", "1", "10070", "1000", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-24 01:01:01", "File4", "Field2", "910", "2", "1", "10100", "890", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-25 01:01:01", "File5", "Field2", "998", "2.8", "1", "12300", "900", "6", "17"));
        return listResult;
    }

    @GetMapping(value = "/resultitemhist/{projectName}/{baseDtim}/{period}/{fieldName}")
    public List<BasicStatInfo> getProjectItemHistResult(@PathVariable String projectName, @PathVariable String baseDtim, @PathVariable String period, @PathVariable String fieldName) {
        List<BasicStatInfo> listResult = new ArrayList<>();
        listResult.add(new BasicStatInfo(projectName, "2021-06-21 01:01:01", "File1", fieldName, "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-22 01:01:01", "File2", fieldName, "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-23 01:01:01", "File3", fieldName, "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-24 01:01:01", "File4", fieldName, "10", "2", "1", "100", "12", "6", "17"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-25 01:01:01", "File5", fieldName, "10", "2", "1", "100", "12", "6", "17"));

        return listResult;
    }
}