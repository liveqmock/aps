/*
 * 2014 Chengdu JunChen Technology
 */
package com.mc.printer.server.service.emp;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbEmployee;
import com.mc.printer.server.entity.TbEmployeeExample;
import com.mc.printer.server.entity.TbRole;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.mapper.TbDepartmentMapper;
import com.mc.printer.server.mapper.TbEmployeeMapper;
import com.mc.printer.server.mapper.TbRoleMapper;
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
public class LoginService implements LoginServiceIF {

    private static final Log log = LogFactory.getLog(LoginService.class);
    public static BeanCopier copier = BeanCopier.create(TbEmployee.class, UserEntity.class, false);
    @Resource
    private TbEmployeeMapper mapper;

    @Resource
    TbRoleMapper roleMapper;

    @Resource
    TbDepartmentMapper departmentMapper;

    @Override
    public UserEntity login(String userName, String passed) {

        UserEntity entity = new UserEntity();
        log.info("try to login with:" + userName);
        TbEmployeeExample example = new TbEmployeeExample();
        example.createCriteria().andFlagNotEqualTo(Constants.FLAG_STATUS_DELETE).andNameEqualTo(userName).andPasswdEqualTo(passed);

        List<TbEmployee> users = mapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            TbEmployee empl = users.get(0);
            log.info("found user:" + empl.getName());
            if (empl.getRoleid() != null) {
                TbRole roles = roleMapper.selectByPrimaryKey(empl.getRoleid());
                entity.setRole(roles);
            }

            if (empl.getDepid() != null) {
                TbDepartment part = departmentMapper.selectByPrimaryKey(empl.getDepid());
                entity.setDepartment(part);
            }

            copier.copy(empl, entity, null);
        }

        return entity;
    }

}
