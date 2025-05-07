package com.sqin.tools.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/7 16:43
 * @Description
 **/
public interface MobileDirtyWordMapper {

    @Select("select dirtyword from mobile_dirtyword")
    List<String> findDirtyWord();

}
