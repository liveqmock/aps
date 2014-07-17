/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.roles;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.TbRoleExample;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbRoleMapper;
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
public class RoleService implements CommServiceIF<TbRole, Long> {

    private static final Log log = LogFactory.getLog(RoleService.class);
    @Resource
    TbRoleMapper mapper;

    @Resource
    PkgeneratorServiceIF pkService;

    @Override
    public TbRole findDataByKey(Long id) {
        log.info("try to find role:" + id);
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public CommFindEntity<TbRole> findAll(TbRole conditionEntity) {

        log.info("try to find all roles");
        CommFindEntity<TbRole> result = new CommFindEntity<TbRole>();
        TbRoleExample example = new TbRoleExample();
        TbRoleExample.Criteria crite = example.createCriteria().andFlagNotEqualTo(Constants.FLAG_STATUS_DELETE);

        if (conditionEntity != null) {
            if (conditionEntity.getFlag() != null && conditionEntity.getFlag() > 0) {
                crite.andFlagEqualTo(conditionEntity.getFlag());
            }
            if (conditionEntity.getRolename() != null) {
                crite.andRolenameEqualTo(conditionEntity.getRolename());
            }
            if (conditionEntity.getRoleid() != null && conditionEntity.getRoleid() > 0) {
                crite.andRoleidEqualTo(conditionEntity.getRoleid());
            }
        }

        example.setOrderByClause("id DESC");
        List<TbRole> ls = mapper.selectByExample(example);
        if (ls != null) {
            log.info("found roles:" + ls.size());
            result.setCount(ls.size());
        } else {
            log.debug("found empty role..");
        }
        result.setResult(ls);

        return result;
    }

    @Override
    public int saveData(TbRole bean) {
        log.info("insert role" + bean.getRolename());
        long key = pkService.getPrimaryKey("role", "id");
        bean.setId(key);
        bean.setFlag(Constants.FLAG_STATUS_ACTIVE);        
        return mapper.insertSelective(bean);
    }

    @Override
    public int deleteDataByKey(Long id) {
        log.info("delete role" + id);
       TbRole role = mapper.selectByPrimaryKey(id);
       if(role!=null){
           role.setFlag(Constants.FLAG_STATUS_DELETE);
          return mapper.updateByPrimaryKeySelective(role);
       }else{
          log.warn("can not delete role  " + id+",it's not exiting.");
       }
        return 0;
    }

    @Override
    public int updateData(TbRole bean) {
        log.info("update role:" + bean.getRolename());
       return mapper.updateByPrimaryKeySelective(bean);
    }

}
