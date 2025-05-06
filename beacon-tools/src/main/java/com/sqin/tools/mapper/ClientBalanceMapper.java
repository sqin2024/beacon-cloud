package com.sqin.tools.mapper;

import com.sqin.tools.entity.ClientBalance;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author Qin
 * @Date 2025/5/6 21:37
 * @Description
 **/
public interface ClientBalanceMapper {

    @Select("select * from client_balance where client_id = #{clientId}")
    ClientBalance findByClientId(@Param("clientId") Long clientId);

}
