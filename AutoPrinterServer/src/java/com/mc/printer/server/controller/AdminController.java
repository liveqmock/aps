/*
 * 2014 Chengdu JunChen Technology
 */
package com.mc.printer.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.child.DataSearchEntity;
import com.mc.printer.server.entity.child.DataSearchResult;
import com.mc.printer.server.entity.child.InitEntity;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.service.admin.AdminServiceIF;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.saveddata.DataServiceIF;
import com.mc.printer.server.utils.DateHelper;
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
    @Qualifier("empService")
    private CommServiceIF<UserEntity, Long> userService;

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
            } else {
                first = Constants.TOPPAGE_ISSUE;
            }
        }
        log.debug("status:::." + first);
        return new ModelAndView("toppage", "first", first);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/search/filter")
    public ModelAndView findAll(HttpServletRequest request) {
        log.debug("findAll.");
        CommFindEntity<DataSearchResult> data = null;
        try {
            String danjuname = request.getParameter("danjuName");
            String key = request.getParameter("key");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String branch = request.getParameter("branch");

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
            }
            if (key != null && !key.equals("")) {
                bean.setKey(key);
            }

            data = dataSearchService.findAllCondition(bean);
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
                String key = it.next();

                List rowValue = new ArrayList();
                List piaojuNumbers = new ArrayList();
            HashMap branchNameLabels = new HashMap();
            branchNameLabels.put("label", key);
                for (String piaoju : piaojuSet) {
                    HashMap<String, Integer> map = branchMap.get(key);
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

        log.debug("get piaoju hashset:"+piaojuSet.size());
        request.setAttribute("grahicpiaoju", piaojuSet);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonScriptObj = mapper.writeValueAsString(jsonJS);
            request.setAttribute("grahicdata", jsonScriptObj);
            log.info("transfer to json data:" + jsonScriptObj);
        } catch (JsonProcessingException ex) {
            log.error(ex);
        }

        return new ModelAndView("search/list", "data", data);
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

}
