/*
 * 2014 Chengdu JunChen Technology
 */
package com.mc.printer.server.service.emp;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.child.UserEntity;
import com.mc.printer.server.entity.common.LoginUserDetails;
import com.mc.printer.server.utils.ToolHelper;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author 305027939
 */
@Service
public class LoginUserDetailsServiceImpl implements LoginUserDetailsService {
    
    private static final Log log = LogFactory.getLog(LoginUserDetailsServiceImpl.class);
    @Resource
    LoginServiceIF loginService;
    
    @Override
    public LoginUserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {
        
        UserEntity userSessionEntity = loginService.login(username, new String(ToolHelper.encryptBASE64(password.getBytes())));
        
        if (userSessionEntity == null || userSessionEntity.getName() == null) {
            log.error("user entity is null.");
            return null;
        } else {
            Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            int roleId = userSessionEntity.getRole().getRoleid();
            
            log.debug("get role id:" + roleId);
            
            if (roleId == Constants.ROLE_ADMIN) { //为管理员的时候
                GrantedAuthorityImpl auth2 = new GrantedAuthorityImpl("ROLE_ADMIN");
                GrantedAuthorityImpl auth1 = new GrantedAuthorityImpl("ROLE_BRANCH");
                GrantedAuthorityImpl auth3 = new GrantedAuthorityImpl("ROLE_USER");
                
                log.info("add Authority:ROLE_ADMIN,ROLE_BRANCH,ROLE_USER");
                auths.add(auth2);
                auths.add(auth1);
                auths.add(auth3);
            } else if (roleId == Constants.ROLE_BRANCH) {
                GrantedAuthorityImpl auth2 = new GrantedAuthorityImpl("ROLE_BRANCH");
                auths.add(auth2);
                log.info("add Authority:ROLE_BRANCH");
            } else if (roleId == Constants.ROLE_USER) {
                GrantedAuthorityImpl auth1 = new GrantedAuthorityImpl("ROLE_USER");
                auths.add(auth1);
                log.info("add Authority:ROLE_USER");
            }
            return new LoginUserDetails(userSessionEntity, new ArrayList<GrantedAuthority>(auths));
        }
        
    }
    
}
