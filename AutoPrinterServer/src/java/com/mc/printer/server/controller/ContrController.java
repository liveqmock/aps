/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbControl;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.control.ControlServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 305027939
 */
@Controller
@RequestMapping("/client/cont/*")
public class ContrController {

    private static final Log log = LogFactory.getLog(ContrController.class);

    @Resource
    ControlServiceIF controlService;

    @Resource
    private LogServiceIF logService;

    @Autowired
    @Qualifier("branchService")
    private CommServiceIF<TbBranch, Long> branchService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    HashMap<String, Integer> findData(@RequestParam(value = "branchName") String branchName,
            @RequestParam(value = "guideName") String guideName) {
        HashMap<String, Integer> statusMap = new HashMap<>();
        try {
//            branchName = new String(branchName.getBytes("ISO-8859-1"), "UTF-8");
//            guideName = new String(guideName.getBytes("ISO-8859-1"), "UTF-8");
            log.debug("findDataByKey,branchName:" + branchName);
            log.debug("findDataByKey,guideName:" + guideName);

            TbControl tbauth = new TbControl();
            tbauth.setBranchname(branchName);
            tbauth.setGuidename(guideName);
            CommFindEntity<TbControl> result = controlService.findControl(tbauth);
            if (result != null && result.getCount() > 0) {
                List<TbControl> ls = result.getResult();
                if (ls != null && ls.size() > 0) {
                    for (TbControl tbcon : ls) {
                        statusMap.put(tbcon.getButtonname(), tbcon.getStatus());
                    }
                }
            }
            log.debug("findDataByKey,return statusMap size:" + statusMap.size());
        } catch (Exception ex) {
            log.error(ex);
        }
        return statusMap;
    }

    @RequestMapping(method = RequestMethod.GET, value = "all")
    public @ResponseBody
    List findAllData() {
        log.debug("findAllData");
        CommFindEntity<TbControl> finds = controlService.findControl(null);
        List<TbControl> result = finds.getResult();
        log.debug("findAllData,return result:" + result);

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public @ResponseBody
    ModelAndView findOne(@PathVariable Long id, HttpServletRequest request) {
        log.debug("findOne:" + id);
        TbControl finds = controlService.findObj(id);
        log.debug("found TbControl:" + finds);

        CommFindEntity<TbBranch> branchResult = branchService.findAll(null);
        if (branchResult != null) {
            List<TbBranch> branchs = branchResult.getResult();
            request.setAttribute("allbranch", branchs);
        }

        if (finds != null) {
            String branchnames = finds.getBranchname();
            if (branchnames != null) {
                String[] barray = branchnames.split(",");
                List ls = Arrays.asList(barray);
                request.setAttribute("mybranch", ls);
            }
        }

        return new ModelAndView("tool/controlConfig", "data", finds);
    }

    @RequestMapping(method = RequestMethod.POST, value = "update/{id}")
    public @ResponseBody
    int updateStatus(@RequestParam(value = "status") Integer status, @PathVariable Long id) {
        TbControl tbauth = new TbControl();
        tbauth.setId(id);
        tbauth.setStatus(status);
        logService.saveLog("更新业务状态.业务ID：" + id + ",状态:" + status);
        return controlService.updateControl(tbauth);
    }

    @RequestMapping(method = RequestMethod.POST, value = "updatebranch/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    int updateStatus(@RequestBody HashMap branch, @PathVariable Long id) {
        log.debug("branch:" + branch);
        try {
//            if (branch != null) {
//                branch = new String(branch.getBytes("ISO-8859-1"), "gb2312");
//            }

            String branchName="";
            if(branch!=null){
             Object obj = branch.get("branch");
             branchName = obj==null?"":obj.toString();
            }
            
            TbControl tbauth = new TbControl();
            tbauth.setId(id);
            tbauth.setBranchname(branchName);
            logService.saveLog("更新业务适用网点信息.业务ID：" + id + ",网点:" + branch);
            return controlService.updateControl(tbauth);
        } catch (Exception ex) {
            log.error(ex);
        }
        return 0;
    }
}
