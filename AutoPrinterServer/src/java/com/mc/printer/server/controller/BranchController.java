/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.service.common.CommServiceIF;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
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
        CommFindEntity<TbBranch> data = null;
        try {
            data = comService.findAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data == null ? null : data.getResult();
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
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("updateData:" + comResponse.getResponseStatus());
        return comResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "checkin", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbBranch> check(@RequestBody TbBranch bean) {
        log.debug("start checkin");
        ComResponse<TbBranch> comResponse = new ComResponse<TbBranch>();
        try {

            if (bean != null) {
                log.debug("start checkin for "+bean.getAddress());
                bean.setCheckin(new Date());

                int result = comService.updateData(bean);
                log.info("update result:" + result);
                comResponse.setResponseStatus(ComResponse.STATUS_OK);
                comResponse.setResponseEntity(bean);
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
