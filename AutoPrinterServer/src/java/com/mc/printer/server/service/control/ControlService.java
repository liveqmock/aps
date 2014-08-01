/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.control;

import com.mc.printer.server.entity.TbControl;
import com.mc.printer.server.entity.TbControlExample;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbControlMapper;
import com.mc.printer.server.service.pkkey.PkgeneratorServiceIF;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author 305027939
 */
@Service
public class ControlService implements ControlServiceIF {

    private static final Log log = LogFactory.getLog(ControlService.class);
    public final static int BUTTON_STATUS_ENABLE = 1;

    public final static int BUTTON_STATUS_DISABLE = 0;
    @Resource
    TbControlMapper mapper;

    @Resource
    PkgeneratorServiceIF pkService;

    @Override
    public CommFindEntity<TbControl> findControl(TbControl tbauth) {
        log.info("findControl");
        CommFindEntity<TbControl> result = new CommFindEntity<>();

        TbControlExample example = new TbControlExample();
        if (tbauth != null) {
            TbControlExample.Criteria crit = example.createCriteria();
            if (tbauth.getGuidename() != null && !tbauth.getGuidename().trim().equals("")) {
                crit.andGuidenameEqualTo(tbauth.getGuidename());
            }
            if (tbauth.getBranchname() != null && !tbauth.getBranchname().trim().equals("")) {
                crit.andBranchnameLike("%" + tbauth.getBranchname() + "%");
            }
            if (tbauth.getButtonname() != null && !tbauth.getButtonname().trim().equals("")) {
                crit.andButtonnameEqualTo(tbauth.getButtonname());
            }
            if (tbauth.getStatus() != null) {
                crit.andStatusEqualTo(tbauth.getStatus());
            }
        }
        example.setOrderByClause("status asc,id desc");

        List<TbControl> tbcons = mapper.selectByExample(example);

        if (tbcons != null) {
            log.debug("found control table,size:" + tbcons.size());
            result.setCount(tbcons.size());
            result.setResult(tbcons);
        } else {
            log.debug("no found control table");
            result.setCount(0);
            result.setResult(null);
        }

        return result;
    }

    @Override
    public int saveControl(TbControl tbauth) {

        if (tbauth != null) {
            log.debug("try to find buttton and guide name whether they are existing.");
            TbControl condition = new TbControl();
            condition.setGuidename(tbauth.getGuidename());
            CommFindEntity<TbControl> result = findControl(condition);
            if (result.getCount() <= 0) {

            } else {
                log.debug("delete existing value");
                List<TbControl> ls = result.getResult();
                if (ls != null) {
                    for (TbControl con : ls) {
                        deleteControl(con.getId());
                    }
                }
            }

            log.debug("insert control:" + tbauth.getButtonname());
            Long key = pkService.getPrimaryKey("control", "id");
            tbauth.setId(key);
            tbauth.setStatus(BUTTON_STATUS_ENABLE);
            return mapper.insertSelective(tbauth);
        }
        return 0;
    }

    @Override
    public int updateControl(TbControl tbauth) {
        log.info("update control:" + tbauth.getButtonname());
        return mapper.updateByPrimaryKeySelective(tbauth);
    }

    @Override
    public int deleteControl(Long id) {
        log.info("delete control:" + id);
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteControl(TbControl tbauth) {
        log.info("delete control:" + tbauth);
        TbControlExample example = new TbControlExample();
        boolean haveCondition = false;
        if (tbauth != null) {
            TbControlExample.Criteria crit = example.createCriteria();
            if (tbauth.getGuidename() != null && !tbauth.getGuidename().trim().equals("")) {
                crit.andGuidenameEqualTo(tbauth.getGuidename());
                haveCondition = true;
            }
            if (tbauth.getBranchname() != null && !tbauth.getBranchname().trim().equals("")) {
                crit.andBranchnameLike("%" + tbauth.getBranchname() + "%");
                haveCondition = true;
            }
            if (tbauth.getButtonname() != null && !tbauth.getButtonname().trim().equals("")) {
                crit.andButtonnameEqualTo(tbauth.getButtonname());
                haveCondition = true;
            }

            if (haveCondition) {
                log.info("start to delete control.");
                mapper.deleteByExample(example);
            }
        }

        return 0;
    }

    @Override
    public TbControl findObj(Long id) {
        log.info("find control:" + id);
        return mapper.selectByPrimaryKey(id);
    }

}
