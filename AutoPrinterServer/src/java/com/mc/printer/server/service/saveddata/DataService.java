/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.saveddata;

import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbBranchExample;
import com.mc.printer.server.entity.TbSavedata;
import com.mc.printer.server.entity.TbSavedataExample;
import com.mc.printer.server.entity.child.SavedDataEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbBranchMapper;
import com.mc.printer.server.mapper.TbSavedataMapper;
import com.mc.printer.server.service.branch.BranchService;
import com.mc.printer.server.service.common.CommServiceIF;
import com.mc.printer.server.service.pkkey.PkgeneratorServiceIF;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author 305027939
 */
@Service
public class DataService implements CommServiceIF<SavedDataEntity, Long> {
    
    public static BeanCopier copier = BeanCopier.create(TbSavedata.class, SavedDataEntity.class, false);
    
    public static BeanCopier recopier = BeanCopier.create(SavedDataEntity.class, TbSavedata.class, false);
    
    private static final Log log = LogFactory.getLog(BranchService.class);
    @Resource
    TbBranchMapper branchMapper;
    
    @Resource
    PkgeneratorServiceIF pkService;
    
    @Resource
    TbSavedataMapper mapper;
    
    @Override
    public SavedDataEntity findDataByKey(Long id) {
        log.info("try to find data:" + id);
        SavedDataEntity target = new SavedDataEntity();
        TbSavedata source = mapper.selectByPrimaryKey(id);
        if (source != null) {
            log.debug("found data key:" + source.getKey());
            copier.copy(source, target, null);
        } else {
            log.debug("found empty for saved data");
        }
        return target;
    }
     
    @Override
    public CommFindEntity<SavedDataEntity> findAll(SavedDataEntity conditionEntity) {
        log.info("try to find all data");
        CommFindEntity<SavedDataEntity> result = new CommFindEntity<SavedDataEntity>();
        TbSavedataExample example = new TbSavedataExample();
        
        TbSavedataExample.Criteria crite = example.createCriteria();
        
        if (conditionEntity != null) {
            if (conditionEntity.getKey() != null) {
                crite.andKeyEqualTo(conditionEntity.getKey());
            }
            if (conditionEntity.getBranchid() != null && conditionEntity.getBranchid() > 0) {
                crite.andBranchidEqualTo(conditionEntity.getBranchid());
            }
            
            if (conditionEntity.getSubmitdate() != null) {
                crite.andSubmitdateGreaterThan(conditionEntity.getSubmitdate());
            }
            
        }
        
        example.setOrderByClause("submitdate DESC");
        
        List<TbSavedata> all = mapper.selectByExample(example);
        List<SavedDataEntity> allEntity = new ArrayList();
        if (all != null) {
            log.info("found result data entity:" + all.size());
            for (TbSavedata empl : all) {
                SavedDataEntity allUse = new SavedDataEntity();
                if (empl.getBranchid() != null) {
                    TbBranch roles = branchMapper.selectByPrimaryKey(empl.getBranchid());
                    allUse.setBranch(roles);
                }
                
                copier.copy(empl, allUse, null);
                allEntity.add(allUse);
            }
            result.setCount(all.size());
        } else {
            log.debug("found empty data.");
        }
        
        result.setResult(allEntity);
        return result;
    }
    
    @Override
    public int saveData(SavedDataEntity bean) {
        log.info("insert data" + bean.getKey());
        long key = pkService.getPrimaryKey("saveddata", "id");
        bean.setId(key);
        TbSavedata savedata = new TbSavedata();
        recopier.copy(bean, savedata, null);
        
        if (bean.getBranch() != null && !bean.getBranch().getAddress().trim().equals("")) {
            String ip = bean.getBranch().getAddress();
            TbBranchExample br = new TbBranchExample();
            br.createCriteria().andAddressEqualTo(ip);
            List<TbBranch> tb = branchMapper.selectByExample(br);
            
            if (tb != null && tb.size() > 0) {
                TbBranch branch = tb.get(0);
                log.info("found branch:" + branch.getName());
                long pk = branch.getId();
                savedata.setBranchid(pk);
            }
            
        }
        savedata.setSubmitdate(new Date());
        
        return mapper.insertSelective(savedata);
    }
    
    @Override
    public int deleteDataByKey(Long id) {
        log.info("delete data" + id);
        return mapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public int updateData(SavedDataEntity bean) {
        log.info("update data" + bean.getKey());
        
        TbSavedata savedata = new TbSavedata();
        recopier.copy(bean, savedata, null);
        return mapper.updateByPrimaryKeySelective(savedata);
    }
    
}
