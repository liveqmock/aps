package com.mc.printer.server.mapper;

import com.mc.printer.server.entity.TbControl;
import com.mc.printer.server.entity.TbControlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbControlMapper {
    int countByExample(TbControlExample example);

    int deleteByExample(TbControlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbControl record);

    int insertSelective(TbControl record);

    List<TbControl> selectByExample(TbControlExample example);

    TbControl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbControl record, @Param("example") TbControlExample example);

    int updateByExample(@Param("record") TbControl record, @Param("example") TbControlExample example);

    int updateByPrimaryKeySelective(TbControl record);

    int updateByPrimaryKey(TbControl record);
}