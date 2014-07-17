package com.mc.printer.server.utils;

public class Pager {
			
    private int pageSize;
        
    private int count;
    
    private int startDataIndex;
    
    private int pageindex;
    
    public Pager(int pageindex,int pagesize){
        this.pageSize = pagesize;
        pageindex=pageindex<=0?1:pageindex;
        this.pageindex = pageindex;
        startDataIndex=(pageindex - 1) * pagesize;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the startDataIndex
     */
    public int getStartDataIndex() {
        if(getCount()<=0){
            startDataIndex = 0;
        }
        if((startDataIndex)>getCount()){
          startDataIndex = getCount()-getPageSize();
          if(startDataIndex<0)startDataIndex=0;
        }
        return startDataIndex;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @return the pageindex
     */
    public int getPageindex() {
        return pageindex;
    }

    /**
     * @param pageindex the pageindex to set
     */
    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }


}
