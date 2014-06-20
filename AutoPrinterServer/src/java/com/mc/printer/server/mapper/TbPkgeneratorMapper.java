package com.mc.printer.server.mapper;

import com.mc.printer.server.entity.TbPkgenerator;
import com.mc.printer.server.entity.TbPkgeneratorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbPkgeneratorMapper {
    int countByExample(TbPkgeneratorExample example);

    int deleteByExample(TbPkgeneratorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbPkgenerator record);

    int insertSelective(TbPkgenerator record);

    List<TbPkgenerator> selectByExample(TbPkgeneratorExample example);

    TbPkgenerator selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbPkgenerator record, @Param("example") TbPkgeneratorExample example);

    int updateByExample(@Param("record") TbPkgenerator record, @Param("example") TbPkgeneratorExample example);

    int updateByPrimaryKeySelective(TbPkgenerator record);

    int updateByPrimaryKey(TbPkgenerator record);
}