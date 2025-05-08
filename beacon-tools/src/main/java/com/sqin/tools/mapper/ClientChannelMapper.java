package com.sqin.tools.mapper;

import com.sqin.tools.entity.ClientChannel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/6 20:35
 * @Description
 **/
public interface ClientChannelMapper {

    @Select("select * from client_channel where is_delete = 0")
    List<ClientChannel> findAll();

}
