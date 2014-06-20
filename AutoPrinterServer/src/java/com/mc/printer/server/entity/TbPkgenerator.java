package com.mc.printer.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TbPkgenerator {
    private Long id;

    private String targettable;

    private String pkcolumnname;

    private Long initialvalue;

    private Long allocationsize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargettable() {
        return targettable;
    }

    public void setTargettable(String targettable) {
        this.targettable = targettable == null ? null : targettable.trim();
    }

    public String getPkcolumnname() {
        return pkcolumnname;
    }

    public void setPkcolumnname(String pkcolumnname) {
        this.pkcolumnname = pkcolumnname == null ? null : pkcolumnname.trim();
    }

    public Long getInitialvalue() {
        return initialvalue;
    }

    public void setInitialvalue(Long initialvalue) {
        this.initialvalue = initialvalue;
    }

    public Long getAllocationsize() {
        return allocationsize;
    }

    public void setAllocationsize(Long allocationsize) {
        this.allocationsize = allocationsize;
    }
}