package com.mc.printer.server.mapper;

import com.mc.printer.server.entity.TbAuth;
import com.mc.printer.server.entity.TbAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAuthMapper {
    int countByExample(TbAuthExample example);

    int deleteByExample(TbAuthExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbAuth record);

    int insertSelective(TbAuth record);

    List<TbAuth> selectByExample(TbAuthExample example);

    TbAuth selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbAuth record, @Param("example") TbAuthExample example);

    int updateByExample(@Param("record") TbAuth record, @Param("example") TbAuthExample example);

    int updateByPrimaryKeySelective(TbAuth record);

    int updateByPrimaryKey(TbAuth record);
}