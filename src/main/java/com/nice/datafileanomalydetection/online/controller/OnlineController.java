package com.nice.datafileanomalydetection.online.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nice.datafileanomalydetection.online.model.OnlineItemResult;
import com.nice.datafileanomalydetection.online.model.OnlineResult;
import com.nice.datafileanomalydetection.online.model.OnlineRowDistResult;
import com.nice.datafileanomalydetection.online.model.OnlineRowResult;
import com.nice.datafileanomalydetection.online.service.OnlineService;
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
public class OnlineController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    OnlineService onlineService;

    @GetMapping(value = "/online/result")
    public ModelAndView onlineRsltView (ModelAndView mv) {
        mv.setViewName("/watchDogOnlineRslt");
        return mv;
    }

    @GetMapping(value = "/online/rowresult")
    public ModelAndView onlineRowView (ModelAndView mv) {
        mv.setViewName("/watchDogOnlineRowRslt");
        return mv;
    }

    @GetMapping(value = "/online/itemresult")
    public ModelAndView onlineItemView (ModelAndView mv) {
        mv.setViewName("/watchDogOnlineItemRslt");
        return mv;
    }

    @GetMapping(value = "/online/rowdist")
    public ModelAndView onlineRowDistView (ModelAndView mv) {
        mv.setViewName("/watchDogOnlineRowGraph");
        return mv;
    }

    @GetMapping(value = "/online/result/projectnames")
    public List<String> getResultProjectNames () {
        return onlineService.getResultProjectNames();
    }

    @GetMapping(value = "/online/result/results")
    public String getResults () {
        List<OnlineResult> ruleList = onlineService.getResults();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(ruleList);
    }

    @GetMapping(value = "/online/result/{projectName}")
    public List<OnlineResult> getProjectResult (@PathVariable String projectName) {
        return onlineService.getProjectResult(projectName);
    }

    @GetMapping(value = "/online/rowresult/projectnames")
    public List<String> getRowResultProjectNames () {
        return onlineService.getRowResultProjectNames();
    }

    @GetMapping(value = "/online/rowresult/{projectName}/regdtim/")
    public List<String> getRowResultRegdtim (@PathVariable String projectName) {
        return onlineService.getRowResultRegdtim(projectName);
    }

    @GetMapping(value = "/online/rowresult/{projectName}/")
    public List<OnlineRowResult> getProjectRowResult (@PathVariable String projectName) {
        return onlineService.getProjectRowResult(projectName);
    }

    @GetMapping(value = "/online/rowresult/{projectName}/{regDtim}")
    public List<OnlineRowResult> getProjectRegdtimRowResult (@PathVariable String projectName, @PathVariable String regDtim) {
        return onlineService.getProjectRegdtimRowResult(projectName, regDtim);
    }

    @GetMapping(value = "/online/itemresult/projectnames")
    public List<String> getItemResultProjectNames () {
        return onlineService.getItemResultProjectNames();
    }

    @GetMapping(value = "/online/itemresult/{projectName}/regdtim/")
    public List<String> getItemResultRegdtim (@PathVariable String projectName) {
        return onlineService.getItemResultRegdtim(projectName);
    }

    @GetMapping(value = "/online/itemresult/{projectName}/{regDtim}/fieldname/")
    public List<String> getItemResultFieldName (@PathVariable String projectName, @PathVariable String regDtim) {
        return onlineService.getItemResultFieldName(projectName, regDtim);
    }

    @GetMapping(value = "/online/itemresult/{projectName}/{regDtim}")
    public List<OnlineItemResult> getProjectRegdtimItemResult (@PathVariable String projectName, @PathVariable String regDtim) {
        return onlineService.getProjectRegdtimItemResult(projectName, regDtim);
    }

    @GetMapping(value = "/online/itemresult/{projectName}/{regDtim}/{fieldName}")
    public List<OnlineItemResult> getProjectRegdtimFieldItemResult (@PathVariable String projectName, @PathVariable String regDtim, @PathVariable String fieldName) {
        return onlineService.getProjectRegdtimFieldItemResult(projectName, regDtim, fieldName);
    }

    @GetMapping(value = "/online/rowdist/projectnames")
    public List<String> getRowDistProjectNames () {
        return onlineService.getRowDistProjectNames();
    }

    @GetMapping(value = "/online/rowdist/{projectName}/regdtim")
    public List<String> getRowDistProjectRegdtim (@PathVariable String projectName) {
        return onlineService.getRowDistProjectRegdtim(projectName);
    }

    @GetMapping(value = "/online/rowdist/{projectName}/")
    public List<OnlineRowDistResult> getDevRowDistResult (@PathVariable String projectName) {
        return onlineService.getDevRowDistResult(projectName);
    }

    @GetMapping(value = "/online/rowdist/{projectName}/{regDtim}/")
    public List<OnlineRowDistResult> getTotRowDistResult (@PathVariable String projectName, @PathVariable String regDtim) {
        return onlineService.getTotRowDistResult(projectName, regDtim);
    }

}
