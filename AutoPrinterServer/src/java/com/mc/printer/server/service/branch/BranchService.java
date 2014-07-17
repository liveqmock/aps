/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.branch;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbBranchExample;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbBranchMapper;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.pkkey.PkgeneratorServiceIF;
import com.mc.printer.server.utils.DateHelper;
import java.util.Date;
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
public class BranchService implements CommServiceIF<TbBranch, Long> {
    
    private static final Log log = LogFactory.getLog(BranchService.class);
    @Resource
    TbBranchMapper mapper;
    
    @Resource
    PkgeneratorServiceIF pkService;
    
    @Override
    public TbBranch findDataByKey(Long id) {
        log.info("try to find branch:" + id);
        return mapper.selectByPrimaryKey(id);
    }
    
    @Override
    public CommFindEntity<TbBranch> findAll(TbBranch conditionEntity) {
        log.info("try to find all branchs");
        CommFindEntity<TbBranch> result = new CommFindEntity<TbBranch>();
        TbBranchExample example = new TbBranchExample();
        TbBranchExample.Criteria crite = example.createCriteria();
        if (conditionEntity != null) {
            if (conditionEntity.getName() != null) {
                crite.andNameEqualTo(conditionEntity.getName());
            }
             if (conditionEntity.getAddress() != null) {
                crite.andAddressEqualTo(conditionEntity.getAddress());
            }
            if (conditionEntity.getStatus() != null) {
                crite.andStatusEqualTo(conditionEntity.getStatus());
            }
            if (conditionEntity.getCheckin() != null) {
                crite.andCheckinGreaterThan(conditionEntity.getCheckin());
            }
        }
        
        example.setOrderByClause("id DESC");
        List<TbBranch> ls = mapper.selectByExample(example);
        if (ls != null) {
            log.info("found branchs:" + ls.size());
            result.setCount(ls.size());

            /*更新时间状态*/
            for (TbBranch b : ls) {
                Date chkDate = b.getCheckin();
                if (chkDate != null) {
                    long sec = DateHelper.diffDateSec(new Date(), chkDate);
                    if (sec > Constants.CHECKIN_LOOP_SEC) {
                        b.setStatus(0);
                        try {
                            mapper.updateByPrimaryKeySelective(b);
                        } catch (Exception e) {
                            log.error(e);
                        }
                    }
                }
            }
            
        } else {
            log.debug("found empty branch..");
        }
        result.setResult(ls);
        
        return result;
    }
    
    @Override
    public int saveData(TbBranch bean) {
        log.info("insert branch" + bean.getName());
        long key = pkService.getPrimaryKey("branch", "id");
        bean.setId(key);
        return mapper.insertSelective(bean);
    }
    
    @Override
    public int deleteDataByKey(Long id) {
        log.info("delete branch" + id);
        return mapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public int updateData(TbBranch bean) {
        if (bean.getAddress() != null && !bean.getAddress().trim().equals("")&&bean.getName()!=null&&!bean.getName().trim().equals("")) {
            TbBranchExample example = new TbBranchExample();
            TbBranchExample.Criteria crite = example.createCriteria();
            crite.andAddressEqualTo(bean.getAddress()).andNameEqualTo(bean.getName());
            List<TbBranch> ls = mapper.selectByExample(example);
            
            TbBranch beans = null;
            if (ls != null && ls.size() > 0) {
                beans = ls.get(0);
                beans.setCheckin(bean.getCheckin());
                log.info("update branch:" + bean.getName());
                return mapper.updateByPrimaryKeySelective(beans);
            } else {
                return saveData(bean);
            }
        } else {
            log.info("IP is null. not update.");
            return 0;
        }
        
    }
    
}
