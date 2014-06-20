/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mc.printer.server.entity.child;

import com.mc.printer.server.entity.TbDepartment;
import com.mc.printer.server.entity.TbEmployee;
import com.mc.printer.server.entity.TbRole;
import java.io.Serializable;

/**
 *
 * @author 305027939
 */
public class UserEntity extends TbEmployee implements Serializable{
    private TbRole role;
    private TbDepartment department;

    /**
     * @return the role
     */
    public TbRole getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(TbRole role) {
        this.role = role;
    }

    /**
     * @return the department
     */
    public TbDepartment getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(TbDepartment department) {
        this.department = department;
    }
    
    
}
