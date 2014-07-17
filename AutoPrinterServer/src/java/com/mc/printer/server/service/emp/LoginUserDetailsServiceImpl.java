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
            String ext1 = userSessionEntity.getRole().getExt1();

            log.debug("get role:" + ext1);
            if (ext1 != null) {
                String[] roles = ext1.split(",");
                
                for(String role:roles){
                    for(int i=0;i<Constants.ROLE_PAGES_CN.length;i++){
                      if(role.equals(Constants.ROLE_PAGES_CN[i])){
                         GrantedAuthorityImpl auth2 = new GrantedAuthorityImpl(Constants.ROLE_PAGES[i]);
                         log.info("add Authority:"+Constants.ROLE_PAGES[i]+",cn_name:"+Constants.ROLE_PAGES_CN[i]);
                         auths.add(auth2);
                      }
                    }
                }
                
            }
            return new LoginUserDetails(userSessionEntity, new ArrayList<GrantedAuthority>(auths));
        }

    }

}
