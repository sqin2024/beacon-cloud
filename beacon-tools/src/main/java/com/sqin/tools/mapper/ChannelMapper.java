package com.sqin.tools.mapper;

import com.sqin.tools.entity.Channel;
import com.sqin.tools.entity.ClientSign;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/6 20:35
 * @Description
 **/
public interface ChannelMapper {

    @Select("select * from channel where is_delete = 0")
    List<Channel> findAll();

}
