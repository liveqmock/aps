
package com.mc.printer.server.entity.child;

import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbSavedata;

/**
 *
 * @author 305027939
 */
public class SavedDataEntity extends TbSavedata{
    private TbBranch branch;

    /**
     * @return the branch
     */
    public TbBranch getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(TbBranch branch) {
        this.branch = branch;
    }
    
    
}
