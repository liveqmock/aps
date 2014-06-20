/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mc.printer.server.controller;

import com.mc.printer.server.controller.common.ComResponse;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.service.emp.LoginServiceIF;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author 305027939
 */
@Controller
public class LoginController {
   private static final Log log = LogFactory.getLog(LoginController.class);
    @Resource
    LoginServiceIF loginService;
   

    @RequestMapping(method = RequestMethod.GET, value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ModelAndView login() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof LoginUserDetails) {
                LoginUserDetails user = (LoginUserDetails) principal;
                log.info("LoginController,Got UserDetails object." + user.getUsername());
                UserEntity session = user.getUserSessionEntity();
                if (session != null) {
                    log.info("LoginController," + session.getName() + ",logined.");
                } else {
                    log.error("LoginController,user is not exist.");
                }

                return new ModelAndView("main","user",session);
            } else {
                log.error("LoginController,login failed. principal is not a  UserDetails object!");
                return null;
            }
        } else {
            log.error("LoginController,login failed. principal is null!");
            return null;
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout/clear")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ComResponse<String> comResponse = new ComResponse<String>();
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //使当前会话失效       
        }
        log.info("LoginController,logout.invalidate session");
        comResponse.setResponseStatus(ComResponse.STATUS_OK);
        return new ModelAndView("toppage");
    } 
}
