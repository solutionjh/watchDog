package com.nice.datafileanomalydetection.main.controller;

import com.nice.datafileanomalydetection.main.model.MainGraphInfo;
import com.nice.datafileanomalydetection.main.model.MainItemInfo;
import com.nice.datafileanomalydetection.main.model.StatGraphInfo;
import com.nice.datafileanomalydetection.main.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


@RestController
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    MainService mainService;

    @GetMapping(value = "/main")
    public ModelAndView mainView (ModelAndView mv) {
        mv.setViewName("/watchDogMain");
        return mv;
    }

    @GetMapping(value = "/dashboard")
    public ModelAndView dashBoardView (ModelAndView mv) {
        mv.setViewName("/watchDogDashboard");
        return mv;
    }

    @GetMapping(value = "/deletedata")
    public ModelAndView deleteProjectView (ModelAndView mv) {
        mv.setViewName("/watchDogDeleteData");
        return mv;
    }

    @GetMapping(value = "/main/allprojectnames")
    public List<String> getAllProjectNames () {
        return mainService.getAllProjectNames();
    }

    @GetMapping(value = "/main/deleteproject/{projectName}")
    public int deleteProjectData (@PathVariable String projectName) {
        return mainService.deleteProjectData(projectName);
    }

    @GetMapping(value = "/main/cleanbatchtable")
    public int cleanBatchTable () {
        return mainService.cleanBatchTable();
    }

    @GetMapping(value = "/main/deletetemp")
    public String deleteTmpFiles () {

        return "";
    }

    @GetMapping(value = "/main/projectnames")
    public List<String> getProjectNames () {
        return mainService.getProjectNames();
    }

    @GetMapping(value = "/main/regdtim")
    public List<String> getRegDtim () {
        return mainService.getRegDtim();
    }

    @GetMapping(value = "/main/regdtim/{projectName}")
    public List<String> getProjectRegDtim (@PathVariable String projectName) {
        return mainService.getProjectRegDtim(projectName);
    }

    @GetMapping(value = "/main/anomalycnt/{period}")
    public List<MainGraphInfo> getAnomalyCount (@PathVariable String period) {
        String fromDtim = getFromDtim(period);

        return mainService.getAnomalyCount(fromDtim);
    }

    @GetMapping(value = "/main/anomalycnt/{projectName}/{period}")
    public List<MainGraphInfo> getProjectAnomalyCount (@PathVariable String projectName, @PathVariable String period) {
        String fromDtim = getFromDtim(period);

        return mainService.getProjectAnomalyCount(projectName, fromDtim);
    }

    @GetMapping(value = "/main/stat/{period}")
    public List<StatGraphInfo> getStats (@PathVariable String period) {
        String fromDtim = getFromDtim(period);

        return mainService.getStats(fromDtim);
    }

    @GetMapping(value = "/main/stat/{projectName}/{period}")
    public List<StatGraphInfo> getProjectStats (@PathVariable String projectName, @PathVariable String period) {
        String fromDtim = getFromDtim(period);

        return mainService.getProjectStats(projectName, fromDtim);
    }

    @GetMapping(value = "/main/changerate/{projectName}/{period}")
    public List<MainItemInfo> getAnomalyItemChangeRate (@PathVariable String projectName, @PathVariable String period) {
        String fromDtim = getFromDtim(period);

        return mainService.getAnomalyItemChangeRate(projectName, fromDtim);
    }

    @GetMapping(value = "/main/detectcount/{projectName}/{period}")
    public List<MainItemInfo> getAnomalyItemDetectCount (@PathVariable String projectName, @PathVariable String period) {
        String fromDtim = getFromDtim(period);

        return mainService.getAnomalyItemDetectCount(projectName, fromDtim);
    }

    private String getFromDtim (String period) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String nowDtim = sdf.format(cal.getTime());
        String fromDtim = "9999";

        if ("oneday".equals(period)) {
            cal.add(Calendar.DATE, - 1);
            fromDtim = sdf.format(cal.getTime());
        } else if ("oneweek".equals(period)) {
            cal.add(Calendar.DATE, - 7);
            fromDtim = sdf.format(cal.getTime());
        } else if ("onemonth".equals(period)) {
            cal.add(Calendar.MONTH, - 1);
            fromDtim = sdf.format(cal.getTime());
        } else if ("onequarter".equals(period)) {
            cal.add(Calendar.MONTH, - 3);
            fromDtim = sdf.format(cal.getTime());
        } else if ("onehalf".equals(period)) {
            cal.add(Calendar.MONTH, - 6);
            fromDtim = sdf.format(cal.getTime());
        } else if ("oneyear".equals(period)) {
            cal.add(Calendar.YEAR, - 1);
            fromDtim = sdf.format(cal.getTime());
        } else if ("all".equals(period)) {
            fromDtim = "0";
        }

        return fromDtim;
    }

}
