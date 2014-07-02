/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc.printer.server.service.saveddata;

import com.mc.printer.server.constants.Constants;
import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbSavedata;
import com.mc.printer.server.entity.TbSavedataExample;
import com.mc.printer.server.entity.child.DataSearchEntity;
import com.mc.printer.server.entity.child.DataSearchResult;
import com.mc.printer.server.entity.child.SavedDataEntity;
import com.mc.printer.server.entity.common.CommFindEntity;
import com.mc.printer.server.mapper.TbBranchMapper;
import com.mc.printer.server.mapper.TbSavedataMapper;
import com.mc.printer.server.service.branch.BranchService;
import static com.mc.printer.server.service.emp.LoginService.copier;
import com.mc.printer.server.service.pkkey.PkgeneratorServiceIF;
import com.mc.printer.server.utils.DateHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class DataSearchService implements DataServiceIF {

    public static BeanCopier copier = BeanCopier.create(TbSavedata.class, SavedDataEntity.class, false);
    private static final Log log = LogFactory.getLog(DataSearchService.class);
    @Resource
    TbBranchMapper branchMapper;

    @Resource
    PkgeneratorServiceIF pkService;

    @Resource
    TbSavedataMapper mapper;

    @Override
    public CommFindEntity<DataSearchResult> findAllCondition(DataSearchEntity conditionEntity) {
        log.info("try to find all data by findAllCondition");
        CommFindEntity<DataSearchResult> result = new CommFindEntity<DataSearchResult>();
        TbSavedataExample example = new TbSavedataExample();

        TbSavedataExample.Criteria crite = example.createCriteria();

        if (conditionEntity != null) {
            if (conditionEntity.getDanjuName() != null && !conditionEntity.getDanjuName().trim().equals("")) {
                crite.andKeyLike(conditionEntity.getDanjuName() + "%");
            }
            if (conditionEntity.getKey() != null && !conditionEntity.getKey().trim().equals("")) {
                crite.andKeyLike("%" + conditionEntity.getKey() + "%");
            }
            if (conditionEntity.getBranchid() != null && conditionEntity.getBranchid() > 0) {
                crite.andBranchidEqualTo(conditionEntity.getBranchid());
            }

            if (conditionEntity.getStartDate() != null && conditionEntity.getEndDate() != null) {
                crite.andSubmitdateBetween(conditionEntity.getStartDate(), conditionEntity.getEndDate());
            } else if (conditionEntity.getStartDate() != null) {
                crite.andSubmitdateGreaterThanOrEqualTo(conditionEntity.getStartDate());
            } else if (conditionEntity.getEndDate() != null) {
                crite.andSubmitdateLessThanOrEqualTo(conditionEntity.getEndDate());
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
            //result.setCount(all.size());
        } else {
            log.debug("found empty data.");
        }

        //这里查询的模块需要用时间来分类，只能精确到分。
        HashMap<String, DataSearchResult> map = new HashMap();

        for (SavedDataEntity data : allEntity) {

            Date date = data.getSubmitdate();
            if (date != null) {
                String keyDate = DateHelper.format(date, "yyyy/MM/dd HH:mm") + data.getKey();
                if (!map.containsKey(keyDate)) {
                    DataSearchResult resultOne = new DataSearchResult();
                    resultOne.setBranchName(data.getBranch() != null ? data.getBranch().getName() : "");
                    resultOne.setContext(data.getElementname() + ":" + data.getValue());
                    String[] keyArray = data.getKey().split(Constants.KEY_SPLIT);
                    resultOne.setDanjuName(keyArray[0]);
                    resultOne.setSubmitdate(DateHelper.format(date, "yyyy-MM-dd HH:mm"));
                    if (keyArray.length > 1) {
                        resultOne.setKey(keyArray[1]);
                    } else {
                        resultOne.setKey(data.getKey());
                    }
                    map.put(keyDate, resultOne);
                } else {
                    map.get(keyDate).setContext(map.get(keyDate).getContext() + "," + data.getElementname() + ":" + data.getValue());
                }
            }

        }

        result.setCount(map.size());
        List<DataSearchResult> allData = new ArrayList();
        allData.addAll(map.values());
        result.setResult(allData);
        return result;
    }

}
