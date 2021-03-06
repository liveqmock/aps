/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 305027939
 */
@Controller
@RequestMapping("/role/*")
public class RoleController {

    private static final Log log = LogFactory.getLog(RoleController.class);

    @Autowired
    @Qualifier("roleService")
    private CommServiceIF<TbRole, Long> comService;

    @Resource
    private LogServiceIF logService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public @ResponseBody
    TbRole findDataByKey(@PathVariable Long id) {
        log.debug("findDataByKey:" + id);
        return comService.findDataByKey(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "detail/{id}")
    public ModelAndView findDataByKeyPlus(@PathVariable Long id, HttpServletRequest request) {
        log.debug("findDataByKey:" + id);
        TbRole t = comService.findDataByKey(id);
        if (t != null && t.getExt1() != null) {
            String ext = t.getExt1();
            String[] exts = ext.split(",");
            List ls = Arrays.asList(exts);
            request.setAttribute("extarray", ls);
        }
        return new ModelAndView("role/edit", "data", t);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public @ResponseBody
    List<TbRole> findAll() {
        log.debug("findAll.");
        CommFindEntity<TbRole> data = null;
        try {
            data = comService.findAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data == null ? null : data.getResult();
    }

    @RequestMapping(method = RequestMethod.POST, value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbRole> saveData(@RequestBody TbRole bean) {
        log.debug("saveData:" + bean.getRolename());
        ComResponse<TbRole> comResponse = new ComResponse<TbRole>();
        try {
            int result = comService.saveData(bean);
            log.info("save result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            comResponse.setResponseEntity(bean);
            logService.saveLog("新建角色:" + bean.getRolename());
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
    ComResponse<TbRole> deleteDataByKey(@PathVariable Long id) {
        log.debug("deleteDataByKey:" + id);
        ComResponse<TbRole> comResponse = new ComResponse<TbRole>();
        try {
            int result = comService.deleteDataByKey(id);
            log.info("delete result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            logService.saveLog("删除角色ID:" + id);
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
    ComResponse<TbRole> updateData(@RequestBody TbRole bean) {
        log.debug("updateData:" + bean.getRolename());
        ComResponse<TbRole> comResponse = new ComResponse<TbRole>();
        try {
            int result = comService.updateData(bean);
            log.info("update result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            comResponse.setResponseEntity(bean);
            logService.saveLog("更新角色:" + bean.getId());
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("updateData:" + comResponse.getResponseStatus());
        return comResponse;
    }
}
