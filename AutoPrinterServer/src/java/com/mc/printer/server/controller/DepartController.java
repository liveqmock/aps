package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    private LogServiceIF logService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public @ResponseBody
    TbDepartment findDataByKey(@PathVariable Long id) {
        log.debug("findDataByKey:" + id);
        return comService.findDataByKey(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List findAll() {
        log.debug("findAll.");
        List all = new ArrayList();
        long pid = 0;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            try {
                if (principal instanceof LoginUserDetails) {
                    LoginUserDetails user = (LoginUserDetails) principal;
                    log.info("Got UserDetails object." + user.getUsername());
                    UserEntity session = user.getUserSessionEntity();
                    TbRole role = session.getRole();

                    pid = session.getDepid();

                    if (role != null) {
                        String ext = role.getExt1();
                        if (ext != null && ext.contains("系统查询")) {
                            pid = 0;
                        }
                    }

                    List childarray = new ArrayList();
                    getChild(pid, childarray, 0);

                    TbDepartment department = session.getDepartment();
                    if (department != null) {
                        HashMap mapLevel1 = new HashMap();
                        mapLevel1.put("id", department.getId());
                        mapLevel1.put("text", department.getDepname());
                        mapLevel1.put("desc", department.getDescms());
                        mapLevel1.put("pid", department.getDepfather());
                        mapLevel1.put("ext1", department.getExt1());
                        if (childarray.size() > 0) {
                            mapLevel1.put("children", childarray);
                            mapLevel1.put("state", "closed");
                        }

                        all.add(mapLevel1);
                    }

                }
            } catch (Exception e) {
                log.error(e);
            }
        }

        return all;
    }

    @RequestMapping(method = RequestMethod.GET, value = "tree", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List findAllTree(@RequestParam(value = "status", defaultValue = "0") int status) {
        log.debug("findAll.");
        List result = new ArrayList();
        try {

            HashMap map = new HashMap();
            List child = new ArrayList();
            map.put("id", 0);
            map.put("text", "请选择部门");
            map.put("desc", "");
            map.put("children", child);
            result.add(map);

            long pid = 0;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                try {
                    if (principal instanceof LoginUserDetails) {
                        LoginUserDetails user = (LoginUserDetails) principal;
                        log.info("Got UserDetails object." + user.getUsername());
                        UserEntity session = user.getUserSessionEntity();
                        TbRole role = session.getRole();

                        pid = session.getDepid();

                        if (role != null) {
                            String ext = role.getExt1();
                            if (ext != null && ext.contains("系统查询")) {
                                pid = 0;
                            }
                        }

                        List childarray = new ArrayList();
                        getChild(pid, childarray, status);

                        TbDepartment department = session.getDepartment();
                        if (department != null) {
                            HashMap mapLevel1 = new HashMap();
                            mapLevel1.put("id", department.getId());
                            mapLevel1.put("text", department.getDepname());
                            mapLevel1.put("desc", department.getDescms());
                            mapLevel1.put("pid", department.getDepfather());
                            mapLevel1.put("ext1", department.getExt1());
                            if (childarray.size() > 0) {
                                mapLevel1.put("children", childarray);
                                if (status == 0) {
                                    mapLevel1.put("state", "closed");
                                }
                            }

                            child.add(mapLevel1);
                        }

                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        return result;
    }

    private void getChild(long pid, List child, int status) {
        TbDepartment conditionEntity = new TbDepartment();
        conditionEntity.setDepfather(pid);
        CommFindEntity<TbDepartment> data = comService.findAll(conditionEntity);
        if (data != null) {
            List<TbDepartment> ls = data.getResult();
            for (TbDepartment tb : ls) {
                HashMap mapLevel1 = new HashMap();
                mapLevel1.put("id", tb.getId());
                mapLevel1.put("text", tb.getDepname());
                mapLevel1.put("desc", tb.getDescms());
                mapLevel1.put("pid", pid);
                mapLevel1.put("ext1", tb.getExt1());

                List children = new ArrayList();
                getChild(tb.getId(), children, status);
                if (children.size() > 0) {
                    mapLevel1.put("children", children);
                    if (status == 0) {
                        mapLevel1.put("state", "closed");
                    }
                }
                child.add(mapLevel1);
            }
        }
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
            logService.saveLog("新建部门:" + bean.getDepname());
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
            logService.saveLog("删除部门ID:" + id);
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
            logService.saveLog("更新部门信息:" + bean.getId());
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("updateData:" + comResponse.getResponseStatus());
        return comResponse;
    }

}
