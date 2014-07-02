/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mc.printer.server.service.saveddata;

import com.mc.printer.server.entity.child.DataSearchEntity;
import com.mc.printer.server.entity.child.DataSearchResult;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.service.common.CommServiceIF;

/**
 *
 * @author 305027939
 */
public interface DataServiceIF{
    
    public CommFindEntity<DataSearchResult> findAllCondition(DataSearchEntity conditionEntity);
}
