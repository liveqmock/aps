/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.TbAuth;
import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbControl;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.service.auth.AuthServiceIF;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import com.mc.printer.server.utils.Pager;
import com.mc.printer.server.utils.ToolHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
@RequestMapping("/client/auth/*")
public class AuthController {

    private static final Log log = LogFactory.getLog(AuthController.class);
    public final static String TYPE_GUIDE_BRANCHNAME = "Branch_Name";
    @Resource
    AuthServiceIF authService;

    @Resource
    private LogServiceIF logService;

    @Autowired
    @Qualifier("departmentService")
    private CommServiceIF<TbDepartment, Long> depService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public @ResponseBody
    List<TbAuth> findAll(@RequestParam(value = "rows", defaultValue = "20") int pagesize,@RequestParam(value = "page", defaultValue = "1") int pageindex) {
        log.debug("findAll.");
        List<TbAuth> result = new ArrayList();
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
                        Pager pager = new Pager(pageindex, pagesize);
//                        if(type==0){
//                        pager = null;
//                        }
                        CommFindEntity<TbAuth> data = authService.findAuth(null,pager);
                        boolean hasSearchAdmin = false;
                        if (ext != null && ext.contains("系统查询")) {
                            hasSearchAdmin = true;
                        }

                        /*得到我所有拥有的网点的IP，包括子网点的*/
                        List extArray = new ArrayList();
                        extArray.add(department.getDepname());
                        getChild(session.getDepid(), extArray);
                        log.debug("found ext array size:" + extArray.size());
                        if (data.getResult() != null) {
                            for (TbAuth tbb : data.getResult()) {
                                String name = tbb.getBranchname();
                                log.debug("found department:" + name);
                                if (hasSearchAdmin) {
                                    result.add(tbb);
                                } else {
                                    if (extArray.contains(name)) {
                                        result.add(tbb);
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
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
                String e = tb.getDepname();
                if (e != null && !e.trim().equals("")) {
                    String[] array = e.split(",");
                    ext.add(e);
                }

                getChild(tb.getId(), ext);
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbAuth> login(@RequestBody HashMap map) {
        log.debug("login:" + map);
        ComResponse<TbAuth> com = new ComResponse<>();
        if (map != null) {
            Object userName = map.get("TextField_UserID");
            Object passwd = map.get("PasswordField");
            if (userName != null && passwd != null) {
                String encryPasswd = new String(ToolHelper.encryptBASE64(passwd.toString().getBytes()));
                log.debug("login transfer userid:" + userName + ",passwd:" + encryPasswd);
                TbAuth tbauth = authService.findLogin(userName.toString(), encryPasswd);
                if (tbauth == null) {
                    com.setExtendResponseContext("用户名和密码验证失败.");
                    com.setResponseStatus(ComResponse.STATUS_FAIL);
                    log.debug("login fail.");
                } else {
                    com.setResponseEntity(tbauth);
                    com.setResponseStatus(ComResponse.STATUS_OK);
                    log.debug("login successful.");
                    logService.saveLog(userName.toString(), map.get(TYPE_GUIDE_BRANCHNAME) == null ? "" : map.get(TYPE_GUIDE_BRANCHNAME).toString(), 0l, "填单机客户：" + userName + " 登陆成功.");
                }

            } else {
                com.setResponseStatus(ComResponse.STATUS_FAIL);
                com.setExtendResponseContext("用户名和密码为空");
                log.debug("login fail. userid or passwd is null");
            }

        } else {
            log.debug("login fail");
            com.setResponseStatus(ComResponse.STATUS_FAIL);
            com.setExtendResponseContext("服务端没有接收到任何数据.");
        }

        return com;
    }

    @RequestMapping(method = RequestMethod.POST, value = "reg", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<TbAuth> register(@RequestBody HashMap map) {
        log.debug("register:" + map);
        ComResponse<TbAuth> com = new ComResponse<>();
        if (map != null) {
            Object userName = map.get("TextField_UserID");
            Object passwd = map.get("PasswordField");
            Object branName = map.get("Branch_Name");
            if (userName != null && passwd != null) {
                try {
                    String encryPasswd = new String(ToolHelper.encryptBASE64(passwd.toString().getBytes()));
                    log.debug("register transfer userid:" + userName + ",passwd:" + encryPasswd);
                    TbAuth tbauth = new TbAuth();
                    tbauth.setUserid(userName.toString());
                    tbauth.setPasswd(encryPasswd);
                    tbauth.setBranchname(branName.toString());

                    map.remove("TextField_UserID");
                    map.remove("PasswordField");
                    Set<String> keys = map.keySet();
                    StringBuilder sb = new StringBuilder();
                    for (String key : keys) {
                        if (key.equals("PasswordField2")) {
                            continue;
                        } else {
                            sb.append(key).append(":").append(map.get(key)).append("###");
                        }
                    }
                    tbauth.setContext(sb.toString());
                    log.debug("found context:" + sb.toString());
                    authService.saveAuth(tbauth);
                    com.setResponseEntity(tbauth);
                    com.setResponseStatus(ComResponse.STATUS_OK);
                    log.debug("register successful.");
                    logService.saveLog(userName.toString(), map.get(TYPE_GUIDE_BRANCHNAME) == null ? "" : map.get(TYPE_GUIDE_BRANCHNAME).toString(), 0l, "填单机客户：" + userName + " 注册成功.");
                } catch (Exception ex) {
                    com.setResponseStatus(ComResponse.STATUS_FAIL);
                    com.setErrorMessage(ex.getLocalizedMessage());
                    com.setExtendResponseContext(ex.getLocalizedMessage());
                    log.debug("register fail." + ex.getLocalizedMessage());
                }
            } else {
                com.setResponseStatus(ComResponse.STATUS_FAIL);
                log.debug("register fail. userid or passwd is null");
            }

        } else {
            log.debug("register fail");
            com.setResponseStatus(ComResponse.STATUS_FAIL);
        }

        return com;
    }

    @RequestMapping(method = RequestMethod.POST, value = "update/{id}")
    public @ResponseBody
    int updatesStatus(@RequestParam(value = "status") Integer status, @PathVariable Long id) {
        TbAuth auth = new TbAuth();
        auth.setStatus(status);
        auth.setId(id);
        int result = authService.updatedAuth(auth);
        logService.saveLog("更新认证用户信息.用户ID：" + id + ",状态:" + status);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ComResponse<UserEntity> deleteDataByKey(@PathVariable Long id) {
        log.debug("deleteDataByKey:" + id);
        ComResponse<UserEntity> comResponse = new ComResponse<UserEntity>();
        try {
            int result = authService.deleteAuth(id);
            log.info("delete result:" + result);
            comResponse.setResponseStatus(ComResponse.STATUS_OK);
            logService.saveLog("删除注册用户ID:" + id);
        } catch (Exception e) {
            log.equals(e);
            comResponse.setResponseStatus(ComResponse.STATUS_FAIL);
            comResponse.setErrorMessage(e.getMessage());
        }
        log.debug("deleteDataByKey result:" + comResponse.getResponseStatus());
        return comResponse;
    }
}
