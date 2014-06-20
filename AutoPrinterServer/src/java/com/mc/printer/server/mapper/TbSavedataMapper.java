package com.mc.printer.server.mapper;

import com.mc.printer.server.entity.TbSavedata;
import com.mc.printer.server.entity.TbSavedataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbSavedataMapper {
    int countByExample(TbSavedataExample example);

    int deleteByExample(TbSavedataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbSavedata record);

    int insertSelective(TbSavedata record);

    List<TbSavedata> selectByExample(TbSavedataExample example);

    TbSavedata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbSavedata record, @Param("example") TbSavedataExample example);

    int updateByExample(@Param("record") TbSavedata record, @Param("example") TbSavedataExample example);

    int updateByPrimaryKeySelective(TbSavedata record);

    int updateByPrimaryKey(TbSavedata record);
}