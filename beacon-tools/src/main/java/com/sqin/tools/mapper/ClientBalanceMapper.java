package com.sqin.tools.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author Qin
 * @Date 2025/5/6 21:37
 * @Description
 **/
public interface ClientBalanceMapper {

    @Select("select balance from client_balance where client_id = #{clientId}")
    Long findByClientId(@Param("clientId") Long clientId);

}
