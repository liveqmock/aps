/*
 * 2014 Chengdu JunChen Technology
 */
package com.mc.printer.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbLog;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.child.DataSearchEntity;
import com.mc.printer.server.entity.child.DataSearchResult;
import com.mc.printer.server.entity.child.InitEntity;
import com.mc.printer.server.entity.child.LogSearchEntity;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.service.admin.AdminServiceIF;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.log.LogServiceIF;
import com.mc.printer.server.service.saveddata.DataServiceIF;
import com.mc.printer.server.utils.DateHelper;
import com.mc.printer.server.utils.Pager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
    @Qualifier("departmentService")
    private CommServiceIF<TbDepartment, Long> depService;

    @Resource
    private LogServiceIF logService;

    @InitBinder
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @Resource
    private DataServiceIF dataSearchService;

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
                logService.saveLog("初始化系统.");
            } else {
                first = Constants.TOPPAGE_ISSUE;
            }
        }
        log.debug("status:::." + first);
        return new ModelAndView("toppage", "first", first);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/log/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView findLog(HttpServletRequest request) {
        log.debug("findLog.");
        List<TbLog> data = new ArrayList();
        String username = request.getParameter("user");
        String key = request.getParameter("key");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String depnamepage = request.getParameter("depname");
        String pagesizeStr = request.getParameter("pagesize");
        String pageindexStr = request.getParameter("pageindex");

        Long pageDepid = 0l;
        if (depnamepage != null && !depnamepage.trim().equals("")) {
            try {
                pageDepid = Long.valueOf(depnamepage);
            } catch (Exception e) {
                log.error(e);
            }
        }

        int pagesize = 20;
        int pageindex = 1;

        List<Long> myDepArray = null;

        LogSearchEntity conditionEntity = new LogSearchEntity();
        if (startDate != null && !startDate.equals("")) {
            conditionEntity.setStartDate(DateHelper.getStringtoDate(startDate, "yyyy-MM-dd"));
        }
        if (endDate != null && !endDate.equals("")) {
            conditionEntity.setEndDate(DateHelper.getStringtoDate(endDate, "yyyy-MM-dd"));
        }
        if (key != null && !key.equals("")) {
            conditionEntity.setContext(key);
        }
        if (username != null && !username.equals("")) {
            conditionEntity.setUsername(username);
        }

        if (pagesizeStr != null && !pagesizeStr.trim().equals("")) {
            try {
                pagesize = Integer.valueOf(pagesizeStr);
            } catch (NumberFormatException e) {
                log.error(e);
            }
        }
        if (pageindexStr != null && !pageindexStr.trim().equals("")) {
            try {
                pageindex = Integer.valueOf(pageindexStr);
            } catch (NumberFormatException e) {
                log.error(e);
            }
        }

        {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                try {
                    if (principal instanceof LoginUserDetails) {
                        LoginUserDetails user = (LoginUserDetails) principal;
                        log.info("Got UserDetails object." + user.getUsername());
                        UserEntity session = user.getUserSessionEntity();
                        if (session != null) {
                            TbRole role = session.getRole();
                            long depid = session.getDepid();
                            List<Long> depArray = new ArrayList();

                            boolean hasSearchAdmin = false;
                            if (role != null && role.getExt1().contains("系统查询")) {
                                hasSearchAdmin = true;
                            }

                            //不是超级用户的情况下，查出我的部门和子部门
                            if (!hasSearchAdmin) {
                                getChild(depid, depArray);
                            }

                            if (hasSearchAdmin) {
                                if (pageDepid > 0) {
                                    conditionEntity.setDepid(pageDepid);
                                }
                            } else if (pageDepid > 0) {
                                if (depArray.contains(pageDepid)) {

                                    myDepArray = new ArrayList(); //选择的部门和子部门
                                    boolean isfound = false;
                                    for (Long dep : depArray) {
                                        if (dep.longValue() == pageDepid) {
                                            isfound = true;
                                        }
                                        if (isfound) {
                                            myDepArray.add(dep);
                                        }
                                    }

                                } else { //选择了自己部门以外的部门
                                    request.setAttribute("condition", conditionEntity);
                                    return new ModelAndView("search/log", "data", data);
                                }
                            } else {
                                myDepArray = depArray;
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }

        Pager pager = new Pager(pageindex, pagesize);
        data = logService.findAll(conditionEntity, pager);

        if (myDepArray != null && data != null && data.size() > 0) { //需要找部门和子部门数据
            List<TbLog> newdata = new ArrayList();
            for (TbLog tb : data) {
                if (myDepArray.contains(tb.getDepid())) {
                    newdata.add(tb);
                }
            }
            conditionEntity.setDepid(pageDepid);
            request.setAttribute("condition", conditionEntity);
            request.setAttribute("pager", pager);
            return new ModelAndView("search/log", "data", newdata);
        }
        request.setAttribute("pager", pager);
        request.setAttribute("condition", conditionEntity);
        return new ModelAndView("search/log", "data", data);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/log/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteLog(HttpServletRequest request) {
        log.debug("deleteLog.");
        List<TbLog> data = new ArrayList();
        String username = request.getParameter("user");
        String key = request.getParameter("key");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String depnamepage = request.getParameter("depname");

        Long pageDepid = 0l;
        if (depnamepage != null && !depnamepage.trim().equals("")) {
            try {
                pageDepid = Long.valueOf(depnamepage);
            } catch (Exception e) {
                log.error(e);
            }
        }

        List<Long> myDepArray = null;

        LogSearchEntity conditionEntity = new LogSearchEntity();
        if (startDate != null && !startDate.equals("")) {
            conditionEntity.setStartDate(DateHelper.getStringtoDate(startDate, "yyyy-MM-dd"));
        }
        if (endDate != null && !endDate.equals("")) {
            conditionEntity.setEndDate(DateHelper.getStringtoDate(endDate, "yyyy-MM-dd"));
        }
        if (key != null && !key.equals("")) {
            conditionEntity.setContext(key);
        }
        if (username != null && !username.equals("")) {
            conditionEntity.setUsername(username);
        }

        {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                try {
                    if (principal instanceof LoginUserDetails) {
                        LoginUserDetails user = (LoginUserDetails) principal;
                        log.info("Got UserDetails object." + user.getUsername());
                        UserEntity session = user.getUserSessionEntity();
                        if (session != null) {
                            TbRole role = session.getRole();
                            long depid = session.getDepid();
                            List<Long> depArray = new ArrayList();

                            boolean hasSearchAdmin = false;
                            if (role != null && role.getExt1().contains("系统查询")) {
                                hasSearchAdmin = true;
                            }

                            //不是超级用户的情况下，查出我的部门和子部门
                            if (!hasSearchAdmin) {
                                getChild(depid, depArray);
                            }

                            if (hasSearchAdmin) {
                                if (pageDepid > 0) {
                                    conditionEntity.setDepid(pageDepid);
                                }
                            } else if (pageDepid > 0) {
                                if (depArray.contains(pageDepid)) {

                                    myDepArray = new ArrayList(); //选择的部门和子部门
                                    boolean isfound = false;
                                    for (Long dep : depArray) {
                                        if (dep.longValue() == pageDepid) {
                                            isfound = true;
                                        }
                                        if (isfound) {
                                            myDepArray.add(dep);
                                        }
                                    }

                                } else { //选择了自己部门以外的部门
                                    request.setAttribute("condition", conditionEntity);
                                    return new ModelAndView("search/log", "data", data);
                                }
                            } else {
                                myDepArray = depArray;
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }

        if (myDepArray != null) { //需要找部门和子部门数据
            for (TbLog tb : data) {
                if (myDepArray.contains(tb.getDepid())) {
                    Long id = tb.getId();
                    logService.deleteDataByKey(id);
                }
            }
            conditionEntity.setDepid(pageDepid);
            request.setAttribute("condition", conditionEntity);
            return new ModelAndView("search/log", "data", data);
        }

        logService.deleteData(conditionEntity);
        request.setAttribute("condition", conditionEntity);
        return new ModelAndView("search/form");
    }

    private void getChild(long pid, List<Long> ext) {
        TbDepartment conditionEntity = new TbDepartment();
        conditionEntity.setDepfather(pid);
        CommFindEntity<TbDepartment> data = depService.findAll(conditionEntity);
        if (data != null) {
            List<TbDepartment> ls = data.getResult();
            for (TbDepartment tb : ls) {
                ext.add(tb.getId());
                getChild(tb.getId(), ext);
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/search/data")
    public ModelAndView findAllData(HttpServletRequest request) {
        log.debug("findAll.");
        CommFindEntity<DataSearchResult> data = null;
        String danjuname = request.getParameter("danjuName");
        String key = request.getParameter("key");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String branch = request.getParameter("branch");
        String pagesizeStr = request.getParameter("pagesize");
        String pageindexStr = request.getParameter("pageindex");

        try {

            int pagesize = 20;
            int pageindex = 1;
            boolean hasSearchAdmin = false;

            if (pagesizeStr != null && !pagesizeStr.trim().equals("")) {
                try {
                    pagesize = Integer.valueOf(pagesizeStr);
                } catch (NumberFormatException e) {
                    log.error(e);
                }
            }
            if (pageindexStr != null && !pageindexStr.trim().equals("")) {
                try {
                    pageindex = Integer.valueOf(pageindexStr);
                } catch (NumberFormatException e) {
                    log.error(e);
                }
            }

            DataSearchEntity bean = new DataSearchEntity();
            if (danjuname != null && !danjuname.equals("")) {
                bean.setDanjuName(danjuname);
            }
            if (startDate != null && !startDate.equals("")) {
                bean.setStartDate(DateHelper.getStringtoDate(startDate, "yyyy-MM-dd"));
            }
            if (endDate != null && !endDate.equals("")) {
                bean.setEndDate(DateHelper.getStringtoDate(endDate, "yyyy-MM-dd"));
            }

            if (branch != null && !branch.equals("")) {
                try {
                    bean.setBranchid(Long.valueOf(branch));
                } catch (Exception e) {
                    log.error(e);
                }
            } else {
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
                                if (ext != null && ext.contains("系统查询")) {
                                    hasSearchAdmin = true;
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }

            if (key != null && !key.equals("")) {
                bean.setKey(key);
            }
            Pager pager = new Pager(pageindex, pagesize);
            if (branch == null || branch.trim().equals("")) {
                if (hasSearchAdmin) {

                    data = dataSearchService.findAllCondition(bean, pager);
                }
            } else {

                data = dataSearchService.findAllCondition(bean, pager);
            }
            if (branch != null && !branch.equals("") && data != null && data.getResult() != null && data.getResult().size() > 0) {
                TbBranch branchBean = new TbBranch();
                DataSearchResult result = data.getResult().get(0);
                branchBean.setName(result.getBranchName());
                bean.setBranch(branchBean);
            }
            request.setAttribute("pager", pager);
            request.setAttribute("condition", bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String conditionStr = "表单页:" + danjuname + ",关键字:" + key + ",开始时间:" + startDate + ",结束:" + endDate + ",网点:" + branch;
        logService.saveLog("查询业务数据信息." + conditionStr);
        return new ModelAndView("search/data", "data", data);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/search/filter")
    public ModelAndView findAll(HttpServletRequest request) {
        log.debug("findAll.");
        CommFindEntity<DataSearchResult> data = null;
        String danjuname = request.getParameter("danjuName");
        String key = request.getParameter("key");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String branch = request.getParameter("branch");

        try {

            boolean hasSearchAdmin = false;

            DataSearchEntity bean = new DataSearchEntity();
            if (danjuname != null && !danjuname.equals("")) {
                bean.setDanjuName(danjuname);
            }
            if (startDate != null && !startDate.equals("")) {
                bean.setStartDate(DateHelper.getStringtoDate(startDate, "yyyy-MM-dd"));
            }
            if (endDate != null && !endDate.equals("")) {
                bean.setEndDate(DateHelper.getStringtoDate(endDate, "yyyy-MM-dd"));
            }

            if (branch != null && !branch.equals("")) {
                try {
                    bean.setBranchid(Long.valueOf(branch));
                } catch (Exception e) {
                    log.error(e);
                }
            } else {
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
                                if (ext != null && ext.contains("系统查询")) {
                                    hasSearchAdmin = true;
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }

            if (key != null && !key.equals("")) {
                bean.setKey(key);
            }

            if (branch == null || branch.trim().equals("")) {
                if (hasSearchAdmin) {
                    data = dataSearchService.findAllCondition(bean, null);
                }
            } else {
                data = dataSearchService.findAllCondition(bean, null);
            }
            if (branch != null && !branch.equals("") && data != null && data.getResult() != null && data.getResult().size() > 0) {
                TbBranch branchBean = new TbBranch();
                DataSearchResult result = data.getResult().get(0);
                branchBean.setName(result.getBranchName());
                bean.setBranch(branchBean);
            }
            request.setAttribute("condition", bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*graph table*/
        HashMap<String, HashMap<String, Integer>> branchMap = new HashMap();
        Set<String> piaojuSet = new HashSet();
        if (data != null && data.getResult() != null && data.getResult().size() > 0) {
            List<DataSearchResult> ls = data.getResult();

            for (DataSearchResult ds : ls) {
                String bname = ds.getBranchName();
                String danju = ds.getDanjuName();
                piaojuSet.add(danju);
                if (branchMap.containsKey(bname)) {
                    HashMap<String, Integer> dmap = branchMap.get(bname);
                    if (dmap.containsKey(danju)) {
                        int count = dmap.get(danju) + 1;
                        dmap.put(danju, count);
                    } else {
                        dmap.put(danju, 1);
                    }
                } else {
                    HashMap<String, Integer> dmap = new HashMap();
                    dmap.put(danju, 1);
                    branchMap.put(bname, dmap);
                }
            }
        }

        List<List> jsonJS = new ArrayList();
        Set<String> branchm = branchMap.keySet();
        if (branchm != null) {
            Iterator<String> it = branchm.iterator();
            while (it.hasNext()) {
                String keybran = it.next();

                List rowValue = new ArrayList();
                List piaojuNumbers = new ArrayList();
                HashMap branchNameLabels = new HashMap();
                branchNameLabels.put("label", keybran);
                for (String piaoju : piaojuSet) {
                    HashMap<String, Integer> map = branchMap.get(keybran);
                    if (map.containsKey(piaoju)) {
                        piaojuNumbers.add(map.get(piaoju));
                    } else {
                        piaojuNumbers.add(0);
                    }
                }

                rowValue.add(piaojuNumbers); //增加票据数据
                rowValue.add(branchNameLabels);
                jsonJS.add(rowValue);
            }
        }

        log.debug("get piaoju hashset:" + piaojuSet.size());
        request.setAttribute("grahicpiaoju", piaojuSet);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonScriptObj = mapper.writeValueAsString(jsonJS);
            request.setAttribute("grahicdata", jsonScriptObj);
            log.info("transfer to json data:" + jsonScriptObj);
        } catch (JsonProcessingException ex) {
            log.error(ex);
        }
        String conditionStr = "表单页:" + danjuname + ",关键字:" + key + ",开始时间:" + startDate + ",结束:" + endDate + ",网点:" + branch;
        logService.saveLog("查询业务图表信息." + conditionStr);
        return new ModelAndView("search/list");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/search/data/delete")
    public ModelAndView deleteAllData(HttpServletRequest request) {
        log.debug("deleteAllData.");
        CommFindEntity<DataSearchResult> data = null;
        String danjuname = request.getParameter("danjuName");
        String key = request.getParameter("key");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String branch = request.getParameter("branch");

        try {

            boolean hasSearchAdmin = false;

            DataSearchEntity bean = new DataSearchEntity();
            if (danjuname != null && !danjuname.equals("")) {
                bean.setDanjuName(danjuname);
            }
            if (startDate != null && !startDate.equals("")) {
                bean.setStartDate(DateHelper.getStringtoDate(startDate, "yyyy-MM-dd"));
            }
            if (endDate != null && !endDate.equals("")) {
                bean.setEndDate(DateHelper.getStringtoDate(endDate, "yyyy-MM-dd"));
            }

            if (branch != null && !branch.equals("")) {
                try {
                    bean.setBranchid(Long.valueOf(branch));
                } catch (Exception e) {
                    log.error(e);
                }
            } else {
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
                                if (ext != null && ext.contains("系统查询")) {
                                    hasSearchAdmin = true;
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }

            if (key != null && !key.equals("")) {
                bean.setKey(key);
            }

            if (branch == null || branch.trim().equals("")) {
                if (hasSearchAdmin) {
                    dataSearchService.deleteAllCondition(bean);
                }
            } else {
                dataSearchService.deleteAllCondition(bean);
            }
            if (branch != null && !branch.equals("") && data != null && data.getResult() != null && data.getResult().size() > 0) {
                TbBranch branchBean = new TbBranch();
                DataSearchResult result = data.getResult().get(0);
                branchBean.setName(result.getBranchName());
                bean.setBranch(branchBean);
            }
            request.setAttribute("condition", bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String conditionStr = "表单页:" + danjuname + ",关键字:" + key + ",开始时间:" + startDate + ",结束:" + endDate + ",网点:" + branch;
        logService.saveLog("删除业务数据." + conditionStr);
        return new ModelAndView("search/form");
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

    @RequestMapping(method = RequestMethod.GET, value = "/open")
    public ModelAndView f() {
        return new ModelAndView("open");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/search")
    public ModelAndView g() {
        return new ModelAndView("search/form");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/control")
    public ModelAndView h() {
        return new ModelAndView("tool/control");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/auth")
    public ModelAndView i() {
        return new ModelAndView("branch/auth");
    }
}
