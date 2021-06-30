/*
2020.03.18 kwb 
- 기존 ResultItemController.java 활용
*/

package com.nice.datafileanomalydetection.resultgraph.controller;

import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.nice.datafileanomalydetection.resultgraph.model.BasicDevStatInfo;
import com.nice.datafileanomalydetection.resultgraph.model.BasicStatInfo;
import com.nice.datafileanomalydetection.resultgraph.model.ResultGraphModelPoints;
import com.nice.datafileanomalydetection.resultgraph.service.ResultGraphService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ResultGraphController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();
    @Autowired
    ResultGraphService resultGraphService;
    private final DateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");

    @GetMapping(value = "/resultgraph")
    public ModelAndView monitoringView(ModelAndView mv) {
        mv.setViewName("/watchDogResultGraph");
        return mv;
    }

    @GetMapping(value = "/resultgraph/projectnames")
    public List<String> getProjectNames() {
        return resultGraphService.getProjectNames();
    }

    @GetMapping(value = "/resultgraph/{projectName}/regdtim")
    public List<String> getResultRegdtim(@PathVariable String projectName) {
        return resultGraphService.getResultRegdtim(projectName);
    }

    @GetMapping(value = "/resultgraph/{projectName}/{regdtim}")
    public ResultGraphModelPoints getResultGraph(@PathVariable String projectName, @PathVariable String regdtim) {
        return resultGraphService.getResultGraphModelPoints(projectName, regdtim);
    }

    @GetMapping(value = "/fileresult/{projectName}/{baseDtim}/{peirod}")
    public List<BasicStatInfo> getFileMseDistInfo(@PathVariable String projectName, @PathVariable String baseDtim, @PathVariable String peirod) throws ParseException {
        // FieldName은 무시
        List<BasicStatInfo> listResult = new ArrayList<BasicStatInfo>();
        listResult.add(new BasicStatInfo(projectName, "2021-06-21 01:01:01", "File1", "", "1.23", "2", "0", "2.5", "1.5", "0.7", "2.0"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-22 01:01:01", "File1", "", "2.01", "1.7", "0", "2.7", "1.6", "0.6", "2.5"));
        listResult.add(new BasicStatInfo(projectName, "2021-06-23 01:01:01", "File1", "", "1.58", "1.5", "0", "3", "1.8", "1.0", "2.3"));
        return listResult;
    }

    @GetMapping(value = "/fileresultmon/{projectName}/{baseDtim}/{peirod}")
    public List<BasicDevStatInfo> getFileMonInfo(@PathVariable String projectName, @PathVariable String baseDtim, @PathVariable String peirod) throws ParseException {
        // 동일 일자에 fieldName 별로 나옴
        List<BasicDevStatInfo> listResult = new ArrayList<BasicDevStatInfo>();
        listResult.add(new BasicDevStatInfo(projectName, "2021-06-21 01:01:01", "File1", "Field1", "1.23", "2"
                , "3", "2.5", "1.5", "0.7", "2.0", "2.7", "1.0", "1.2"
                , "0.5", "0.6", "1.8", "2.0"));
        listResult.add(new BasicDevStatInfo(projectName, "2021-06-21 01:01:01", "File1", "Field2", "200000", "170000"
                , "3", "2.5", "0", "10", "10000000", "20000503", "187654", "180078"
                , "50001", "20005", "987654", "1002007"));
        listResult.add(new BasicDevStatInfo(projectName, "2021-06-21 01:01:01", "File1", "Field3", "1000", "1100"
                , "12", "11", "1.5", "0.7", "2.0", "2.7", "1.0", "1.2"
                , "0.5", "0.6", "1.8", "2.0"));
        return listResult;
    }
    @GetMapping(value = "/fileresultmon/{projectName}/{baseDtim}/{peirod}/{fieldName}")
    public List<BasicDevStatInfo> getFileMonInfo1(@PathVariable String projectName, @PathVariable String baseDtim, @PathVariable String peirod) throws ParseException {
    	 // 동일 일자에 fieldName 별로 나옴
        List<BasicDevStatInfo> listResult = new ArrayList<BasicDevStatInfo>();
        listResult.add(new BasicDevStatInfo(projectName, "2021-06-21 01:01:01", "File1", "Field1", "1.23", "2"
                , "3", "2.5", "1.5", "0.7", "2.0", "2.7", "1.0", "1.2"
                , "0.5", "0.6", "1.8", "2.0"));
        listResult.add(new BasicDevStatInfo(projectName, "2021-06-21 01:01:01", "File1", "Field2", "200000", "170000"
                , "3", "2.5", "0", "10", "10000000", "20000503", "187654", "180078"
                , "50001", "20005", "987654", "1002007"));
        listResult.add(new BasicDevStatInfo(projectName, "2021-06-21 01:01:01", "File1", "Field3", "1000", "1100"
                , "12", "11", "1.5", "0.7", "2.0", "2.7", "1.0", "1.2"
                , "0.5", "0.6", "1.8", "2.0"));
        return listResult;
    }
}