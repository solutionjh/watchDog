/*
2020.03.18 kwb 
- 기존 ResultItemController.java 활용
*/

package com.nice.datafileanomalydetection.resultgraph.controller;

import com.nice.datafileanomalydetection.resultgraph.model.ResultGraphModelPoints;
import com.nice.datafileanomalydetection.resultgraph.service.ResultGraphService;
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
public class ResultGraphController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    ResultGraphService resultGraphService;

    @GetMapping(value = "/resultgraph")
    public ModelAndView monitoringView (ModelAndView mv) {
        mv.setViewName("/watchDogResultGraph");
        return mv;
    }

    @GetMapping(value = "/resultgraph/projectnames")
    public List<String> getProjectNames () {
        return resultGraphService.getProjectNames();
    }

    @GetMapping(value = "/resultgraph/{projectName}/regdtim")
    public List<String> getResultRegdtim (@PathVariable String projectName) {
        return resultGraphService.getResultRegdtim(projectName);
    }

    @GetMapping(value = "/resultgraph/{projectName}/{regdtim}")
    public ResultGraphModelPoints getResultGraph (@PathVariable String projectName, @PathVariable String regdtim) {
        return resultGraphService.getResultGraphModelPoints(projectName, regdtim);
    }
}
