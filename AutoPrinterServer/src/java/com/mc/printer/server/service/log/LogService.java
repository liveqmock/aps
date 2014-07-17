/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.log;

import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbLog;
import com.mc.printer.server.entity.TbLogExample;
import com.mc.printer.server.entity.child.LogSearchEntity;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.mapper.TbLogMapper;
import com.mc.printer.server.service.pkkey.PkgeneratorServiceIF;
import com.mc.printer.server.utils.Pager;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author 305027939
 */
@Service
public class LogService implements LogServiceIF {

    private static final Log log = LogFactory.getLog(LogService.class);

    @Resource
    TbLogMapper mapper;

    @Resource
    PkgeneratorServiceIF pkService;

    @Override
    public TbLog findDataByKey(Long id) {
        log.debug("findDataByKey:" + id);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TbLog> findAll(LogSearchEntity conditionEntity, Pager page) {
        List<TbLog> result = null;
        log.debug("findAll");

        TbLogExample example = new TbLogExample();
        TbLogExample.Criteria crit = example.createCriteria();
        if (conditionEntity != null) {

            if (conditionEntity.getUsername() != null && !conditionEntity.getUsername().trim().equals("")) {
                crit.andUsernameLike("%" + conditionEntity.getUsername() + "%");
            }

            if (conditionEntity.getDepname() != null && !conditionEntity.getDepname().trim().equals("")) {
                crit.andDepnameLike("%" + conditionEntity.getDepname() + "%");
            }

            if (conditionEntity.getDepid() != null && conditionEntity.getDepid() > 0) {
                crit.andDepidEqualTo(conditionEntity.getDepid());
            }

            if (conditionEntity.getContext() != null && !conditionEntity.getContext().trim().equals("")) {
                crit.andContextLike("%" + conditionEntity.getContext() + "%");
            }

            if (conditionEntity.getStartDate() != null && conditionEntity.getEndDate() != null) {
                crit.andEventdateBetween(conditionEntity.getStartDate(), conditionEntity.getEndDate());
            } else if (conditionEntity.getStartDate() != null) {
                crit.andEventdateGreaterThanOrEqualTo(conditionEntity.getStartDate());
            } else if (conditionEntity.getEndDate() != null) {
                crit.andEventdateLessThanOrEqualTo(conditionEntity.getEndDate());
            }
        }

        /*分页*/
        int count = mapper.countByExample(example);
        log.debug("count is " + count);
        page.setCount(count);
        example.setLimit(page.getPageSize());
        example.setOffset(page.getStartDataIndex());
        example.setOrderByClause("eventdate desc");

        if (count > 0) {
            result = mapper.selectByExample(example);
            if (result != null) {
                log.debug("result:" + result.size());
            } else {
                log.debug("result is null");
            }
        }
        return result;
    }

    @Override
    public int saveData(TbLog bean) {
        log.debug("saveData:" + bean);
        if (bean != null) {
            long key = pkService.getPrimaryKey("log", "id");
            bean.setId(key);
            return mapper.insertSelective(bean);
        }
        return 0;
    }

    @Override
    public int deleteDataByKey(Long id) {
        log.debug("deleteDataByKey:" + id);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateData(TbLog bean) {
        log.debug("updateData:" + bean.getId());
        return mapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public void saveLog(String context) {
        if (context != null && !context.trim().equals("")) {
            log.debug("start to write log.");
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                try {
                    if (principal instanceof LoginUserDetails) {
                        LoginUserDetails user = (LoginUserDetails) principal;
                        log.info("Got UserDetails object." + user.getUsername());
                        UserEntity session = user.getUserSessionEntity();
                        TbDepartment department = session.getDepartment();

                        TbLog tblog = new TbLog();
                        tblog.setEventdate(new Date());
                        tblog.setDepid(session.getDepid());
                        tblog.setDepname(department.getDepname());
                        tblog.setUsername(session.getName());
                        tblog.setContext(context);
                        saveData(tblog);
                        log.debug("write done for log. username:" + session.getName());
                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    @Override
    public void saveLog(String user, String dept, Long deptid, String context) {
        log.debug("start to write log.");
        TbLog tblog = new TbLog();
        tblog.setEventdate(new Date());
        tblog.setDepid(deptid);
        tblog.setDepname(dept);
        tblog.setUsername(user);
        tblog.setContext(context);
        saveData(tblog);
        log.debug("write done for log. username:" + user);
    }

    @Override
    public int deleteData(LogSearchEntity conditionEntity) {
       log.debug("deleteData："+conditionEntity);
        TbLogExample example = new TbLogExample();
        TbLogExample.Criteria crit = example.createCriteria();
        if (conditionEntity != null) {

            if (conditionEntity.getUsername() != null && !conditionEntity.getUsername().trim().equals("")) {
                crit.andUsernameLike("%" + conditionEntity.getUsername() + "%");
            }

            if (conditionEntity.getDepname() != null && !conditionEntity.getDepname().trim().equals("")) {
                crit.andDepnameLike("%" + conditionEntity.getDepname() + "%");
            }

            if (conditionEntity.getDepid() != null && conditionEntity.getDepid() > 0) {
                crit.andDepidEqualTo(conditionEntity.getDepid());
            }

            if (conditionEntity.getContext() != null && !conditionEntity.getContext().trim().equals("")) {
                crit.andContextLike("%" + conditionEntity.getContext() + "%");
            }

            if (conditionEntity.getStartDate() != null && conditionEntity.getEndDate() != null) {
                crit.andEventdateBetween(conditionEntity.getStartDate(), conditionEntity.getEndDate());
            } else if (conditionEntity.getStartDate() != null) {
                crit.andEventdateGreaterThanOrEqualTo(conditionEntity.getStartDate());
            } else if (conditionEntity.getEndDate() != null) {
                crit.andEventdateLessThanOrEqualTo(conditionEntity.getEndDate());
            }
        }

        return mapper.deleteByExample(example);
    }

}
