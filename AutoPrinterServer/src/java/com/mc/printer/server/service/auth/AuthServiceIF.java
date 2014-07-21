/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mc.printer.server.service.auth;

import com.mc.printer.server.entity.TbAuth;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.utils.Pager;

/**
 *
 * @author 305027939
 */
public interface AuthServiceIF {
    
    public TbAuth findLogin(String userId,String passwd);

    public CommFindEntity<TbAuth> findAuth(TbAuth tbauth,Pager page);
    
    public int saveAuth(TbAuth tbah) throws Exception;
    
    public int updatedAuth(TbAuth tbah);
    
    public int deleteAuth(Long id);
}
