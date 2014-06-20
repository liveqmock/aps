/*
 * 2014 Chengdu JunChen Technology
 */

package com.mc.printer.server.service.emp;

import com.mc.printer.server.entity.child.UserEntity;

/**
 *
 * @author 305027939
 */
public interface LoginServiceIF {
    
    public UserEntity login(String userName,String passed);
    
}
