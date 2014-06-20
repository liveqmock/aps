package com.mc.printer.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TbDepartment  implements Serializable{
    private Long id;

    private String depname;

    private Long deplevel;

    private Long depfather;

    private Long deppresentid;

    private String descms;

    private String ext1;

    private Integer flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname == null ? null : depname.trim();
    }

    public Long getDeplevel() {
        return deplevel;
    }

    public void setDeplevel(Long deplevel) {
        this.deplevel = deplevel;
    }

    public Long getDepfather() {
        return depfather;
    }

    public void setDepfather(Long depfather) {
        this.depfather = depfather;
    }

    public Long getDeppresentid() {
        return deppresentid;
    }

    public void setDeppresentid(Long deppresentid) {
        this.deppresentid = deppresentid;
    }

    public String getDescms() {
        return descms;
    }

    public void setDescms(String descms) {
        this.descms = descms == null ? null : descms.trim();
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}