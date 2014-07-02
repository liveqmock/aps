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
public class DataSearchResult {
    
    private String branchName;
    
    private String danjuName;
    
    private String key;
    
    private String context;
    
    private String submitdate;

    /**
     * @return the branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName the branchName to set
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
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

    /**
     * @return the submitdate
     */
    public String getSubmitdate() {
        return submitdate;
    }

    /**
     * @param submitdate the submitdate to set
     */
    public void setSubmitdate(String submitdate) {
        this.submitdate = submitdate;
    }
    
}
