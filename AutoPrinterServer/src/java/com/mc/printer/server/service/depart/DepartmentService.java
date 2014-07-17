/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.depart;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbDepartmentExample;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbDepartmentMapper;
import com.mc.printer.server.service.common.CommServiceIF;
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
public class DepartmentService implements CommServiceIF<TbDepartment, Long> {
    
    private static final Log log = LogFactory.getLog(DepartmentService.class);
    @Resource
    TbDepartmentMapper mapper;
    
    @Resource
    PkgeneratorServiceIF pkService;
    
    @Override
    public TbDepartment findDataByKey(Long id) {
        log.info("try to find department:" + id);
        return mapper.selectByPrimaryKey(id);
    }
    
    @Override
    public CommFindEntity<TbDepartment> findAll(TbDepartment conditionEntity) {
        log.info("try to find all departmenet");
        CommFindEntity<TbDepartment> result = new CommFindEntity<TbDepartment>();
        TbDepartmentExample example = new TbDepartmentExample();
        TbDepartmentExample.Criteria crite = example.createCriteria().andFlagNotEqualTo(Constants.FLAG_STATUS_DELETE);
        if (conditionEntity != null) {
            if (conditionEntity.getDepname() != null) {
                crite.andDepnameEqualTo(conditionEntity.getDepname());
            }
            if (conditionEntity.getFlag() != null && conditionEntity.getFlag() > 0) {
                crite.andFlagEqualTo(conditionEntity.getFlag());
            }
            if (conditionEntity.getDepfather() != null) {
                crite.andDepfatherEqualTo(conditionEntity.getDepfather());
            }
            if (conditionEntity.getExt1() != null&&!conditionEntity.getExt1().trim().equals("")) {
                crite.andExt1Like("%"+conditionEntity.getExt1()+"%");
            }
        }
        
        example.setOrderByClause("depfather asc,id desc");
        List<TbDepartment> ls = mapper.selectByExample(example);
        if (ls != null) {
            log.info("found departmenet:" + ls.size());
            result.setCount(ls.size());
        } else {
            log.debug("found empty departmenet..");
        }
        result.setResult(ls);
        
        return result;
    }
    
    @Override
    public int saveData(TbDepartment bean) {
        log.info("insert departmenet" + bean.getDepname());
        long key = pkService.getPrimaryKey("branch", "id");
        bean.setId(key);
        bean.setFlag(Constants.FLAG_STATUS_ACTIVE);
        if (bean.getDepfather() == null) {
            bean.setDepfather(0l);
        }
        return mapper.insertSelective(bean);
    }
    
    @Override
    public int deleteDataByKey(Long id) {
        log.info("delete departmenet" + id);
        TbDepartment dep = mapper.selectByPrimaryKey(id);
        if (dep != null) {
            dep.setFlag(Constants.FLAG_STATUS_DELETE);
            return mapper.updateByPrimaryKeySelective(dep);
        } else {
            log.warn("can not delete departmenet " + id + ",it's not exiting.");
        }
        return 0;
    }
    
    @Override
    public int updateData(TbDepartment bean) {
        log.info("update departmenet:" + bean.getDepname());
        return mapper.updateByPrimaryKeySelective(bean);
    }
}
