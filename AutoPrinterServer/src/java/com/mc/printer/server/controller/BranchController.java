/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author 305027939
 */
@Controller
@RequestMapping("/branch/*")
public class BranchController {

    private static final Log log = LogFactory.getLog(BranchController.class);

    @Autowired
    @Qualifier("branchService")
    private CommServiceIF<TbBranch, Long> comService;

    @Autowired
    @Qualifier("departmentService")
    private CommServiceIF<TbDepartment, Long> depService;

    @Resource
    private LogServiceIF logService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public @ResponseBody
    TbBranch findDataByKey(@PathVariable Long id) {
        log.debug("findDataByKey:" + id);
        return comService.findDataByKey(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public @ResponseBody
    List<TbBranch> findAll() {
        log.debug("findAll.");
        List<TbBranch> result = new ArrayList();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            try {
                if (principal instanceof LoginUserDetails) {
                    LoginUserDetails user = (LoginUserDetails) principal;
                    log.info("Got UserDetails object." + user.getUsername());
                    UserEntity session = user.getUserSessionEntity();
                    TbDepartment department = session.getDepartment();
                    TbRole role = session.getRole();

                    if (role != null) {
                        String ext = role.getExt1();
                        CommFindEntity<TbBranch> data = comService.findAll(null);
                        boolean hasSearchAdmin = false;
                        if (ext != null && ext.contains("系统查询")) {
                            hasSearchAdmin = true;
                        }

                        /*得到我所有拥有的网点的IP，包括子网点的*/
                        List extArray = new ArrayList();
                        String ipString = department.getExt1();
                        if (ipString != null && !ipString.trim().equals("")) {
                            String[] strs = ipString.split(",");
                            extArray.addAll(Arrays.asList(strs));
                        }
                        getChild(session.getDepid(), extArray);
                        log.debug("found ext array size:" + extArray.size());
                        if (data.getResult() != null) {
                            for (TbBranch tbb : data.getResult()) {
                                String name = tbb.getName();
                                String ip = tbb.getAddress();
                                String extvalue = name + "-" + ip;
                                log.debug("found running branch:" + extvalue);
                                if (hasSearchAdmin) {
                                    result.add(tbb);
                                } else {
                                    if (extArray.contains(extvalue)) {
                                        result.add(tbb);
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                log.error(e);
            }
        } else {
            log.error("principal is null in session");
        }

        return result;
    }

    private void getChild(long pid, List ext) {
        TbDepartment conditionEntity = new TbDepartment();
        conditionEntity.setDepfather(pid);
        CommFindEntity<TbDepartment> data = depService.findAll(conditionEntity);
        if (data != null) {
            List<TbDepartment> ls = data.getResult();
            for (TbDepartment tb : ls) {
                String e = tb.getExt1();
                if (e != null && !e.trim().equals("")) {
                    String[] array = e.split(",");
                    ext.addAll(Arrays.asList(array));
                }

                getChild(tb.getId(), ext);
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbBranch> saveData(@RequestBody TbBranch bean) {
        log.debug("saveData:" + bean.getName());
        ComResponse<TbBranch> comResponse = new ComResponse<TbBranch>();
        try {
            int result = comService.saveData(bean);
            log.info("save result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            comResponse.setResponseEntity(bean);
            logService.saveLog("保存网点：" + bean.getName());
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("EmpController,saveData:" + comResponse.getResponseStatus());

        return comResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbBranch> deleteDataByKey(@PathVariable Long id) {
        log.debug("deleteDataByKey:" + id);
        ComResponse<TbBranch> comResponse = new ComResponse<TbBranch>();
        try {
            int result = comService.deleteDataByKey(id);
            log.info("delete result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            logService.saveLog("删除网点ID：" +id);
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("deleteDataByKey result:" + comResponse.getResponseStatus());
        return comResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbBranch> updateData(@RequestBody TbBranch bean) {
        log.debug("updateData:" + bean.getName());
        ComResponse<TbBranch> comResponse = new ComResponse<TbBranch>();
        try {
            int result = comService.updateData(bean);
            log.info("update result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            comResponse.setResponseEntity(bean);
            logService.saveLog("更新网点信息：" +bean.getName());
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("updateData:" + comResponse.getResponseStatus());
        return comResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "checkregister", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbBranch> isRegister(@RequestBody TbBranch bean) {
        log.debug("isRegister");
        ComResponse<TbBranch> comResponse = new ComResponse<TbBranch>();
        if (bean != null) {
            log.debug("check ip whether it exist in department.");
            String name = bean.getName();
            String ip = bean.getAddress();
            TbDepartment depart = new TbDepartment();
            depart.setExt1(name + "-" + ip);
            CommFindEntity<TbDepartment> resultDp = depService.findAll(depart);
            if (resultDp != null && resultDp.getResult() != null && resultDp.getResult().size() > 0) {
                comResponse.setResponseStatus(ComResponse.STATUS_OK);
                comResponse.setResponseEntity(bean);
            } else {
                comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
                comResponse.setErrorMessage("no registered name and ip.");
                comResponse.setExtendResponseContext("no registered name and ip.");
            }
        } else {
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage("name or ip is null");
            comResponse.setExtendResponseContext("name or ip is null");
        }
        return comResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "checkin", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbBranch> check(@RequestBody TbBranch bean) {
        log.debug("start checkin");
        ComResponse<TbBranch> comResponse = new ComResponse<TbBranch>();
        try {
            if (bean != null) {
                log.debug("check ip whether it exist in department.");
                String name = bean.getName();
                String ip = bean.getAddress();
                TbDepartment depart = new TbDepartment();
                depart.setExt1(name + "-" + ip);
                CommFindEntity<TbDepartment> resultDp = depService.findAll(depart);
                if (resultDp != null && resultDp.getResult() != null && resultDp.getResult().size() > 0) {
                    log.debug("start checkin for " + bean.getAddress());
                    bean.setCheckin(new Date());
                    int result = comService.updateData(bean);
                    log.info("update result:" + result);
                    comResponse.setResponseStatus(ComResponse.STATUS_OK);
                    comResponse.setResponseEntity(bean);
                } else {
                    comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
                    comResponse.setErrorMessage("no registered name and ip.");
                    comResponse.setExtendResponseContext("no registered name and ip.");
                }
            }
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("updateData:" + comResponse.getResponseStatus());
        return comResponse;
    }

}
