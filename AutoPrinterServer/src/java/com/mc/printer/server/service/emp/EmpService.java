/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.emp;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbEmployee;
import com.mc.printer.server.entity.TbEmployeeExample;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbDepartmentMapper;
import com.mc.printer.server.mapper.TbEmployeeMapper;
import com.mc.printer.server.mapper.TbRoleMapper;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.pkkey.PkgeneratorServiceIF;
import com.mc.printer.server.utils.ToolHelper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author 305027939
 */
@Service
public class EmpService implements CommServiceIF<UserEntity, Long> {

    public static BeanCopier copier = BeanCopier.create(TbEmployee.class, UserEntity.class, false);
    private static final Log log = LogFactory.getLog(EmpService.class);
    @Resource
    TbEmployeeMapper mapper;

    @Resource
    TbRoleMapper roleMapper;

    @Resource
    TbDepartmentMapper departmentMapper;

    @Resource
    PkgeneratorServiceIF pkService;

    @Override
    public UserEntity findDataByKey(Long id) {
        log.info("try to find employee:" + id);
        UserEntity user = new UserEntity();
        TbEmployee emp = mapper.selectByPrimaryKey(id);
        if (emp != null) {
            log.debug("found TbEmployee name:" + emp.getName());
            copier.copy(emp, user, null);
        } else {
            log.debug("found empty for TbEmployee ");
        }
        return user;
    }

    @Override
    public CommFindEntity<UserEntity> findAll(UserEntity conditionEntity) {
        log.info("try to find all employee");
        CommFindEntity<UserEntity> result = new CommFindEntity<UserEntity>();
        TbEmployeeExample example = new TbEmployeeExample();

        TbEmployeeExample.Criteria crite = example.createCriteria().andFlagNotEqualTo(Constants.FLAG_STATUS_DELETE);

        if (conditionEntity != null) {
            if (conditionEntity.getRoleid() != null && conditionEntity.getRoleid() > 0) {
                crite.andRoleidEqualTo(conditionEntity.getRoleid());
            }
            if (conditionEntity.getDepid() != null && conditionEntity.getDepid() > 0) {
                crite.andDepidEqualTo(conditionEntity.getDepid());
            }
            if (conditionEntity.getFlag() != null && conditionEntity.getFlag() > 0) {
                crite.andFlagEqualTo(conditionEntity.getFlag());
            }
            if (conditionEntity.getName() != null) {
                crite.andNameEqualTo(conditionEntity.getName());
            }
            if (conditionEntity.getType() != null && conditionEntity.getType() > 0) {
                crite.andTypeEqualTo(conditionEntity.getType());
            }
        }

        example.setOrderByClause("id DESC");

        int count = mapper.countByExample(example);

        List<UserEntity> allEntity = new ArrayList();
        if (count > 0) {
            List<TbEmployee> all = mapper.selectByExample(example);
            if (all != null) {
                log.info("found result user entity:" + all.size());

                for (TbEmployee empl : all) {
                    UserEntity allUse = new UserEntity();

                    if (empl.getRoleid() != null) {
                        TbRole roles = roleMapper.selectByPrimaryKey(empl.getRoleid());
                        allUse.setRole(roles);
                    }

                    if (empl.getDepid() != null) {
                        TbDepartment part = departmentMapper.selectByPrimaryKey(empl.getDepid());
                        allUse.setDepartment(part);
                    }

                    copier.copy(empl, allUse, null);
                    allEntity.add(allUse);
                }
                result.setCount(all.size());
            } else {
                log.debug("found empty employee.");
            }
        } else {
            log.debug("found empty employee.");
            result.setCount(0);
        }

        result.setResult(allEntity);
        return result;
    }

    @Override
    public int saveData(UserEntity bean) {
        log.info("insert emp" + bean.getName());
        long key = pkService.getPrimaryKey("emp", "id");
        bean.setId(key);
        bean.setFlag(Constants.FLAG_STATUS_ACTIVE);
        bean.setPasswd(new String(ToolHelper.encryptBASE64(bean.getPasswd().getBytes())));
        return mapper.insertSelective(bean);
    }

    @Override
    public int deleteDataByKey(Long id) {
        log.info("delete emp" + id);

        TbEmployee emp = mapper.selectByPrimaryKey(id);
        if (emp != null) {
            emp.setFlag(Constants.FLAG_STATUS_DELETE);
            return mapper.updateByPrimaryKeySelective(emp);
        }else{
            log.warn("can not delete user " + id+",it's not exiting.");
        }
        return 0;
    }

    @Override
    public int updateData(UserEntity bean) {
        log.info("update emp" + bean.getName());
        return mapper.updateByPrimaryKeySelective(bean);
    }

}
