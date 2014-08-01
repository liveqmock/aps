/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mc.printer.server.service.control;

import com.mc.printer.server.entity.TbControl;
import com.mc.printer.server.entity.common.CommFindEntity;

/**
 *
 * @author 305027939
 */
public interface ControlServiceIF {
    public CommFindEntity<TbControl> findControl(TbControl tbauth);
    
    public TbControl findObj(Long id);
    
    public int saveControl(TbControl tbauth);
    
    public int updateControl(TbControl tbauth);
    
    public int deleteControl(Long id);
    
    public int deleteControl(TbControl tbauth);
}
