/*
 * 2014 Chengdu JunChen Technology
 */

package com.mc.printer.server.service.pkkey;

import com.mc.printer.server.entity.TbPkgenerator;
import com.mc.printer.server.service.common.CommServiceIF;

/**
 *
 * @author 305027939
 */
public interface PkgeneratorServiceIF extends CommServiceIF<TbPkgenerator, Long>{
    
    public Long getPrimaryKey(String TABLE_NAME,String COLUMN_NAME);
    
}
