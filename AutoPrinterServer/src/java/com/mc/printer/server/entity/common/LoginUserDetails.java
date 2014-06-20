/*
 * 2014 Chengdu JunChen Technology
 */
package com.mc.printer.server.entity.common;

import com.mc.printer.server.entity.child.UserEntity;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author 305027939
 */
public class LoginUserDetails extends User {

    private UserEntity userSessionEntity;

    public LoginUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public LoginUserDetails(UserEntity userSessionEntity,List<GrantedAuthority> grantedAuthority) {
        super(userSessionEntity.getName(), userSessionEntity.getPasswd(), grantedAuthority);
        this.userSessionEntity = userSessionEntity;
    }

    public UserEntity getUserSessionEntity() {
        return userSessionEntity;
    }

}
