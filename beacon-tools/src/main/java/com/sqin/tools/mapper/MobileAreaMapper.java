package com.sqin.tools.mapper;

import com.sqin.tools.entity.MobileArea;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/7 15:33
 * @Description
 **/
public interface MobileAreaMapper {

    @Select("select mobile_number,mobile_area,mobile_type from mobile_area")
    List<MobileArea> findAll();

}
