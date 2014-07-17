/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.TbAuth;
import com.mc.printer.server.service.auth.AuthServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import com.mc.printer.server.utils.ToolHelper;
import java.util.HashMap;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
                    logService.saveLog(userName.toString(),map.get(TYPE_GUIDE_BRANCHNAME)==null?"":map.get(TYPE_GUIDE_BRANCHNAME).toString(),0l,"填单机客户："+userName+" 登陆成功.");
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
                        sb.append(key).append(":").append(map.get(key)).append("###");
                    }
                    tbauth.setContext(sb.toString());
                    log.debug("found context:" + sb.toString());
                    authService.saveAuth(tbauth);
                    com.setResponseEntity(tbauth);
                    com.setResponseStatus(ComResponse.STATUS_OK);
                    log.debug("register successful.");
                    logService.saveLog(userName.toString(),map.get(TYPE_GUIDE_BRANCHNAME)==null?"":map.get(TYPE_GUIDE_BRANCHNAME).toString(),0l,"填单机客户："+userName+" 注册成功.");
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
}
