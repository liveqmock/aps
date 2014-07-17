/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.admin;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.child.InitEntity;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.pkkey.PkgeneratorServiceIF;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 305027939
 */
@Service
public class AdminService implements AdminServiceIF {

    private static final Log log = LogFactory.getLog(AdminService.class);

    @Autowired
    @Qualifier("departmentService")
    private CommServiceIF<TbDepartment, Long> depService;

    @Resource
    PkgeneratorServiceIF pkService;

    @Autowired
    @Qualifier("roleService")
    private CommServiceIF<TbRole, Long> roleService;

    @Autowired
    @Qualifier("empService")
    private CommServiceIF<UserEntity, Long> empService;

    @Override
    public int saveDataBase(InitEntity eneity) {
        log.info("start to init. database data.");

        if (eneity != null) {

            String roles = "";
            
            for(int i=0;i<Constants.ROLE_PAGES_CN.length;i++){
              if(i<Constants.ROLE_PAGES_CN.length-1){
                  roles+=Constants.ROLE_PAGES_CN[i]+",";
              }else{
                   roles+=Constants.ROLE_PAGES_CN[i];
              }
            }

            /*首先初始化角色*/
            TbRole role1 = new TbRole();
            role1.setRolename("管理员");
            role1.setFlag(Constants.FLAG_STATUS_ACTIVE);
            role1.setExt1(roles);
            roleService.saveData(role1);

            log.info("role table completed.");

            TbDepartment dep = new TbDepartment();
            dep.setFlag(Constants.FLAG_STATUS_ACTIVE);
            dep.setDepname(eneity.getDepartmentName());
            dep.setDeplevel(0l);
            dep.setDepfather(0l);
            dep.setDeppresentid(0l);
            depService.saveData(dep);

            log.info("role table completed.");

            UserEntity user = new UserEntity();
            user.setFlag(Constants.FLAG_STATUS_ACTIVE);
            user.setName(eneity.getName());
            user.setPasswd(eneity.getPassword());
            if (role1.getId() != null) {
                user.setRoleid(role1.getId());
            }
            if (dep.getId() != null) {
                user.setDepid(dep.getId());
            }
            log.info("employee table completed.");
            return empService.saveData(user);

        }

        return 1;
    }

}
