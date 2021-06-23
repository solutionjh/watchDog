package com.nice.datafileanomalydetection.main.controller;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> getRegDtim() {
        return mainService.getRegDtim();
    }

    @GetMapping(value = "/main/regdtim/{projectName}")
    public List<String> getProjectRegDtim(@PathVariable String projectName) {
        return mainService.getProjectRegDtim(projectName);
    }

    @GetMapping(value = "/main/anomalycnt/{period}/{baseDtim}")
    public List<MainGraphInfo> getAnomalyCount(@PathVariable String period, @PathVariable String baseDtim) {

        List<MainGraphInfo> listResult = new ArrayList<MainGraphInfo>();
        listResult.add(new MainGraphInfo("Project1", "2021-06-01 01:01:01", "0", "1"));
        listResult.add(new MainGraphInfo("Project1", "2021-06-02 01:01:01", "0", "3"));
        listResult.add(new MainGraphInfo("Project2", "2021-06-01 01:01:01", "1", "1"));
        listResult.add(new MainGraphInfo("Project2", "2021-06-02 01:01:01", "2", "1"));
        return listResult;
    }

    @GetMapping(value = "/main/anomalycnt/{period}/{baseDtim}/{projectName}")
    public List<MainGraphInfo> getProjectAnomalyCount(@PathVariable String period, @PathVariable String baseDtim, @PathVariable String projectName) {
        List<MainGraphInfo> listResult = new ArrayList<MainGraphInfo>();
        listResult.add(new MainGraphInfo(projectName, "2021-06-01 01:01:01", "0", "1"));
        listResult.add(new MainGraphInfo(projectName, "2021-06-02 01:01:01", "0", "3"));
        listResult.add(new MainGraphInfo(projectName, "2021-06-03 01:01:01", "1", "1"));
        return listResult;
    }

    @GetMapping(value = "/main/stat/{period}/{baseDtim}")
    public List<StatGraphInfo> getStats(@PathVariable String period, @PathVariable String baseDtim) {
        List<StatGraphInfo> listResult = new ArrayList<StatGraphInfo>();
        listResult.add(new StatGraphInfo("project1", "2021-06-01 01:01:01", "2.01", "1.11", "3", "12345"));
        listResult.add(new StatGraphInfo("project1", "2021-06-02 01:01:01", "3.01", "0.91", "1", "12335"));
        listResult.add(new StatGraphInfo("project2", "2021-06-01 01:01:01", "5.01", "7.11", "5", "13445"));
        listResult.add(new StatGraphInfo("project2", "2021-06-02 01:01:01", "6.01", "9.11", "2", "21345"));

        return listResult;
    }

    @GetMapping(value = "/main/stat/{period}/{baseDtim}/{projectName}")
    public List<StatGraphInfo> getProjectStats(@PathVariable String period, @PathVariable String baseDtim, @PathVariable String projectName) {
        List<StatGraphInfo> listResult = new ArrayList<StatGraphInfo>();
        listResult.add(new StatGraphInfo(projectName, "2021-06-01 01:01:01", "2.01", "1.11", "3", "12345"));
        listResult.add(new StatGraphInfo(projectName, "2021-06-02 01:01:01", "3.01", "0.91", "1", "12335"));
        listResult.add(new StatGraphInfo(projectName, "2021-06-03 01:01:01", "4.01", "2.11", "5", "13445"));
        return listResult;
    }

    @GetMapping(value = "/main/changerate/{period}/{baseDtim}")
    public List<MainItemInfo> getAllAnomalyItemChangeRate(@PathVariable String period, @PathVariable String baseDtim) {
        List<MainItemInfo> listResult = new ArrayList<MainItemInfo>();
        listResult.add(new MainItemInfo("Project1", "2021-06-01 01:01:01", "field1", "12.01", "0"));
        listResult.add(new MainItemInfo("Project1", "2021-06-01 01:01:01", "field2", "31.01", "1"));
        listResult.add(new MainItemInfo("Project1", "2021-06-01 01:01:01", "field3", "17.01", "0"));
        listResult.add(new MainItemInfo("Project1", "2021-06-01 01:01:01", "field4", "10.01", "0"));
        listResult.add(new MainItemInfo("Project1", "2021-06-02 01:01:01", "field1", "120.01", "1"));
        listResult.add(new MainItemInfo("Project1", "2021-06-02 01:01:01", "field2", "11.01", "0"));
        listResult.add(new MainItemInfo("Project1", "2021-06-02 01:01:01", "field3", "92.01", "1"));
        listResult.add(new MainItemInfo("Project1", "2021-06-02 01:01:01", "field4", "117.01", "1"));
        listResult.add(new MainItemInfo("Project2", "2021-06-01 01:01:01", "field1", "12.01", "0"));
        listResult.add(new MainItemInfo("Project2", "2021-06-01 01:01:01", "field2", "1.01", "0"));
        listResult.add(new MainItemInfo("Project2", "2021-06-01 01:01:01", "field3", "2.01", "0"));
        listResult.add(new MainItemInfo("Project2", "2021-06-01 01:01:01", "field4", "152.01", "1"));
        listResult.add(new MainItemInfo("Project2", "2021-06-01 01:01:01", "field5", "12.01", "0"));
        listResult.add(new MainItemInfo("Project2", "2021-06-02 01:01:01", "field1", "312.01", "1"));
        listResult.add(new MainItemInfo("Project2", "2021-06-02 01:01:01", "field2", "812.01", "1"));
        listResult.add(new MainItemInfo("Project2", "2021-06-02 01:01:01", "field3", "12.01", "0"));
        listResult.add(new MainItemInfo("Project2", "2021-06-02 01:01:01", "field4", "2.51", "0"));
        listResult.add(new MainItemInfo("Project2", "2021-06-02 01:01:01", "field5", "217.01", "1"));

        listResult.sort(MainItemInfo::compareTo);
        return listResult;
    }

    @GetMapping(value = "/main/changerate/{period}/{baseDtim}/{projectName}")
    public List<MainItemInfo> getProjectAnomalyItemChangeRate(@PathVariable String period, @PathVariable String baseDtim, @PathVariable String projectName) {
        List<MainItemInfo> listResult = new ArrayList<MainItemInfo>();
        listResult.add(new MainItemInfo(projectName, "2021-06-01 01:01:01", "field1", "12.01", "0"));
        listResult.add(new MainItemInfo(projectName, "2021-06-01 01:01:01", "field2", "1.01", "0"));
        listResult.add(new MainItemInfo(projectName, "2021-06-01 01:01:01", "field3", "2.01", "0"));
        listResult.add(new MainItemInfo(projectName, "2021-06-01 01:01:01", "field4", "152.01", "1"));
        listResult.add(new MainItemInfo(projectName, "2021-06-01 01:01:01", "field5", "12.01", "0"));
        listResult.add(new MainItemInfo(projectName, "2021-06-02 01:01:01", "field1", "312.01", "1"));
        listResult.add(new MainItemInfo(projectName, "2021-06-02 01:01:01", "field2", "812.01", "1"));
        listResult.add(new MainItemInfo(projectName, "2021-06-02 01:01:01", "field3", "12.01", "0"));
        listResult.add(new MainItemInfo(projectName, "2021-06-02 01:01:01", "field4", "2.51", "0"));
        listResult.add(new MainItemInfo(projectName, "2021-06-02 01:01:01", "field5", "217.01", "1"));

        listResult.sort(MainItemInfo::compareTo);
        return listResult;
    }

    @GetMapping(value = "/main/detectcount/{period}/{baseDtim}/{projectName}")
    public List<MainItemInfo> getAnomalyItemDetectCount(@PathVariable String period, @PathVariable String baseDtim, @PathVariable String projectName) {
        List<MainItemInfo> listResult = new ArrayList<MainItemInfo>();
        listResult.add(new MainItemInfo(projectName, "2021-06-01 01:01:01", "", "", "3"));
        listResult.add(new MainItemInfo(projectName, "2021-06-02 01:01:01", "", "", "1"));

        return listResult;
    }
}