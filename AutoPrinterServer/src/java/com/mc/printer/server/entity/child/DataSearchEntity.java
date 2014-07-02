/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mc.printer.server.entity.child;

import java.util.Date;

/**
 *
 * @author 305027939
 */
public class DataSearchEntity extends SavedDataEntity{
    
    private Date startDate;
    
    private Date endDate;
    
    private String danjuName;

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the danjuName
     */
    public String getDanjuName() {
        return danjuName;
    }

    /**
     * @param danjuName the danjuName to set
     */
    public void setDanjuName(String danjuName) {
        this.danjuName = danjuName;
    }

   
    
    
}
