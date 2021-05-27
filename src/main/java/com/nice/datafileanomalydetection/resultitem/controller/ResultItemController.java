package com.nice.datafileanomalydetection.resultitem.controller;

import com.nice.datafileanomalydetection.resultitem.model.ResultItem;
import com.nice.datafileanomalydetection.resultitem.service.ResultItemService;
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
    public List<String> getResultItemRegdtim (@PathVariable String projectName) {
        return resultItemService.getResultItemRegdtim(projectName);
    }

    @GetMapping(value = "/resultitem/{projectName}/{regdtim}/{changeRate}")
    public List<ResultItem> getProjectItemResult (@PathVariable String projectName, @PathVariable String regdtim, @PathVariable String changeRate) {
        return resultItemService.getProjectItemResult(projectName, regdtim, changeRate);
    }


}
