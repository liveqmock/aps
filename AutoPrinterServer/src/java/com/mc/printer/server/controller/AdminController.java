/*
 * 2014 Chengdu JunChen Technology
 */
package com.mc.printer.server.controller;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.child.InitEntity;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.service.admin.AdminServiceIF;
import com.mc.printer.server.service.common.CommServiceIF;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
public class AdminController {

    private static final Log log = LogFactory.getLog(AdminController.class);

    @Autowired
    @Qualifier("empService")
    private CommServiceIF<UserEntity, Long> comService;

    @Resource
    private AdminServiceIF adminServie;

    @Autowired
    @Qualifier("empService")
    private CommServiceIF<UserEntity, Long> userService;

    @InitBinder
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    /**
     * 这里很特殊，是整个软件的toppage。
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/toppage")
    public ModelAndView toppage() {
        log.info("AdminController,toppage.");

        CommFindEntity<UserEntity> data = null;
        try {
            data = comService.findAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int first = 0;
        if (data == null || data.getCount() == 0) {
            first = Constants.TOPPAGE_NEEDINIT; //是第一次运行
        }
        //首先检查是不是第一次运行
        return new ModelAndView("toppage", "first", first);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/main")
    public ModelAndView mainworkspace() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof LoginUserDetails) {
                LoginUserDetails user = (LoginUserDetails) principal;
                log.info("AdminController,Got UserDetails object." + user.getUsername());
                UserEntity session = user.getUserSessionEntity();
                return new ModelAndView("main", "user", session);
            } else {
                return new ModelAndView("toppage", "first", Constants.TOPPAGE_TIMEOUT);
            }
        } else {
            return new ModelAndView("toppage", "login", null);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView postInitDB(@RequestBody InitEntity eneity) {
        log.info("init database information." + eneity.getName());
        int first = 0;
        if (eneity.getName() == null || eneity.getDepartmentName() == null || eneity.getPassword() == null
                || eneity.getName().trim().equals("") || eneity.getDepartmentName().trim().equals("") || eneity.getPassword().trim().equals("")) {
            first = Constants.TOPPAGE_NEEDINIT;
        } else {
            CommFindEntity<UserEntity> user = comService.findAll(null);
            if (user == null || user.getCount() <= 0) {
                adminServie.saveDataBase(eneity);
                first = Constants.TOPPAGE_INITDONE;
            } else {
                first = Constants.TOPPAGE_ISSUE;
            }
        }
        log.debug("status:::." + first);
        return new ModelAndView("toppage", "first", first);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/user")
    public ModelAndView a() {
        return new ModelAndView("emp/list");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/dep")
    public ModelAndView b() {
        return new ModelAndView("dep/list");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/role")
    public ModelAndView c() {
        return new ModelAndView("role/list");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/branch")
    public ModelAndView d() {
        return new ModelAndView("branch/list");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/tool")
    public ModelAndView e() {
        return new ModelAndView("tool/list");
    }

}
