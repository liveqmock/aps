/*
 * 2014 Chengdu JunChen Technology
 */

package com.mc.printer.server.service.emp;

import com.mc.printer.server.entity.common.LoginUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author 305027939
 */
public interface LoginUserDetailsService {
    LoginUserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException;
}
