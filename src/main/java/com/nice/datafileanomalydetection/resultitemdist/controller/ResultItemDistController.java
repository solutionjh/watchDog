package com.nice.datafileanomalydetection.resultitemdist.controller;

import com.nice.datafileanomalydetection.resultitemdist.model.ResultItemDist;
import com.nice.datafileanomalydetection.resultitemdist.service.ResultItemDistService;
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
public class ResultItemDistController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    ResultItemDistService resultItemDistService;

    @GetMapping(value = "/resultitemdist")
    public ModelAndView monitoringView (ModelAndView mv) {
        mv.setViewName("/watchDogResultItemDist");
        return mv;
    }

    @GetMapping(value = "/resultitemdist/projectnames")
    public List<String> getProjectNames () {
        return resultItemDistService.getProjectNames();
    }

    @GetMapping(value = "/resultitemdist/{projectName}/regdtim/")
    public List<String> getResultItemDistRegdtim (@PathVariable String projectName) {
        return resultItemDistService.getResultItemDistRegdtim(projectName);
    }

    @GetMapping(value = "/resultitemdist/{projectName}/{regdtim}")
    public List<ResultItemDist> getProjectItemDistResult (@PathVariable String projectName, @PathVariable String regdtim) {
        return resultItemDistService.getProjectItemDistResult(projectName, regdtim);
    }

    @GetMapping(value = "/fileresultmon/fieldnames/{projectName}/{regdtim}")
    public List<String> getProjectRegdtimFieldNames1 (@PathVariable String projectName, @PathVariable String regdtim) {
        return resultItemDistService.getProjectRegdtimFieldNames(projectName, regdtim);
    }
    @GetMapping(value = "/resultitemdist/fieldname/{projectName}/{regdtim}")
    public List<String> getProjectRegdtimFieldNames (@PathVariable String projectName, @PathVariable String regdtim) {
    	return resultItemDistService.getProjectRegdtimFieldNames(projectName, regdtim);
    }

    @GetMapping(value = "/resultitemdist/{projectName}/{regdtim}/{fieldName}")
    public List<ResultItemDist> getProjectRegdtimFieldResult (@PathVariable String projectName, @PathVariable String regdtim, @PathVariable String fieldName) {
        return resultItemDistService.getProjectRegdtimFieldResult(projectName, regdtim, fieldName);
    }

}
