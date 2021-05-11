package com.nice.datafileanomalydetection.role.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nice.datafileanomalydetection.role.dao.RoleDao;
import com.nice.datafileanomalydetection.role.model.Role;

@Service
public class RoleService{

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Boolean isDebugEnabled = logger.isDebugEnabled();

    @Autowired
    RoleDao roleDao;
    
	public Role getRole(String roleType){	
		return roleDao.getRole(roleType);
	}
	
	public List<Role> getRoleList(){	
		return roleDao.getRoleList();
	}
}
