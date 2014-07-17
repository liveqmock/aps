/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.log;

import com.mc.printer.server.entity.TbLog;
import com.mc.printer.server.entity.child.LogSearchEntity;
import com.mc.printer.server.utils.Pager;
import java.util.List;

/**
 *
 * @author 305027939
 */
public interface LogServiceIF {

    public TbLog findDataByKey(Long id);

    public List<TbLog> findAll(LogSearchEntity conditionEntity,Pager page);

    public int saveData(TbLog bean);
    
    public void saveLog(String context);

    public int deleteDataByKey(Long id);
    
    public int deleteData(LogSearchEntity conditionEntity);

    public int updateData(TbLog bean);
    
    public void saveLog(String user, String dept, Long deptid, String context);
}
