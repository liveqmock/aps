package com.mc.printer.server.mapper;

import com.mc.printer.server.entity.TbBranch;
import com.mc.printer.server.entity.TbBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbBranchMapper {
    int countByExample(TbBranchExample example);

    int deleteByExample(TbBranchExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbBranch record);

    int insertSelective(TbBranch record);

    List<TbBranch> selectByExample(TbBranchExample example);

    TbBranch selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbBranch record, @Param("example") TbBranchExample example);

    int updateByExample(@Param("record") TbBranch record, @Param("example") TbBranchExample example);

    int updateByPrimaryKeySelective(TbBranch record);

    int updateByPrimaryKey(TbBranch record);
}