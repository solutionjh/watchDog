package com.nice.datafileanomalydetection.role.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nice.datafileanomalydetection.role.model.Role;
import com.nice.datafileanomalydetection.role.service.RoleService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = {"/api/role"})
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    RoleService roleService;
    
    @GetMapping(value = "/getList")
    @ApiOperation(value = "역할목록조회")
    public List<Role> getRoleList(){	
		return roleService.getRoleList();
	}


}
