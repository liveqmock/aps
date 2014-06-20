/*
 * 2014 Chengdu JunChen Technology
 */
package com.mc.printer.server.service.pkkey;


import com.mc.printer.server.entity.TbPkgenerator;
import com.mc.printer.server.entity.TbPkgeneratorExample;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbPkgeneratorMapper;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author 305027939
 */
@Service
public class PkgeneratorService implements PkgeneratorServiceIF {

    private static final Log log = LogFactory.getLog(PkgeneratorService.class);
    @Resource
    private TbPkgeneratorMapper mapper;

    @Override
    public TbPkgenerator findDataByKey(Long id) {
        TbPkgenerator pin = mapper.selectByPrimaryKey(id);
        log.debug("PkgeneratorService,find: " + pin.getId());
        return pin;
    }

    @Override
    public CommFindEntity<TbPkgenerator> findAll(TbPkgenerator tbPkgenerator) {
        TbPkgeneratorExample example = new TbPkgeneratorExample();
        CommFindEntity<TbPkgenerator> allData = new CommFindEntity<TbPkgenerator>();

        
        //必须先设置count数，再设置limtStart/limitEnd
        int count = mapper.countByExample(example);
        log.debug("found total:" + count);

        //排序
        example.setOrderByClause("id DESC");

        allData.setCount(count);

        //查询数据库
        List<TbPkgenerator> results = mapper.selectByExample(example);
        allData.setResult(results);
        return allData;
    }

    @Override
    public int saveData(TbPkgenerator bean) {
        log.debug("PkgeneratorService,save: " + bean.getPkcolumnname());
        int result = mapper.insertSelective(bean);
        return result;
    }

    @Override
    public int deleteDataByKey(Long id) {
        log.debug("PkgeneratorService,delete: " + id);
        int result = mapper.deleteByPrimaryKey(id);
        return result;
    }

    @Override
    public int updateData(TbPkgenerator bean) {
        log.debug("PkgeneratorService,update: " + bean.getId());
        int result = mapper.updateByPrimaryKeySelective(bean);
        return result;
    }

    @Override
    public Long getPrimaryKey(String TABLE_NAME, String COLUMN_NAME) {
        long primaryValue = 0;
        TbPkgeneratorExample example = new TbPkgeneratorExample();
        example.createCriteria().andPkcolumnnameEqualTo(COLUMN_NAME).andTargettableEqualTo(TABLE_NAME);

        List<TbPkgenerator> result = mapper.selectByExample(example);
        if (result != null && result.size() > 0) {
            TbPkgenerator pk = result.get(0);
            primaryValue = pk.getInitialvalue();
            log.debug("table generator,table: " + TABLE_NAME+",colum:"+COLUMN_NAME+",primary value:"+primaryValue);
            pk.setInitialvalue((primaryValue+pk.getAllocationsize()));
            mapper.updateByExampleSelective(pk, example);
        } else {
            log.debug("table generator, NOT FOUND table: " + TABLE_NAME+",colum:"+COLUMN_NAME+",initialize primary value:1");
            primaryValue = 1;
            TbPkgenerator pk = new TbPkgenerator();
            pk.setInitialvalue(2l);
            pk.setPkcolumnname(COLUMN_NAME);
            pk.setTargettable(TABLE_NAME);
            pk.setAllocationsize(1l);
            mapper.insert(pk);
        }

        return primaryValue;
    }

}
