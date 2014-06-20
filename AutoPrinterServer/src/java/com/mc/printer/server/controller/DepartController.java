package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.service.common.CommServiceIF;
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
@RequestMapping("/dep/*")
public class DepartController {

    private static final Log log = LogFactory.getLog(DepartController.class);

    @Autowired
    @Qualifier("departmentService")
    private CommServiceIF<TbDepartment, Long> comService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public @ResponseBody
    TbDepartment findDataByKey(@PathVariable Long id) {
        log.debug("findDataByKey:" + id);
        return comService.findDataByKey(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public @ResponseBody
    List<TbDepartment> findAll() {
        log.debug("findAll.");
        CommFindEntity<TbDepartment> data = null;
        try {
            data = comService.findAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data == null ? null : data.getResult();
    }

    @RequestMapping(method = RequestMethod.POST, value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbDepartment> saveData(@RequestBody TbDepartment bean) {
        log.debug("saveData:" + bean.getDepname());
        ComResponse<TbDepartment> comResponse = new ComResponse<TbDepartment>();
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
    ComResponse<TbDepartment> deleteDataByKey(@PathVariable Long id) {
        log.debug("deleteDataByKey:" + id);
        ComResponse<TbDepartment> comResponse = new ComResponse<TbDepartment>();
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
    ComResponse<TbDepartment> updateData(@RequestBody TbDepartment bean) {
        log.debug("updateData:" + bean.getDepname());
        ComResponse<TbDepartment> comResponse = new ComResponse<TbDepartment>();
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
