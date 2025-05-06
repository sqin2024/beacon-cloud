package com.sqin.tools.mapper;

import com.sqin.tools.entity.ClientSign;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/6 20:35
 * @Description
 **/
public interface ClientSignMapper {

    @Select("select * from client_sign where client_id = #{clientId}")
    List<ClientSign> findByClientId(@Param("clientId") Long clientId);

}
