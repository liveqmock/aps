/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.auth;

import com.mc.printer.server.entity.TbAuth;
import com.mc.printer.server.entity.TbAuthExample;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbAuthMapper;
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
public class AuthService implements AuthServiceIF {

    private static final Log log = LogFactory.getLog(AuthService.class);
    
    public static final int STATUS_APPROVED=1;
    
    public static final int STATUS_SUBMITED=2;
    
    @Resource
    TbAuthMapper mapper;

    @Resource
    PkgeneratorServiceIF pkService;

    @Override
    public TbAuth findLogin(String userId, String passwd) {
        log.info("login user:" + userId);
        TbAuthExample example = new TbAuthExample();
        example.createCriteria().andUseridEqualTo(userId).andPasswdEqualTo(passwd);
        List<TbAuth> tbAuths = mapper.selectByExample(example);

        if (tbAuths != null && tbAuths.size() > 0) {
            log.debug("found user. size:" + tbAuths.size());
            return tbAuths.get(0);
        }
        log.debug("no found user.");
        return null;
    }

    @Override
    public CommFindEntity<TbAuth> findAuth(TbAuth tbauth) {
        log.info("findAuth:" + tbauth);
        TbAuthExample example = new TbAuthExample();
        CommFindEntity<TbAuth> result = new CommFindEntity<>();
        TbAuthExample.Criteria crit = example.createCriteria();
        if (tbauth.getId() != null && tbauth.getId() > 0) {
            crit.andIdEqualTo(tbauth.getId());
        }
        if (tbauth.getUserid() != null) {
            crit.andUseridEqualTo(tbauth.getUserid());
        }
        if (tbauth.getPasswd() != null) {
            crit.andPasswdEqualTo(tbauth.getPasswd());
        }
        if (tbauth.getContext() != null) {
            crit.andContextLike("%" + tbauth.getContext() + "%");
        }
        if (tbauth.getStatus() != null) {
            crit.andStatusEqualTo(tbauth.getStatus());
        }
        if (tbauth.getBranchname()!= null&&!tbauth.getBranchname().trim().equals("")) {
            crit.andBranchnameLike(tbauth.getBranchname()+"%");
        }
        example.setOrderByClause("id,status DESC");
        List<TbAuth> tbAuths = mapper.selectByExample(example);
        if (tbAuths != null) {
            log.debug("found user,size:" + tbAuths.size());
            result.setCount(tbAuths.size());
            result.setResult(tbAuths);
        } else {
            log.debug("no found user.");
            result.setCount(0);
            result.setResult(null);
        }
        return result;

    }

    @Override
    public int saveAuth(TbAuth tbah) throws Exception {
        log.info("no found user.");
        TbAuthExample example = new TbAuthExample();
        example.createCriteria().andUseridEqualTo(tbah.getUserid());
        List<TbAuth> tbAuths = mapper.selectByExample(example);
        if (tbAuths != null && tbAuths.size() > 0) {
            log.debug("user is existing. size:" + tbAuths.size());
            throw new Exception("用户名已经存在.");
        }
        log.debug("user id is not existing. go ahead.");
        Long key = pkService.getPrimaryKey("auth", "id");
        tbah.setId(key);
        tbah.setStatus(STATUS_SUBMITED);
        return mapper.insertSelective(tbah);
    }

    @Override
    public int updatedAuth(TbAuth tbah) {
        log.info("update user." + tbah.getUserid());
        return mapper.updateByPrimaryKey(tbah);
    }

    @Override
    public int deleteAuth(Long id) {
        log.info("delete user." + id);
        return mapper.deleteByPrimaryKey(id);
    }

}
