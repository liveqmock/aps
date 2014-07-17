/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.child.SavedDataEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author 305027939
 */
@Controller
@RequestMapping("/data/*")
public class SavedDataController {

    private static final Log log = LogFactory.getLog(SavedDataController.class);
    @Resource
    private LogServiceIF logService;

    @InitBinder
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @Autowired
    @Qualifier("departmentService")
    private CommServiceIF<TbDepartment, Long> depService;

    @Autowired
    @Qualifier("dataService")
    private CommServiceIF<SavedDataEntity, Long> comService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public @ResponseBody
    SavedDataEntity findDataByKey(@PathVariable Long id) {
        log.debug("findDataByKey:" + id);
        return comService.findDataByKey(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public @ResponseBody
    CommFindEntity<SavedDataEntity> findAll(@RequestParam String key, HttpServletRequest request) {
        log.debug("findAll.");
        CommFindEntity<SavedDataEntity> data = null;
        try {
            SavedDataEntity conditionEntity = new SavedDataEntity();
            conditionEntity.setKey(key);
            data = comService.findAll(conditionEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @RequestMapping(method = RequestMethod.POST, value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<SavedDataEntity> saveData(@RequestBody SavedDataEntity bean) {
        log.debug("saveData:" + bean.getKey());
        ComResponse<SavedDataEntity> comResponse = new ComResponse<SavedDataEntity>();
        try {
            TbBranch branch = bean.getBranch();
            if (branch != null && branch.getAddress() != null && !branch.getAddress().trim().equals("")
                    && branch.getName() != null && !branch.getAddress().trim().equals("")) {
                log.debug("check ip whether it exist in department.");
                String name = branch.getName();
                String ip = branch.getAddress();
                TbDepartment depart = new TbDepartment();
                depart.setExt1(name + "-" + ip);
                CommFindEntity<TbDepartment> resultDp = depService.findAll(depart);

                if (resultDp != null && resultDp.getResult() != null && resultDp.getResult().size() > 0) {
                    int result = comService.saveData(bean);
                    log.info("save result:" + result);
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

        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("saveData:" + comResponse.getResponseStatus());
        return comResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<SavedDataEntity> deleteDataByKey(@PathVariable Long id) {
        log.debug("deleteDataByKey:" + id);
        ComResponse<SavedDataEntity> comResponse = new ComResponse<SavedDataEntity>();
        try {
            int result = comService.deleteDataByKey(id);
            log.info("delete result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            logService.saveLog("删除填单机数据:" + id);
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
    ComResponse<SavedDataEntity> updateData(@RequestBody SavedDataEntity bean) {
        log.debug("updateData:" + bean.getKey());
        ComResponse<SavedDataEntity> comResponse = new ComResponse<SavedDataEntity>();
        try {
            int result = comService.updateData(bean);
            log.info("update result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            comResponse.setResponseEntity(bean);
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("updateData:" + comResponse.getResponseStatus());
        return comResponse;
    }
}
